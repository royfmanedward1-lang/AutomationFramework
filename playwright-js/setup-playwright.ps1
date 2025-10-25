#!/usr/bin/env pwsh
Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass -Force

Write-Host "Installing npm dependencies (npm ci)..."
npm ci

Write-Host "Installing Playwright browsers (with deps)..."
npx playwright install --with-deps

Write-Host "Ensure Allure CLI is available (for reports)..."
try {
	npx -y allure --version | Out-Null
	Write-Host "Allure CLI is available via npx."
} catch {
	Write-Host "Installing allure-commandline locally..."
	npm i --no-save allure-commandline
}

Write-Host "Setup complete. Run 'npm test' to execute tests. For Allure reports run 'npm run test:allure' then 'npm run allure:generate'."
