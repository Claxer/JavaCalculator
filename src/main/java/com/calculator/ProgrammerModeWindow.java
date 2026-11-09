package com.calculator;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.math.BigInteger;

public class ProgrammerModeWindow {

    private final ProgrammerModeManager manager =
            new ProgrammerModeManager();

    private final Stage window = new Stage();

    private TextField inputField;
    private TextField secondInputField;
    private TextField shiftField;

    private ComboBox<String> inputMode;
    private ComboBox<String> outputMode;

    private Label resultLabel;
    private Label statusLabel;

    public void show(Stage owner) {
        window.initOwner(owner);
        window.initModality(Modality.NONE);
        window.setTitle("Programmer Mode");

        VBox main = new VBox(16);
        main.setPadding(new Insets(22));
        main.setStyle(
                "-fx-background-color: #181818;"
        );

        Label title = new Label("Programmer Mode");
        title.setStyle(
                "-fx-font-size: 23px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;"
        );

        Label subtitle = new Label(
                "Number conversion and bitwise operations"
        );
        subtitle.setStyle("-fx-text-fill: #999999;");

        main.getChildren().addAll(
                title,
                subtitle,
                createConversionSection(),
                createBitwiseSection(),
                createArithmeticSection()
        );

        Scene scene = new Scene(main, 520, 700);

        window.setScene(scene);
        window.setMinWidth(480);
        window.setMinHeight(650);
        window.show();
    }

    private VBox createConversionSection() {
        VBox section = createSection("Number System Converter");

        inputField = createTextField("Enter a number");

        inputMode = new ComboBox<>();
        inputMode.getItems().addAll(
                "BIN", "OCT", "DEC", "HEX"
        );
        inputMode.setValue("DEC");

        outputMode = new ComboBox<>();
        outputMode.getItems().addAll(
                "BIN", "OCT", "DEC", "HEX"
        );
        outputMode.setValue("BIN");

        Button convertButton = createButton("Convert");
        convertButton.setOnAction(event -> convertNumber());

        resultLabel = new Label("Result will appear here");
        resultLabel.setWrapText(true);
        resultLabel.setStyle(
                "-fx-text-fill: #ff9f43;" +
                "-fx-font-size: 17px;" +
                "-fx-font-weight: bold;"
        );

        section.getChildren().addAll(
                new Label("Input"),
                inputField,
                new Label("Convert from"),
                inputMode,
                new Label("Convert to"),
                outputMode,
                convertButton,
                resultLabel
        );

        return section;
    }

    private VBox createBitwiseSection() {
        VBox section = createSection("Bitwise Operations");

        secondInputField = createTextField(
                "Enter second number"
        );

        Button andButton = createButton("AND");
        Button orButton = createButton("OR");
        Button xorButton = createButton("XOR");
        Button notButton = createButton("NOT");

        andButton.setOnAction(event -> performBitwise("AND"));
        orButton.setOnAction(event -> performBitwise("OR"));
        xorButton.setOnAction(event -> performBitwise("XOR"));
        notButton.setOnAction(event -> performBitwise("NOT"));

        HBox buttons = new HBox(
                8, andButton, orButton, xorButton, notButton
        );

        buttons.setAlignment(Pos.CENTER_LEFT);

        section.getChildren().addAll(
                new Label("Second operand"),
                secondInputField,
                buttons
        );

        return section;
    }

    private VBox createArithmeticSection() {
        VBox section = createSection("Arithmetic and Bit Shifting");

        shiftField = createTextField("Shift positions");

        Button add = createButton("Add");
        Button subtract = createButton("Subtract");
        Button multiply = createButton("Multiply");
        Button divide = createButton("Divide");

        Button leftShift = createButton("Shift Left");
        Button rightShift = createButton("Shift Right");

        add.setOnAction(event -> performArithmetic("ADD"));
        subtract.setOnAction(event -> performArithmetic("SUBTRACT"));
        multiply.setOnAction(event -> performArithmetic("MULTIPLY"));
        divide.setOnAction(event -> performArithmetic("DIVIDE"));

        leftShift.setOnAction(event -> performShift(true));
        rightShift.setOnAction(event -> performShift(false));

        GridPane buttons = new GridPane();
        buttons.setHgap(8);
        buttons.setVgap(8);

        buttons.add(add, 0, 0);
        buttons.add(subtract, 1, 0);
        buttons.add(multiply, 0, 1);
        buttons.add(divide, 1, 1);
        buttons.add(leftShift, 0, 2);
        buttons.add(rightShift, 1, 2);

        statusLabel = new Label("Ready");
        statusLabel.setWrapText(true);
        statusLabel.setStyle("-fx-text-fill: #bbbbbb;");

        section.getChildren().addAll(
                new Label("Shift positions"),
                shiftField,
                buttons,
                statusLabel
        );

        return section;
    }

