# POM Framework Quick Reference

## Running Tests

### Run All Tests
```powershell
.\scripts\.maven\apache-maven-3.8.8\bin\mvn.cmd test
```

### Run Specific Test Class
```powershell
.\scripts\.maven\apache-maven-3.8.8\bin\mvn.cmd -Dtest=LoginTest test
.\scripts\.maven\apache-maven-3.8.8\bin\mvn.cmd -Dtest=GoogleSearchTest test
```

### Run Single Test Method
```powershell
.\scripts\.maven\apache-maven-3.8.8\bin\mvn.cmd -Dtest=LoginTest#testSuccessfulLogin test
```

### Run with Different Options
```powershell
# Firefox browser
.\scripts\.maven\apache-maven-3.8.8\bin\mvn.cmd test -Dbrowser=firefox

# Headed mode (see browser)
.\scripts\.maven\apache-maven-3.8.8\bin\mvn.cmd test -Dheadless=false

# Custom base URL
.\scripts\.maven\apache-maven-3.8.8\bin\mvn.cmd test -Dbase.url=https://example.com

# Combine multiple options
.\scripts\.maven\apache-maven-3.8.8\bin\mvn.cmd test -Dbrowser=firefox -Dheadless=false
```

## Framework Structure

```
com.example
├── base/
│   ├── BasePage.java      # Common page operations & waits
│   └── BaseTest.java      # WebDriver setup/teardown
├── pages/
│   ├── LoginPage.java     # Login page object
│   └── GoogleHomePage.java # Google page object
└── tests/
    ├── LoginTest.java     # Login test suite
    ├── GoogleSearchTest.java # Google test suite
    └── SeleniumTest.java  # Refactored legacy test
```

## Creating New Page Object

```java
package com.example.pages;

import com.example.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NewPage extends BasePage {
    // 1. Define locators
    private final By elementLocator = By.id("element-id");
    
    // 2. Constructor
    public NewPage(WebDriver driver) {
        super(driver);
    }
    
    // 3. Page actions (return 'this' for chaining)
    public NewPage doAction() {
        click(elementLocator);
        return this;
    }
    
    // 4. Verification methods
    public boolean isActionComplete() {
        return isDisplayed(elementLocator);
    }
}
```

## Creating New Test

```java
package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.NewPage;
import org.junit.Test;
import static org.junit.Assert.*;

public class NewTest extends BaseTest {
    
    @Test
    public void testFeature() {
        NewPage page = new NewPage(driver);
        page.doAction();
        assertTrue("Verify action", page.isActionComplete());
    }
}
```

## BasePage Methods Available

### Navigation
- `navigateTo(String url)` - Navigate to URL
- `getCurrentUrl()` - Get current URL
- `getTitle()` - Get page title

### Element Interaction
- `find(By locator)` - Find element with wait
- `findAll(By locator)` - Find all elements
- `click(By locator)` - Click element
- `type(By locator, String text)` - Type text
- `getText(By locator)` - Get element text
- `isDisplayed(By locator)` - Check if visible

### Wait Utilities
- `waitForElementToBeVisible(By locator)`
- `waitForElementToBeClickable(By locator)`
- `waitForElementToDisappear(By locator)`
- `waitForUrlContains(String fragment)`

## Configuration System Properties

| Property | Default | Description |
|----------|---------|-------------|
| `browser` | chrome | Browser: chrome, firefox |
| `headless` | true | Run headless mode |
| `base.url` | https://the-internet.herokuapp.com | Base URL |

## Test Results Location
- Reports: `target/surefire-reports/`
- Compiled classes: `target/test-classes/`

## Troubleshooting

**Chrome not found:**
```powershell
.\scripts\.maven\apache-maven-3.8.8\bin\mvn.cmd test -Dbrowser=firefox
```

**Want to see browser:**
```powershell
.\scripts\.maven\apache-maven-3.8.8\bin\mvn.cmd test -Dheadless=false
```

**Clean build:**
```powershell
.\scripts\.maven\apache-maven-3.8.8\bin\mvn.cmd clean test
```
