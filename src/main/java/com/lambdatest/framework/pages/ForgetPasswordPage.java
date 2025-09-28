package com.lambdatest.framework.pages;

import com.lambdatest.framework.base.BasePage;
import com.lambdatest.framework.utils.ElementActions;

public class ForgetPasswordPage extends BasePage {

    private final ElementActions actions;

    public ForgetPasswordPage(ElementActions actions) {
        super();
        this.actions = new ElementActions(driver);
    }
}
