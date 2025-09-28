package com.lambdatest.framework.pages;

import com.lambdatest.framework.base.BasePage;
import com.lambdatest.framework.utils.ElementActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ForgetPasswordPage extends BasePage {

    private final ElementActions actions;

    public ForgetPasswordPage(ElementActions actions) {
        super();
        this.actions = new ElementActions(driver);
    }

    @FindBy(css = ".page-title.h3.mb-3")
    private WebElement forgetPasswordPageHeader;

    public String getForgetPasswordPageHeaderText() {
        return actions.getText(forgetPasswordPageHeader);
    }
}
