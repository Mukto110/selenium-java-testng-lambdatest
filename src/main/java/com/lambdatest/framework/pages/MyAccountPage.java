package com.lambdatest.framework.pages;

import com.lambdatest.framework.base.BasePage;
import com.lambdatest.framework.utils.ConfigReader;
import com.lambdatest.framework.utils.ElementActions;
import com.lambdatest.framework.utils.LoggerHelper;
import com.lambdatest.framework.utils.WaitUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage {

    private static final Logger log = LoggerHelper.getLogger(MyAccountPage.class);
    private final ElementActions actions;

    // Constructor
    public MyAccountPage() {
        super();
        this.actions = new ElementActions(driver, log);
    }

    // Locators
    @FindBy(xpath = "//h2[normalize-space()='My Account']")
    private WebElement myAccountTitle;

    // Actions
    public String getMyAccountTitleText() {
        log.info("Validating My Account page title");
        WaitUtils.waitForVisibility(myAccountTitle);
        WaitUtils.waitForTitleContains(ConfigReader.getProperty("myAccountPageTitle"));
        return actions.getText(myAccountTitle);
    }
}
