package com.lambdatest.tests;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.pages.LoginPage;
import com.lambdatest.framework.pages.MyAccountPage;
import com.lambdatest.framework.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests  extends BaseTest {

    protected LoginPage loginPage;
    protected MyAccountPage myAccountPage;

    @Test(description = "Verify login page loads", groups = {"smoke"})
    public void testLoginPageLoads() {
        loginPage = new LoginPage();
        loginPage.navigateTo(ConfigReader.getProperty("loginUrl"));
        Assert.assertEquals(loginPage.getLoginTitle(), ConfigReader.getProperty("loginPageTitle"));
    }

    @Test(description = "Verify login with valid username & password", groups = {"smoke"})
    public void testValidLogin() {
        loginPage = new LoginPage();
        loginPage.navigateTo(ConfigReader.getProperty("loginUrl"));
        String email = ConfigReader.getProperty("email");
        String password = ConfigReader.getProperty("password");
        loginPage.login(email, password);
        myAccountPage = new MyAccountPage();
        Assert.assertEquals(myAccountPage.getMyAccountTitleText(), ConfigReader.getProperty("myAccountPageTitle"));
    }
}
