# ChargePoint Test Suite Documentation

## Overview
This document provides comprehensive documentation for the ChargePoint automation test suite. The test suite implements TestNG with Selenium testing best practices to ensure code quality and reliability.

## Test Structure

### 1. Configuration Tests (`ConfigurationTest.java`)
Tests the configuration loading and property management system.

**Test Cases:**
- `testGetPropertyWithDefaultValue()` - Verify default value retrieval
- `testGetProperty()` - Verify property retrieval
- `testSetAndGetProperty()` - Verify property setting and retrieval
- `testGetIntProperty()` - Verify integer property parsing
- `testGetIntPropertyWithDefaultValue()` - Verify default integer values
- `testGetIntPropertyWithInvalidValue()` - Verify error handling for invalid integers

**Coverage:** Configuration loading, property management

### 2. XPath Selector Tests (`XPathSelectorsTest.java`)
Validates XPath selectors and utility methods for web element identification.

**Test Cases:**
- `testXPathSelectorsNotEmpty()` - Verify all selectors are defined
- `testIsValidXPath()` - Validate XPath validation logic
- `testGetXPathByIndex()` - Verify dynamic XPath generation
- `testGetXPathByIndexWithMultipleIndices()` - Verify index-based XPath construction
- `testXPathFormatValid()` - Verify XPath format compliance

**Coverage:** XPath selector validation, utility method functionality

### 3. Product Model Tests (`ProductTest.java`)
Tests the Product data model and its operations.

**Test Cases:**
- `testProductCreationWithConstructor()` - Verify object construction
- `testProductSettersAndGetters()` - Verify property accessors
- `testProductEquality()` - Verify equality comparison
- `testProductInequality()` - Verify inequality comparison
- `testProductHashCode()` - Verify hash code generation
- `testProductToString()` - Verify string representation
- `testProductEmptyConstructor()` - Verify default initialization

**Coverage:** Product model data representation, object comparison

### 4. Driver Manager Tests (`DriverManagerTest.java`)
Tests WebDriver lifecycle management and initialization.

**Test Cases:**
- `testDriverNotInitializedByDefault()` - Verify initial state
- `testInitializeDriver()` - Verify driver initialization
- `testGetDriverBeforeInitialization()` - Verify error handling
- `testSetImplicitTimeout()` - Verify timeout configuration
- `testQuitDriver()` - Verify driver cleanup
- `testQuitDriverWhenNotInitialized()` - Verify safe cleanup
- `testMultipleInitialization()` - Verify reinitialization capability

**Coverage:** Driver lifecycle management, error handling, resource cleanup

### 5. Product Data Extractor Tests (`ProductDataExtractorTest.java`)
Tests product data extraction logic with mocked WebDriver.

**Test Cases:**
- `testProductDataExtractorInitialization()` - Verify object creation
- `testProductDataExtractorWithNullDriver()` - Verify null checking
- `testExtractProductDataWithEmptyList()` - Verify empty data handling
- `testExtractProductDataWithValidElements()` - Verify successful extraction
- `testGetProductCount()` - Verify count functionality
- `testGetProductCountWithEmptyList()` - Verify empty list handling
- `testExtractProductByIndex()` - Verify index-based extraction
- `testExtractProductByInvalidIndex()` - Verify index error handling

**Coverage:** Product data extraction, error handling, recovery mechanisms

**Mock Elements Used:**
- WebDriver (mocked)
- WebElement (mocked)
- By locators (any locator type)

### 6. Data Exporter Tests (`DataExporterTest.java`)
Tests data export functionality for CSV and JSON formats.

**Test Cases:**
- `testExportToCSV()` - Verify CSV file creation
- `testExportToCSVContent()` - Verify CSV content accuracy
- `testExportToCSVWithSpecialCharacters()` - Verify special character handling
- `testExportToJSON()` - Verify JSON file creation
- `testExportToJSONContent()` - Verify JSON content accuracy
- `testExportToJSONStructure()` - Verify JSON structure compliance
- `testExportEmptyProductList()` - Verify empty data handling
- `testExportToJSONWithSpecialCharacters()` - Verify JSON escaping

