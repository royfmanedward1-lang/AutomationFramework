package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.LoginPage;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * SeleniumTest - Legacy test refactored to use POM framework
 * Now uses LoginPage page object for better maintainability
 */
public class SeleniumTest extends BaseTest {
    
    /**
     * Multi-array username and password test - refactored to use POM
     */
    @Test
    public void testMultipleUserLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open(getBaseUrl());
        
        String[][] credentials = {
            {"user1", "pass1"},
            {"user2", "pass2"},
            {"user3", "pass3"}
        };
        
        for (String[] cred : credentials) {
            loginPage.login(cred[0], cred[1]);
            
            // All these credentials are invalid, so verify error
            assertFalse("Login should fail for invalid credentials", 
                       loginPage.isLoginSuccessful());
            
            // Return to login page for next attempt if needed
            if (!loginPage.isOnLoginPage()) {
                loginPage.open(getBaseUrl());
            }
        }
    }
}