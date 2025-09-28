package com.lambdatest.framework.data;

import java.util.UUID;

public class TestDataGenerator {
    public static String getRandomEmail() {
        return "user" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
    }

    public static String getRandomPassword() {
        return "Pass" + UUID.randomUUID().toString().substring(0, 8);
    }

    public static String getRandomString(int length) {
        return UUID.randomUUID().toString().replace("-", "").substring(0, length);
    }
}
