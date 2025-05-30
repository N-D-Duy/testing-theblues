package com.dnd.testingtheblueswebsite.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage {
    @FindBy(css = "button[aria-label*='Account']")
    public WebElement buttonOpenAccountDetails;

    @FindBy(css = "#menu-item-11475 a")
    public WebElement navigateToAccountPage;

    @FindBy(css = "form[class*='woocommerce-form-login']")
    public WebElement formUsernameEmailAddressPassword;

    public void navigateToAccountPage() {
        navigateToAccountPage.click();
    }
}
