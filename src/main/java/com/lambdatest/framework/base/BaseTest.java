package com.lambdatest.framework.base;

import com.lambdatest.framework.utils.ConfigReader;
import com.lambdatest.framework.utils.LoggerHelper;
import com.lambdatest.framework.utils.WebDriverFactory;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {

    protected WebDriver driver;
    private static final Logger log = LoggerHelper.getLogger(BaseTest.class);

    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser) {
        log.info("====================================================");
        log.info("Starting test: {}", this.getClass().getSimpleName());
        String browserName = System.getProperty("browser", "chrome");
        WebDriverFactory.setDriver(browserName);
        driver = WebDriverFactory.getDriver();
        driver.manage().window().maximize();
        log.info("Browser launched: {}", browserName);
    }

    @AfterMethod
    public void tearDown() {
        log.info("Closing browser for test: {}", this.getClass().getSimpleName());
        WebDriverFactory.closeDriver();
        log.info("Test finished: {}", this.getClass().getSimpleName());
        log.info("====================================================");
    }
}
