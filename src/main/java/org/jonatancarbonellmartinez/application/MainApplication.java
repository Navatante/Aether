package org.jonatancarbonellmartinez.application;

import javafx.application.Application;
import javafx.stage.Stage;
import org.jonatancarbonellmartinez.application.coordinator.MainCoordinator;
import org.jonatancarbonellmartinez.application.coordinator.PersonCoordinator;
import org.jonatancarbonellmartinez.application.di.AppComponent;
import org.jonatancarbonellmartinez.application.di.DaggerAppComponent;
import org.jonatancarbonellmartinez.presentation.viewmodel.DatabaseViewModel;

import javax.inject.Inject;

public class MainApplication extends Application {
    @Inject
    MainCoordinator mainCoordinator;

    private AppComponent appComponent;

    @Override
    public void start(Stage primaryStage) {
        // Initialize Dagger
        appComponent = DaggerAppComponent.factory().create();
        appComponent.inject(this);

        // Start coordinator
        mainCoordinator.start(primaryStage);

        // Navigate to initial view
        // mainCoordinator.navigateTo(MainCoordinator.class);
    }

    @Override
    public void stop() {
        if (mainCoordinator != null) {
            mainCoordinator.cleanup();
        }
    }
}