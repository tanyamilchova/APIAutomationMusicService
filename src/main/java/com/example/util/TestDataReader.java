package com.example.util;

import com.example.exseption.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ResourceBundle;

public class TestDataReader {
    private  static final Logger logger = LoggerFactory.getLogger(TestDataReader.class);
    static {
        System.setProperty("environment", "qa");
    }
    public static ResourceBundle resourceBundle=ResourceBundle.getBundle(System.getProperty("environment"));

    public static String getTestData(String key){
       Util.validateNullAndEmptyProperty(key);
       String envValue = System.getenv(key);
       if (envValue != null && !envValue.trim().isEmpty()) {
           return envValue;
       }

       try {
           return resourceBundle.getString(key);
       } catch (Exception e) {
            logger.error("Error in retrieving the resource from property.");
            throw new NotFoundException("Key: " + key + " does not exist in properties");
        }
    }
}
