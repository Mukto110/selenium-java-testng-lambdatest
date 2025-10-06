package com.lambdatest.framework.pages;

import com.lambdatest.framework.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountLogoutPage extends BasePage {

    public AccountLogoutPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "div[id$='content'] h1[class='page-title my-3']")
    private WebElement accountLogoutPageHeader;

    @FindBy(css = "a[class='btn btn-primary']")
    private WebElement continueButton;

    @FindBy(xpath = "//a[normalize-space()='My Account']")
    private WebElement sideBarMyAccountLink;

    public String getAccountLogoutPageHeaderText() {
        log.info("Getting account logout page header text");
        return actions.getText(accountLogoutPageHeader);
    }

    public HomePage clickOnContinueButton() {
        log.info("Clicking on the continue button");
        actions.click(continueButton);
        return new HomePage(driver);
    }

    public LoginPage clickOnSideBarMyAccountLink() {
        log.info("Clicking on the 'My Account' link from the sidebar");
        actions.click(sideBarMyAccountLink);
        return new LoginPage(driver);
    }
}
