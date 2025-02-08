package com.example.util;

import com.example.exseption.ResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ResourceBundle;

public class TestDataReader {
    private  static final Logger logger = LoggerFactory.getLogger(TestDataReader.class);

    private static final String ENVIRONMENT = System.getenv("ENVIRONMENT") != null ?
        System.getenv("ENVIRONMENT") :
        System.getProperty("environment", "qa");

    public static ResourceBundle resourceBundle ;

    private static ResourceBundle getResourceBundle() {
        if (resourceBundle == null) {
            resourceBundle = ResourceBundle.getBundle(ENVIRONMENT);
        }
        return resourceBundle;
    }
    public static String getTestData(String key) {
        Util.validateNullAndEmptyProperty(key);

        String envValue = System.getenv(key);
        if (envValue != null && !envValue.trim().isEmpty()) {
            return envValue;
        }
        try {
            return getResourceBundle().getString(key);
        } catch (Exception e) {
            logger.error("Failed to retrieve property key: {}", key, e);
            throw new ResourceException("Key: " + key + " does not exist in properties");
        }
    }
}
