package com.lambdatest.framework.pages;

import com.lambdatest.framework.base.BasePage;
import com.lambdatest.framework.pages.components.SearchBox;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class SearchPage extends BasePage {

    private final SearchBox searchBox;

    public SearchPage(WebDriver driver){
        super(driver);
        searchBox = new SearchBox(driver, log);
    }

    @FindBy(css = "div[id='entry_212456'] h1[class='h4']")
    private WebElement searchPageHeader;

    @FindBy(css = "div[id='entry_212469'] h4[class='title'] a")
    private List<WebElement> productNames;

    @FindBy(css = "div[id='entry_212469'] span[class='price-new']")
    private List<WebElement> productPrices;

    @FindBy(css = "div[id='entry_212469'] p")
    private WebElement noProductMatchMessage;

    @FindBy(css = "input[placeholder='Keywords']")
    private WebElement searchCriteriaInputBox;

    @FindBy(xpath = "//input[@id='button-search']")
    private WebElement searchCriteriaSearchButton;

    @FindBy(xpath = "//a[normalize-space()='Product Compare (0)']")
    private WebElement productCompareLink;

    @FindBy(id = "input-sort-212464")
    private WebElement sortByDropdown;

    @FindBy(id = "input-limit-212463")
    private WebElement showDropdown;


    public SearchBox getSearchBox() {
        return searchBox;
    }

    public String getSearchPageHeaderText() {
        log.info("Getting search page header text");
        return actions.getText(searchPageHeader);
    }

    public List<String> getProductNames() {
        List<String> names = new ArrayList<>();
        for (WebElement product : productNames)
            names.add(product.getText().trim());
        return names;
    }

    public List<Double> getProductPrices() {
        List<Double> prices = new ArrayList<>();
        for (WebElement price : productPrices) {
            String priceText = price.getText().replace("$", "").trim();
            priceText = priceText.replaceAll("[^0-9.]", "");
            if (!priceText.isEmpty())
                prices.add(Double.parseDouble(priceText));
        }
        return prices;
    }

    public int getProductCount() {
        log.info("Getting total number of products displayed in search results");
        wait.waitForVisibilityOfAllElements(productNames);
        return productNames.size();
    }


    public boolean doesProductNameContain(String expectedValue) {
        log.info("🔍 Validating that search results contain the product name: '{}'", expectedValue);
        wait.waitForVisibilityOfAllElements(productNames);
        for (WebElement productName : productNames) {
            String titleText = actions.getText(productName);
            log.info("Found product title: {}", titleText);
            if (titleText.toLowerCase().contains(expectedValue.toLowerCase())) {
                log.info("✅ Match found with: {}", titleText);
                return true;
            }
        }
        log.warn("❌ No matching product found for: {}", expectedValue);
        return false;
    }

    public void selectSortOption(String visibleText) {
        log.info("Selecting sorting options by text: {}", visibleText);
        actions.selectByText(sortByDropdown ,visibleText);
    }

    public void selectShowOption(String visibleText) {
        log.info("Selecting show options by text: {}", visibleText);
        actions.selectByText(showDropdown ,visibleText);
    }

    public String getNoProductMatchMessage() {
        log.info("Getting no product match text");
        return actions.getText(noProductMatchMessage);
    }

    public String getSearchCriteriaInputBoxPlaceholderText() {
        log.info("Getting search criteria input box placeholder text");
        return actions.getAttribute(searchCriteriaInputBox, "placeholder");
    }

    public SearchPage fillSearchCriteriaInputBox(String value) {
        log.info("Filling search criteria input box with value: {}", value);
        actions.fillInputBox(searchCriteriaInputBox, value);
        return this;
    }

    public SearchPage clickOnSearchCriteriaSearchButton() {
        log.info("Clicking on search criteria box search button");
        actions.click(searchCriteriaSearchButton);
        return this;
    }

    public ProductComparePage clickOnProductCompareLink() {
        log.info("Clicking on product compare link");
        actions.click(productCompareLink);
        return new ProductComparePage(driver);
    }
}