package com.example.tests;

import com.codeborne.selenide.Configuration;
import org.junit.After;
import org.junit.Before;
import static com.codeborne.selenide.Selenide.closeWebDriver;

public abstract class SelenideBaseTest {
    @Before
    public void setUp() {
        Configuration.browser = "firefox";
        Configuration.headless = false;
        Configuration.timeout = 10000;
    }

    @After
    public void tearDown() {
        closeWebDriver();
    }
}
