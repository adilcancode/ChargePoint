package com.chargepoint.config;

import java.util.Map;
import java.util.Objects;

/**
 * Model class representing the configuration structure loaded from YAML.
 * Provides typed access to configuration properties.
 */
public class ConfigurationModel {
    private final Map<String, Object> rawData;
    
    public ConfigurationModel(Map<String, Object> rawData) {
        this.rawData = Objects.requireNonNull(rawData, "Raw configuration data cannot be null");
    }
    
    /**
     * Get the platform name
     * @return platform name (e.g., "flipkart", "amazon")
     */
    public String getPlatform() {
        return (String) rawData.get("platform");
    }
    
    /**
     * Get the configuration version
     * @return version string
     */
    public String getVersion() {
        return (String) rawData.get("version");
    }
    
    /**
     * Get the configuration description
     * @return description string
     */
    public String getDescription() {
        return (String) rawData.get("description");
    }
    
    /**
     * Get all navigation selectors
     * @return Map containing navigation XPath selectors
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> getNavigationSelectors() {
        return (Map<String, String>) rawData.get("navigation");
    }
    
    /**
     * Get all product list selectors
     * @return Map containing product list XPath selectors
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> getProductListSelectors() {
        return (Map<String, String>) rawData.get("productList");
    }
    
    /**
     * Get all product detail selectors
     * @return Map containing product detail XPath selectors
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> getProductDetailSelectors() {
        return (Map<String, String>) rawData.get("productDetails");
    }
    
    /**
     * Get all modal selectors
     * @return Map containing modal XPath selectors
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> getModalSelectors() {
        return (Map<String, String>) rawData.get("modals");
    }
    
    /**
     * Get all wait time configurations
     * @return Map containing wait time settings in seconds
     */
    @SuppressWarnings("unchecked")
    public Map<String, Integer> getWaitTimes() {
        return (Map<String, Integer>) rawData.get("waitTimes");
    }
    
    /**
     * Get all retry policy configurations
     * @return Map containing retry policy settings
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getRetryPolicies() {
        return (Map<String, Object>) rawData.get("retryPolicies");
    }
    
    /**
     * Get all feature flags
     * @return Map containing feature flag configurations
     */
    @SuppressWarnings("unchecked")
    public Map<String, Boolean> getFeatures() {
        return (Map<String, Boolean>) rawData.get("features");
    }
    
    /**
     * Get a specific XPath selector by category and key
     * @param category the selector category (navigation, productList, productDetails, modals)
     * @param key the specific selector key
     * @return the XPath selector string, or null if not found
     */
    public String getSelector(String category, String key) {
        Map<String, String> categoryMap = null;
        
        switch (category.toLowerCase()) {
            case "navigation":
                categoryMap = getNavigationSelectors();
                break;
            case "productlist":
                categoryMap = getProductListSelectors();
                break;
            case "productdetails":
                categoryMap = getProductDetailSelectors();
                break;
            case "modals":
                categoryMap = getModalSelectors();
                break;
        }
        
        if (categoryMap != null) {
            return categoryMap.get(key);
        }
        
        return null;
    }
    
    /**
     * Get a specific wait time configuration
     * @param key the wait time key (implicitWait, pageLoadWait, elementWait)
     * @return the wait time in seconds
     */
    public int getWaitTime(String key) {
        Map<String, Integer> waitTimes = getWaitTimes();
        return waitTimes != null ? waitTimes.getOrDefault(key, 10) : 10;
    }
    
    /**
     * Get a specific retry policy configuration
     * @param key the retry policy key
     * @return the retry policy value
     */
    public Object getRetryPolicy(String key) {
        Map<String, Object> policies = getRetryPolicies();
        return policies != null ? policies.get(key) : null;
    }
    
    /**
     * Get the raw configuration data map
     * @return the raw Map containing all configuration data
     */
    public Map<String, Object> getRawData() {
        return rawData;
    }
}
