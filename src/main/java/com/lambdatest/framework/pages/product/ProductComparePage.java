package com.lambdatest.framework.pages.product;

import com.lambdatest.framework.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductComparePage extends BasePage {

    public ProductComparePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "div[id='content'] h1")
    private WebElement productCompareHeader;

    public String getProductComparePageHeaderText() {
        log.info("Getting product compare page header text");
        return actions.getText(productCompareHeader);
    }
}
