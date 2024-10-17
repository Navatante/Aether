package org.jonatancarbonellmartinez.model;

import java.io.File;
import java.sql.*;

public class Database { // THIS IS A SINGLETON
    private static Database databaseInstance;
    private String pathToDatabase;
    private Connection connection;
    private final Properties propertiesFile = Properties.getInstanceOfPropertiesFile();

    public static synchronized Database getInstance() throws SQLException {
        if (databaseInstance == null) {
            databaseInstance = new Database();
        }
        return databaseInstance;
    }

    public void setPathToDatabaseFromPropertiesFile() {
        this.pathToDatabase = propertiesFile.readFromPropertiesFile("path");
    }

    private boolean isDatabaseFilePresent() {
        if (pathToDatabase == null || pathToDatabase.isEmpty()) {
            return false; // No valid path, database doesn't exist
        }

        // Remove "jdbc:sqlite:" from the beginning of the path to get the actual file path
        String actualFilePath = pathToDatabase.replace("jdbc:sqlite:", "");

        // Check if the file exists
        File databaseFile = new File(actualFilePath);
        return databaseFile.exists() && databaseFile.isFile();
    }

    public Connection getConnection() throws SQLException {
        setPathToDatabaseFromPropertiesFile();  // Ensure the path is set before connecting

        if (pathToDatabase == null || pathToDatabase.isEmpty()) {
            throw new SQLException("El archivo de base de datos no existe: " + pathToDatabase);
        }

        if (this.connection == null || this.connection.isClosed()) {
            this.connection = DriverManager.getConnection(this.pathToDatabase);
            System.out.println("Connected to database at " + pathToDatabase);
        }
        return this.connection;
    }

}
