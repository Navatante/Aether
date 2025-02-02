package org.jonatancarbonellmartinez.utilities;

import java.io.File;
import java.sql.*;
// TODO tengo que crear dos connections, una para read y otra para write. mirate la captura de pantalla
public class Database {
    private static Database instance;
    private String databasePath;
    private Connection connection;
    private final Properties properties;

    private Database() {
        this.properties = Properties.getInstanceOfPropertiesFile();
        loadDatabasePath();
    }

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private void loadDatabasePath() {
        this.databasePath = properties.readFromPropertiesFile("path");
    }

    public boolean isDatabaseFilePresent() {
        if (databasePath == null || databasePath.isEmpty()) {
            return false;
        }

        String actualFilePath = databasePath.replace("jdbc:sqlite:", "");
        File databaseFile = new File(actualFilePath);
        return databaseFile.exists() && databaseFile.isFile();
    }

    public Connection getConnection() throws SQLException {
        loadDatabasePath();

        if (!isDatabaseFilePresent()) {
            throw new SQLException("Database file does not exist: " + databasePath);
        }

        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(databasePath);
        }

        return connection;
    }

    public void setDatabasePath(String path) {
        this.databasePath = "jdbc:sqlite:" + path.replace("\\", "/");
        properties.writeIntoPropertiesFile("path", this.databasePath);
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            // Log error
        }
    }
}
