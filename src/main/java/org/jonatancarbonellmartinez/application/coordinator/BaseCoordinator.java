package org.jonatancarbonellmartinez.application.coordinator;

import javafx.stage.Stage;

public abstract class BaseCoordinator {
    protected Stage primaryStage;

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        onStart();
    }

    protected abstract void onStart(); // Metodo abstracto para que cada coordinador implemente su lógica
}
