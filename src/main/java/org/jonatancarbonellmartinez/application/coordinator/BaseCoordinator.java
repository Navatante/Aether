package org.jonatancarbonellmartinez.application.coordinator;

import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class BaseCoordinator implements Cleanable {
    protected Stage primaryStage;
    protected static final String DARK_THEME_CSS = "/css/styles/dark-theme.css";

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        onStart();
    }

    protected void applyStylesheet(Scene scene) {
        String css = getClass().getResource(DARK_THEME_CSS).toExternalForm();
        scene.getStylesheets().add(css);
    }

    protected abstract void onStart();

    @Override
    public void cleanup() {
        // Base implementation if needed
    }
}
