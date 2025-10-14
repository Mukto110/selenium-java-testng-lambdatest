package com.lambdatest.tests.cart;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.pages.home.HomePage;
import org.testng.annotations.Test;

public class AddToCartTests extends BaseTest {

    @Test(description = "TC_AddToCart_001: Validate adding the product to Cart from 'Product Display' Page", groups = {"regression"})
    public void testAddProductToCartFromProductDisplayPage() {
        HomePage homePage = new HomePage(driver);
        homePage.getSearchBox().performSearch(TestData.EXISTING_VALUE);
    }
}
