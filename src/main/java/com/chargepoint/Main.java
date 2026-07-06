package com.chargepoint;

import com.chargepoint.driver.DriverManager;
import com.chargepoint.extractor.ProductDataExtractor;
import com.chargepoint.exporter.DataExporter;
import com.chargepoint.model.Product;
import com.chargepoint.utils.XPathSelectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Main entry point for ChargePoint automation.
 * Orchestrates the product data extraction workflow.
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final String TARGET_URL = "https://www.flipkart.com/";
    private static final long INTERACTION_DELAY_MS = 1000;

    public static void main(String[] args) {
        DriverManager driverManager = new DriverManager();
        
        try {
            // Initialize driver
            driverManager.initializeDriver();
            
            // Navigate to target URL
            driverManager.getDriver().get(TARGET_URL);
            logger.info("Navigated to: " + TARGET_URL);
            
            // Close login popup
            closeLoginPopup(driverManager);
            
            // Perform search
            performSearch(driverManager, "boat blue");
            
            // Select first search result
            selectSearchResult(driverManager);
            
            // Extract product data
            ProductDataExtractor extractor = new ProductDataExtractor(driverManager.getDriver());
            List<Product> products = extractor.extractProductData();
            
            // Log extracted products
            logProducts(products);
            
            // Export data
            DataExporter exporter = new DataExporter();
            exporter.exportToCSV(products, "products.csv");
            exporter.exportToJSON(products, "products.json");
            
            logger.info("Automation completed successfully");
            
        } catch (Exception e) {
            logger.error("Error during automation execution", e);
        } finally {
            driverManager.quitDriver();
        }
    }

    /**
     * Close the login popup
     */
    private static void closeLoginPopup(DriverManager driverManager) {
        try {
            WebElement closeButton = driverManager.getDriver().findElement(By.xpath(XPathSelectors.CLOSE_LOGIN_POPUP));
            closeButton.click();
            logger.info("Closed login popup");
        } catch (Exception e) {
            logger.warn("Could not close login popup", e);
        }
    }

    /**
     * Perform search with the given query
     */
    private static void performSearch(DriverManager driverManager, String searchQuery) {
        try {
            WebElement searchInput = driverManager.getDriver().findElement(By.xpath(XPathSelectors.SEARCH_INPUT));
            searchInput.sendKeys(searchQuery);
            searchInput.submit();
            logger.info("Performed search for: " + searchQuery);
        } catch (Exception e) {
            logger.error("Error during search", e);
            throw new RuntimeException("Search failed", e);
        }
    }

    /**
     * Select the first search result
     */
    private static void selectSearchResult(DriverManager driverManager) {
        try {
            List<WebElement> searchResults = driverManager.getDriver()
                    .findElements(By.xpath(XPathSelectors.SEARCH_RESULTS));
            
            if (searchResults.isEmpty()) {
                logger.warn("No search results found");
                return;
            }
            
            // Interact with the last result
            Actions actions = new Actions(driverManager.getDriver());
            Thread.sleep(INTERACTION_DELAY_MS);
            actions.moveToElement(searchResults.get(searchResults.size() - 1))
                    .click()
                    .build()
                    .perform();
            
            logger.info("Selected search result");
        } catch (Exception e) {
            logger.error("Error selecting search result", e);
            throw new RuntimeException("Failed to select search result", e);
        }
    }

    /**
     * Log extracted product information
     */
    private static void logProducts(List<Product> products) {
        logger.info("========== Extracted Products ==========");
        for (int i = 0; i < products.size(); i++) {
            logger.info("Product " + (i + 1) + ": " + products.get(i));
        }
        logger.info("========== End of Products ==========");
    }
}