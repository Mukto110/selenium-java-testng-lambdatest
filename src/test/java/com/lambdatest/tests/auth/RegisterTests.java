package com.lambdatest.tests.auth;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.pages.HomePage;
import com.lambdatest.framework.pages.RegisterPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegisterTests extends BaseTest {

    private HomePage homePage;
    private RegisterPage registerPage;

    @BeforeMethod(alwaysRun = true)
    public void initPageObjects() {
        homePage = new HomePage(driver);
    }

    @BeforeMethod(alwaysRun = true)
    public void navigateToRegisterPage() {
        homePage.navigateToHomePage();
        registerPage = homePage.hoverOnMyAccountDropdown().clickRegister();
    }

    @Test(description = "TC_Register_000: Validate user can navigate to Register Page", groups = {"smoke"})
    public void testNavigateToRegisterPage() {
        assertUtils.assertEquals(registerPage.getRegisterPageHeaderText(), TestData.REGISTER_PAGE_HEADER);
        assertUtils.assertTrue(registerPage.getCurrentUrl().contains("route=account/register"), "Register page URL contains 'route=account/register'");
    }

//    @Test(description = "TC_Register_001: Validate Registering an Account by providing only the Mandatory fields", groups = {"smoke", "regression"})
//    public void testValidRegisterWithMandatoryFields() {
//        registerPage.validRegister()
//    }
}
