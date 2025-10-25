import { test, expect } from '@playwright/test';

test('homepage has title', async ({ page }, testInfo) => {
  console.log(`Running on project: ${testInfo.project.name}`);
  await page.goto('https://example.com');
  await expect(page).toHaveTitle(/Example Domain/);
});
