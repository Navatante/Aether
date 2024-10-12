package org.jonatancarbonellmartinez.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFile {
    // Path to the properties file
    private static final String PROPERTIES_FILE_PATH = System.getProperty("user.dir") + "/properties/flightHubDatabase.properties";
    private static PropertiesFile instance;
    private Properties properties;

    // Private constructor for Singleton pattern
    private PropertiesFile() {
        properties = new Properties();
        createPropertiesDirectory(); // Ensure the directory exists
        loadProperties();
    }

    // Ensure the properties directory exists
    private void createPropertiesDirectory() {
        File directory = new File(System.getProperty("user.dir") + "\\properties");
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it doesn't exist
        }
    }

    // Thread-safe singleton instance retrieval
    public static synchronized PropertiesFile getInstance() {
        if (instance == null) {
            instance = new PropertiesFile();
        }
        return instance;
    }

    // Load properties from the file
    private void loadProperties() {
        File propertiesFile = new File(PROPERTIES_FILE_PATH);
        if (propertiesFile.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(propertiesFile)) {
                properties.load(fileInputStream);
            } catch (IOException e) {
                System.err.println("Failed to load properties: " + e.getMessage());
            }
        } else {
            // Optional: Create a new properties file with default values
            saveProperties(); // Create an empty properties file
        }
    }

    // Write a key-value pair to properties
    public void write(String key, String value) {
        properties.setProperty(key, value);
        saveProperties();
    }

    // Save properties to the file
    private void saveProperties() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(PROPERTIES_FILE_PATH)) {
            properties.store(fileOutputStream, null);
        } catch (IOException e) {
            System.err.println("Failed to save properties: " + e.getMessage());
        }
    }

    // Read a value by key
    public String read(String key) {
        return properties.getProperty(key);
    }
}
