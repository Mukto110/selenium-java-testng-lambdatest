package com.lambdatest.tests.auth;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.pages.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LogoutTests extends BaseTest {

    private HomePage homePage;
    private MyAccountPage myAccountPage;
    private AccountLogoutPage accountLogoutPage;

    @BeforeMethod(alwaysRun = true)
    public void loginToAccount() {
        log.info("🔐 Logging into account before each test...");
        homePage = new HomePage(driver);

        // Step 1: Navigate to home page
        homePage.navigateToHomePage();

        // Step 2: Hover and navigate to Login page
        LoginPage loginPage = homePage.getNavbar()
                .hoverOnMyAccountDropdown()
                .clickLogin();

        // Step 3: Log in with valid credentials
        myAccountPage = loginPage.loginAsValidUser(TestData.VALID_EMAIL, TestData.VALID_PASSWORD);

        // Step 4: Verify user landed on My Account page
        assertUtils.assertEquals(
                myAccountPage.getMyAccountPageHeaderText(),
                TestData.MY_ACCOUNT_PAGE_HEADER
        );
    }

    @Test(description = "TC_Logout_001: Validate logout from My Account dropdown", groups = {"smoke"})
    public void testLogoutFromMyAccountDropMenu() {
        log.info("🧭 Logging out using 'My Account' dropdown...");
        accountLogoutPage = homePage.getNavbar()
                .hoverOnMyAccountDropdown()
                .clickLogout();

        assertUtils.assertEquals(
                accountLogoutPage.getAccountLogoutPageHeaderText(),
                TestData.ACCOUNT_LOGOUT_PAGE_HEADER
        );

        accountLogoutPage.clickOnContinueButton();

        assertUtils.assertTrue(
                homePage.getCurrentUrl().contains("index.php?route=common/home"),
                "User returned to Home page"
        );
    }

    @Test(description = "TC_Logout_002: Validate logout from sidebar options", groups = {"smoke"})
    public void testLogoutFromSidebarOptions() {
        log.info("🧭 Logging out using sidebar option...");
        accountLogoutPage = myAccountPage.getSidebar().clickOnSidebarLogout();
        accountLogoutPage.clickOnContinueButton();

        assertUtils.assertTrue(
                homePage.getCurrentUrl().contains("index.php?route=common/home"),
                "User returned to Home page"
        );
    }

    @Test(description = "TC_Logout_005: Validate logout option is not visible after logout", groups = {"regression"})
    public void testLogoutOptionNotVisibleAfterLogout() {
        log.info("👀 Checking visibility of Logout option post-logout...");
        accountLogoutPage = homePage.getNavbar()
                .hoverOnMyAccountDropdown()
                .clickLogout();
        accountLogoutPage.clickOnContinueButton();

        homePage.getNavbar().hoverOnMyAccountDropdown();
        assertUtils.assertFalse(
                homePage.getNavbar().isLogoutOptionVisible()
        );
    }

    @Test(description = "TC_Logout_007: Validate logging out and logging in immediately after logout", groups = {"regression"})
    public void testImmediateLoginAfterLogout() {
        log.info("🔁 Logging out and immediately logging in again...");
        accountLogoutPage = homePage.getNavbar()
                .hoverOnMyAccountDropdown()
                .clickLogout();

        accountLogoutPage.clickOnContinueButton();

        LoginPage loginPage = homePage.getNavbar()
                .hoverOnMyAccountDropdown()
                .clickLogin();

        assertUtils.assertEquals(
                loginPage.getLoginFormHeaderText(),
                TestData.LOGIN_PAGE_HEADER
        );
    }

    @Test(description = "TC_Logout_008: Validate 'Account Logout' page breadcrumb", groups = {"ui"})
    public void testAccountLogoutPageUi() {
        accountLogoutPage = homePage.getNavbar().clickLogout();
        assertUtils.softAssertEquals(accountLogoutPage.getBreadcrumbText(), "Logout");
    }
}
