package org.jonatancarbonellmartinez.application;

import javafx.application.Application;
import javafx.stage.Stage;
import org.jonatancarbonellmartinez.application.coordinator.MainCoordinator;
import org.jonatancarbonellmartinez.application.di.DaggerAppComponent;
import org.jonatancarbonellmartinez.application.di.AppComponent;

public class App extends Application {
    private AppComponent appComponent;

    @Override
    public void start(Stage primaryStage) {
        appComponent = DaggerAppComponent.create();

        // Iniciar el coordinador principal
        MainCoordinator coordinator = appComponent.mainCoordinator();
        coordinator.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
