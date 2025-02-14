package org.jonatancarbonellmartinez.application.di;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import org.jonatancarbonellmartinez.data.database.configuration.DatabaseConnection;
import org.jonatancarbonellmartinez.data.database.configuration.GlobalLoadingManager;
import org.jonatancarbonellmartinez.domain.repository.contract.PersonRepository;
import org.jonatancarbonellmartinez.presentation.mapper.PersonDomainUiMapper;
import org.jonatancarbonellmartinez.presentation.viewmodel.AddPersonViewModel;
import org.jonatancarbonellmartinez.presentation.viewmodel.PersonViewModel;
import org.jonatancarbonellmartinez.services.DniService;

/**
 * Módulo que proporciona ViewModels para la capa de presentación.
 * Los ViewModels gestionan la lógica de presentación y el estado de la UI.
 */
@Module
public class ViewModelModule {

    @Provides
    @Singleton
    PersonViewModel providePersonViewModel(
            PersonRepository repository,
            PersonDomainUiMapper mapper,
            DatabaseConnection databaseConnection,
            GlobalLoadingManager loadingManager
    ) {
        return new PersonViewModel(
                repository,
                mapper,
                databaseConnection,
                loadingManager
        );
    }

    @Provides
    @Singleton
    AddPersonViewModel provideAddPersonViewModel(
            PersonRepository repository,
            PersonDomainUiMapper mapper,
            DatabaseConnection databaseConnection,
            GlobalLoadingManager loadingManager,
            DniService dniService
    ) {
        return new AddPersonViewModel(
                repository,
                mapper,
                databaseConnection,
                loadingManager,
                dniService
        );
    }

    // Future ViewModels can be added here as needed
    // For example:
    // @Provides
    // @Singleton
    // EditPersonViewModel provideEditPersonViewModel(...) {
    //     return new EditPersonViewModel(...);
    // }
}