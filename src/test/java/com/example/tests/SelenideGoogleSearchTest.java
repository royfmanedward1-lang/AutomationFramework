package com.example.tests;

import org.junit.Test;

public class SelenideGoogleSearchTest extends SelenideBaseTest {
    /**
     * Google search test using SelenideBaseTest (Selenide manages browser)
     */
    @Test
    public void testGoogleSearch() {
        String[] searchTerms = {"Selenide Java", "JUnit 5", "Allure reporting"};
        GoogleHomePage page = new GoogleHomePage();
        for (String searchTerm : searchTerms) {
            page.open();
            page.search(searchTerm);
            assert page.getTitle().contains(searchTerm.split(" ")[0]);
        }
    }
}
