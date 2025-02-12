package org.jonatancarbonellmartinez.application.di;

import dagger.Module;
import dagger.Provides;
import org.jonatancarbonellmartinez.data.mapper.PersonMapper;
import org.jonatancarbonellmartinez.presentation.mapper.PersonUiMapper;
import org.jonatancarbonellmartinez.services.DateService;

import javax.inject.Singleton;

@Module
public class MapperModule {
    @Provides
    @Singleton
    PersonMapper providePersonDataMapper(DateService dateService) {
        return new PersonMapper(dateService);
    }

    @Provides
    @Singleton
    PersonUiMapper providePersonUiMapper(DateService dateService) {
        return new PersonUiMapper(dateService);
    }
}
