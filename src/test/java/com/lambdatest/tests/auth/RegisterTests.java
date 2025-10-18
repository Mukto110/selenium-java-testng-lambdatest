package com.lambdatest.tests.auth;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.data.TestDataGenerator;
import com.lambdatest.framework.pages.account.AccountCreateSuccessPage;
import com.lambdatest.framework.pages.account.LoginPage;
import com.lambdatest.framework.pages.account.MyAccountPage;
import com.lambdatest.framework.pages.account.RegisterPage;
import com.lambdatest.framework.pages.home.HomePage;
import com.lambdatest.framework.pages.newsletter.NewsletterSubscriptionPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegisterTests extends BaseTest {

    private RegisterPage registerPage;
    private String password;

    @BeforeClass(alwaysRun = true)
    public void initTestData() {
        log.info("Initializing test data for Register tests...");
        password = TestDataGenerator.getRandomPassword();
        log.info("✅ Test data initialization completed");
    }

    @BeforeMethod(alwaysRun = true)
    public void navigateToRegisterPage() {
        log.info("Navigating to the Register page before each test...");
        HomePage homePage = new HomePage(driver);

        homePage.navigateToHomePage();
        registerPage = homePage.getHeader().hoverOnMyAccountDropdown().clickRegister();
        assertUtils.assertEquals(registerPage.getRegisterPageHeaderText(), TestData.REGISTER_PAGE_HEADER);
        log.info("✅ Successfully navigated to Register page");
    }

    @Test(description = "TC_Register_000: Validate user can navigate to Register Page", groups = {"auth", "smoke"})
    public void testNavigateToRegisterPage() {
        log.info("Starting test: Validate navigation to Register page...");
        assertUtils.assertEquals(registerPage.getRegisterPageHeaderText(), TestData.REGISTER_PAGE_HEADER);
        assertUtils.assertTrue(registerPage.getCurrentUrl().contains("route=account/register"), "User should be in register page, but current URL: " + registerPage.getCurrentUrl());
        log.info("✅ Register page navigation validated successfully");
    }

    @Test(description = "TC_Register_001: Validate Registering an Account by providing only the Mandatory fields", groups = {"auth", "smoke", "regression"})
    public void testValidRegisterWithMandatoryFields() {
        log.info("Starting test: Validate Registering with mandatory fields only...");
        AccountCreateSuccessPage accountCreateSuccessPage = registerPage.validRegister(TestDataGenerator.getRandomFirstName(), TestDataGenerator.getRandomLastName(), TestDataGenerator.getRandomEmail(), TestDataGenerator.getRandomTelephone(), password, password, false);

        MyAccountPage myAccountPage = accountCreateSuccessPage.clickContinueButton();
        wait.waitForUrlContains("route=account/account");

        assertUtils.assertTrue(myAccountPage.getCurrentUrl().contains("route=account/account"), "User should be in my account page, but current URL: " + myAccountPage.getCurrentUrl());
        assertUtils.assertEquals(myAccountPage.getMyAccountPageHeaderText(), TestData.MY_ACCOUNT_PAGE_HEADER);
        log.info("✅ Account successfully registered with mandatory fields");
    }

    @Test(description = "TC_Register_002: Validate proper error messages display when user doesn't provide any field value", groups = {"auth", "regression"})
    public void testErrorMessagesDisplayOnEmptyFieldSubmit() {
        log.info("Starting test: Validate error messages for empty field submission...");
        registerPage.clickContinueExpectingFailure();
        assertUtils.assertEquals(registerPage.getFirstNameEmptyFieldErrorMessage(), TestData.FIRST_NAME_EMPTY_FIELD_ERROR_MESSAGE);
        assertUtils.assertEquals(registerPage.getLastNameEmptyFieldErrorMessage(), TestData.LAST_NAME_EMPTY_FIELD_ERROR_MESSAGE);
        assertUtils.assertEquals(registerPage.getEmailErrorMessage(), TestData.EMAIL_ERROR_MESSAGE);
        assertUtils.assertEquals(registerPage.getTelephoneEmptyFieldErrorMessage(), TestData.TELEPHONE_EMPTY_FIELD_ERROR_MESSAGE);
        assertUtils.assertEquals(registerPage.getPasswordEmptyFieldErrorMessage(), TestData.PASSWORD_EMPTY_FIELD_ERROR_MESSAGE);
        assertUtils.assertEquals(registerPage.getPrivacyPolicyWarningMessage(), TestData.PRIVACY_POLICY_WARNING_MESSAGE);
        log.info("✅ All expected error messages displayed correctly");
    }

    @Test(description = "TC_Register_003: Validate registering an account when 'Yes' is selected for Newsletter -> Subscribe", groups = {"auth", "regression"})
    public void testValidRegisterWithSubscribeYes() {
        log.info("Starting test: Validate registration with 'Yes' for Newsletter subscription...");
        AccountCreateSuccessPage accountCreateSuccessPage = registerPage.validRegister(TestDataGenerator.getRandomFirstName(), TestDataGenerator.getRandomLastName(), TestDataGenerator.getRandomEmail(), TestDataGenerator.getRandomTelephone(), password, password, true);

        MyAccountPage myAccountPage = accountCreateSuccessPage.clickContinueButton();
        wait.waitForUrlContains("route=account/account");

        assertUtils.assertTrue(myAccountPage.getCurrentUrl().contains("route=account/account"), "User should be in my account page, but current URL: " + myAccountPage.getCurrentUrl());
        NewsletterSubscriptionPage newsletterSubscriptionPage = myAccountPage.clickOnSidebarNewsletterLink();
        assertUtils.assertEquals(newsletterSubscriptionPage.getNewsletterPageHeaderText(), TestData.NEWSLETTER_PAGE_HEADER);
        assertUtils.assertEquals(newsletterSubscriptionPage.getSelectedNewsletterOption(), TestData.NEWSLETTER_YES);
        log.info("✅ Account successfully registered with Newsletter subscription 'Yes'");
    }

    @Test(description = "TC_Register_004: Validate registering an account when 'No' is selected for Newsletter -> Subscribe", groups = {"auth", "regression"})
    public void testValidRegisterWithSubscribeNo() {
        log.info("Starting test: Validate registration with 'No' for Newsletter subscription...");
        AccountCreateSuccessPage accountCreateSuccessPage = registerPage.validRegister(TestDataGenerator.getRandomFirstName(), TestDataGenerator.getRandomLastName(), TestDataGenerator.getRandomEmail(), TestDataGenerator.getRandomTelephone(), password, password, false);

        MyAccountPage myAccountPage = accountCreateSuccessPage.clickContinueButton();
        wait.waitForUrlContains("route=account/account");

        assertUtils.assertTrue(myAccountPage.getCurrentUrl().contains("route=account/account"), "User should be in my account page, but current URL: " + myAccountPage.getCurrentUrl());
        NewsletterSubscriptionPage newsletterSubscriptionPage = myAccountPage.clickOnSidebarNewsletterLink();
        assertUtils.assertEquals(newsletterSubscriptionPage.getNewsletterPageHeaderText(), TestData.NEWSLETTER_PAGE_HEADER);
        assertUtils.assertEquals(newsletterSubscriptionPage.getSelectedNewsletterOption(), TestData.NEWSLETTER_NO);
        log.info("✅ Account successfully registered with Newsletter subscription 'No'");
    }

    @Test(description = "TC_Register_006: Validate Registering an Account with mismatched 'Password' and 'Password Confirm' fields", groups = {"auth", "regression"})
    public void testRegisterWithMismatchedPasswords() {
        log.info("Starting test: Validate error handling for mismatched passwords...");
        registerPage.invalidRegister(TestDataGenerator.getRandomFirstName(), TestDataGenerator.getRandomLastName(), TestDataGenerator.getRandomEmail(), TestDataGenerator.getRandomTelephone(), TestDataGenerator.getRandomPassword(), TestDataGenerator.getRandomPassword());

        assertUtils.assertEquals(registerPage.getPasswordNoMatchErrorMessageText(), TestData.PASSWORD_NOT_MATCH_ERROR_MESSAGE);
        log.info("✅ Proper error message displayed for mismatched passwords");
    }

    @Test(description = "TC_Register_007: Validate Registering an Account with existing email address", groups = {"auth", "regression"})
    public void testRegisterWithExistingAccountDetails() {
        log.info("Starting test: Validate error handling for existing email registration...");
        registerPage.invalidRegister(TestDataGenerator.getRandomFirstName(), TestDataGenerator.getRandomLastName(), TestData.VALID_EMAIL, TestDataGenerator.getRandomTelephone(), TestDataGenerator.getRandomPassword(), TestDataGenerator.getRandomPassword());

        assertUtils.assertEquals(registerPage.getEmailAlreadyRegisteredErrorText(), TestData.EMAIL_ALREADY_REGISTERED_MESSAGE);
        log.info("✅ Proper error message displayed for existing email");
    }

    @Test(description = "TC_Register_008: Validate Registering an Account with invalid email format", groups = {"auth", "regression"})
    public void testRegisterWithInvalidEmailFormat() {
        log.info("Starting test: Validate error message for invalid email format...");
        registerPage.invalidRegister(TestDataGenerator.getRandomFirstName(), TestDataGenerator.getRandomLastName(), TestData.INVALID_EMAIL_FORMAT, TestDataGenerator.getRandomTelephone(), password, password);

        assertUtils.assertEquals(registerPage.getEmailErrorMessage(), TestData.EMAIL_ERROR_MESSAGE);
        log.info("✅ Proper error message displayed for invalid email format");
    }

    @Test(description = "TC_Register_011: Validate all Register Account page fields have proper placeholder texts", groups = {"auth", "ui"})
    public void testRegisterFieldPlaceholders() {
        log.info("Starting test: Validate placeholder texts on Register page...");
        assertUtils.softAssertEquals(registerPage.getFirstNamePlaceholderText(), "First Name");
        assertUtils.softAssertEquals(registerPage.getLastNamePlaceholderText(), "Last Name");
        assertUtils.softAssertEquals(registerPage.getEmailPlaceholderText(), "E-Mail");
        assertUtils.softAssertEquals(registerPage.getTelephonePlaceholderText(), "Telephone");
        assertUtils.softAssertEquals(registerPage.getPasswordPlaceholderText(), "Password");
        assertUtils.softAssertEquals(registerPage.getConfirmPasswordPlaceholderText(), "Password Confirm");
        assertUtils.assertAll();
        log.info("✅ All placeholder texts validated successfully");
    }

    @Test(description = "TC_Register_017: Validate Registering an Account without selecting the 'Privacy Policy' checkbox", groups = {"auth", "regression"})
    public void testRegisterWithoutCheckPrivacyCheckbox() {
        log.info("Starting test: Validate behavior when Privacy Policy checkbox is not selected...");
        registerPage.registerWithoutPrivacyCheckbox(TestDataGenerator.getRandomFirstName(), TestDataGenerator.getRandomLastName(), TestDataGenerator.getRandomEmail(), TestDataGenerator.getRandomTelephone(), password, password);

        assertUtils.assertEquals(registerPage.getPrivacyPolicyWarningMessage(), TestData.PRIVACY_POLICY_WARNING_MESSAGE);
        log.info("Proper warning message displayed when Privacy Policy checkbox not selected ✅");
    }

    @Test(description = "TC_Register_018: Validate navigating to other pages via links on the Register Account page", groups = {"auth", "regression"})
    public void testNavigateToLinksInRegisterPage() {
        log.info("Starting test: Validate link navigation from Register page...");
        LoginPage loginPage = registerPage.clickOnLoginPageLink();
        assertUtils.assertEquals(loginPage.getLoginFormHeaderText(), TestData.LOGIN_PAGE_HEADER);

        loginPage.navigateBack();
        registerPage.clickOnPrivacyPolicyLink();
        assertUtils.assertEquals(registerPage.getPrivacyPolicyModalHeaderText(), "Privacy Policy");
        log.info("✅ Navigation to links from Register page validated successfully");
    }

    @Test(description = "TC_Register_019: Validate Registering an Account by filling 'Password' field but not 'Password Confirm'", groups = {"auth", "regression"})
    public void testRegisterAccountByNotFillingConfirmPassword() {
        log.info("Starting test: Validate error when 'Confirm Password' field is left empty...");
        registerPage.invalidRegister(TestDataGenerator.getRandomFirstName(), TestDataGenerator.getRandomLastName(), TestDataGenerator.getRandomEmail(), TestDataGenerator.getRandomTelephone(), password, "");

        assertUtils.assertEquals(registerPage.getPasswordNoMatchErrorMessageText(), TestData.PASSWORD_NOT_MATCH_ERROR_MESSAGE);
        log.info("✅ Proper error displayed for missing Confirm Password field");
    }
}