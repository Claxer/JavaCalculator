package com.calculator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryManager {

    private final List<String> history;
    private final Path historyFile;

    public HistoryManager() {
        history = new ArrayList<>();

        String userHome = System.getProperty("user.home");
        Path appFolder = Paths.get(userHome, ".java-calculator-pro");

        historyFile = appFolder.resolve("history.txt");

        createFolder();
        loadHistory();
    }

    private void createFolder() {
        try {
            Files.createDirectories(historyFile.getParent());
        } catch (IOException e) {
            System.err.println("Could not create history folder: " + e.getMessage());
        }
    }

    public void addCalculation(String expression, String result) {
        String entry = expression + " = " + result;

        history.add(0, entry);
        saveHistory();
    }

    public List<String> getHistory() {
        return Collections.unmodifiableList(new ArrayList<>(history));
    }

    public void clearHistory() {
        history.clear();
        saveHistory();
    }

    public void removeCalculation(int index) {
        if (index >= 0 && index < history.size()) {
            history.remove(index);
            saveHistory();
        }
    }

    private void loadHistory() {
        if (!Files.exists(historyFile)) {
            return;
        }

        try {
            List<String> savedEntries = Files.readAllLines(
                    historyFile,
                    StandardCharsets.UTF_8
            );

            history.addAll(savedEntries);

        } catch (IOException e) {
            System.err.println("Could not load history: " + e.getMessage());
        }
    }

    private void saveHistory() {
        try {
            Files.write(
                    historyFile,
                    history,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );

        } catch (IOException e) {
            System.err.println("Could not save history: " + e.getMessage());
        }
    }
}
