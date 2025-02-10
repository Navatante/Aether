package org.jonatancarbonellmartinez.application;

import javafx.application.Application;
import javafx.stage.Stage;
import org.jonatancarbonellmartinez.application.coordinator.MainCoordinator;
import org.jonatancarbonellmartinez.application.di.AppComponent;
import org.jonatancarbonellmartinez.application.di.DaggerAppComponent;

import javax.inject.Inject;

public class MainApplication extends Application {
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