package com.chargepoint.model;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Test suite for Product model class.
 * Tests product data representation and operations.
 */
public class ProductTest {
    private Product product;

    @BeforeMethod
    public void setUp() {
        product = new Product("Test Product", "₹1000", "₹800", "20%");
    }

    @Test
    public void testProductCreationWithConstructor() {
        Product p = new Product("Boat Earphones", "₹3000", "₹2400", "20%");
        assertEquals(p.getName(), "Boat Earphones");
        assertEquals(p.getOriginalPrice(), "₹3000");
        assertEquals(p.getCurrentPrice(), "₹2400");
        assertEquals(p.getDiscount(), "20%");
    }

    @Test
    public void testProductSettersAndGetters() {
        product.setName("New Product");
        product.setOriginalPrice("₹500");
        product.setCurrentPrice("₹400");
        product.setDiscount("20%");

        assertEquals(product.getName(), "New Product");
        assertEquals(product.getOriginalPrice(), "₹500");
        assertEquals(product.getCurrentPrice(), "₹400");
        assertEquals(product.getDiscount(), "20%");
    }

    @Test
    public void testProductEquality() {
        Product p1 = new Product("Product A", "₹100", "₹80", "20%");
        Product p2 = new Product("Product A", "₹100", "₹80", "20%");
        assertEquals(p1, p2);
    }

    @Test
    public void testProductInequality() {
        Product p1 = new Product("Product A", "₹100", "₹80", "20%");
        Product p2 = new Product("Product B", "₹100", "₹80", "20%");
        assertNotEquals(p1, p2);
    }

    @Test
    public void testProductHashCode() {
        Product p1 = new Product("Product A", "₹100", "₹80", "20%");
        Product p2 = new Product("Product A", "₹100", "₹80", "20%");
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    public void testProductToString() {
        String result = product.toString();
        assertTrue(result.contains("Test Product"));
        assertTrue(result.contains("₹1000"));
        assertTrue(result.contains("₹800"));
        assertTrue(result.contains("20%"));
    }

    @Test
    public void testProductEmptyConstructor() {
        Product p = new Product();
        assertNull(p.getName());
        assertNull(p.getOriginalPrice());
        assertNull(p.getCurrentPrice());
        assertNull(p.getDiscount());
    }
}