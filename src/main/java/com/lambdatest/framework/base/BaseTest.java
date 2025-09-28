package com.lambdatest.framework.base;

import com.lambdatest.framework.utils.LoggerHelper;
import com.lambdatest.framework.utils.WebDriverFactory;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.lang.reflect.Method;

public class BaseTest {

    protected WebDriver driver;
    private static final Logger log = LoggerHelper.getLogger(BaseTest.class);

    public WebDriver getDriver() {
        return driver;
    }

    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser, Method method) {
        log.info("====================================================");
        log.info("Starting test: {}", method.getName());
        String browserName = System.getProperty("browser", "chrome");
        WebDriverFactory.setDriver(browserName);
        driver = WebDriverFactory.getDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        log.info("Browser launched: {}", browserName);
    }

    @AfterMethod
    public void tearDown(Method method) {
        log.info("Closing browser for test: {}", method.getName());
        WebDriverFactory.closeDriver();
        log.info("====================================================");
    }
}
