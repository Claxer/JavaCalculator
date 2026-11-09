package com.calculator;

import java.math.BigInteger;
import java.util.Locale;

public class ProgrammerModeManager {

    public static final int BINARY = 2;
    public static final int OCTAL = 8;
    public static final int DECIMAL = 10;
    public static final int HEXADECIMAL = 16;

    private static final int MAX_SHIFT = 4096;

    public BigInteger parseValue(String input, int radix) {
        validateRadix(radix);

        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Enter a valid number.");
        }

        String value = input.trim()
                .replace("_", "")
                .replace(" ", "");

        boolean negative = value.startsWith("-");

        if (negative || value.startsWith("+")) {
            value = value.substring(1);
        }

        String prefix = getPrefix(radix);

        if (!prefix.isEmpty()
                && value.toLowerCase(Locale.ROOT).startsWith(prefix)) {
            value = value.substring(prefix.length());
        }

        if (value.isEmpty()) {
            throw new IllegalArgumentException("Invalid number.");
        }

        try {
            BigInteger result = new BigInteger(value, radix);
            return negative ? result.negate() : result;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Invalid number for base " + radix + "."
            );
        }
    }

    public String formatValue(BigInteger value, int radix) {
        validateRadix(radix);

        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }

        return value.toString(radix).toUpperCase(Locale.ROOT);
    }

    public String convert(String input, int fromRadix, int toRadix) {
        BigInteger value = parseValue(input, fromRadix);
        return formatValue(value, toRadix);
    }

    public BigInteger bitwiseAnd(BigInteger a, BigInteger b) {
        return a.and(b);
    }

    public BigInteger bitwiseOr(BigInteger a, BigInteger b) {
        return a.or(b);
    }

    public BigInteger bitwiseXor(BigInteger a, BigInteger b) {
        return a.xor(b);
    }

    public BigInteger bitwiseNot(BigInteger value) {
        return value.not();
    }

    public BigInteger shiftLeft(BigInteger value, int positions) {
        validateShift(positions);
        return value.shiftLeft(positions);
    }

    public BigInteger shiftRight(BigInteger value, int positions) {
        validateShift(positions);
        return value.shiftRight(positions);
    }

    public BigInteger add(BigInteger a, BigInteger b) {
        return a.add(b);
    }

    public BigInteger subtract(BigInteger a, BigInteger b) {
        return a.subtract(b);
    }

    public BigInteger multiply(BigInteger a, BigInteger b) {
        return a.multiply(b);
    }

    public BigInteger divide(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) {
            throw new ArithmeticException("Cannot divide by zero.");
        }

        return a.divide(b);
    }

    public int getRadix(String mode) {
        if (mode == null) {
            throw new IllegalArgumentException("Invalid number mode.");
        }

        switch (mode.toUpperCase(Locale.ROOT)) {
            case "BIN":
            case "BINARY":
                return BINARY;

            case "OCT":
            case "OCTAL":
                return OCTAL;

            case "DEC":
            case "DECIMAL":
                return DECIMAL;

            case "HEX":
            case "HEXADECIMAL":
                return HEXADECIMAL;

            default:
                throw new IllegalArgumentException(
                        "Unknown number system: " + mode
                );
        }
    }

    public String getModeName(int radix) {
        switch (radix) {
            case BINARY:
                return "BIN";

            case OCTAL:
                return "OCT";

            case DECIMAL:
                return "DEC";

            case HEXADECIMAL:
                return "HEX";

            default:
                throw new IllegalArgumentException(
                        "Unsupported number system."
                );
        }
    }

    private String getPrefix(int radix) {
        switch (radix) {
            case BINARY:
                return "0b";

            case OCTAL:
                return "0o";

            case HEXADECIMAL:
                return "0x";

            default:
                return "";
        }
    }

    private void validateRadix(int radix) {
        if (radix != BINARY
                && radix != OCTAL
                && radix != DECIMAL
                && radix != HEXADECIMAL) {
            throw new IllegalArgumentException(
                    "Supported bases are 2, 8, 10, and 16."
            );
        }
    }

    private void validateShift(int positions) {
        if (positions < 0 || positions > MAX_SHIFT) {
            throw new IllegalArgumentException(
                    "Shift amount must be between 0 and "
                            + MAX_SHIFT + "."
            );
        }
    }
}
