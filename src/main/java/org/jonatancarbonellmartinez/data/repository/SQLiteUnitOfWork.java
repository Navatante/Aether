package org.jonatancarbonellmartinez.data.repository;

import org.jonatancarbonellmartinez.domain.repository.contract.PersonRepository;
import org.jonatancarbonellmartinez.domain.repository.contract.UnitOfWork;
import org.jonatancarbonellmartinez.data.database.ConnectionManager;
import org.jonatancarbonellmartinez.data.database.SQLiteTransactionManager;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class SQLiteUnitOfWork implements UnitOfWork {
    private final ConnectionManager connectionManager;
    private final SQLiteTransactionManager transactionManager;
    private final ExecutorService executorService;
    private final PersonRepositoryImpl personRepository;

    @Inject
    public SQLiteUnitOfWork(
            ConnectionManager connectionManager,
            SQLiteTransactionManager transactionManager,
            PersonRepositoryImpl personRepository
    ) {
        this.connectionManager = connectionManager;
        this.transactionManager = transactionManager;
        this.personRepository = personRepository;
        this.executorService = Executors.newFixedThreadPool(2);
    }

    @Override
    public void begin() {
        transactionManager.beginTransaction();
    }

    @Override
    public void commit() {
        transactionManager.commitTransaction();
    }

    @Override
    public void rollback() {
        transactionManager.rollbackTransaction();
    }

    @Override
    public PersonRepository personRepository() {
        return personRepository;
    }

    @Override
    public CompletableFuture<Void> executeAsync(UnitOfWorkAction action) {
        return CompletableFuture.runAsync(() -> {
            try {
                begin();
                action.execute(this);
                commit();
            } catch (Exception e) {
                rollback();
                throw new RuntimeException("Transaction failed", e);
            }
        }, executorService);
    }

    @Override
    public void close() {
        try {
            if (transactionManager.isTransactionActive()) {
                rollback();
            }
            connectionManager.releaseConnection();
        } catch (Exception e) {
            throw new RuntimeException("Error closing unit of work", e);
        }
    }
}
