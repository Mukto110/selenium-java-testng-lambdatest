package com.lambdatest.tests;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.data.TestDataGenerator;
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

    private String expectedLoginPageTitle;
    private String expectedMyAccountPageTitle;
    private String expectedWrongCredentialMessage;


    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        loginPage = new LoginPage();
        myAccountPage = new MyAccountPage();
        this.assertUtils = new AssertUtils();

        expectedLoginPageTitle = TestData.LOGIN_PAGE_TITLE;
        expectedMyAccountPageTitle = TestData.MY_ACCOUNT_PAGE_TITLE;
        expectedWrongCredentialMessage = TestData.WRONG_CREDENTIAL_MESSAGE;
    }

    @Test(description = "Verify login page loads", groups = {"smoke"}, priority = 1)
    public void testLoginPageLoads() {
        loginPage.goToLoginPage();
        String actualTitle = loginPage.getLoginTitle();
        assertUtils.assertEquals(actualTitle, expectedLoginPageTitle);
    }

    @Test(testName = "TC_Login_01", description = "Validate Login with valid credentials", groups = {"smoke"}, priority = 2)
    public void testValidLogin() {
        loginPage.goToLoginPage();
        loginPage.login(TestData.VALID_EMAIL, TestData.VALID_PASSWORD);
        String actualTitle = myAccountPage.getMyAccountTitleText();
        assertUtils.assertEquals(actualTitle, expectedMyAccountPageTitle);
    }

    @Test(testName = "TC_Login_02", description = "Validate Login with invalid credentials (Invalid email and password)", groups = {"regression"}, priority = 3)
    public void testInvalidLogin() {
        loginPage.goToLoginPage();
        loginPage.login(TestDataGenerator.getRandomEmail(), TestDataGenerator.getRandomPassword());
        String actualError = loginPage.getWrongCredentialErrorText();
        assertUtils.assertEquals(actualError, expectedWrongCredentialMessage);
    }

    @Test(testName = "TC_Login_003", description = "Validate Login with invalid email address and valid password", groups = {"regression"}, priority = 4)
    public void testInvalidEmail() {
        loginPage.goToLoginPage();
        loginPage.login(TestDataGenerator.getRandomEmail(), TestData.VALID_PASSWORD);
        String actualError = loginPage.getWrongCredentialErrorText();
        assertUtils.assertEquals(actualError, expectedWrongCredentialMessage);
    }

    @Test(testName = "TC_Login_004", description = "Validate Login with valid email address and invalid password", groups = {"regression"}, priority = 5)
    public void testInvalidPassword() {
        loginPage.goToLoginPage();
        loginPage.login(TestData.VALID_EMAIL, TestDataGenerator.getRandomPassword());
        String actualError = loginPage.getWrongCredentialErrorText();
        assertUtils.assertEquals(actualError, expectedWrongCredentialMessage);
    }

    @Test(testName = "TC_Login_005", description = "Validate Login without providing any credentials (Known Bug: BUG-1234)", groups = {"regression"}, priority = 6)
    public void testEmptyFields() {
        loginPage.goToLoginPage();
        loginPage.login("", "");
        String actualError = loginPage.getWrongCredentialErrorText();
        assertUtils.assertEquals(actualError, expectedWrongCredentialMessage);
    }

    @Test(testName = "TC_Login_006", description = "Validate 'Forgotten Password' link is available in the Login page and is working", priority = 7)
    public void testForgetPasswordLink() {
        loginPage.goToLoginPage();
        loginPage.clickForgetPasswordLink();
    }
}
