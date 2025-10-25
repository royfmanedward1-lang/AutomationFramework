import { test, expect } from '@playwright/test';
import { HomePage } from '../src/pages/homePage';
import { AuthenticationPage } from '../src/pages/authenticationPage';
import { env } from '../fixtures/env';
import users from '../fixtures/users.json';

test.describe('Authentication Tests', () => {
    let homePage: HomePage;
    let authPage: AuthenticationPage;

    test.beforeEach(async ({ page }) => {
        homePage = new HomePage(page);
        authPage = new AuthenticationPage(page);
        await homePage.navigate();
        await homePage.goToSignIn();
    });

    test('should not login with invalid credentials', async () => {
        await authPage.login(users.invalidUser.email, users.invalidUser.password);
        const errorMessage = await authPage.getErrorMessage();
        expect(errorMessage).toContain('Authentication failed');
    });

    test('should start account creation process', async () => {
        await authPage.startAccountCreation(users.newUser.email);
        const currentUrl = await authPage.getCurrentUrl();
        expect(currentUrl).toContain('controller=authentication');
    });

    test('forgot password link should work', async () => {
        await authPage.forgotPassword();
        const currentUrl = await authPage.getCurrentUrl();
        expect(currentUrl).toContain('controller=password');
    });
});