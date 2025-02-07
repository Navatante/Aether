package org.jonatancarbonellmartinez.application.di;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import org.jonatancarbonellmartinez.application.coordinator.MainCoordinator;

// Coordinadores para navegación entre pantallas.
@Module
public class CoordinatorModule {
    @Provides
    @Singleton
    MainCoordinator provideMainCoordinator(
            PersonViewModel personViewModel,
            EventViewModel eventViewModel
    ) {
        return new MainCoordinator(personViewModel, eventViewModel);
    }
}
