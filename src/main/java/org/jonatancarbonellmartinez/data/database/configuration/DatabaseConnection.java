package org.jonatancarbonellmartinez.data.database.configuration;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.jonatancarbonellmartinez.services.CustomLogger;
import org.jonatancarbonellmartinez.exceptions.DatabaseException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
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

    // Este metodo ejecuta las operaciones de la base de datos de forma asíncrona. El primero en usarlo ha sido PersonRepositoryImpl.
    public <T> CompletableFuture<T> executeOperation(DatabaseOperation<T> operation, boolean isWrite) {
        Platform.runLater(loadingManager::startLoading);

        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = getConnection(isWrite)) {
                return operation.execute(connection);
            } catch (Exception e) {
                CustomLogger.logError("Database operation failed", e);
                throw new DatabaseException("Database operation failed", e);
            } finally {
                Platform.runLater(loadingManager::endLoading);
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
    public Connection getConnection(boolean isWrite) throws SQLException {
        if (!isDatabaseFilePresent()) {
            CustomLogger.logError("Database file does not exist: " + databasePath.get(), null);
            throw new SQLException("Database file does not exist: " + databasePath.get());
        }

        try {
            Connection conn = DriverManager.getConnection(databasePath.get());
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");   // Habilita claves foráneas.
                stmt.execute("PRAGMA busy_timeout = 5000;");  // Configura el tiempo de espera en 5 segundos.

                if (isWrite) {
                    // Si es una conexión de escritura, limpia e inserta en session_info
                    stmt.execute("DELETE FROM session_info;");
                    String userName = System.getProperty("user.name");
                    String ipAddress = InetAddress.getLocalHost().getHostAddress();

                    String insertSQL = "INSERT INTO session_info (user_id, ip_address) VALUES (?, ?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                        pstmt.setString(1, userName);
                        pstmt.setString(2, ipAddress);
                        pstmt.executeUpdate();
                    }
                }
            } catch (UnknownHostException e) {
                CustomLogger.logError("Failed to retrieve IP address", e);
                throw new RuntimeException("Failed to retrieve IP address", e);
            }
            return conn;
        } catch (SQLException e) {
            CustomLogger.logError("Error connecting to database", e);
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
