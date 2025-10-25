<#
Preflight environment check for Playwright project.
Prints which tools are available and gives copy-paste winget commands to install Node and Docker on Windows.
Run: .\scripts\check-env.ps1
#>

Write-Host "Playwright project environment check" -ForegroundColor Cyan

function Check-Cmd($name, $displayName) {
    $cmd = Get-Command $name -ErrorAction SilentlyContinue
    if ($cmd) {
        Write-Host "[OK]  $displayName -> $($cmd.Path)" -ForegroundColor Green
        return $true
    } else {
        Write-Host "[MISS] $displayName is not found on PATH" -ForegroundColor Yellow
        return $false
    }
}

$node = Check-Cmd -name node -displayName 'Node (node)'
$npm = Check-Cmd -name npm -displayName 'npm (npm)'
$npx = Check-Cmd -name npx -displayName 'npx (npx)'
$docker = Check-Cmd -name docker -displayName 'Docker (docker)'
$winget = Check-Cmd -name winget -displayName 'winget (Windows Package Manager)'

Write-Host "`nSummary:" -ForegroundColor Cyan
if ($node -and $npm) {
    Write-Host "Node/npm appear installed." -ForegroundColor Green
} else {
    Write-Host "Node/npm missing. To install Node 18 LTS via winget run (requires winget):" -ForegroundColor Yellow
    Write-Host "  winget install --id OpenJS.NodeJS.LTS -e" -ForegroundColor White
    Write-Host "If winget isn't available, download Node LTS from https://nodejs.org and run the installer." -ForegroundColor White
}

if (-not $docker) {
    Write-Host "`nDocker not found. To install Docker Desktop via winget run (may require admin):" -ForegroundColor Yellow
    Write-Host "  winget install --id Docker.DockerDesktop -e" -ForegroundColor White
    Write-Host "After installing Docker Desktop, ensure it is running and that you have enabled WSL2 integration if required." -ForegroundColor White
}

if ($winget -and -not ($node -and $npm -and $docker)) {
    Write-Host "`nYou can copy-paste the above winget commands to install missing tools." -ForegroundColor Cyan
}

Write-Host "`nWhen Node/npm are installed, re-run: .\setup-playwright.ps1" -ForegroundColor Cyan
Write-Host "Or use Docker: .\run-in-docker.ps1 (requires Docker installed)" -ForegroundColor Cyan
