package com.example.tests;
import org.junit.Test;
import org.openqa.selenium.By;
public class SeleniumTest extends BaseTest {
    /**
     * Test invalid login and assert error message
     */
    @Test
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.setUsername("invalidUser");
        loginPage.setPassword("invalidPass");
        loginPage.submit();
        assert driver.findElement(By.cssSelector(".flash.error")).isDisplayed();
    }

    /**
     * Test valid login and logout
     */
    @Test
    public void testValidLoginAndLogout() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.setUsername("tomsmith");
        loginPage.setPassword("SuperSecretPassword!");
        loginPage.submit();
        assert loginPage.isSuccessMessagePresent();
        // Click logout button
        driver.findElement(By.cssSelector("a.button.secondary.radius")).click();
        assert driver.findElement(By.cssSelector(".flash.success, .flash.info")).isDisplayed();
    }
    /**
     * Multi-array username and password test using Page Object Model
     */
    @Test
    public void testMultipleUserLogin() {
        String[][] credentials = {
            {"tomsmith", "SuperSecretPassword!"}, // valid
            {"user2", "pass2"}, // invalid
            {"user3", "pass3"}  // invalid
        };
        LoginPage loginPage = new LoginPage(driver);
        for (String[] cred : credentials) {
            loginPage.open();
            loginPage.setUsername(cred[0]);
            loginPage.setPassword(cred[1]);
            loginPage.submit();
            // Check for success message only for valid credentials
            if ("tomsmith".equals(cred[0]) && "SuperSecretPassword!".equals(cred[1])) {
                assert loginPage.isSuccessMessagePresent();
            }
        }
    }

    // Setup and teardown are now handled by BaseTest

    // ...existing code...
}