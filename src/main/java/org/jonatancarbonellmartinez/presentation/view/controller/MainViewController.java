package org.jonatancarbonellmartinez.presentation.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static javafx.scene.Cursor.*;

@Singleton
public class MainViewController {
    private static final int RESIZE_PADDING = 5;
    private double xOffset = 0;
    private double yOffset = 0;
    private boolean resizing = false;
    private boolean isDragging = false;
    private double initX = 0;
    private double initY = 0;
    private double initStageX = 0;
    private double initStageY = 0;
    private double initWidth = 0;
    private double initHeight = 0;
    private final Map<String, Node> cachedViews = new HashMap<>();
    private final PersonViewController personViewController;
    @FXML
    private BorderPane root;
    @FXML
    private BorderPane contentArea;
    @FXML
    private BorderPane topBar;

    @Inject
    public MainViewController(PersonViewController personViewController) {
        this.personViewController = personViewController;
    }

    @FXML
    public void initialize() {
        loadView("PersonView"); // Load PersonView by default
    }

    @FXML
    private void handleRootMouseMoved(MouseEvent event) {
        if (!resizing) {
            Cursor cursor = getCursor(event);
            root.setCursor(cursor);
        }
    }

    @FXML
    private void handleRootMousePressed(MouseEvent event) {
        initX = event.getScreenX();
        initY = event.getScreenY();

        Stage stage = (Stage) root.getScene().getWindow();
        initStageX = stage.getX();
        initStageY = stage.getY();
        initWidth = root.getWidth();
        initHeight = root.getHeight();

        Cursor cursor = getCursor(event);
        if (cursor != Cursor.DEFAULT) {
            resizing = true;
            event.consume();
        }
    }

    @FXML
    private void handleRootMouseDragged(MouseEvent event) {

        if (resizing) {
            Stage stage = (Stage) root.getScene().getWindow();
            Cursor cursor = root.getCursor();

            double deltaX = event.getScreenX() - initX;
            double deltaY = event.getScreenY() - initY;

            if (cursor == Cursor.SE_RESIZE) {
                stage.setWidth(Math.max(initWidth + deltaX, stage.getMinWidth()));
                stage.setHeight(Math.max(initHeight + deltaY, stage.getMinHeight()));
            } else if (cursor == Cursor.SW_RESIZE) {
                stage.setX(Math.min(initStageX + deltaX, initStageX + initWidth - stage.getMinWidth()));
                stage.setWidth(Math.max(initWidth - deltaX, stage.getMinWidth()));
                stage.setHeight(Math.max(initHeight + deltaY, stage.getMinHeight()));
            } else if (cursor == Cursor.NW_RESIZE) {
                stage.setX(Math.min(initStageX + deltaX, initStageX + initWidth - stage.getMinWidth()));
                stage.setY(Math.min(initStageY + deltaY, initStageY + initHeight - stage.getMinHeight()));
                stage.setWidth(Math.max(initWidth - deltaX, stage.getMinWidth()));
                stage.setHeight(Math.max(initHeight - deltaY, stage.getMinHeight()));
            } else if (cursor == Cursor.NE_RESIZE) {
                stage.setY(Math.min(initStageY + deltaY, initStageY + initHeight - stage.getMinHeight()));
                stage.setWidth(Math.max(initWidth + deltaX, stage.getMinWidth()));
                stage.setHeight(Math.max(initHeight - deltaY, stage.getMinHeight()));
            } else if (cursor == Cursor.E_RESIZE) {
                stage.setWidth(Math.max(initWidth + deltaX, stage.getMinWidth()));
            } else if (cursor == Cursor.W_RESIZE) {
                stage.setX(Math.min(initStageX + deltaX, initStageX + initWidth - stage.getMinWidth()));
                stage.setWidth(Math.max(initWidth - deltaX, stage.getMinWidth()));
            } else if (cursor == Cursor.N_RESIZE) {
                stage.setY(Math.min(initStageY + deltaY, initStageY + initHeight - stage.getMinHeight()));
                stage.setHeight(Math.max(initHeight - deltaY, stage.getMinHeight()));
            } else if (cursor == Cursor.S_RESIZE) {
                stage.setHeight(Math.max(initHeight + deltaY, stage.getMinHeight()));
            }
            event.consume();
        }
    }

    @FXML
    private void handleRootMouseReleased(MouseEvent event) {
        resizing = false;
        root.setCursor(getCursor(event));
    }

    @FXML
    private void handleTopBarClicked(MouseEvent event) {
        Stage stage = (Stage) topBar.getScene().getWindow();
        if (event.getClickCount() == 2) {
            toggleMaximize(stage);
        }
    }

    @FXML
    private void handleTopBarPressed(MouseEvent event) {
        // Guardar la posición inicial del cursor
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();

        // Marcar que el usuario aún no ha empezado a arrastrar
        isDragging = false;
    }

    @FXML
    private void handleTopBarReleased(MouseEvent event) {
        isDragging = false;
    }

    @FXML
    private void handleTopBarDragged(MouseEvent event) {
        Stage stage = (Stage) topBar.getScene().getWindow();

        if (resizing) return; // No permitir arrastre si se está redimensionando

        if (stage.isMaximized()) {
            double cursorScreenX = event.getScreenX();
            double cursorScreenY = event.getScreenY();

            // Solo restaurar si el usuario realmente está arrastrando
            if (Math.abs(cursorScreenX - xOffset) > 5 || Math.abs(cursorScreenY - yOffset) > 5) {

                double proportionX = event.getSceneX() / stage.getWidth();
                double proportionY = event.getSceneY() / stage.getHeight();

                double normalWidth = stage.getMinWidth() * 1.5;
                double normalHeight = stage.getMinHeight() * 1.5;

                stage.setMaximized(false);
                stage.setWidth(normalWidth);
                stage.setHeight(normalHeight);

                // Calcular la nueva posición de la ventana manteniendo la posición relativa del cursor
                double newX = cursorScreenX - (normalWidth * proportionX);
                double newY = cursorScreenY - (normalHeight * proportionY);

                stage.setX(newX);
                stage.setY(newY);

                // **Corrección clave**: Actualizamos xOffset e yOffset con la posición relativa en la nueva ventana
                xOffset = cursorScreenX - stage.getX();
                yOffset = cursorScreenY - stage.getY();
            }
        }

        // Mover la ventana con el nuevo offset
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
        isDragging = true;
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

    private Cursor getCursor(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        double width = root.getWidth();
        double height = root.getHeight();

        if (y < RESIZE_PADDING && x < RESIZE_PADDING) return Cursor.NW_RESIZE;
        if (y < RESIZE_PADDING && x > width - RESIZE_PADDING) return Cursor.NE_RESIZE;
        if (y > height - RESIZE_PADDING && x < RESIZE_PADDING) return Cursor.SW_RESIZE;
        if (y > height - RESIZE_PADDING && x > width - RESIZE_PADDING) return Cursor.SE_RESIZE;
        if (x < RESIZE_PADDING) return Cursor.W_RESIZE;
        if (x > width - RESIZE_PADDING) return Cursor.E_RESIZE;
        if (y < RESIZE_PADDING) return Cursor.N_RESIZE;
        if (y > height - RESIZE_PADDING) return Cursor.S_RESIZE;

        return Cursor.DEFAULT;
    }

    private void toggleMaximize(Stage stage) {
        if (stage.isMaximized()) {
            stage.setMaximized(false);
        } else {
            stage.setMaximized(true);
        }
    }
}
