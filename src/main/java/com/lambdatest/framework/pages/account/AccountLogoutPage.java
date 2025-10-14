package com.lambdatest.framework.pages.account;

import com.lambdatest.framework.base.BasePage;
import com.lambdatest.framework.pages.home.HomePage;
import com.lambdatest.framework.pages.components.Sidebar;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountLogoutPage extends BasePage {

    private final Sidebar sidebar;

    public AccountLogoutPage(WebDriver driver) {
        super(driver);
        this.sidebar = new Sidebar(driver, log);
    }

    @FindBy(css = "div[id$='content'] h1[class='page-title my-3']")
    private WebElement accountLogoutPageHeader;

    @FindBy(css = "a[class='btn btn-primary']")
    private WebElement continueButton;

    @FindBy(xpath = "//li[@class='breadcrumb-item active']")
    private WebElement breadcrumb;

    public Sidebar getSidebar() {
        return sidebar;
    }

    public String getAccountLogoutPageHeaderText() {
        log.info("Getting account logout page header text");
        return actions.getText(accountLogoutPageHeader);
    }

    public HomePage clickOnContinueButton() {
        log.info("Clicking on the continue button");
        actions.click(continueButton);
        return new HomePage(driver);
    }

    public String getBreadcrumbText() {
        return actions.getText(breadcrumb);
    }
}
