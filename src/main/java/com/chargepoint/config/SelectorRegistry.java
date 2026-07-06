package com.chargepoint.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Registry for managing and caching selectors from configuration.
 * Provides easy access to XPath selectors with fallback support.
 */
public class SelectorRegistry {
    private static final Logger logger = LoggerFactory.getLogger(SelectorRegistry.class);
    
    private final ConfigurationModel configuration;
    private final Map<String, String> selectorCache;
    private final Map<String, String> fallbackSelectors;
    
    public SelectorRegistry(ConfigurationModel configuration) {
        this.configuration = Objects.requireNonNull(configuration, "Configuration cannot be null");
        this.selectorCache = new HashMap<>();
        this.fallbackSelectors = new HashMap<>();
        initializeFallbackSelectors();
    }
    
    /**
     * Initialize fallback selectors for critical operations.
     * Ensures minimum functionality even if configuration is incomplete.
     */
    private void initializeFallbackSelectors() {
        fallbackSelectors.put("closeLoginPopup", "//button[@aria-label='Close']");
        fallbackSelectors.put("searchInput", "//input[contains(@placeholder, 'search')]");
        fallbackSelectors.put("searchButton", "//button[contains(text(), 'Search')]");
    }
    
    /**
     * Get a selector by name with caching.
     * @param selectorName the name of the selector (e.g., "closeLoginPopup", "productNames")
     * @return the XPath selector string
     */
    public String getSelector(String selectorName) {
        Objects.requireNonNull(selectorName, "Selector name cannot be null");
        
        // Check cache first
        if (selectorCache.containsKey(selectorName)) {
            return selectorCache.get(selectorName);
        }
        
        // Try to find in configuration
        String selector = findSelectorInConfiguration(selectorName);
        
        // Use fallback if not found
        if (selector == null) {
            selector = fallbackSelectors.get(selectorName);
            if (selector != null) {
                logger.warn("Using fallback selector for: " + selectorName);
            }
        }
        
        // Cache the result
        if (selector != null) {
            selectorCache.put(selectorName, selector);
        }
        
        return selector;
    }
    
    /**
     * Search for a selector in the configuration hierarchy.
     * @param selectorName the name of the selector
     * @return the XPath selector string, or null if not found
     */
    private String findSelectorInConfiguration(String selectorName) {
        Map<String, String> navigationSelectors = configuration.getNavigationSelectors();
        if (navigationSelectors != null && navigationSelectors.containsKey(selectorName)) {
            return navigationSelectors.get(selectorName);
        }
        
        Map<String, String> productListSelectors = configuration.getProductListSelectors();
        if (productListSelectors != null && productListSelectors.containsKey(selectorName)) {
            return productListSelectors.get(selectorName);
        }
        
        Map<String, String> productDetailSelectors = configuration.getProductDetailSelectors();
        if (productDetailSelectors != null && productDetailSelectors.containsKey(selectorName)) {
            return productDetailSelectors.get(selectorName);
        }
        
        Map<String, String> modalSelectors = configuration.getModalSelectors();
        if (modalSelectors != null && modalSelectors.containsKey(selectorName)) {
            return modalSelectors.get(selectorName);
        }
        
        return null;
    }
    
    /**
     * Register a custom selector, overriding configuration if needed.
     * @param selectorName the name of the selector
     * @param xpath the XPath expression
     */
    public void registerSelector(String selectorName, String xpath) {
        Objects.requireNonNull(selectorName, "Selector name cannot be null");
        Objects.requireNonNull(xpath, "XPath cannot be null");
        
        selectorCache.put(selectorName, xpath);
        logger.info("Registered custom selector: " + selectorName);
    }
    
    /**
     * Clear the selector cache.
     */
    public void clearCache() {
        selectorCache.clear();
        logger.debug("Selector cache cleared");
    }
    
    /**
     * Get all cached selectors.
     * @return immutable map of cached selectors
     */
    public Map<String, String> getCachedSelectors() {
        return Collections.unmodifiableMap(selectorCache);
    }
    
    /**
     * Get the underlying configuration.
     * @return the ConfigurationModel
     */
    public ConfigurationModel getConfiguration() {
        return configuration;
    }
}
