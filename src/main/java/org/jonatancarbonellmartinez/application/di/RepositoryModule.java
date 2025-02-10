package org.jonatancarbonellmartinez.application.di;

import dagger.Binds;
import dagger.Module;
import javax.inject.Singleton;

import org.jonatancarbonellmartinez.data.repository.PersonRepositoryImpl;
import org.jonatancarbonellmartinez.domain.repository.contract.PersonRepository;
import org.jonatancarbonellmartinez.domain.repository.contract.UnitOfWork;
import org.jonatancarbonellmartinez.data.repository.SQLiteUnitOfWork;

/**
 * This module provides bindings for repositories and UnitOfWork.
 * Using @Binds is more efficient than @Provides for simple bindings.
 */
@Module
public abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract UnitOfWork bindUnitOfWork(SQLiteUnitOfWork implementation);

    @Binds
    @Singleton
    abstract PersonRepository bindPersonRepository(PersonRepositoryImpl implementation);

    // Add other repository bindings as needed
}