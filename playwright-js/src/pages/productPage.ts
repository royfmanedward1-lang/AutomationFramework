import { Page } from '@playwright/test';

export class ProductPage {
    private page: Page;
    
    // Locators
    private readonly quantityInput = '#quantity_wanted';
    private readonly sizeSelect = '#group_1';
    private readonly addToCartButton = '#add_to_cart button';
    private readonly colorOptions = '#color_to_pick_list li';
    private readonly proceedToCheckoutButton = 'a[title="Proceed to checkout"]';

    constructor(page: Page) {
        this.page = page;
    }

    async setQuantity(quantity: number) {
        await this.page.fill(this.quantityInput, quantity.toString());
    }

    async selectSize(size: string) {
        await this.page.selectOption(this.sizeSelect, size);
    }

    async selectColor(colorIndex: number) {
        await this.page.click(`${this.colorOptions}:nth-child(${colorIndex})`);
    }

    async addToCart(quantity?: string, size?: string) {
        // Wait for product page elements with longer timeout
        await Promise.all([
            this.page.waitForSelector(this.addToCartButton, { state: 'visible', timeout: 60000 }),
            this.page.waitForSelector(this.quantityInput, { state: 'visible', timeout: 60000 })
        ]);

        // Set quantity if provided
        if (quantity) {
            await this.page.fill(this.quantityInput, ''); // Clear first
            await this.page.fill(this.quantityInput, quantity);
        }

        // Set size if provided
        if (size) {
            await this.page.waitForSelector(this.sizeSelect, { state: 'visible', timeout: 30000 });
            await this.page.selectOption(this.sizeSelect, size);
        }

        // Add to cart with retry
        for (let attempt = 0; attempt < 3; attempt++) {
            try {
                await this.page.click(this.addToCartButton);
                await this.page.waitForSelector('#layer_cart', { state: 'visible', timeout: 30000 });
                break;
            } catch (e) {
                if (attempt === 2) throw e;
                await this.page.waitForTimeout(1000);
            }
        }
    }

    async proceedToCheckout() {
        // Wait for the checkout button to be visible after adding to cart
        await this.page.waitForSelector(this.proceedToCheckoutButton, { state: 'visible', timeout: 30000 });
        await this.page.click(this.proceedToCheckoutButton);
        await this.page.waitForLoadState('domcontentloaded');
    }

    async getProductPrice() {
        return this.page.textContent('#our_price_display');
    }

    async getProductName() {
        return this.page.textContent('h1[itemprop="name"]');
    }
}