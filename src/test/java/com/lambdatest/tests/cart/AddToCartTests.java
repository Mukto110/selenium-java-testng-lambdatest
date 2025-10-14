package com.lambdatest.tests.cart;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.pages.cart.ShoppingCartPage;
import com.lambdatest.framework.pages.home.HomePage;
import com.lambdatest.framework.pages.product.ProductDisplayPage;
import com.lambdatest.framework.pages.search.SearchPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddToCartTests extends BaseTest {

    private HomePage homePage;

    @BeforeMethod(alwaysRun = true)
    public void navigateToHomePage() {
        homePage = new HomePage(driver);
        homePage.navigateToHomePage();
    }

    @Test(description = "TC_AddToCart_001: Validate adding the product to Cart from 'Product Display' Page", groups = {"regression"})
    public void testAddProductToCartFromProductDisplayPage() {
        SearchPage searchPage = homePage.getSearchBox().performSearch(TestData.EXISTING_VALUE);
        ProductDisplayPage productDisplayPage = searchPage.clickOnProduct();
        productDisplayPage.clickOnAddToCartButton();
        assertUtils.assertTrue(productDisplayPage.getCartModal().getModalAddToCartSuccessMessageText().contains("Success: You have added"), "User should see the success message");
        ShoppingCartPage shoppingCartPage = productDisplayPage.getCartModal().clickOnShoppingCartLink();
        assertUtils.assertTrue(shoppingCartPage.getShoppingCartPageHeaderText().contains("Shopping Cart"), "Shopping cart header should contain  the header value: Shopping Cart");
        assertUtils.assertEquals(shoppingCartPage.getProductName(), TestData.EXISTING_VALUE);
    }

    @Test(description = "TC_AddToCart_003: Validate adding the product to Cart from Search Results Page", groups = {"regression"})
    public void testAddProductToCartFromWishlistPage() {
        SearchPage searchPage = homePage.getSearchBox().performSearch(TestData.EXISTING_VALUE);
        searchPage.clickAddToCartByIndex(1);
    }
}
