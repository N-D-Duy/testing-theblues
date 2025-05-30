package com.dnd.testingtheblueswebsite.remote.ui;

import com.dnd.testingtheblueswebsite.SeleniumTest;
import org.testng.annotations.Test;

import java.util.Objects;

@Test(groups = {"UI-Tests", "BrowserStack"})
public class UITest extends SeleniumTest {
    @Test(priority = 2)
    public void testMainPageTitle() {
        String expectedTitle = "THE BLUES | Blue Exchange";
        String actualTitle = driver.getTitle();
        assert Objects.equals(actualTitle, expectedTitle) : "Expected title: " + expectedTitle + ", but got: " + actualTitle;
    }
}