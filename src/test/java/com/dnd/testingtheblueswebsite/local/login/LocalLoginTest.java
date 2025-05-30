package com.dnd.testingtheblueswebsite.local.login;

import com.dnd.testingtheblueswebsite.SeleniumTest;
import com.dnd.testingtheblueswebsite.constants.MyConstants;
import com.dnd.testingtheblueswebsite.pages.AccountPage;
import com.dnd.testingtheblueswebsite.pages.HomePage;
import io.qameta.allure.Description;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertTrue;

@Test(groups = {"local", "Login-Tests"})
public class LocalLoginTest extends SeleniumTest {
    private AccountPage accountPage;
    @BeforeMethod
    public void setUp() throws Exception {
        super.setUp();

        HomePage homePage = new HomePage();
        PageFactory.initElements(driver, homePage);

        homePage.navigateToAccountPage();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(homePage.formUsernameEmailAddressPassword));

        accountPage = new AccountPage();
        PageFactory.initElements(driver, accountPage);
    }

    @Test(description = "TC03 - Login with valid credentials")
    public void testLoginWithValidCredentials() {
        accountPage.inputUsername.sendKeys(MyConstants.username);
        accountPage.inputPassword.sendKeys(MyConstants.password);
        accountPage.buttonLogin.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(accountPage.loginSuccessMessage));
        String expectedMessage = MyConstants.loginSuccessMessage;
        String actualMessage = accountPage.loginSuccessMessage.getText();
        assertTrue(actualMessage.contains(expectedMessage), "Expected message: " + expectedMessage + ", but got: " + actualMessage);
    }

    @Test(description = "TC04 - Login with invalid credentials")
    public void testLoginWithInvalidCredentials() {
        accountPage.inputUsername.sendKeys(MyConstants.testUsername);
        accountPage.inputPassword.sendKeys(MyConstants.testPassword);
        accountPage.buttonLogin.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(accountPage.loginFailureMessage));
        String expectedError = MyConstants.loginFailureMessage;
        String actualError = accountPage.loginFailureMessage.getText();
        assertTrue(actualError.contains(expectedError), "Expected error: " + expectedError + ", but got: " + actualError);
    }

    @Test(description = "TC04b - Login with empty username")
    @Description("Verify that client-side validation is not working when username is empty")
    public void testLoginWithEmptyUsername() {
        accountPage.inputUsername.clear();
        accountPage.inputPassword.sendKeys(MyConstants.testPassword);
        accountPage.buttonLogin.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(accountPage.loginFailureMessage));
        String actualError = accountPage.loginFailureMessage.getText();

        assertTrue(actualError.contains(MyConstants.loginFailureMessage),
                "Client-side validation failed: Server error message '" + MyConstants.loginFailureMessage +
                        "' was displayed instead of client-side validation message");
    }

    @Test(description = "TC04c - Login with special characters in credentials")
    public void testLoginWithSpecialCharacters() {
        accountPage.inputUsername.sendKeys(MyConstants.specialEmail);
        accountPage.inputPassword.sendKeys(MyConstants.password);
        accountPage.buttonLogin.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(accountPage.loginFailureMessage));
        String expectedError = MyConstants.loginFailureMessage;
        String actualError = accountPage.loginFailureMessage.getText();
        assertTrue(actualError.contains(expectedError), "Expected error: " + expectedError + ", but got: " + actualError);
    }
}
