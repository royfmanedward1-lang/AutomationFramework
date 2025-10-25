# Download and setup portable JDK, then run Selenium tests
$ErrorActionPreference = 'Stop'

# Create .java folder for portable JDK
$javaDir = ".java"
New-Item -ItemType Directory -Path $javaDir -Force | Out-Null

# Download Zulu JDK (more reliable than Adoptium for direct downloads)
$jdkUrl = "https://cdn.azul.com/zulu/bin/zulu17.44.53-ca-jdk17.0.8.1-win_x64.zip"
$jdkZip = Join-Path $javaDir "jdk.zip"
Write-Host "Downloading portable JDK..."
Invoke-WebRequest -Uri $jdkUrl -OutFile $jdkZip

# Extract JDK
Write-Host "Extracting JDK..."
Expand-Archive -Path $jdkZip -DestinationPath $javaDir -Force
Remove-Item $jdkZip

# Find JDK folder
$jdkFolder = Get-ChildItem $javaDir | Where-Object { $_.PSIsContainer -and $_.Name -like "zulu*" } | Select-Object -First 1
if (-not $jdkFolder) {
    throw "Could not find JDK folder in $javaDir"
}

# Set JAVA_HOME for this session
$env:JAVA_HOME = $jdkFolder.FullName
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"

# Verify Java
Write-Host "`nVerifying Java installation:"
java -version

# Run tests using local Maven
Write-Host "`nCompiling and running Selenium tests with Maven..."
$mavenCmd = Join-Path (Get-Location) "scripts\.maven\apache-maven-3.8.8\bin\mvn.cmd"

# First clean and compile
Write-Host "Compiling project..."
& $mavenCmd clean compile test-compile

# Then run specific test
Write-Host "`nRunning SeleniumTest..."
& $mavenCmd surefire:test "-Dtest=com.example.tests.SeleniumTest"