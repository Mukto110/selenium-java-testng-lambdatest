package com.lambdatest.framework.base;

import com.lambdatest.framework.utils.ElementActions;
import com.lambdatest.framework.utils.LoggerHelper;
import com.lambdatest.framework.utils.WaitUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
    protected WebDriver driver;
    protected ElementActions actions;
    protected Logger log;
    protected WaitUtils wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.log = LoggerHelper.getLogger(this.getClass());
        this.actions = new ElementActions(driver, log);
        this.wait = new WaitUtils(driver, log);
        PageFactory.initElements(driver, this);
    }

    public void navigateTo(String url) {
        log.debug("Navigating to URL: {}", url);
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
