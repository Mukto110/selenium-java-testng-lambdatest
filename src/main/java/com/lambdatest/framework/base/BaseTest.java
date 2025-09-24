package com.lambdatest.framework.base;

import com.lambdatest.framework.utils.ConfigReader;
import com.lambdatest.framework.utils.LoggerHelper;
import com.lambdatest.framework.utils.WebDriverFactory;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class BaseTest {

    protected WebDriver driver;
    private static final Logger log = LoggerHelper.getLogger(BaseTest.class);

    @Parameters("browser")
    @BeforeMethod
    public void setUp() {
        log.info("====================================================");
        log.info("Starting test: {}", this.getClass().getSimpleName());
        WebDriverFactory.setDriver(ConfigReader.getProperty("browser"));
        driver = WebDriverFactory.getDriver();
        log.info("Browser launched: {}", driver);
    }

    @AfterMethod
    public void tearDown() {
        log.info("Closing browser for test: {}", this.getClass().getSimpleName());
        WebDriverFactory.closeDriver();
        log.info("Test finished: {}", this.getClass().getSimpleName());
        log.info("====================================================");
    }
}
