# Playwright JS Framework

This folder contains a minimal Playwright test framework using Playwright Test.

Quick start (Windows):

1. Open PowerShell and navigate to this folder:

```powershell
cd C:\automation\AutomationFramework\playwright-js
```

2. Install dependencies and browsers:

```powershell
.\setup-playwright.ps1
```

3. Run tests (headless):

```powershell
npm test
```

4. Run tests headed (visible browser):

```powershell
npm run test:headed
```

CI (GitHub Actions) suggestion:
- Use node 18+ and run `npm ci` then `npx playwright install --with-deps` and `npx playwright test`.

Cross-browser and CI
---------------------
The Playwright config runs tests across Chromium, Firefox, and WebKit by default. A GitHub Actions workflow is included at `.github/workflows/playwright.yml` which:

- Installs Node.js and dependencies
- Installs Playwright browsers with dependencies
- Runs tests and uploads JUnit + HTML reports as artifacts

Use `npm test` locally or the workflow will run tests automatically on push/PR to `main`.

Login flow tests
----------------
There is a realistic cross-browser login flow under `tests/login.spec.ts` which uses a simple page object at `src/pages/loginPage.ts`.

The tests are data-driven and exercise both successful and failed login flows against `https://the-internet.herokuapp.com/login`.

Run the login tests specifically:

```powershell
npx playwright test tests/login.spec.ts
```

Accessibility, visual regression and Allure
-----------------------------------------

- Accessibility: the login tests include an Axe accessibility scan. Failures will fail the test. You can add additional pages to scan in `tests/`.
- Visual regression: `tests/visual.spec.ts` captures a full-page screenshot and compares against a baseline. On first run a snapshot will be recorded under `tests\__snapshots__`.
- Allure: Run Playwright with the Allure reporter and generate a report:

```powershell
# Run tests with Allure reporter
npm run test:allure

# Generate Allure report
npm run allure:generate
npm run allure:open
```

Docker (no Node install)
------------------------
If you don't want to install Node/npm locally, use the provided Dockerfile and helper to run tests inside a container:

```powershell
# Build the image (from playwright-js folder)
.\run-in-docker.ps1

# The script builds the image and runs tests; reports will be available in the project folder.
```




