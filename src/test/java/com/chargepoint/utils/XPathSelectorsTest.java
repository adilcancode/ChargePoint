package com.chargepoint.utils;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Test suite for XPathSelectors utility class.
 * Validates XPath selector constants and utility methods.
 */
public class XPathSelectorsTest {

    @Test
    public void testXPathSelectorsNotEmpty() {
        assertNotNull(XPathSelectors.CLOSE_LOGIN_POPUP);
        assertNotNull(XPathSelectors.SEARCH_INPUT);
        assertNotNull(XPathSelectors.SEARCH_RESULTS);
        assertNotNull(XPathSelectors.PRODUCT_NAMES);
        assertNotNull(XPathSelectors.PRODUCT_ORIGINAL_PRICE);
        assertNotNull(XPathSelectors.PRODUCT_CURRENT_PRICE);
        assertNotNull(XPathSelectors.PRODUCT_DISCOUNT);
    }

    @Test
    public void testIsValidXPath() {
        assertTrue(XPathSelectors.isValidXPath("//div[@class='test']"));
        assertFalse(XPathSelectors.isValidXPath(null));
        assertFalse(XPathSelectors.isValidXPath(""));
        assertFalse(XPathSelectors.isValidXPath("   "));
    }

    @Test
    public void testGetXPathByIndex() {
        String baseXpath = "//div[@class='item']";
        String result = XPathSelectors.getXPathByIndex(baseXpath, 1);
        assertEquals(result, "(//div[@class='item'])[1]");
    }

    @Test
    public void testGetXPathByIndexWithMultipleIndices() {
        String baseXpath = "//table//tr";
        String result = XPathSelectors.getXPathByIndex(baseXpath, 5);
        assertEquals(result, "(//table//tr)[5]");
    }

    @Test
    public void testXPathFormatValid() {
        assertTrue(XPathSelectors.CLOSE_LOGIN_POPUP.startsWith("//"));
        assertTrue(XPathSelectors.SEARCH_INPUT.startsWith("//"));
        assertTrue(XPathSelectors.PRODUCT_NAMES.startsWith("//"));
    }
}