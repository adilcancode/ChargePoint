# Configuration Management Guide

This guide explains how to use the configuration file system for managing XPath selectors and settings dynamically.

## Overview

The configuration system allows you to:
- Store XPath selectors in YAML files instead of hardcoding them
- Support multiple platforms with platform-specific configurations
- Easily update selectors when websites change their structure
- Enable non-developers to modify selectors without code changes
- Manage wait times, retry policies, and feature flags

## Configuration Structure

Configuration files are organized in the following structure:

```yaml
platform: flipkart              # Platform identifier
version: "1.0.0"               # Configuration version
description: "..."

navigation:                     # Navigation and modal selectors
  closeLoginPopup: "//xpath"
  searchInput: "//xpath"
  searchButton: "//xpath"

productList:                    # Product list selectors
  searchResults: "//xpath"
  productContainer: "//xpath"

productDetails:                 # Product detail selectors
  names: "//xpath"
  originalPrice: "//xpath"
  currentPrice: "//xpath"
  discount: "//xpath"
  rating: "//xpath"

modals:                         # Modal and popup selectors
  loginModal: "//xpath"
  notificationBanner: "//xpath"

waitTimes:                      # Wait configurations (in seconds)
  implicitWait: 10
  pageLoadWait: 15
  elementWait: 5

retryPolicies:                  # Retry configuration
  maxRetries: 3
  retryDelayMs: 1000
  backoffMultiplier: 1.5

features:                       # Feature flags
  enableHeadlessMode: false
  enableScreenshots: true
  enableLogging: true
```

## Available Configurations

### Platform-Specific Files

- `selectors-flipkart.yaml` - Flipkart e-commerce platform
- `selectors-amazon.yaml` - Amazon e-commerce platform

### Directory Structure

```
src/main/resources/config/
├── selectors-flipkart.yaml
└── selectors-amazon.yaml
```

## Usage Examples

### Loading Configuration from Classpath

```java
ConfigurationLoader loader = new ConfigurationLoader();
ConfigurationModel config = loader.loadFromClasspath("selectors-flipkart.yaml");

String closePopupXpath = config.getSelector("navigation", "closeLoginPopup");
int implicitWait = config.getWaitTime("implicitWait");
```

### Loading Configuration from External File

```java
ConfigurationLoader loader = new ConfigurationLoader();
ConfigurationModel config = loader.loadFromFile("/path/to/selectors-flipkart.yaml");
```

### Using Fallback Mechanism

```java
ConfigurationLoader loader = new ConfigurationLoader();

// Try external file first, fall back to classpath
ConfigurationModel config = loader.loadWithFallback(
    "/etc/chargepoint/selectors-flipkart.yaml",
    "selectors-flipkart.yaml"
);
```

### Using SelectorRegistry

```java
ConfigurationLoader loader = new ConfigurationLoader();
ConfigurationModel config = loader.loadFromClasspath("selectors-flipkart.yaml");
SelectorRegistry registry = new SelectorRegistry(config);

// Get selector (with caching)
String searchInputXpath = registry.getSelector("searchInput");

// Register custom selector
registry.registerSelector("customElement", "//custom[@id='element']");

// Clear cache when needed
registry.clearCache();
```

## Adding New Configuration Files

### Steps to Add a New Platform

1. Create a new YAML file: `src/main/resources/config/selectors-{platform}.yaml`

2. Define the configuration structure:

```yaml
platform: mynewplatform
version: "1.0.0"
description: "Configuration for my new platform"

navigation:
  closeLoginPopup: "//div[@id='login-modal']//button[@class='close']"
  searchInput: "//input[@id='search-box']"
  searchButton: "//button[@type='submit']"

productDetails:
  names: "//h2[@class='product-title']"
  originalPrice: "//span[@class='original-price']"
  currentPrice: "//span[@class='current-price']"
  discount: "//span[@class='discount-percent']"

waitTimes:
  implicitWait: 10
  pageLoadWait: 15
  elementWait: 5

retryPolicies:
  maxRetries: 3
  retryDelayMs: 1000
  backoffMultiplier: 1.5

features:
  enableHeadlessMode: false
  enableScreenshots: true
  enableLogging: true
```

3. Load the configuration in your code:

```java
ConfigurationModel config = loader.loadFromClasspath("selectors-mynewplatform.yaml");
```

## Updating Selectors

When a website changes its structure:

