<#
Sets up this PowerShell session to use the project's local Maven and local JDK (if present).
Run from any folder: `C:\...\selenium-java-project\scripts\env-local-maven.ps1` or `./scripts/env-local-maven.ps1`
This modifies environment variables for the current session only.
#>

$ErrorActionPreference = 'Stop'

# Determine script and project root
$scriptPath = $MyInvocation.MyCommand.Path
if (-not $scriptPath) { $scriptPath = (Get-Location).Path }
$scriptDir = Split-Path -Parent $scriptPath
$projectRoot = Resolve-Path (Join-Path $scriptDir '..')

# Local Maven bin
$mavenBin = Join-Path $projectRoot 'scripts\.maven\apache-maven-3.8.8\bin'
if (Test-Path $mavenBin) {
    $env:PATH = $mavenBin + ';' + $env:PATH
    Write-Host "Added local Maven to PATH: $mavenBin"
} else {
    Write-Warning "Local Maven not found at $mavenBin. You can still use full path to mvn.cmd or install Maven system-wide."
}

# If java isn't on PATH, try to find a portable JDK in the project ('.java' folder)
if (-not (Get-Command java -ErrorAction SilentlyContinue)) {
    $localJdkRoot = Join-Path $projectRoot '.java'
    if (Test-Path $localJdkRoot) {
        $jdkDir = Get-ChildItem $localJdkRoot -Directory | Where-Object { $_.Name -like 'zulu*' -or $_.Name -like 'jdk*' } | Select-Object -First 1
        if ($jdkDir) {
            $env:JAVA_HOME = $jdkDir.FullName
            $env:PATH = "$env:JAVA_HOME\bin;" + $env:PATH
            Write-Host "Set JAVA_HOME to: $env:JAVA_HOME"
        } else {
            Write-Host "No JDK directory found under $localJdkRoot. If you want to run Maven, set JAVA_HOME or install Java." -ForegroundColor Yellow
        }
    } else {
        Write-Host "No local .java folder found. If you want to run Maven, set JAVA_HOME or install Java." -ForegroundColor Yellow
    }
} else {
    Write-Host "Java is already on PATH; no change to JAVA_HOME." -ForegroundColor Green
}

Write-Host "Session updated. You can now run: mvn clean test (or mvn clear if you have a custom goal)"
Write-Host "Note: changes apply only to this PowerShell session."