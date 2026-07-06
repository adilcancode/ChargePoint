package com.chargepoint.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * WebDriver manager for initialization and cleanup.
 * Handles driver lifecycle management.
 */
public class DriverManager {
    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);
    private static final int DEFAULT_TIMEOUT = 10;
    private WebDriver driver;

    /**
     * Initialize the WebDriver
     */
    public void initializeDriver() {
        try {
            driver = new FirefoxDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, SECONDS);
            logger.info("WebDriver initialized successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize WebDriver", e);
            throw new RuntimeException("Failed to initialize WebDriver", e);
        }
    }

    /**
     * Get the current WebDriver instance
     * @return the WebDriver instance
     */
    public WebDriver getDriver() {
        if (driver == null) {
            throw new RuntimeException("WebDriver not initialized. Call initializeDriver() first.");
        }
        return driver;
    }

    /**
     * Set a custom timeout for implicit waits
     * @param timeoutSeconds the timeout in seconds
     */
    public void setImplicitTimeout(int timeoutSeconds) {
        if (driver != null) {
            driver.manage().timeouts().implicitlyWait(timeoutSeconds, SECONDS);
            logger.info("Implicit timeout set to: " + timeoutSeconds + " seconds");
        }
    }

    /**
     * Quit the WebDriver and close all windows
     */
    public void quitDriver() {
        try {
            if (driver != null) {
                driver.quit();
                driver = null;
                logger.info("WebDriver closed successfully");
            }
        } catch (Exception e) {
            logger.error("Error while closing WebDriver", e);
        }
    }

    /**
     * Check if driver is initialized
     * @return true if driver is initialized, false otherwise
     */
    public boolean isDriverInitialized() {
        return driver != null;
    }
}