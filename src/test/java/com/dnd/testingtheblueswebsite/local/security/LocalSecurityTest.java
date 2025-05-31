package com.dnd.testingtheblueswebsite.local.security;


import com.codeborne.selenide.webdriver.WebDriverFactory;
import com.dnd.testingtheblueswebsite.SeleniumTest;
import com.dnd.testingtheblueswebsite.pages.AccountPage;
import com.dnd.testingtheblueswebsite.pages.HomePage;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

@Test(groups = {"local", "Security-Tests"})
public class LocalSecurityTest extends SeleniumTest {
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

    @Test(description = "TC-SEC-001: Verify that the login form is secure against SQL injection attacks")
    public void testLoginFormSQLInjection() {}

    @Test(description = "TC-SEC-002: Verify that the registration form is secure against XSS attacks")
    public void testRegistrationFormXSS() {
    }
}
