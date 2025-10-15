package com.lambdatest.framework.pages.components;

import com.lambdatest.framework.base.BasePage;
import com.lambdatest.framework.pages.account.AccountLogoutPage;
import com.lambdatest.framework.pages.account.ChangePasswordPage;
import com.lambdatest.framework.pages.account.ForgetPasswordPage;
import com.lambdatest.framework.pages.account.LoginPage;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Sidebar extends BasePage {

    public Sidebar(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//a[normalize-space()='My Account']")
    private WebElement sideBarMyAccountLink;

    @FindBy(xpath = "//a[@class='list-group-item'][normalize-space()='Forgotten Password']")
    private WebElement sidebarForgetPasswordLink;

    @FindBy(xpath = "//a[contains(text(),'Logout')]")
    private WebElement sidebarLogoutLink;

    @FindBy(xpath = "//a[normalize-space()='Password']")
    private WebElement sidebarPasswordLink;

    public LoginPage clickOnSideBarMyAccountLink() {
        log.info("Clicking on the 'My Account' link from the sidebar");
        actions.click(sideBarMyAccountLink);
        return new LoginPage(driver);
    }

    public ForgetPasswordPage clickSidebarForgetPasswordLink() {
        log.info("Clicking on sidebar forget password link");
        actions.click(sidebarForgetPasswordLink);
        return new ForgetPasswordPage(driver);
    }

    public AccountLogoutPage clickOnSidebarLogout() {
        log.info("Clicking on sidebar logout");
        actions.click(sidebarLogoutLink);
        return new AccountLogoutPage(driver);
    }

    public ChangePasswordPage clickOnSidebarPasswordLink() {
        log.info("Clicking on sidebar password link");
        actions.click(sidebarPasswordLink);
        return new ChangePasswordPage(driver);
    }
}
