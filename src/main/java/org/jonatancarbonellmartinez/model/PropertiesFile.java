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
    public boolean allAboutPropertiesIsFine = false;

    // Private constructor for Singleton pattern
    private PropertiesFile() {
        this.propertiesDirectory = new File(PROPERTIES_DIRECTORY_PATH);
        this.propertiesFile = new File(PROPERTIES_FILE_PATH);

        if (checkIfPropertiesDirectoryExists() && checkIfPropertiesFileExists()) {
            loadProperties();
        } else if (checkIfPropertiesDirectoryExists()) {
            if (createPropertiesFile()) // if true = creation of file is successful
                loadProperties();
        } else {
            if (createPropertiesDirectory()) { // if true = creation of directory is successful
                if (createPropertiesFile()) // if true = creation of file is successful
                    loadProperties();
            }
        }
    }

    // Thread-safe singleton instance retrieval
    public static synchronized PropertiesFile getInstanceOfPropertiesFile() {
        if (instanceOfPropertiesFile == null) {
            instanceOfPropertiesFile = new PropertiesFile();
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
        try{
            return propertiesFile.createNewFile();
        } catch (IOException e) {
            System.err.println("Failed to create propertie file: " + e.getMessage());
            return false;
        }
    }

    // Load properties from the file (it reads from the file)
    private void loadProperties() { // Loads properties from the file into memory.
        try (FileInputStream fileInputStream = new FileInputStream(propertiesFile)) {
            instanceOfPropertiesFile.load(fileInputStream);
            allAboutPropertiesIsFine = true; // if we finally arrive here, everything has been good.
        } catch (IOException e) {
            System.err.println("Failed to load properties: " + e.getMessage());
        }
    }

    // Save properties to the file (it writes into the file)
    private void saveProperties() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(PROPERTIES_FILE_PATH)) {
            instanceOfPropertiesFile.store(fileOutputStream, null);
        } catch (IOException e) {
            System.err.println("Failed to save properties: " + e.getMessage());
        }
    }

    // Write a key-value pair to properties
    public void writeIntoPropertiesFile(String key, String value) {
        instanceOfPropertiesFile.setProperty(key, value);
        saveProperties();
    }

    // Read a value by key
    public String readFromPropertiesFile(String key) {
        return instanceOfPropertiesFile.getProperty(key);
    }
}
