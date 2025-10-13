package com.lambdatest.tests.search;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.pages.HomePage;
import com.lambdatest.framework.pages.ProductComparePage;
import com.lambdatest.framework.pages.SearchPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchTests extends BaseTest {

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
        assertUtils.assertTrue(homePage.getSearchBox().isSearchInputBoxVisible(),
                "Search input box should be visible on the homepage");
    }

    @Test(description = "TC_Search_001: Validate searching with an existing Product Name", groups = {"sanity", "regression"})
    public void testSearchWithAnExistingProduct() {
        homePage.getSearchBox().performSearch(existingValue);
        assertUtils.assertEquals(searchPage.getSearchPageHeaderText(),
                TestData.SEARCH_PAGE_HEADER + " " + existingValue);
        assertUtils.assertTrue(searchPage.doesProductNameContain(existingValue),
                "Search results should contain the product name: '" + existingValue + "'");

        int productCount = searchPage.getProductCount();
        log.info("Total products found for '{}': {}", existingValue, productCount);
        assertUtils.assertTrue(productCount > 0,
                "Product count should be greater than 0 for: " + existingValue);
    }

    @Test(description = "TC_Search_002: Validate searching with a non existing Product Name", groups = {"regression", "negative"})
    public void testSearchWithAnNotExistingProduct() {
        homePage.getSearchBox().performSearch(nonExistingValue);
        assertUtils.assertEquals(searchPage.getSearchPageHeaderText(),
                TestData.SEARCH_PAGE_HEADER + " " + nonExistingValue);
        assertUtils.assertEquals(searchPage.getNoProductMatchMessage(),
                TestData.NO_PRODUCT_MATCH_MESSAGE);
    }

    @Test(description = "TC_Search_003: Validate searching without providing any Product Name", groups = {"regression"})
    public void testSearchWithNoValue() {
        homePage.getSearchBox().performSearch("");
        assertUtils.assertEquals(searchPage.getSearchPageHeaderText(),
                TestData.SEARCH_PAGE_HEADER);
    }

    @Test(description = "TC_Search_005: Validate searching by providing a search criteria which results in multiple products", groups = {"regression"})
    public void testSearchByProvidingSearchCriteria() {
        homePage.getSearchBox().performSearch(searchCriteriaValue);
        assertUtils.assertTrue(searchPage.doesProductNameContain(searchCriteriaValue),
                "Product titles should match with value: " + searchCriteriaValue);

        int productCount = searchPage.getProductCount();
        log.info("Total products found for '{}': {}", searchCriteriaValue, productCount);
        assertUtils.assertTrue(productCount > 1,
                "More than one product should be displayed for: " + searchCriteriaValue);
    }

    @Test(description = "TC_Search_006: Validate search boxes has the placeholder text", groups = {"ui"})
    public void testSearchInputsPlaceholderTexts() {
        homePage.getSearchBox().performSearch("");
        assertUtils.assertEquals(searchPage.getSearchPageHeaderText(),
                TestData.SEARCH_PAGE_HEADER);
        assertUtils.assertEquals(searchPage.getSearchBox().getSearchInputBoxPlaceholderText(),
                "Search For Products");
        assertUtils.assertEquals(searchPage.getSearchCriteriaInputBoxPlaceholderText(),
                "Keywords");
    }

    @Test(description = "TC_Search_007: Validate searching using 'Search Criteria' field", groups = {"regression"})
    public void testSearchUsingSearchCriteria() {
        homePage.getSearchBox().performSearch("");
        searchPage.fillSearchCriteriaInputBox(existingValue).clickOnSearchCriteriaSearchButton();
        assertUtils.assertTrue(searchPage.doesProductNameContain(existingValue),
                "Search results should contain the product name: '" + existingValue + "'");

        int productCount = searchPage.getProductCount();
        log.info("Total products found for '{}': {}", existingValue, productCount);
        assertUtils.assertTrue(productCount > 0,
                "Product count should be greater than 0 for: " + existingValue);
    }

    @Test(description = "TC_Search_013: Validate navigating to Product Compare Page from Search Results page", groups = {"regression"})
    public void testValidateNavigateToProductComparePage() {
        homePage.getSearchBox().performSearch("");
        ProductComparePage productComparePage = searchPage.clickOnProductCompareLink();
        assertUtils.assertEquals(productComparePage.getProductComparePageHeaderText(),
                TestData.PRODUCT_COMPARE_PAGE_HEADER);
    }

    @Test(description = "TC_Search_014: Validate User is able to sort the Products displayed in the Search Results", groups = {"regression"})
    public void testValidateSortSearchedProduct() {
        homePage.getSearchBox().performSearch(searchCriteriaValue);
        searchPage.selectSortOption("Name (A - Z)");
        List<String> actualNames = searchPage.getProductNames();
        List<String> expectedNames = new ArrayList<>(actualNames);
        expectedNames.sort(String.CASE_INSENSITIVE_ORDER);
        assertUtils.assertEquals(actualNames, expectedNames);

        int productCountByName = searchPage.getProductCount();
        log.info("Product count (after Name sort): {}", productCountByName);
        assertUtils.assertTrue(productCountByName > 1, "Expected multiple products for sorting check");

        homePage.getSearchBox().performSearch(searchCriteriaValue);
        searchPage.selectSortOption("Price (Low > High)");
        List<Double> actualPrices = searchPage.getProductPrices();
        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedPrices);
        assertUtils.assertEquals(actualPrices, expectedPrices);

        int productCountByPrice = searchPage.getProductCount();
        log.info("Product count (after Price sort): {}", productCountByPrice);
        assertUtils.assertTrue(productCountByPrice > 1, "Expected multiple products for sorting check");
    }

    @Test(description = "TC_Search_015: Validate the User can select how many products can be displayed in the Search Results", groups = {"regression"})
    public void testValidateProductDisplayLimitOption() {
        homePage.getSearchBox().performSearch("apple");
        searchPage.selectShowOption("25");
        assertUtils.assertTrue(searchPage.getProductCount() > 15, "Product count is more than 15");
    }
}
