package org.jonatancarbonellmartinez.data.database.configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Manages database operations in a simplified way.
 * Works directly with SQLite's built-in connection management.
 */
@Singleton
public class DatabaseManager {
    private final Database database;

    @Inject
    public DatabaseManager(Database database) {
        this.database = database;
    }

    /**
     * Gets a database connection
     */
    public Connection getConnection() throws SQLException {
        return database.getConnection();
    }

    /**
     * Safely closes a database connection
     */
    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // Log error but don't throw - closing shouldn't fail
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    /**
     * Begins a transaction
     */
    public void beginTransaction(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.setAutoCommit(false);
        }
    }

    /**
     * Commits a transaction
     */
    public void commitTransaction(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.commit();
            connection.setAutoCommit(true);
        }
    }

    /**
     * Rolls back a transaction
     */
    public void rollbackTransaction(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.rollback();
            connection.setAutoCommit(true);
        }
    }
}