package com.lambdatest.framework.pages.home;

import com.lambdatest.framework.base.BasePage;
import com.lambdatest.framework.pages.components.CartModal;
import com.lambdatest.framework.pages.components.Header;
import com.lambdatest.framework.pages.components.SearchBox;
import com.lambdatest.framework.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomePage extends BasePage {

    private final Header header;
    private final SearchBox searchBox;
    private final CartModal cartModal;

    public HomePage(WebDriver driver) {
        super(driver);
        this.header = new Header(driver);
        this.searchBox = new SearchBox(driver);
        this.cartModal = new CartModal(driver);
    }



    @FindBy(css = "#entry_218399")
    private WebElement topProductSection;

    @FindBy(css = "#entry_218399 div.product-thumb.image-top a[id^='mz-product-listing-image']")
    private List<WebElement> topProducts;

    @FindBy(css = "button[title='Add to Cart']")
    private List<WebElement> topProductsAddToCartButtons;

    @FindBy(css = "div[id='entry_218398'] h3")
    private WebElement topProductHeader;


    public Header getHeader() {
        return header;
    }

    public SearchBox getSearchBox() {
        return searchBox;
    }

    public CartModal getCartModal() {return cartModal;}

    public HomePage navigateToHomePage() {
        log.info("Navigating to the home page");
        navigateTo(ConfigReader.getProperty("baseUrl"));
        return this;
    }




    public HomePage hoverOnProduct(int index) {
        actions.scrollToElement(topProducts.get(index));
        log.info("Hovering on product number: {}", index);
        actions.hover(topProducts.get(index));
        return this;
    }

    public HomePage clickOnAddToCartButton(int index) {
        log.info("Clicking on add to cart button");
        actions.click(topProductsAddToCartButtons.get(index));
        return this;
    }
}
