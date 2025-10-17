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

    @Test(description = "TC_004: Validate searching for a product after login to the Application",
            groups = {"auth", "search", "e2e", "regression"})
    public void testSearchForProductAfterLogin() {

        // Step 1 -> Navigate to Home Page
        HomePage homePage = new HomePage(driver);
        homePage.navigateToHomePage();

        // Step 2 -> Go to Login Page
        LoginPage loginPage = homePage.getHeader().clickLogin();

        // Step 3 -> Login with valid credentials
        MyAccountPage myAccountPage = loginPage.loginAsValidUser(TestData.VALID_EMAIL, TestData.VALID_PASSWORD);

        // Step 4 -> Search for product
        SearchPage searchPage = myAccountPage.getSearchBox()
                .fillSearchInputBox(productName)
                .clickOnSearchButton();

        // Step 5 -> Validate search result page
        assertUtils.assertEquals(searchPage.getSearchPageHeaderText(),
                TestData.SEARCH_PAGE_HEADER + " " + productName);
        assertUtils.assertTrue(searchPage.doesProductNameContain(productName),
                "Product list should match with the value: " + productName);
    }
}