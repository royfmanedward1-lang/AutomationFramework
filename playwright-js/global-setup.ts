import { chromium } from '@playwright/test';
import { mkdirSync } from 'fs';

// Generates a reusable authenticated storage state for valid login
export default async function globalSetup() {
  const browser = await chromium.launch();
  const context = await browser.newContext();
  const page = await context.newPage();

  // Navigate and perform valid login against the-internet demo site
  await page.goto('https://the-internet.herokuapp.com/login');
  await page.getByLabel(/username/i).fill('tomsmith');
  await page.getByLabel(/password/i).fill('SuperSecretPassword!');
  await page.getByRole('button', { name: /login/i }).click();

  // Basic assertion to ensure we are logged in
  await page.waitForURL(/\/secure/);

  // Persist authenticated state for reuse in tests
  mkdirSync('storage', { recursive: true });
  await context.storageState({ path: 'storage/auth.json' });

  await browser.close();
}
