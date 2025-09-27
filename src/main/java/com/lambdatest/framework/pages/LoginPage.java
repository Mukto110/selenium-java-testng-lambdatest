package com.lambdatest.framework.pages;

import com.lambdatest.framework.base.BasePage;
import com.lambdatest.framework.utils.ConfigReader;
import com.lambdatest.framework.utils.ElementActions;
import com.lambdatest.framework.utils.LoggerHelper;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    private static final Logger log = LoggerHelper.getLogger(LoginPage.class);
    private final ElementActions actions;

    public LoginPage() {
        super();
        this.actions = new ElementActions(driver, log);
    }

    @FindBy(xpath = "//h2[normalize-space()='Returning Customer']")
    private WebElement loginTitle;

    @FindBy(id = "input-email")
    private WebElement emailInputField;

    @FindBy(id = "input-password")
    private WebElement passwordInputField;

    @FindBy(css = "input[value='Login']")
    private WebElement loginButton;

    @FindBy(xpath = "//div[@class='alert alert-danger alert-dismissible']")
    private  WebElement wrongCredentialErrorMessage;


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
        return wrongCredentialErrorMessage.getText();
    }
}