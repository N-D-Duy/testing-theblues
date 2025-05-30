package com.dnd.testingtheblueswebsite.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginFormPopup {
    @FindBy(css = "#username")
    public WebElement inputUsername;

    @FindBy(css = "#password")
    public WebElement inputPassword;

    @FindBy(css = "span[class='rey-label-text']")
    public WebElement spanRememberMe;

    @FindBy(css = "button[name='login']")
    public WebElement buttonLogin;

    @FindBy(xpath = "/html/body/div[4]/div/div[2]/div[1]/form/div[3]/button[1]")
    public WebElement buttonReyRegisterForm;

    @FindBy(xpath = "/html/body/div[4]/div/div[2]/div[1]/form/div[3]/button[2]")
    public WebElement buttonReyForgetForm;
}
