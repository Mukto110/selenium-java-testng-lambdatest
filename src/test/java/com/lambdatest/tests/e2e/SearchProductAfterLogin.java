package com.lambdatest.tests.e2e;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.pages.home.HomePage;
import com.lambdatest.framework.pages.account.LoginPage;
import com.lambdatest.framework.pages.account.MyAccountPage;
import com.lambdatest.framework.pages.search.SearchPage;
import org.testng.annotations.Test;

public class SearchProductAfterLogin extends BaseTest {

    String productName = "imac";

    @Test(description = "TC_004: Validate searching for a product after login to the Application", groups = {"e2e"})
    public void testSearchForProductAfterLogin() {
        HomePage homePage = new HomePage(driver);
        homePage.navigateToHomePage();
        LoginPage loginPage = homePage.getNavbar().clickLogin();
        MyAccountPage myAccountPage = loginPage.loginAsValidUser(TestData.VALID_EMAIL, TestData.VALID_PASSWORD);
        SearchPage searchPage = myAccountPage.getSearchBox().fillSearchInputBox(productName).clickOnSearchButton();
        assertUtils.assertEquals(searchPage.getSearchPageHeaderText(), TestData.SEARCH_PAGE_HEADER + " " +productName);
        assertUtils.assertTrue(searchPage.doesProductNameContain(productName), "Product list should match with the value: "+productName);
    }
}
