package com.lambdatest.framework.pages;

import com.lambdatest.framework.base.BasePage;
import com.lambdatest.framework.utils.ConfigReader;
import com.lambdatest.framework.utils.WaitUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage {

    @FindBy(xpath = "//h2[normalize-space()='My Account']")
    WebElement myAccountTitle;

    public String getMyAccountTitleText() {
        WaitUtils.waitForVisibility(myAccountTitle);
        WaitUtils.waitForTitleContains(ConfigReader.getProperty("myAccountPageTitle"));
        return myAccountTitle.getText();
    }
}
