package com.lambdatest.framework.pages;

import com.lambdatest.framework.base.BasePage;
import com.lambdatest.framework.utils.ConfigReader;
import com.lambdatest.framework.utils.ElementActions;
import com.lambdatest.framework.utils.LoggerHelper;
import com.lambdatest.framework.utils.WaitUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    private static final Logger log = LoggerHelper.getLogger(HomePage.class);
    private final ElementActions actions;

    public HomePage(WebDriver driver) {
        super(driver);
        this.actions = new ElementActions(driver);
    }

    @FindBy(css = "img[alt='Poco Electro']")
    private WebElement homeLogo;

    @FindBy(xpath = "//body/div[@class='mz-pure-container']/div[@id='container']/header[@class='header']/div[@id='main-navigation']/div[@id='entry_217831']/div[@class='entry-section container d-none d-md-flex flex-row align-items-center']/div[@id='entry_217834']/nav[@class='navbar navbar-expand-sm hoverable navbar-default bg-default horizontal']/div[@id='widget-navbar-217834']/ul[@class='navbar-nav horizontal']/li[6]/a[1]")
    private WebElement myAccountDropdown;

    @FindBy(xpath = "//span[normalize-space()='Login']")
    private WebElement loginOption;

    @FindBy(xpath = "//span[normalize-space()='Logout']")
    private WebElement logoutOption;


    public HomePage navigateToHomePage() {
        log.info("Navigating to the home page");
        navigateTo(ConfigReader.getProperty("baseUrl"));
        return this;
    }

    public HomePage hoverOnMyAccountDropdown() {
        log.info("Hovering on 'My Account' navbar option");
        actions.hover(myAccountDropdown);
        WaitUtils.waitForVisibility(loginOption);
        return this;
    }

    public LoginPage clickLogin() {
        log.info("Clicking on login");
        WaitUtils.waitForVisibility(loginOption);
        actions.click(loginOption);
        return new LoginPage(driver);
    }

    public AccountLogoutPage clickLogout() {
        log.info("Clicking on logout");
        WaitUtils.waitForVisibility(logoutOption);
        actions.click(logoutOption);
        return new AccountLogoutPage(driver);
    }
}