package com.example.pages;

import com.example.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * GoogleHomePage - Page Object for Google search
 * Demonstrates handling search functionality and dynamic results
 */
public class GoogleHomePage extends BasePage {
    
    // Page URL
    private static final String PAGE_URL = "https://www.google.com";
    
    // Locators
    private final By searchBox = By.name("q");
    private final By searchButton = By.name("btnK");
    private final By searchResults = By.cssSelector("div.g");
    private final By consentButton = By.xpath("//button[contains(., 'Accept all') or contains(., 'I agree')]");
    
    // Constructor
    public GoogleHomePage(WebDriver driver) {
        super(driver);
    }
    
    // ============ Page Actions ============
    
    /**
     * Navigate to Google homepage
     */
    public GoogleHomePage open() {
        navigateTo(PAGE_URL);
        handleConsent();
        return this;
    }
    
    /**
     * Handle cookie consent popup if present
     */
    private void handleConsent() {
        try {
            if (isDisplayed(consentButton)) {
                click(consentButton);
                waitSeconds(1);
            }
        } catch (Exception e) {
            // Consent already handled or not present
        }
    }
    
    /**
     * Perform search
     */
    public GoogleHomePage search(String query) {
        type(searchBox, query);
        // Submit form instead of clicking button (more reliable)
        find(searchBox).submit();
        waitForSearchResults();
        return this;
    }
    
    /**
     * Wait for search results to load
     */
    private void waitForSearchResults() {
        waitForElementToBeVisible(searchResults);
    }
    
    // ============ Verification Methods ============
    
    /**
     * Get all search result elements
     */
    public List<WebElement> getSearchResults() {
        return findAll(searchResults);
    }
    
    /**
     * Get count of search results
     */
    public int getSearchResultCount() {
        return getSearchResults().size();
    }
    
    /**
     * Check if search results are displayed
     */
    public boolean hasSearchResults() {
        return getSearchResultCount() > 0;
    }
    
    /**
     * Check if on Google homepage
     */
    public boolean isOnGooglePage() {
        return getCurrentUrl().contains("google.com");
    }
}
