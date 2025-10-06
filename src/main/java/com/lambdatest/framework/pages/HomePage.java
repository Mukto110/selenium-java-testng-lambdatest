package com.lambdatest.framework.pages;

import com.lambdatest.framework.base.BasePage;
import com.lambdatest.framework.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {


    public HomePage(WebDriver driver) {
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


    public HomePage navigateToHomePage() {
        log.info("Navigating to the home page");
        navigateTo(ConfigReader.getProperty("baseUrl"));
        return this;
    }

    public HomePage hoverOnMyAccountDropdown() {
        log.info("Hovering on 'My Account' navbar option");
        actions.hover(myAccountDropdown);
        wait.waitForVisibility(loginOption);
        return this;
    }

    public LoginPage clickLogin() {
        log.info("Clicking on login");
        actions.click(loginOption);
        return new LoginPage(driver);
    }

    public RegisterPage clickRegister() {
        log.info("Clicking on Register");
        actions.click(registerOption);
        return new RegisterPage(driver);
    }

    public AccountLogoutPage clickLogout() {
        log.info("Clicking on logout");
        actions.click(logoutOption);
        return new AccountLogoutPage(driver);
    }
}