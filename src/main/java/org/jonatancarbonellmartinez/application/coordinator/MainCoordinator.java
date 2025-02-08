package org.jonatancarbonellmartinez.application.coordinator;

import javafx.stage.Stage;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

/**
 * Los Coordinators en una arquitectura MVVM-C (Model-View-ViewModel-Coordinator)
 * tienen la responsabilidad de manejar la navegación entre vistas y la lógica de coordinación en la UI.
 * Se encargan de instanciar los ViewModels y establecer la interacción entre ellos y las vistas.
 *
 * Este código implementa el patrón Coordinator en una arquitectura MVVM-C (Model-View-ViewModel-Coordinator), donde:
 *
 * -- Los coordinadores son responsables de la navegación entre vistas
 * -- Cada vista tiene su propio coordinador que hereda de BaseCoordinator
 * -- El MainCoordinator actúa como un router central que:
 *      Mantiene registro de todos los coordinadores disponibles
 *      Proporciona navegación entre diferentes vistas
 *      Maneja la ventana principal de la aplicación
 *
 *      Ejemplo de uso:
 *      // Para navegar a la vista de personas
 *          mainCoordinator.navigateTo(PersonCoordinator.class);
 */

@Singleton
public class MainCoordinator implements Cleanable {
    private final Map<Class<?>, BaseCoordinator> coordinators;
    private Stage primaryStage;
    private BaseCoordinator currentCoordinator;

    @Inject
    public MainCoordinator(Map<Class<?>, BaseCoordinator> coordinators) {
        this.coordinators = coordinators;
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Mi Aplicación JavaFX");

        // Configurar el evento de cierre
        primaryStage.setOnCloseRequest(event -> cleanup());

        primaryStage.show();
    }

    public void navigateTo(Class<?> viewClass) {
        // Limpiar el coordinador actual
        if (currentCoordinator != null) {
            currentCoordinator.cleanup();
        }

        BaseCoordinator coordinator = coordinators.get(viewClass);
        if (coordinator != null) {
            coordinator.start(primaryStage);
            currentCoordinator = coordinator;
        } else {
            System.out.println("No se encontró un coordinador para la vista: " + viewClass.getSimpleName());
        }
    }

    @Override
    public void cleanup() {
        // Limpiar el coordinador actual
        if (currentCoordinator != null) {
            currentCoordinator.cleanup();
        }

        // Limpiar todos los coordinadores por si acaso
        coordinators.values().forEach(BaseCoordinator::cleanup);
    }
}
