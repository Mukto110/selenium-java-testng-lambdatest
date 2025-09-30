package com.lambdatest.tests;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.data.TestDataGenerator;
import com.lambdatest.framework.pages.ForgetPasswordPage;
import com.lambdatest.framework.pages.HomePage;
import com.lambdatest.framework.pages.LoginPage;
import com.lambdatest.framework.pages.MyAccountPage;
import com.lambdatest.framework.utils.AssertUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    private LoginPage loginPage;
    private HomePage homePage;
    private AssertUtils assertUtils;

    private String expectedLoginPageTitle;
    private String expectedMyAccountPageTitle;
    private String expectedWrongCredentialMessage;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        loginPage = new LoginPage();
        homePage = new HomePage();
        this.assertUtils = new AssertUtils();

        expectedLoginPageTitle = TestData.LOGIN_PAGE_TITLE;
        expectedMyAccountPageTitle = TestData.MY_ACCOUNT_PAGE_TITLE;
        expectedWrongCredentialMessage = TestData.WRONG_CREDENTIAL_MESSAGE;
    }

    @Test(description = "Verify login page loads", groups = {"smoke"})
    public void testLoginPageLoads() {
        loginPage.goToLoginPage();
        String actualTitle = loginPage.getLoginFormHeaderText();
        assertUtils.assertPageTitle(actualTitle, expectedLoginPageTitle);
    }

    @Test(description = "Validate Login with valid credentials", groups = {"smoke"})
    public void testValidLogin() {
        MyAccountPage myAccountPage = loginPage
                .goToLoginPage()
                .loginAsValidUser(TestData.VALID_EMAIL, TestData.VALID_PASSWORD);

        String myAccountPageTitle = myAccountPage.getMyAccountPageHeaderText();
        assertUtils.assertPageTitle(myAccountPageTitle, expectedMyAccountPageTitle);
    }

    @Test(description = "Validate Login with invalid credentials (Invalid email and password)", groups = {"regression"})
    public void testInvalidLogin() {
        loginPage.goToLoginPage()
                .loginAsInvalidUser(TestDataGenerator.getRandomEmail(), TestDataGenerator.getRandomPassword());

        assertUtils.assertWrongCredentialError(loginPage, expectedWrongCredentialMessage);
    }

    @Test(description = "Validate Login with invalid email address and valid password", groups = {"regression"})
    public void testInvalidEmail() {
        loginPage.goToLoginPage()
                .loginAsInvalidUser(TestDataGenerator.getRandomEmail(), TestData.VALID_PASSWORD);

        assertUtils.assertWrongCredentialError(loginPage, expectedWrongCredentialMessage);
    }

    @Test(description = "Validate Login with valid email address and invalid password", groups = {"regression"})
    public void testInvalidPassword() {
        loginPage.goToLoginPage()
                .loginAsInvalidUser(TestData.VALID_EMAIL, TestDataGenerator.getRandomPassword());

        assertUtils.assertWrongCredentialError(loginPage, expectedWrongCredentialMessage);
    }

    @Test(description = "Validate Login without providing any credentials (Known Bug: BUG-1234)", groups = {"regression"})
    public void testEmptyFields() {
        loginPage.goToLoginPage()
                .loginAsInvalidUser("", "");

        assertUtils.assertWrongCredentialError(loginPage, expectedWrongCredentialMessage);
    }

    @Test(description = "Validate 'Forgotten Password' link is available in the Login page and is working")
    public void testForgetPasswordLink() {
        ForgetPasswordPage forgetPasswordPage = loginPage
                .goToLoginPage()
                .clickForgetPasswordLink();

        String forgetPasswordTitleText = forgetPasswordPage.getForgetPasswordPageTitleText();
        assertUtils.assertPageTitle(forgetPasswordTitleText, TestData.FORGET_PASSWORD_PAGE_TITLE);
    }

    @Test(description = "Validate user can login with valid credentials", groups = {"e2e"})
    public void testE2ELoginValidFlow() {
        homePage.navigateToHomePage()
                .hoverOnMyAccountDropdown()
                .clickLogin();

        String actualTitle = loginPage.getLoginFormHeaderText();
        assertUtils.assertPageTitle(actualTitle, expectedLoginPageTitle);

        MyAccountPage myAccountPage = loginPage.loginAsValidUser(TestData.VALID_EMAIL, TestData.VALID_PASSWORD);
        String myAccountPageTitle = myAccountPage.getMyAccountPageHeaderText();
        assertUtils.assertPageTitle(myAccountPageTitle, expectedMyAccountPageTitle);
    }
}