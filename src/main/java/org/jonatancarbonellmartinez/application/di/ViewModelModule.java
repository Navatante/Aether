package org.jonatancarbonellmartinez.application.di;

import dagger.Module;
import dagger.Provides;
import org.jonatancarbonellmartinez.domain.repository.contract.PersonRepository;

// ViewModels para la UI.
@Module
public class ViewModelModule {
    @Provides
    PersonViewModel providePersonViewModel(
            PersonRepository repository,
            PersonMapper mapper
    ) {
        return new PersonViewModel(repository, mapper);
    }

    @Provides
    EventViewModel provideEventViewModel(
            EventRepository repository,
            EventMapper mapper
    ) {
        return new EventViewModel(repository, mapper);
    }
}
