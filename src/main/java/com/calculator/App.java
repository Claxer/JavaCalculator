package com.calculator;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Locale;

public class App extends Application {

    private final CalculatorEngine engine = new CalculatorEngine();
    private final HistoryManager historyManager = new HistoryManager();
    private final MemoryManager memoryManager = new MemoryManager();
    private final SettingsManager settingsManager = new SettingsManager();

    private TextField display;
    private Label statusLabel;
    private ListView<String> historyList;
    private VBox historyPanel;
    private BorderPane root;

    private String lastResult = "";
    private boolean resultDisplayed = false;

    @Override
    public void start(Stage stage) {

        engine.setDegreeMode(settingsManager.isDegreeMode());

        root = new BorderPane();
        root.setPadding(new Insets(18));
        root.getStyleClass().add("calculator-root");

        VBox mainContent = new VBox(14);
        mainContent.setAlignment(Pos.TOP_CENTER);

        HBox topBar = createTopBar(stage);

        statusLabel = new Label(
                settingsManager.isDegreeMode() ? "DEG" : "RAD"
        );
        statusLabel.getStyleClass().add("status-label");

        display = new TextField("0");
        display.setEditable(true);
        display.setAlignment(Pos.CENTER_RIGHT);
        display.setPromptText("0");
        display.getStyleClass().add("display");

        display.setOnAction(event -> calculate());

        VBox displaySection = new VBox(8, statusLabel, display);
        displaySection.setAlignment(Pos.CENTER_RIGHT);

        GridPane memoryButtons = createMemoryButtons();
        GridPane scientificButtons = createScientificButtons();
        GridPane numberButtons = createNumberButtons();

        mainContent.getChildren().addAll(
                topBar,
                displaySection,
                memoryButtons,
                scientificButtons,
                numberButtons
        );

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStyleClass().add("calculator-scroll");

        root.setCenter(scrollPane);

        createHistoryPanel();
        applyTheme();

        Scene scene = new Scene(root, 430, 790);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                clearDisplay();
            } else if (event.getCode() == KeyCode.ENTER) {
                calculate();
            } else if (event.getCode() == KeyCode.F9) {
                toggleHistory();
            }
        });

        stage.setTitle("Java Calculator Pro");
        stage.setMinWidth(390);
        stage.setMinHeight(650);
        stage.setScene(scene);
        stage.show();

        display.requestFocus();
    }

    private HBox createTopBar(Stage stage) {

        Label title = new Label("Calculator Pro");
        title.getStyleClass().add("app-title");

        Button historyButton = createButton("History", "utility-button");
        Button settingsButton = createButton("Settings", "utility-button");
        Button copyButton = createButton("Copy", "utility-button");

        historyButton.setOnAction(event -> toggleHistory());
        settingsButton.setOnAction(event -> showSettings(stage));
        copyButton.setOnAction(event -> copyResult());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox topBar = new HBox(8, title, spacer, copyButton,
                historyButton, settingsButton);

        topBar.setAlignment(Pos.CENTER_LEFT);

        return topBar;
    }

    private GridPane createMemoryButtons() {

        GridPane grid = new GridPane();
        grid.setHgap(7);
        grid.setVgap(7);

        String[] labels = {"MC", "MR", "M+", "M−"};

        for (int i = 0; i < labels.length; i++) {
            String label = labels[i];

            Button button = createButton(label, "memory-button");

            button.setOnAction(event -> handleMemory(label));

            grid.add(button, i, 0);
            GridPane.setHgrow(button, Priority.ALWAYS);
            button.setMaxWidth(Double.MAX_VALUE);
        }

        return grid;
    }

    private GridPane createScientificButtons() {

        GridPane grid = new GridPane();
        grid.setHgap(7);
        grid.setVgap(7);

        String[][] buttons = {
                {"sin", "cos", "tan", "log"},
                {"ln", "√", "x²", "xʸ"},
                {"(", ")", "π", "e"},
                {"n!", "1/x", "DEG/RAD", "HISTORY"}
        };

        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {

                String label = buttons[row][col];

                Button button = createButton(label, "scientific-button");

                button.setOnAction(event -> handleScientific(label));

                grid.add(button, col, row);
                GridPane.setHgrow(button, Priority.ALWAYS);
                button.setMaxWidth(Double.MAX_VALUE);
            }
        }

        return grid;
    }

    private GridPane createNumberButtons() {

        GridPane grid = new GridPane();
        grid.setHgap(7);
        grid.setVgap(7);

        String[][] buttons = {
                {"AC", "DEL", "%", "÷"},
                {"7", "8", "9", "×"},
                {"4", "5", "6", "−"},
                {"1", "2", "3", "+"},
                {"±", "0", ".", "="}
        };

        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {

                String label = buttons[row][col];

                String style = "number-button";

                if (label.equals("=")) {
                    style = "equals-button";
                } else if ("AC DEL % ÷ × − + ±".contains(label)
                        && !label.isEmpty()) {
                    style = "operator-button";
                }

                Button button = createButton(label, style);

                button.setOnAction(event -> handleButton(label));

                grid.add(button, col, row);
                GridPane.setHgrow(button, Priority.ALWAYS);
                GridPane.setVgrow(button, Priority.ALWAYS);
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            }
        }

        return grid;
    }

    private Button createButton(String text, String styleClass) {

        Button button = new Button(text);
        button.getStyleClass().addAll("calculator-button", styleClass);

        return button;
    }

    private void handleButton(String value) {

        switch (value) {

            case "AC":
                clearDisplay();
                break;

            case "DEL":
                deleteLastCharacter();
                break;

            case "=":
                calculate();
                break;

            case "±":
                toggleSign();
                break;

            case "%":
                insertValue("/100");
                break;

            case "÷":
                insertValue("/");
                break;

            case "×":
                insertValue("*");
                break;

            case "−":
                insertValue("-");
                break;

            case "+":
                insertValue("+");
                break;

            default:
                insertValue(value);
                break;
        }
    }

    private void handleScientific(String value) {

        switch (value) {

            case "sin":
            case "cos":
            case "tan":
            case "log":
            case "ln":
                insertValue(value + "(");
                break;

            case "√":
                insertValue("sqrt(");
                break;

            case "x²":
                insertValue("^2");
                break;

            case "xʸ":
                insertValue("^");
                break;

            case "(":
            case ")":
                insertValue(value);
                break;

            case "π":
                insertValue("pi");
                break;

            case "e":
                insertValue("e");
                break;

            case "n!":
                insertValue("!");
                break;

            case "1/x":
                insertValue("^(-1)");
                break;

            case "DEG/RAD":
                boolean newMode = !engine.isDegreeMode();
                engine.setDegreeMode(newMode);
                settingsManager.setDegreeMode(newMode);

                statusLabel.setText(newMode ? "DEG" : "RAD");
                break;

            case "HISTORY":
                toggleHistory();
                break;
        }
    }

    private void handleMemory(String action) {

        try {
            double value = evaluate(display.getText());

            switch (action) {

                case "MC":
                    memoryManager.memoryClear();
                    break;

                case "MR":
                    display.setText(engine.formatResult(
                            memoryManager.memoryRecall()
                    ));
                    resultDisplayed = true;
                    break;

                case "M+":
                    memoryManager.memoryAdd(value);
                    break;

                case "M−":
                    memoryManager.memorySubtract(value);
                    break;
            }

        } catch (Exception e) {
            showError("Enter a valid number before using memory.");
        }
    }

    private void insertValue(String value) {

        String current = display.getText();

        if (current.equals("Error")) {
            current = "0";
        }

        if (resultDisplayed && startsNewExpression(value)) {
            current = "0";
        }

        if (current.equals("0") && isSimpleDigit(value)) {
            current = value;
        } else {
            current += value;
        }

        display.setText(current);
        resultDisplayed = false;
        display.positionCaret(display.getText().length());
    }

    private boolean startsNewExpression(String value) {
        return value.matches("[0-9.]")
                || value.equals("pi")
                || value.equals("e")
                || value.endsWith("(");
    }

    private boolean isSimpleDigit(String value) {
        return value.matches("[0-9.]");
    }

    private void clearDisplay() {
        display.setText("0");
        resultDisplayed = false;
    }

    private void deleteLastCharacter() {

        String current = display.getText();

        if (current.equals("Error") || current.length() <= 1) {
            clearDisplay();
            return;
        }

        display.setText(current.substring(0, current.length() - 1));
        resultDisplayed = false;
    }

    private void toggleSign() {

        String current = display.getText();

        if (current.equals("0") || current.equals("Error")) {
            return;
        }

        if (current.startsWith("-")) {
            display.setText(current.substring(1));
        } else {
            display.setText("-(" + current + ")");
        }

        resultDisplayed = false;
    }

    private void calculate() {

        String expression = display.getText().trim();

        if (expression.isEmpty()) {
            return;
        }

        try {
            double result = evaluate(expression);

            String formatted = formatResult(result);

            historyManager.addCalculation(expression, formatted);

            display.setText(formatted);
            lastResult = formatted;
            resultDisplayed = true;

            refreshHistory();

        } catch (Exception e) {
            showError(e.getMessage() == null
                    ? "Invalid expression."
                    : e.getMessage());
        }
    }

    private double evaluate(String expression) {

        ExpressionParser parser = new ExpressionParser(
                expression,
                engine
        );

        return parser.parse();
    }

    private String formatResult(double value) {

        if (!Double.isFinite(value)) {
            throw new ArithmeticException("Result is not a valid number.");
        }

        int places = settingsManager.getDecimalPlaces();

        if (Math.abs(value) < 1e15 && value == Math.rint(value)) {
            return String.format(Locale.US, "%.0f", value);
        }

        return String.format(Locale.US, "%." + places + "f", value)
                .replaceAll("0+$", "")
                .replaceAll("\\.$", "");
    }

    private void showError(String message) {
        display.setText("Error");
        statusLabel.setText(message);
        resultDisplayed = true;
    }

    private void copyResult() {

        String value = display.getText();

        if (value.isEmpty() || value.equals("Error")) {
            return;
        }

        ClipboardContent content = new ClipboardContent();
        content.putString(value);

        Clipboard.getSystemClipboard().setContent(content);
        statusLabel.setText("Copied to clipboard");
    }

    private void createHistoryPanel() {

        historyList = new ListView<>();
        historyList.setItems(FXCollections.observableArrayList(
                historyManager.getHistory()
        ));

        historyList.setPrefWidth(280);

        historyList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {

                String selected = historyList.getSelectionModel()
                        .getSelectedItem();

                if (selected != null && selected.contains(" = ")) {
                    String expression = selected.substring(
                            0,
                            selected.lastIndexOf(" = ")
                    );

                    display.setText(expression);
                    resultDisplayed = false;
                }
            }
        });

        Label heading = new Label("Calculation History");
        heading.getStyleClass().add("panel-title");

        Button clearButton = createButton("Clear History", "utility-button");
        clearButton.setOnAction(event -> {
            historyManager.clearHistory();
            refreshHistory();
        });

        Button closeButton = createButton("Close", "utility-button");
        closeButton.setOnAction(event -> toggleHistory());

        HBox actions = new HBox(8, clearButton, closeButton);

        historyPanel = new VBox(12, heading, historyList, actions);
        historyPanel.setPadding(new Insets(15));
        historyPanel.setPrefWidth(310);
        historyPanel.getStyleClass().add("history-panel");

        historyPanel.setVisible(false);
        historyPanel.setManaged(false);

        root.setRight(historyPanel);

        refreshHistory();
    }

    private void refreshHistory() {

        if (historyList != null) {
            historyList.setItems(FXCollections.observableArrayList(
                    historyManager.getHistory()
            ));
        }
    }

    private void toggleHistory() {

        boolean show = !historyPanel.isVisible();

        historyPanel.setVisible(show);
        historyPanel.setManaged(show);
    }

    private void showSettings(Stage owner) {

        Dialog<Void> dialog = new Dialog<>();
        dialog.initOwner(owner);
        dialog.setTitle("Calculator Settings");
        dialog.setHeaderText("Customize your calculator");

        CheckBox degreeCheck = new CheckBox("Use Degrees");
        degreeCheck.setSelected(settingsManager.isDegreeMode());

        CheckBox darkCheck = new CheckBox("Use Dark Theme");
        darkCheck.setSelected(settingsManager.isDarkTheme());

        CheckBox soundCheck = new CheckBox("Enable Sound");
        soundCheck.setSelected(settingsManager.isSoundEnabled());

        ComboBox<Integer> decimalChoice = new ComboBox<>(
                FXCollections.observableArrayList(2, 4, 6, 8, 10, 12, 15)
        );

        decimalChoice.setValue(settingsManager.getDecimalPlaces());

        VBox content = new VBox(
                14,
                degreeCheck,
                darkCheck,
                soundCheck,
                new Label("Decimal Places"),
                decimalChoice
        );

        content.setPadding(new Insets(15));

        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(
                ButtonType.APPLY,
                ButtonType.CANCEL
        );

        dialog.setResultConverter(button -> {
            if (button == ButtonType.APPLY) {

                settingsManager.setDegreeMode(degreeCheck.isSelected());
                settingsManager.setDarkTheme(darkCheck.isSelected());
                settingsManager.setSoundEnabled(soundCheck.isSelected());

                if (decimalChoice.getValue() != null) {
                    settingsManager.setDecimalPlaces(decimalChoice.getValue());
                }

                engine.setDegreeMode(degreeCheck.isSelected());
                statusLabel.setText(degreeCheck.isSelected() ? "DEG" : "RAD");

                applyTheme();
            }

            return null;
        });

        dialog.showAndWait();
    }

    private void applyTheme() {

        boolean dark = settingsManager.isDarkTheme();

        root.getStyleClass().removeAll("dark-theme", "light-theme");
        root.getStyleClass().add(dark ? "dark-theme" : "light-theme");
    }

    private static class ExpressionParser {

        private final String input;
        private final CalculatorEngine engine;
        private int position = -1;
        private char current;

        ExpressionParser(String input, CalculatorEngine engine) {
            this.input = input.replace("×", "*").replace("÷", "/");
            this.engine = engine;
            nextCharacter();
        }

        private void nextCharacter() {
            position++;
            current = position < input.length()
                    ? input.charAt(position)
                    : '\0';
        }

        private boolean eat(char character) {

            while (current == ' ') {
                nextCharacter();
            }

            if (current == character) {
                nextCharacter();
                return true;
            }

            return false;
        }

        double parse() {

            double result = parseExpression();

            if (current != '\0') {
                throw new IllegalArgumentException(
                        "Unexpected character: " + current
                );
            }

            return result;
        }

        private double parseExpression() {

            double result = parseTerm();

            while (true) {
                if (eat('+')) {
                    result += parseTerm();
                } else if (eat('-')) {
                    result -= parseTerm();
                } else {
                    return result;
                }
            }
        }

        private double parseTerm() {

            double result = parsePower();

            while (true) {
                if (eat('*')) {
                    result *= parsePower();
                } else if (eat('/')) {
                    result = engine.divide(result, parsePower());
                } else {
                    return result;
                }
            }
        }

        private double parsePower() {

            double result = parseUnary();

            if (eat('^')) {
                result = engine.power(result, parsePower());
            }

            return result;
        }

        private double parseUnary() {

            if (eat('+')) {
                return parseUnary();
            }

            if (eat('-')) {
                return -parseUnary();
            }

            return parsePostfix();
        }

        private double parsePostfix() {

            double result = parsePrimary();

            while (eat('!')) {
                result = engine.factorial(result);
            }

            return result;
        }

        private double parsePrimary() {

            while (current == ' ') {
                nextCharacter();
            }

            if (eat('(')) {
                double result = parseExpression();

                if (!eat(')')) {
                    throw new IllegalArgumentException(
                            "Missing closing parenthesis."
                    );
                }

                return result;
            }

            if (Character.isDigit(current) || current == '.') {
                return parseNumber();
            }

            if (Character.isLetter(current)) {

                String name = parseName().toLowerCase();

                if (name.equals("pi")) {
                    return engine.getPi();
                }

                if (name.equals("e")) {
                    return engine.getEulerNumber();
                }

                if (!eat('(')) {
                    throw new IllegalArgumentException(
                            "Expected '(' after " + name
                    );
                }

                double argument = parseExpression();

                if (!eat(')')) {
                    throw new IllegalArgumentException(
                            "Missing closing parenthesis."
                    );
                }

                return applyFunction(name, argument);
            }

            throw new IllegalArgumentException("Invalid expression.");
        }

        private double parseNumber() {

            int start = position;

            while (Character.isDigit(current) || current == '.') {
                nextCharacter();
            }

            try {
                return Double.parseDouble(input.substring(start, position));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid number.");
            }
        }

        private String parseName() {

            int start = position;

            while (Character.isLetter(current)) {
                nextCharacter();
            }

            return input.substring(start, position);
        }

        private double applyFunction(String name, double value) {

            switch (name) {

                case "sin":
                    return engine.sin(value);

                case "cos":
                    return engine.cos(value);

                case "tan":
                    return engine.tan(value);

                case "log":
                    return engine.log(value);

                case "ln":
                    return engine.naturalLog(value);

                case "sqrt":
                    return engine.squareRoot(value);

                default:
                    throw new IllegalArgumentException(
                            "Unknown function: " + name
                    );
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
