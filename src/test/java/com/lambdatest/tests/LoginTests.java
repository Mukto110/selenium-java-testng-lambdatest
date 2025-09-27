package com.lambdatest.tests;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.pages.LoginPage;
import com.lambdatest.framework.pages.MyAccountPage;
import com.lambdatest.framework.utils.AssertUtils;
import com.lambdatest.framework.utils.ConfigReader;
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
        String expectedTitle = ConfigReader.getProperty("loginPageTitle");
        assertUtils.assertEquals(actualTitle, expectedTitle);
    }

    @Test(description = "Verify login with valid username & password", groups = {"smoke"})
    public void testValidLogin() {
        loginPage.goToLoginPage();
        String email = ConfigReader.getProperty("email");
        String password = ConfigReader.getProperty("password");
        loginPage.login(email, password);
        String actualTitle = myAccountPage.getMyAccountTitleText();
        String expectedTitle = ConfigReader.getProperty("myAccountPageTitle");
        assertUtils.assertEquals(actualTitle, expectedTitle);
    }

    @Test(description = "Verify error message with invalid email.", groups = {"regression"})
    public void testInvalidEmail() {

    }
}
