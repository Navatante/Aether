package org.jonatancarbonellmartinez.data.database.configuration;

import org.jonatancarbonellmartinez.services.CustomLogger;
import org.jonatancarbonellmartinez.exceptions.DatabaseConfigurationException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.*;
import java.util.Properties;

@Singleton
public class DatabaseProperties {
    private final Properties properties;
    private final File propertiesFile;

    @Inject
    public DatabaseProperties() {
        this.properties = new Properties();
        this.propertiesFile = initializePropertiesFile();
        loadProperties();
    }

    private File initializePropertiesFile() {
        File directory = new File(System.getProperty("user.dir"), "properties");
        File file = new File(directory, "aetherdb.properties");

        try {
            if (!directory.exists() && !directory.mkdirs()) {
                throw new IOException("Failed to create properties directory");
            }

            if (!file.exists() && file.createNewFile()) {
                properties.setProperty("path", "");
                saveProperties(file);
            }
            return file;
        } catch (IOException e) {
            CustomLogger.logError("Failed to initialize properties file", e);
            throw new DatabaseConfigurationException("Failed to initialize properties file", e);
        }
    }

    private void loadProperties() {
        try (FileInputStream input = new FileInputStream(propertiesFile)) {
            properties.load(input);
        } catch (IOException e) {
            CustomLogger.logError("Failed to load properties", e);
            throw new DatabaseConfigurationException("Failed to load properties", e);
        }
    }

    private void saveProperties(File file) throws IOException {
        try (FileOutputStream output = new FileOutputStream(file)) {
            properties.store(output, null);
        }
    }

    public void save(String key, String value) throws IOException {
        properties.setProperty(key, value);
        saveProperties(propertiesFile);
    }

    public String read(String key) {
        return properties.getProperty(key);
    }
}