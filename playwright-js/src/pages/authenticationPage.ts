import { Page } from '@playwright/test';

export class AuthenticationPage {
    private page: Page;
    
    // Locators
    private readonly emailInput = '#email';
    private readonly passwordInput = '#passwd';
    private readonly signInButton = '#SubmitLogin';
    private readonly createEmailInput = '#email_create';
    private readonly createAccountButton = '#SubmitCreate';
    private readonly forgotPasswordLink = 'a[title="Recover your forgotten password"]';

    constructor(page: Page) {
        this.page = page;
    }

    async login(email: string, password: string) {
        await this.page.fill(this.emailInput, email);
        await this.page.fill(this.passwordInput, password);
        await this.page.click(this.signInButton);
    }

    async startAccountCreation(email: string) {
        await this.page.fill(this.createEmailInput, email);
        await this.page.click(this.createAccountButton);
    }

    async forgotPassword() {
        await this.page.click(this.forgotPasswordLink);
    }

    async getCurrentUrl() {
        return this.page.url();
    }

    async getErrorMessage() {
        return this.page.textContent('.alert-danger');
    }
}