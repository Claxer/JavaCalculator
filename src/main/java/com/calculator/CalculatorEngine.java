package com.calculator;

public class CalculatorEngine {

    private boolean degreeMode = true;

    public void setDegreeMode(boolean degreeMode) {
        this.degreeMode = degreeMode;
    }

    public boolean isDegreeMode() {
        return degreeMode;
    }

    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero.");
        }
        return a / b;
    }

    public double percentage(double value) {
        return value / 100.0;
    }

    public double square(double value) {
        return Math.pow(value, 2);
    }

    public double squareRoot(double value) {
        if (value < 0) {
            throw new ArithmeticException("Cannot calculate the square root of a negative number.");
        }
        return Math.sqrt(value);
    }

    public double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    public double reciprocal(double value) {
        if (value == 0) {
            throw new ArithmeticException("Cannot divide by zero.");
        }
        return 1.0 / value;
    }

    public double sin(double value) {
        return Math.sin(toRadians(value));
    }

    public double cos(double value) {
        return Math.cos(toRadians(value));
    }

    public double tan(double value) {
        double radians = toRadians(value);

        if (Math.abs(Math.cos(radians)) < 1e-10) {
            throw new ArithmeticException("Tangent is undefined at this angle.");
        }

        return Math.tan(radians);
    }

    public double log(double value) {
        if (value <= 0) {
            throw new ArithmeticException("Logarithm requires a positive number.");
        }
        return Math.log10(value);
    }

    public double naturalLog(double value) {
        if (value <= 0) {
            throw new ArithmeticException("Natural logarithm requires a positive number.");
        }
        return Math.log(value);
    }

    public double factorial(double value) {
        if (value < 0 || value != Math.floor(value)) {
            throw new ArithmeticException("Factorial requires a non-negative whole number.");
        }

        if (value > 170) {
            throw new ArithmeticException("Number is too large for factorial.");
        }

        double result = 1;

        for (int i = 1; i <= (int) value; i++) {
            result *= i;
        }

        return result;
    }

    public double getPi() {
        return Math.PI;
    }

    public double getEulerNumber() {
        return Math.E;
    }

    private double toRadians(double value) {
        return degreeMode ? Math.toRadians(value) : value;
    }

    public String formatResult(double result) {
        if (!Double.isFinite(result)) {
            return "Error";
        }

        if (result == Math.floor(result) && Math.abs(result) < 1e15) {
            return String.format("%.0f", result);
        }

        return String.format("%.10f", result)
                .replaceAll("0+$", "")
                .replaceAll("\\.$", "");
    }
}
