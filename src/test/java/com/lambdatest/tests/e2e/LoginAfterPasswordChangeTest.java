package com.lambdatest.tests.e2e;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.pages.account.ChangePasswordPage;
import com.lambdatest.framework.pages.account.LoginPage;
import com.lambdatest.framework.pages.account.MyAccountPage;
import com.lambdatest.framework.pages.home.HomePage;
import org.testng.annotations.Test;

public class LoginAfterPasswordChangeTest extends BaseTest {

    // TODO: Move test credentials to a secure external config or test data class later.
    private final String email = "testuser2@test.com";
    private final String originalPassword = "testuser001";
    private final String newPassword = "Thisistest102";

    @Test(description = "TC_Login_016: Validate login into the application after password change",
            groups = {"auth", "e2e", "regression"})
    public void testLoginAfterPasswordChange() {

        log.info("Starting E2E Test: Validate login after password change");

        // Step 1 -> Navigate to homepage
        log.info("Navigating to Home page...");
        HomePage homePage = new HomePage(driver);
        homePage.navigateToHomePage();
        log.info("✅ Successfully navigated to Home page");

        // Step 2 -> Go to Login Page
        log.info("Navigating to Login page...");
        LoginPage loginPage = homePage.getHeader().hoverOnMyAccountDropdown().clickLogin();
        log.info("✅ Landed on Login page successfully");

        // Step 3 -> Login with valid credentials (old/original password)
        log.info("Logging in with original credentials...");
        MyAccountPage myAccountPage = loginPage.loginAsValidUser(email, originalPassword);
        assertUtils.assertEquals(myAccountPage.getMyAccountPageHeaderText(), TestData.MY_ACCOUNT_PAGE_HEADER);
        log.info("✅ Successfully logged in using original password");

        // Step 4 -> Navigate to Change Password page
        log.info("Navigating to Change Password page...");
        ChangePasswordPage changePasswordPage = myAccountPage.getSidebar().clickOnSidebarPasswordLink();
        assertUtils.assertEquals(changePasswordPage.getChangePasswordPageHeaderText(), TestData.CHANGE_PASSWORD_PAGE_HEADER);
        log.info("✅ Landed on Change Password page");

        // Step 5 -> Change Password (original → new)
        log.info("Changing password from original to new...");
        myAccountPage = changePasswordPage.changePassword(newPassword, newPassword);
        assertUtils.assertEquals(myAccountPage.getPasswordUpdateSuccessText(), TestData.PASSWORD_UPDATE_SUCCESS_MESSAGE);
        log.info("✅ Password successfully changed to new one");

        // Step 6 -> Logout
        log.info("Logging out after password change...");
        homePage = myAccountPage.getSidebar().clickOnSidebarLogout().clickOnContinueButton();
        log.info("✅ Successfully logged out");

        // Step 7 -> Navigate to the login page again
        log.info("Navigating back to Login page to validate login with new password...");
        loginPage = homePage.getHeader().hoverOnMyAccountDropdown().clickLogin();

        // Step 8 -> Login again using the new password
        log.info("Attempting to login using the new password...");
        myAccountPage = loginPage.loginAsValidUser(email, newPassword);
        assertUtils.assertEquals(myAccountPage.getMyAccountPageHeaderText(), TestData.MY_ACCOUNT_PAGE_HEADER);
        log.info("✅ Successfully logged in using new password");

        // Step 9 -> ROLLBACK (Temporary - restore original password)
        log.info("Rolling back password to original for test reusability...");
        changePasswordPage = myAccountPage.getSidebar().clickOnSidebarPasswordLink();
        myAccountPage = changePasswordPage.changePassword(originalPassword, originalPassword);
        assertUtils.assertEquals(myAccountPage.getPasswordUpdateSuccessText(), TestData.PASSWORD_UPDATE_SUCCESS_MESSAGE);
        log.info("✅ Rollback complete — password restored to original");
    }
}