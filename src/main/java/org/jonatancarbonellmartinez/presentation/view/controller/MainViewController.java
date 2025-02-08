package org.jonatancarbonellmartinez.presentation.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
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
    @FXML
    private MenuItem personsMenuItem;

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
