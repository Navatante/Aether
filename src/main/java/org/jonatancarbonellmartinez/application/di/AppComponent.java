package org.jonatancarbonellmartinez.application.di;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {
        DatabaseModule.class,
        ViewModelModule.class,
        RepositoryModule.class,
        CoordinatorModule.class
})
public interface AppComponent {
    // Factory para crear instancias
    @Component.Factory
    interface Factory {
        AppComponent create();
    }

    // Métodos para inyección
    void inject(MainApplication app);
    MainCoordinator mainCoordinator();
}