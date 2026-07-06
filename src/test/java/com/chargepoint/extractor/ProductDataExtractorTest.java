package com.chargepoint.extractor;

import com.chargepoint.model.Product;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

/**
 * Test suite for ProductDataExtractor class.
 * Tests product data extraction logic with mocked WebDriver.
 */
public class ProductDataExtractorTest {
    @Mock
    private WebDriver mockDriver;

    @Mock
    private WebElement mockElement;

    private ProductDataExtractor extractor;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        extractor = new ProductDataExtractor(mockDriver);
    }

    @Test
    public void testProductDataExtractorInitialization() {
        assertNotNull(extractor);
    }

    @Test
    public void testProductDataExtractorWithNullDriver() {
        assertThrows(IllegalArgumentException.class, () -> new ProductDataExtractor(null));
    }

    @Test
    public void testExtractProductDataWithEmptyList() {
        List<WebElement> emptyList = new ArrayList<>();
        
        when(mockDriver.findElements(any(By.class))).thenReturn(emptyList);
        
        List<Product> products = extractor.extractProductData();
        assertTrue(products.isEmpty());
    }

    @Test
    public void testExtractProductDataWithValidElements() {
        List<WebElement> names = createMockElements(new String[]{"Product 1"});
        List<WebElement> prices = createMockElements(new String[]{"₹100"});
        List<WebElement> currentPrices = createMockElements(new String[]{"₹80"});
        List<WebElement> discounts = createMockElements(new String[]{"20%"});

        when(mockDriver.findElements(any(By.class)))
                .thenReturn(names)
                .thenReturn(prices)
                .thenReturn(currentPrices)
                .thenReturn(discounts);

        List<Product> products = extractor.extractProductData();
        assertFalse(products.isEmpty());
    }

    @Test
    public void testGetProductCount() {
        List<WebElement> elements = createMockElements(new String[]{"P1", "P2", "P3"});
        when(mockDriver.findElements(any(By.class))).thenReturn(elements);
        
        int count = extractor.getProductCount();
        assertEquals(count, 3);
    }

    @Test
    public void testGetProductCountWithEmptyList() {
        List<WebElement> emptyList = new ArrayList<>();
        when(mockDriver.findElements(any(By.class))).thenReturn(emptyList);
        
        int count = extractor.getProductCount();
        assertEquals(count, 0);
    }

    @Test
    public void testExtractProductByIndex() {
        List<WebElement> names = createMockElements(new String[]{"Product 1"});
        List<WebElement> prices = createMockElements(new String[]{"₹100"});
        List<WebElement> currentPrices = createMockElements(new String[]{"₹80"});
        List<WebElement> discounts = createMockElements(new String[]{"20%"});

        when(mockDriver.findElements(any(By.class)))
                .thenReturn(names)
                .thenReturn(prices)
                .thenReturn(currentPrices)
                .thenReturn(discounts);

        Product product = extractor.extractProductByIndex(0);
        assertNotNull(product);
    }

    @Test
    public void testExtractProductByInvalidIndex() {
        List<WebElement> emptyList = new ArrayList<>();
        when(mockDriver.findElements(any(By.class))).thenReturn(emptyList);
        
        Product product = extractor.extractProductByIndex(0);
        assertNull(product);
    }

    /**
     * Helper method to create mock WebElements with text
     */
    private List<WebElement> createMockElements(String[] texts) {
        List<WebElement> elements = new ArrayList<>();
        for (String text : texts) {
            WebElement element = org.mockito.Mockito.mock(WebElement.class);
            when(element.getText()).thenReturn(text);
            elements.add(element);
        }
        return elements;
    }
}