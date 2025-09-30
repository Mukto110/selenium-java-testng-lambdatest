package com.lambdatest.framework.pages;

import com.lambdatest.framework.base.BasePage;
import com.lambdatest.framework.utils.ConfigReader;
import com.lambdatest.framework.utils.ElementActions;
import com.lambdatest.framework.utils.WaitUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    private final ElementActions actions;

    public LoginPage() {
        super();
        this.actions = new ElementActions(driver);
    }

    // Locators (as PageFactory elements)
    @FindBy(xpath = "//h2[normalize-space()='Returning Customer']")
    private WebElement loginFormHeader;

    @FindBy(id = "input-email")
    private WebElement emailInputField;

    @FindBy(id = "input-password")
    private WebElement passwordInputField;

    @FindBy(css = "input[value='Login']")
    private WebElement loginButton;

    @FindBy(css = "div#account-login .alert.alert-danger.alert-dismissible")
    private WebElement wrongCredentialErrorMessage;

    @FindBy(css = "div.form-group a")
    private WebElement forgetPasswordLink;

    // ---------- Page Actions ---------- //

    public LoginPage goToLoginPage() {
        navigateTo(ConfigReader.getProperty("loginUrl"));
        return this;
    }

    public String getLoginFormHeaderText() {
        return actions.getText(loginFormHeader);
    }

    public LoginPage enterEmail(String email) {
        actions.fillInputBox(emailInputField, email);
        return this;
    }

    public LoginPage enterPassword(String password) {
        actions.fillInputBox(passwordInputField, password);
        return this;
    }

    public LoginPage clickLoginExpectingFailure() {
        actions.click(loginButton);
        return this;
    }

    public MyAccountPage clickLoginExpectingSuccess() {
        actions.click(loginButton);
        return new MyAccountPage();
    }

    public MyAccountPage loginAsValidUser(String email, String password) {
        return enterEmail(email)
                .enterPassword(password)
                .clickLoginExpectingSuccess();
    }

    public LoginPage loginAsInvalidUser(String email, String password) {
        return enterEmail(email)
                .enterPassword(password)
                .clickLoginExpectingFailure();
    }

    public String getWrongCredentialErrorText() {
        WaitUtils.waitForVisibility(wrongCredentialErrorMessage);
        return actions.getText(wrongCredentialErrorMessage);
    }

    public ForgetPasswordPage clickForgetPasswordLink() {
        actions.click(forgetPasswordLink);
        return new ForgetPasswordPage();
    }
}
