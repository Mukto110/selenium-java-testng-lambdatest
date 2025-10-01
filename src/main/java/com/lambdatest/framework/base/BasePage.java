package com.lambdatest.framework.base;

import com.lambdatest.framework.utils.LoggerHelper;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
    protected WebDriver driver;
    private static final Logger log = LoggerHelper.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void navigateTo(String url) {
        log.info("Navigating to URL: {}", url);
        driver.get(url);
    }

    public String getPageTitle() {
        log.debug("Getting page title");
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        log.debug("Getting current URL");
        return driver.getCurrentUrl();
    }
}
