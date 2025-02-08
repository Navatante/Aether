package org.jonatancarbonellmartinez.data.database.configuration;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.File;
import java.sql.*;

public class Database {
    private static Database databaseInstance;
    private final StringProperty databasePath = new SimpleStringProperty();
    private Connection connection;
    private final Properties propertiesFile = Properties.getInstanceOfPropertiesFile();

    private Database() {
        loadPathFromProperties();
    }

    public static synchronized Database getInstance() {
        if (databaseInstance == null) {
            databaseInstance = new Database();
        }
        return databaseInstance;
    }

    private void loadPathFromProperties() {
        String path = propertiesFile.readFromPropertiesFile("path");
        databasePath.set(path);
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

    public Connection getConnection() throws SQLException {
        if (!isDatabaseFilePresent()) {
            throw new SQLException("El archivo de base de datos no existe: " + databasePath.get());
        }

        if (this.connection == null || this.connection.isClosed()) {
            try {
                this.connection = DriverManager.getConnection(databasePath.get());
            } catch (SQLException e) {
                throw new SQLException("Error connecting to the database: " + e.getMessage(), e);
            }
        }

        return this.connection;
    }

    public void closeConnection() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing the database connection: " + e.getMessage());
            }
        }
    }
}