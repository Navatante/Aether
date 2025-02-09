package org.jonatancarbonellmartinez.presentation.view.controller;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

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

    private Rectangle2D previousBounds;
    private boolean wasMaximized = false;

    @FXML
    private BorderPane root;
    @FXML
    private BorderPane contentArea;
    @FXML
    private BorderPane topBar;
    @FXML
    private Button refreshButton;
    @FXML
    private ImageView refreshIcon;
    @FXML
    private ToggleGroup leftPanelToggleButtonsGroup;

    @Inject
    public MainViewController(PersonViewController personViewController) {
        this.personViewController = personViewController;
    }

    @FXML
    public void initialize() {
        loadView("PersonView"); // Load PersonView by default

        // Add keyboard shortcut for refreshButton
        root.setOnKeyPressed(event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.R) {
                handleRefreshButtonClicked(null);
            }
        });

        // Make sure the root node can receive key events
        root.setFocusTraversable(true);

        // Asegurarse de que siempre haya un botón seleccionado
        leftPanelToggleButtonsGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle == null) {
                leftPanelToggleButtonsGroup.selectToggle(oldToggle);
            }
        });
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

            // Le pongo al stage el tamano minimo en altura y anchura del root, asi no se puede redimensionar por completo.
            stage.setMinWidth(root.getMinWidth());
            stage.setMinHeight(root.getMinHeight());

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
        final int SNAP_DISTANCE = 20;
        double cursorScreenX = event.getScreenX();
        double cursorScreenY = event.getScreenY();
        Screen currentScreen = getCurrentScreen(cursorScreenX, cursorScreenY);
        Rectangle2D screenBounds = currentScreen.getVisualBounds();

        if (resizing) return; // No permitir arrastre si se está redimensionando



        if (stage.isMaximized()) {


            // Solo restaurar si el usuario realmente está arrastrando
            if (Math.abs(cursorScreenX - xOffset) > 5 || Math.abs(cursorScreenY - yOffset) > 5) {

                stage.setMaximized(false);

                stage.setX(cursorScreenX);
                stage.setY(cursorScreenY);

                // Actualizamos xOffset e yOffset con la posición relativa en la nueva ventana
                double actualWidth = stage.getWidth();
                xOffset = cursorScreenX - stage.getX()+(actualWidth/2);
                yOffset = cursorScreenY - stage.getY()+50;
            }
        }

        // TODO new code

        if (previousBounds == null && !stage.isMaximized()) {
            storePreviousState(stage);
        }

        // Left edge snap
        if (cursorScreenX <= screenBounds.getMinX() + SNAP_DISTANCE) {
            snapToLeft(stage, screenBounds);
            return;
        }

        // Right edge snap
        if (cursorScreenX >= screenBounds.getMaxX() - SNAP_DISTANCE) {
            snapToRight(stage, screenBounds);
            return;
        }

        // Restore if dragging away from snapped position
        if (isDragging && previousBounds != null) {
            restorePreviousState(stage);
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

    @FXML
    private void handleRefreshButtonClicked(MouseEvent event) {
        // Create a 360-degree rotation animation
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(750), refreshIcon);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(1);
        rotateTransition.setInterpolator(Interpolator.EASE_BOTH);

        // Add animation finished handler
        rotateTransition.setOnFinished(e -> {
            // Reset the rotation to 0 after animation
            refreshIcon.setRotate(0);

            // Refresh your content here
            refreshContent();
        });

        // Start the animation
        rotateTransition.play();
    }

    private void refreshContent() {
        // TODO
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

    private Screen getCurrentScreen(double x, double y) {
        List<Screen> screens = Screen.getScreensForRectangle(x, y, 1, 1);
        return screens.isEmpty() ? Screen.getPrimary() : screens.get(0);
    }

    private void storePreviousState(Stage stage) {
        previousBounds = new Rectangle2D(
                stage.getX(), stage.getY(),
                stage.getWidth(), stage.getHeight()
        );
        wasMaximized = stage.isMaximized();
    }

    private void restorePreviousState(Stage stage) {
        if (previousBounds != null) {
            stage.setMaximized(false);
            stage.setX(previousBounds.getMinX());
            stage.setY(previousBounds.getMinY());
            stage.setWidth(previousBounds.getWidth());
            stage.setHeight(previousBounds.getHeight());
            previousBounds = null;
            wasMaximized = false;
        }
    }

    private void snapToLeft(Stage stage, Rectangle2D screenBounds) {
        stage.setMaximized(false);
        stage.setX(screenBounds.getMinX());
        stage.setY(screenBounds.getMinY());
        stage.setWidth(screenBounds.getWidth() / 2);
        stage.setHeight(screenBounds.getHeight());
    }

    private void snapToRight(Stage stage, Rectangle2D screenBounds) {
        stage.setMaximized(false);
        stage.setX(screenBounds.getMinX() + screenBounds.getWidth() / 2);
        stage.setY(screenBounds.getMinY());
        stage.setWidth(screenBounds.getWidth() / 2);
        stage.setHeight(screenBounds.getHeight());
    }
}
