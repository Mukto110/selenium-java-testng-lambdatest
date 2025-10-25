# Selenium E-Commerce Test Automation Framework

A modern, scalable test automation framework built with **Java**, **Selenium WebDriver**, and **TestNG** to demonstrate professional QA engineering practices. This project showcases comprehensive testing of an e-commerce application with parallel execution, method chaining, and industry-standard design patterns.

---

## 📋 Table of Contents

- [Why This Framework?](#-why-this-framework)
- [Tech Stack](#️-tech-stack)
- [Framework Architecture](#️-framework-architecture)
- [Key Features](#-key-features)
- [Project Structure](#-project-structure)
- [Getting Started](#-getting-started)
- [Running Tests](#️-running-tests)
- [Reports & Logging](#-reports--logging)
- [What I Learned](#-what-i-learned)

---

## 💡 Why This Framework?

I built this framework from scratch to demonstrate my ability to design robust, maintainable test automation solutions. The focus was on:

- **Scalability**: Parallel execution to handle large test suites efficiently
- **Maintainability**: Clean code with POM and reusable components
- **Real-world readiness**: Comprehensive reporting, logging, and data-driven testing
- **Best practices**: Following industry standards and proven design patterns

This isn't just a framework—it's a showcase of how I approach test automation challenges.

### 📝 From Manual to Automated Testing

**Here's what makes this project unique:** I didn't just build a random automation framework. I started with a complete **manual testing project** on the LambdaTest Playground e-commerce application, where I:

1. **Designed comprehensive test scenarios** covering all critical user flows
2. **Documented detailed test cases** in a structured Google Sheet
3. **Executed manual testing** to understand the application behavior thoroughly
4. **Then automated selected test cases** from my manual suite using this framework

This approach gave me a solid foundation before writing a single line of automation code. I knew exactly what to test, what edge cases to cover, and what the expected behavior should be. **This is how real-world QA works** - you understand the application manually first, then strategically automate the repetitive and critical test cases.

**The result?** Automated tests that are meaningful, comprehensive, and based on real testing experience rather than just technical exercises.

> 📂 **Want to see my complete manual testing work?**  
> Check out my [Manual Testing Project Repository](https://github.com/Mukto110/manual-testing-project-ecommerce) with detailed test scenarios, test cases, and bug reports.

---

## 🛠️ Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 25 | Programming Language |
| Selenium WebDriver | 4.36.0 | Browser Automation |
| TestNG | 7.11.0 | Test Runner & Orchestration |
| Maven | - | Build & Dependency Management |
| WebDriverManager | Latest | Automatic Driver Management |
| ExtentReports | 5.1.2 | Test Reporting |
| Log4j2 | Latest | Logging Framework |
| Apache POI | Latest | Excel Data Handling |

---

## 🏗️ Framework Architecture

### Page Object Model (POM) with Method Chaining

The framework follows the **Page Object Model** design pattern, where each web page is represented as a Java class. But I took it a step further by implementing **method chaining** to make tests more readable and fluent.
```java
// Example of method chaining in action
loginPage
    .enterEmail("user@example.com")
    .enterPassword("securePass123")
    .clickLoginButton()
    .verifySuccessfulLogin();
```

I implemented this pattern by studying these excellent resources:
- [Selenium Official Documentation on POM](https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/)
- [Method Chaining in Page Object Model](https://test-automation.blog/selenium/method-chaining-page-object-model/)

**Benefits of this approach:**
- Tests read like plain English
- Reduced boilerplate code
- Better IDE autocomplete support
- Easier to chain multiple actions in a single flow

### Thread-Safe Parallel Execution

From day one, I designed this framework with **parallel execution** in mind. I used `ThreadLocal<WebDriver>` to ensure each test thread gets its own isolated browser session.

**Why ThreadLocal instead of regular WebDriver instances?**

✅ **Thread Safety**: Each parallel test runs in complete isolation  
✅ **No Race Conditions**: Tests never interfere with each other  
✅ **Faster Execution**: Run 5-10 tests simultaneously without issues  
✅ **Resource Efficiency**: Better memory management and cleanup  

Without ThreadLocal, parallel tests would fight over the same WebDriver instance, causing random failures and flaky tests. With ThreadLocal, each thread maintains its own WebDriver, making parallel execution rock-solid.

### Component-Based Page Architecture

Instead of monolithic page classes, I broke down pages into **reusable components**:

- `Header` component → Reused across all pages
- `CartModal` component → Consistent cart behavior
- `Sidebar` component → Navigation elements

This composition approach means if the header changes, I update it in one place, and all pages automatically inherit the fix.

---

## ✨ Key Features

### 🔄 Parallel & Cross-Browser Testing
- Run tests concurrently to slash execution time
- Support for Chrome, Firefox, Edge, and Safari
- Headless mode for CI/CD pipelines
- Thread-safe execution using `ThreadLocal<WebDriver>`

### 📊 Data-Driven Testing
- Excel-based test data management with Apache POI
- Dynamic test data generation for negative scenarios
- TestNG `@DataProvider` integration for parameterized tests
- Centralized test data in `TestData` class

### 📝 Advanced Reporting & Logging
- **ExtentReports**: Beautiful, interactive HTML reports with screenshots
- **Log4j2**: Detailed logs in both file (`automation.log`) and console
- **Automatic Screenshots**: Captured on test failure and attached to reports
- **Test Listener**: Tracks test status (pass/fail/skip) in real-time

### 🧰 Robust Utility Classes
- `WebDriverFactory`: Complete WebDriver lifecycle management
- `ElementActions`: Wrapper with built-in explicit waits
- `WaitUtils`: Reusable wait conditions for element states
- `AssertUtils`: Custom assertions with hard/soft modes
- `ExcelUtility`: Simplified Excel data reading

### 🎯 Smart Test Organization
- Tests grouped by modules: `auth`, `cart`, `search`
- TestNG groups for flexible execution: `smoke`, `regression`, `negative`, `ui`
- Master suite files for different test scenarios
- Clear separation of test logic from page interactions

---

## 📁 Project Structure
```
selenium-ecommerce-framework/
│
├── src/main/java/
│   ├── base/                    # BasePage, BaseTest
│   ├── pages/                   # Page Object classes
│   ├── components/              # Reusable UI components
│   ├── utils/                   # Utility classes
│   └── config/                  # Configuration readers
│
├── src/main/resources/
│   ├── config.properties        # Environment config
│   ├── testdata.properties      # Static test data
│   ├── log4j2.xml               # Logging configuration
│   └── data/
│       └── LoginData.xlsx       # Test data for data-driven tests
│
├── src/test/java/
│   ├── auth/                    # Login/Authentication tests
│   ├── cart/                    # Shopping cart tests
│   └── search/                  # Search functionality tests
│
├── src/test/resources/
│   ├── testng-master.xml        # Master test suite
│   └── testng-dataDriven.xml    # Data-driven test suite
│
├── logs/                        # Auto-generated logs
├── screenshots/                 # Failure screenshots
├── test-output/                 # ExtentReports output
└── pom.xml                      # Maven dependencies
```

---

## Getting Started

### Prerequisites

- Java JDK 17 or higher
- Maven 3.6+
- Your favorite IDE (IntelliJ IDEA recommended)

### Installation

1. **Clone the repository**
```bash
   git clone https://github.com/Mukto110/selenium-java-testng-lambdatest.git
   cd selenium-java-testng-lambdatest
```

2. **Install dependencies**
```bash
   mvn clean install
```

3. **Update configuration** (if needed)
   
   Edit `src/main/resources/config.properties` to set your application URL or other environment-specific values.

---

## ▶️ Running Tests

### Run All Tests (Master Suite)
```bash
mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testng-master.xml
```

### Run Specific Test Groups
```bash
# Smoke tests only
mvn clean test -Dgroups=smoke

# Regression tests
mvn clean test -Dgroups=regression

# Negative tests
mvn clean test -Dgroups=negative
```

### Cross-Browser Testing
```bash
# Run on Firefox
mvn clean test -Dbrowser=firefox

# Run on Edge
mvn clean test -Dbrowser=edge
```

### Headless Mode (CI/CD)
```bash
mvn clean test -Dheadless=true
```

### Run Data-Driven Tests
```bash
mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testng-dataDriven.xml
```

---

## 📈 Reports & Logging

### ExtentReports
After test execution, open the HTML report (Open the specified HTML report file in the default web browser):
```
open reports/Test-Report-2025.10.25.14.21.15.html
```

The report includes:
- Test execution summary with pass/fail counts
- Detailed logs for each test step
- Screenshots attached to failed tests
- Execution timeline and duration

### Log Files
Check detailed logs in:
```
logs/automation.log
```

Logs include:
- Test start/end markers
- WebDriver actions
- Explicit wait operations
- Error stack traces

---

## 🎓 What I Learned

Building this framework taught me a lot about:

- **Design Patterns**: How POM with method chaining improves test readability and maintenance
- **Concurrency**: Why `ThreadLocal` is crucial for parallel test execution
- **Test Architecture**: Breaking down pages into reusable components
- **CI/CD Readiness**: Configuring headless mode and flexible test execution
- **Professional Reporting**: Making test results presentable for stakeholders

The biggest takeaway? **Good test automation is 80% framework design and 20% writing tests.** A solid foundation makes everything else easier.

---

## 🤝 Contributing

This is a personal showcase project, but I'm always open to feedback and suggestions! Feel free to:

- Open an issue for bugs or improvements
- Fork the repo and experiment with your own ideas
- Reach out if you want to discuss test automation practices

---

## 📬 Contact

**Your Name**  
📧 merajhossainmukto@gmail.com 
💼 [LinkedIn]([https://linkedin.com/in/yourprofile](https://www.linkedin.com/in/meraj-hossain-mukto-05aa4a275/))  
🐙 [GitHub](https://github.com/Mukto110)

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

---

<div align="center">

**⭐ If this framework helped you learn something new, give it a star! ⭐**

Made with ☕ and lots of debugging sessions

</div>
