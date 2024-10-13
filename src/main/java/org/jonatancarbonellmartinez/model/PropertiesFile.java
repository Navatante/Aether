package org.jonatancarbonellmartinez.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFile {
    // Path to the properties file
    private static final String PROPERTIES_FILE_PATH = System.getProperty("user.dir") + "/properties/flightHubDatabase.properties";
    private static PropertiesFile instanceOfPropertiesFile;
    private final Properties properties;

    // Private constructor for Singleton pattern
    private PropertiesFile() {
        properties = new Properties();
        if (checkIfPropertiesDirectoryExists()) {
            loadProperties();
        } else {
            createPropertiesDirectory();
            loadProperties();
        }
    }

    private boolean checkIfPropertiesDirectoryExists() { // This method only checks existence, it won't create a folder in case of inexistence.
        String propertiesDirectory = System.getProperty("user.dir") + File.separator + "properties"; // The path to the properties directory always should be from the path where the FlightHub App is installed.
        File folder = new File(propertiesDirectory);
        return folder.exists() && folder.isDirectory();
    }

    // Ensure the properties directory exists
    private void createPropertiesDirectory() {
        File propertiesDirectory = new File(System.getProperty("user.dir") + File.separator + "properties");
        propertiesDirectory.mkdirs(); // Create the directory
    }

    private boolean checkIfPropertiesFileExists() {

    }

    private void createPropertiesFile() {

    }

    private void checkIfContentInsidePropertiesFileIsCorrect() {

    }

    private void writeContentIntoPropertiesFile() {

    }

    private String askTheUserForDirectoryPath() { // maybe this method should be moved to Controller or View packages.
        // JFileChooser
    }

    public boolean allAboutPropertiesIsFine() {
        // DatabaseLink class will use this to work.
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

    // Thread-safe singleton instance retrieval
    public static synchronized PropertiesFile getInstanceOfPropertiesFile() {
        if (instanceOfPropertiesFile == null) {
            instanceOfPropertiesFile = new PropertiesFile();
        }
        return instanceOfPropertiesFile;
    }
}
