package com.dnd.testingtheblueswebsite.local.login;

import com.dnd.testingtheblueswebsite.SeleniumTest;
import com.dnd.testingtheblueswebsite.constants.MyConstants;
import com.dnd.testingtheblueswebsite.pages.AccountPage;
import com.dnd.testingtheblueswebsite.pages.EditAccountPage;
import com.dnd.testingtheblueswebsite.pages.HomePage;
import io.qameta.allure.Description;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Objects;

import static org.testng.Assert.assertFalse;
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

    @Test(description = "TC-Login-00 - Login with valid credentials")
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

    @Test(description = "TC-Login-01 - Login with invalid credentials")
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

    @Test(description = "TC-Login-01b - Login with empty username")
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

    @Test(description = "TC-Login-01c - Login with special characters in credentials")
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

    @Test(description = "TC-Login-01d - Check Length of username and password fields")
    public void testLoginFieldLength() {
        accountPage.inputUsername.sendKeys(MyConstants.longUsername);
        accountPage.inputPassword.sendKeys(MyConstants.longPassword);
        accountPage.buttonLogin.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(accountPage.loginFailureMessage));
        String expectedError = MyConstants.loginFailureMessage;
        String actualError = accountPage.loginFailureMessage.getText();
        assertTrue(actualError.contains(expectedError), "Expected error: " + expectedError + ", but got: " + actualError);
    }

    @Test(description = "TC-Login-01e - Login with redundant spaces in credentials")
    public void testLoginWithRedundantSpaces() {
        accountPage.inputUsername.sendKeys(MyConstants.username);
        accountPage.inputPassword.sendKeys(MyConstants.unTrimmedPassword);
        accountPage.buttonLogin.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(accountPage.loginSuccessMessage));
        String expectedMessage = MyConstants.loginSuccessMessage;
        String actualMessage = accountPage.loginSuccessMessage.getText();
        assertTrue(actualMessage.contains(expectedMessage), "Expected message: " + expectedMessage + ", but got: " + actualMessage);
    }

    @Test(description = "TC-Login-01f - Distinct uppercase and lowercase letters")
    public void testLoginWithDistinctUppercaseAndLowercase() {
        accountPage.inputUsername.sendKeys(MyConstants.username.toUpperCase());
        accountPage.inputPassword.sendKeys(MyConstants.password.toUpperCase());
        accountPage.buttonLogin.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(accountPage.loginFailureMessage));
        String expectedError = MyConstants.loginFailureMessage;
        String actualError = accountPage.loginFailureMessage.getText();
        assertTrue(actualError.contains(expectedError), "Expected error: " + expectedError + ", but got: " + actualError);
    }

    @Test(description = "TC-Login-02 - Change password")
    public void testChangePasswordWithUntrimmedPassword() {
        accountPage.inputUsername.sendKeys(MyConstants.username);
        accountPage.inputPassword.sendKeys(MyConstants.password);
        accountPage.buttonLogin.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(accountPage.editAccountLink));

        accountPage.editAccountLink.click();
        wait.until(ExpectedConditions.urlContains("/edit-account"));
        EditAccountPage editAccountPage = new EditAccountPage();
        PageFactory.initElements(driver, editAccountPage);

        editAccountPage.inputPasswordCurrent.sendKeys(MyConstants.password);
        editAccountPage.inputNewPassword.sendKeys(MyConstants.unTrimmedPassword);
        editAccountPage.inputConfirmNewPassword.sendKeys(MyConstants.unTrimmedPassword);
        editAccountPage.buttonSaveAccountDetails.click();

        wait.until(ExpectedConditions.visibilityOf(accountPage.accountUpdatedMessage));
        String expectedMessage = MyConstants.accountDetailsUpdatedMessage;
        String actualMessage = accountPage.accountUpdatedMessage.getText();
        assertTrue(actualMessage.contains(expectedMessage), "Expected message: " + expectedMessage + ", but got: " + actualMessage);
    }

    @Test(description = "TC-Login-02b - Change password with the same password")
    public void testChangePasswordWithSamePassword() {
        accountPage.inputUsername.sendKeys(MyConstants.username);
        accountPage.inputPassword.sendKeys(MyConstants.password);
        accountPage.buttonLogin.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(accountPage.editAccountLink));

        accountPage.editAccountLink.click();
        wait.until(ExpectedConditions.urlContains("/edit-account"));
        EditAccountPage editAccountPage = new EditAccountPage();
        PageFactory.initElements(driver, editAccountPage);

        editAccountPage.inputPasswordCurrent.sendKeys(MyConstants.password);
        editAccountPage.inputNewPassword.sendKeys(MyConstants.password);
        editAccountPage.inputConfirmNewPassword.sendKeys(MyConstants.password);
        editAccountPage.buttonSaveAccountDetails.click();

        wait.until(ExpectedConditions.visibilityOf(accountPage.accountUpdatedMessage));
        String expectedMessage = MyConstants.accountDetailsUpdatedMessage;
        String actualMessage = accountPage.accountUpdatedMessage.getText();
        assertFalse(
                actualMessage.contains(expectedMessage),
                "Password change should not be allowed with old password, but success message was shown."
        );
    }

    @Test(description = "TC-Login-03 - Remember login when checkbox is ticked")
    public void testRememberLoginWithCheckbox() {
        accountPage.inputUsername.sendKeys(MyConstants.username);
        accountPage.inputPassword.sendKeys(MyConstants.password);
        accountPage.spanRememberMe.click();
        accountPage.buttonLogin.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(accountPage.editAccountLink));

        driver.navigate().refresh();
        boolean stillLoggedIn = accountPage.editAccountLink.isDisplayed();
        assertTrue(stillLoggedIn, "User should stay logged in when 'Remember me' is ticked.");
    }

    @Test(description = "TC-Login-04 - Do not remember login when checkbox is not ticked")
    public void testDoNotRememberLoginWithoutCheckbox() {
        accountPage.inputUsername.sendKeys(MyConstants.username);
        accountPage.inputPassword.sendKeys(MyConstants.password);
        accountPage.buttonLogin.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(accountPage.editAccountLink));

        driver.navigate().refresh();
        boolean stillLoggedIn = accountPage.editAccountLink.isDisplayed();
        assertFalse(stillLoggedIn, "User should not stay logged in without ticking 'Remember me'.");
    }

    @Test(description = "TC-Login-05 - Logout")
    public void testLogout() {
        accountPage.inputUsername.sendKeys(MyConstants.username);
        accountPage.inputPassword.sendKeys(MyConstants.password);
        accountPage.buttonLogin.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(accountPage.editAccountLink));

        accountPage.logoutLink.click();
        wait.until(ExpectedConditions.visibilityOf(accountPage.inputUsername));

        boolean isLoggedOut = accountPage.inputUsername.isDisplayed();
        assertTrue(isLoggedOut, "User should be logged out and login form should be displayed.");
    }

    @Test(description = "TC-Login-06 - Navigate to forgot password page")
    public void testNavigateToForgotPasswordPage() {
        accountPage.forgotPasswordLink.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("/lost-password"));
        String currentUrl = driver.getCurrentUrl();
        assertTrue(Objects.requireNonNull(currentUrl).contains("/lost-password"), "Expected to be on the forgot password page, but was on: " + currentUrl);
    }
}
