import { Page } from '@playwright/test';

export class BasePage {
    protected page: Page;
    protected baseUrl: string;

    constructor(page: Page) {
        this.page = page;
        this.baseUrl = 'http://www.automationpractice.pl';
    }

    /**
     * Navigate to a specific path
     * @param path - Path to navigate to (will be appended to baseUrl)
     */
    async navigate(path: string = '') {
        await this.page.goto(`${this.baseUrl}${path}`);
        await this.page.waitForLoadState('domcontentloaded');
    }

    /**
     * Wait for element to be visible
     * @param selector - Element selector
     * @param timeout - Maximum time to wait (default: 30000ms)
     */
    async waitForElement(selector: string, timeout: number = 30000) {
        await this.page.waitForSelector(selector, { state: 'visible', timeout });
    }

    /**
     * Get element text
     * @param selector - Element selector
     */
    async getText(selector: string): Promise<string> {
        await this.waitForElement(selector);
        return this.page.locator(selector).textContent() || '';
    }

    /**
     * Click element with retry logic
     * @param selector - Element selector
     * @param options - Click options
     */
    async clickWithRetry(selector: string, maxAttempts: number = 3) {
        for (let attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                await this.waitForElement(selector);
                await this.page.click(selector);
                break;
            } catch (error) {
                if (attempt === maxAttempts) throw error;
                await this.page.waitForTimeout(1000);
            }
        }
    }
}