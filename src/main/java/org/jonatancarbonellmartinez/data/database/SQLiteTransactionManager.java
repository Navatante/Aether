package org.jonatancarbonellmartinez.data.database;

import org.jonatancarbonellmartinez.data.database.configuration.ConnectionManager;
import org.jonatancarbonellmartinez.domain.repository.contract.DatabaseTransactionManager;
import org.jonatancarbonellmartinez.exceptions.DatabaseException;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLiteTransactionManager implements DatabaseTransactionManager {
    private final ConnectionManager connectionManager;
    private final ThreadLocal<Boolean> transactionActive = ThreadLocal.withInitial(() -> false);

    @Inject
    public SQLiteTransactionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public void beginTransaction() {
        try {
            Connection conn = connectionManager.getConnection();
            if (!transactionActive.get()) {
                conn.setAutoCommit(false);
                transactionActive.set(true);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error beginning transaction", e);
        }
    }

    @Override
    public void commitTransaction() {
        try {
            if (transactionActive.get()) {
                Connection conn = connectionManager.getConnection();
                conn.commit();
                conn.setAutoCommit(true);
                transactionActive.set(false);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error committing transaction", e);
        }
    }

    @Override
    public void rollbackTransaction() {
        try {
            if (transactionActive.get()) {
                Connection conn = connectionManager.getConnection();
                conn.rollback();
                conn.setAutoCommit(true);
                transactionActive.set(false);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error rolling back transaction", e);
        }
    }

    @Override
    public boolean isTransactionActive() {
        return transactionActive.get();
    }
}
