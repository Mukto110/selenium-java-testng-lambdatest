package com.lambdatest.framework.pages.product;

import com.lambdatest.framework.base.BasePage;
import com.lambdatest.framework.pages.components.CartModal;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductDisplayPage extends BasePage {

    private final CartModal cartModal;

    public ProductDisplayPage(WebDriver driver) {
        super(driver);
        this.cartModal = new CartModal(driver);
    }

    @FindBy(css = "div[id='entry_216842'] button[title='Add to Cart']")
    private WebElement addToCartButton;


    public CartModal getCartModal() {
        return cartModal;
    }

    public ProductDisplayPage clickOnAddToCartButton() {
        log.info("Clicking on add to cart button");
        actions.click(addToCartButton);
        return this;
    }


}
