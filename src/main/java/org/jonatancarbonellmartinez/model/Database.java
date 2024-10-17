package org.jonatancarbonellmartinez.model;

import java.io.File;
import java.sql.*;

public class Database { // THIS IS A SINGLETON
    private static Database databaseInstance;
    private String pathToDatabase;
    private Connection connection;
    private final Properties propertiesFile = Properties.getInstanceOfPropertiesFile();

    // Private constructor to prevent direct instantiation
    private Database() {
        setPathToDatabaseFromPropertiesFile();  // Load path on initialization
    }

    // Singleton pattern with lazy initialization
    public static synchronized Database getInstance() {
        if (databaseInstance == null) {
            databaseInstance = new Database();
        }
        return databaseInstance;
    }

    // Set database path from properties file
    public void setPathToDatabaseFromPropertiesFile() {
        this.pathToDatabase = propertiesFile.readFromPropertiesFile("path");
    }

    // Check if the database file is present
    private boolean isDatabaseFilePresent() {
        if (pathToDatabase == null || pathToDatabase.isEmpty()) {
            return false; // No valid path, database doesn't exist
        }

        // Remove "jdbc:sqlite:" from the beginning of the path to get the actual file path
        String actualFilePath = pathToDatabase.replace("jdbc:sqlite:", "");
        File databaseFile = new File(actualFilePath);

        // Check if the file exists and is indeed a file
        return databaseFile.exists() && databaseFile.isFile();
    }

    // Establish connection to the database
    public Connection getConnection() throws SQLException {
        // Ensure path is loaded before connecting
        setPathToDatabaseFromPropertiesFile();

        // Check if the database file exists before connecting
        if (!isDatabaseFilePresent()) {
            throw new SQLException("El archivo de base de datos no existe: " + pathToDatabase);
        }

        // If the connection is null or closed, establish a new connection
        if (this.connection == null || this.connection.isClosed()) {
            try {
                this.connection = DriverManager.getConnection(this.pathToDatabase);
                System.out.println("Connected to database at " + pathToDatabase);
            } catch (SQLException e) {
                // Handle SQL exceptions more explicitly if needed
                throw new SQLException("Error connecting to the database: " + e.getMessage(), e);
            }
        }

        return this.connection;
    }

    // Optional: Method to close the connection (if required later)
    public void closeConnection() {
        if (this.connection != null) {
            try {
                this.connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing the database connection: " + e.getMessage());
            }
        }
    }
}
