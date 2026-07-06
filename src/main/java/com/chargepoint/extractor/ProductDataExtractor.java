package com.chargepoint.extractor;

import com.chargepoint.model.Product;
import com.chargepoint.utils.XPathSelectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Extracts product data from web pages.
 * Handles data extraction logic with error handling and recovery.
 */
public class ProductDataExtractor {
    private static final Logger logger = LoggerFactory.getLogger(ProductDataExtractor.class);
    private final WebDriver driver;
    private static final int MAX_PRODUCTS = 10;

    public ProductDataExtractor(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver cannot be null");
        }
        this.driver = driver;
    }

    /**
     * Extract product information from the page
     * @return list of extracted products
     */
    public List<Product> extractProductData() {
        List<Product> products = new ArrayList<>();
        
        try {
            List<WebElement> productNames = driver.findElements(By.xpath(XPathSelectors.PRODUCT_NAMES));
            List<WebElement> originalPrices = driver.findElements(By.xpath(XPathSelectors.PRODUCT_ORIGINAL_PRICE));
            List<WebElement> currentPrices = driver.findElements(By.xpath(XPathSelectors.PRODUCT_CURRENT_PRICE));
            List<WebElement> discounts = driver.findElements(By.xpath(XPathSelectors.PRODUCT_DISCOUNT));

            int productsToExtract = Math.min(MAX_PRODUCTS, Math.min(productNames.size(), 
                    Math.min(originalPrices.size(), Math.min(currentPrices.size(), discounts.size()))));

            for (int i = 0; i < productsToExtract; i++) {
                try {
                    Product product = new Product(
                            productNames.get(i).getText(),
                            originalPrices.get(i).getText(),
                            currentPrices.get(i).getText(),
                            discounts.get(i).getText()
                    );
                    products.add(product);
                    logger.debug("Extracted product: " + product.getName());
                } catch (Exception e) {
                    logger.warn("Error extracting product at index " + i, e);
                    // Continue with next product
                }
            }

            logger.info("Successfully extracted " + products.size() + " products");
        } catch (Exception e) {
            logger.error("Error during product data extraction", e);
            throw new RuntimeException("Failed to extract product data", e);
        }

        return products;
    }

    /**
     * Extract single product by index
     * @param index the product index
     * @return the extracted product or null if not found
     */
    public Product extractProductByIndex(int index) {
        try {
            List<WebElement> productNames = driver.findElements(By.xpath(XPathSelectors.PRODUCT_NAMES));
            List<WebElement> originalPrices = driver.findElements(By.xpath(XPathSelectors.PRODUCT_ORIGINAL_PRICE));
            List<WebElement> currentPrices = driver.findElements(By.xpath(XPathSelectors.PRODUCT_CURRENT_PRICE));
            List<WebElement> discounts = driver.findElements(By.xpath(XPathSelectors.PRODUCT_DISCOUNT));

            if (index < productNames.size() && index < originalPrices.size() && 
                index < currentPrices.size() && index < discounts.size()) {
                return new Product(
                        productNames.get(index).getText(),
                        originalPrices.get(index).getText(),
                        currentPrices.get(index).getText(),
                        discounts.get(index).getText()
                );
            }
        } catch (Exception e) {
            logger.error("Error extracting product at index " + index, e);
        }
        return null;
    }

    /**
     * Get the number of available products on page
     * @return count of products
     */
    public int getProductCount() {
        try {
            return driver.findElements(By.xpath(XPathSelectors.PRODUCT_NAMES)).size();
        } catch (Exception e) {
            logger.warn("Error getting product count", e);
            return 0;
        }
    }
}