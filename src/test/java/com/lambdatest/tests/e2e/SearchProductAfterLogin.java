package com.lambdatest.tests.e2e;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.pages.home.HomePage;
import com.lambdatest.framework.pages.account.LoginPage;
import com.lambdatest.framework.pages.account.MyAccountPage;
import com.lambdatest.framework.pages.search.SearchPage;
import org.testng.annotations.Test;

public class SearchProductAfterLogin extends BaseTest {

    private final String productName = "imac";

    @Test(description = "TC_004: Validate searching for a product after login to the Application",
            groups = {"auth", "search", "e2e", "regression"})
    public void testSearchForProductAfterLogin() {

        log.info("Starting E2E Test: Validate searching for a product after login");

        // Step 1 -> Navigate to Home Page
        log.info("Navigating to Home page...");
        HomePage homePage = new HomePage(driver);
        homePage.navigateToHomePage();
        log.info("✅ Successfully navigated to Home page");

        // Step 2 -> Go to Login Page
        log.info("Navigating to Login page...");
        LoginPage loginPage = homePage.getHeader().clickLogin();
        log.info("✅ Landed on Login page successfully");

        // Step 3 -> Login with valid credentials
        log.info("Logging in with valid user credentials...");
        MyAccountPage myAccountPage = loginPage.loginAsValidUser(TestData.VALID_EMAIL, TestData.VALID_PASSWORD);
        assertUtils.assertEquals(myAccountPage.getMyAccountPageHeaderText(), TestData.MY_ACCOUNT_PAGE_HEADER);
        log.info("✅ Successfully logged in as valid user");

        // Step 4 -> Search for a product
        log.info("Searching for product: " + productName);
        SearchPage searchPage = myAccountPage.getSearchBox()
                .fillSearchInputBox(productName)
                .clickOnSearchButton();
        log.info("✅ Search executed for product: " + productName);

        // Step 5 -> Validate search result page
        log.info("Validating search results page...");
        assertUtils.assertEquals(
                searchPage.getSearchPageHeaderText(),
                TestData.SEARCH_PAGE_HEADER + " " + productName
        );
        assertUtils.assertTrue(
                searchPage.doesProductNameContain(productName),
                "Product list should match with the value: " + productName
        );
        log.info("✅ Search results validated successfully for product: " + productName);

        log.info("✅ E2E Test completed: Product search after login validated successfully");
    }
}