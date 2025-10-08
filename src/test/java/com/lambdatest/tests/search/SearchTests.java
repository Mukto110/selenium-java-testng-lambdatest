package com.lambdatest.tests.search;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.pages.HomePage;
import com.lambdatest.framework.pages.SearchPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SearchTests extends BaseTest {

    String existingValue = "imac";
    String nonExistingValue = "Fitbit";

    private HomePage homePage;

    @BeforeMethod(alwaysRun = true)
    public void navigateToHomePage() {
        homePage = new HomePage(driver);
        homePage.navigateToHomePage();
    }

    @Test(description = "TC_Search_UI_000: Validate Search box is visible on homepage", groups = {"ui", "smoke"})
    public void testSearchBoxIsVisible() {
        assertUtils.assertTrue(homePage.getSearchBox().isSearchInputBoxVisible(), "Search input box should be visible on the homepage");
    }

    @Test(description = "TC_Search_001: Validate searching with an existing Product Name", groups = {"sanity", "regression"})
    public void testSearchWithAnExistingProduct() {
        homePage.getSearchBox().fillSearchInputBox(existingValue);
        SearchPage searchPage = homePage.getSearchBox().clickOnSearchButton();
        assertUtils.assertEquals(searchPage.getSearchPageHeaderText(), TestData.SEARCH_PAGE_HEADER + " " + existingValue);
        assertUtils.assertTrue(searchPage.doesResultContains(existingValue), "Search results should contain the product name: '" + existingValue + "'");
    }

    @Test(description = "TC_Search_002: Validate searching with a non existing Product Name", groups = {"regression", "negative"})
    public void testSearchWithAnNotExistingProduct() {
        homePage.getSearchBox().fillSearchInputBox(nonExistingValue);
        SearchPage searchPage = homePage.getSearchBox().clickOnSearchButton();
        assertUtils.assertEquals(searchPage.getSearchPageHeaderText(), TestData.SEARCH_PAGE_HEADER + " " + nonExistingValue);
        assertUtils.assertEquals(searchPage.getNoProductMatchMessage(), TestData.NO_PRODUCT_MATCH_MESSAGE);
    }
}
