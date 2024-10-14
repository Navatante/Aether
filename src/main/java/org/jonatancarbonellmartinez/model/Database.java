package org.jonatancarbonellmartinez.model;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database { // THIS IS A SINGLETON
    private static Database databaseInstance;
    private String pathToDatabase;
    private Connection connection;
    private final Properties propertiesFile = Properties.getInstanceOfPropertiesFile();

    public static synchronized Database getDatabaseInstance() throws SQLException {
        if (databaseInstance == null) {
            databaseInstance = new Database();
        }
        return databaseInstance;
    }

    public void setPathToDatabaseFromPropertiesFile() {
        this.pathToDatabase = propertiesFile.readFromPropertiesFile("path");
    }

    public Connection connectToDatabase() throws SQLException {
        setPathToDatabaseFromPropertiesFile();
        if (!doesDatabaseFileExist()) {
            throw new SQLException("El archivo de base de datos no existe: " + pathToDatabase);
        }

        if (this.connection == null || this.connection.isClosed()) {
            this.connection = DriverManager.getConnection(this.pathToDatabase);
            System.out.println("Connected to database");
        }
        return this.connection;
    }

    public void disconnectFromDatabase() throws SQLException {
        if (this.connection != null && !this.connection.isClosed()) {
            this.connection.close();
            System.out.println("Disconnected from database");
        }
    }

    private boolean doesDatabaseFileExist() {
        if (pathToDatabase == null || pathToDatabase.isEmpty()) {
            return false; // No valid path, database doesn't exist
        }

        // Remove "jdbc:sqlite:" from the beginning of the path to get the actual file path
        String actualFilePath = pathToDatabase.replace("jdbc:sqlite:", "");

        // Check if the file exists
        File databaseFile = new File(actualFilePath);
        return databaseFile.exists() && databaseFile.isFile();
    }
}
