package com.lambdatest.framework.base;

import com.lambdatest.framework.utils.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class BaseTest {

    protected WebDriver driver;

    @Parameters("browser")
    @BeforeMethod
    public void setUp() {
        String browser = "chrome"; // This will come from config.properties
        WebDriverFactory.setDriver(browser);
        driver = WebDriverFactory.getDriver();
    }

    @AfterMethod
    public void tearDown() {
        WebDriverFactory.closeDriver();
    }
}
