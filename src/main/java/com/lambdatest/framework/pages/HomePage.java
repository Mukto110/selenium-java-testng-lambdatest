package com.lambdatest.framework.pages;

import com.lambdatest.framework.base.BasePage;
import com.lambdatest.framework.utils.ConfigReader;
import com.lambdatest.framework.utils.ElementActions;
import com.lambdatest.framework.utils.WaitUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    private final ElementActions actions;

    public HomePage() {
        super();
        this.actions = new ElementActions(driver);
    }

    @FindBy(xpath = "//body/div[@class='mz-pure-container']/div[@id='container']/header[@class='header']/div[@id='main-navigation']/div[@id='entry_217831']/div[@class='entry-section container d-none d-md-flex flex-row align-items-center']/div[@id='entry_217834']/nav[@class='navbar navbar-expand-sm hoverable navbar-default bg-default horizontal']/div[@id='widget-navbar-217834']/ul[@class='navbar-nav horizontal']/li[6]/a[1]")
    private WebElement myAccountDropdown;

    @FindBy(xpath = "//span[normalize-space()='Login']")
    private WebElement loginOption;


    // ✅ Return HomePage for chaining
    public HomePage navigateToHomePage() {
        navigateTo(ConfigReader.getProperty("baseUrl"));
        return this;
    }

    // ✅ Return HomePage for chaining
    public HomePage hoverOnMyAccountDropdown() {
        actions.hover(myAccountDropdown);
        WaitUtils.waitForVisibility(loginOption);
        return this;
    }

    // ✅ Clicking login should return LoginPage object
    public LoginPage clickLogin() {
        WaitUtils.waitForVisibility(loginOption);
        actions.click(loginOption);
        return new LoginPage();
    }
}