package org.jonatancarbonellmartinez.application.di;

import dagger.Module;
import dagger.Provides;
import org.jonatancarbonellmartinez.data.mapper.PersonMapper;
import org.jonatancarbonellmartinez.presentation.mapper.PersonUiMapper;

import javax.inject.Singleton;

@Module
public class MapperModule {
    @Provides
    @Singleton
    PersonMapper providePersonDataMapper() {
        return new PersonMapper();
    }

    @Provides
    @Singleton
    PersonUiMapper providePersonUiMapper() {
        return new PersonUiMapper();
    }
}
