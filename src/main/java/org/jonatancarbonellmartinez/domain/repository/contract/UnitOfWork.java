package org.jonatancarbonellmartinez.domain.repository.contract;

import java.util.concurrent.CompletableFuture;

public interface UnitOfWork extends AutoCloseable {
    void begin();
    void commit();
    void rollback();

    PersonRepository personRepository();
    //EventRepository eventRepository();
    // ... otros repositorios

    // Para operaciones asíncronas
    CompletableFuture<Void> executeAsync(UnitOfWorkAction action);

    @FunctionalInterface
    interface UnitOfWorkAction {
        void execute(UnitOfWork uow) throws Exception;
    }
}
