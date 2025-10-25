package com.example.pages;

import com.example.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * LoginPage - Page Object for the-internet.herokuapp.com/login
 * Demonstrates proper POM encapsulation of page elements and actions
 */
public class LoginPage extends BasePage {
    
    // Page URL
    private static final String PAGE_URL = "/login";
    
    // Locators
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By flashMessage = By.id("flash");
    private final By logoutButton = By.cssSelector("a[href='/logout']");
    
    // Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    // ============ Page Actions ============
    
    /**
     * Navigate to login page
     */
    public LoginPage open(String baseUrl) {
        navigateTo(baseUrl + PAGE_URL);
        return this;
    }
    
    /**
     * Enter username
     */
    public LoginPage enterUsername(String username) {
        type(usernameField, username);
        return this;
    }
    
    /**
     * Enter password
     */
    public LoginPage enterPassword(String password) {
        type(passwordField, password);
        return this;
    }
    
    /**
     * Click login button
     */
    public LoginPage clickLogin() {
        click(loginButton);
        return this;
    }
    
    /**
     * Complete login flow
     */
    public LoginPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        return this;
    }
    
    // ============ Verification Methods ============
    
    /**
     * Get flash message text
     */
    public String getFlashMessage() {
        return getText(flashMessage).trim();
    }
    
    /**
     * Check if login was successful
     */
    public boolean isLoginSuccessful() {
        try {
            return isDisplayed(logoutButton) && 
                   getFlashMessage().contains("You logged into a secure area!");
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        try {
            String message = getFlashMessage();
            return message.contains("Your username is invalid!") || 
                   message.contains("Your password is invalid!");
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if on login page
     */
    public boolean isOnLoginPage() {
        return getCurrentUrl().contains("/login");
    }
}
