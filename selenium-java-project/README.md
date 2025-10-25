# Selenium Java Project

## Overview
This project is a Selenium Java application designed for automated testing of web applications. It utilizes the Selenium framework to interact with web elements and perform various test cases.

## Project Structure
```
selenium-java-project
├── src
│   └── test
│       └── java
│           └── com
│               └── example
│                   └── tests
│                       ├── SeleniumTest.java
│                       └── TestRunner.java
├── scripts
│   └── .maven        # Local Maven installation
├── pom.xml
└── README.md
```

## Running Tests

### Option 1: Using Local Maven (Recommended, no install needed)
The project includes a local Maven installation in `scripts/.maven/`. Use:
```powershell
# From the selenium-java-project folder:
.\scripts\.maven\apache-maven-3.8.8\bin\mvn.cmd -Dtest=com.example.tests.SeleniumTest test
```

### Option 2: Using System Maven
If you have Maven installed globally:
```powershell
mvn -Dtest=com.example.tests.SeleniumTest test
```

### Option 3: Direct Java Execution (No Maven)
If you prefer not to use Maven, you can run tests directly with Java:

1. Set JAVA_HOME to point to your JDK:
```powershell
# Example - adjust path to your JDK
$env:JAVA_HOME = "C:\Program Files\Java\jdk-17.0.8"
$env:PATH = "$env:JAVA_HOME\bin;" + $env:PATH
```

2. Compile and run:
```powershell
# Create target directory
mkdir -Force target\test-classes

# Compile test classes
javac -cp "lib/*" -d target/test-classes src/test/java/com/example/tests/*.java

# Run tests via TestRunner
java -cp "target/test-classes;lib/*" com.example.tests.TestRunner
```

## Usage Guidelines
- The `SeleniumTest.java` file is where you will write your Selenium test cases to interact with web applications.

## Dependencies
- Selenium Java (version 4.0.0)
- JUnit (version 4.13.2) for testing

## License
This project is licensed under the MIT License. See the LICENSE file for more details.
