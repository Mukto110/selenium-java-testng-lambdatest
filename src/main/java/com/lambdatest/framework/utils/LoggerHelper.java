package com.lambdatest.framework.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerHelper {

    private LoggerHelper() {
        // Prevent instantiation -> Don't need to make objects, just use the static methods from here
    }

    public static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }
}
