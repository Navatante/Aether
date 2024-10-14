package org.jonatancarbonellmartinez.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Properties {

    java.util.Properties properties;
    private static Properties instanceOfPropertiesFile;
    private static final String PROPERTIES_DIRECTORY_PATH = System.getProperty("user.dir") + File.separator + "properties"; // The path to the properties directory always should be from the path where the FlightHub App is installed.
    private static final String PROPERTIES_FILE_PATH = System.getProperty("user.dir") + File.separator + "properties" + File.separator + "flightHubDatabase.properties";
    private File propertiesDirectory;
    private File propertiesFile;
    private boolean allAboutPropertiesIsFine = false;

    // Private constructor for Singleton pattern
    private Properties() {
        this.properties = new java.util.Properties();
        this.propertiesDirectory = new File(PROPERTIES_DIRECTORY_PATH);
        this.propertiesFile = new File(PROPERTIES_FILE_PATH);

        if (!propertiesDirectory.exists() && !createPropertiesDirectory()) {
            throw new RuntimeException("Failed to create properties directory.");
        }

        if (!propertiesFile.exists() && !createPropertiesFile()) {
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

    private boolean checkIfPropertiesDirectoryExists() { // This method only checks existence, it won't create a folder in case of inexistence.
        return propertiesDirectory.exists() && propertiesDirectory.isDirectory();
    }

    private boolean createPropertiesDirectory() {
        return propertiesDirectory.mkdirs(); // Create the directory
    }

    private boolean checkIfPropertiesFileExists() { // It will check if 'flightHubDatabase.properties' exists inside the directory.
        return propertiesFile.exists() && propertiesFile.isFile();
    }

    private boolean createPropertiesFile() {
        try {
            if (propertiesFile.createNewFile()) {
                return true;
            } else {
                System.err.println("File already exists or failed to create.");
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create properties file", e);
        }
    }


    // Load properties from the file (it reads from the file)
    private void loadProperties() { // Loads properties from the file into memory.
        try (FileInputStream fileInputStream = new FileInputStream(propertiesFile)) {
            properties.load(fileInputStream);
            allAboutPropertiesIsFine = true; // if we finally arrive here, everything has been good.
        } catch (IOException e) {
            System.err.println("Failed to load properties: " + e.getMessage());
        }
    }

    // Save properties to the file (it writes into the file)
    private void saveProperties() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(PROPERTIES_FILE_PATH)) {
            properties.store(fileOutputStream, null);
        } catch (IOException e) {
            System.err.println("Failed to save properties: " + e.getMessage());
        }
    }

    // Write a key-value pair to properties
    public void writeIntoPropertiesFile(String key, String value) {
        properties.setProperty(key, value);
        saveProperties();
    }

    // Read a value by key
    public String readFromPropertiesFile(String key) {
        return properties.getProperty(key);
    }

    public boolean isAllAboutPropertiesIsFine() {
        return this.allAboutPropertiesIsFine;
    }
}
