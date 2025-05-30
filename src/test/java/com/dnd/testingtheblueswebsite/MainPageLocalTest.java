package com.dnd.testingtheblueswebsite;

import org.testng.annotations.Test;

import java.util.Objects;

@Test(groups = "local")
public class MainPageLocalTest extends SeleniumTest {

    @Test
    public void testMainPageTitle() {
        driver.get("https://jetbrains.com/");
        String expectedTitle = "JetBrains: Essential tools for software developers and teams";
        String actualTitle = driver.getTitle();
        assert Objects.equals(actualTitle, expectedTitle) : "Expected title: " + expectedTitle + ", but got: " + actualTitle;
    }
}