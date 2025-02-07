package org.jonatancarbonellmartinez.application.di;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import org.jonatancarbonellmartinez.data.database.Database;

// Provee conexiones y DAOs.
@Module
public class DatabaseModule {
    @Provides
    @Singleton
    Database provideDatabase() {
        return Database.getInstance();
    }
}
