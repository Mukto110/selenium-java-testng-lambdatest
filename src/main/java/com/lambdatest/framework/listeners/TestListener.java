package com.lambdatest.framework.listeners;

import com.lambdatest.framework.base.BaseTest;
import com.lambdatest.framework.utils.LoggerHelper;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestListener implements ITestListener {

    private static final Logger log = LoggerHelper.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext context) {
        log.info("====================================================");
        log.info("Test Suite started: {}", context.getName());
        log.info("====================================================");
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("====================================================");
        log.info("Test Suite finished: {}", context.getName());
        log.info("====================================================");
    }

    @Override
    public void onTestStart(ITestResult result) {
        log.info("Test started: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("Test passed: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("❌ Test FAILED: {}", result.getMethod().getMethodName(), result.getThrowable());

        Object testClass = result.getInstance();
        WebDriver driver = ((BaseTest) testClass).getDriver();

        if (driver != null) {
            try {
                log.info("📸 Capturing screenshot for failed test: {}", result.getMethod().getMethodName());

                // Take screenshot
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

                // Save screenshot with timestamp
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String screenshotDir = "target/screenshots/";
                Files.createDirectories(Paths.get(screenshotDir));
                String screenshotPath = screenshotDir + result.getMethod().getMethodName() + "_" + timestamp + ".png";

                Files.copy(screenshot.toPath(), Paths.get(screenshotPath));

                log.info("✅ Screenshot saved at: {}", screenshotPath);
            } catch (IOException e) {
                log.error("⚠️ Failed to save screenshot for test: {}", result.getMethod().getMethodName(), e);
            }
        } else {
            log.warn("⚠️ WebDriver instance was null. Cannot capture screenshot.");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("⚠️ Test SKIPPED: {}", result.getMethod().getMethodName());

        if (result.getThrowable() != null) {
            log.warn("↪️ Reason: {}", result.getThrowable().getMessage());
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        log.warn("⚠️ Test '{}' FAILED but is within success percentage → continuing.",
                result.getMethod().getMethodName());
    }

}
