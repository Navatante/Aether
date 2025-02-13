package org.jonatancarbonellmartinez.data.database.configuration;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.jonatancarbonellmartinez.services.CustomLogger;
import org.jonatancarbonellmartinez.exceptions.DatabaseException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.concurrent.CompletableFuture;

@Singleton
public class DatabaseConnection {
    private final StringProperty databasePath;
    private final DatabaseProperties properties;
    private final GlobalLoadingManager loadingManager;

    @Inject
    public DatabaseConnection(DatabaseProperties properties, GlobalLoadingManager loadingManager) {
        this.properties = properties;
        this.loadingManager = loadingManager;
        this.databasePath = new SimpleStringProperty();
        loadPathFromProperties();
    }

    public <T> CompletableFuture<T> executeOperation(DatabaseOperation<T> operation) {
        return CompletableFuture.supplyAsync(() -> {
            loadingManager.startLoading();
            try (Connection connection = getConnection()) {
                return operation.execute(connection);
            } catch (Exception e) {
                throw new DatabaseException("Database operation failed", e);
            } finally {
                loadingManager.endLoading();
            }
        });
    }

    @FunctionalInterface
    public interface DatabaseOperation<T> {
        T execute(Connection connection) throws Exception;
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
            Connection conn = DriverManager.getConnection(databasePath.get());
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");   // Toda conexion tendra implicitamente habilitadas las claves foraneas.
                stmt.execute("PRAGMA busy_timeout = 5000;");  // Toda conexion tendra implicitamente reintentos de conexion en intervalos de microsegundos aleatorios durante 5 seg.
            }
            return conn;
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
