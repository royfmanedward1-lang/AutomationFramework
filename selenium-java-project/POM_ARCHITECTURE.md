# POM Framework Architecture

## Framework Layer Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                       TEST LAYER                            │
│  ┌────────────┐  ┌────────────┐  ┌────────────┐            │
│  │ LoginTest  │  │GoogleSearch│  │SeleniumTest│            │
│  │            │  │    Test    │  │            │            │
│  └─────┬──────┘  └─────┬──────┘  └─────┬──────┘            │
│        │                │                │                   │
│        └────────────────┴────────────────┘                   │
│                         │                                    │
│                    extends                                   │
│                         │                                    │
│                   ┌─────▼──────┐                             │
│                   │  BaseTest  │  ◄─── Setup/Teardown       │
│                   │            │       WebDriver mgmt        │
│                   └────────────┘       Browser config        │
└─────────────────────────────────────────────────────────────┘
                              │
                          creates
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                       PAGE LAYER                            │
│  ┌────────────┐  ┌────────────┐  ┌────────────┐            │
│  │ LoginPage  │  │GoogleHome  │  │  NewPage   │            │
│  │            │  │    Page    │  │            │            │
│  └─────┬──────┘  └─────┬──────┘  └─────┬──────┘            │
│        │                │                │                   │
│        └────────────────┴────────────────┘                   │
│                         │                                    │
│                    extends                                   │
│                         │                                    │
│                   ┌─────▼──────┐                             │
│                   │  BasePage  │  ◄─── Common actions       │
│                   │            │       Wait utilities        │
│                   └────────────┘       Element finders       │
└─────────────────────────────────────────────────────────────┘
                              │
                           uses
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    SELENIUM LAYER                           │
│                                                             │
│  ┌──────────────────────────────────────────────┐          │
│  │           WebDriver Interface                │          │
│  └──────────────────┬───────────────────────────┘          │
│                     │                                       │
│        ┌────────────┼────────────┐                          │
│        │            │            │                          │
│  ┌─────▼─────┐ ┌────▼────┐ ┌────▼────┐                     │
│  │ChromeDriver│ │Firefox  │ │Edge     │                     │
│  │           │ │Driver   │ │Driver   │                     │
│  └───────────┘ └─────────┘ └─────────┘                     │
└─────────────────────────────────────────────────────────────┘
```

## Data Flow

```
Test Method
    │
    ├─► Creates Page Object (passes driver)
    │
    ├─► Calls Page Actions
    │       │
    │       ├─► Page uses BasePage methods
    │       │       │
    │       │       ├─► find() with automatic wait
    │       │       ├─► click(), type(), getText()
    │       │       └─► WebDriver executes
    │       │
    │       └─► Returns this (method chaining)
    │
    └─► Calls Verification Methods
            │
            └─► Assert in test (NOT in page)
```

## Typical Test Flow

```
1. @Before (BaseTest.setUp())
   ├─► Select browser (chrome/firefox)
   ├─► Configure options (headless, etc)
   ├─► Create WebDriver instance
   └─► Maximize window

2. @Test (Your test method)
   ├─► Create page object
   ├─► Navigate to page
   ├─► Perform actions (type, click, etc)
   ├─► Verify results
   └─► Make assertions

3. @After (BaseTest.tearDown())
   └─► driver.quit()
```

## Method Chaining Example

```java
// Fluent API - reads like English
loginPage.open(baseUrl)              // Navigate
         .enterUsername("tomsmith")  // Type username
         .enterPassword("password")  // Type password
         .clickLogin();              // Click button

// Same as:
loginPage.open(baseUrl);
loginPage.enterUsername("tomsmith");
loginPage.enterPassword("password");
loginPage.clickLogin();
```

## Component Responsibilities

### BaseTest
- ✓ WebDriver lifecycle (setup/teardown)
- ✓ Browser selection & configuration
- ✓ Cross-browser support
- ✗ Page interactions (not its job)
- ✗ Business logic (not its job)

### BasePage
- ✓ Common WebDriver operations
- ✓ Wait utilities
- ✓ Element finding with waits
- ✗ Page-specific locators (in subclasses)
- ✗ Test assertions (in tests)

### Page Objects (LoginPage, etc)
- ✓ Page-specific locators
- ✓ Page actions (user-facing)
- ✓ Verification helpers (isDisplayed, getText)
- ✗ Test assertions (in tests)
- ✗ WebDriver management (in BaseTest)

### Test Classes
- ✓ Test scenarios
- ✓ Assertions
- ✓ Test data
- ✗ WebDriver commands (use page objects)
- ✗ Locators (in page objects)

## POM Principles

### 1. Single Responsibility
Each class has ONE job:
- **BaseTest**: Manage WebDriver
- **BasePage**: Common operations
- **LoginPage**: Login page interactions
- **LoginTest**: Test login scenarios

### 2. Encapsulation
```java
// ✗ BAD - locator exposed
public By usernameField = By.id("username");

// ✓ GOOD - locator hidden
private final By usernameField = By.id("username");
public LoginPage enterUsername(String user) {
    type(usernameField, user);
    return this;
}
```

### 3. DRY (Don't Repeat Yourself)
```java
// ✗ BAD - repeated in every test
driver.findElement(By.id("username")).sendKeys("user");

// ✓ GOOD - reusable page method
loginPage.enterUsername("user");
```

### 4. Readability
```java
// ✗ BAD - unclear what's happening
driver.findElement(By.id("username")).sendKeys("tom");
driver.findElement(By.id("password")).sendKeys("pass");
driver.findElement(By.cssSelector("button")).click();

// ✓ GOOD - clear test intent
loginPage.login("tom", "pass");
```

### 5. Maintainability
```
Locator changes → Update once in page object → All tests work
New page element → Add to page object → Available everywhere
```
