package org.jonatancarbonellmartinez.application;

import javafx.application.Application;
import javafx.stage.Stage;
import org.jonatancarbonellmartinez.application.coordinator.MainCoordinator;
import org.jonatancarbonellmartinez.application.di.AppComponent;
import org.jonatancarbonellmartinez.application.di.DaggerAppComponent;
import org.jonatancarbonellmartinez.presentation.viewmodel.DatabaseCheckViewModel;

import javax.inject.Inject;

public class MainApplication extends Application {
    @Inject
    DatabaseCheckViewModel databaseCheckViewModel;

    @Inject
    MainCoordinator mainCoordinator;

    private AppComponent appComponent;

    @Override
    public void start(Stage primaryStage) {

        // Reset default stylesheet
        Application.setUserAgentStylesheet(null);

        // Initialize Dagger
        appComponent = DaggerAppComponent.factory().create();
        appComponent.inject(this);

        // Primero comprobamos conexion a la base de datos, si la conexion no es exitosa y no se elige Db, no se inicia la aplicacion.
        databaseCheckViewModel.checkAndInitializeDatabase();

        // Start coordinator
        mainCoordinator.start(primaryStage);
    }

    @Override
    public void stop() {
        if (mainCoordinator != null) {
            mainCoordinator.cleanup();
        }
    }
}