    private VBox createSection(String heading) {
        VBox section = new VBox(9);

        section.setPadding(new Insets(15));
        section.setStyle(
                "-fx-background-color: #242424;" +
                "-fx-background-radius: 10;"
        );

        Label label = new Label(heading);
        label.setStyle(
                "-fx-text-fill: white;" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;"
        );

        section.getChildren().add(label);

        return section;
    }

    private TextField createTextField(String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setStyle(
                "-fx-background-color: #333333;" +
                "-fx-text-fill: white;" +
                "-fx-prompt-text-fill: #888888;" +
                "-fx-background-radius: 6;" +
                "-fx-padding: 9;"
        );

        return field;
    }

    private Button createButton(String text) {
        Button button = new Button(text);

        button.setMaxWidth(Double.MAX_VALUE);
        button.setStyle(
                "-fx-background-color: #f39c3d;" +
                "-fx-text-fill: #171717;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 6;" +
                "-fx-padding: 9 13;"
        );

        return button;
    }

    private void convertNumber() {
        try {
            int from = manager.getRadix(inputMode.getValue());
            int to = manager.getRadix(outputMode.getValue());

            String result = manager.convert(
                    inputField.getText(),
                    from,
                    to
            );

            resultLabel.setText(
                    outputMode.getValue() + ": " + result
            );

            statusLabel.setText("Conversion successful.");

        } catch (Exception e) {
            showError(e);
        }
    }

    private void performBitwise(String operation) {
        try {
            int radix = manager.getRadix(inputMode.getValue());

            BigInteger first = manager.parseValue(
                    inputField.getText(), radix
            );

            BigInteger result;

            if (operation.equals("NOT")) {
                result = manager.bitwiseNot(first);
            } else {
                BigInteger second = manager.parseValue(
                        secondInputField.getText(), radix
                );

                switch (operation) {
                    case "AND":
                        result = manager.bitwiseAnd(first, second);
                        break;

                    case "OR":
                        result = manager.bitwiseOr(first, second);
                        break;

                    case "XOR":
                        result = manager.bitwiseXor(first, second);
                        break;

                    default:
                        return;
                }
            }

            resultLabel.setText(
                    "Result: " + manager.formatValue(result, radix)
            );

            statusLabel.setText(
                    operation + " operation successful."
            );

        } catch (Exception e) {
            showError(e);
        }
    }

    private void performArithmetic(String operation) {
        try {
            int radix = manager.getRadix(inputMode.getValue());

            BigInteger first = manager.parseValue(
                    inputField.getText(), radix
            );

            BigInteger second = manager.parseValue(
                    secondInputField.getText(), radix
            );

            BigInteger result;

            switch (operation) {
                case "ADD":
                    result = manager.add(first, second);
                    break;

                case "SUBTRACT":
                    result = manager.subtract(first, second);
                    break;

                case "MULTIPLY":
                    result = manager.multiply(first, second);
                    break;

                case "DIVIDE":
                    result = manager.divide(first, second);
                    break;

                default:
                    return;
            }

            resultLabel.setText(
                    "Result: " + manager.formatValue(result, radix)
            );

            statusLabel.setText(
                    operation + " operation successful."
            );

        } catch (Exception e) {
            showError(e);
        }
    }

    private void performShift(boolean left) {
        try {
            int radix = manager.getRadix(inputMode.getValue());

            BigInteger value = manager.parseValue(
                    inputField.getText(), radix
            );

            int positions = Integer.parseInt(
                    shiftField.getText().trim()
            );

            BigInteger result = left
                    ? manager.shiftLeft(value, positions)
                    : manager.shiftRight(value, positions);

            resultLabel.setText(
                    "Result: " + manager.formatValue(result, radix)
            );

            statusLabel.setText(
                    left ? "Left shift successful."
                            : "Right shift successful."
            );

        } catch (Exception e) {
            showError(e);
        }
    }

    private void showError(Exception e) {
        statusLabel.setText(
                "Error: " + (
                        e.getMessage() == null
                                ? "Invalid operation."
                                : e.getMessage()
                )
        );
    }
}
