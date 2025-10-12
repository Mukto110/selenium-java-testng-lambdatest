package com.lambdatest.tests.search;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.pages.HomePage;
import com.lambdatest.framework.pages.ProductComparePage;
import com.lambdatest.framework.pages.SearchPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SearchTests extends BaseTest {

    // TODO: Move test credentials to a secure external config or test data class later.
    String existingValue = "imac";
    String nonExistingValue = "Fitbit";
    String searchCriteriaValue = "mac";

    private HomePage homePage;
    private SearchPage searchPage;

    @BeforeMethod(alwaysRun = true)
    public void navigateToHomePage() {
        homePage = new HomePage(driver);
        searchPage = new SearchPage(driver);
        homePage.navigateToHomePage();
    }

    @Test(description = "TC_Search_UI_000: Validate Search box is visible on homepage", groups = {"ui", "smoke"})
    public void testSearchBoxIsVisible() {
        assertUtils.assertTrue(homePage.getSearchBox().isSearchInputBoxVisible(), "Search input box should be visible on the homepage");
    }

    @Test(description = "TC_Search_001: Validate searching with an existing Product Name", groups = {"sanity", "regression"})
    public void testSearchWithAnExistingProduct() {
        homePage.getSearchBox().performSearch(existingValue);
        assertUtils.assertEquals(searchPage.getSearchPageHeaderText(), TestData.SEARCH_PAGE_HEADER + " " + existingValue);
        assertUtils.assertTrue(searchPage.doesProductNameContain(existingValue), "Search results should contain the product name: '" + existingValue + "'");
    }

    @Test(description = "TC_Search_002: Validate searching with a non existing Product Name", groups = {"regression", "negative"})
    public void testSearchWithAnNotExistingProduct() {
        homePage.getSearchBox().performSearch(nonExistingValue);
        assertUtils.assertEquals(searchPage.getSearchPageHeaderText(), TestData.SEARCH_PAGE_HEADER + " " + nonExistingValue);
        assertUtils.assertEquals(searchPage.getNoProductMatchMessage(), TestData.NO_PRODUCT_MATCH_MESSAGE);
    }

    @Test(description = "TC_Search_003: Validate searching without providing any Product Name", groups = {"regression"})
    public void testSearchWithNoValue() {
        homePage.getSearchBox().performSearch("");
        assertUtils.assertEquals(searchPage.getSearchPageHeaderText(), TestData.SEARCH_PAGE_HEADER);
    }

    @Test(description = "TC_Search_005: Validate searching by providing a search criteria which results in multiple products", groups = {"regression"})
    public void testSearchByProvidingSearchCriteria() {
        homePage.getSearchBox().performSearch(searchCriteriaValue);
        assertUtils.assertTrue(searchPage.doesProductNameContain(searchCriteriaValue), "Product titles should match with value: "+searchCriteriaValue);
    }

    @Test(description = "TC_Search_006: Validate search boxes has the placeholder text", groups = {"ui"})
    public void testSearchInputsPlaceholderTexts() {
        homePage.getSearchBox().performSearch("");
        assertUtils.assertEquals(searchPage.getSearchPageHeaderText(), TestData.SEARCH_PAGE_HEADER);
        assertUtils.assertEquals(searchPage.getSearchBox().getSearchInputBoxPlaceholderText(), "Search For Products");
        assertUtils.assertEquals(searchPage.getSearchCriteriaInputBoxPlaceholderText(), "Keywords");
    }

    @Test(description = "TC_Search_007: Validate searching using 'Search Criteria' field", groups = {"regression"})
    public void testSearchUsingSearchCriteria() {
        homePage.getSearchBox().performSearch("");
        searchPage.fillSearchCriteriaInputBox(existingValue).clickOnSearchCriteriaSearchButton();
        assertUtils.assertTrue(searchPage.doesProductNameContain(existingValue), "Search results should contain the product name: '" + existingValue + "'");
    }

    @Test(description = "TC_Search_013: Validate navigating to Product Compare Page from Search Results page", groups = {"regression"})
    public void testValidateNavigateToProductComparePage() {
        homePage.getSearchBox().performSearch("");
        ProductComparePage productComparePage = searchPage.clickOnProductCompareLink();
        assertUtils.assertEquals(productComparePage.getProductComparePageHeaderText(), TestData.PRODUCT_COMPARE_PAGE_HEADER);
    }

    @Test(description = "TC_Search_014: Validate User is able to sort the Products displayed in the Search Results", groups = {"regression"})
    public void testValidateSortSearchedProduct() {
        homePage.getSearchBox().performSearch(searchCriteriaValue).selectSortOption("Price (High > Low)");

    }
}
