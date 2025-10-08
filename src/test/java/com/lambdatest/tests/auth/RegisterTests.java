package com.lambdatest.tests.auth;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.data.TestDataGenerator;
import com.lambdatest.framework.pages.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegisterTests extends BaseTest {

    private RegisterPage registerPage;
    private String password;

    @BeforeClass(alwaysRun = true)
    public void initTestData() {
        password = TestDataGenerator.getRandomPassword();
    }

    @BeforeMethod(alwaysRun = true)
    public void navigateToRegisterPage() {
        log.info("🔁 Navigating to the Register page before each test...");
        HomePage homePage = new HomePage(driver);

        // Step 1: Navigate to home page
        homePage.navigateToHomePage();

        // Step 2: Hover and click on "Register" from My Account dropdown
        registerPage = homePage.getNavbar().hoverOnMyAccountDropdown().clickRegister();

        // Step 3: Verify Register page loaded
        assertUtils.assertEquals(registerPage.getRegisterPageHeaderText(), TestData.REGISTER_PAGE_HEADER);
    }

    @Test(description = "TC_Register_000: Validate user can navigate to Register Page", groups = {"smoke"})
    public void testNavigateToRegisterPage() {
        assertUtils.assertEquals(registerPage.getRegisterPageHeaderText(), TestData.REGISTER_PAGE_HEADER);
        assertUtils.assertTrue(registerPage.getCurrentUrl().contains("route=account/register"), "User should be in register page, but current URL: " + registerPage.getCurrentUrl());
    }

    @Test(description = "TC_Register_001: Validate Registering an Account by providing only the Mandatory fields", groups = {"smoke", "regression"})
    public void testValidRegisterWithMandatoryFields() {
        AccountCreateSuccessPage accountCreateSuccessPage = registerPage.validRegister(TestDataGenerator.getRandomFirstName(), TestDataGenerator.getRandomLastName(), TestDataGenerator.getRandomEmail(), TestDataGenerator.getRandomTelephone(), password, password, false);

        MyAccountPage myAccountPage = accountCreateSuccessPage.clickContinueButton();
        wait.waitForUrlContains("route=account/account");

        assertUtils.assertTrue(myAccountPage.getCurrentUrl().contains("route=account/account"), "User should be in my account page, but current URL: " + myAccountPage.getCurrentUrl());
        assertUtils.assertEquals(myAccountPage.getMyAccountPageHeaderText(), TestData.MY_ACCOUNT_PAGE_HEADER);
    }

    @Test(description = "TC_Register_002: Validate proper error messages display when user doesn't provide any field value", groups = {"regression"})
    public void testErrorMessagesDisplayOnEmptyFieldSubmit() {
        registerPage.clickContinueExpectingFailure();
        assertUtils.assertEquals(registerPage.getFirstNameEmptyFieldErrorMessage(), TestData.FIRST_NAME_EMPTY_FIELD_ERROR_MESSAGE);
        assertUtils.assertEquals(registerPage.getLastNameEmptyFieldErrorMessage(), TestData.LAST_NAME_EMPTY_FIELD_ERROR_MESSAGE);
        assertUtils.assertEquals(registerPage.getEmailErrorMessage(), TestData.EMAIL_ERROR_MESSAGE);
        assertUtils.assertEquals(registerPage.getTelephoneEmptyFieldErrorMessage(), TestData.TELEPHONE_EMPTY_FIELD_ERROR_MESSAGE);
        assertUtils.assertEquals(registerPage.getPasswordEmptyFieldErrorMessage(), TestData.PASSWORD_EMPTY_FIELD_ERROR_MESSAGE);
        assertUtils.assertEquals(registerPage.getPrivacyPolicyWarningMessage(), TestData.PRIVACY_POLICY_WARNING_MESSAGE);
    }

    @Test(description = "TC_Register_003: Validate registering an account when 'Yes' is selected for Newsletter -> Subscribe", groups = {"regression"})
    public void testValidRegisterWithSubscribeYes() {
        AccountCreateSuccessPage accountCreateSuccessPage = registerPage.validRegister(TestDataGenerator.getRandomFirstName(), TestDataGenerator.getRandomLastName(), TestDataGenerator.getRandomEmail(), TestDataGenerator.getRandomTelephone(), password, password, true);

        MyAccountPage myAccountPage = accountCreateSuccessPage.clickContinueButton();
        wait.waitForUrlContains("route=account/account");

        assertUtils.assertTrue(myAccountPage.getCurrentUrl().contains("route=account/account"), "User should be in my account page, but current URL: " + myAccountPage.getCurrentUrl());
        NewsletterSubscriptionPage newsletterSubscriptionPage = myAccountPage.clickOnSidebarNewsletterLink();
        assertUtils.assertEquals(newsletterSubscriptionPage.getNewsletterPageHeaderText(), TestData.NEWSLETTER_PAGE_HEADER);
        assertUtils.assertEquals(newsletterSubscriptionPage.getSelectedNewsletterOption(), TestData.NEWSLETTER_YES);
    }

    @Test(description = "TC_Register_004: Validate registering an account when 'No' is selected for Newsletter -> Subscribe", groups = {"regression"})
    public void testValidRegisterWithSubscribeNo() {
        AccountCreateSuccessPage accountCreateSuccessPage = registerPage.validRegister(TestDataGenerator.getRandomFirstName(), TestDataGenerator.getRandomLastName(), TestDataGenerator.getRandomEmail(), TestDataGenerator.getRandomTelephone(), password, password, false);

        MyAccountPage myAccountPage = accountCreateSuccessPage.clickContinueButton();
        wait.waitForUrlContains("route=account/account");

        assertUtils.assertTrue(myAccountPage.getCurrentUrl().contains("route=account/account"), "User should be in my account page, but current URL: " + myAccountPage.getCurrentUrl());
        NewsletterSubscriptionPage newsletterSubscriptionPage = myAccountPage.clickOnSidebarNewsletterLink();
        assertUtils.assertEquals(newsletterSubscriptionPage.getNewsletterPageHeaderText(), TestData.NEWSLETTER_PAGE_HEADER);
        assertUtils.assertEquals(newsletterSubscriptionPage.getSelectedNewsletterOption(), TestData.NEWSLETTER_NO);
    }

    @Test(description = "TC_Register_006: Validate Registering an Account with mismatched 'Password' and 'Password Confirm' fields", groups = {"regression"})
    public void testRegisterWithMismatchedPasswords() {
        registerPage.invalidRegister(TestDataGenerator.getRandomFirstName(), TestDataGenerator.getRandomLastName(), TestDataGenerator.getRandomEmail(), TestDataGenerator.getRandomTelephone(), TestDataGenerator.getRandomPassword(), TestDataGenerator.getRandomPassword());

        assertUtils.assertEquals(registerPage.getPasswordNoMatchErrorMessageText(), TestData.PASSWORD_NOT_MATCH_ERROR_MESSAGE);
    }

    @Test(description = "TC_Register_007: Validate Registering an Account with existing email address", groups = {"regression"})
    public void testRegisterWithExistingAccountDetails() {
        registerPage.invalidRegister(TestDataGenerator.getRandomFirstName(), TestDataGenerator.getRandomLastName(), TestData.VALID_EMAIL, TestDataGenerator.getRandomTelephone(), TestDataGenerator.getRandomPassword(), TestDataGenerator.getRandomPassword());

        assertUtils.assertEquals(registerPage.getEmailAlreadyRegisteredErrorText(), TestData.EMAIL_ALREADY_REGISTERED_MESSAGE);
    }

    @Test(description = "TC_Register_008: Validate Registering an Account with invalid email format", groups = {"regression"})
    public void testRegisterWithInvalidEmailFormat() {
        registerPage.invalidRegister(TestDataGenerator.getRandomFirstName(), TestDataGenerator.getRandomLastName(), TestData.INVALID_EMAIL_FORMAT, TestDataGenerator.getRandomTelephone(), password, password);

        assertUtils.assertEquals(registerPage.getEmailErrorMessage(), TestData.EMAIL_ERROR_MESSAGE);
    }

    @Test(description = "TC_Register_011: Validate all Register Account page fields have proper placeholder texts", groups = {"ui"})
    public void testRegisterFieldPlaceholders() {
        assertUtils.softAssertEquals(registerPage.getFirstNamePlaceholderText(), "First Name");
        assertUtils.softAssertEquals(registerPage.getLastNamePlaceholderText(), "Last Name");
        assertUtils.softAssertEquals(registerPage.getEmailPlaceholderText(), "E-Mail");
        assertUtils.softAssertEquals(registerPage.getTelephonePlaceholderText(), "Telephone");
        assertUtils.softAssertEquals(registerPage.getPasswordPlaceholderText(), "Password");
        assertUtils.softAssertEquals(registerPage.getConfirmPasswordPlaceholderText(), "Password Confirm");
        assertUtils.assertAll();
        log.info("✅ Completed placeholder validation test successfully!");
    }

    @Test(description = "TC_Register_017: Validate Registering an Account without selecting the 'Privacy Policy' checkbox", groups = {"regression"})
    public void testRegisterWithoutCheckPrivacyCheckbox() {
        registerPage.registerWithoutPrivacyCheckbox(TestDataGenerator.getRandomFirstName(), TestDataGenerator.getRandomLastName(), TestDataGenerator.getRandomEmail(), TestDataGenerator.getRandomTelephone(), password, password);

        assertUtils.assertEquals(registerPage.getPrivacyPolicyWarningMessage(), TestData.PRIVACY_POLICY_WARNING_MESSAGE);
    }

    @Test(description = "TC_Register_018: Validate navigating to other pages via links on the Register Account page", groups = {"regression"})
    public void testNavigateToLinksInRegisterPage() {
        LoginPage loginPage = registerPage.clickOnLoginPageLink();
        assertUtils.assertEquals(loginPage.getLoginFormHeaderText(), TestData.LOGIN_PAGE_HEADER);

        loginPage.navigateBack();

        registerPage.clickOnPrivacyPolicyLink();
        assertUtils.assertEquals(registerPage.getPrivacyPolicyModalHeaderText(), "Privacy Policy");
    }

    @Test(description = "TC_Register_019: Validate Registering an Account by filling 'Password' field but not 'Password Confirm'", groups = {"regression"})
    public void testRegisterAccountByNotFillingConfirmPassword() {
        registerPage.invalidRegister(TestDataGenerator.getRandomFirstName(), TestDataGenerator.getRandomLastName(), TestDataGenerator.getRandomEmail(), TestDataGenerator.getRandomTelephone(), password, "");

        assertUtils.assertEquals(registerPage.getPasswordNoMatchErrorMessageText(), TestData.PASSWORD_NOT_MATCH_ERROR_MESSAGE);
    }
}