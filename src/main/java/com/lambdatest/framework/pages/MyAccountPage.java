package com.lambdatest.framework.pages;

import com.lambdatest.framework.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage {

    @FindBy(xpath = "//h2[normalize-space()='My Account']")
    WebElement myAccountTitle;

    public String getMyAccountTitleText() {
        return myAccountTitle.getText();
    }
}
