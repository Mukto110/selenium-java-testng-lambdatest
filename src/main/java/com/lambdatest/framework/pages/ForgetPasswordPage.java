package com.lambdatest.framework.pages;

import com.lambdatest.framework.base.BasePage;
import com.lambdatest.framework.utils.ElementActions;
import com.lambdatest.framework.utils.LoggerHelper;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ForgetPasswordPage extends BasePage {

    public ForgetPasswordPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".page-title.h3.mb-3")
    private WebElement forgetPasswordPageTitle;


    public String getForgetPasswordPageHeaderText() {
        log.info("Getting forget password page header");
        return actions.getText(forgetPasswordPageTitle);
    }
}
