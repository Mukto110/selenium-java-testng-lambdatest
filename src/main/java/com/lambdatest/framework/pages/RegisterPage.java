package com.lambdatest.framework.pages;

import com.lambdatest.framework.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegisterPage extends BasePage {

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//h1[normalize-space()='Register Account']")
    private WebElement registerPageHeader;

    @FindBy(id = "input-firstname")
    private WebElement firstNameInputField;

    @FindBy(id = "input-lastname")
    private WebElement lastNameInputField;

    @FindBy(id = "input-email")
    private WebElement emailInputField;

    @FindBy(id = "input-telephone")
    private WebElement telephoneInputField;

    @FindBy(id = "input-password")
    private WebElement passwordInputField;

    @FindBy(id = "input-confirm")
    private WebElement confirmPasswordInputField;

    @FindBy(id = "input-agree")
    private WebElement privacyPolicyAgreeCheckbox;

    @FindBy(css = "input[value='Continue']")
    private WebElement continueButton;


    public String getRegisterPageHeaderText() {
        log.info("Getting register page header text");
        return actions.getText(registerPageHeader);
    }

    public RegisterPage enterFirstName(String firstName) {
        log.info("Entering first name: {}", firstName);
        actions.fillInputBox(firstNameInputField, firstName);
        return this;
    }

    public RegisterPage enterLastName(String lastName) {
        log.info("Entering last name: {}", lastName);
        actions.fillInputBox(lastNameInputField, lastName);
        return this;
    }

    public RegisterPage enterEmail(String email) {
        log.info("Entering email: {}", email);
        actions.fillInputBox(emailInputField, email);
        return this;
    }

    public RegisterPage enterTelephoneNo(String telephoneNo) {
        log.info("Entering telephone no: {}", telephoneNo);
        actions.fillInputBox(telephoneInputField, telephoneNo);
        return this;
    }

    public RegisterPage enterPassword(String password) {
        log.info("Entering password: [PROTECTED]");
        actions.fillInputBox(passwordInputField, password);
        return this;
    }

    public RegisterPage enterConfirmPassword(String confirmPassword) {
        log.info("Entering confirm password: [PROTECTED]");
        actions.fillInputBox(confirmPasswordInputField, confirmPassword);
        return this;
    }

    public RegisterPage clickPrivacyCheckbox() {
        log.info("Clicking on privacy checkbox");
        actions.click(privacyPolicyAgreeCheckbox);
        return this;
    }

    public AccountCreateSuccessPage clickContinueExpectingSuccess() {
        log.info("Clicking continue button (expecting success)");
        actions.click(continueButton);
        return new AccountCreateSuccessPage(driver);
    }

    public RegisterPage clickContinueExpectingFailure() {
        log.info("Clicking continue button (expecting failure");
        actions.click(continueButton);
        return this;
    }

    public AccountCreateSuccessPage validRegister(String firstName, String lastName, String email, String telephoneNo, String password, String confirmPassword) {
        log.info("Registering account with the mandatory fields:");
        return enterFirstName(firstName)
                .enterLastName(lastName)
                .enterEmail(email)
                .enterTelephoneNo(telephoneNo)
                .enterPassword(password)
                .enterConfirmPassword(confirmPassword)
                .clickPrivacyCheckbox()
                .clickContinueExpectingSuccess();
    }

    public RegisterPage invalidRegister(String firstName, String lastName, String email, String telephoneNo, String password, String confirmPassword) {
        log.info("Registering account with invalid/existing details:");
        return enterFirstName(firstName)
                .enterLastName(lastName)
                .enterEmail(email)
                .enterTelephoneNo(telephoneNo)
                .enterPassword(password)
                .enterConfirmPassword(confirmPassword)
                .clickPrivacyCheckbox()
                .clickContinueExpectingFailure();
    }
}
