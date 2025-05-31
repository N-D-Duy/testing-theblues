package com.dnd.testingtheblueswebsite.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditAccountPage {
    @FindBy(css = "#password_current")
    public WebElement inputPasswordCurrent;

    @FindBy(css = "#password_1")
    public WebElement inputNewPassword;

    @FindBy(css = "#password_2")
    public WebElement inputConfirmNewPassword;

    @FindBy(css = "button[name='save_account_details']")
    public WebElement buttonSaveAccountDetails;
}
