package com.lambdatest.tests.cart;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.pages.cart.ShoppingCartPage;
import com.lambdatest.framework.pages.components.CartDrawer;
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
        assertUtils.assertTrue(homePage.getHeader().isHomeLogoVisible(), "Homepage logo should be visible");
    }

    @Test(description = "TC_AddToCart_001: Validate adding the product to Cart from 'Product Display' Page",
            groups = {"cart", "regression"})
    public void testAddProductToCartFromProductDisplayPage() {
        SearchPage searchPage = homePage.getSearchBox().performSearch(TestData.EXISTING_VALUE);
        ProductDisplayPage productDisplayPage = searchPage.clickOnProduct(1);
        productDisplayPage.clickOnAddToCartButton();
        assertUtils.assertTrue(productDisplayPage.getCartModal().getModalAddToCartSuccessMessageText()
                .contains("Success: You have added"), "User should see the success message");
        ShoppingCartPage shoppingCartPage = productDisplayPage.getCartModal().clickOnShoppingCartLink();
        assertUtils.assertTrue(shoppingCartPage.getShoppingCartPageHeaderText().contains("Shopping Cart"),
                "Shopping cart header should contain the header value: Shopping Cart");
        assertUtils.assertEquals(shoppingCartPage.getProductName(), TestData.EXISTING_VALUE);
    }

    @Test(description = "TC_AddToCart_003: Validate adding the product to Cart from Search Results Page",
            groups = {"cart", "regression"})
    public void testAddProductToCartFromWishlistPage() {
        SearchPage searchPage = homePage.getSearchBox().performSearch(TestData.EXISTING_VALUE);
        searchPage.hoverOnProduct(1).clickAddToCartByIndex(1);
        assertUtils.assertTrue(searchPage.getCartModal().getModalAddToCartSuccessMessageText()
                .contains("Success: You have added"), "User should see the success message in modal");
        ShoppingCartPage shoppingCartPage = searchPage.getCartModal().clickOnViewCartButton();
        assertUtils.assertEquals(shoppingCartPage.getProductName(), TestData.EXISTING_VALUE);
    }

    @Test(description = "TC_AddToCart_004: Validate adding the product to Cart from the 'Top Products' section in homepage",
            groups = {"cart", "regression"})
    public void testAddProductToCartFromTopProductsSection() {
        homePage.hoverOnProduct(3).clickOnAddToCartButton(3);
        CartDrawer cartDrawer = homePage.getHeader().clickOnCartIcon();
        assertUtils.assertEquals(cartDrawer.getProductName(), TestData.EXISTING_VALUE);
        ShoppingCartPage shoppingCartPage = cartDrawer.clickOnEditCartButton();
        assertUtils.assertEquals(shoppingCartPage.getProductName(), TestData.EXISTING_VALUE);
    }
}