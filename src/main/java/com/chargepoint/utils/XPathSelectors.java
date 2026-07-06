package com.chargepoint.utils;

/**
 * XPath Selectors for web elements.
 * Centralizes all XPath expressions for easier maintenance and testing.
 */
public class XPathSelectors {
    
    // Login/Navigation XPaths
    public static final String CLOSE_LOGIN_POPUP = "//div[@class='_2QfC02']/button";
    public static final String SEARCH_INPUT = "//input[@name='q']";
    
    // Product List XPaths
    public static final String SEARCH_RESULTS = "//ul[contains(@class,'col-12-12')]//li/div/a/div[2]";
    
    // Product Details XPaths
    public static final String PRODUCT_NAMES = "//div[@class='_32g5_j']/preceding-sibling::a[1]";
    public static final String PRODUCT_ORIGINAL_PRICE = "//div[@class='_32g5_j']/following-sibling::a[1]/div[1]/div[1]";
    public static final String PRODUCT_CURRENT_PRICE = "//div[@class='_32g5_j']/following-sibling::a[1]/div/div[2]";
    public static final String PRODUCT_DISCOUNT = "//div[@class='_32g5_j']/following-sibling::a[1]/div/div[3]";

    /**
     * Validate if an XPath is valid (not null or empty)
     * @param xpath the xpath string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidXPath(String xpath) {
        return xpath != null && !xpath.trim().isEmpty();
    }

    /**
     * Get XPath for dynamic element by index
     * @param baseXpath the base xpath
     * @param index the element index
     * @return the complete xpath with index
     */
    public static String getXPathByIndex(String baseXpath, int index) {
        return "(" + baseXpath + ")[" + index + "]";
    }
}