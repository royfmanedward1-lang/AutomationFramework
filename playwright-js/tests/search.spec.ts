import { test, expect } from '@playwright/test';
import { HomePage } from '../src/pages/homePage';
import { env } from '../fixtures/env';
import products from '../fixtures/products.json';

test.describe('Search Functionality', () => {
    let homePage: HomePage;

    test.beforeEach(async ({ page }) => {
        homePage = new HomePage(page);
        await homePage.navigate();
    });

    test('should be able to search for a product', async ({ page }) => {
        await homePage.searchProduct(products.searchQueries.valid);
        // Verify search results
        const resultsCount = await page.$$eval('.product-container', items => items.length);
        expect(resultsCount).toBeGreaterThan(0);
    });

    test('should show no results for invalid search', async ({ page }) => {
        await homePage.searchProduct(products.searchQueries.noResults);
        const noResultsAlert = await page.textContent('.alert-warning');
        expect(noResultsAlert).toContain('No results were found');
    });

    test('should display product details in search results', async ({ page }) => {
        await homePage.searchProduct(products.searchQueries.valid);
        // Verify product elements
        await expect(page.locator('h5.product-name').first()).toBeVisible();
        await expect(page.locator('.right-block .content_price').first()).toBeVisible();
        await expect(page.locator('.product_img_link img').first()).toBeVisible();
    });
});