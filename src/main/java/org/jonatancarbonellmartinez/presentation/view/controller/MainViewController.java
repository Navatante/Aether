package org.jonatancarbonellmartinez.presentation.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class MainViewController {
    @FXML
    private BorderPane contentArea;
    @FXML
    private MenuItem personsMenuItem;

    private final Map<String, Node> cachedViews = new HashMap<>();

    @Inject
    public MainViewController() {
    }

    @FXML
    public void initialize() {
        personsMenuItem.setOnAction(event -> loadView("PersonView"));
    }

    public void loadView(String viewName) {
        try {
            Node view = cachedViews.get(viewName);

            if (view == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + viewName + ".fxml"));
                view = loader.load();
                cachedViews.put(viewName, view);
            }

            contentArea.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BorderPane getContentArea() {
        return contentArea;
    }
}
