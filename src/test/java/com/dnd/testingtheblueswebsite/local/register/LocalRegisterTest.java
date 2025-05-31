package com.dnd.testingtheblueswebsite.local.register;

import com.dnd.testingtheblueswebsite.SeleniumTest;
import com.dnd.testingtheblueswebsite.constants.MyConstants;
import com.dnd.testingtheblueswebsite.pages.AccountPage;
import com.dnd.testingtheblueswebsite.pages.HomePage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.*;

@Test(groups = {"local", "Register-Tests"})
public class LocalRegisterTest extends SeleniumTest {
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

    @Test(description = "TC10 - Register with valid credentials")
    public void testRegisterWithValidCredentials() {
        accountPage.inputRegisterEmail.sendKeys(MyConstants.validRegisterEmail);
        accountPage.buttonRegister.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(accountPage.loginSuccessMessage));
        assertTrue(accountPage.loginSuccessMessage.isDisplayed(), "Register success message should be displayed");
    }

    @Test(description = "TC11 - Register with invalid email")
    public void testRegisterWithInvalidEmail() {
        accountPage.inputRegisterEmail.sendKeys(MyConstants.invalidFormatEmail);
        accountPage.buttonRegister.click();

        String validationMessage = accountPage.inputRegisterEmail.getAttribute("validationMessage");
        assertNotNull(validationMessage);
        assertFalse(validationMessage.isEmpty(), "Validation message should not be empty");

        Boolean isValid = (Boolean) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].validity.valid;", accountPage.inputRegisterEmail);
        assertNotEquals(isValid, Boolean.TRUE, "Email should be invalid");
    }

    @Test(description = "TC12 - Register with too long email")
    public void testRegisterWithTooLongEmail() {
        accountPage.inputRegisterEmail.sendKeys(MyConstants.longEmail);
        accountPage.buttonRegister.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(accountPage.loginFailureMessage));

        String expectedErrorMessage = MyConstants.tooLongEmailErrMsg;
        String errorMessage = accountPage.loginFailureMessage.getText();

        assertTrue(errorMessage.contains(expectedErrorMessage), "Error message should contain: " + expectedErrorMessage);
    }

    @Test(description = "TC13 - Register with already registered email")
    public void testRegisterWithAlreadyRegisteredEmail() {
        accountPage.inputRegisterEmail.sendKeys(MyConstants.alreadyRegisteredEmail);
        accountPage.buttonRegister.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(accountPage.loginFailureMessage));

        String expectedErrorMessage = MyConstants.emailAlreadyRegisteredErrMsg;
        String errorMessage = accountPage.loginFailureMessage.getText();

        assertTrue(errorMessage.contains(expectedErrorMessage), "Error message should contain: " + expectedErrorMessage);
    }

    @Test(description = "TC14 - Register with empty email")
    public void testRegisterWithEmptyEmail() {
        accountPage.inputRegisterEmail.clear();
        accountPage.buttonRegister.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(accountPage.loginFailureMessage));
        String expectedErrorMessage = MyConstants.registerWithEmptyEmailErrMsg;
        String errorMessage = accountPage.loginFailureMessage.getText();
        assertTrue(errorMessage.contains(expectedErrorMessage), "Error message should contain: " + expectedErrorMessage);
    }
}
