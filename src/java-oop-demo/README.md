# Java OOP Demo

This project is a demonstration of Object-Oriented Programming (OOP) concepts using Java. It showcases the creation and management of `Person` objects through a service layer, along with utility methods for supporting operations.

## Project Structure

```
java-oop-demo
├── src
│   ├── main
│   │   └── java
│   │       └── com
│   │           └── example
│   │               ├── App.java
│   │               ├── model
│   │               │   └── Person.java
│   │               ├── service
│   │               │   └── PersonService.java
│   │               └── util
│   │                   └── Utils.java
│   └── test
│       └── java
│           └── com
│               └── example
│                   └── AppTest.java
├── pom.xml
└── README.md
```

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Maven

### Building the Project

To build the project, navigate to the project directory and run:

```
mvn clean install
```

### Running the Application

To run the application, execute the following command:

```
mvn exec:java -Dexec.mainClass="com.example.App"
```

### Usage

The application demonstrates OOP concepts by creating instances of the `Person` class and utilizing the `PersonService` to manage these instances. You can modify the `App.java` file to add more functionality or test different scenarios.

## Contributing

Feel free to fork the repository and submit pull requests for any improvements or features you would like to add.