# Planit Test Automation

## Overview

This project is an automated UI testing framework built using Selenium WebDriver, Java, TestNG, and Maven. It follows the Page Object Model (POM) design pattern and is integrated with Jenkins for Continuous Integration.

## Tech Stack

- Java
- Selenium WebDriver
- TestNG
- Maven
- Jenkins

## Project Structure

```text
src
 ├── main
 │    ├── pages
 │    ├── utilities
 │    └── base
 ├── test
 │    └── tests
pom.xml
testng.xml
```

## Prerequisites

- Java 21
- Maven
- Google Chrome

## Installation

Clone the repository:

```bash
git clone <repository-url>
```

Navigate to the project:

```bash
cd PlanitTestAutomation
```

Install dependencies:

```bash
mvn clean install
```

## Running Tests

Run with parameters:

```bash
clean test -DplatformName=chrome
```

## Jenkins

The project can be executed through Jenkins by creating a Maven job and executing:

```bash
clean test -DplatformName=chrome
```

## Reports

TestNG reports are generated in:

```text
test-output/ExtentReport/ExtentReport.html
```

## Email Notification

After the test execution completes, the framework automatically sends an HTML email containing:

- Execution summary
- Total tests executed
- Passed, failed, and skipped test counts
- Execution duration
- Environment details
- Report attachment/link (if configured)

This enables stakeholders to review the test execution results without manually accessing the generated reports.

## Design Pattern

- Page Object Model (POM)
- Base Test
- Utility Classes
- Configurable execution using Maven system properties
