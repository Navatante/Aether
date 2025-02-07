package org.jonatancarbonellmartinez.application.di;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import org.jonatancarbonellmartinez.application.coordinator.MainCoordinator;
import org.jonatancarbonellmartinez.presentation.viewmodel.PersonViewModel;

/**
 * Este módulo proporciona un coordinador, que organiza la navegación o interacción entre ViewModels.
 */

// Coordinadores para navegación entre pantallas.
@Module
public class CoordinatorModule {
    @Provides
    @Singleton
    MainCoordinator provideMainCoordinator(
            PersonViewModel personViewModel
            //EventViewModel eventViewModel
    ) {
        return new MainCoordinator(personViewModel);
    }
}
