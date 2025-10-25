import { test, expect } from '@playwright/test';
import { LoginPage } from '../src/pages/loginPage';
import credentials from '../fixtures/credentials.json';
import AxeBuilder from '@axe-core/playwright';

test.describe('Login flow', () => {
  for (const cred of credentials as any[]) {
    test(`login test for ${cred.user}`, async ({ page }) => {
      const login = new LoginPage(page);
      await login.goto();
      await login.login(cred.user, cred.pass);
      if (cred.success) {
        await expect(page).toHaveURL(/\/secure/);
        const flash = await login.getFlashText();
        expect(flash).toContain('You logged into a secure area!');
      } else {
        const flash = await login.getFlashText();
        expect(flash).toContain('Your username is invalid!');
      }
    });
  }

  test('accessibility check on login page', async ({ page }) => {
    const login = new LoginPage(page);
    await login.goto();
    const accessibilityScanResults = await new AxeBuilder({ page }).analyze();
    // Fail the test if there are any violations
    expect(accessibilityScanResults.violations.length).toBe(0);
  });
});
