import { Page } from '@playwright/test';

export class LoginPage {
  constructor(private page: Page) {}

  async goto() {
    await this.page.goto('https://the-internet.herokuapp.com/login');
  }

  async login(username: string, password: string) {
    await this.page.getByLabel(/username/i).fill(username);
    await this.page.getByLabel(/password/i).fill(password);
    await this.page.getByRole('button', { name: /login/i }).click();
  }

  async getFlashText() {
    return this.page.locator('#flash').textContent();
  }

  async isLoginSuccessful(): Promise<boolean> {
    const flash = await this.getFlashText();
    return flash?.includes('You logged into a secure area!') || false;
  }
}
