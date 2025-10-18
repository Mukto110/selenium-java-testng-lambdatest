package com.lambdatest.tests.auth;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.pages.account.AccountLogoutPage;
import com.lambdatest.framework.pages.account.LoginPage;
import com.lambdatest.framework.pages.account.MyAccountPage;
import com.lambdatest.framework.pages.home.HomePage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LogoutTests extends BaseTest {

    private HomePage homePage;
    private MyAccountPage myAccountPage;
    private AccountLogoutPage accountLogoutPage;

    @BeforeMethod(alwaysRun = true)
    public void loginToAccount() {
        log.info("Starting setup: Logging in before logout tests");

        homePage = new HomePage(driver);
        log.info("Navigating to Home Page");
        homePage.navigateToHomePage();

        log.info("Navigating to Login Page via 'My Account' dropdown");
        LoginPage loginPage = homePage.getHeader().hoverOnMyAccountDropdown().clickLogin();

        log.info("Logging in with valid credentials");
        myAccountPage = loginPage.loginAsValidUser(TestData.VALID_EMAIL, TestData.VALID_PASSWORD);

        log.info("Verifying user landed on My Account Page after login");
        assertUtils.assertEquals(myAccountPage.getMyAccountPageHeaderText(), TestData.MY_ACCOUNT_PAGE_HEADER);

        log.info("✅ Setup complete: User is successfully logged in and ready for logout tests");
    }

    @Test(description = "TC_Logout_001: Validate logout from My Account dropdown", groups = {"auth", "smoke", "regression"})
    public void testLogoutFromMyAccountDropMenu() {
        log.info("Starting test: Validate logout from My Account dropdown");

        accountLogoutPage = homePage.getHeader().hoverOnMyAccountDropdown().clickLogout();
        assertUtils.assertEquals(accountLogoutPage.getAccountLogoutPageHeaderText(), TestData.ACCOUNT_LOGOUT_PAGE_HEADER);

        log.info("Verified 'Account Logout' page header text");
        accountLogoutPage.clickOnContinueButton();
        assertUtils.assertTrue(homePage.getCurrentUrl().contains("index.php?route=common/home"), "User should be in homepage, but current URL: " + homePage.getCurrentUrl());

        log.info("✅ Logout via dropdown verified successfully");
    }

    @Test(description = "TC_Logout_002: Validate logout from sidebar options", groups = {"auth", "smoke", "regression"})
    public void testLogoutFromSidebarOptions() {
        log.info("Starting test: Validate logout from sidebar options");

        accountLogoutPage = myAccountPage.getSidebar().clickOnSidebarLogout();
        accountLogoutPage.clickOnContinueButton();
        assertUtils.assertTrue(homePage.getCurrentUrl().contains("index.php?route=common/home"), "User should be in homepage, but current URL: " + homePage.getCurrentUrl());

        log.info("✅ Logout via sidebar option verified successfully");
    }

    @Test(description = "TC_Logout_005: Validate logout option is not visible after logout", groups = {"auth", "regression", "security"})
    public void testLogoutOptionNotVisibleAfterLogout() {
        log.info("Starting test: Validate logout option is hidden after logout");

        accountLogoutPage = homePage.getHeader().hoverOnMyAccountDropdown().clickLogout();
        accountLogoutPage.clickOnContinueButton();

        log.info("Verifying 'Logout' option is not visible after logging out");
        homePage.getHeader().hoverOnMyAccountDropdown();
        assertUtils.assertFalse(homePage.getHeader().isLogoutOptionVisible());

        log.info("✅ Verified: 'Logout' option is correctly hidden after user logs out");
    }

    @Test(description = "TC_Logout_007: Validate logging out and logging in immediately after logout", groups = {"auth", "regression", "sanity"})
    public void testImmediateLoginAfterLogout() {
        log.info("Starting test: Validate immediate login after logout");

        accountLogoutPage = homePage.getHeader().hoverOnMyAccountDropdown().clickLogout();
        accountLogoutPage.clickOnContinueButton();

        log.info("Attempting to login immediately after logout");
        LoginPage loginPage = homePage.getHeader().hoverOnMyAccountDropdown().clickLogin();
        assertUtils.assertEquals(loginPage.getLoginFormHeaderText(), TestData.LOGIN_PAGE_HEADER);

        log.info("✅ Verified user can navigate to Login Page immediately after logout");
    }

    @Test(description = "TC_Logout_008: Validate 'Account Logout' page breadcrumb", groups = {"auth", "ui", "regression"})
    public void testAccountLogoutPageUi() {
        log.info("Starting test: Validate 'Account Logout' page breadcrumb text");

        accountLogoutPage = homePage.getHeader().clickLogout();
        assertUtils.softAssertEquals(accountLogoutPage.getBreadcrumbText(), "Logout");

        log.info("✅ Verified breadcrumb text on 'Account Logout' page");
    }
}