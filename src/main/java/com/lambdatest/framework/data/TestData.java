package com.lambdatest.framework.data;

import com.lambdatest.framework.utils.PropertyReader;

public class TestData {

    private static final PropertyReader reader = new PropertyReader("testdata.properties");

    // Valid credentials
    public static final String VALID_EMAIL = reader.getProperty("validEmail");
    public static final String VALID_PASSWORD = reader.getProperty("validPassword");

    // Invalid credentials
    public static final String INVALID_EMAIL = reader.getProperty("invalidEmail");
    public static final String INVALID_PASSWORD = reader.getProperty("invalidPassword");

    // Page titles
    public static final String LOGIN_PAGE_TITLE = reader.getProperty("loginPageTitle");
    public static final String MY_ACCOUNT_PAGE_TITLE = reader.getProperty("myAccountPageTitle");
    public static final String FORGET_PASSWORD_PAGE_TITLE = reader.getProperty("forgetPasswordPageTitle");

    // Error messages
    public static final String WRONG_CREDENTIAL_MESSAGE = reader.getProperty("wrongCredentialMessage");
}
