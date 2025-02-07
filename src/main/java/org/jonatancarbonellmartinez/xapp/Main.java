package org.jonatancarbonellmartinez.xapp;

import javafx.application.Application;
import javafx.stage.Stage;
import org.jonatancarbonellmartinez.presentation.view.fxml.xMainXView;
import org.jonatancarbonellmartinez.presentation.viewmodel.DatabaseViewModel;

import java.util.Objects;

// TODO sigue la estructura MVVM-C el Coordinator seria lo que estaba haciendo el NavigationController
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // TODO Cargar el CSS global
        Application.setUserAgentStylesheet(Objects.requireNonNull(getClass().getResource("/css/global-FlatLafDark.css")).toExternalForm());

        DatabaseViewModel databaseViewModel = new DatabaseViewModel();

        // Solo procedemos a mostrar la MainView si la conexión es exitosa
        databaseViewModel.connectionSuccessProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                // Inicializar y mostrar la vista principal
                xMainXView mainView = new xMainXView();
                //mainView.show(primaryStage);
            }
        });

        // Iniciar el proceso de verificación de la base de datos
        databaseViewModel.checkAndInitializeDatabase();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
