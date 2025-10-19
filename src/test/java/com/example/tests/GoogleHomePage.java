package com.example.tests;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

public class GoogleHomePage {
    public void open() {
        Selenide.open("https://www.google.com");
    }
    public void search(String query) {
        // Handle overlays: consent, region/language, privacy, etc.
        String[] overlaySelectors = {
            "button[aria-label='Accept all']",
            "button[aria-label='Agree to all']",
            "button[aria-label='I agree']",
            "div[role='none'] button",
            "button[role='button']",
            "#L2AGLb", // Google's 'I agree' button id
            "div[role='dialog'] .YrSbJc", // Google promo dialog buttons
            "div[role='dialog'] [role='button']", // Any button in promo dialog
        };
        for (String selector : overlaySelectors) {
            if (Selenide.$(selector).exists() && Selenide.$(selector).isDisplayed()) {
                // Prefer "Stay signed out" if present
                if (Selenide.$("span.YrSbJc").exists() && Selenide.$("span.YrSbJc").getText().contains("Stay signed out")) {
                    // Click the parent div of the span
                    Selenide.$("span.YrSbJc").parent().click();
                    System.out.println("Clicked promo: Stay signed out (parent div)");
                } else {
                    Selenide.$(selector).click();
                    System.out.println("Clicked overlay: " + selector);
                }
                Selenide.sleep(1000); // Wait for overlay to disappear
            }
        }
        // Handle region/language selector
        if (Selenide.$("form[action*='setprefs']").exists()) {
            Selenide.$("form[action*='setprefs']").submit();
            System.out.println("Submitted region/language selector.");
            Selenide.sleep(1000);
        }
        // Try multiple selectors for the search box, retrying for up to 10 seconds
        SelenideElement searchBox = null;
        String[] selectors = {"input[name='q']", "input[title='Search']", "input[type='text']"};
        long start = System.currentTimeMillis();
        boolean found = false;
        while (System.currentTimeMillis() - start < 10000 && !found) {
            for (String selector : selectors) {
                try {
                    searchBox = Selenide.$(selector)
                        .shouldBe(com.codeborne.selenide.Condition.visible, java.time.Duration.ofSeconds(2))
                        .shouldBe(com.codeborne.selenide.Condition.editable, java.time.Duration.ofSeconds(2));
                    if (searchBox.exists()) {
                        found = true;
                        break;
                    }
                } catch (Exception e) {
                    // Try next selector
                }
            }
            if (!found) {
                Selenide.sleep(500);
            }
        }
        if (searchBox != null && searchBox.exists()) {
            searchBox.setValue(query).pressEnter();
        } else {
            System.out.println("Search box not found after waiting.");
            System.out.println("Page source:\n" + com.codeborne.selenide.WebDriverRunner.source());
        }
    }
    public String getTitle() {
        return Selenide.title();
    }
}
