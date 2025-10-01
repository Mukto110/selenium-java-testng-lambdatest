package com.lambdatest.framework.pages;

import com.lambdatest.framework.base.BasePage;
import com.lambdatest.framework.data.TestData;
import com.lambdatest.framework.utils.ElementActions;
import com.lambdatest.framework.utils.LoggerHelper;
import com.lambdatest.framework.utils.WaitUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage {

    private static final Logger log = LoggerHelper.getLogger(MyAccountPage.class);
    private final ElementActions actions;

    // Constructor
    public MyAccountPage(WebDriver driver) {
        super(driver);
        this.actions = new ElementActions(driver);
    }

    // Locators
    @FindBy(xpath = "//h2[normalize-space()='My Account']")
    private WebElement myAccountPageHeader;

    // Actions
    public String getMyAccountPageHeaderText() {
        log.info("Validating My Account page title");
        WaitUtils.waitForVisibility(myAccountPageHeader);
        WaitUtils.waitForTitleContains(TestData.MY_ACCOUNT_PAGE_TITLE);
        return actions.getText(myAccountPageHeader);
    }
}
