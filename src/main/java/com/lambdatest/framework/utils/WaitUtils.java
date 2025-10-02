package com.lambdatest.framework.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

public class WaitUtils {
    private final WebDriverWait wait;
    private final Logger log;

    public WaitUtils(WebDriver driver, Logger log) {
        int timeout = Integer.parseInt(ConfigReader.getProperty("timeout"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        this.log = log;
    }

    public void waitForVisibility(WebElement element) {
        try {
            log.debug("Waiting for visibility of element: {}", element);
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            log.error("Timeout waiting for visibility of element: {}", element, e);
            throw e;
        }
    }

    public void waitForClickable(WebElement element) {
        try {
            log.debug("Waiting for element to be clickable: {}", element);
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException e) {
            log.error("Timeout waiting for element to be clickable: {}", element, e);
            throw e;
        }
    }

    public void waitForUrlContains(String fraction) {
        try {
            log.debug("Waiting for URL to contain: {}", fraction);
            wait.until(ExpectedConditions.urlContains(fraction));
        } catch (TimeoutException e) {
            log.error("Timeout waiting for URL to contain: {}", fraction, e);
            throw e;
        }
    }

    public void waitForTitleContains(String title) {
        try {
            log.debug("Waiting for title to contain: {}", title);
            wait.until(ExpectedConditions.titleContains(title));
        } catch (TimeoutException e) {
            log.error("Timeout waiting for title to contain: {}", title, e);
            throw e;
        }
    }
}