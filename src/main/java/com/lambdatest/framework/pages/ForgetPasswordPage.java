package com.lambdatest.framework.pages;

import com.lambdatest.framework.base.BasePage;
import com.lambdatest.framework.utils.ElementActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ForgetPasswordPage extends BasePage {

    private final ElementActions actions;

    public ForgetPasswordPage(WebDriver driver) {
        super(driver);
        this.actions = new ElementActions(driver);
    }

    @FindBy(css = ".page-title.h3.mb-3")
    private WebElement forgetPasswordPageTitle;

    public String getForgetPasswordPageHeaderText() {
        return actions.getText(forgetPasswordPageTitle);
    }
}
