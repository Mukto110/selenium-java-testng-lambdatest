package com.lambdatest.framework.pages;

import com.lambdatest.framework.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    // Locators
    @FindBy(xpath = "//h2[normalize-space()='Returning Customer']")
    WebElement loginTitle;

    @FindBy(id = "input-email")
    WebElement emailInputField;

    @FindBy(id = "input-password")
    WebElement passwordInputField;

    @FindBy(css = "input[value='Login'")
    WebElement loginButton;

    // Action methods
    public String getLoginTitle() {
        return loginTitle.getText();
    }

    public void enterEmail(String email) {
        emailInputField.clear();
        emailInputField.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordInputField.clear();
        passwordInputField.sendKeys(password);
    }

    public void clickLogin() {
        loginButton.click();
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }
}
