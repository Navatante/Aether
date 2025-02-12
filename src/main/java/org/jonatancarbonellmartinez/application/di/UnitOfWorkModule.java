package org.jonatancarbonellmartinez.application.di;


import dagger.Module;
import dagger.Provides;
import org.jonatancarbonellmartinez.data.database.configuration.DatabaseConnection;
import org.jonatancarbonellmartinez.data.repository.PersonRepositoryImpl;
import org.jonatancarbonellmartinez.data.repository.UnitOfWork;


import javax.inject.Singleton;

@Module
public abstract class UnitOfWorkModule {

    /**
     * Provides UnitOfWork instance.
     * Using @Provides since we need to construct with specific dependencies.
     */
    @Provides
    @Singleton
    static UnitOfWork provideUnitOfWork(DatabaseConnection databaseConnection,
                                        PersonRepositoryImpl personRepository) {
        return new UnitOfWork(databaseConnection, personRepository);
    }
}
