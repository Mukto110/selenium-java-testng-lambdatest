package com.lambdatest.tests.auth;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.data.TestDataGenerator;
import com.lambdatest.framework.pages.*;
import com.lambdatest.framework.utils.ConfigReader;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    private HomePage homePage;
    private LoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void initPageObjects() {
        homePage = new HomePage(driver);
    }

    @BeforeMethod(alwaysRun = true)
    public void navigateToLoginPage() {
        // Step 1 -> Navigate to the homepage
        homePage.navigateToHomePage();
        // Step 2 -> Hover on 'My Account' dropdown and click 'Login'
        loginPage = homePage.hoverOnMyAccountDropdown().clickLogin();
    }

    @Test(description = "TC_Login_000: Validate user can navigate to Login Page", groups = {"smoke"})
    public void testNavigateToLoginPage() {
        // Assertion (If the user landed to the login page successfully or not)
        assertUtils.assertEquals(loginPage.getLoginFormHeaderText(), TestData.LOGIN_PAGE_HEADER);
        assertUtils.assertTrue(loginPage.getCurrentUrl().contains("route=account/login"), "User is in the login page'");
    }


    @Test(description = "TC_Login_001: Validate Login with valid credentials", groups = {"smoke", "regression"})
    public void testLoginWithValidCredentials() {
        // Step 3 -> Enter valid credentials to the email and password field and click on login button
        MyAccountPage myAccountPage = loginPage.loginAsValidUser(TestData.VALID_EMAIL, TestData.VALID_PASSWORD);
        // Assertion (Expected Result)
        assertUtils.assertEquals(myAccountPage.getMyAccountPageHeaderText(), TestData.MY_ACCOUNT_PAGE_HEADER);
    }

    @Test(description = "TC_Login_002: Validate Login with invalid credentials (Invalid email and password)", groups = {"regression", "negative"})
    public void testLoginWithInvalidCredentials() {
        loginPage.loginAsInvalidUser(TestDataGenerator.getRandomEmail(), TestDataGenerator.getRandomPassword());
        assertUtils.assertEquals(loginPage.getWrongCredentialErrorText(), TestData.WRONG_CREDENTIAL_MESSAGE);
    }

    @Test(description = "TC_Login_003: Validate Login with invalid email address and valid password", groups = {"regression", "negative"})
    public void testLoginWithInvalidEmail() {
        loginPage.loginAsInvalidUser(TestDataGenerator.getRandomEmail(), TestData.VALID_PASSWORD);
        assertUtils.assertEquals(loginPage.getWrongCredentialErrorText(), TestData.WRONG_CREDENTIAL_MESSAGE);
    }

    @Test(description = "TC_Login_004: Validate Login with valid email address and invalid password", groups = {"regression", "negative"})
    public void testLoginWithInvalidPassword() {
        loginPage.loginAsInvalidUser(TestData.VALID_EMAIL, TestDataGenerator.getRandomPassword());
        assertUtils.assertEquals(loginPage.getWrongCredentialErrorText(), TestData.WRONG_CREDENTIAL_MESSAGE);
    }

    @Test(description = "TC_Login_005: Validate Login without providing any credentials (Empty field) -> Known Bug (001)", groups = {"regression", "negative"})
    public void testLoginWithEmptyField() {
        loginPage.loginAsInvalidUser("", "");
        assertUtils.assertEquals(loginPage.getWrongCredentialErrorText(), TestData.WRONG_CREDENTIAL_MESSAGE);
    }

    @Test(description = "TC_Login_006: Validate 'Forgotten Password' link is available in the Login page and is working", groups = {"sanity", "regression"})
    public void testForgetPasswordLink() {
        ForgetPasswordPage forgetPasswordPage = loginPage.clickForgetPasswordLink();
        assertUtils.assertEquals(forgetPasswordPage.getForgetPasswordPageHeaderText(), TestData.FORGET_PASSWORD_PAGE_HEADER);
    }

    @Test(description = "TC_Login_008: Validate placeholders in login fields", groups = {"ui"})
    public void testLoginFieldPlaceholders() {
        assertUtils.assertEquals(loginPage.getEmailFieldPlaceholder(), "E-Mail Address");
        assertUtils.assertEquals(loginPage.getPasswordFieldPlaceholder(), "Password");
    }

    @Test(description = "TC_Login_010: Validate any action on dashboard get user logout after successful logout and click browser back button", groups = {"regression", "security"})
    public void testUserCannotAccessDashboardAfterLogout() {
        MyAccountPage myAccountPage = loginPage.loginAsValidUser(TestData.VALID_EMAIL, TestData.VALID_PASSWORD);
        AccountLogoutPage accountLogoutPage = myAccountPage.clickOnSidebarLogout();
        accountLogoutPage.clickOnContinueButton();
        assertUtils.assertTrue(homePage.getCurrentUrl().contains("route=common/home"), "User is in the homepage");
        driver.navigate().back(); // Will put this somewhere later.
        assertUtils.assertPageTitle(accountLogoutPage.getPageTitle(), "Account Logout");
        accountLogoutPage.clickOnSideBarMyAccountLink();
        assertUtils.assertTrue(loginPage.getCurrentUrl().contains(ConfigReader.getProperty("loginUrl")), "User is in the login page after clicking on 'My Account' dashboard/sidebar link");
    }

    @Test(description = "TC_Login_020: Validate user is able to navigate to different pages from Login page", groups = {"regression"})
    public void testDifferentPageNavigationFromLoginPage() {
        RegisterPage registerPage = loginPage.clickContinueButtonUnderNewCustomer();
        assertUtils.assertEquals(registerPage.getRegisterPageHeaderText(), TestData.REGISTER_PAGE_HEADER);
        driver.navigate().back();
        ForgetPasswordPage forgetPasswordPage = loginPage.clickSidebarForgetPasswordLink();
        assertUtils.assertEquals(forgetPasswordPage.getForgetPasswordPageHeaderText(), TestData.FORGET_PASSWORD_PAGE_HEADER);
    }
}