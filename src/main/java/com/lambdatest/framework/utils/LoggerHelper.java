package com.lambdatest.framework.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerHelper {

    private LoggerHelper() {
        // Prevent instantiation → Since it's an utility class, so that nobody can create instances of it
    }

    public static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }
}
