# ChargePoint

A Java-based Selenium WebDriver automation project for web scraping product information from e-commerce platforms.

## Overview

ChargePoint is a lightweight web automation tool that demonstrates automated browser interaction and data extraction using Selenium WebDriver. It automates product searches on Flipkart and extracts structured product data including names, original prices, current prices, and discount information.

## Features

- **Automated Browser Control**: Uses Firefox WebDriver to automate browser interactions
- **Product Search**: Automatically searches for products on e-commerce platforms
- **Data Extraction**: Scrapes and structures product information (name, pricing, discounts)
- **XPath Navigation**: Leverages XPath selectors for precise element location
- **Error Handling**: Implements try-catch blocks for robust error management

## Prerequisites

- **Java Development Kit (JDK)**: Java 8 or higher
- **Firefox Browser**: Latest version installed
- **Selenium WebDriver**: Version 4.10.0
- **IDE**: IntelliJ IDEA (or any Java IDE supporting Selenium)

## Setup & Installation

### 1. Clone the Repository
```bash
git clone https://github.com/adilcancode/ChargePoint.git
cd ChargePoint
```

### 2. Configure Selenium Library
- Open the project in IntelliJ IDEA
- The `ChargePoint.iml` file contains project configuration with Selenium 4.10.0 library reference
- Ensure the Selenium library is properly configured in your IDE's classpath

### 3. Compile
```bash
javac -cp selenium-server-4.10.0.jar src/Main.java
```

## Usage

Run the automation script:
```bash
java -cp .:selenium-server-4.10.0.jar Main
```

### What It Does
1. Initializes a Firefox WebDriver instance and maximizes the browser window
2. Navigates to Flipkart (https://www.flipkart.com/)
3. Handles and closes any modal dialogs
4. Searches for "boat blue" products
5. Extracts the top 10 FAssured product listings
6. Displays product information:
   - Product Name
   - Original Price
   - Current Price
   - Discount Percentage
7. Prints results to console
8. Closes the browser

### Example Output
```
FAssured Earphones Information:
Model 1: {Name=..., Original Price=..., Current Price=..., Discount=...}
Model 2: {Name=..., Original Price=..., Current Price=..., Discount=...}
...
```

## Project Structure

```
ChargePoint/
├── src/
│   └── Main.java              # Main automation script with Selenium WebDriver logic
├── ChargePoint.iml            # IntelliJ IDEA project configuration
├── .idea/                     # IDE configuration files
├── out/                       # Compiled class files directory
└── README.md                  # This file
```

## Key Implementation Details

### Technologies Used
- **Selenium WebDriver 4.10.0**: Browser automation framework
- **Firefox WebDriver**: Browser driver implementation
- **Java Collections**: HashMap for storing product data

### Important Methods
- `FirefoxDriver()`: Initializes Firefox browser instance
- `driver.manage().window().maximize()`: Maximizes browser window
- `driver.findElement()`: Locates single elements using XPath
- `driver.findElements()`: Locates multiple elements as a list
- `Actions().moveToElement()`: Simulates mouse hover interactions

### XPath Selectors
The script uses specific XPath expressions to target:
- Modal close buttons
- Search input fields
- Product list items
- Product attributes (name, price, discount)

## Error Handling

The script implements exception handling for:
- Modal dialog interactions (might not always be present)
- Search and element navigation failures
- WebElement interaction issues

## Limitations & Notes

- Currently hardcoded to search for "boat blue" products
- Designed specifically for Flipkart's HTML structure
- XPath selectors may break if the website structure changes
- No persistent storage; results printed to console only
- Single-threaded execution

## Future Enhancements

- Parameterize search queries
- Support multiple e-commerce platforms
- Export results to CSV/JSON
- Implement multithreading for parallel product extraction
- Add configuration file for dynamic XPath selectors
- Create test suite for validation

## Troubleshooting

### Firefox Driver Issues
- Ensure Firefox browser is installed and up-to-date
- Check that WebDriver version matches Firefox version
- Verify Firefox is accessible in system PATH

### NoSuchElementException
- Website structure may have changed
- Update XPath expressions to match current HTML
- Increase implicit wait time if elements load slowly

### Permission Errors
- Ensure file permissions allow execution
- Run with appropriate Java classpath configuration

## License

This project is provided as-is for educational and automation purposes.

## Author

**adilcancode** - GitHub: https://github.com/adilcancode

## Contributing

Feel free to fork this project and submit pull requests for improvements, bug fixes, or enhancements.

---

**Last Updated**: July 2023
