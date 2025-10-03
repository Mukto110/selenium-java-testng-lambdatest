package com.lambdatest.framework.data;

import com.lambdatest.framework.utils.PropertyReader;

public class TestData {

    private static final PropertyReader reader = new PropertyReader("testdata.properties");

    // Valid credentials
    public static final String VALID_EMAIL = reader.getProperty("validEmail");
    public static final String VALID_PASSWORD = reader.getProperty("validPassword");

    // Page titles
    public static final String LOGIN_PAGE_HEADER = reader.getProperty("loginPageHeader");
    public static final String REGISTER_PAGE_HEADER = reader.getProperty("registerPageHeader");
    public static final String MY_ACCOUNT_PAGE_HEADER = reader.getProperty("myAccountPageHeader");
    public static final String FORGET_PASSWORD_PAGE_HEADER = reader.getProperty("forgetPasswordPageHeader");
    public static final String CHANGE_PASSWORD_PAGE_HEADER = reader.getProperty("changePasswordPageHeader");

    // Success message
    public static final String PASSWORD_UPDATE_SUCCESS_MESSAGE = reader.getProperty("passwordUpdateSuccessMessage");

    // Error messages
    public static final String WRONG_CREDENTIAL_MESSAGE = reader.getProperty("wrongCredentialMessage");
}
