package org.jonatancarbonellmartinez.application.di;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import org.jonatancarbonellmartinez.application.coordinator.BaseCoordinator;
import org.jonatancarbonellmartinez.application.coordinator.MainCoordinator;
import org.jonatancarbonellmartinez.application.coordinator.PersonCoordinator;
import org.jonatancarbonellmartinez.presentation.view.controller.MainViewController;
import org.jonatancarbonellmartinez.presentation.view.controller.PersonViewController;
import org.jonatancarbonellmartinez.presentation.viewmodel.PersonViewModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Este módulo proporciona un coordinador, que organiza la navegación o interacción entre ViewModels.
 */
@Module
public class CoordinatorModule {
    @Provides
    @Singleton
    Map<Class<?>, BaseCoordinator> provideCoordinators(
            PersonCoordinator personCoordinator
    ) {
        Map<Class<?>, BaseCoordinator> coordinators = new HashMap<>();
        coordinators.put(PersonCoordinator.class, personCoordinator);
        return coordinators;
    }

    @Provides
    @Singleton
    MainCoordinator provideMainCoordinator(
            Map<Class<?>, BaseCoordinator> coordinators,
            MainViewController mainViewController
    ) {
        return new MainCoordinator(coordinators, mainViewController);
    }

    @Provides
    @Singleton
    PersonViewController providePersonViewController(PersonViewModel viewModel) {
        return new PersonViewController(viewModel);
    }

    @Provides
    @Singleton
    MainViewController provideMainViewController(PersonViewController personViewController) {
        return new MainViewController(personViewController);
    }

    @Provides
    @Singleton
    PersonCoordinator providePersonCoordinator(
            PersonViewModel viewModel,
            PersonViewController personViewController
    ) {
        return new PersonCoordinator(viewModel, personViewController);
    }
}