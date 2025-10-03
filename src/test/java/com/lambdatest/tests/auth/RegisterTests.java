package com.lambdatest.tests.auth;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.data.TestDataGenerator;
import com.lambdatest.framework.pages.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegisterTests extends BaseTest {

    private HomePage homePage;
    private RegisterPage registerPage;
    private String password;


    @BeforeMethod(alwaysRun = true)
    public void initPageObjects() {
        homePage = new HomePage(driver);
        password = TestDataGenerator.getRandomPassword();
    }

    @BeforeMethod(alwaysRun = true)
    public void navigateToRegisterPage() {
        homePage.navigateToHomePage();
        registerPage = homePage.hoverOnMyAccountDropdown().clickRegister();
    }

    @Test(description = "TC_Register_000: Validate user can navigate to Register Page", groups = {"smoke"})
    public void testNavigateToRegisterPage() {
        assertUtils.assertEquals(registerPage.getRegisterPageHeaderText(), TestData.REGISTER_PAGE_HEADER);
        assertUtils.assertTrue(registerPage.getCurrentUrl().contains("route=account/register"), "Register page URL contains 'route=account/register'");
    }

    @Test(description = "TC_Register_001: Validate Registering an Account by providing only the Mandatory fields", groups = {"smoke", "regression"})
    public void testValidRegisterWithMandatoryFields() {
        AccountCreateSuccessPage accountCreateSuccessPage = registerPage.validRegister(TestDataGenerator.getRandomFirstName(), TestDataGenerator.getRandomLastName(), TestDataGenerator.getRandomEmail(), TestDataGenerator.getRandomTelephone(), password, password);
        MyAccountPage myAccountPage = accountCreateSuccessPage.clickContinueButton();
        wait.waitForUrlContains("route=account/account");
        assertUtils.assertTrue(myAccountPage.getCurrentUrl().contains("route=account/account"), "User is in the 'My Account' page");
        assertUtils.assertEquals(myAccountPage.getMyAccountPageHeaderText(), TestData.MY_ACCOUNT_PAGE_HEADER);
    }

    @Test(description = "TC_Register_002: Validate proper error messages displays when user don't provide values to any field and try to submit the register form", groups = {"regression"})
    public void testErrorMessagesDisplayOnEmptyFieldSubmit() {
        registerPage.clickContinueExpectingFailure();
        assertUtils.assertEquals(registerPage.getFirstNameEmptyFieldErrorMessage(), TestData.FIRST_NAME_EMPTY_FIELD_ERROR_MESSAGE);
        assertUtils.assertEquals(registerPage.getLastNameEmptyFieldErrorMessage(), TestData.LAST_NAME_EMPTY_FIELD_ERROR_MESSAGE);
        assertUtils.assertEquals(registerPage.getEmailEmptyFieldErrorMessage(), TestData.EMAIL_EMPTY_FIELD_ERROR_MESSAGE);
        assertUtils.assertEquals(registerPage.getTelephoneEmptyFieldErrorMessage(), TestData.TELEPHONE_EMPTY_FIELD_ERROR_MESSAGE);
        assertUtils.assertEquals(registerPage.getPasswordEmptyFieldErrorMessage(), TestData.PASSWORD_EMPTY_FIELD_ERROR_MESSAGE);
        assertUtils.assertEquals(registerPage.getPrivacyPolicyWarningMessage(), TestData.PRIVACY_POLICY_WARNING_MESSAGE);
    }
}
