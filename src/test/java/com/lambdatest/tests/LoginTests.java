package com.lambdatest.tests;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.pages.LoginPage;
import com.lambdatest.framework.pages.MyAccountPage;
import com.lambdatest.framework.utils.AssertUtils;
import com.lambdatest.framework.utils.LoggerHelper;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTests  extends BaseTest {

    private LoginPage loginPage;
    private MyAccountPage myAccountPage;
    private AssertUtils assertUtils;

    private static final Logger log = LoggerHelper.getLogger(LoginTests.class);

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        loginPage = new LoginPage();
        myAccountPage = new MyAccountPage();
        this.assertUtils = new AssertUtils(log);
    }

    @Test(description = "Verify login page loads", groups = {"smoke"})
    public void testLoginPageLoads() {
        loginPage.goToLoginPage();
        String actualTitle = loginPage.getLoginTitle();
        String expectedTitle = TestData.LOGIN_PAGE_TITLE;
        assertUtils.assertEquals(actualTitle, expectedTitle);
    }

    @Test(description = "Verify login with valid username & password", groups = {"smoke"})
    public void testValidLogin() {
        loginPage.goToLoginPage();
        loginPage.login(TestData.VALID_EMAIL, TestData.VALID_PASSWORD);
        String actualTitle = myAccountPage.getMyAccountTitleText();
        assertUtils.assertEquals(actualTitle, TestData.MY_ACCOUNT_PAGE_TITLE);
    }

    @Test(description = "Verify error message with invalid email.", groups = {"regression"})
    public void testInvalidEmail() {
        loginPage.goToLoginPage();
        loginPage.login(TestData.INVALID_EMAIL, TestData.VALID_PASSWORD);
    }
}
