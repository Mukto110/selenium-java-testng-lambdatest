package com.lambdatest.framework.utils;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class AssertUtils {

    private final Logger log;
    private final SoftAssert softAssert;

    // Constructor for normal hard assert usage
    public AssertUtils(Logger log) {
        this.log = log;
        this.softAssert = new SoftAssert();
    }

    // ---------- HARD ASSERTS ---------- //

    public void assertEquals(Object actual, Object expected) {
        log.info("Asserting Equals → Expected: {}, Actual: {}", expected, actual);
        try {
            Assert.assertEquals(actual, expected);
            log.info("✅ Assertion Passed");
        } catch (AssertionError e) {
            log.error("❌ Assertion Failed. Expected: {}, Actual: {}", expected, actual, e);
            throw e;
        }
    }

    public void assertTrue(boolean condition, String message) {
        log.info("Asserting True → {}", message);
        try {
            Assert.assertTrue(condition, message);
            log.info("✅ Assertion Passed");
        } catch (AssertionError e) {
            log.error("❌ Assertion Failed. {}", String.valueOf(e));
            throw e;
        }
    }

    public void assertFalse(boolean condition) {
        log.info("Asserting False → {}", condition);
        try {
            Assert.assertFalse(condition);
            log.info("✅ Assertion Passed");
        } catch (AssertionError e) {
            log.error("❌ Assertion Failed. Condition was true", e);
            throw e;
        }
    }

    // ---------- SOFT ASSERTS ---------- //

    public void softAssertEquals(Object actual, Object expected) {
        log.info("Soft Asserting Equals → Expected: {}, Actual: {}", expected, actual);
        softAssert.assertEquals(actual, expected);
    }

    public void softAssertTrue(boolean condition, String message) {
        log.info("Soft Asserting True → {}", message);
        softAssert.assertTrue(condition, message);
    }

    public void softAssertFalse(boolean condition, String message) {
        log.info("Soft Asserting False → {}", message);
        softAssert.assertFalse(condition, message);
    }

    public void softAssertContains(String actual, String expectedSubstring) {
        log.info("Soft Asserting Contains → '{}' should contain '{}'", actual, expectedSubstring);
        softAssert.assertTrue(actual.contains(expectedSubstring));
    }

    // Call this at the end of a test to aggregate soft assert results
    public void assertAll() {
        log.info("🔍 Verifying all soft assertions...");
        softAssert.assertAll();
    }

    // ---------- ELEMENT ASSERTS ---------- //

    public void assertElementDisplayed(WebElement element) {
        log.info("Asserting Element Displayed → {}", element);
        try {
            Assert.assertTrue(element.isDisplayed());
            log.info("✅ Assertion Passed");
        } catch (AssertionError e) {
            log.error("❌ Assertion Failed. Element not displayed: {}", element, e);
            throw e;
        }
    }

    public void assertElementText(WebElement element, String expected) {
        String actual = element.getText().trim();
        log.info("Asserting Element Text → Expected: '{}', Actual: '{}'", expected, actual);
        try {
            Assert.assertEquals(actual, expected);
            log.info("✅ Assertion Passed");
        } catch (AssertionError e) {
            log.error("❌ Assertion Failed. Expected: '{}', Actual: '{}'", expected, actual, e);
            throw e;
        }
    }

    public void assertElementAttribute(WebElement element, String attribute, String expected) {
        String actual = element.getAttribute(attribute);
        log.info("Asserting Element Attribute → {}='{}', Expected='{}'", attribute, actual, expected);
        try {
            Assert.assertEquals(actual, expected);
            log.info("✅ Assertion Passed");
        } catch (AssertionError e) {
            log.error("❌ Assertion Failed. {}='{}', Expected='{}'", attribute, actual, expected, e);
            throw e;
        }
    }

    public void assertPageTitle(String actualTitle, String expectedTitle) {
        log.info("Asserting Page Title → Expected: '{}', Actual: '{}'", expectedTitle, actualTitle);
        try {
            Assert.assertEquals(actualTitle, expectedTitle);
            log.info("✅ Assertion Passed");
        } catch (AssertionError e) {
            log.error("❌ Assertion Failed. Expected Title: '{}', Actual: '{}'", expectedTitle, actualTitle, e);
            throw e;
        }
    }

    public void assertCurrentUrl(String actualUrl, String expectedUrl) {
        log.info("Asserting Current URL → Expected: '{}', Actual: '{}'", expectedUrl, actualUrl);
        try {
            Assert.assertEquals(actualUrl, expectedUrl);
            log.info("✅ Assertion Passed");
        } catch (AssertionError e) {
            log.error("❌ Assertion Failed. Expected URL: '{}', Actual: '{}'", expectedUrl, actualUrl, e);
            throw e;
        }
    }
}