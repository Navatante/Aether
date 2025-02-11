package org.jonatancarbonellmartinez.data.repository;

import org.jonatancarbonellmartinez.data.database.configuration.DatabaseConnection;
import org.jonatancarbonellmartinez.domain.repository.contract.PersonRepository;
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

    private final DatabaseConnection databaseConnection;
    private final PersonRepositoryImpl personRepository;
    private final ExecutorService executorService;
    private Connection connection;
    private final List<Runnable> queries;

    @Inject
    public UnitOfWork(DatabaseConnection databaseConnection, PersonRepositoryImpl personRepository) {
        this.databaseConnection = databaseConnection;
        this.personRepository = personRepository;
        this.executorService = Executors.newFixedThreadPool(2); // Puedes ajustarlo si lo deseas
        this.queries = new ArrayList<>();
    }

    /**
     * Inicia una nueva transacción.
     */
    public void begin() {
        try {
            connection = databaseConnection.getConnection();
            databaseConnection.beginTransaction(connection);
        } catch (Exception e) {
            throw new DatabaseException("Failed to begin transaction", e);
        }
    }

    /**
     * Registra una operación para que se ejecute en la transacción.
     */
    public void registerOperation(Runnable query) {
        queries.add(query);
    }

    /**
     * Ejecuta todas las operaciones registradas dentro de una transacción y luego hace commit.
     */
    public void commit() {
        try {
            for (Runnable query : queries) {
                query.run(); // Ejecuta todas las queries registradas
            }
            databaseConnection.commitTransaction(connection);
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
                databaseConnection.rollbackTransaction(connection);
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
                databaseConnection.close(connection);
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
