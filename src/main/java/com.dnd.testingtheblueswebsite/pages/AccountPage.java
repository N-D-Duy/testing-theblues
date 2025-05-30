package com.dnd.testingtheblueswebsite.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountPage {
    //Login form elements
    @FindBy(css = "#username")
    public WebElement inputUsername;

    @FindBy(css = "#password")
    public WebElement inputPassword;

    @FindBy(css = "label[class*='woocommerce-form-login'] span")
    public WebElement spanRememberMe;

    @FindBy(css = "button[class*='woocommerce-form-login']")
    public WebElement buttonLogin;

    @FindBy(css = "p[class*='lost'] a")
    public WebElement forgotPasswordLink;

    // Register form elements
    @FindBy(css = "#reg_email")
    public WebElement inputRegisterEmail;

    @FindBy(css = "button[class*='woocommerce-form-register']")
    public WebElement buttonRegister;

    @FindBy(xpath = "/html/body/div[1]/div[2]/div/div/main/article/div/div/div[2]/p[1]")
    public WebElement loginSuccessMessage;

    @FindBy(css = "ul[class='woocommerce-error'] li")
    public WebElement loginFailureMessage;
    


}
