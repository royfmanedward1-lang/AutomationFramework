package com.example.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * BaseTest - foundation for all test classes
 * Handles WebDriver setup, teardown, and browser configuration
 */
public abstract class BaseTest {
    protected WebDriver driver;
    
    // Browser type (can be overridden via system property: -Dbrowser=firefox)
    private static final String BROWSER = System.getProperty("browser", "chrome");
    
    // Headless mode (can be overridden via system property: -Dheadless=false)
    private static final boolean HEADLESS = Boolean.parseBoolean(System.getProperty("headless", "true"));
    
    @Before
    public void setUp() {
        driver = createDriver(BROWSER);
        driver.manage().window().maximize();
    }
    
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    /**
     * Creates WebDriver instance based on browser type
     */
    private WebDriver createDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "firefox":
                return createFirefoxDriver();
            case "chrome":
            default:
                return createChromeDriver();
        }
    }
    
    /**
     * Creates Chrome WebDriver with recommended options
     */
    private WebDriver createChromeDriver() {
        // Check if Chrome is installed
        String[] possibleChromePaths = new String[] {
            "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe",
            "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe"
        };
        boolean chromeExists = false;
        for (String path : possibleChromePaths) {
            if (Files.exists(Paths.get(path))) {
                chromeExists = true;
                break;
            }
        }
        Assume.assumeTrue(
            "Chrome binary not found; skipping tests. Install Chrome or run with -Dbrowser=firefox", 
            chromeExists
        );
        
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        
        if (HEADLESS) {
            options.addArguments("--headless=new");
        }
        
        // Recommended flags for stability
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        
        return new ChromeDriver(options);
    }
    
    /**
     * Creates Firefox WebDriver with recommended options
     */
    private WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        
        if (HEADLESS) {
            options.addArguments("--headless");
        }
        
        return new FirefoxDriver(options);
    }
    
    /**
     * Utility method to get base URL (can be overridden via system property)
     */
    protected String getBaseUrl() {
        return System.getProperty("base.url", "https://the-internet.herokuapp.com");
    }
}
