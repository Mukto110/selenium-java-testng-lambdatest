package com.lambdatest.framework.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {
    private static final int TIMEOUT = Integer.parseInt(ConfigReader.getProperty("timeout"));

    private static WebDriverWait getWait() {
        WebDriver driver = WebDriverFactory.getDriver();
        return new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
    }

    public static void waitForVisibility(WebElement element) {
        getWait().until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForClickable(WebElement element) {
        getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForUrlContains(String fraction) {
        getWait().until(ExpectedConditions.urlContains(fraction));
    }

    public static void waitForTitleContains(String title) {
        getWait().until(ExpectedConditions.titleContains(title));
    }
}
