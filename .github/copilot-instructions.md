# Copilot Instructions for this Repo

This repo is a multi-language automation workspace with separate test projects. Optimize for fast, isolated edits per project; don’t mix stacks unless asked.

## Big picture
- Projects:
  - `selenium-java-project/` – UI tests with Selenium (JUnit4), plus simple runner; uses WebDriverManager. Java source/target currently 1.8 (see `pom.xml`).
  - `api-test-java/` – API tests with Rest-Assured + JUnit 5; Allure/JUnit5 deps in `pom.xml`.
  - `api-test-python/` – Pytest-based API tests.
  - `playwright-js/` – Playwright Test (TS/JS) with Axe, JUnit+HTML+Allure reporters.
- These are independent; there is no shared runtime between them. Results are written to per-project output folders.

## Key workflows (Windows PowerShell)
- Selenium Java (JUnit4):
  - System Maven: from `selenium-java-project/`: `mvn -Dtest=com.example.tests.SeleniumTest test`
  - Bundled Maven (no install): `selenium-java-project\scripts\.maven\apache-maven-3.8.8\bin\mvn.cmd -Dtest=com.example.tests.SeleniumTest test`
- Java API (JUnit5): from `api-test-java/`:
  - Build: `mvn install`
  - Test: `mvn test`
- Python API: from `api-test-python/`:
  - Setup: `pip install -r requirements.txt`
  - Run: `pytest test_api.py`
- Playwright JS: from `playwright-js/`:
  - First-time setup: `./setup-playwright.ps1`
  - Headless run: `npm test`
  - Headed run: `npm run test:headed`
  - Allure: `npm run test:allure` then `npm run allure:generate` and `npm run allure:open`

## Project conventions and patterns
- Selenium POM: Page Object classes live under `selenium-java-project/src/test/java/com/example/tests` (e.g., `GoogleHomePage.java`). Tests extend base classes like `BaseTest`/`SelenideBaseTest` when present.
- Drivers: Prefer WebDriverManager (no manual driver binaries). Browsers are switched via config in tests/utilities.
- Java versions: `selenium-java-project/pom.xml` uses `<source>/<target>` 1.8; `api-test-java/pom.xml` uses 11. Keep compatibility unless a coordinated upgrade is requested.
- Playwright config: `playwright-js/playwright.config.ts` defines three projects (Chromium/Firefox/WebKit), JUnit report at `playwright-js/test-results/results.xml`, and `baseURL` from `BASE_URL` env (defaults to `https://the-internet.herokuapp.com`).
- Credentials/fixtures: Playwright reads from `playwright-js/fixtures/credentials.json`. Don’t hardcode secrets; use fixtures or env vars.
- Reports:
  - Java: Maven Surefire output under `target/`; Allure results when configured (e.g., `target/allure-results`).
  - Playwright: HTML at `playwright-js/playwright-report/`, Allure at `playwright-js/allure-results/` and `allure-report/` after generate.

## Cross-project boundaries
- Keep each subproject’s dependencies and scripts isolated. Don’t import code across language boundaries.
- Network usage:
  - UI tests target public demo sites (e.g., the-internet.herokuapp.com).
  - API tests often call JSONPlaceholder; mock with WireMock in Java when needed.

## Examples to follow
- Playwright login flow: `playwright-js/tests/login.spec.ts` with page object in `src/pages/loginPage.ts` and Axe scan.
- Java API test scaffolding: see `api-test-java/src/test/` (JUnit 5 + Rest-Assured).
- Selenium sample tests: `selenium-java-project/src/test/java/com/example/tests/SeleniumTest.java` and `SelenideGoogleSearchTest.java`.

## When adding or changing tests
- Match the existing stack in that folder (JUnit4 vs JUnit5; Node vs Python).
- Prefer existing reporters and output locations so CI/artifacts remain consistent.
- For Playwright, update `playwright.config.ts` for new projects or env defaults; keep reporters aligned.

Notes
- There’s a GitHub Actions workflow at `.github/workflows/ci.yml`; use it as reference but verify locally—the YAML may require correction before use.
