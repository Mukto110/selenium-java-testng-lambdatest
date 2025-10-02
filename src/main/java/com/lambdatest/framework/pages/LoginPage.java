package com.lambdatest.framework.pages;

import com.lambdatest.framework.base.BasePage;
import com.lambdatest.framework.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

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
        log.info("Navigating to Login Page");
        navigateTo(ConfigReader.getProperty("loginUrl"));
        return this;
    }

    public String getLoginFormHeaderText() {
        log.info("Getting login form header text");
        return actions.getText(loginFormHeader);
    }

    public LoginPage enterEmail(String email) {
        log.info("Entering email: {}", email);
        actions.fillInputBox(emailInputField, email);
        return this;
    }

    public LoginPage enterPassword(String password) {
        log.info("Entering password [PROTECTED]");
        actions.fillInputBox(passwordInputField, password);
        return this;
    }

    public LoginPage clickLoginExpectingFailure() {
        log.info("Clicking login button (expecting failure)");
        actions.click(loginButton);
        return this;
    }

    public MyAccountPage clickLoginExpectingSuccess() {
        log.info("Clicking login button (expecting success)");
        actions.click(loginButton);
        return new MyAccountPage(driver);
    }

    public MyAccountPage loginAsValidUser(String email, String password) {
        log.info("Logging in with valid credentials: {}", email);
        return enterEmail(email)
                .enterPassword(password)
                .clickLoginExpectingSuccess();
    }

    public LoginPage loginAsInvalidUser(String email, String password) {
        log.info("Logging in with invalid credentials: {}", email);
        return enterEmail(email)
                .enterPassword(password)
                .clickLoginExpectingFailure();
    }

    public String getWrongCredentialErrorText() {
        log.info("Getting wrong credential error message text");
        wait.waitForVisibility(wrongCredentialErrorMessage);
        return actions.getText(wrongCredentialErrorMessage);
    }

    public ForgetPasswordPage clickForgetPasswordLink() {
        log.info("Clicking Forget Password link");
        actions.click(forgetPasswordLink);
        return new ForgetPasswordPage(driver);
    }

    public String getEmailFieldPlaceholder() {
        log.info("Getting email field placeholder");
        return actions.getAttribute(emailInputField, "placeholder");
    }

    public String getPasswordFieldPlaceholder() {
        log.info("Getting password field placeholder");
        return passwordInputField.getAttribute("placeholder");
    }
}
