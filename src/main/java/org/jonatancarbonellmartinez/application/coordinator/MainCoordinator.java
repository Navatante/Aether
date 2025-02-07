package org.jonatancarbonellmartinez.application.coordinator;

import javafx.stage.Stage;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class MainCoordinator {
    private Stage primaryStage;
    private final Map<Class<?>, BaseCoordinator> coordinators = new HashMap<>();

    @Inject
    public MainCoordinator(
            FlightCoordinator flightCoordinator,
            PersonCoordinator personCoordinator
            // ... otros coordinadores
    ) {
        coordinators.put(FlightCoordinator.class, flightCoordinator);
        coordinators.put(PersonCoordinator.class, personCoordinator);
        // ... registrar otros coordinadores
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Mi Aplicación JavaFX");
        primaryStage.show();
    }

    public void navigateTo(Class<?> viewClass) {
        BaseCoordinator coordinator = coordinators.get(viewClass);
        if (coordinator != null) {
            coordinator.start(primaryStage);
        } else {
            System.out.println("No se encontró un coordinador para la vista: " + viewClass.getSimpleName());
        }
    }
}
