package com.example.tests;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
// import com.example.DataObject; // Removed because class does not exist
import io.github.bonigarcia.wdm.WebDriverManager;


public class SeleniumTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/WebDriver/bin/chromedriver.exe");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }
    /**
     * Multi-array username and password test
     */
    @Test
    public void testMultipleUserLogin() {
        String[][] credentials = {
            {"user1", "pass1"},
            {"user2", "pass2"},
            {"user3", "pass3"}
        };
        for (String[] cred : credentials) {
            driver.get("http://example.com/login");
            WebElement usernameField = driver.findElement(By.id("username"));
            usernameField.clear();
            usernameField.sendKeys(cred[0]);
            WebElement passwordField = driver.findElement(By.id("password"));
            passwordField.clear();
            passwordField.sendKeys(cred[1]);
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}