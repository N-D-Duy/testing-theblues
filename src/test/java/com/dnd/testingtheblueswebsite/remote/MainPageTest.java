package com.dnd.testingtheblueswebsite.remote;

import com.dnd.testingtheblueswebsite.SeleniumTest;
import org.testng.annotations.Test;

import java.util.Objects;


public class MainPageTest extends SeleniumTest {

    @Test
    public void testMainPageTitle() {
        driver.get("https://jetbrains.com/");
        String expectedTitle = "JetBrains: Essential tools for software developers and teams";
        String actualTitle = driver.getTitle();
        assert Objects.equals(actualTitle, expectedTitle) : "Expected title: " + expectedTitle + ", but got: " + actualTitle;
    }
}
