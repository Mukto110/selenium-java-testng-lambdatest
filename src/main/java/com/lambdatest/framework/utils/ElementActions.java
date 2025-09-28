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

    // This method is to resolve locator for better logging (Will try to find any better alternative option in future)
    private String describeElement(WebElement element) {
        try {
            String str = element.toString();
            return str.substring(str.indexOf("->") + 3, str.length() - 1);
        } catch (Exception e) {
            return element.toString(); // fallback if parsing fails
        }
    }

    public void click(WebElement element) {
        try {
            log.info("Clicking on element: {}", describeElement(element));
            element.click();
        } catch (Exception e) {
            log.error("❌ Failed to click on element: {}", describeElement(element), e);
            throw e;
        }
    }

    public void fillInputBox(WebElement element, String text) {
        try {
            log.info("Typing '{}' into element: {}", text, describeElement(element));
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            log.error("❌ Failed to type '{}' into element: {}", text, describeElement(element), e);
            throw e;
        }
    }

    public String getText(WebElement element) {
        try {
            String text = element.getText().trim();
            log.info("Retrieved text from element: {} → '{}'", describeElement(element), text);
            return text;
        } catch (Exception e) {
            log.error("❌ Failed to get text from element: {}", describeElement(element), e);
            throw e;
        }
    }

    public String getAttribute(WebElement element, String attribute) {
        try {
            String value = element.getAttribute(attribute);
            log.info("Retrieved attribute '{}' from element: {} → '{}'", attribute, describeElement(element), value);
            return value;
        } catch (Exception e) {
            log.error("❌ Failed to get attribute '{}' from element: {}", attribute, describeElement(element), e);
            throw e;
        }
    }

    public boolean isDisplayed(WebElement element) {
        try {
            boolean visible = element.isDisplayed();
            log.info("Element displayed? {} → {}", describeElement(element), visible);
            return visible;
        } catch (NoSuchElementException e) {
            log.warn("❌ Element not found: {}", describeElement(element), e);
            return false;
        }
    }

    public void selectByText(WebElement element, String visibleText) {
        try {
            log.info("Selecting '{}' from dropdown: {}", visibleText, describeElement(element));
            Select select = new Select(element);
            select.selectByVisibleText(visibleText);
        } catch (Exception e) {
            log.error("❌ Failed to select '{}' from dropdown: {}", visibleText, describeElement(element), e);
            throw e;
        }
    }

    public void selectByValue(WebElement element, String value) {
        try {
            log.info("Selecting value '{}' from dropdown: {}", value, describeElement(element));
            Select select = new Select(element);
            select.selectByValue(value);
        } catch (Exception e) {
            log.error("❌ Failed to select value '{}' from dropdown: {}", value, describeElement(element), e);
            throw e;
        }
    }

    public void selectByIndex(WebElement element, int index) {
        try {
            log.info("Selecting index '{}' from dropdown: {}", index, describeElement(element));
            Select select = new Select(element);
            select.selectByIndex(index);
        } catch (Exception e) {
            log.error("❌ Failed to select index '{}' from dropdown: {}", index, describeElement(element), e);
            throw e;
        }
    }

    public void hover(WebElement element) {
        try {
            log.info("Hovering over element: {}", describeElement(element));
            Actions actions = new Actions(driver);
            actions.moveToElement(element).perform();
        } catch (Exception e) {
            log.error("❌ Failed to hover over element: {}", describeElement(element), e);
            throw e;
        }
    }

    public void scrollIntoView(WebElement element) {
        try {
            log.info("Scrolling into view: {}", describeElement(element));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            log.error("❌ Failed to scroll into view: {}", describeElement(element), e);
            throw e;
        }
    }

    public void jsClick(WebElement element) {
        try {
            log.info("Performing JavaScript click on: {}", describeElement(element));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            log.error("❌ Failed to JS-click element: {}", describeElement(element), e);
            throw e;
        }
    }
}

