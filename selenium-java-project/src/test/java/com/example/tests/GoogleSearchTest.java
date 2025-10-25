package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.GoogleHomePage;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * GoogleSearchTest - demonstrates POM-based Google search testing
 * Uses GoogleHomePage page object for clean, maintainable tests
 */
public class GoogleSearchTest extends BaseTest {
    
    @Test
    public void testGoogleSearchSelenium() {
        GoogleHomePage googlePage = new GoogleHomePage(driver);
        
        googlePage.open()
                  .search("Selenium WebDriver");
        
        assertTrue("Should be on Google page", googlePage.isOnGooglePage());
        assertTrue("Should have search results", googlePage.hasSearchResults());
        assertTrue("Should have multiple results", googlePage.getSearchResultCount() > 5);
    }
    
    @Test
    public void testGoogleSearchJava() {
        GoogleHomePage googlePage = new GoogleHomePage(driver);
        
        googlePage.open()
                  .search("Java programming");
        
        assertTrue("Should have search results", googlePage.hasSearchResults());
        assertTrue("Page title should contain Java", 
                   googlePage.getTitle().toLowerCase().contains("java"));
    }
    
    @Test
    public void testGoogleHomepageLoads() {
        GoogleHomePage googlePage = new GoogleHomePage(driver);
        
        googlePage.open();
        
        assertTrue("Should be on Google page", googlePage.isOnGooglePage());
        assertEquals("Page title should be Google", "Google", googlePage.getTitle());
    }
    
    @Test
    public void testMultipleSearches() {
        GoogleHomePage googlePage = new GoogleHomePage(driver);
        googlePage.open();
        
        String[] searchQueries = {
            "Selenium WebDriver",
            "JUnit testing",
            "Page Object Model"
        };
        
        for (String query : searchQueries) {
            googlePage.search(query);
            
            assertTrue("Should have results for: " + query, 
                      googlePage.hasSearchResults());
            assertTrue("Should have multiple results for: " + query, 
                      googlePage.getSearchResultCount() > 3);
            
            // Navigate back to Google homepage for next search
            googlePage.open();
        }
    }
}
