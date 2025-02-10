package org.jonatancarbonellmartinez.application.di;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import org.jonatancarbonellmartinez.data.database.PersonDAO;
import org.jonatancarbonellmartinez.data.database.configuration.Database;
import org.jonatancarbonellmartinez.data.database.configuration.Properties;

/**
 * Module that provides database-related dependencies.
 */
@Module
public class DatabaseModule {
    @Provides
    @Singleton
    Properties provideProperties() {
        return new Properties();
    }

    @Provides
    @Singleton
    Database provideDatabase(Properties properties) {
        return new Database(properties);
    }

    @Provides
    @Singleton
    PersonDAO providePersonDAO() {
        return new PersonDAO();
    }
}
