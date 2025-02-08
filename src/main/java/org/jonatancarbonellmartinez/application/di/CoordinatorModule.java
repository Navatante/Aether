package org.jonatancarbonellmartinez.application.di;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import org.jonatancarbonellmartinez.application.coordinator.BaseCoordinator;
import org.jonatancarbonellmartinez.application.coordinator.MainCoordinator;
import org.jonatancarbonellmartinez.application.coordinator.PersonCoordinator;
import org.jonatancarbonellmartinez.presentation.view.controller.PersonViewController;
import org.jonatancarbonellmartinez.presentation.viewmodel.PersonViewModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Este módulo proporciona un coordinador, que organiza la navegación o interacción entre ViewModels.
 */

// Coordinadores para navegación entre pantallas.
@Module
public class CoordinatorModule {
    @Provides
    @Singleton
    Map<Class<?>, BaseCoordinator> provideCoordinators(
            PersonCoordinator personCoordinator
            // Otros coordinadores...
    ) {
        Map<Class<?>, BaseCoordinator> coordinators = new HashMap<>();
        coordinators.put(PersonCoordinator.class, personCoordinator);
        // Agregar otros coordinadores al mapa...
        return coordinators;
    }

    @Provides
    @Singleton
    MainCoordinator provideMainCoordinator(
            Map<Class<?>, BaseCoordinator> coordinators
    ) {
        return new MainCoordinator(coordinators);
    }

    @Provides
    @Singleton
    PersonCoordinator providePersonCoordinator(
            PersonViewModel viewModel,
            PersonViewController controller) {
        return new PersonCoordinator(viewModel, controller);
    }

    // Proveer otros coordinadores...
}
