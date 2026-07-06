package com.chargepoint.config;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Test suite for Configuration class.
 * Tests configuration loading and property management.
 */
public class ConfigurationTest {
    private Configuration configuration;

    @BeforeMethod
    public void setUp() {
        configuration = new Configuration();
    }

    @Test
    public void testGetPropertyWithDefaultValue() {
        String value = configuration.getProperty("non.existent.key", "defaultValue");
        assertEquals(value, "defaultValue");
    }

    @Test
    public void testGetProperty() {
        assertNotNull(configuration);
    }

    @Test
    public void testSetAndGetProperty() {
        configuration.setProperty("test.key", "test.value");
        assertEquals(configuration.getProperty("test.key"), "test.value");
    }

    @Test
    public void testGetIntProperty() {
        configuration.setProperty("timeout", "30");
        int timeout = configuration.getIntProperty("timeout", 10);
        assertEquals(timeout, 30);
    }

    @Test
    public void testGetIntPropertyWithDefaultValue() {
        int timeout = configuration.getIntProperty("non.existent.timeout", 15);
        assertEquals(timeout, 15);
    }

    @Test
    public void testGetIntPropertyWithInvalidValue() {
        configuration.setProperty("invalid.int", "not.a.number");
        int value = configuration.getIntProperty("invalid.int", 20);
        assertEquals(value, 20);
    }
}