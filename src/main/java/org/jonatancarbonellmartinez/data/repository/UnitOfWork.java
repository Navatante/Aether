package org.jonatancarbonellmartinez.data.repository;

import org.jonatancarbonellmartinez.domain.repository.contract.PersonRepository;
import org.jonatancarbonellmartinez.data.database.configuration.DatabaseManager;
import org.jonatancarbonellmartinez.exceptions.DatabaseException;

import javax.inject.Inject;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Implementación del patrón Unit of Work para gestionar las transacciones y repositorios.
 * Maneja el ciclo de vida de la transacción, el registro de operaciones y la ejecución asincrónica.
 */
public class UnitOfWork {

    private final DatabaseManager databaseManager;
    private final PersonRepositoryImpl personRepository;
    private final ExecutorService executorService;
    private Connection connection;
    private final List<Runnable> operations;

    @Inject
    public UnitOfWork(DatabaseManager databaseManager, PersonRepositoryImpl personRepository) {
        this.databaseManager = databaseManager;
        this.personRepository = personRepository;
        this.executorService = Executors.newFixedThreadPool(2); // Puedes ajustarlo si lo deseas
        this.operations = new ArrayList<>();
    }

    /**
     * Inicia una nueva transacción.
     */
    public void begin() {
        try {
            connection = databaseManager.getConnection();
            databaseManager.beginTransaction(connection);
        } catch (Exception e) {
            throw new DatabaseException("Failed to begin transaction", e);
        }
    }

    /**
     * Registra una operación para que se ejecute en la transacción.
     */
    public void registerOperation(Runnable operation) {
        operations.add(operation);
    }

    /**
     * Ejecuta todas las operaciones registradas dentro de una transacción y luego hace commit.
     */
    public void commit() {
        try {
            for (Runnable operation : operations) {
                operation.run(); // Ejecuta todas las operaciones registradas
            }
            databaseManager.commitTransaction(connection);
        } catch (Exception e) {
            rollback(); // En caso de error, revertir la transacción
            throw new DatabaseException("Failed to commit transaction", e);
        } finally {
            close();
        }
    }

    /**
     * Revierte la transacción si algo falla.
     */
    public void rollback() {
        try {
            if (connection != null) {
                databaseManager.rollbackTransaction(connection);
            }
        } catch (Exception e) {
            throw new DatabaseException("Failed to rollback transaction", e);
        } finally {
            close();
        }
    }

    /**
     * Devuelve el repositorio de Person.
     */
    public PersonRepository personRepository() {
        return personRepository;
    }

    /**
     * Cierra la conexión y finaliza cualquier operación pendiente.
     */
    public void close() {
        try {
            if (connection != null) {
                databaseManager.closeConnection(connection);
            }
        } finally {
            executorService.shutdown();
        }
    }

    /**
     * Ejecuta una operación asíncrona.
     */
    public CompletableFuture<Void> executeAsync(UnitOfWorkAction action) {
        return CompletableFuture.runAsync(() -> {
            try {
                action.execute(this);
            } catch (Exception e) {
                throw new DatabaseException("Async operation failed", e);
            }
        }, executorService);
    }

    /**
     * Funcional interface para ejecutar una operación dentro del UnitOfWork de forma asíncrona.
     */
    @FunctionalInterface
    public interface UnitOfWorkAction {
        void execute(UnitOfWork uow) throws Exception;
    }
}
