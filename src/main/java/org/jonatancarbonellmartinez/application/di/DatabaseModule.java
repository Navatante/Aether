package org.jonatancarbonellmartinez.application.di;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import org.jonatancarbonellmartinez.data.database.DAO.PersonDAO;
import org.jonatancarbonellmartinez.data.database.configuration.DatabaseConnection;
import org.jonatancarbonellmartinez.data.database.configuration.DatabaseProperties;
import org.jonatancarbonellmartinez.data.repository.PersonRepositoryImpl;

/**
 * Module that provides database-related dependencies.
 */
@Module
public class DatabaseModule {
    @Provides
    @Singleton
    DatabaseConnection provideDatabaseConnection(DatabaseProperties properties) {
        return new DatabaseConnection(properties);
    }

    @Provides
    @Singleton
    DatabaseProperties provideDatabase() {
        return new DatabaseProperties();
    }
}
