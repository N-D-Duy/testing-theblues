package com.dnd.testingtheblueswebsite.remote.ui;

import com.dnd.testingtheblueswebsite.SeleniumTest;
import com.dnd.testingtheblueswebsite.constants.MyConstants;
import com.dnd.testingtheblueswebsite.pages.HomePage;
import com.dnd.testingtheblueswebsite.pages.LoginFormPopup;
import io.qameta.allure.Description;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Objects;

import static org.testng.Assert.*;

@Test(groups = {"BrowserStack", "UI-Tests"})
public class UITest extends SeleniumTest {
    private HomePage homePage;
    @BeforeMethod
    public void setUp() throws Exception {
        super.setUp();
        homePage = new HomePage();
        PageFactory.initElements(driver, homePage);
    }

    @Test(description = "TC-UI-01 - Verify that the account popup appears when the user clicks on the account button and contains the correct elements")
    public void testAccountPopup() {
        homePage.buttonOpenAccountDetails.click();

        LoginFormPopup loginForm = new LoginFormPopup();
        PageFactory.initElements(driver, loginForm);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(loginForm.inputUsername));

        assertTrue(loginForm.inputUsername.isDisplayed());
        assertTrue(loginForm.inputPassword.isDisplayed());
        assertTrue(loginForm.buttonLogin.isDisplayed());
        assertTrue(loginForm.spanRememberMe.isDisplayed());
        assertTrue(loginForm.buttonReyRegisterForm.isDisplayed());
        assertTrue(loginForm.buttonReyForgetForm.isDisplayed());
    }

    @Test(description = "TC-UI-01b - Verify successful login via popup")
    @Description("User logs in via popup. If login successful, form disappears and login state changes.")
    public void testLoginByAccountPopupForm() {
        homePage.buttonOpenAccountDetails.click();

        LoginFormPopup loginForm = new LoginFormPopup();
        PageFactory.initElements(driver, loginForm);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(loginForm.inputUsername));

        loginForm.inputUsername.sendKeys(MyConstants.username);
        loginForm.inputPassword.sendKeys(MyConstants.password);
        loginForm.buttonLogin.click();

        wait.until(ExpectedConditions.invisibilityOf(loginForm.inputUsername));

        homePage.buttonOpenAccountDetails.click();
        boolean loginFormVisibleAgain;
        try {
            loginFormVisibleAgain = loginForm.inputUsername.isDisplayed();
        } catch (Exception e) {
            loginFormVisibleAgain = false;
        }

        assertFalse(loginFormVisibleAgain, "Expected login form to disappear after successful login, but it is still visible");
    }



    @Test(description = "TC-UI-02 - Verify that the user can navigate to the account page from the account popup")
    public void testNavigateToAccountPage() {
        homePage.navigateToAccountPage.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("tai-khoan"));

        String expectedUrl = "https://theblues.com.vn/tai-khoan/";
        String actualUrl = driver.getCurrentUrl();
        assertEquals(actualUrl, expectedUrl, "Expected URL: " + expectedUrl + ", but got: " + actualUrl);
    }
}