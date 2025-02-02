package org.jonatancarbonellmartinez.view;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jonatancarbonellmartinez.viewmodel.DatabaseViewModel;

import java.io.File;

public class DatabaseView {
    private final DatabaseViewModel viewModel;
    private final Stage stage;

    public DatabaseView(DatabaseViewModel viewModel, Stage stage) {
        this.viewModel = viewModel;
        this.stage = stage;

        // Bind to view model properties
        viewModel.getErrorMessageProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                showDatabaseFileChooser();
            }
        });
    }

    private void showDatabaseFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Database File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Database Files", "*.db")
        );

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            viewModel.setDatabaseFile(selectedFile.getAbsolutePath());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Database Selected");
            alert.setContentText("Would you like to try again?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    showDatabaseFileChooser();
                } else {
                    System.exit(1);
                }
            });
        }
    }

    public void show() {
        // Show initial loading view if needed
        VBox loadingBox = new VBox();
        // Add loading indicators or messages

        Scene scene = new Scene(loadingBox);
        stage.setScene(scene);
        stage.show();
    }
}
