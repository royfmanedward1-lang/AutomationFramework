import { Page } from '@playwright/test';

export class HomePage {
    constructor(private page: Page) {}

    async navigate() {
        await this.page.goto('http://www.automationpractice.pl/index.php');
    }

    async searchProduct(productName: string) {
        await this.page.getByPlaceholder(/search/i).fill(productName);
        await this.page.getByRole('button', { name: /search/i }).click();
    }

    async goToWomenCategory() {
        await this.page.goto('http://www.automationpractice.pl/index.php?id_category=3&controller=category');
        await this.page.waitForLoadState('domcontentloaded');
        await this.page.locator('.product-container').first().waitFor({ state: 'visible', timeout: 30000 });
    }

    async selectFirstProduct() {
        // Wait for products to load
        await this.page.locator('.product_list').waitFor({ state: 'visible', timeout: 30000 });
        
        // Get first product href
        const productUrl = await this.page.evaluate(() => {
            const link = document.querySelector('.product-container .product-name');
            return link ? link.getAttribute('href') : null;
        });
        
        if (!productUrl) {
            throw new Error('No products found on page');
        }
        
        // Navigate directly to product page
        await this.page.goto(productUrl);
        await this.page.waitForLoadState('networkidle');
    }

    async goToSignIn() {
        await this.page.getByRole('link', { name: /sign in/i }).click();
    }

    async goToContact() {
        await this.page.getByRole('link', { name: /contact/i }).click();
    }

    async goToCart() {
        await this.page.getByRole('link', { name: /cart/i }).click();
    }

    async subscribeNewsletter(email: string) {
        await this.page.getByPlaceholder(/newsletter/i).fill(email);
        await this.page.getByRole('button', { name: /subscribe/i }).click();
    }

    async switchToPopular() {
        await this.page.getByRole('tab', { name: /popular/i }).click();
    }

    async switchToBestSellers() {
        await this.page.getByRole('tab', { name: /best seller/i }).click();
    }
}