package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.LoginPage;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * LoginTest - demonstrates POM-based login testing
 * Uses LoginPage page object for clean, maintainable tests
 */
public class LoginTest extends BaseTest {
    
    // Valid credentials for the-internet.herokuapp.com
    private static final String VALID_USERNAME = "tomsmith";
    private static final String VALID_PASSWORD = "SuperSecretPassword!";
    
    @Test
    public void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.open(getBaseUrl())
                 .login(VALID_USERNAME, VALID_PASSWORD);
        
        assertTrue("Login should be successful", loginPage.isLoginSuccessful());
        assertTrue("Success message should be displayed", 
                   loginPage.getFlashMessage().contains("You logged into a secure area!"));
    }
    
    @Test
    public void testLoginWithInvalidUsername() {
        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.open(getBaseUrl())
                 .login("invaliduser", VALID_PASSWORD);
        
        assertFalse("Login should fail", loginPage.isLoginSuccessful());
        assertTrue("Error message should be displayed", loginPage.isErrorMessageDisplayed());
    }
    
    @Test
    public void testLoginWithInvalidPassword() {
        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.open(getBaseUrl())
                 .login(VALID_USERNAME, "wrongpassword");
        
        assertFalse("Login should fail", loginPage.isLoginSuccessful());
        assertTrue("Error message should be displayed", loginPage.isErrorMessageDisplayed());
    }
    
    @Test
    public void testLoginWithEmptyCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.open(getBaseUrl())
                 .login("", "");
        
        assertFalse("Login should fail", loginPage.isLoginSuccessful());
        assertTrue("Error message should be displayed", loginPage.isErrorMessageDisplayed());
    }
    
    @Test
    public void testMultipleLoginAttempts() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open(getBaseUrl());
        
        // Test data: username, password, should succeed
        String[][] testData = {
            {"user1", "pass1", "false"},
            {"user2", "pass2", "false"},
            {VALID_USERNAME, VALID_PASSWORD, "true"}
        };
        
        for (String[] data : testData) {
            String username = data[0];
            String password = data[1];
            boolean shouldSucceed = Boolean.parseBoolean(data[2]);
            
            loginPage.login(username, password);
            
            if (shouldSucceed) {
                assertTrue("Login should succeed for " + username, 
                          loginPage.isLoginSuccessful());
            } else {
                assertFalse("Login should fail for " + username, 
                           loginPage.isLoginSuccessful());
            }
            
            // Return to login page for next iteration if not on login page
            if (!loginPage.isOnLoginPage()) {
                loginPage.open(getBaseUrl());
            }
        }
    }
}
