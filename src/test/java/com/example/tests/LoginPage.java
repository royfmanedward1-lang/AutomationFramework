package com.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {
    private final By usernameInput = By.id("username");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By successMessage = By.cssSelector(".flash.success, .flash.info");
    private final By errorMessage = By.cssSelector(".flash.error");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get("https://the-internet.herokuapp.com/login");
    }

    public void setUsername(String username) {
        driver.findElement(usernameInput).clear();
        driver.findElement(usernameInput).sendKeys(username);
    }

    public void setPassword(String password) {
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void submit() {
        driver.findElement(loginButton).click();
    }

    public boolean isSuccessMessagePresent() {
        return !driver.findElements(successMessage).isEmpty() && driver.findElement(successMessage).isDisplayed();
    }

    public boolean isErrorMessagePresent() {
        return !driver.findElements(errorMessage).isEmpty() && driver.findElement(errorMessage).isDisplayed();
    }
}
