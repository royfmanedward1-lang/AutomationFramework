import { test, expect } from '@playwright/test';

test('visual snapshot of homepage', async ({ page }) => {
  await page.goto('https://example.com');
  // capture full page snapshot
  expect(await page.screenshot({ fullPage: true })).toMatchSnapshot('example-homepage.png');
});
