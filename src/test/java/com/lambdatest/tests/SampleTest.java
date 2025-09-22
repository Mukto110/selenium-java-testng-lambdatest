package com.lambdatest.tests;

import com.lambdatest.framework.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SampleTest extends BaseTest {

    @Test
    public void simpleTest() {
        driver.get("https://ecommerce-playground.lambdatest.io/");

        WebElement homeText = driver.findElement(By.xpath("//span[normalize-space()='Home']"));
        System.out.printf(String.valueOf(homeText));
    }
}
