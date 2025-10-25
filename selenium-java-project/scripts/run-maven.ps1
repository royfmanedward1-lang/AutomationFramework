<#
run-maven.ps1

Downloads a local Maven binary into the project's scripts folder (once) and forwards any arguments to the local mvn.cmd.
Usage:
  .\run-maven.ps1 clean install
  .\run-maven.ps1 -Dtest=com.example.tests.SeleniumTest test

Notes:
- Requires internet access to download Maven the first time.
- If you already have Maven installed system-wide, just run "mvn ..." directly.
#>

param(
    # Accept a single string containing all arguments, or an array via remaining arguments.
    [string]$Args,
    [Parameter(ValueFromRemainingArguments=$true)]
    [string[]]$MavenArgs
)

$ErrorActionPreference = 'Stop'
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Definition
$localDir = Join-Path $scriptDir '.maven'
if (-not (Test-Path $localDir)) { New-Item -ItemType Directory -Path $localDir | Out-Null }

# Pick a reasonably compatible Maven version (change if you prefer another).
$mavenVersion = '3.8.8'
$mavenBase = "apache-maven-$mavenVersion"
$mavenDir = Join-Path $localDir $mavenBase
$mvnCmd = Join-Path $mavenDir 'bin\mvn.cmd'

if (-not (Test-Path $mvnCmd)) {
    Write-Host "Local Maven not found. Downloading Apache Maven $mavenVersion into $localDir..."
    $zipName = "$mavenBase-bin.zip"
    $zipPath = Join-Path $scriptDir $zipName
    # Use the Apache archive URL which is stable.
    $url = "https://archive.apache.org/dist/maven/maven-3/$mavenVersion/binaries/$zipName"

    Write-Host "Downloading $url ..."
    try {
        Invoke-WebRequest -Uri $url -OutFile $zipPath -UseBasicParsing
    } catch {
        Write-Error ('Failed to download Maven from ' + $url + '. Check internet access or choose a different installation method. ' + $_.ToString())
        exit 1
    }

    Write-Host "Extracting $zipPath to $localDir ..."
    try {
        Expand-Archive -Path $zipPath -DestinationPath $localDir -Force
        Remove-Item $zipPath -Force
    } catch {
        Write-Error ('Failed to extract ' + $zipPath + ': ' + $_.ToString())
        exit 1
    }

    if (-not (Test-Path $mvnCmd)) {
        Write-Error "Expected mvn.cmd not found after extraction. Looked for $mvnCmd"
        exit 1
    }
    Write-Host "Local Maven ready: $mvnCmd"
} else {
    Write-Host "Using existing local Maven at $mvnCmd"
}

# Forward arguments to mvn.cmd
if ($Args) {
    # Split on spaces respecting simple quoting is not implemented; expect simple usage.
    $argList = $Args -split ' '
} else {
    $argList = @()
    if ($MavenArgs) { $argList += $MavenArgs }
}

Write-Host "Running: $mvnCmd $($argList -join ' ')"
& $mvnCmd @argList

exit $LASTEXITCODE
