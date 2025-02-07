package org.jonatancarbonellmartinez.application;

import javafx.application.Application;
import javafx.stage.Stage;
import org.jonatancarbonellmartinez.application.coordinator.MainCoordinator;
import org.jonatancarbonellmartinez.application.di.AppComponent;
import org.jonatancarbonellmartinez.application.di.DaggerAppComponent;
import org.jonatancarbonellmartinez.presentation.viewmodel.DatabaseViewModel;

import javax.inject.Inject;

public class MainApplication extends Application {
    @Inject
    MainCoordinator mainCoordinator;

    @Inject
    DatabaseViewModel databaseViewModel;

    private AppComponent appComponent;

    @Override
    public void init() {
        appComponent = DaggerAppComponent.builder().build();
        appComponent.inject(this);
    }

    @Override
    public void start(Stage primaryStage) {
        // Check database connection first
        databaseViewModel.connectionSuccessProperty().addListener((obs, old, success) -> {
            if (success) {
                mainCoordinator.start(primaryStage);
            }
        });

        databaseViewModel.checkAndInitializeDatabase();
    }

    public static void main(String[] args) {
        launch(args);
    }
}