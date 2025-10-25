# Download required JARs for running Selenium tests directly (no Maven)
$libDir = "lib"
New-Item -ItemType Directory -Path $libDir -Force | Out-Null

# List of dependencies and their direct download URLs
$deps = @(
    @{
        name = "selenium-java-4.15.0.jar"
        url = "https://repo1.maven.org/maven2/org/seleniumhq/selenium/selenium-java/4.15.0/selenium-java-4.15.0.jar"
    },
    @{
        name = "junit-4.13.2.jar"
        url = "https://repo1.maven.org/maven2/junit/junit/4.13.2/junit-4.13.2.jar"
    },
    @{
        name = "webdrivermanager-5.5.3.jar"
        url = "https://repo1.maven.org/maven2/io/github/bonigarcia/webdrivermanager/5.5.3/webdrivermanager-5.5.3.jar"
    }
)

foreach ($dep in $deps) {
    Write-Host "Downloading $($dep.name)..."
    $outFile = Join-Path $libDir $dep.name
    try {
        Invoke-WebRequest -Uri $dep.url -OutFile $outFile
        Write-Host "Downloaded $($dep.name)"
    } catch {
        Write-Error "Failed to download $($dep.name): $_"
    }
}

Write-Host "`nNext steps:"
Write-Host "1. Set JAVA_HOME to point to your JDK"
Write-Host "2. Run: mkdir -Force target\test-classes"
Write-Host "3. Run: javac -cp 'lib/*' -d target/test-classes src/test/java/com/example/tests/*.java"
Write-Host "4. Run: java -cp 'target/test-classes;lib/*' com.example.tests.TestRunner"