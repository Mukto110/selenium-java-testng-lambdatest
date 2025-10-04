package com.lambdatest.framework.pages;

import com.lambdatest.framework.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewsletterSubscriptionPage extends BasePage {

    public NewsletterSubscriptionPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "div[id$='content'] h1[class='page-title h3 mb-3']")
    private WebElement newsletterPageHeader;

    @FindBy(id = "input-newsletter-yes")
    private WebElement newsletterYesRadio;

    @FindBy(id = "input-newsletter-no")
    private WebElement newsletterNoRadio;

    public String getNewsletterPageHeaderText() {
        log.info("Getting newsletter page header text");
        return actions.getText(newsletterPageHeader);
    }

    public boolean isNewsletterYesSelected() {
        log.debug("Checking if 'Yes' newsletter option is selected");
        return newsletterYesRadio.isSelected();
    }

    public boolean isNewsletterNoSelected() {
        log.debug("Checking if 'No' newsletter option is selected");
        return newsletterNoRadio.isSelected();
    }

    public String getSelectedNewsletterOption() {
        log.debug("Fetching currently selected newsletter option");
        if (newsletterYesRadio.isSelected()) {
            return "Yes";
        } else if (newsletterNoRadio.isSelected()) {
            return "No";
        }
        return "None";
    }
}
