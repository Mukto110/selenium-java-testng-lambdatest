package com.lambdatest.tests.auth;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.data.TestDataGenerator;
import com.lambdatest.framework.pages.account.*;
import com.lambdatest.framework.pages.home.HomePage;
import com.lambdatest.framework.utils.ConfigReader;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    private HomePage homePage;
    private LoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void navigateToLoginPage() {
        homePage = new HomePage(driver);
        // Step 1 -> Navigate to the homepage
        homePage.navigateToHomePage();
        // Step 2 -> Click 'Login' from 'My Account' dropdown (hover handled inside Navbar)
        loginPage = homePage.getNavbar().clickLogin();
    }

    @Test(description = "TC_Login_000: Validate user can navigate to Login Page", groups = {"smoke"})
    public void testNavigateToLoginPage() {
        // Assertion (If the user landed to the login page successfully or not)
        assertUtils.assertEquals(loginPage.getLoginFormHeaderText(), TestData.LOGIN_PAGE_HEADER);
        assertUtils.assertTrue(loginPage.getCurrentUrl().contains("route=account/login"), "User should be in login page, but current URL: " + loginPage.getCurrentUrl());
    }

    @Test(description = "TC_Login_001: Validate Login with valid credentials", groups = {"smoke", "regression"})
    public void testLoginWithValidCredentials() {
        MyAccountPage myAccountPage = loginPage.loginAsValidUser(TestData.VALID_EMAIL, TestData.VALID_PASSWORD);
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

    @Test(description = "TC_Login_005: Validate Login without providing any credentials (Known Bug: BUG-001)", groups = {"regression", "negative"})
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
        assertUtils.softAssertEquals(loginPage.getEmailFieldPlaceholder(), "E-Mail Address");
        assertUtils.assertEquals(loginPage.getPasswordFieldPlaceholder(), "Password");
        assertUtils.assertAll();
        log.info("✅ Completed placeholder validation test successfully!");
    }

    @Test(description = "TC_Login_010: Validate any action on dashboard get user logout after successful logout and click browser back button", groups = {"regression", "security"})
    public void testUserCannotAccessDashboardAfterLogout() {
        MyAccountPage myAccountPage = loginPage.loginAsValidUser(TestData.VALID_EMAIL, TestData.VALID_PASSWORD);
        AccountLogoutPage accountLogoutPage = myAccountPage.getSidebar().clickOnSidebarLogout();
        accountLogoutPage.clickOnContinueButton();
        assertUtils.assertTrue(homePage.getCurrentUrl().contains("route=common/home"), "User should be in homepage, but current URL: " + homePage.getCurrentUrl());
        homePage.navigateBack();
        assertUtils.assertPageTitle(accountLogoutPage.getPageTitle(), "Account Logout");
        accountLogoutPage.getSidebar().clickOnSideBarMyAccountLink();
        assertUtils.assertTrue(loginPage.getCurrentUrl().contains(ConfigReader.getProperty("loginUrl")), "User should be in login page, but current URL: " + loginPage.getCurrentUrl());
    }

    @Test(description = "TC_Login_020: Validate user is able to navigate to different pages from Login page", groups = {"regression"})
    public void testDifferentPageNavigationFromLoginPage() {
        RegisterPage registerPage = loginPage.clickContinueButtonUnderNewCustomer();
        assertUtils.assertEquals(registerPage.getRegisterPageHeaderText(), TestData.REGISTER_PAGE_HEADER);
        driver.navigate().back();
        ForgetPasswordPage forgetPasswordPage = loginPage.getSidebar().clickSidebarForgetPasswordLink();
        assertUtils.assertEquals(forgetPasswordPage.getForgetPasswordPageHeaderText(), TestData.FORGET_PASSWORD_PAGE_HEADER);
    }
}