# Selenium Java Project

## Selenium Java Automation Framework (Enterprise-Grade)

## Overview
This repository contains a modular, enterprise-grade automation framework for web and API testing using Java, Selenide, Selenium, JUnit, REST Assured, WireMock, and Allure. It is designed for maintainability, scalability, reporting, and CI/CD integration.

## Features
- **Modular Page Object Model (POM)** for UI tests
- **Data-driven testing** using external files (CSV, JSON, Excel)
- **API contract validation** with OpenAPI/Swagger
- **Advanced reporting** with Allure
- **Mocking/stubbing** with WireMock
- **Cross-browser support** (Chrome, Firefox, Edge)
- **Robust overlay/consent handling** for dynamic web pages
- **CI/CD ready** (GitHub Actions, artifact uploads)
- **Best practices for maintainability and scalability**

## Project Structure
```
selenium-java-project/
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/example/
│   │           ├── model/         # Domain models (Person, Student, etc.)
│   │           ├── service/       # Business logic/services
│   │           ├── util/          # Utilities (data, config, helpers)
│   │           └── App.java       # Main entry point (if needed)
│   └── test/
│       └── java/
│           └── com/example/tests/
│               ├── BaseTest.java
│               ├── SelenideBaseTest.java
│               ├── SeleniumTest.java
│               ├── SelenideGoogleSearchTest.java
│               ├── GoogleHomePage.java
│               └── ApiTest.java
├── target/
│   └── ... (build output)
```
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/example/
│   │           ├── model/         # Domain models (Person, Student, etc.)
│   │           ├── service/       # Business logic/services
│   │           ├── util/          # Utilities (data, config, helpers)
│   │           └── App.java       # Main entry point (if needed)
│   └── test/
│       └── java/
│           └── com/example/tests/
│               ├── BaseTest.java
│               ├── SelenideBaseTest.java
│               ├── SeleniumTest.java
│               ├── SelenideGoogleSearchTest.java
│               ├── GoogleHomePage.java
│               └── ApiTest.java
├── target/
│   └── ... (build output)
```

## Getting Started
### Prerequisites
- Java 11 or higher
- Maven 3.6+
- Chrome, Firefox, or Edge browser installed

### Setup
1. **Clone the repository:**
   ```sh
   git clone <repo-url>
   cd selenium-java-project
   ```
2. **Install dependencies:**
   ```sh
   mvn clean install
   ```
3. **Run all tests:**
   ```sh
   mvn test
   ```
4. **Generate Allure report:**
   ```sh
   mvn allure:serve
   ```

## Key Components
### 1. Page Object Model (POM)
- Each page is represented by a class (e.g., `GoogleHomePage.java`).
- Encapsulates selectors, actions, and overlay handling.
- Promotes reusability and maintainability.

### 2. Data-Driven Testing
- Test data can be loaded from CSV, JSON, or Excel files.
- Example: Parameterized JUnit tests or reading data in test setup.

### 3. API Testing & Contract Validation
- Use REST Assured for API tests (`ApiTest.java`).
- Validate responses against OpenAPI/Swagger schemas.
- Mock external services with WireMock.

### 4. Reporting
- Allure integrated for rich test reports.
- Attach screenshots, logs, and API responses.

### 5. CI/CD Integration
- Example GitHub Actions workflow for build, test, and report upload.
- Artifacts: Allure reports, test logs, screenshots.

### 6. Cross-Browser Support
- Easily switch browsers via Selenide config or WebDriverManager.
- Example: `Configuration.browser = "firefox";`

<<<<<<< HEAD
## Usage Guidelines
- The `SeleniumTest.java` file is where you will write your Selenium test cases to interact with web applications.
=======
### 7. Robust Overlay Handling
- Page objects include logic to dismiss overlays, consent dialogs, and promos.
- Ensures tests are resilient to dynamic content and popups.
>>>>>>> Extended README and framework improvements

## Best Practices
- Use POM for all UI tests.
- Keep test data externalized.
- Use assertions with clear error messages.
- Modularize business logic and utilities.
- Use Allure for reporting and attach artifacts.
- Mock APIs for isolated testing.
- Integrate with CI/CD for automation.
- Regularly update dependencies for browser compatibility.

## Example Test: Google Search (Selenide)
```java
@Test
public void testGoogleSearch() {
    GoogleHomePage page = new GoogleHomePage();
    page.open();
    page.search("Selenide Java");
    assert page.getTitle().contains("Selenide");
}
```

## Example Test: API Contract Validation
```java
@Test
public void testGetUserApiContract() {
    given()
        .baseUri("https://api.example.com")
        .when()
        .get("/user/123")
        .then()
        .assertThat()
        .body(matchesJsonSchemaInClasspath("user-schema.json"));
}
```

## Advanced: Data-Driven Test Example
```java
@ParameterizedTest
@CsvFileSource(resources = "/test-data.csv", numLinesToSkip = 1)
void testLogin(String username, String password, boolean expectedSuccess) {
    LoginPage loginPage = new LoginPage(driver);
    loginPage.open();
    loginPage.setUsername(username);
    loginPage.setPassword(password);
    loginPage.submit();
    assertEquals(expectedSuccess, loginPage.isSuccessMessagePresent());
}
```

## CI/CD Example (GitHub Actions)
```yaml
name: Java Selenium CI
on: [push, pull_request]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'
      - name: Build and Test
        run: mvn clean test
      - name: Generate Allure Report
        run: mvn allure:report
      - name: Upload Allure Report
        uses: actions/upload-artifact@v3
        with:
          name: allure-report
          path: target/allure-report
```

## Troubleshooting
- **Element not found:** Check overlay handling and selectors.
- **Browser compatibility:** Update WebDriverManager and browser drivers.
- **Test data issues:** Validate external data files for format and encoding.
- **API contract failures:** Update schemas and validate endpoints.

## Contributing
- Fork the repo and create a feature branch.
- Submit pull requests with clear descriptions.
- Follow code style and documentation standards.

## License
<<<<<<< HEAD
This project is licensed under the MIT License. See the LICENSE file for more details.
=======
MIT

## Authors
- Your Name (your.email@example.com)
- Contributors listed in repo

---
For more details, see the code comments and individual test files.
>>>>>>> Extended README and framework improvements
