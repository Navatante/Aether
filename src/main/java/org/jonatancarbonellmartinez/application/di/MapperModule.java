package org.jonatancarbonellmartinez.application.di;

import dagger.Module;
import dagger.Provides;
import org.jonatancarbonellmartinez.data.mapper.PersonEntityDomainMapper;
import org.jonatancarbonellmartinez.presentation.mapper.PersonDomainUiMapper;
import org.jonatancarbonellmartinez.services.DateService;
import org.jonatancarbonellmartinez.services.DniService;

import javax.inject.Singleton;

@Module
public class MapperModule {
    @Provides
    @Singleton
    PersonEntityDomainMapper providePersonDataMapper(DateService dateService) {
        return new PersonEntityDomainMapper(dateService);
    }

    @Provides
    @Singleton
    PersonDomainUiMapper providePersonUiMapper(DateService dateService, DniService dniService) {
        return new PersonDomainUiMapper(dateService, dniService);
    }
}
