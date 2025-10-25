package com.lambdatest.framework.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.lambdatest.framework.utils.ExtentReportManager;
import com.lambdatest.framework.utils.WebDriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
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
import java.util.*;

public class TestListener implements ITestListener {

    private static final ExtentReports extent = ExtentReportManager.createInstance();
    private static final ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

    // 🔹 Store logs for each test
    private static final ThreadLocal<List<String>> logMessages = ThreadLocal.withInitial(ArrayList::new);

    @Override
    public void onStart(ITestContext testContext) {
        System.out.println("=== Test Suite Started: " + testContext.getName() + " ===");

        List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if (!includedGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includedGroups.toString());
        }
        org.apache.logging.log4j.core.Logger rootLogger = (org.apache.logging.log4j.core.Logger) LogManager.getRootLogger();
        rootLogger.addAppender(new MemoryAppender("MemoryAppender"));
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        System.out.println("=== Test Suite Finished: " + context.getName() + " ===");

        try {
            // Find the most recent report file from /reports directory
            String reportsDir = System.getProperty("user.dir") + "/reports";
            File dir = new File(reportsDir);
            if (dir.exists()) {
                File[] files = dir.listFiles((d, name) -> name.endsWith(".html"));
                if (files != null && files.length > 0) {
                    File latestReport = Arrays.stream(files).max(Comparator.comparingLong(File::lastModified)).orElse(null);
                    System.out.println("Opening report: " + latestReport.getAbsolutePath());
                    java.awt.Desktop.getDesktop().browse(latestReport.toURI());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        testThread.set(test);
        logMessages.get().clear();
        String[] groups = result.getMethod().getGroups();
        if (groups.length > 0) {
            test.assignCategory(groups);
            test.log(Status.INFO, "Groups: " + String.join(", ", groups));
        }
    }


    @Override
    public void onTestSuccess(ITestResult result) {
        attachLogs();
        testThread.get().log(Status.PASS, "✅ Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = WebDriverFactory.getDriver();
        String screenshotPath = captureScreenshot(driver, result.getMethod().getMethodName());
        testThread.get().log(Status.FAIL, "❌ Test failed: " + result.getThrowable());
        attachLogs();

        if (screenshotPath != null) {
            testThread.get().addScreenCaptureFromPath(screenshotPath);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        attachLogs();
        testThread.get().log(Status.SKIP, "⚠️ Test skipped: " + result.getThrowable());
    }

    private String captureScreenshot(WebDriver driver, String testName) {
        if (driver == null) return null;
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotDir = "target/screenshots/";
        try {
            Files.createDirectories(Paths.get(screenshotDir));
            String screenshotPath = screenshotDir + testName + "_" + timestamp + ".png";
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), Paths.get(screenshotPath));
            return screenshotPath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void attachLogs() {
        List<String> logs = logMessages.get();
        if (logs.isEmpty()) return;
        for (String log : logs) {
            testThread.get().log(Status.INFO, log);
        }
    }

    private static class MemoryAppender extends AbstractAppender {
        protected MemoryAppender(String name) {
            super(name, null, null, false, null);
            start();
        }

        @Override
        public void append(LogEvent event) {
            logMessages.get().add(event.getMessage().getFormattedMessage());
        }
    }
}