package com.lambdatest.tests.auth;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.pages.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LogoutTests extends BaseTest {

    private HomePage homePage;
    private MyAccountPage myAccountPage;

    @BeforeMethod(alwaysRun = true)
    public void loginToAccount() {
        log.info("🔐 Logging into account before each test...");
        homePage = new HomePage(driver);
        homePage.navigateToHomePage();
        LoginPage loginPage = homePage.hoverOnMyAccountDropdown().clickLogin();
        myAccountPage = loginPage.loginAsValidUser(TestData.VALID_EMAIL, TestData.VALID_PASSWORD);
        assertUtils.assertEquals(myAccountPage.getMyAccountPageHeaderText(), TestData.MY_ACCOUNT_PAGE_HEADER);
    }

    @Test(description = "TC_Logout_001: Validate logout from My Account dropdown", groups = {"smoke"})
    public void testLogoutFromMyAccountDropMenu() {
        AccountLogoutPage logoutPage = homePage.hoverOnMyAccountDropdown().clickLogout();
        assertUtils.assertEquals(logoutPage.getAccountLogoutPageHeaderText(), TestData.ACCOUNT_LOGOUT_PAGE_HEADER);
        logoutPage.clickOnContinueButton();
        assertUtils.assertTrue(homePage.getCurrentUrl().contains("index.php?route=common/home"), "User returned to Home page");
    }

    @Test(description = "TC_Logout_002: Validate logout from sidebar options", groups = {"smoke"})
    public void testLogoutFromSidebarOptions() {
        AccountLogoutPage logoutPage = myAccountPage.clickOnSidebarLogout();
        logoutPage.clickOnContinueButton();
        assertUtils.assertTrue(homePage.getCurrentUrl().contains("index.php?route=common/home"), "User returned to Home page");
    }

    @Test(description = "TC_Logout_004: Validate logging out and navigating back", groups = {"regression"})
    public void testLogoutAndBrowseBack() {
        AccountLogoutPage logoutPage = homePage.hoverOnMyAccountDropdown().clickLogout();
        logoutPage.clickOnContinueButton();
        homePage.navigateBack();
        assertUtils.assertEquals(new LoginPage(driver).getLoginFormHeaderText(), TestData.LOGIN_PAGE_HEADER);
    }

    @Test(description = "TC_Logout_005: Validate logout option is not visible after logout", groups = {"regression"})
    public void testLogoutOptionNotVisibleAfterLogout() {
        AccountLogoutPage logoutPage = homePage.hoverOnMyAccountDropdown().clickLogout();
        logoutPage.clickOnContinueButton();
        homePage.hoverOnMyAccountDropdown();
        assertUtils.assertFalse(homePage.isLogoutOptionVisible());
    }
}