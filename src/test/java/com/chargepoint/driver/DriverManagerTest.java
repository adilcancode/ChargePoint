package com.chargepoint.driver;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Test suite for DriverManager class.
 * Tests WebDriver initialization and lifecycle management.
 */
public class DriverManagerTest {
    private DriverManager driverManager;

    @BeforeMethod
    public void setUp() {
        driverManager = new DriverManager();
    }

    @AfterMethod
    public void tearDown() {
        if (driverManager != null && driverManager.isDriverInitialized()) {
            driverManager.quitDriver();
        }
    }

    @Test
    public void testDriverNotInitializedByDefault() {
        assertFalse(driverManager.isDriverInitialized());
    }

    @Test
    public void testInitializeDriver() {
        driverManager.initializeDriver();
        assertTrue(driverManager.isDriverInitialized());
        assertNotNull(driverManager.getDriver());
    }

    @Test
    public void testGetDriverBeforeInitialization() {
        assertThrows(RuntimeException.class, () -> driverManager.getDriver());
    }

    @Test
    public void testSetImplicitTimeout() {
        driverManager.initializeDriver();
        driverManager.setImplicitTimeout(20);
        assertTrue(driverManager.isDriverInitialized());
    }

    @Test
    public void testQuitDriver() {
        driverManager.initializeDriver();
        assertTrue(driverManager.isDriverInitialized());
        driverManager.quitDriver();
        assertFalse(driverManager.isDriverInitialized());
    }

    @Test
    public void testQuitDriverWhenNotInitialized() {
        driverManager.quitDriver();
        assertFalse(driverManager.isDriverInitialized());
    }

    @Test
    public void testMultipleInitialization() {
        driverManager.initializeDriver();
        assertTrue(driverManager.isDriverInitialized());
        driverManager.quitDriver();
        
        driverManager.initializeDriver();
        assertTrue(driverManager.isDriverInitialized());
    }
}