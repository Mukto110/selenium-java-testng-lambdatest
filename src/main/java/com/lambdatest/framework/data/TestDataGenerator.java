package com.lambdatest.framework.data;

import java.util.Random;
import java.util.UUID;

public class TestDataGenerator {
    private static final String[] FIRST_NAMES = {
            "John", "Jane", "Alex", "Emily", "Michael", "Sarah", "David", "Sophia", "Chris", "Olivia"
    };

    private static final String[] LAST_NAMES = {
            "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Martinez", "Taylor"
    };

    private static final Random RANDOM = new Random();

    public static String getRandomEmail() {
        return "user" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
    }

    public static String getRandomPassword() {
        return "Pass" + UUID.randomUUID().toString().substring(0, 8);
    }

    public static String getRandomString(int length) {
        return UUID.randomUUID().toString().replace("-", "").substring(0, length);
    }

    public static String getRandomFirstName() {
        return FIRST_NAMES[RANDOM.nextInt(FIRST_NAMES.length)];
    }

    public static String getRandomLastName() {
        return LAST_NAMES[RANDOM.nextInt(LAST_NAMES.length)];
    }

    public static String getRandomTelephone() {
        // Generates a 10-digit random number starting with a non-zero digit
        return String.valueOf(1000000000L + (long)(RANDOM.nextDouble() * 9000000000L));
    }
}
