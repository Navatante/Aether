package org.jonatancarbonellmartinez.application.di;

import dagger.Module;
import dagger.Provides;
import org.jonatancarbonellmartinez.data.database.DAO.PersonDAO;

import javax.inject.Singleton;

@Module
public abstract class DAOModule {
    /**
     * Provides PersonDAO instance.
     * Using @Provides since we're creating a concrete class.
     */
    @Provides
    @Singleton
    static PersonDAO providePersonDAO() {
        return new PersonDAO();
    }
}
