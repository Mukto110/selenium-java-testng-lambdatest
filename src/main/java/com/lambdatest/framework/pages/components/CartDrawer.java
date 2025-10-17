package com.lambdatest.framework.pages.components;

import com.lambdatest.framework.base.BasePage;
import com.lambdatest.framework.pages.cart.ShoppingCartPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartDrawer extends BasePage {
    public CartDrawer(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "a[href='https://ecommerce-playground.lambdatest.io/index.php?route=product/product&product_id=104'][data-toggle='tooltip']")
    private WebElement productName;

    @FindBy(css = "div[id='entry_217850'] a")
    private WebElement editCartButton;

    public String getProductName() {
        log.info("Getting product name in cart drawer");
        return actions.getText(productName);
    }

    public ShoppingCartPage clickOnEditCartButton() {
        log.info("Clicking on edit cart button");
        actions.click(editCartButton);
        return new ShoppingCartPage(driver);
    }
}