**Coverage:** Data export functionality, file I/O operations, format compliance

## Test Coverage

### Coverage Areas
- ✅ Product data extraction logic
- ✅ XPath selector validation
- ✅ Data export functionality (CSV, JSON)
- ✅ Error handling and recovery
- ✅ Configuration loading
- ✅ WebDriver lifecycle management
- ✅ Multi-threaded operations (testng.xml parallel execution)

### Current Coverage Metrics
- **Target:** Minimum 80% code coverage
- **Measurement Tool:** JaCoCo Maven Plugin
- **Report Location:** `target/site/jacoco/index.html`

## Running Tests

### Prerequisites
- Java 11+
- Maven 3.6+
- Firefox browser (for integration tests)

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test Class
```bash
mvn test -Dtest=ProductTest
```

### Run Tests with Code Coverage Report
```bash
mvn clean test jacoco:report
```

### Run Tests in Parallel
```bash
mvn test -DparallelSuites=2
```

### View Coverage Report
After running tests with coverage:
```bash
# Open in browser
open target/site/jacoco/index.html
```

## TestNG Configuration

### Test Suite File: `testng.xml`
- **Parallel Execution:** Methods (thread-count: 3)
- **Test Groups:** Configuration, Utility, Model, Driver, Extraction, Export

### Example Test Run Output
```
[INFO] Tests run: 30, Failures: 0, Skips: 0, Time elapsed: 5.234 s
[INFO] Code Coverage: 82.5%
[INFO] BUILD SUCCESS
```

## Best Practices Used

### 1. Mocking and Isolation
- WebDriver interactions are mocked to avoid browser dependencies
- Tests run independently without side effects

### 2. Error Handling
- All edge cases are tested (null values, empty lists, invalid inputs)
- Exception handling is verified

### 3. Data-Driven Testing
- Test data is created in `@BeforeMethod` setup
- Cleanup is performed in `@AfterMethod` tearDown

### 4. Test Independence
- Each test can run in any order
- No dependencies between test methods
- Resources are properly cleaned up

### 5. Assertions
- Clear assertion messages
- Multiple assertions per test for comprehensive validation

## Integration Tests

### Running Integration Tests
Integration tests with real Selenium WebDriver can be run separately:

```bash
mvn test -Dgroups=integration
```

### Integration Test Example
```java
@Test(groups = "integration")
public void testEndToEndWorkflow() {
    // Full automation workflow test
}
```

## Continuous Integration

### Maven Configuration
Tests are automatically run on:
- Every build: `mvn clean test`
- Pre-commit hooks
- CI/CD pipeline

### Code Coverage Check
The build fails if code coverage drops below 80%:
```xml
<limit>
    <counter>LINE</counter>
    <value>COVEREDRATIO</value>
    <minimum>0.80</minimum>
</limit>
```

## Troubleshooting

### Common Issues

**Issue:** Tests fail with "WebDriver not initialized"
- **Solution:** Ensure `@BeforeMethod` is properly calling `setUp()`

**Issue:** Timeout exceptions in tests
- **Solution:** Increase implicit wait in DriverManager

**Issue:** ClassNotFoundException for test classes
- **Solution:** Ensure test classes are in `src/test/java` directory

## Future Enhancements

1. Add Performance Testing
2. Implement BDD with Cucumber
3. Add API Testing with REST Assured
4. Implement Visual Regression Testing
5. Add Database Validation Tests

## Support and Maintenance

For questions or issues:
1. Check existing test documentation
2. Review test class comments
3. Run tests with debug logs: `mvn test -X`

## References

- [TestNG Documentation](https://testng.org/doc/)
- [Selenium WebDriver Documentation](https://www.selenium.dev/documentation/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [JaCoCo Documentation](https://www.jacoco.org/jacoco/)
