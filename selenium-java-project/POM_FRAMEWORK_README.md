# Page Object Model (POM) Framework - Selenium Java

## Overview
This is a production-ready Page Object Model framework for Selenium WebDriver testing. It provides a clean separation between test logic and page interactions, making tests more maintainable and readable.

## Framework Structure

```
selenium-java-project/
├── src/test/java/com/example/
│   ├── base/                    # Framework foundation
│   │   ├── BasePage.java       # Base class for all page objects
│   │   └── BaseTest.java       # Base class for all tests
│   ├── pages/                   # Page Objects
│   │   ├── LoginPage.java      # Login page object
│   │   └── GoogleHomePage.java # Google search page object
│   └── tests/                   # Test classes
│       ├── LoginTest.java      # Login tests using POM
│       ├── GoogleSearchTest.java # Google search tests using POM
│       ├── SeleniumTest.java   # Legacy test refactored to POM
│       └── TestRunner.java     # Main test runner
```

## Key Components

### 1. BasePage
Foundation for all page objects with:
- Common WebDriver operations (click, type, getText)
- Smart wait utilities (explicit waits for visibility, clickability)
- Element finding methods with automatic waits
- URL and title helpers

### 2. BaseTest
Foundation for all test classes with:
- WebDriver setup and teardown
- Cross-browser support (Chrome, Firefox)
- Headless mode configuration
- Browser-specific options and stability flags

### 3. Page Objects
Encapsulate web page elements and actions:
- **LoginPage**: Handles login functionality on the-internet.herokuapp.com
- **GoogleHomePage**: Handles Google search with consent dialog handling

Each page object follows these principles:
- Locators defined as private constants
- Public methods for user actions (fluent API with method chaining)
- Verification methods for assertions
- No test assertions in page objects

### 4. Test Classes
Clean, readable tests using page objects:
- **LoginTest**: Various login scenarios (valid, invalid credentials)
- **GoogleSearchTest**: Search functionality tests
- **SeleniumTest**: Refactored legacy test using POM

## Usage

### Run All Tests with Maven
```powershell
# From selenium-java-project directory
mvn clean test
```

### Run Specific Test Class
```powershell
mvn -Dtest=LoginTest test
mvn -Dtest=GoogleSearchTest test
```

### Run with Different Browsers
```powershell
# Firefox
mvn test -Dbrowser=firefox

# Chrome (default)
mvn test -Dbrowser=chrome
```

### Run in Headed Mode (visible browser)
```powershell
mvn test -Dheadless=false
```

### Run with Custom Base URL
```powershell
mvn test -Dbase.url=https://your-test-site.com
```

### Run with TestRunner (no Maven)
```powershell
# Compile first
javac -cp "lib/*" -d target/test-classes src/test/java/com/example/**/*.java

# Run TestRunner
java -cp "target/test-classes;lib/*" com.example.tests.TestRunner
```

## POM Best Practices Implemented

### 1. Separation of Concerns
- Page objects handle page interactions
- Test classes contain test logic and assertions
- Base classes provide common functionality

### 2. Encapsulation
- Locators are private within page objects
- Only user-facing actions are exposed publicly
- Internal helper methods are protected/private

### 3. Fluent API
Page objects return `this` to enable method chaining:
```java
loginPage.open(baseUrl)
         .enterUsername("user")
         .enterPassword("pass")
         .clickLogin();
```

### 4. DRY (Don't Repeat Yourself)
- Common WebDriver operations in BasePage
- Test setup/teardown in BaseTest
- Reusable wait utilities

### 5. Maintainability
- Change a locator once in page object, not in every test
- Add new page methods without touching tests
- Easy to extend with new page objects

## Example: Creating New Page Object

```java
package com.example.pages;

import com.example.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MyPage extends BasePage {
    // Locators
    private final By submitButton = By.id("submit");
    private final By nameField = By.name("username");
    
    // Constructor
    public MyPage(WebDriver driver) {
        super(driver);
    }
    
    // Actions
    public MyPage open() {
        navigateTo("https://example.com/mypage");
        return this;
    }
    
    public MyPage enterName(String name) {
        type(nameField, name);
        return this;
    }
    
    public MyPage submit() {
        click(submitButton);
        return this;
    }
    
    // Verifications
    public boolean isSubmitted() {
        return getCurrentUrl().contains("/success");
    }
}
```

## Example: Creating New Test

```java
package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.MyPage;
import org.junit.Test;
import static org.junit.Assert.*;

public class MyTest extends BaseTest {
    
    @Test
    public void testMyFeature() {
        MyPage page = new MyPage(driver);
        
        page.open()
            .enterName("John Doe")
            .submit();
        
        assertTrue("Should be submitted", page.isSubmitted());
    }
}
```

## Configuration Options

All options can be set via system properties (-D flag):

| Property | Default | Description |
|----------|---------|-------------|
| `browser` | chrome | Browser to use (chrome, firefox) |
| `headless` | true | Run in headless mode |
| `base.url` | https://the-internet.herokuapp.com | Base URL for tests |

## Troubleshooting

### Chrome not found
Install Chrome browser or switch to Firefox:
```powershell
mvn test -Dbrowser=firefox
```

### Tests timing out
Increase timeout in BasePage or disable headless:
```powershell
mvn test -Dheadless=false
```

### WebDriver version issues
WebDriverManager automatically handles driver versions. If issues persist:
```powershell
mvn clean test -U
```

## Extending the Framework

### Add New Browser Support
Update `BaseTest.createDriver()` method:
```java
case "edge":
    WebDriverManager.edgedriver().setup();
    return new EdgeDriver();
```

### Add Allure Reporting
Add to `pom.xml`:
```xml
<dependency>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-junit4</artifactId>
    <version>2.24.0</version>
</dependency>
```

### Add Screenshot on Failure
Override `tearDown()` in test classes:
```java
@After
public void tearDown() {
    if (testFailed) {
        takeScreenshot();
    }
    super.tearDown();
}
```

## Benefits of This Framework

✅ **Maintainable**: Change locators in one place  
✅ **Readable**: Tests read like plain English  
✅ **Reusable**: Page objects used across multiple tests  
✅ **Scalable**: Easy to add new pages and tests  
✅ **Stable**: Smart waits prevent flaky tests  
✅ **Cross-browser**: Run same tests on different browsers  
✅ **CI/CD Ready**: Headless mode for automation pipelines  

## Next Steps

1. Add more page objects for your application pages
2. Expand test coverage with new test classes
3. Integrate with Allure for better reporting
4. Add data-driven testing with CSV/JSON
5. Set up CI/CD pipeline (GitHub Actions, Jenkins)
