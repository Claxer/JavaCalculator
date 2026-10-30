# Java Calculator Pro

A modern, Apple-inspired desktop calculator built with **Java 25, JavaFX, CSS, and Maven**. Java Calculator Pro features a clean dark interface designed for simple, convenient, and efficient calculations. The project is being expanded with scientific functions, calculation history, memory management, and customizable settings.

## Features

### Basic Calculator
- **Basic Arithmetic** — Addition, subtraction, multiplication, and division.
- **Percentage Calculations** — Perform percentage calculations.
- **Decimal Support** — Work with decimal numbers.
- **Sign Toggle** — Switch between positive and negative values.
- **Delete and Clear** — Remove the last character or reset the display.
- **Error Handling** — Handles invalid calculations and division by zero.

### Scientific Calculator
- **Trigonometric Functions** — Sine, cosine, and tangent.
- **Logarithmic Functions** — Logarithm (log) and natural logarithm (ln).
- **Square Root** — Calculate square roots.
- **Powers and Exponents** — Calculate squares and custom powers.
- **Factorial** — Calculate factorials of non-negative whole numbers.
- **Mathematical Constants** — Support for Pi (π) and Euler's number (e).
- **Parentheses** — Evaluate expressions with grouped operations.
- **Degree and Radian Modes** — Switch between DEG and RAD for trigonometric calculations.

### Calculation History
- **Automatic History Saving** — Save completed calculations.
- **Persistent History** — Keep previous calculations after closing the application.
- **Reusable Calculations** — Double-click a history entry to load its expression.
- **Clear History** — Remove saved calculation records.

### Memory Functions
- **MC** — Clear calculator memory.
- **MR** — Recall the stored value.
- **M+** — Add the current value to memory.
- **M−** — Subtract the current value from memory.

### Settings and Interface
- **Dark and Light Theme Options** — Switch between interface themes.
- **Decimal Precision** — Customize the number of decimal places.
- **Saved Preferences** — Store settings between application sessions.
- **Copy Results** — Copy displayed results to the clipboard.
- **Keyboard Support** — Use keyboard shortcuts for common actions.
- **Minimalist Design** — Charcoal backgrounds, grey buttons, and orange operators.

## Technologies Used

- Java 25
- JavaFX
- CSS
- Apache Maven
- Java Properties
- Java NIO File Handling

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

Maven will download the required dependencies, compile the project, and launch the calculator.

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
        │           ├── App.java
        │           ├── CalculatorEngine.java
        │           ├── HistoryManager.java
        │           ├── MemoryManager.java
        │           └── SettingsManager.java
        └── resources/
            └── com/
                └── calculator/
                    └── style.css
```

## Current Status

The project has been expanded beyond its original basic calculator implementation.

The supporting Java classes and upgraded application code now include scientific calculations, persistent history, memory functions, and customizable settings.

The upgraded interface and all features still need to be tested together to confirm that they work correctly in the running application.

## Learning Objectives

This project is designed to practice:

- Java fundamentals and object-oriented programming.
- Building desktop applications with JavaFX.
- Creating and managing user interfaces with CSS.
- Implementing mathematical operations and expression parsing.
- Working with collections and data management.
- Reading and writing local files.
- Saving application settings using Java Properties.
- Managing dependencies with Maven.
- Handling user input and calculation errors.

## Future Improvements

- More advanced scientific calculations.
- Improved percentage behavior.
- More flexible keyboard shortcuts.
- Undo and redo functionality.
- Calculation history search and management.
- Sound effects and additional interface customization.
- Improved expression validation and error messages.
- Additional calculator themes.

## License

This project is available for educational and personal portfolio purposes. A formal open-source license can be added when one is selected.