1. Open the corresponding configuration file
2. Use browser developer tools to find the new XPath
3. Update the selector value in the YAML file
4. No code changes needed!

### Example: Updating a Selector

**Before:**
```yaml
navigation:
  closeLoginPopup: "//div[@class='_2QfC02']/button"
```

**After Website Structure Change:**
```yaml
navigation:
  closeLoginPopup: "//div[@id='modal-login']//button[@aria-label='Close']"
```

## Validation

Validate configuration files programmatically:

```java
ConfigurationLoader loader = new ConfigurationLoader();
ConfigurationModel config = loader.loadFromClasspath("selectors-flipkart.yaml");

if (loader.validateConfiguration(config)) {
    System.out.println("Configuration is valid");
} else {
    System.out.println("Configuration validation failed");
}
```

## Best Practices

1. **Keep XPaths Generic**: Use relative XPaths that are less likely to break
   ```xpath
   // Good - less brittle
   //button[@aria-label='Close']
   
   // Bad - too specific
   //div[@class='container']//div[@class='header']//div//button
   ```

2. **Use Semantic Selectors**: Prefer element attributes that describe purpose
   ```xpath
   // Better
   //input[@name='search']
   
   // Less reliable
   //input[@class='abc-123-xyz']
   ```

3. **Organize by Function**: Group selectors by their purpose (navigation, product details, etc.)

4. **Version Your Configurations**: Update version when making changes

5. **Document Changes**: Add comments to explain why selectors were changed
   ```yaml
   productDetails:
     # Changed on 2024-01-15 due to website redesign
     names: "//h3[@class='product-name']"
   ```

6. **Test After Updates**: Always test selectors with the application

## Troubleshooting

### Configuration File Not Found

```
ConfigurationException: Configuration file not found on classpath: config/selectors-myplatform.yaml
```

**Solution:**
- Ensure the file exists in `src/main/resources/config/`
- Run `mvn clean compile` to update resources
- Check the filename spelling and extension

### XPath Selector Returns Null

```java
String selector = registry.getSelector("unknownSelector");
// Returns null or fallback value
```

**Solution:**
- Verify the selector name exists in the configuration file
- Check the exact spelling in the YAML file
- Use a fallback mechanism with `loadWithFallback()`

### Configuration Parse Error

```
ConfigurationException: Failed to load configuration: selectors-flipkart.yaml
Caused by: org.yaml.error.YAMLException: ...
```

**Solution:**
- Validate YAML syntax (check indentation, colons, quotes)
- Use an online YAML validator
- Ensure no tabs are used (YAML requires spaces)

## Version Management

Configuration versions help track changes:

```yaml
version: "1.0.0"  # Major.Minor.Patch format
```

### Version Increment Guide

- **Major (1.x.x)**: Breaking changes that require application updates
- **Minor (x.1.x)**: New selectors or settings added
- **Patch (x.x.1)**: Bug fixes or selector updates for existing functionality

## Integration with Application

### In Main Application

```java
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    
    public static void main(String[] args) {
        ConfigurationLoader loader = new ConfigurationLoader();
        ConfigurationModel config = loader.loadFromClasspath("selectors-flipkart.yaml");
        SelectorRegistry registry = new SelectorRegistry(config);
        
        // Use selectors from configuration
        String searchInputXpath = registry.getSelector("searchInput");
        driver.findElement(By.xpath(searchInputXpath)).sendKeys("search term");
    }
}
```

## Fallback Mechanism

The system provides automatic fallbacks for critical selectors:

- `closeLoginPopup` → `//button[@aria-label='Close']`
- `searchInput` → `//input[contains(@placeholder, 'search')]`
- `searchButton` → `//button[contains(text(), 'Search')]`

These ensure the application continues to function even if configuration files are missing.

## Performance Considerations

- **Caching**: SelectorRegistry caches selectors to avoid repeated lookups
- **Lazy Loading**: Selectors are only loaded when accessed
- **Memory**: Typical configuration takes < 1KB

## Testing Configurations

See `ConfigurationLoaderTest.java` and `SelectorRegistryTest.java` for comprehensive test examples.

## Additional Resources

- YAML Syntax: https://yaml.org/spec/1.2/spec.html
- XPath Tutorial: https://www.w3schools.com/xml/xpath_intro.asp
- Selenium Locators: https://www.selenium.dev/documentation/webdriver/elements/locators/
