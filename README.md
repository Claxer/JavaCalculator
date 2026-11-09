# Java Calculator Pro

A modern, Apple-inspired desktop calculator built with **Java 25, JavaFX, CSS, and Maven**. Java Calculator Pro features a clean, customizable interface designed for simple, convenient, and efficient calculations. It includes basic arithmetic, scientific functions, calculation history, memory management, customizable settings, and a dedicated Programmer Mode for number-system conversions and bitwise operations.

The project is continuously being expanded with additional functionality and interface improvements.

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

### Programmer Mode
- **Number System Conversion** — Convert values between Binary, Octal, Decimal, and Hexadecimal.
- **Binary Support** — Work with base-2 number representations.
- **Octal Support** — Work with base-8 number representations.
- **Decimal Support** — Work with base-10 integer values.
- **Hexadecimal Support** — Work with base-16 number representations.
- **Bitwise AND** — Perform bitwise AND operations.
- **Bitwise OR** — Perform bitwise OR operations.
- **Bitwise XOR** — Perform bitwise exclusive OR operations.
- **Bitwise NOT** — Invert the bits of an integer.
- **Bit Shifting** — Perform left-shift and right-shift operations.
- **Integer Arithmetic** — Addition, subtraction, multiplication, and division using integer values.
- **Input Validation** — Validate number-system inputs and handle invalid values.
- **Large Integer Support** — Uses Java's `BigInteger` for integer-based programmer calculations.

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
- **Modern CSS Styling** — Updated dark and light themes with improved colors, spacing, borders, and typography.
- **Interactive Buttons** — Rounded buttons with hover and pressed visual states.
- **Improved Calculator Display** — Refined display styling with better readability.
- **Styled Interface Controls** — Updated text fields, combo boxes, checkboxes, history lists, and scrollbars.
- **Minimalist Design** — Charcoal backgrounds, subtle grey panels, and warm orange operator accents.

## Technologies Used

- Java 25
- JavaFX
- CSS
- Apache Maven
- Java Properties
- Java NIO File Handling
- Java BigInteger

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
        │           ├── SettingsManager.java
        │           ├── ProgrammerModeManager.java
        │           └── ProgrammerModeWindow.java
        └── resources/
            └── com/
                └── calculator/
                    └── style.css
```

## Current Status

Java Calculator Pro has grown beyond its original basic calculator implementation.

The project currently includes:

- Basic arithmetic and scientific calculations.
- Expression parsing and mathematical functions.
- Persistent calculation history.
- Calculator memory management.
- Customizable settings and saved preferences.
- A dedicated Programmer Mode window.
- Number-system conversion and bitwise operations.
- An upgraded CSS theme for a more polished desktop interface.

The project remains under active development. Newly added features and interface changes should be tested together in the running application to verify their behavior.

## Learning Objectives

This project is designed to practice:

- Java fundamentals and object-oriented programming.
- Building desktop applications with JavaFX.
- Creating and managing user interfaces with CSS.
- Implementing mathematical operations and expression parsing.
- Working with collections and data management.
- Using BigInteger for large integer calculations.
- Implementing number-system conversions and bitwise operations.
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
- More advanced Programmer Mode features.
- Sound effects and additional interface customization.
- Improved expression validation and error messages.
- Additional calculator themes.

## License

This project is available for educational and personal portfolio purposes. A formal open-source license can be added when one is selected.
