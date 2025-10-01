package com.lambdatest.framework.utils;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class ElementActions {
    private static final Logger log = LoggerHelper.getLogger(ElementActions.class);
    private final WebDriver driver;

    public ElementActions(WebDriver driver) {
        this.driver = driver;
    }

    // Friendly description (for debug logs)
    private String shortDescribe(WebElement element) {
        try {
            String tag = element.getTagName();
            String text = element.getText().trim();
            if (text.length() > 20) text = text.substring(0, 20) + "...";
            return "<" + tag + "> " + (text.isEmpty() ? "" : "with text='" + text + "'");
        } catch (Exception e) {
            return element.toString();
        }
    }

    // Full locator (for error logs)
    private String fullDescribe(WebElement element) {
        try {
            String str = element.toString();
            return str.substring(str.indexOf("->") + 3, str.length() - 1);
        } catch (Exception e) {
            return element.toString();
        }
    }

    public void click(WebElement element) {
        try {
            log.debug("Clicking {}", shortDescribe(element));
            element.click();
        } catch (Exception e) {
            log.error("❌ Failed to click element: {}", fullDescribe(element), e);
            throw e;
        }
    }

    public void fillInputBox(WebElement element, String text) {
        try {
            log.debug("Typing '{}' into {}", text, shortDescribe(element));
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            log.error("❌ Failed to type '{}' into element: {}", text, fullDescribe(element), e);
            throw e;
        }
    }

    public String getText(WebElement element) {
        try {
            String text = element.getText().trim();
            log.debug("Retrieved text from {} → '{}'", shortDescribe(element), text);
            return text;
        } catch (Exception e) {
            log.error("❌ Failed to get text from element: {}", fullDescribe(element), e);
            throw e;
        }
    }

    public String getAttribute(WebElement element, String attribute) {
        try {
            String value = element.getAttribute(attribute);
            log.debug("Retrieved attribute '{}' from {} → '{}'", attribute, shortDescribe(element), value);
            return value;
        } catch (Exception e) {
            log.error("❌ Failed to get attribute '{}' from element: {}", attribute, fullDescribe(element), e);
            throw e;
        }
    }

    public boolean isDisplayed(WebElement element) {
        try {
            boolean visible = element.isDisplayed();
            log.debug("Element displayed? {} → {}", shortDescribe(element), visible);
            return visible;
        } catch (NoSuchElementException e) {
            log.warn("❌ Element not found: {}", fullDescribe(element), e);
            return false;
        }
    }

    public void selectByText(WebElement element, String visibleText) {
        try {
            log.debug("Selecting '{}' from {}", visibleText, shortDescribe(element));
            new Select(element).selectByVisibleText(visibleText);
        } catch (Exception e) {
            log.error("❌ Failed to select '{}' from element: {}", visibleText, fullDescribe(element), e);
            throw e;
        }
    }

    public void selectByValue(WebElement element, String value) {
        try {
            log.debug("Selecting value '{}' from {}", value, shortDescribe(element));
            new Select(element).selectByValue(value);
        } catch (Exception e) {
            log.error("❌ Failed to select value '{}' from element: {}", value, fullDescribe(element), e);
            throw e;
        }
    }

    public void selectByIndex(WebElement element, int index) {
        try {
            log.debug("Selecting index '{}' from {}", index, shortDescribe(element));
            new Select(element).selectByIndex(index);
        } catch (Exception e) {
            log.error("❌ Failed to select index '{}' from element: {}", index, fullDescribe(element), e);
            throw e;
        }
    }

    public void hover(WebElement element) {
        try {
            log.debug("Hovering over {}", shortDescribe(element));
            new Actions(driver).moveToElement(element).perform();
        } catch (Exception e) {
            log.error("❌ Failed to hover over element: {}", fullDescribe(element), e);
            throw e;
        }
    }

    public void scrollIntoView(WebElement element) {
        try {
            log.debug("Scrolling into view: {}", shortDescribe(element));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            log.error("❌ Failed to scroll into view: {}", fullDescribe(element), e);
            throw e;
        }
    }

    public void jsClick(WebElement element) {
        try {
            log.debug("Performing JavaScript click on {}", shortDescribe(element));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            log.error("❌ Failed to JS-click element: {}", fullDescribe(element), e);
            throw e;
        }
    }
}
