package com.lambdatest.tests.e2e;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.pages.HomePage;
import com.lambdatest.framework.pages.LoginPage;
import com.lambdatest.framework.pages.MyAccountPage;
import org.testng.annotations.Test;

public class LoginAfterPasswordChangeTest extends BaseTest {


    @Test(description = "TC_Login_016: Validate login into the application after password change", groups = {"e2e", "regression"})
    public void testLoginAfterPasswordChange() {
        HomePage homePage = new HomePage(driver);
        // Step 1 -> Navigate to homepage
        homePage.navigateToHomePage();

        // Step 2 -> Go to Login Page
        LoginPage loginPage = homePage.hoverOnMyAccountDropdown().clickLogin();

        // Step 3 -> Login with valid credentials
        MyAccountPage myAccountPage = loginPage.loginAsValidUser(TestData.VALID_EMAIL, TestData.VALID_PASSWORD);
        assertUtils.assertEquals(myAccountPage.getMyAccountPageHeaderText(), TestData.MY_ACCOUNT_PAGE_HEADER);

        // Step 4 -> Navigate to Change Password page
    }
}
