package org.jonatancarbonellmartinez.viewmodel;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import org.jonatancarbonellmartinez.model.utilities.Database;

import java.io.File;
import java.sql.SQLException;
import java.util.Optional;

public class DatabaseViewModel {
    private final Database model;
    private final BooleanProperty connectionSuccess = new SimpleBooleanProperty(false);

    public DatabaseViewModel() {
        this.model = Database.getInstance();
    }

    public void checkAndInitializeDatabase() {
        if (!model.isDatabaseFilePresent()) {
            showFileChooser();
        } else {
            testConnection();
        }
    }

    private void showFileChooser() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Seleccione la base de datos SQLite");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos de base de datos (*.db)", "*.db")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            handleFileSelection(selectedFile.getAbsolutePath());
        } else {
            showConfirmationDialog();
        }
    }

    private void handleFileSelection(String filePath) {
        String fullPath = "jdbc:sqlite:" + filePath.replace("\\", "/");
        model.setDatabasePath(fullPath);
        testConnection();
    }

    private void testConnection() {
        try {
            model.getConnection();
            model.closeConnection();
            connectionSuccess.set(true);
        } catch (SQLException e) {
            showError("Error de conexión: " + e.getMessage());
            showFileChooser();
        }
    }

    private void showConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        // TODO metele una hoja de estilos tema oscuro.
        //alert.getDialogPane().getStylesheets().add("/path/to/your/stylesheet.css");
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText("No ha seleccionado ninguna base de datos. ¿Desea intentarlo de nuevo?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            showFileChooser();
        } else {
            showError("Se requiere una conexión a la base de datos. Cerrando aplicación.");
            Platform.exit();
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        // TODO metele una hoja de estilos tema oscuro.
        //alert.getDialogPane().getStylesheets().add("/path/to/your/stylesheet.css");
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public BooleanProperty connectionSuccessProperty() {
        return connectionSuccess;
    }
}
