package com.dnd.testingtheblueswebsite.local.ui;

import com.dnd.testingtheblueswebsite.SeleniumTest;
import com.dnd.testingtheblueswebsite.pages.HomePage;
import com.dnd.testingtheblueswebsite.pages.LoginFormPopup;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Test(groups = {"UI-Tests", "local"})
public class LocalUITest extends SeleniumTest {
    private HomePage homePage;
    @BeforeMethod
    public void setUp() throws Exception {
        super.setUp();
        homePage = new HomePage();
        PageFactory.initElements(driver, homePage);
    }

    @Test(description = "TC00 - Verify that the main page title is correct")
    public void testMainPageTitle() {
        String expectedTitle = "THE BLUES | Blue Exchange";
        String actualTitle = driver.getTitle();
        assertEquals(actualTitle, expectedTitle, "Expected title: " + expectedTitle + ", but got: " + actualTitle);
    }

    @Test(description = "TC01 - Verify that the account popup appears when the user clicks on the account button and contains the correct elements")
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

    @Test(description = "TC01b - Verify login attempt via popup does not change login state (bug)")
    public void testLoginByAccountPopupForm() throws InterruptedException {
        homePage.buttonOpenAccountDetails.click();

        LoginFormPopup loginForm = new LoginFormPopup();
        PageFactory.initElements(driver, loginForm);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(loginForm.inputUsername));

        loginForm.inputUsername.sendKeys("samuelclarkvn");
        loginForm.inputPassword.sendKeys("   abc123   ");
        loginForm.buttonLogin.click();

        Thread.sleep(5000);

        homePage.buttonOpenAccountDetails.click();
        wait.until(ExpectedConditions.visibilityOf(loginForm.inputUsername));

        boolean loginFormStillVisible = loginForm.inputUsername.isDisplayed();
        assertTrue(loginFormStillVisible, "Expected login form to remain (indicating user is not logged in), but it disappeared");

        System.out.println("Popup closed but login state did not change. Form reappears => bug confirmed.");
    }


    @Test(description = "TC02 - Verify that the user can navigate to the account page from the account popup")
    public void testNavigateToAccountPage() {
        homePage.navigateToAccountPage.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("tai-khoan"));

        String expectedUrl = "https://theblues.com.vn/tai-khoan/";
        String actualUrl = driver.getCurrentUrl();
        assertEquals(actualUrl, expectedUrl, "Expected URL: " + expectedUrl + ", but got: " + actualUrl);
    }
}
