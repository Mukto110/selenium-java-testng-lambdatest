package com.lambdatest.framework.pages.components;

import com.lambdatest.framework.base.BasePage;
import com.lambdatest.framework.pages.SearchPage;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchBox extends BasePage {

    public SearchBox(WebDriver driver, Logger log){
        super(driver);
    }

    @FindBy(css = "div[id='entry_217822'] input[placeholder='Search For Products']")
    private WebElement searchInputBox;

    @FindBy(xpath = "//button[normalize-space()='Search']")
    private WebElement searchButton;

    public boolean isSearchInputBoxVisible() {
        log.info("Checking is search input box is visible and enable");
        return actions.isDisplayed(searchInputBox) && actions.isEnabled(searchInputBox);
    }

    public SearchBox fillSearchInputBox(String value){
        log.info("Filling search input with value: {}", value);
        actions.fillInputBox(searchInputBox, value);
        return this;
    }

    public SearchPage clickOnSearchButton() {
        log.info("Clicking on search button");
        actions.click(searchButton);
        return new SearchPage(driver);
    }
}
