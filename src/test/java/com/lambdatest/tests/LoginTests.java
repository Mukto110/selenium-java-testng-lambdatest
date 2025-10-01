package com.lambdatest.tests;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.pages.HomePage;
import com.lambdatest.framework.pages.LoginPage;
import com.lambdatest.framework.pages.MyAccountPage;
import com.lambdatest.framework.utils.AssertUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    private LoginPage loginPage;
    private HomePage homePage;
    private MyAccountPage myAccountPage;
    private AssertUtils assertUtils;

    private String expectedLoginPageTitle;
    private String expectedMyAccountPageTitle;
    private String expectedWrongCredentialMessage;

    @BeforeMethod(alwaysRun = true)
    public void initPageObjects() {
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        myAccountPage = new MyAccountPage(driver);
        this.assertUtils = new AssertUtils();

        expectedLoginPageTitle = TestData.LOGIN_PAGE_TITLE;
        expectedMyAccountPageTitle = TestData.MY_ACCOUNT_PAGE_TITLE;
        expectedWrongCredentialMessage = TestData.WRONG_CREDENTIAL_MESSAGE;
    }

    @Test(description = "TC_Login_001: Validate Login with valid credentials", groups = {"smoke", "regression"})
    public void testLoginWithValidCredentials() {
        // Step 1 -> Navigate to the homepage
        homePage.navigateToHomePage();

        // Step 2 -> Hover on 'My Account' dropdown and click 'Login'
        homePage.hoverOnMyAccountDropdown().clickLogin();

        // Step 3 -> Enter valid credentials to the email and password field and click on login button
        myAccountPage = loginPage.loginAsValidUser(TestData.VALID_EMAIL, TestData.VALID_PASSWORD);

        // Assertion (Expected Result)
        assertUtils.assertEquals(myAccountPage.getMyAccountPageHeaderText(), TestData.MY_ACCOUNT_PAGE_TITLE);
    }
}