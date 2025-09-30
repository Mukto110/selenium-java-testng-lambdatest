package com.lambdatest.framework.utils;

import com.lambdatest.framework.pages.LoginPage;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.openqa.selenium.WebElement;

public class AssertUtils {

    private static final Logger log = LoggerHelper.getLogger(AssertUtils.class);


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

    public void assertContains(String actual, String expectedSubstring) {
        log.info("Asserting Contains → '{}' should contain '{}'", actual, expectedSubstring);
        try {
            Assert.assertTrue(actual.contains(expectedSubstring));
            log.info("✅ Assertion Passed");
        } catch (AssertionError e) {
            log.error("❌ Assertion Failed. '{}' did not contain '{}'", actual, expectedSubstring, e);
            throw e;
        }
    }

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

    public void assertWrongCredentialError(LoginPage loginPage, String expectedMessage) {
        String actualError = loginPage.getWrongCredentialErrorText();
        assertEquals(actualError, expectedMessage);
    }
}
