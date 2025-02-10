package org.jonatancarbonellmartinez.data.database.configuration;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.sql.*;

@Singleton
public class Database {
    private final StringProperty databasePath = new SimpleStringProperty();
    private final Properties propertiesFile;

    @Inject
    public Database(Properties propertiesFile) {
        this.propertiesFile = propertiesFile;
        loadPathFromProperties();
    }

    private void loadPathFromProperties() {
        String path = propertiesFile.readFromPropertiesFile("path");
        databasePath.set(path);
    }

    public Connection getConnection() throws SQLException {
        if (!isDatabaseFilePresent()) {
            throw new SQLException("Database file does not exist: " + databasePath.get());
        }

        try {
            return DriverManager.getConnection(databasePath.get());
        } catch (SQLException e) {
            throw new SQLException("Error connecting to database: " + e.getMessage(), e);
        }
    }

    public StringProperty databasePathProperty() {
        return databasePath;
    }

    public void setDatabasePath(String path) {
        databasePath.set(path);
        propertiesFile.writeIntoPropertiesFile("path", path);
    }

    public boolean isDatabaseFilePresent() {
        String path = databasePath.get();
        if (path == null || path.isEmpty()) {
            return false;
        }

        String actualFilePath = path.replace("jdbc:sqlite:", "");
        File databaseFile = new File(actualFilePath);
        return databaseFile.exists() && databaseFile.isFile();
    }
}