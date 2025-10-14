package com.lambdatest.framework.pages.components;

import com.lambdatest.framework.base.BasePage;
import com.lambdatest.framework.pages.cart.ShoppingCartPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartModal extends BasePage {
    public CartModal(WebDriver driver){
        super(driver);
    }

    @FindBy(css = "div[id='notification-box-top'] div[class='d-flex mb-3 align-items-start'] p")
    private WebElement modalAddToCartSuccessMessage;

    @FindBy(css = "div[id='notification-box-top'] div[class='d-flex mb-3 align-items-start'] a[href='https://ecommerce-playground.lambdatest.io/index.php?route=checkout/cart']")
    private WebElement shoppingCartLink;

    public String getModalAddToCartSuccessMessageText() {
        log.info("Getting modal add to cart success message");
        return actions.getText(modalAddToCartSuccessMessage);
    }

    public ShoppingCartPage clickOnShoppingCartLink() {
        log.info("Clicking on shopping cart link from modal");
        actions.click(shoppingCartLink);
        return new ShoppingCartPage(driver);
    }
}
