package org.jonatancarbonellmartinez.application.di;

import dagger.Binds;
import dagger.Module;
import javax.inject.Singleton;

import dagger.Provides;
import org.jonatancarbonellmartinez.data.database.DAO.PersonDAO;
import org.jonatancarbonellmartinez.data.database.configuration.DatabaseConnection;
import org.jonatancarbonellmartinez.data.mapper.PersonMapper;
import org.jonatancarbonellmartinez.data.repository.PersonRepositoryImpl;
import org.jonatancarbonellmartinez.data.repository.UnitOfWork;

/**
 * This module provides bindings for repositories and UnitOfWork.
 *
 * @Binds is best used when:
 *
 * 1. You're mapping an interface to its implementation
 * 2. There's a direct 1:1 relationship (the parameter type implements/extends the return type)
 * 3. No additional logic is needed to create the instance
 *
 * @Provides is better when:
 *
 * 1. You need to instantiate a concrete class
 * 2. You need custom logic to create the instance
 * 3. You need to configure the instance with specific parameters
 * 4. The type you're providing isn't a direct implementation of the return type
 */
@Module
public class RepositoryModule {

    @Provides
    @Singleton
    static PersonRepositoryImpl providePersonRepository(PersonDAO personDAO,
                                                        PersonMapper personMapper) {
        return new PersonRepositoryImpl(personDAO, personMapper);
    }

    @Provides
    @Singleton
    static UnitOfWork provideUnitOfWork(DatabaseConnection databaseConnection,
                                        PersonRepositoryImpl personRepository) {
        return new UnitOfWork(databaseConnection, personRepository);
    }

    @Provides
    @Singleton
    static PersonDAO providePersonDAO() {
        return new PersonDAO();
    }
}