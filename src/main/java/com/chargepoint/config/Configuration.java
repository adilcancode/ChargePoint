package com.chargepoint.config;

import java.io.IOException;
import java.util.Properties;

/**
 * Configuration loader for automation settings.
 * Handles loading and managing configuration properties.
 */
public class Configuration {
    private static final String CONFIG_FILE = "config.properties";
    private Properties properties;

    public Configuration() {
        this.properties = new Properties();
        loadConfiguration();
    }

    /**
     * Load configuration from properties file
     */
    private void loadConfiguration() {
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(CONFIG_FILE));
        } catch (IOException e) {
            System.err.println("Failed to load configuration file: " + CONFIG_FILE);
            e.printStackTrace();
        }
    }

    /**
     * Get a configuration property
     * @param key the property key
     * @param defaultValue the default value if key not found
     * @return the property value or default value
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Get a configuration property
     * @param key the property key
     * @return the property value
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Get integer configuration property
     * @param key the property key
     * @param defaultValue the default value
     * @return the integer value
     */
    public int getIntProperty(String key, int defaultValue) {
        try {
            return Integer.parseInt(getProperty(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Set a configuration property
     * @param key the property key
     * @param value the property value
     */
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }
}