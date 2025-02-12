package org.jonatancarbonellmartinez.application.di;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import org.jonatancarbonellmartinez.data.database.DAO.PersonDAO;
import org.jonatancarbonellmartinez.data.database.configuration.DatabaseConnection;
import org.jonatancarbonellmartinez.data.database.configuration.DatabaseProperties;
import org.jonatancarbonellmartinez.data.database.configuration.GlobalLoadingManager;
import org.jonatancarbonellmartinez.data.repository.PersonRepositoryImpl;

/**
 * Module that provides database-related dependencies.
 */
@Module
public class DatabaseModule {
    @Provides
    @Singleton
    DatabaseConnection provideDatabaseConnection(DatabaseProperties properties, GlobalLoadingManager loadingManager) {
        return new DatabaseConnection(properties, loadingManager);
    }

    @Provides
    @Singleton
    DatabaseProperties provideDatabase() {
        return new DatabaseProperties();
    }

    @Provides
    @Singleton
    public GlobalLoadingManager provideGlobalLoadingManager() {
        return new GlobalLoadingManager();
    }
}
