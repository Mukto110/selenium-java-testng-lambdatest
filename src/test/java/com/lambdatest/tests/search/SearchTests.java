package com.lambdatest.tests.search;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.pages.HomePage;
import com.lambdatest.framework.pages.SearchPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SearchTests extends BaseTest {

    private HomePage homePage;

    @BeforeMethod(alwaysRun = true)
    public void navigateToHomePage() {
        homePage = new HomePage(driver);
        homePage.navigateToHomePage();
    }

    @Test(description = "TC_Search_001: Validate searching with an existing Product Name", groups = {"sanity", "regression"})
    public void testSearchWithAnExistingProduct() {
        String value = "imac";
        assertUtils.assertTrue(homePage.getSearchBox().isSearchInputBoxVisible());
        homePage.getSearchBox().fillSearchInputBox(value);
        SearchPage searchPage = homePage.getSearchBox().clickOnSearchButton();
        assertUtils.assertEquals(searchPage.getSearchPageHeaderText(), TestData.SEARCH_PAGE_HEADER+ " " + value);
        assertUtils.assertTrue(searchPage.doesResultContains(value));
    }
}
