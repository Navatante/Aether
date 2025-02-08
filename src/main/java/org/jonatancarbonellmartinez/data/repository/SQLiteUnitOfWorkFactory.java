package org.jonatancarbonellmartinez.data.repository;

import org.jonatancarbonellmartinez.data.database.configuration.ConnectionManager;
import org.jonatancarbonellmartinez.data.database.SQLiteTransactionManager;
import org.jonatancarbonellmartinez.domain.repository.contract.UnitOfWork;
import org.jonatancarbonellmartinez.domain.repository.contract.UnitOfWorkFactory;
import javax.inject.Inject;

public class SQLiteUnitOfWorkFactory implements UnitOfWorkFactory {
    private final ConnectionManager connectionManager;
    private final SQLiteTransactionManager transactionManager;
    private final PersonRepositoryImpl personRepository;

    @Inject
    public SQLiteUnitOfWorkFactory(
            ConnectionManager connectionManager,
            SQLiteTransactionManager transactionManager,
            PersonRepositoryImpl personRepository
    ) {
        this.connectionManager = connectionManager;
        this.transactionManager = transactionManager;
        this.personRepository = personRepository;
    }

    @Override
    public UnitOfWork create() {
        return new SQLiteUnitOfWork(connectionManager, transactionManager, personRepository);
    }
}
