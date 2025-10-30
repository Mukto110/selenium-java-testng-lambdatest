package com.lambdatest.framework.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class WebDriverFactory {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void setDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();

                // check if headless mode should be enabled
                boolean headless = shouldRunHeadless();
                if (headless) {
                    options.addArguments("--headless=new");
                    options.addArguments("--disable-gpu");
                    options.addArguments("--no-sandbox");
                    options.addArguments("--disable-dev-shm-usage");
                    System.out.println("✅ Running Chrome in headless mode (CI or -Dheadless=true)");
                } else {
                    System.out.println("🖥️ Running Chrome in headed mode (local environment)");
                }

                options.addArguments("--window-size=1920,1080");
                driver.set(new ChromeDriver(options));
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver.set(new FirefoxDriver());
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                driver.set(new EdgeDriver());
                break;

            case "safari":
                WebDriverManager.safaridriver().setup();
                driver.set(new SafariDriver());
                break;

            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void closeDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }

    private static boolean shouldRunHeadless() {
        // 1️⃣ If system property is set explicitly
        String headlessProp = System.getProperty("headless");
        if (headlessProp != null) {
            return Boolean.parseBoolean(headlessProp);
        }

        // 2️⃣ Otherwise, check if running inside CI environment
        String ciEnv = System.getenv("CI");
        return ciEnv != null && ciEnv.equalsIgnoreCase("true");
    }
}