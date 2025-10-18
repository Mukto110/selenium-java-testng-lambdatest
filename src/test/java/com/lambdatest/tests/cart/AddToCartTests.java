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
        log.info("Navigating to the Home page before each test...");
        homePage = new HomePage(driver);
        homePage.navigateToHomePage();
        assertUtils.assertTrue(homePage.getHeader().isHomeLogoVisible(), "Homepage logo should be visible");
        log.info("✅ Successfully navigated to Home page");
    }

    @Test(description = "TC_AddToCart_001: Validate adding the product to Cart from 'Product Display' Page",
            groups = {"cart", "regression"})
    public void testAddProductToCartFromProductDisplayPage() {
        log.info("Starting test: Validate adding a product to Cart from Product Display Page...");

        SearchPage searchPage = homePage.getSearchBox().performSearch(TestData.EXISTING_VALUE);
        log.info("Searched for product: " + TestData.EXISTING_VALUE);

        ProductDisplayPage productDisplayPage = searchPage.clickOnProduct(1);
        log.info("Navigated to Product Display Page for the first search result");

        productDisplayPage.clickOnAddToCartButton();
        log.info("Clicked 'Add to Cart' button from Product Display Page");

        assertUtils.assertTrue(productDisplayPage.getCartModal().getModalAddToCartSuccessMessageText()
                .contains("Success: You have added"), "User should see the success message");
        log.info("✅ Product successfully added to cart from Product Display Page");

        ShoppingCartPage shoppingCartPage = productDisplayPage.getCartModal().clickOnShoppingCartLink();
        assertUtils.assertTrue(shoppingCartPage.getShoppingCartPageHeaderText().contains("Shopping Cart"),
                "Shopping cart header should contain the header value: Shopping Cart");
        assertUtils.assertEquals(shoppingCartPage.getProductName(), TestData.EXISTING_VALUE);
        log.info("✅ Verified product presence in Shopping Cart page");
    }

    @Test(description = "TC_AddToCart_003: Validate adding the product to Cart from Search Results Page",
            groups = {"cart", "regression"})
    public void testAddProductToCartFromWishlistPage() {
        log.info("Starting test: Validate adding a product to Cart from Search Results Page...");

        SearchPage searchPage = homePage.getSearchBox().performSearch(TestData.EXISTING_VALUE);
        log.info("Searched for product: " + TestData.EXISTING_VALUE);

        searchPage.hoverOnProduct(1).clickAddToCartByIndex(1);
        log.info("Clicked 'Add to Cart' directly from Search Results Page");

        assertUtils.assertTrue(searchPage.getCartModal().getModalAddToCartSuccessMessageText()
                .contains("Success: You have added"), "User should see the success message in modal");
        log.info("✅ Product successfully added to cart from Search Results Page");

        ShoppingCartPage shoppingCartPage = searchPage.getCartModal().clickOnViewCartButton();
        assertUtils.assertEquals(shoppingCartPage.getProductName(), TestData.EXISTING_VALUE);
        log.info("✅ Verified product presence in Shopping Cart page");
    }

    @Test(description = "TC_AddToCart_004: Validate adding the product to Cart from the 'Top Products' section in homepage",
            groups = {"cart", "regression"})
    public void testAddProductToCartFromTopProductsSection() {
        log.info("Starting test: Validate adding a product to Cart from Top Products section on Homepage...");

        homePage.hoverOnProduct(3).clickOnAddToCartButton(3);
        log.info("Clicked 'Add to Cart' for product index 3 in Top Products section");

        CartDrawer cartDrawer = homePage.getHeader().clickOnCartIcon();
        log.info("Opened Cart Drawer to validate added product");

        assertUtils.assertEquals(cartDrawer.getProductName(), TestData.EXISTING_VALUE);
        log.info("✅ Verified product presence in Cart Drawer");

        ShoppingCartPage shoppingCartPage = cartDrawer.clickOnEditCartButton();
        assertUtils.assertEquals(shoppingCartPage.getProductName(), TestData.EXISTING_VALUE);
        log.info("✅ Verified product presence in Shopping Cart page after navigating from Cart Drawer");
    }
}