package com.lambdatest.framework.pages.home;

import com.lambdatest.framework.base.BasePage;
import com.lambdatest.framework.pages.components.CartModal;
import com.lambdatest.framework.pages.components.Navbar;
import com.lambdatest.framework.pages.components.SearchBox;
import com.lambdatest.framework.utils.ConfigReader;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomePage extends BasePage {

    private final Navbar navbar;
    private final SearchBox searchBox;
    private final CartModal cartModal;

    public HomePage(WebDriver driver) {
        super(driver);
        this.navbar = new Navbar(driver);
        this.searchBox = new SearchBox(driver);
        this.cartModal = new CartModal(driver);
    }

    @FindBy(css = "img[alt='Poco Electro']")
    private WebElement homeLogo;

    @FindBy(css = "#entry_218399")
    private WebElement topProductSection;

    @FindBy(css = "#entry_218399 div.product-thumb.image-top a[id^='mz-product-listing-image']")
    private List<WebElement> topProducts;

    @FindBy(css = "div[id='entry_218399'] button[title='Add to Cart']")
    private List<WebElement> topProductsAddToCartButtons;

    @FindBy(css = "div[id='entry_218398'] h3")
    private WebElement topProductHeader;

    public Navbar getNavbar() {
        return navbar;
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

    public boolean isHomeLogoVisible() {
        log.info("Checking if home logo is visible on the homepage");
        return actions.isDisplayed(homeLogo);
    }


    public HomePage hoverOnProduct(int index) {
        log.info("Hovering on product number: {}", index);
        WebElement product = topProducts.get(index);

        actions.scrollToElement(product);
        wait.waitForVisibility(product); // less strict than visibility
        ((JavascriptExecutor) driver).executeScript(
                "const ev = new MouseEvent('mouseover', { bubbles: true }); arguments[0].dispatchEvent(ev);",
                product
        );
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("✅ Hovered on product index {}", index);
        return this;
    }

    public HomePage clickOnAddToCartButton(int index) {
        log.info("Clicking on add to cart button");
        actions.click(topProductsAddToCartButtons.get(index));
        return this;
    }
}
