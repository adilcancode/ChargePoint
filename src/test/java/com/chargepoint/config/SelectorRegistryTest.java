package com.chargepoint.config;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Test suite for SelectorRegistry.
 * Tests selector caching, registration, and fallback mechanisms.
 */
public class SelectorRegistryTest {
    
    private ConfigurationLoader loader;
    private SelectorRegistry registry;
    
    @BeforeClass
    public void setUp() {
        loader = new ConfigurationLoader();
        ConfigurationModel config = loader.loadFromClasspath("selectors-flipkart.yaml");
        registry = new SelectorRegistry(config);
    }
    
    @Test
    public void testGetSelectorFromConfiguration() {
        String selector = registry.getSelector("closeLoginPopup");
        assertNotNull(selector);
        assertEquals(selector, "//div[@class='_2QfC02']/button");
    }
    
    @Test
    public void testSelectorCaching() {
        String selector1 = registry.getSelector("searchInput");
        String selector2 = registry.getSelector("searchInput");
        
        assertNotNull(selector1);
        assertNotNull(selector2);
        assertEquals(selector1, selector2);
        assertTrue(registry.getCachedSelectors().containsKey("searchInput"));
    }
    
    @Test
    public void testRegisterCustomSelector() {
        String customXpath = "//custom[@path='test']/element";
        registry.registerSelector("customSelector", customXpath);
        
        String retrievedSelector = registry.getSelector("customSelector");
        assertEquals(retrievedSelector, customXpath);
    }
    
    @Test
    public void testFallbackSelectorForUnknownKey() {
        // Request a selector that's in fallback
        String selector = registry.getSelector("searchButton");
        assertNotNull(selector);
    }
    
    @Test
    public void testClearCache() {
        registry.getSelector("closeLoginPopup");
        assertTrue(registry.getCachedSelectors().size() > 0);
        
        registry.clearCache();
        assertTrue(registry.getCachedSelectors().isEmpty());
    }
    
    @Test
    public void testGetCachedSelectorsReturnsUnmodifiableMap() {
        registry.getSelector("searchInput");
        var cached = registry.getCachedSelectors();
        
        assertThrows(UnsupportedOperationException.class, () -> {
            cached.put("newKey", "value");
        });
    }
    
    @Test
    public void testGetUnderlyingConfiguration() {
        ConfigurationModel config = registry.getConfiguration();
        assertNotNull(config);
        assertEquals(config.getPlatform(), "flipkart");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void testNullSelectorNameThrowsException() {
        registry.getSelector(null);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void testNullConfigurationThrowsException() {
        new SelectorRegistry(null);
    }
}
