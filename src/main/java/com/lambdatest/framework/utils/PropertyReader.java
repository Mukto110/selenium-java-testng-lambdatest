package com.lambdatest.framework.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
    private final Properties properties = new Properties();

    public PropertyReader(String fileName) {
        try (FileInputStream fis = new FileInputStream("src/main/resources/" + fileName)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Could not load properties file: " + fileName, e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}