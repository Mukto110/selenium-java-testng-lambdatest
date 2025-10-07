package com.lambdatest.framework.pages;

import com.lambdatest.framework.base.BasePage;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.pages.components.Sidebar;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage {

    private final Sidebar sidebar;

    public MyAccountPage(WebDriver driver) {
        super(driver);
        this.sidebar = new Sidebar(driver, log);
    }

    @FindBy(xpath = "//h2[normalize-space()='My Account']")
    private WebElement myAccountPageHeader;

    @FindBy(css = "div[id='account-account'] div[class='alert alert-success alert-dismissible']")
    private WebElement passwordUpdateSuccessMessage;

    @FindBy(xpath = "//a[normalize-space()='Newsletter']")
    private WebElement sidebarNewsletterLink;


    public Sidebar getSidebar() {
        return sidebar;
    }

    public String getMyAccountPageHeaderText() {
        log.info("Validating My Account page title");
        wait.waitForTitleContains(TestData.MY_ACCOUNT_PAGE_HEADER);
        return actions.getText(myAccountPageHeader);
    }

    public String getPasswordUpdateSuccessText() {
        log.info("Getting password update success text");
        return actions.getText(passwordUpdateSuccessMessage);
    }

    public NewsletterSubscriptionPage clickOnSidebarNewsletterLink() {
        log.info("Clicking on sidebar newsletter link");
        actions.click(sidebarNewsletterLink);
        return new NewsletterSubscriptionPage(driver);
    }
}
