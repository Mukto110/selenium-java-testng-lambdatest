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
        log.info("Navigating to Home Page before test execution");
        homePage = new HomePage(driver);
        homePage.navigateToHomePage();

        log.info("Opening Login Page via 'My Account' dropdown");
        loginPage = homePage.getHeader().clickLogin();
    }

    @Test(description = "TC_Login_000: Validate user can navigate to Login Page", groups = {"auth", "smoke"})
    public void testNavigateToLoginPage() {
        log.info("▶ Starting test: Validate user can navigate to Login Page");
        assertUtils.assertEquals(loginPage.getLoginFormHeaderText(), TestData.LOGIN_PAGE_HEADER);
        assertUtils.assertTrue(loginPage.getCurrentUrl().contains("route=account/login"), "User should be in login page, but current URL: " + loginPage.getCurrentUrl());
        log.info("✅ Successfully verified navigation to Login Page");
    }

    @Test(description = "TC_Login_001: Validate Login with valid credentials", groups = {"auth", "smoke", "regression"})
    public void testLoginWithValidCredentials() {
        log.info("▶ Starting test: Validate Login with valid credentials");
        MyAccountPage myAccountPage = loginPage.loginAsValidUser(TestData.VALID_EMAIL, TestData.VALID_PASSWORD);
        assertUtils.assertEquals(myAccountPage.getMyAccountPageHeaderText(), TestData.MY_ACCOUNT_PAGE_HEADER);
        log.info("✅ Login successful with valid credentials");
    }

    @Test(description = "TC_Login_002: Validate Login with invalid credentials (Invalid email and password)", groups = {"auth", "regression", "negative"})
    public void testLoginWithInvalidCredentials() {
        log.info("▶ Starting test: Validate Login with invalid credentials (email + password)");
        loginPage.loginAsInvalidUser(TestDataGenerator.getRandomEmail(), TestDataGenerator.getRandomPassword());
        assertUtils.assertEquals(loginPage.getWrongCredentialErrorText(), TestData.WRONG_CREDENTIAL_MESSAGE);
        log.info("✅ Proper error message displayed for invalid credentials");
    }

    @Test(description = "TC_Login_003: Validate Login with invalid email address and valid password", groups = {"auth", "regression", "negative"})
    public void testLoginWithInvalidEmail() {
        log.info("▶ Starting test: Validate Login with invalid email and valid password");
        loginPage.loginAsInvalidUser(TestDataGenerator.getRandomEmail(), TestData.VALID_PASSWORD);
        assertUtils.assertEquals(loginPage.getWrongCredentialErrorText(), TestData.WRONG_CREDENTIAL_MESSAGE);
        log.info("✅ Correct error message displayed for invalid email");
    }

    @Test(description = "TC_Login_004: Validate Login with valid email address and invalid password", groups = {"auth", "regression", "negative"})
    public void testLoginWithInvalidPassword() {
        log.info("▶ Starting test: Validate Login with valid email and invalid password");
        loginPage.loginAsInvalidUser(TestData.VALID_EMAIL, TestDataGenerator.getRandomPassword());
        assertUtils.assertEquals(loginPage.getWrongCredentialErrorText(), TestData.WRONG_CREDENTIAL_MESSAGE);
        log.info("✅ Correct error message displayed for invalid password");
    }

//    @Test(description = "TC_Login_005: Validate Login without providing any credentials (Known Bug: BUG-001)", groups = {"auth", "regression", "negative"})
//    public void testLoginWithEmptyField() {
//        log.info("▶ Starting test: Validate Login without providing any credentials");
//        loginPage.loginAsInvalidUser("", "");
//        assertUtils.assertEquals(loginPage.getWrongCredentialErrorText(), TestData.WRONG_CREDENTIAL_MESSAGE);
//        log.warn("⚠ Known Bug (BUG-001): Error message may not appear properly for empty fields");
//    }

    @Test(description = "TC_Login_006: Validate 'Forgotten Password' link is available in the Login page and is working", groups = {"auth", "sanity", "regression"})
    public void testForgetPasswordLink() {
        log.info("▶ Starting test: Validate 'Forgotten Password' link functionality");
        ForgetPasswordPage forgetPasswordPage = loginPage.clickForgetPasswordLink();
        assertUtils.assertEquals(forgetPasswordPage.getForgetPasswordPageHeaderText(), TestData.FORGET_PASSWORD_PAGE_HEADER);
        log.info("✅ 'Forgotten Password' link navigates correctly");
    }

    @Test(description = "TC_Login_008: Validate placeholders in login fields", groups = {"auth", "ui"})
    public void testLoginFieldPlaceholders() {
        log.info("▶ Starting test: Validate placeholders in login fields");
        assertUtils.softAssertEquals(loginPage.getEmailFieldPlaceholder(), "E-Mail Address");
        assertUtils.assertEquals(loginPage.getPasswordFieldPlaceholder(), "Password");
        assertUtils.assertAll();
        log.info("✅ Completed placeholder validation test successfully!");
    }

    @Test(description = "TC_Login_010: Validate any action on dashboard get user logout after successful logout and click browser back button", groups = {"auth", "regression", "security"})
    public void testUserCannotAccessDashboardAfterLogout() {
        log.info("▶ Starting test: Validate user cannot access dashboard after logout");
        MyAccountPage myAccountPage = loginPage.loginAsValidUser(TestData.VALID_EMAIL, TestData.VALID_PASSWORD);
        AccountLogoutPage accountLogoutPage = myAccountPage.getSidebar().clickOnSidebarLogout();

        log.info("🔹 User logged out successfully. Verifying post-logout navigation behavior");
        accountLogoutPage.clickOnContinueButton();
        assertUtils.assertTrue(homePage.getCurrentUrl().contains("route=common/home"), "User should be in homepage, but current URL: " + homePage.getCurrentUrl());

        homePage.navigateBack();
        assertUtils.assertPageTitle(accountLogoutPage.getPageTitle(), "Account Logout");

        accountLogoutPage.getSidebar().clickOnSideBarMyAccountLink();
        assertUtils.assertTrue(loginPage.getCurrentUrl().contains(ConfigReader.getProperty("loginUrl")), "User should be in login page, but current URL: " + loginPage.getCurrentUrl());
        log.info("✅ Verified user cannot access dashboard after logout");
    }

    @Test(description = "TC_Login_020: Validate user is able to navigate to different pages from Login page", groups = {"auth", "regression"})
    public void testDifferentPageNavigationFromLoginPage() {
        log.info("▶ Starting test: Validate navigation to other pages from Login page");
        RegisterPage registerPage = loginPage.clickContinueButtonUnderNewCustomer();
        assertUtils.assertEquals(registerPage.getRegisterPageHeaderText(), TestData.REGISTER_PAGE_HEADER);

        log.info("🔹 Navigated to Register Page successfully, returning to Login Page");
        driver.navigate().back();

        ForgetPasswordPage forgetPasswordPage = loginPage.getSidebar().clickSidebarForgetPasswordLink();
        assertUtils.assertEquals(forgetPasswordPage.getForgetPasswordPageHeaderText(), TestData.FORGET_PASSWORD_PAGE_HEADER);
        log.info("✅ Verified page navigation links from Login page are functional");
    }
}