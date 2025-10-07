package com.lambdatest.tests.e2e;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.pages.ChangePasswordPage;
import com.lambdatest.framework.pages.HomePage;
import com.lambdatest.framework.pages.LoginPage;
import com.lambdatest.framework.pages.MyAccountPage;
import org.testng.annotations.Test;

public class LoginAfterPasswordChangeTest extends BaseTest {

    // These data will be store somewhere else later.
    private final String email = "testuser2@test.com";
    private final String originalPassword = "testuser001";
    private final String newPassword = "Thisistest102";

    @Test(description = "TC_Login_016: Validate login into the application after password change",
            groups = {"e2e", "regression"})
    public void testLoginAfterPasswordChange() {
        HomePage homePage = new HomePage(driver);

        // Step 1 -> Navigate to homepage
        homePage.navigateToHomePage();

        // Step 2 -> Go to Login Page
        LoginPage loginPage = homePage.getNavbar().hoverOnMyAccountDropdown().clickLogin();

        // Step 3 -> Login with valid credentials (old/original password)
        MyAccountPage myAccountPage = loginPage.loginAsValidUser(email, originalPassword);
        assertUtils.assertEquals(myAccountPage.getMyAccountPageHeaderText(), TestData.MY_ACCOUNT_PAGE_HEADER);

        // Step 4 -> Navigate to Change Password page
        ChangePasswordPage changePasswordPage = myAccountPage.getSidebar().clickOnSidebarPasswordLink();
        assertUtils.assertEquals(changePasswordPage.getChangePasswordPageHeaderText(), TestData.CHANGE_PASSWORD_PAGE_HEADER);

        // Step 5 -> Change Password (original → new)
        myAccountPage = changePasswordPage.changePassword(newPassword, newPassword);
        assertUtils.assertEquals(myAccountPage.getPasswordUpdateSuccessText(), TestData.PASSWORD_UPDATE_SUCCESS_MESSAGE);

        // Step 6 -> Logout
        homePage = myAccountPage.getSidebar().clickOnSidebarLogout().clickOnContinueButton();

        // Step 7 -> Navigate to the login page from homepage
        loginPage = homePage.getNavbar().hoverOnMyAccountDropdown().clickLogin();

        // Step 8 -> Login as a valid user with new password
        loginPage.loginAsValidUser(email, newPassword);
        assertUtils.assertEquals(myAccountPage.getMyAccountPageHeaderText(), TestData.MY_ACCOUNT_PAGE_HEADER);

        // 🔄 Step 9 -> ROLLBACK (new → original password) -> I don't have DB or API access for this demo website, so I need to rollback the password to run this test continuously
        changePasswordPage = myAccountPage.getSidebar().clickOnSidebarPasswordLink();
        myAccountPage = changePasswordPage.changePassword(originalPassword, originalPassword);
        assertUtils.assertEquals(myAccountPage.getPasswordUpdateSuccessText(), TestData.PASSWORD_UPDATE_SUCCESS_MESSAGE);

        log.info("Rollback complete: password reset back to original.");
    }
}
