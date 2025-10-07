package com.lambdatest.framework.pages;

import com.lambdatest.framework.base.BasePage;
import com.lambdatest.framework.pages.components.Navbar;
import com.lambdatest.framework.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    private final Navbar navbar;

    public HomePage(WebDriver driver) {
        super(driver);
        this.navbar = new Navbar(driver, log);
    }

    @FindBy(css = "img[alt='Poco Electro']")
    private WebElement homeLogo;

    public Navbar getNavbar() {
        return navbar;
    }

    public HomePage navigateToHomePage() {
        log.info("Navigating to the home page");
        navigateTo(ConfigReader.getProperty("baseUrl"));
        return this;
    }

    public boolean isHomeLogoVisible() {
        log.info("Checking if home logo is visible on the homepage");
        return actions.isDisplayed(homeLogo);
    }
}
