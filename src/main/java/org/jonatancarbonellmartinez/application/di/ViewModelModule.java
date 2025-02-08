package org.jonatancarbonellmartinez.application.di;

import dagger.Module;
import dagger.Provides;
import org.jonatancarbonellmartinez.domain.repository.contract.PersonRepository;
import org.jonatancarbonellmartinez.presentation.mapper.PersonUiMapper;
import org.jonatancarbonellmartinez.presentation.viewmodel.PersonViewModel;

/**
 * Este módulo proporciona ViewModels, que manejan la lógica de presentación.
 */

// ViewModels para la UI.
@Module
public class ViewModelModule {
    @Provides
    PersonViewModel providePersonViewModel(
            PersonRepository repository,
            PersonUiMapper mapper
    ) {
        return new PersonViewModel(repository, mapper);
    }

//    @Provides
//    EventViewModel provideEventViewModel(
//            EventRepository repository,
//            EventMapper mapper
//    ) {
//        return new EventViewModel(repository, mapper);
//    }
}
