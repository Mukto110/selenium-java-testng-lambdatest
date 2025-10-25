package com.lambdatest.tests.dataDriven;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.pages.account.LoginPage;
import com.lambdatest.framework.pages.account.MyAccountPage;
import com.lambdatest.framework.pages.home.HomePage;
import com.lambdatest.framework.utils.DataProviders;
import org.testng.annotations.Test;

public class LoginTestsDD extends BaseTest {

    @Test(description = "Validate login with multiple data sets", dataProvider = "LoginData", dataProviderClass = DataProviders.class)
    public void testLoginDD(String email, String password, String expectedResult) {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.navigateToHomePage().getHeader().hoverOnMyAccountDropdown().clickLogin();
        if (expectedResult.equalsIgnoreCase("valid")) {
            MyAccountPage myAccountPage = loginPage.loginAsValidUser(email, password);
            assertUtils.assertEquals(myAccountPage.getMyAccountPageHeaderText(), TestData.MY_ACCOUNT_PAGE_HEADER);
            myAccountPage.getSidebar().clickOnSidebarLogout();
        }
        if (expectedResult.equalsIgnoreCase("invalid")) {
            loginPage.loginAsInvalidUser(email, password);
            assertUtils.assertEquals(loginPage.getWrongCredentialErrorText(), TestData.WRONG_CREDENTIAL_MESSAGE);
        }
    }
}
