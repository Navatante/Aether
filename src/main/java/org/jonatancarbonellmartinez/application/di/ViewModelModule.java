package org.jonatancarbonellmartinez.application.di;

import dagger.Module;
import dagger.Provides;
import org.jonatancarbonellmartinez.data.database.configuration.DatabaseConnection;
import org.jonatancarbonellmartinez.data.database.configuration.GlobalLoadingManager;
import org.jonatancarbonellmartinez.data.repository.PersonRepositoryImpl;
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
            PersonRepositoryImpl repository,
            PersonUiMapper mapper,
            DatabaseConnection databaseConnection,
            GlobalLoadingManager loadingManager
    ) {
        return new PersonViewModel(repository, mapper, databaseConnection, loadingManager);
    }

//    @Provides
//    EventViewModel provideEventViewModel(
//            EventRepository repository,
//            EventMapper mapper
//    ) {
//        return new EventViewModel(repository, mapper);
//    }
}
