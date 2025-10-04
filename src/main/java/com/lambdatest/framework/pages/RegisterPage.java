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

    @FindBy(css = "label[for='input-agree']")
    private WebElement privacyPolicyAgreeCheckbox;

    @FindBy(css = "input[value='Continue']")
    private WebElement continueButton;

    @FindBy(xpath = "//div[contains(text(),'First Name must be between 1 and 32 characters!')]")
    private WebElement firstNameEmptyFieldErrorMessage;

    @FindBy(xpath = "//div[contains(text(),'Last Name must be between 1 and 32 characters!')]")
    private WebElement lastNameEmptyFieldErrorMessage;

    @FindBy(xpath = "//div[contains(text(),'E-Mail Address does not appear to be valid!')]")
    private WebElement emailEmptyFieldErrorMessage;

    @FindBy(xpath = "//div[normalize-space()='Telephone must be between 3 and 32 characters!']")
    private WebElement telephoneEmptyFieldErrorMessage;

    @FindBy(xpath = "//div[contains(text(),'Password must be between 4 and 20 characters!')]")
    private WebElement passwordEmptyFieldErrorMessage;

    @FindBy(css = "div[id='account-register'] div[class='alert alert-danger alert-dismissible']")
    private WebElement privacyPolicyWarningMessage;

    @FindBy(css = "label[for$='input-newsletter-yes']")
    private WebElement subscribeYesRadio;

    @FindBy(css = "label[for$='input-newsletter-no']")
    private WebElement subscribeNoRadio;

    @FindBy(xpath = "//div[@class='text-danger']")
    private WebElement confirmPasswordNotMatchErrorMessage;


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

    public AccountCreateSuccessPage validRegister(String firstName, String lastName, String email, String telephoneNo, String password, String confirmPassword, boolean subscribe) {
        log.info("Registering account with subscription: {}", subscribe ? "Yes" : "No");

        enterFirstName(firstName)
                .enterLastName(lastName)
                .enterEmail(email)
                .enterTelephoneNo(telephoneNo)
                .enterPassword(password)
                .enterConfirmPassword(confirmPassword);

        if (subscribe) {
            selectNewsletterYes();
        } else {
            selectNewsletterNo();
        }

        clickPrivacyCheckbox();
        return clickContinueExpectingSuccess();
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

    public String getFirstNameEmptyFieldErrorMessage() {
        log.info("Getting error message for empty First Name field");
        wait.waitForVisibility(firstNameEmptyFieldErrorMessage);
        return actions.getText(firstNameEmptyFieldErrorMessage);
    }

    public String getLastNameEmptyFieldErrorMessage() {
        log.info("Getting error message for empty Last Name field");
        return actions.getText(lastNameEmptyFieldErrorMessage);
    }

    public String getEmailEmptyFieldErrorMessage() {
        log.info("Getting error message for invalid/empty Email field");
        return actions.getText(emailEmptyFieldErrorMessage);
    }

    public String getTelephoneEmptyFieldErrorMessage() {
        log.info("Getting error message for empty Telephone field");
        return actions.getText(telephoneEmptyFieldErrorMessage);
    }

    public String getPasswordEmptyFieldErrorMessage() {
        log.info("Getting error message for empty Password field");
        return actions.getText(passwordEmptyFieldErrorMessage);
    }

    public String getPrivacyPolicyWarningMessage() {
        log.info("Getting error message for Privacy Policy checkbox");
        return actions.getText(privacyPolicyWarningMessage);
    }

    public RegisterPage selectNewsletterYes() {
        log.info("Selecting newsletter: Yes");
        actions.click(subscribeYesRadio);
        return this;
    }

    public RegisterPage selectNewsletterNo() {
        log.info("Selecting newsletter: No");
        actions.click(subscribeNoRadio);
        return this;
    }

    public String getPasswordNoMatchErrorMessageText() {
        log.info("Getting password does not match text under confirm password input box");
        wait.waitForVisibility(confirmPasswordNotMatchErrorMessage);
        return actions.getText(confirmPasswordNotMatchErrorMessage);
    }
}
