package com.lambdatest.framework.pages;

import com.lambdatest.framework.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ChangePasswordPage extends BasePage {

    public ChangePasswordPage(WebDriver driver){
        super(driver);
    }

    @FindBy(css = "div[id='content'] h1")
    private WebElement changePasswordPageHeader;

    @FindBy(id = "input-password")
    private WebElement passwordInputField;

    @FindBy(id = "input-confirm")
    private WebElement confirmPasswordInputField;

    @FindBy(css = "input[value='Continue']")
    private WebElement continueButton;



    public String getChangePasswordPageHeaderText() {
        log.info("Getting change password page header text");
        return actions.getText(changePasswordPageHeader);
    }

    public ChangePasswordPage enterPassword(String password) {
        log.info("Entering password to the password field");
        actions.fillInputBox(passwordInputField, password);
        return this;
    }

    public ChangePasswordPage enterConfirmPassword(String confirmPassword) {
        log.info("Entering password to the confirm password field");
        actions.fillInputBox(confirmPasswordInputField, confirmPassword);
        return this;
    }

    public MyAccountPage clickOnContinueButton() {
        log.info("Clicking on continue button");
        actions.click(continueButton);
        return new MyAccountPage(driver);
    }

    public MyAccountPage changePassword(String password, String confirmPassword) {
        return enterPassword(password)
                .enterConfirmPassword(confirmPassword)
                .clickOnContinueButton();
    }
}
