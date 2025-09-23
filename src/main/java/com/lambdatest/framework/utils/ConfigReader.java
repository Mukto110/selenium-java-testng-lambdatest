package com.lambdatest.framework.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    private static final String CONFIG_PATH = "src/main/resources/config.properties";

    // A static block runs only once when the class is first used. This means the properties file is loaded into memory one time → faster access later.
    static {
        loadProperties();
    }

    private static void loadProperties() {
        try {
            properties = new Properties();
            FileInputStream file = new FileInputStream(CONFIG_PATH);
            properties.load(file); // Loads all key=value pairs into a Properties object (like a map/dictionary).
            file.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config properties: " + e.getMessage());
        }
    }

    // Ask for a property by key e.g., getProperty("browser"). If the key exists → returns its value (e.g., "chrome")
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property not found: " + key);
        }
        return value;
    }

    // Get with default value. If the key exists, return the real value. If the key doesn’t exist, return the default I gave.
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
        // Example: getProperty("timeout", "30") → returns 30 if timeout isn’t found in the file.
    }
}
