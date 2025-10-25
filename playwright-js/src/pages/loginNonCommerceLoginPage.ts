import { Page, Locator } from '@playwright/test';

export class LoginPageNoncommerce {
  private readonly emailInput: Locator;
  private readonly passwordInput: Locator;
  private readonly loginButton: Locator;
  private readonly alert: Locator;
  private readonly accountHeading: Locator;
  private readonly myAccountLink: Locator;
  private readonly logoutLink: Locator;

  constructor(private page: Page) {
    // Declare elements up front
    this.emailInput = page.getByLabel(/E-Mail Address/i);
    this.passwordInput = page.getByLabel(/Password/i);
    this.loginButton = page.getByRole('button', { name: /Login/i });
    this.alert = page.locator('.alert');
    this.accountHeading = page.locator('h2:has-text("My Account")');
    this.myAccountLink = page.getByRole('link', { name: /My Account/i });
    this.logoutLink = page.getByRole('link', { name: /Logout/i });
  }

  async login(username: string, password: string) {
    await this.emailInput.fill(username);
    await this.passwordInput.fill(password);
    await this.loginButton.click();
    
    // Wait for navigation or error message
    await Promise.race([
      this.page.waitForURL(/account\/account/, { timeout: 5000 }).catch(() => {}),
      this.page.waitForSelector('.alert', { timeout: 5000 }).catch(() => {})
    ]);
  }

  // Fills email and password fields without submitting
  async fillCredentials(email: string, password: string) {
    await this.emailInput.fill(email);
    await this.passwordInput.fill(password);
    console.log('Filled credentials for:', email);
  }

  async getErrorMessage(): Promise<string | null> {
    const alertText = await this.alert.textContent().catch(() => null);
    console.log('Error message:', alertText);
    return alertText;
  }

  async isLoginSuccessful(): Promise<boolean> {
    // Wait a bit for any redirects/navigation after login
    await this.page.waitForTimeout(2000);
    
    const currentURL = this.page.url();
    console.log('Current URL after login:', currentURL);
    
    // For LambdaTest ecommerce playground, successful login redirects to account page
    // Check if URL contains account/account (the my account page)
    if (currentURL.includes('account/account')) {
      return true;
    }
    
    // Alternative: check for presence of account navigation or heading
    const accountHeading = await this.accountHeading.isVisible().catch(() => false);
    return accountHeading;
  }

  async logout() {
    // Click on My Account dropdown in the header
    await this.myAccountLink.click();
    
    // Click Logout from the dropdown menu
    await this.logoutLink.click();
    
    // Wait for navigation to confirm logout
    await this.page.waitForURL(/account\/logout/, { timeout: 5000 }).catch(() => {});
  }
}
