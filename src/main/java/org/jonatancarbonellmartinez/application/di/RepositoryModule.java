package org.jonatancarbonellmartinez.application.di;

import dagger.Binds;
import dagger.Module;
import javax.inject.Singleton;

import org.jonatancarbonellmartinez.data.repository.PersonRepositoryImpl;
import org.jonatancarbonellmartinez.data.repository.UnitOfWork;
import org.jonatancarbonellmartinez.domain.repository.contract.PersonRepository;

/**
 * This module provides bindings for repositories and UnitOfWork.
 * Using @Binds is more efficient than @Provides for simple bindings.
 */
@Module
public abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract UnitOfWork bindUnitOfWork();

    @Binds
    @Singleton
    abstract PersonRepository bindPersonRepository(PersonRepositoryImpl implementation);

    // Add other repository bindings as needed
}