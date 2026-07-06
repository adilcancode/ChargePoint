package com.chargepoint.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

/**
 * Loads configuration from YAML files.
 * Supports both classpath resources and external file paths.
 */
public class ConfigurationLoader {
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationLoader.class);
    private static final String CONFIG_DIR = "config";
    private static final String DEFAULT_CONFIG_PATH = "src/main/resources/config";
    
    private final Yaml yaml;
    
    public ConfigurationLoader() {
        this.yaml = new Yaml();
    }
    
    /**
     * Load configuration from a YAML file in the classpath resources.
     * @param filename the name of the configuration file (e.g., "selectors-flipkart.yaml")
     * @return ConfigurationModel representing the loaded configuration
     * @throws ConfigurationException if file cannot be loaded or parsed
     */
    public ConfigurationModel loadFromClasspath(String filename) {
        logger.info("Loading configuration from classpath: " + filename);
        
        try {
            String resourcePath = CONFIG_DIR + "/" + filename;
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);
            
            if (inputStream == null) {
                throw new ConfigurationException("Configuration file not found on classpath: " + resourcePath);
            }
            
            Map<String, Object> data = yaml.load(inputStream);
            ConfigurationModel config = new ConfigurationModel(data);
            logger.info("Successfully loaded configuration: " + config.getPlatform() + " v" + config.getVersion());
            
            return config;
        } catch (Exception e) {
            logger.error("Error loading configuration from classpath", e);
            throw new ConfigurationException("Failed to load configuration: " + filename, e);
        }
    }
    
    /**
     * Load configuration from an external file path.
     * @param filePath the path to the configuration file
     * @return ConfigurationModel representing the loaded configuration
     * @throws ConfigurationException if file cannot be loaded or parsed
     */
    public ConfigurationModel loadFromFile(String filePath) {
        logger.info("Loading configuration from file: " + filePath);
        
        try {
            Path path = Paths.get(filePath);
            
            if (!Files.exists(path)) {
                throw new ConfigurationException("Configuration file not found: " + filePath);
            }
            
            InputStream inputStream = Files.newInputStream(path);
            Map<String, Object> data = yaml.load(inputStream);
            ConfigurationModel config = new ConfigurationModel(data);
            logger.info("Successfully loaded configuration: " + config.getPlatform() + " v" + config.getVersion());
            
            return config;
        } catch (Exception e) {
            logger.error("Error loading configuration from file", e);
            throw new ConfigurationException("Failed to load configuration from file: " + filePath, e);
        }
    }
    
    /**
     * Load configuration with fallback mechanism.
     * Attempts to load from external file first, then falls back to classpath.
     * @param externalPath the external file path to try first
     * @param classpathFilename the classpath resource filename as fallback
     * @return ConfigurationModel representing the loaded configuration
     */
    public ConfigurationModel loadWithFallback(String externalPath, String classpathFilename) {
        try {
            // Try external path first
            return loadFromFile(externalPath);
        } catch (ConfigurationException e) {
            logger.warn("Failed to load from external path, attempting classpath fallback: " + classpathFilename);
            try {
                return loadFromClasspath(classpathFilename);
            } catch (ConfigurationException fallbackException) {
                logger.error("Both external and classpath loading failed", fallbackException);
                throw new ConfigurationException(
                    "Failed to load configuration from both " + externalPath + " and classpath: " + classpathFilename,
                    fallbackException
                );
            }
        }
    }
    
    /**
     * Validate loaded configuration.
     * @param config the ConfigurationModel to validate
     * @return true if valid, false otherwise
     */
    public boolean validateConfiguration(ConfigurationModel config) {
        try {
            Objects.requireNonNull(config, "Configuration cannot be null");
            Objects.requireNonNull(config.getPlatform(), "Platform must be specified");
            Objects.requireNonNull(config.getVersion(), "Version must be specified");
            
            logger.info("Configuration validation passed for platform: " + config.getPlatform());
            return true;
        } catch (NullPointerException e) {
            logger.error("Configuration validation failed", e);
            return false;
        }
    }
}
