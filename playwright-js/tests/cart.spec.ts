import { test, expect } from '@playwright/test';
import { HomePage } from '../src/pages/homePage';
import { ProductPage } from '../src/pages/productPage';
import { CartPage } from '../src/pages/cartPage';
import { env } from '../fixtures/env';

test.describe('Shopping Cart', () => {
    let homePage: HomePage;
    let productPage: ProductPage;
    let cartPage: CartPage;

    test.beforeEach(async ({ page }, testInfo) => {
        // Increase timeout for all tests in this file
        testInfo.setTimeout(120000);
        
        // Configure viewport
        await page.setViewportSize({ width: 1280, height: 720 });
        
        // Initialize pages
        homePage = new HomePage(page);
        productPage = new ProductPage(page);
        cartPage = new CartPage(page);
        
        // Navigate with retry
        try {
            await homePage.navigate();
        } catch (e) {
            console.log('First navigation attempt failed, retrying...');
            await page.waitForTimeout(5000);
            await homePage.navigate();
        }
    });

    test('should add product to cart', async ({ page }, testInfo) => {
        // Retry the entire test up to 2 times
        for (let attempt = 0; attempt < 2; attempt++) {
            try {
                // Navigate to category and select product using HomePage
                await homePage.goToWomenCategory();
                await homePage.selectFirstProduct();

                // Add product to cart using ProductPage
                await productPage.addToCart('1', 'M');
                
                // Wait for product page and add to cart
                await page.waitForLoadState('domcontentloaded');
                await Promise.all([
                    page.waitForSelector('button.exclusive', { state: 'visible', timeout: 30000 }),
                    page.waitForSelector('#quantity_wanted', { state: 'visible', timeout: 30000 })
                ]);
                
                // Click add to cart with retry
                await page.click('button.exclusive');
                
                // Wait for success and verify
                await page.waitForSelector('#layer_cart', { state: 'visible', timeout: 30000 });
                const cartLayer = await page.isVisible('#layer_cart');
                expect(cartLayer).toBe(true);
                
                // Test succeeded, break the retry loop
                break;
            } catch (e) {
                if (attempt === 1) {
                    // On last attempt, rethrow the error
                    throw e;
                }
                // Wait before retrying
                await page.waitForTimeout(5000);
                console.log(`Attempt ${attempt + 1} failed, retrying...`);
            }
        }
    });

    test('should update product quantity in cart', async ({ page }, testInfo) => {
        // Retry logic for stability
        for (let attempt = 0; attempt < 2; attempt++) {
            try {
                // Navigate to category and select product using HomePage
                await homePage.goToWomenCategory();
                await homePage.selectFirstProduct();
                
                // Wait for product page elements
                await page.waitForLoadState('domcontentloaded');
                await Promise.all([
                    page.waitForSelector('button.exclusive', { state: 'visible', timeout: 30000 }),
                    page.waitForSelector('#quantity_wanted', { state: 'visible', timeout: 30000 })
                ]);
                
                // Clear and update quantity
                await page.fill('#quantity_wanted', '');
                await page.fill('#quantity_wanted', '3');
                
                // Add to cart
                await page.click('button.exclusive');
                
                // Verify cart layer and quantity
                await page.waitForSelector('#layer_cart', { state: 'visible', timeout: 30000 });
                const quantity = await page.locator('#layer_cart_product_quantity').textContent();
                expect(quantity.trim()).toBe('3');
                
                // Test succeeded, break retry loop
                break;
            } catch (e) {
                if (attempt === 1) {
                    throw e;
                }
                await page.waitForTimeout(5000);
                console.log(`Attempt ${attempt + 1} failed, retrying...`);
            }
        }
    });    test('should remove product from cart', async ({ page }, testInfo) => {
        // Retry logic for stability
        for (let attempt = 0; attempt < 2; attempt++) {
            try {
                // Add product to cart using POM methods
                await homePage.goToWomenCategory();
                await homePage.selectFirstProduct();
                await productPage.addToCart('1', 'M');
                
                // Wait for product page elements
                await page.waitForLoadState('domcontentloaded');
                await Promise.all([
                    page.waitForSelector('button.exclusive', { state: 'visible', timeout: 30000 }),
                    page.waitForSelector('#quantity_wanted', { state: 'visible', timeout: 30000 })
                ]);
                
                // Add to cart
                await page.click('button.exclusive');
                
                // Wait for cart layer and proceed to checkout
                await page.waitForSelector('#layer_cart', { state: 'visible', timeout: 30000 });
                await page.click('a[title="Proceed to checkout"]');
                
                // Proceed to cart and remove product
                await productPage.proceedToCheckout();
                await cartPage.removeProduct();
                
                // Verify cart is empty
                const isEmpty = await cartPage.isCartEmpty();
                expect(isEmpty).toBe(true);
                
                // Test succeeded, break retry loop
                break;
            } catch (e) {
                if (attempt === 1) {
                    throw e;
                }
                await page.waitForTimeout(5000);
                console.log(`Attempt ${attempt + 1} failed, retrying...`);
            }
        }
    });
});