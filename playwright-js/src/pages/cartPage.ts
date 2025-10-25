import { Page } from '@playwright/test';

export class CartPage {
    constructor(private page: Page) {}
    
    /**
     * Remove product from cart
     */
    async removeProduct() {
        await this.page.getByRole('button', { name: /remove|delete|trash/i }).first().click();
        await this.page.waitForLoadState('domcontentloaded');
    }
    
    /**
     * Update product quantity in cart
     * @param quantity - New quantity to set
     */
    async updateQuantity(quantity: string) {
        await this.page.getByLabel(/quantity/i).fill(quantity);
        await this.page.getByRole('button', { name: /update/i }).click();
        await this.page.waitForLoadState('networkidle');
    }
    
    /**
     * Check if cart is empty
     */
    async isCartEmpty(): Promise<boolean> {
        const emptyAlert = this.page.getByText(/your shopping cart is empty/i);
        await emptyAlert.waitFor({ state: 'visible', timeout: 30000 });
        return emptyAlert.isVisible();
    }
    
    /**
     * Get cart total price
     */
    async getTotalPrice(): Promise<string> {
        const totalLocator = this.page.locator('#total_price').or(this.page.getByText(/total/i).locator('..'));
        return (await totalLocator.textContent())?.trim() || '';
    }
}