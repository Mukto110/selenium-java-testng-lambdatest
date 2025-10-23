package com.lambdatest.framework.base;

import com.lambdatest.framework.utils.AssertUtils;
import com.lambdatest.framework.utils.LoggerHelper;
import com.lambdatest.framework.utils.WaitUtils;
import com.lambdatest.framework.utils.WebDriverFactory;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTest {

    protected WebDriver driver;
    protected Logger log;
    protected AssertUtils assertUtils;
    protected WaitUtils wait;

    @BeforeClass(alwaysRun = true)
    public void preSetUp() {
        log = LoggerHelper.getLogger(this.getClass());
        log.info("==========================================================");
        log.info("Starting test: {}", this.getClass());
    }

    @Parameters({"os" ,"browser"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("Windows 11") String os, @Optional("chrome") String browser, Method method) {
        log.info("------------------------------------------------------------");
        log.info("Starting test: {}", method.getName());
        String browserName = System.getProperty("browser", browser);
        WebDriverFactory.setDriver(browserName);
        driver = WebDriverFactory.getDriver();
        log.info("Running on OS: {} | Browser: {}", os, browserName);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        assertUtils = new AssertUtils(log);
        wait = new WaitUtils(driver, log);
        log.info("Browser launched: {}", browserName);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(Method method) {
        log.info("Closing browser for test: {}", method.getName());
        if (driver != null) {
            WebDriverFactory.closeDriver();
        }
        log.info("------------------------------------------------------------");
    }

    @AfterClass(alwaysRun = true)
    public void postSetUp() {
        log.info("All tests finished for class: {}", this.getClass().getSimpleName());
        log.info("==========================================================");
    }

//    public String captureScreen(String tname) throws IOException {
//        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
//        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
//        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
//        String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
//        File targetFile=new File(targetFilePath);
//        sourceFile.renameTo(targetFile);
//        return targetFilePath;
//    }

    public String captureScreen(String tname) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String screenshotDir = System.getProperty("user.dir") + "\\screenshots\\";
        File directory = new File(screenshotDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String targetFilePath = screenshotDir + tname + "_" + timeStamp + ".png";
        File targetFile = new File(targetFilePath);
        org.openqa.selenium.io.FileHandler.copy(sourceFile, targetFile);
        return targetFilePath;
    }

}
