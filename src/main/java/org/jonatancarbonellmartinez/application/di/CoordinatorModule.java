package org.jonatancarbonellmartinez.application.di;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import org.jonatancarbonellmartinez.application.coordinator.BaseCoordinator;
import org.jonatancarbonellmartinez.application.coordinator.MainCoordinator;
import org.jonatancarbonellmartinez.application.coordinator.PersonCoordinator;
import org.jonatancarbonellmartinez.data.database.configuration.GlobalLoadingManager;
import org.jonatancarbonellmartinez.presentation.controller.AddPersonViewController;
import org.jonatancarbonellmartinez.presentation.controller.MainViewController;
import org.jonatancarbonellmartinez.presentation.controller.PersonViewController;
import org.jonatancarbonellmartinez.presentation.viewmodel.AddPersonViewModel;
import org.jonatancarbonellmartinez.presentation.viewmodel.PersonViewModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Este módulo proporciona coordinadores y controladores para la navegación y gestión de vistas.
 * Sigue el patrón MVVM-C (Model-View-ViewModel-Coordinator).
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
    AddPersonViewController provideAddPersonViewController(AddPersonViewModel viewModel) {
        return new AddPersonViewController(viewModel);
    }

    @Provides
    @Singleton
    MainViewController provideMainViewController(
            PersonViewController personViewController,
            GlobalLoadingManager loadingManager
    ) {
        return new MainViewController(personViewController, loadingManager);
    }

    @Provides
    @Singleton
    PersonCoordinator providePersonCoordinator(
            PersonViewModel personViewModel,
            PersonViewController personViewController,
            AddPersonViewModel addPersonViewModel,
            AddPersonViewController addPersonViewController
    ) {
        return new PersonCoordinator(
                personViewModel,
                personViewController,
                addPersonViewModel,
                addPersonViewController
        );
    }
}