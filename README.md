# Java Calculator Pro

A modern, Apple-inspired desktop calculator built with **Java, JavaFX, and CSS**. Java Calculator Pro features a clean dark interface designed for simple, convenient, and efficient calculations.

## Features

- **Basic Arithmetic** — Addition, subtraction, multiplication, and division.
- **Percentage Calculations** — Calculate percentages quickly.
- **Decimal Support** — Perform calculations with decimal numbers.
- **Sign Toggle** — Switch between positive and negative numbers.
- **Delete and Clear** — Remove the last digit or reset the calculator.
- **Keyboard Support** — Enter numbers and operators using your keyboard.
- **Dark Theme** — A minimalist interface with charcoal backgrounds and orange operator buttons.
- **Error Handling** — Displays an error when attempting to divide by zero.

## Technologies Used

- Java 25
- JavaFX
- CSS
- Apache Maven

## Requirements

Before running the application, make sure you have installed:

- Java Development Kit (JDK) 25
- Apache Maven
- Visual Studio Code or another Java-compatible IDE

## Installation and Setup

1. Clone or download this repository.
2. Open the project folder in Visual Studio Code.
3. Make sure Java and Maven are installed correctly.
4. Open a terminal in the project root directory.
5. Run the following command:

```bash
mvn clean javafx:run
```

Maven will download the required dependencies and launch the calculator.

## Project Structure

```text
JavaCalculator/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── calculator/
        │           └── App.java
        └── resources/
            └── com/
                └── calculator/
                    └── style.css
```

## Current Status

The current version includes the calculator interface and basic arithmetic functionality. Scientific functions, an expanded calculation history, and additional improvements are planned for future updates.

## Learning Objectives

This project is designed to practice:

- Java fundamentals and object-oriented programming.
- Building desktop interfaces with JavaFX.
- Styling applications with CSS.
- Managing dependencies with Maven.
- Handling user input and basic calculation errors.

## Future Improvements

- Scientific calculator mode.
- Calculation history with reusable results.
- Improved percentage behavior.
- More keyboard shortcuts.
- Additional interface customization.

## License

This project is available for educational and personal portfolio purposes. A formal open-source license can be added when one is selected.
