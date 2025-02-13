package org.jonatancarbonellmartinez.application.coordinator;

import org.jonatancarbonellmartinez.presentation.controller.AddPersonViewController;
import org.jonatancarbonellmartinez.presentation.viewmodel.AddPersonViewModel;
import org.jonatancarbonellmartinez.presentation.viewmodel.PersonViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jonatancarbonellmartinez.presentation.controller.PersonViewController;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

/**
 * La vista solo se encarga de la UI
 * El ViewModel maneja la lógica de presentación
 * El Coordinator se encarga específicamente de:
 *
 * La navegación hacia/desde esa vista
 * La creación y configuración del ViewModel
 * La inicialización de dependencias específicas de esa vista
 *
 * Cada Coordinator puede manejar el ciclo de vida específico de su vista
 * Puede limpiar recursos cuando la vista se cierra
 * Puede manejar la persistencia de estado específica de esa vista
 */

@Singleton
public class PersonCoordinator extends BaseCoordinator {
    private final PersonViewModel viewModel;
    private final PersonViewController controller;  // Inject controller directly
    private final FXMLLoader fxmlLoader;

    @Inject
    public PersonCoordinator(PersonViewModel viewModel, PersonViewController controller) {
        this.viewModel = viewModel;
        this.controller = controller;
        this.fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/PersonView.fxml"));
        this.fxmlLoader.setController(controller); // Set the controller explicitly
    }

    @Override
    protected void onStart() {
        try {
            VBox root = fxmlLoader.load();
            Scene scene = new Scene(root);
            applyStylesheet(scene);  // Apply the dark theme
            primaryStage.setScene(scene);

            // Load initial data
            viewModel.loadPersons();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showEditPersonDialog() {
        try {
            // Crear Stage para el diálogo
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setTitle("Editar Persona");

            // Cargar FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EditPersonDialog.fxml"));
            VBox dialogRoot = loader.load();

            // Configurar ViewModel y Controller
            AddPersonViewController controller = loader.getController();
            AddPersonViewModel viewModel

            // Configurar y mostrar escena
            Scene dialogScene = new Scene(dialogRoot);
            applyStylesheet(dialogScene);
            dialogStage.setScene(dialogScene);


            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            // Aquí deberías manejar el error apropiadamente
        }
    }

    public void cleanup() {
        viewModel.cleanup();
    }
}

