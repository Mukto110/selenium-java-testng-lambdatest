package com.lambdatest.framework.pages;

import com.lambdatest.framework.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountCreateSuccessPage extends BasePage {

    public AccountCreateSuccessPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "div[id$='content'] h1[class='page-title my-3']")
    private WebElement accountSuccessHeader;

    @FindBy(css = "a[class='btn btn-primary']")
    private WebElement continueButton;

    public String getAccountSuccessHeaderText() {
        log.info("Getting account success page's header text");
        wait.waitForVisibility(accountSuccessHeader);
        return actions.getText(accountSuccessHeader);
    }

    public MyAccountPage clickContinueButton() {
        log.info("Clicking on continue button");
        actions.click(continueButton);
        return new MyAccountPage(driver);
    }
}
