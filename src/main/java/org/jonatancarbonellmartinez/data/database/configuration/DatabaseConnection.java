package org.jonatancarbonellmartinez.data.database.configuration;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.jonatancarbonellmartinez.exceptions.CustomLogger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.sql.*;

@Singleton
public class DatabaseConnection {
    private final StringProperty databasePath;
    private final DatabaseProperties properties;

    @Inject
    public DatabaseConnection(DatabaseProperties properties) {
        this.properties = properties;
        this.databasePath = new SimpleStringProperty();
        loadPathFromProperties();
    }

    private void loadPathFromProperties() {
        databasePath.set(properties.read("path"));
    }

    // Para conexiones de una sola  consulta. Utilizo este metodo directamente
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

    public void setDatabasePath(String path) throws IOException {
        databasePath.set(path);
        properties.save("path", path);
    }

    public boolean isDatabaseFilePresent() {
        String path = databasePath.get();
        if (path == null || path.isEmpty()) {
            return false;
        }

        File dbFile = new File(path.replace("jdbc:sqlite:", ""));
        return dbFile.exists() && dbFile.isFile();
    }

    public void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                CustomLogger.logError("Error closing connection", e);
            }
        }
    }

    // Estos metodos relacionados con una Transaccion no los llamo directamente, utilizare Unit of Work para ello.
    public void beginTransaction(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.setAutoCommit(false);
        }
    }

    public void commitTransaction(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.commit();
            connection.setAutoCommit(true);
        }
    }

    public void rollbackTransaction(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.rollback();
            connection.setAutoCommit(true);
        }
    }

    public StringProperty databasePathProperty() {
        return databasePath;
    }
}
