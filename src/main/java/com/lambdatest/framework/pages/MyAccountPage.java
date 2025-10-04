package com.lambdatest.framework.pages;

import com.lambdatest.framework.base.BasePage;
import com.lambdatest.framework.data.TestData;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage {


    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//h2[normalize-space()='My Account']")
    private WebElement myAccountPageHeader;

    @FindBy(xpath = "//a[contains(text(),'Logout')]")
    private WebElement sidebarLogoutLink;

    @FindBy(xpath = "//a[normalize-space()='Password']")
    private WebElement sidebarPasswordLink;

    @FindBy(css = "div[id='account-account'] div[class='alert alert-success alert-dismissible']")
    private WebElement passwordUpdateSuccessMessage;

    @FindBy(xpath = "//a[normalize-space()='Newsletter']")
    private WebElement sidebarNewsletterLink;



    public String getMyAccountPageHeaderText() {
        log.info("Validating My Account page title");
        wait.waitForVisibility(myAccountPageHeader);
        wait.waitForTitleContains(TestData.MY_ACCOUNT_PAGE_HEADER);
        return actions.getText(myAccountPageHeader);
    }

    public AccountLogoutPage clickOnSidebarLogout() {
        log.info("Clicking on sidebar logout");
        wait.waitForVisibility(sidebarLogoutLink);
        actions.click(sidebarLogoutLink);
        return new AccountLogoutPage(driver);
    }

    public ChangePasswordPage clickOnSidebarPasswordLink() {
        log.info("Clicking on sidebar password link");
        actions.click(sidebarPasswordLink);
        return new ChangePasswordPage(driver);
    }

    public String getPasswordUpdateSuccessText() {
        log.info("Getting password update success text");
        wait.waitForVisibility(passwordUpdateSuccessMessage);
        return actions.getText(passwordUpdateSuccessMessage);
    }

    public NewsletterSubscriptionPage clickOnSidebarNewsletterLink() {
        log.info("Clicking on sidebar newsletter link");
        actions.click(sidebarNewsletterLink);
        return new NewsletterSubscriptionPage(driver);
    }
}
