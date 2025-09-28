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

    @FindBy(xpath = "//h2[normalize-space()='Returning Customer']")
    private WebElement loginTitle;

    @FindBy(id = "input-email")
    private WebElement emailInputField;

    @FindBy(id = "input-password")
    private WebElement passwordInputField;

    @FindBy(css = "input[value='Login']")
    private WebElement loginButton;

    @FindBy(css = "div#account-login .alert.alert-danger.alert-dismissible")
    private  WebElement wrongCredentialErrorMessage;

    @FindBy(css = "div[class='form-group'] a")
    private WebElement forgetPasswordLink;


    public void goToLoginPage() {
        navigateTo(ConfigReader.getProperty("loginUrl"));
    }

    public String getLoginTitle() {
        return actions.getText(loginTitle);
    }

    public void enterEmail(String email) {
        actions.fillInputBox(emailInputField, email);
    }

    public void enterPassword(String password) {
        actions.fillInputBox(passwordInputField, password);
    }

    public void clickLogin() {
        actions.click(loginButton);
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }

    public String getWrongCredentialErrorText() {
        WaitUtils.waitForVisibility(wrongCredentialErrorMessage);
        return actions.getText(wrongCredentialErrorMessage);
    }

    public void clickForgetPasswordLink() {
        actions.click(forgetPasswordLink);
    }
}