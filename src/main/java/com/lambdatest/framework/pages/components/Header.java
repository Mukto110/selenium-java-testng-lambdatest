package com.lambdatest.framework.pages.components;

import com.lambdatest.framework.base.BasePage;
import com.lambdatest.framework.pages.account.AccountLogoutPage;
import com.lambdatest.framework.pages.account.LoginPage;
import com.lambdatest.framework.pages.account.RegisterPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Header extends BasePage {

    public Header(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "img[alt='Poco Electro']")
    private WebElement homeLogo;

    @FindBy(css = "a.nav-link.dropdown-toggle[href*='route=account/account']")
    private WebElement myAccountDropdown;

    @FindBy(xpath = "//span[normalize-space()='Login']")
    private WebElement loginOption;

    @FindBy(xpath = "//span[normalize-space()='Logout']")
    private WebElement logoutOption;

    @FindBy(xpath = "//span[normalize-space()='Register']")
    private WebElement registerOption;

    @FindBy(css = "div[id='entry_217825'] a[role='button']")
    private WebElement cartIcon;

    public boolean isHomeLogoVisible() {
        log.info("Checking if home logo is visible on the homepage");
        return actions.isDisplayed(homeLogo);
    }

    public Header hoverOnMyAccountDropdown() {
        log.info("Hovering on 'My Account' navbar option");
        actions.hover(myAccountDropdown);
        return this;
    }

    public LoginPage clickLogin() {
        log.info("Clicking on login");
        hoverOnMyAccountDropdown().actions.click(loginOption);
        return new LoginPage(driver);
    }

    public RegisterPage clickRegister() {
        log.info("Clicking on Register");
        hoverOnMyAccountDropdown().actions.click(registerOption);
        return new RegisterPage(driver);
    }

    public AccountLogoutPage clickLogout() {
        log.info("Clicking on logout");
        hoverOnMyAccountDropdown().actions.click(logoutOption);
        return new AccountLogoutPage(driver);
    }

    public boolean isLogoutOptionVisible() {
        log.info("Checking if logout option is visible or not");
        return actions.isDisplayed(logoutOption);
    }

    public CartDrawer clickOnCartIcon() {
        actions.scrollToTop();
        log.info("Clicking on cart icon");
        actions.click(cartIcon);
        return new CartDrawer(driver);
    }
}
