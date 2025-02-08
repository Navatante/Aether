package org.jonatancarbonellmartinez.application.di;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import org.jonatancarbonellmartinez.data.database.configuration.Database;

/**
 * Este módulo proporciona una instancia de la base de datos.
 */

// Provee conexiones y DAOs.
@Module
public class DatabaseModule {
    @Provides
    @Singleton
    Database provideDatabase() {
        return Database.getInstance();
    }
}
