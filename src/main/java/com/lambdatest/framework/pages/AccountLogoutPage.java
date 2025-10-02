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

    public AccountLogoutPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "a[class='btn btn-primary']")
    private WebElement continueButton;

    @FindBy(xpath = "//a[normalize-space()='My Account']")
    private WebElement sideBarMyAccountLink;

    public HomePage clickOnContinueButton() {
        WaitUtils.waitForVisibility(continueButton);
        log.info("Clicking on the continue button");
        actions.click(continueButton);
        return new HomePage(driver);
    }

    public LoginPage clickOnSideBarMyAccountLink() {
        WaitUtils.waitForVisibility(sideBarMyAccountLink);
        log.info("Clicking on the 'My Account' link from the sidebar");
        actions.click(sideBarMyAccountLink);
        return new LoginPage(driver);
    }
}
