package org.jonatancarbonellmartinez.data.repository;

import org.jonatancarbonellmartinez.domain.repository.contract.PersonRepository;
import org.jonatancarbonellmartinez.domain.repository.contract.UnitOfWork;
import org.jonatancarbonellmartinez.data.database.configuration.DatabaseManager;
import org.jonatancarbonellmartinez.exceptions.DatabaseException;

import javax.inject.Inject;
import java.sql.Connection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class SQLiteUnitOfWork implements UnitOfWork {
    private final DatabaseManager databaseManager;
    private final PersonRepositoryImpl personRepository;
    private final ExecutorService executorService;
    private Connection connection;

    @Inject
    public SQLiteUnitOfWork(
            DatabaseManager databaseManager,
            PersonRepositoryImpl personRepository
    ) {
        this.databaseManager = databaseManager;
        this.personRepository = personRepository;
        this.executorService = Executors.newFixedThreadPool(2);
    }

    @Override
    public void begin() {
        try {
            connection = databaseManager.getConnection();
            databaseManager.beginTransaction(connection);
        } catch (Exception e) {
            throw new DatabaseException("Failed to begin transaction", e);
        }
    }

    @Override
    public void commit() {
        try {
            if (connection != null) {
                databaseManager.commitTransaction(connection);
            }
        } catch (Exception e) {
            throw new DatabaseException("Failed to commit transaction", e);
        }
    }

    @Override
    public void rollback() {
        try {
            if (connection != null) {
                databaseManager.rollbackTransaction(connection);
            }
        } catch (Exception e) {
            throw new DatabaseException("Failed to rollback transaction", e);
        }
    }

    @Override
    public PersonRepository personRepository() {
        return null;
    }

    @Override
    public CompletableFuture<Void> executeAsync(UnitOfWorkAction action) {
        return null;
    }

    @Override
    public void close() {
        try {
            if (connection != null) {
                databaseManager.closeConnection(connection);
            }
        } finally {
            executorService.shutdown();
        }
    }
}