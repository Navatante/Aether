package org.jonatancarbonellmartinez.data.database.configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Singleton
public class Properties {
    private final java.util.Properties properties;
    private final String propertiesDirectoryPath;
    private final String propertiesFilePath;
    private final File propertiesDirectory;
    private final File propertiesFile;

    @Inject
    public Properties() {
        this.properties = new java.util.Properties();
        this.propertiesDirectoryPath = System.getProperty("user.dir") + File.separator + "properties";
        this.propertiesFilePath = propertiesDirectoryPath + File.separator + "flightHubDatabase.properties";
        this.propertiesDirectory = new File(propertiesDirectoryPath);
        this.propertiesFile = new File(propertiesFilePath);

        initializeProperties();
    }

    private void initializeProperties() {
        if (!checkIfPropertiesDirectoryExists() && !createPropertiesDirectory()) {
            throw new RuntimeException("Failed to create properties directory.");
        }

        if (!checkIfPropertiesFileExists() && !createPropertiesFile()) {
            throw new RuntimeException("Failed to create properties file.");
        }

        loadProperties();
    }

    // Rest of the methods remain the same...
    private boolean checkIfPropertiesDirectoryExists() {
        return propertiesDirectory.exists() && propertiesDirectory.isDirectory();
    }

    private boolean createPropertiesDirectory() {
        return propertiesDirectory.mkdirs();
    }

    private boolean checkIfPropertiesFileExists() {
        return propertiesFile.exists() && propertiesFile.isFile();
    }

    private boolean createPropertiesFile() {
        try {
            if (propertiesFile.createNewFile()) {
                properties.setProperty("path", "");  // Default to empty path
                saveProperties();
                return true;
            }
            throw new RuntimeException("Failed to create properties file");
        } catch (IOException e) {
            throw new RuntimeException("Failed to create properties file", e);
        }
    }

    private void loadProperties() {
        try (FileInputStream fileInputStream = new FileInputStream(propertiesFile)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties", e);
        }
    }

    private void saveProperties() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(propertiesFile)) {
            properties.store(fileOutputStream, null);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save properties", e);
        }
    }

    public void writeIntoPropertiesFile(String key, String value) {
        properties.setProperty(key, value);
        saveProperties();
    }

    public String readFromPropertiesFile(String key) {
        return properties.getProperty(key);
    }
}