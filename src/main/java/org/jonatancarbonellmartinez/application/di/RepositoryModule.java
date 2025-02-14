package org.jonatancarbonellmartinez.application.di;

import dagger.Binds;
import dagger.Module;

import javax.inject.Singleton;

import org.jonatancarbonellmartinez.data.repository.PersonRepositoryImpl;
import org.jonatancarbonellmartinez.domain.repository.contract.PersonRepository;

/**
 * This module provides bindings for repositories and UnitOfWork.
 * We use a combination of @Binds and @Provides annotations:
 *
 * - @Binds for interface-to-implementation bindings (PersonRepository)
 * - @Provides for concrete class instantiation (PersonDAO, UnitOfWork)
 */
@Module
public abstract class RepositoryModule {

    /**
     * Binds the PersonRepository interface to its implementation.
     * Using @Binds is more efficient than @Provides for this case.
     */
    @Binds
    @Singleton
    abstract PersonRepository bindPersonRepository(PersonRepositoryImpl implementation);
}