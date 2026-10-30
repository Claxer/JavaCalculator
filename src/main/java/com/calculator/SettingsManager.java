package com.calculator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class SettingsManager {

    private final Properties settings;
    private final Path settingsFile;

    public SettingsManager() {
        settings = new Properties();

        String userHome = System.getProperty("user.home");
        Path appFolder = Paths.get(userHome, ".java-calculator-pro");

        settingsFile = appFolder.resolve("settings.properties");

        createFolder();
        loadSettings();
    }

    private void createFolder() {
        try {
            Files.createDirectories(settingsFile.getParent());
        } catch (IOException e) {
            System.err.println("Could not create settings folder: " + e.getMessage());
        }
    }

    private void loadSettings() {
        if (Files.exists(settingsFile)) {
            try (InputStream input = Files.newInputStream(settingsFile)) {
                settings.load(input);
            } catch (IOException e) {
                System.err.println("Could not load settings: " + e.getMessage());
            }
        }

        setDefaultSettings();
    }

    private void setDefaultSettings() {
        settings.putIfAbsent("degreeMode", "true");
        settings.putIfAbsent("decimalPlaces", "10");
        settings.putIfAbsent("darkTheme", "true");
        settings.putIfAbsent("soundEnabled", "false");

        saveSettings();
    }

    public boolean isDegreeMode() {
        return Boolean.parseBoolean(settings.getProperty("degreeMode", "true"));
    }

    public void setDegreeMode(boolean enabled) {
        settings.setProperty("degreeMode", String.valueOf(enabled));
        saveSettings();
    }

    public int getDecimalPlaces() {
        try {
            int places = Integer.parseInt(
                    settings.getProperty("decimalPlaces", "10")
            );

            return Math.max(0, Math.min(15, places));

        } catch (NumberFormatException e) {
            return 10;
        }
    }

    public void setDecimalPlaces(int places) {
        int safePlaces = Math.max(0, Math.min(15, places));

        settings.setProperty("decimalPlaces", String.valueOf(safePlaces));
        saveSettings();
    }

    public boolean isDarkTheme() {
        return Boolean.parseBoolean(settings.getProperty("darkTheme", "true"));
    }

    public void setDarkTheme(boolean enabled) {
        settings.setProperty("darkTheme", String.valueOf(enabled));
        saveSettings();
    }

    public boolean isSoundEnabled() {
        return Boolean.parseBoolean(settings.getProperty("soundEnabled", "false"));
    }

    public void setSoundEnabled(boolean enabled) {
        settings.setProperty("soundEnabled", String.valueOf(enabled));
        saveSettings();
    }

    private void saveSettings() {
        try (OutputStream output = Files.newOutputStream(settingsFile)) {
            settings.store(output, "Java Calculator Pro Settings");
        } catch (IOException e) {
            System.err.println("Could not save settings: " + e.getMessage());
        }
    }
}
