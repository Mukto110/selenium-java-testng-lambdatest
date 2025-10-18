package com.lambdatest.tests.search;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.pages.home.HomePage;
import com.lambdatest.framework.pages.product.ProductComparePage;
import com.lambdatest.framework.pages.search.SearchPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchTests extends BaseTest {

    String existingValue = TestData.EXISTING_VALUE;
    String nonExistingValue = TestData.NON_EXISTING_VALUE;
    String searchCriteriaValue = TestData.SEARCH_CRITERIA_VALUE;

    private HomePage homePage;
    private SearchPage searchPage;

    @BeforeMethod(alwaysRun = true)
    public void navigateToHomePage() {
        homePage = new HomePage(driver);
        searchPage = new SearchPage(driver);
        log.info("Navigating to Home page before test execution...");
        homePage.navigateToHomePage();
        log.info("✅ Home page navigation completed successfully");
    }

    @Test(description = "TC_Search_UI_000: Validate Search box is visible on homepage",
            groups = {"ui", "search", "smoke"})
    public void testSearchBoxIsVisible() {
        log.info("Starting test: Validate search box visibility on homepage");
        assertUtils.assertTrue(homePage.getSearchBox().isSearchInputBoxVisible(),
                "Search input box should be visible on the homepage");
        log.info("✅ Search box is visible on homepage");
    }

    @Test(description = "TC_Search_001: Validate searching with an existing Product Name",
            groups = {"search", "sanity", "regression"})
    public void testSearchWithAnExistingProduct() {
        log.info("Starting test: Validate searching with existing product name");
        homePage.getSearchBox().performSearch(existingValue);
        log.info("Performed search for product: {}", existingValue);

        assertUtils.assertEquals(searchPage.getSearchPageHeaderText(),
                TestData.SEARCH_PAGE_HEADER + " " + existingValue);
        assertUtils.assertTrue(searchPage.doesProductNameContain(existingValue),
                "Search results should contain the product name: '" + existingValue + "'");

        int productCount = searchPage.getProductCount();
        log.info("Total products found for '{}': {}", existingValue, productCount);
        assertUtils.assertTrue(productCount > 0, "Product count should be greater than 0 for: " + existingValue);
        log.info("✅ Search with existing product validated successfully");
    }

    @Test(description = "TC_Search_002: Validate searching with a non existing Product Name",
            groups = {"search", "negative", "regression"})
    public void testSearchWithAnNotExistingProduct() {
        log.info("Starting test: Validate searching with a non-existing product name");
        homePage.getSearchBox().performSearch(nonExistingValue);
        assertUtils.assertEquals(searchPage.getSearchPageHeaderText(),
                TestData.SEARCH_PAGE_HEADER + " " + nonExistingValue);
        assertUtils.assertEquals(searchPage.getNoProductMatchMessage(),
                TestData.NO_PRODUCT_MATCH_MESSAGE);
        log.info("✅ Non-existing product search validated successfully");
    }

    @Test(description = "TC_Search_003: Validate searching without providing any Product Name",
            groups = {"search", "negative", "regression"})
    public void testSearchWithNoValue() {
        log.info("Starting test: Validate searching without providing any product name");
        homePage.getSearchBox().performSearch("");
        assertUtils.assertEquals(searchPage.getSearchPageHeaderText(), TestData.SEARCH_PAGE_HEADER);
        log.info("✅ Search without product name handled correctly");
    }

    @Test(description = "TC_Search_005: Validate searching by providing a search criteria which results in multiple products",
            groups = {"search", "regression"})
    public void testSearchByProvidingSearchCriteria() {
        log.info("Starting test: Validate searching with a search criteria that yields multiple products");
        homePage.getSearchBox().performSearch(searchCriteriaValue);
        assertUtils.assertTrue(searchPage.doesProductNameContain(searchCriteriaValue),
                "Product titles should match with value: " + searchCriteriaValue);

        int productCount = searchPage.getProductCount();
        log.info("Total products found for '{}': {}", searchCriteriaValue, productCount);
        assertUtils.assertTrue(productCount > 1, "Expected more than one product for: " + searchCriteriaValue);
        log.info("✅ Search by criteria validated successfully");
    }

    @Test(description = "TC_Search_006: Validate search boxes have the placeholder text",
            groups = {"ui", "search"})
    public void testSearchInputsPlaceholderTexts() {
        log.info("Starting test: Validate placeholder text in search boxes");
        homePage.getSearchBox().performSearch("");
        assertUtils.assertEquals(searchPage.getSearchPageHeaderText(), TestData.SEARCH_PAGE_HEADER);
        assertUtils.assertEquals(searchPage.getSearchBox().getSearchInputBoxPlaceholderText(), "Search For Products");
        assertUtils.assertEquals(searchPage.getSearchCriteriaInputBoxPlaceholderText(), "Keywords");
        log.info("✅ Search input placeholder texts validated successfully");
    }

    @Test(description = "TC_Search_007: Validate searching using 'Search Criteria' field",
            groups = {"search", "regression"})
    public void testSearchUsingSearchCriteria() {
        log.info("Starting test: Validate search using 'Search Criteria' field");
        homePage.getSearchBox().performSearch("");
        searchPage.fillSearchCriteriaInputBox(existingValue).clickOnSearchCriteriaSearchButton();

        assertUtils.assertTrue(searchPage.doesProductNameContain(existingValue),
                "Search results should contain the product name: '" + existingValue + "'");

        int productCount = searchPage.getProductCount();
        log.info("Total products found for '{}': {}", existingValue, productCount);
        assertUtils.assertTrue(productCount > 0, "Product count should be greater than 0 for: " + existingValue);
        log.info("✅ Search using 'Search Criteria' validated successfully");
    }

    @Test(description = "TC_Search_013: Validate navigating to Product Compare Page from Search Results page",
            groups = {"search", "navigation", "regression"})
    public void testValidateNavigateToProductComparePage() {
        log.info("Starting test: Validate navigation to Product Compare page from Search Results");
        homePage.getSearchBox().performSearch("");
        ProductComparePage productComparePage = searchPage.clickOnProductCompareLink();
        assertUtils.assertEquals(productComparePage.getProductComparePageHeaderText(),
                TestData.PRODUCT_COMPARE_PAGE_HEADER);
        log.info("✅ Navigation to Product Compare page validated successfully");
    }

    @Test(description = "TC_Search_014: Validate the User is able to sort the Products displayed in the Search Results",
            groups = {"search", "sorting", "regression"})
    public void testValidateSortSearchedProduct() {
        log.info("Starting test: Validate sorting of searched products by name and price");
        homePage.getSearchBox().performSearch(searchCriteriaValue);
        searchPage.selectSortOption("Name (A - Z)");
        List<String> actualNames = searchPage.getProductNames();
        List<String> expectedNames = new ArrayList<>(actualNames);
        expectedNames.sort(String.CASE_INSENSITIVE_ORDER);
        assertUtils.assertEquals(actualNames, expectedNames);
        log.info("✅ Products sorted correctly by name (A - Z)");

        int productCountByName = searchPage.getProductCount();
        log.info("Product count (after Name sort): {}", productCountByName);
        assertUtils.assertTrue(productCountByName > 1, "Expected multiple products for sorting check");

        homePage.getSearchBox().performSearch(searchCriteriaValue);
        searchPage.selectSortOption("Price (Low > High)");
        List<Double> actualPrices = searchPage.getProductPrices();
        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedPrices);
        assertUtils.assertEquals(actualPrices, expectedPrices);
        log.info("✅ Products sorted correctly by price (Low > High)");

        int productCountByPrice = searchPage.getProductCount();
        log.info("Product count (after Price sort): {}", productCountByPrice);
        assertUtils.assertTrue(productCountByPrice > 1, "Expected multiple products for sorting check");
        log.info("✅ Sorting functionality validated successfully");
    }

    @Test(description = "TC_Search_015: Validate the User can select how many products can be displayed in the Search Results",
            groups = {"search", "pagination", "regression"})
    public void testValidateProductDisplayLimitOption() {
        log.info("Starting test: Validate changing product display limit in search results");
        homePage.getSearchBox().performSearch("apple");
        searchPage.selectShowOption("25");
        int count = searchPage.getProductCount();
        assertUtils.assertTrue(count > 15, "Product count should be more than 15 after selecting 'Show 25'");
        log.info("✅ Product display limit selection validated successfully ({} products shown)", count);
    }
}