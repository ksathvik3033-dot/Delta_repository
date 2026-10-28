package com.automation.hooks;

import io.cucumber.java.After;
import org.openqa.selenium.WebDriver;

public class Hooks {

    public static WebDriver driver;

    @After
    public void tearDown() {

        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}

