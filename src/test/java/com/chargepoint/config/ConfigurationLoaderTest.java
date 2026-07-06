package com.chargepoint.config;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Test suite for ConfigurationLoader.
 * Tests configuration loading from classpath resources.
 */
public class ConfigurationLoaderTest {
    
    private ConfigurationLoader loader;
    
    @BeforeClass
    public void setUp() {
        loader = new ConfigurationLoader();
    }
    
    @Test
    public void testLoadFlipkartConfigurationFromClasspath() {
        ConfigurationModel config = loader.loadFromClasspath("selectors-flipkart.yaml");
        
        assertNotNull(config);
        assertEquals(config.getPlatform(), "flipkart");
        assertEquals(config.getVersion(), "1.0.0");
        assertNotNull(config.getDescription());
    }
    
    @Test
    public void testLoadAmazonConfigurationFromClasspath() {
        ConfigurationModel config = loader.loadFromClasspath("selectors-amazon.yaml");
        
        assertNotNull(config);
        assertEquals(config.getPlatform(), "amazon");
        assertEquals(config.getVersion(), "1.0.0");
        assertNotNull(config.getDescription());
    }
    
    @Test
    public void testLoadNavigationSelectors() {
        ConfigurationModel config = loader.loadFromClasspath("selectors-flipkart.yaml");
        
        var navigationSelectors = config.getNavigationSelectors();
        assertNotNull(navigationSelectors);
        assertNotNull(navigationSelectors.get("closeLoginPopup"));
        assertNotNull(navigationSelectors.get("searchInput"));
    }
    
    @Test
    public void testLoadProductDetailSelectors() {
        ConfigurationModel config = loader.loadFromClasspath("selectors-flipkart.yaml");
        
        var productSelectors = config.getProductDetailSelectors();
        assertNotNull(productSelectors);
        assertNotNull(productSelectors.get("names"));
        assertNotNull(productSelectors.get("originalPrice"));
        assertNotNull(productSelectors.get("currentPrice"));
        assertNotNull(productSelectors.get("discount"));
    }
    
    @Test
    public void testLoadWaitTimes() {
        ConfigurationModel config = loader.loadFromClasspath("selectors-flipkart.yaml");
        
        var waitTimes = config.getWaitTimes();
        assertNotNull(waitTimes);
        assertTrue(waitTimes.containsKey("implicitWait"));
        assertTrue(waitTimes.containsKey("pageLoadWait"));
        assertTrue(waitTimes.containsKey("elementWait"));
    }
    
    @Test
    public void testLoadRetryPolicies() {
        ConfigurationModel config = loader.loadFromClasspath("selectors-flipkart.yaml");
        
        var retryPolicies = config.getRetryPolicies();
        assertNotNull(retryPolicies);
        assertTrue(retryPolicies.containsKey("maxRetries"));
        assertTrue(retryPolicies.containsKey("retryDelayMs"));
        assertTrue(retryPolicies.containsKey("backoffMultiplier"));
    }
    
    @Test
    public void testLoadFeatureFlags() {
        ConfigurationModel config = loader.loadFromClasspath("selectors-flipkart.yaml");
        
        var features = config.getFeatures();
        assertNotNull(features);
        assertTrue(features.containsKey("enableHeadlessMode"));
        assertTrue(features.containsKey("enableScreenshots"));
        assertTrue(features.containsKey("enableLogging"));
    }
    
    @Test
    public void testValidateConfiguration() {
        ConfigurationModel config = loader.loadFromClasspath("selectors-flipkart.yaml");
        
        assertTrue(loader.validateConfiguration(config));
    }
    
    @Test
    public void testGetSpecificSelector() {
        ConfigurationModel config = loader.loadFromClasspath("selectors-flipkart.yaml");
        
        String closePopupSelector = config.getSelector("navigation", "closeLoginPopup");
        assertNotNull(closePopupSelector);
        assertEquals(closePopupSelector, "//div[@class='_2QfC02']/button");
    }
    
    @Test
    public void testLoadInvalidConfigurationThrowsException() {
        assertThrows(ConfigurationException.class, () -> {
            loader.loadFromClasspath("non-existent-file.yaml");
        });
    }
    
    @Test
    public void testGetWaitTimeWithDefault() {
        ConfigurationModel config = loader.loadFromClasspath("selectors-flipkart.yaml");
        
        int implicitWait = config.getWaitTime("implicitWait");
        assertTrue(implicitWait > 0);
    }
}
