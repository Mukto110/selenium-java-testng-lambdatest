package com.lambdatest.framework.base;

import com.lambdatest.framework.utils.AssertUtils;
import com.lambdatest.framework.utils.LoggerHelper;
import com.lambdatest.framework.utils.WaitUtils;
import com.lambdatest.framework.utils.WebDriverFactory;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.lang.reflect.Method;

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
    public void setUp(@Optional("chrome") String os, String browser, Method method) {
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
        log.info("Closing browser for test: {}", this.getClass());
        log.info("==========================================================");
    }
}
