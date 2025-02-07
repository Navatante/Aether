package org.jonatancarbonellmartinez.application.di;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import org.jonatancarbonellmartinez.data.repository.PersonRepositoryImpl;
import org.jonatancarbonellmartinez.domain.repository.contract.PersonRepository;

/**
 * Este módulo administra los repositorios, que manejan la lógica de acceso a datos.
 */


// Implementaciones de repositorios.
@Module
public class RepositoryModule {
    @Provides
    @Singleton
    PersonRepository providePersonRepository(PersonRepositoryImpl impl) {
        return impl;
    }

//    @Provides
//    @Singleton
//    EventRepository provideEventRepository(EventRepositoryImpl impl) {
//        return impl;
//    }

    // Agregar más repositories según necesites
}
