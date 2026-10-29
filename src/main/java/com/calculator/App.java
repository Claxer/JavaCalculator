
package com.calculator;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.DecimalFormat;

public class App extends Application {

    private TextField display;
    private Label expressionLabel;

    private double firstNumber = 0;
    private String operator = "";
    private boolean startNewNumber = true;

    private final DecimalFormat format =
            new DecimalFormat("0.##########");

    @Override
    public void start(Stage stage) {

        Label title = new Label("Calculator");
        title.getStyleClass().add("app-title");

        expressionLabel = new Label("Ready");
        expressionLabel.getStyleClass().add("expression");

        display = new TextField("0");
        display.setEditable(false);
        display.setFocusTraversable(false);
        display.setAlignment(Pos.CENTER_RIGHT);
        display.getStyleClass().add("display");

        VBox displayBox = new VBox(
                8, title, expressionLabel, display
        );
        displayBox.setAlignment(Pos.CENTER_RIGHT);
        displayBox.setPadding(new Insets(20, 0, 22, 0));

        String[][] keys = {
                {"AC", "DEL", "%", "÷"},
                {"7", "8", "9", "×"},
                {"4", "5", "6", "−"},
                {"1", "2", "3", "+"},
                {"+/−", "0", ".", "="}
        };

        GridPane keypad = new GridPane();
        keypad.setHgap(10);
        keypad.setVgap(10);
        keypad.setAlignment(Pos.CENTER);

        for (int row = 0; row < keys.length; row++) {
            for (int col = 0; col < keys[row].length; col++) {

                String key = keys[row][col];
                Button button = new Button(key);

                button.setMaxSize(
                        Double.MAX_VALUE, Double.MAX_VALUE
                );
                button.setMinSize(60, 60);

                if (key.equals("AC") || key.equals("DEL")
                        || key.equals("%") || key.equals("+/−")) {
                    button.getStyleClass().add("utility-button");
                } else if ("÷×−+".contains(key)) {
                    button.getStyleClass().add("operator-button");
                } else {
                    button.getStyleClass().add("number-button");
                }

                if (key.equals("=")) {
                    button.getStyleClass().add("equals-button");
                }

                button.setOnAction(event -> handleInput(key));
                keypad.add(button, col, row);
            }
        }

        for (int col = 0; col < 4; col++) {
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setPercentWidth(25);
            constraints.setHgrow(Priority.ALWAYS);
            keypad.getColumnConstraints().add(constraints);
        }

        BorderPane root = new BorderPane();
        root.setTop(displayBox);
        root.setCenter(keypad);
        root.setPadding(new Insets(22));
        root.getStyleClass().add("calculator-root");

        Scene scene = new Scene(root, 380, 560);

        var css = getClass().getResource(
                "/com/calculator/style.css"
        );

        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        }

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER, EQUALS -> handleInput("=");
                case BACK_SPACE -> handleInput("DEL");
                case ESCAPE -> handleInput("AC");
                case DIGIT0, NUMPAD0 -> handleInput("0");
                case DIGIT1, NUMPAD1 -> handleInput("1");
                case DIGIT2, NUMPAD2 -> handleInput("2");
                case DIGIT3, NUMPAD3 -> handleInput("3");
                case DIGIT4, NUMPAD4 -> handleInput("4");
                case DIGIT5, NUMPAD5 -> handleInput("5");
                case DIGIT6, NUMPAD6 -> handleInput("6");
                case DIGIT7, NUMPAD7 -> handleInput("7");
                case DIGIT8, NUMPAD8 -> handleInput("8");
                case DIGIT9, NUMPAD9 -> handleInput("9");
                case PERIOD, DECIMAL -> handleInput(".");
                case ADD -> handleInput("+");
                case SUBTRACT -> handleInput("−");
                case MULTIPLY -> handleInput("×");
                case DIVIDE -> handleInput("÷");
                default -> {
                    if ("%".equals(event.getText())) {
                        handleInput("%");
                    }
                }
            }
        });

        stage.setTitle("Java Calculator Pro");
        stage.setMinWidth(340);
        stage.setMinHeight(520);
        stage.setScene(scene);
        stage.show();
    }

    private void handleInput(String key) {

        if (key.matches("[0-9]")) {
            if (startNewNumber || display.getText().equals("0")) {
                display.setText(key);
                startNewNumber = false;
            } else {
                display.appendText(key);
            }
            return;
        }

        switch (key) {
            case "." -> {
                if (startNewNumber) {
                    display.setText("0.");
                    startNewNumber = false;
                } else if (!display.getText().contains(".")) {
                    display.appendText(".");
                }
            }
            case "AC" -> clear();
            case "DEL" -> {
                if (!startNewNumber) {
                    String value = display.getText();

                    if (value.length() <= 1
                            || (value.length() == 2
                            && value.startsWith("-"))) {
                        display.setText("0");
                    } else {
                        display.setText(
                                value.substring(0, value.length() - 1)
                        );
                    }
                }
            }
            case "+/−" -> {
                display.setText(format.format(-currentValue()));
            }
            case "%" -> {
                display.setText(
                        format.format(currentValue() / 100.0)
                );
                startNewNumber = true;
            }
            case "+", "−", "×", "÷" -> {
                firstNumber = currentValue();
                operator = key;
                expressionLabel.setText(
                        format.format(firstNumber) + " " + operator
                );
                startNewNumber = true;
            }
            case "=" -> calculate();
        }
    }

    private void calculate() {

        if (operator.isEmpty()) {
            return;
        }

        double secondNumber = currentValue();
        double result;

        switch (operator) {
            case "+" -> result = firstNumber + secondNumber;
            case "−" -> result = firstNumber - secondNumber;
            case "×" -> result = firstNumber * secondNumber;
            case "÷" -> {
                if (secondNumber == 0) {
                    display.setText("Error");
                    expressionLabel.setText("Cannot divide by zero");
                    operator = "";
                    startNewNumber = true;
                    return;
                }
                result = firstNumber / secondNumber;
            }
            default -> {
                return;
            }
        }

        expressionLabel.setText(
                format.format(firstNumber) + " " + operator + " "
                        + format.format(secondNumber) + " ="
        );

        display.setText(format.format(result));
        operator = "";
        startNewNumber = true;
    }

    private double currentValue() {
        try {
            return Double.parseDouble(display.getText());
        } catch (NumberFormatException exception) {
            return 0;
        }
    }

    private void clear() {
        display.setText("0");
        expressionLabel.setText("Ready");
        firstNumber = 0;
        operator = "";
        startNewNumber = true;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
