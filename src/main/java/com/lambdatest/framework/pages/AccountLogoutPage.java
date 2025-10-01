package com.lambdatest.framework.pages;

import com.lambdatest.framework.base.BasePage;
import com.lambdatest.framework.utils.ElementActions;
import com.lambdatest.framework.utils.LoggerHelper;
import com.lambdatest.framework.utils.WaitUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountLogoutPage extends BasePage {

    private static final Logger log = LoggerHelper.getLogger(AccountLogoutPage.class);
    private final ElementActions actions;

    public AccountLogoutPage(WebDriver driver) {
        super(driver);
        this.actions = new ElementActions(driver);
    }

    @FindBy(css = "a[class='btn btn-primary']")
    private WebElement continueButton;

    @FindBy(xpath = "//a[normalize-space()='My Account']")
    private WebElement sideBarMyAccountLink;

    public HomePage clickOnContinueButton() {
        WaitUtils.waitForVisibility(continueButton);
        actions.click(continueButton);
        return new HomePage(driver);
    }

    public LoginPage clickOnSideBarMyAccountLink() {
        WaitUtils.waitForVisibility(sideBarMyAccountLink);
        actions.click(sideBarMyAccountLink);
        return new LoginPage(driver);
    }
}
