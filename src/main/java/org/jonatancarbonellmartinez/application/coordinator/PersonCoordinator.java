package org.jonatancarbonellmartinez.application.coordinator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jonatancarbonellmartinez.presentation.controller.AddPersonViewController;
import org.jonatancarbonellmartinez.presentation.navigation.PersonNavigationCallback;
import org.jonatancarbonellmartinez.presentation.viewmodel.AddPersonViewModel;
import org.jonatancarbonellmartinez.presentation.viewmodel.PersonViewModel;
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
public class PersonCoordinator extends BaseCoordinator implements PersonNavigationCallback {
    private final PersonViewModel personViewModel;
    private final PersonViewController personViewController;
    private final AddPersonViewModel addPersonViewModel;
    private final AddPersonViewController addPersonViewController;

    @Inject
    public PersonCoordinator(
            PersonViewModel personViewModel,
            PersonViewController personViewController,
            AddPersonViewModel addPersonViewModel,
            AddPersonViewController addPersonViewController
    ) {
        this.personViewModel = personViewModel;
        this.personViewController = personViewController;
        this.addPersonViewModel = addPersonViewModel;
        this.addPersonViewController = addPersonViewController;

        // Establecer el callback de navegación
        this.personViewController.setNavigationCallback(this);
    }

    @Override
    public void onAddPersonRequested() {
        showAddPersonDialog();
    }

    @Override
    protected void onStart() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PersonView.fxml"));
            loader.setController(personViewController);
            VBox root = loader.load();
            Scene scene = new Scene(root);
            applyStylesheet(scene);
            primaryStage.setScene(scene);

            // Load initial data
            personViewModel.loadPersons();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAddPersonDialog() {
        try {
            // Create dialog stage
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.initStyle(StageStyle.UNIFIED);
            dialogStage.setTitle("Add New Person");
            dialogStage.setResizable(false);

            // Load FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddPersonView.fxml"));
            loader.setController(addPersonViewController);
            VBox dialogRoot = loader.load();

            // Setup and show scene
            Scene dialogScene = new Scene(dialogRoot);
            applyStylesheet(dialogScene);
            dialogStage.setScene(dialogScene);

            // Reset view model state
            addPersonViewModel.reset();

            // Show dialog
            dialogStage.showAndWait();

            // Refresh person list after dialog closes
            personViewModel.loadPersons();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cleanup() {
        personViewModel.cleanup();
    }
}