package org.jonatancarbonellmartinez.application.di;

import javax.inject.Singleton;
import dagger.Component;
import org.jonatancarbonellmartinez.application.MainApplication;
import org.jonatancarbonellmartinez.application.coordinator.MainCoordinator;

/**
 * Este es el componente principal de Dagger 2.
 * Su función es conectar los módulos (donde se definen las dependencias) con las clases que las necesitan.
 */

@Singleton // Indica que las instancias creadas por este componente serán únicas en toda la aplicación.
@Component(modules = { // Lista los módulos que proporcionan dependencias a la aplicación.
        DatabaseModule.class,
        ViewModelModule.class,
        RepositoryModule.class,
        CoordinatorModule.class,
        MapperModule.class
})
public interface AppComponent {
    // Factory para crear instancias
    @Component.Factory
    interface Factory {
        AppComponent create();
    }

    // Métodos para inyección
    void inject(MainApplication app); // Permite inyectar dependencias en MainApplication
    MainCoordinator mainCoordinator(); // Expone MainCoordinator para su uso en otras partes de la aplicacion.
}