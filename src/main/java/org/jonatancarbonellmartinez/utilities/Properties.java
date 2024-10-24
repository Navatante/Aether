package org.jonatancarbonellmartinez.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Properties {

    private java.util.Properties properties;
    private static Properties instanceOfPropertiesFile;
    private static final String PROPERTIES_DIRECTORY_PATH = System.getProperty("user.dir") + File.separator + "properties";
    private static final String PROPERTIES_FILE_PATH = PROPERTIES_DIRECTORY_PATH + File.separator + "flightHubDatabase.properties";
    private File propertiesDirectory;
    private File propertiesFile;

    // Private constructor for Singleton pattern
    private Properties() {
        this.properties = new java.util.Properties();
        this.propertiesDirectory = new File(PROPERTIES_DIRECTORY_PATH);
        this.propertiesFile = new File(PROPERTIES_FILE_PATH);

        if (!checkIfPropertiesDirectoryExists() && !createPropertiesDirectory()) {
            throw new RuntimeException("Failed to create properties directory.");
        }

        if (!checkIfPropertiesFileExists() && !createPropertiesFile()) {
            throw new RuntimeException("Failed to create properties file.");
        }

        loadProperties();
    }

    // Thread-safe singleton instance retrieval
    public static synchronized Properties getInstanceOfPropertiesFile() {
        if (instanceOfPropertiesFile == null) {
            instanceOfPropertiesFile = new Properties();
        }
        return instanceOfPropertiesFile;
    }

    // Check if the properties directory exists
    private boolean checkIfPropertiesDirectoryExists() {
        return propertiesDirectory.exists() && propertiesDirectory.isDirectory();
    }

    // Create the properties directory if it doesn't exist
    private boolean createPropertiesDirectory() {
        return propertiesDirectory.mkdirs();
    }

    // Check if the properties file exists inside the directory
    private boolean checkIfPropertiesFileExists() {
        return propertiesFile.exists() && propertiesFile.isFile();
    }

    // Create the properties file if it doesn't exist
    private boolean createPropertiesFile() {
        try {
            if (propertiesFile.createNewFile()) {
                // Add default properties (like an empty path)
                properties.setProperty("path", "");  // Default to empty path
                saveProperties();
                return true;
            } else {
                System.err.println("File already exists or failed to create.");
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create properties file", e);
        }
    }

    // Load properties from the file into memory
    private void loadProperties() {
        try (FileInputStream fileInputStream = new FileInputStream(propertiesFile)) {
            properties.load(fileInputStream);

        } catch (IOException e) {
            System.err.println("Failed to load properties: " + e.getMessage());
        }
    }

    // Save properties to the file
    private void saveProperties() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(propertiesFile)) {
            properties.store(fileOutputStream, null);
        } catch (IOException e) {
            System.err.println("Failed to save properties: " + e.getMessage());
        }
    }

    // Write a key-value pair to the properties file
    public void writeIntoPropertiesFile(String key, String value) {
        properties.setProperty(key, value);
        saveProperties();
    }

    // Read a value by key from the properties file
    public String readFromPropertiesFile(String key) {
        return properties.getProperty(key);
    }
}
