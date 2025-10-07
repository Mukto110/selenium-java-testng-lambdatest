package com.lambdatest.tests.e2e;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.pages.ChangePasswordPage;
import com.lambdatest.framework.pages.HomePage;
import com.lambdatest.framework.pages.LoginPage;
import com.lambdatest.framework.pages.MyAccountPage;
import org.testng.annotations.Test;

public class LoginAfterPasswordChangeTest extends BaseTest {

    // TODO: Move test credentials to a secure external config or test data class later.
    private final String email = "testuser2@test.com";
    private final String originalPassword = "testuser001";
    private final String newPassword = "Thisistest102";

    @Test(description = "TC_Login_016: Validate login into the application after password change",
            groups = {"e2e"})
    public void testLoginAfterPasswordChange() {

        log.info("🔹 Starting E2E Test: Login after password change.");

        // Step 1 -> Navigate to homepage
        HomePage homePage = new HomePage(driver);
        homePage.navigateToHomePage();
        log.info("✅ Navigated to Home Page.");

        // Step 2 -> Go to Login Page
        LoginPage loginPage = homePage.getNavbar().hoverOnMyAccountDropdown().clickLogin();
        log.info("✅ Navigated to Login Page.");

        // Step 3 -> Login with valid credentials (old/original password)
        MyAccountPage myAccountPage = loginPage.loginAsValidUser(email, originalPassword);
        assertUtils.assertEquals(myAccountPage.getMyAccountPageHeaderText(), TestData.MY_ACCOUNT_PAGE_HEADER);
        log.info("✅ Logged in successfully using original credentials.");

        // Step 4 -> Navigate to Change Password page
        ChangePasswordPage changePasswordPage = myAccountPage.getSidebar().clickOnSidebarPasswordLink();
        assertUtils.assertEquals(changePasswordPage.getChangePasswordPageHeaderText(), TestData.CHANGE_PASSWORD_PAGE_HEADER);
        log.info("✅ Navigated to Change Password page.");

        // Step 5 -> Change Password (original → new)
        log.info("🔐 Changing password from original to new...");
        myAccountPage = changePasswordPage.changePassword(newPassword, newPassword);
        assertUtils.assertEquals(myAccountPage.getPasswordUpdateSuccessText(), TestData.PASSWORD_UPDATE_SUCCESS_MESSAGE);
        log.info("✅ Password changed successfully.");

        // Step 6 -> Logout
        homePage = myAccountPage.getSidebar().clickOnSidebarLogout().clickOnContinueButton();
        log.info("✅ Logged out successfully and navigated to Home Page.");

        // Step 7 -> Navigate to the login page again
        loginPage = homePage.getNavbar().hoverOnMyAccountDropdown().clickLogin();
        log.info("✅ Navigated back to Login Page.");

        // Step 8 -> Login again using the new password
        log.info("🔁 Logging in again using the new password...");
        myAccountPage = loginPage.loginAsValidUser(email, newPassword);
        assertUtils.assertEquals(myAccountPage.getMyAccountPageHeaderText(), TestData.MY_ACCOUNT_PAGE_HEADER);
        log.info("✅ Logged in successfully with the new password.");

        // Step 9 -> ROLLBACK (Temporary - restore original password)
        log.info("♻️ Rolling back password to original for test reusability...");
        changePasswordPage = myAccountPage.getSidebar().clickOnSidebarPasswordLink();
        myAccountPage = changePasswordPage.changePassword(originalPassword, originalPassword);
        assertUtils.assertEquals(myAccountPage.getPasswordUpdateSuccessText(), TestData.PASSWORD_UPDATE_SUCCESS_MESSAGE);
        log.info("✅ Rollback complete — password restored to original.");

        log.info("🎯 Test completed successfully: Login after password change and rollback verified.");
    }
}