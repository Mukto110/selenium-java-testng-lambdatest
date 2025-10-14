package com.lambdatest.framework.pages.cart;

import com.lambdatest.framework.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ShoppingCartPage extends BasePage {
    public ShoppingCartPage(WebDriver driver){
        super(driver);
    }

    @FindBy(css = "div[id='content'] h1[class='page-title mb-3 h4']")
    private WebElement shoppingCartPageHeader;

    @FindBy(css = "td[class='text-left'] a")
    private WebElement productName;

    public String getShoppingCartPageHeaderText() {
        log.info("Getting shopping cart page header text");
        return actions.getText(shoppingCartPageHeader);
    }

    public String getProductName() {
        log.info("Getting the product name from cart page");
        return actions.getText(productName);
    }
}
