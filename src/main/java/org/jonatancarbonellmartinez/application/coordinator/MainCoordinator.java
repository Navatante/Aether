package org.jonatancarbonellmartinez.application.coordinator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jonatancarbonellmartinez.presentation.view.MainViewController;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
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
    private final MainViewController mainViewController;

    @Inject
    public MainCoordinator(
            Map<Class<?>, BaseCoordinator> coordinators,
            MainViewController mainViewController
    ) {
        this.coordinators = coordinators;
        this.mainViewController = mainViewController;
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.initStyle(StageStyle.TRANSPARENT); // Necesario para border redondeados (implementarlos mas adelante)

        try {
            // Load MainView
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            loader.setController(mainViewController);
            Scene scene = new Scene(loader.load());

            // Necesario para border redondeados (implementarlos mas adelante)
            scene.setFill(Color.TRANSPARENT);

            // Apply styles
            String css = getClass().getResource("/css/styles/dark-theme.css").toExternalForm();
            scene.getStylesheets().add(css);

            primaryStage.setScene(scene);

            // Set the taskbar icon
            primaryStage.getIcons().add(new Image("/images/Icon_Aether_provisional_logo.png"));

            // Configure close event
            primaryStage.setOnCloseRequest(event -> cleanup());

            primaryStage.show();

            // TODO todavia no navego a ningun sitio hasta que tenga controlado el tema base de datos.
            // Navigate to PersonView by default
            //mainViewController.loadView("PersonView");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
