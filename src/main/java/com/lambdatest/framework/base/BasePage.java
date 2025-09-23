package com.lambdatest.framework.base;

import com.lambdatest.framework.utils.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
    protected WebDriver driver;

    public BasePage() {
        this.driver = WebDriverFactory.getDriver();
        PageFactory.initElements(driver, this);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void navigateTo(String url) {
        driver.get(url);
    }
}
