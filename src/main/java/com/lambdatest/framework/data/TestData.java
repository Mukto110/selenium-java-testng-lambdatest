package com.lambdatest.framework.data;

import com.lambdatest.framework.utils.PropertyReader;

public class TestData {

    private static final PropertyReader reader = new PropertyReader("testdata.properties");

    // Valid credentials
    public static final String VALID_EMAIL = reader.getProperty("validEmail");
    public static final String VALID_PASSWORD = reader.getProperty("validPassword");
    public static final String INVALID_EMAIL_FORMAT = reader.getProperty("invalidEmailFormat");

    // Page headers
    public static final String LOGIN_PAGE_HEADER = reader.getProperty("loginPageHeader");
    public static final String REGISTER_PAGE_HEADER = reader.getProperty("registerPageHeader");
    public static final String MY_ACCOUNT_PAGE_HEADER = reader.getProperty("myAccountPageHeader");
    public static final String FORGET_PASSWORD_PAGE_HEADER = reader.getProperty("forgetPasswordPageHeader");
    public static final String CHANGE_PASSWORD_PAGE_HEADER = reader.getProperty("changePasswordPageHeader");
    public static final String ACCOUNT_SUCCESS_HEADER = reader.getProperty("accountSuccessPageHeader");
    public static final String NEWSLETTER_PAGE_HEADER = reader.getProperty("newsletterPageHeader");
    public static final String ACCOUNT_LOGOUT_PAGE_HEADER = reader.getProperty("accountLogoutPageHeader");

    // Success message
    public static final String PASSWORD_UPDATE_SUCCESS_MESSAGE = reader.getProperty("passwordUpdateSuccessMessage");

    // Error messages
    public static final String WRONG_CREDENTIAL_MESSAGE = reader.getProperty("wrongCredentialMessage");
    public static final String FIRST_NAME_EMPTY_FIELD_ERROR_MESSAGE = reader.getProperty("firstNameEmptyFieldErrorMessage");
    public static final String LAST_NAME_EMPTY_FIELD_ERROR_MESSAGE = reader.getProperty("lastNameEmptyFieldErrorMessage");
    public static final String EMAIL_ERROR_MESSAGE = reader.getProperty("emailEmptyFieldErrorMessage");
    public static final String TELEPHONE_EMPTY_FIELD_ERROR_MESSAGE = reader.getProperty("telephoneEmptyFieldErrorMessage");
    public static final String PASSWORD_EMPTY_FIELD_ERROR_MESSAGE = reader.getProperty("passwordEmptyFieldErrorMessage");
    public static final String PRIVACY_POLICY_WARNING_MESSAGE = reader.getProperty("privacyPolicyWarningMessage");
    public static final String PASSWORD_NOT_MATCH_ERROR_MESSAGE = reader.getProperty("passwordNotMatchErrorMessage");
    public static final String EMAIL_ALREADY_REGISTERED_MESSAGE = reader.getProperty("emailAlreadyRegisteredMessage");

    // Newsletter options
    public static final String NEWSLETTER_YES = "Yes";
    public static final String NEWSLETTER_NO = "No";
}
