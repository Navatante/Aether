package org.jonatancarbonellmartinez.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFile extends Properties {
    // Path to the properties file
    private static PropertiesFile instanceOfPropertiesFile;
    private static final String PROPERTIES_DIRECTORY_PATH = System.getProperty("user.dir") + File.separator + "properties"; // The path to the properties directory always should be from the path where the FlightHub App is installed.
    private static final String PROPERTIES_FILE_PATH = System.getProperty("user.dir") + File.separator + "properties" + File.separator + "flightHubDatabase.properties";
    private File propertiesDirectory;
    private File propertiesFile;

    // Private constructor for Singleton pattern
    private PropertiesFile() {
        this.propertiesDirectory = new File(PROPERTIES_DIRECTORY_PATH);
        this.propertiesFile = new File(PROPERTIES_FILE_PATH);

        if (checkIfPropertiesDirectoryExists()) {
            if (checkIfPropertiesFileExists()) {
                writeContentIntoPropertiesFile();
            } else {
                createPropertiesFile();
            }
        } else {
            createPropertiesDirectory();


        }
    }

    private boolean checkIfPropertiesDirectoryExists() { // This method only checks existence, it won't create a folder in case of inexistence.
        return propertiesDirectory.exists() && propertiesDirectory.isDirectory();
    }

    private void createPropertiesDirectory() {
        propertiesDirectory.mkdirs(); // Create the directory
    }

    private boolean checkIfPropertiesFileExists() { // It will check if 'flightHubDatabase.properties' exists inside the directory.
        return propertiesFile.exists() && propertiesFile.isFile();
    }

    private void createPropertiesFile() {

    }

    private void checkIfContentInsidePropertiesFileIsCorrect() {

    }

    private void writeContentIntoPropertiesFile() {

    }

    private String askTheUserForDatabaseFilePath() { // maybe this method should be moved to Controller or View packages. or DatabaseLink class
        // JFileChooser
    }

    public boolean allAboutPropertiesIsFine() {
        // DatabaseLink class will use this to work.
    }

    // Load properties from the file
    private void loadProperties() {
        if (propertiesFile.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(propertiesFile)) {
                this.load(fileInputStream);
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
        this.setProperty(key, value);
        saveProperties();
    }

    // Save properties to the file
    private void saveProperties() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(PROPERTIES_FILE_PATH)) {
            this.store(fileOutputStream, null);
        } catch (IOException e) {
            System.err.println("Failed to save properties: " + e.getMessage());
        }
    }

    // Read a value by key
    public String read(String key) {
        return this.getProperty(key);
    }

    // Thread-safe singleton instance retrieval
    public static synchronized PropertiesFile getInstanceOfPropertiesFile() {
        if (instanceOfPropertiesFile == null) {
            instanceOfPropertiesFile = new PropertiesFile();
        }
        return instanceOfPropertiesFile;
    }
}
