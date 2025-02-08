package org.jonatancarbonellmartinez.presentation.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class MainViewController {
    @FXML
    private BorderPane contentArea;
//    @FXML
//    private MenuItem personsMenuItem;

    @FXML
    private BorderPane topBar;
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private void handleTopBarPressed(MouseEvent event) {
        Stage stage = (Stage) topBar.getScene().getWindow();
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    private void handleTopBarDragged(MouseEvent event) {
        Stage stage = (Stage) topBar.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
        stage.setMaximized(false); // TODO tengo que arreglar que el cursor se queda fuera de la ventana.
    }

    @FXML
    private void handleWindowClosed(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleWindowMaximized(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (stage.isMaximized()) {
            stage.setMaximized(false);
        } else {
            stage.setMaximized(true);
        }
    }

    @FXML
    private void handleWindowMinimized(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    private final Map<String, Node> cachedViews = new HashMap<>();
    private final PersonViewController personViewController;

    @Inject
    public MainViewController(PersonViewController personViewController) {
        this.personViewController = personViewController;
    }

    @FXML
    public void initialize() {
        // personsMenuItem.setOnAction(event -> loadView("PersonView"));
        // Load PersonView by default
        loadView("PersonView");

    }

    public void loadView(String viewName) {
        try {
            Node view = cachedViews.get(viewName);

            if (view == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + viewName + ".fxml"));

                // Set the correct controller based on view
                if ("PersonView".equals(viewName)) {
                    loader.setController(personViewController);
                }

                view = loader.load();
                cachedViews.put(viewName, view);
            }

            contentArea.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
