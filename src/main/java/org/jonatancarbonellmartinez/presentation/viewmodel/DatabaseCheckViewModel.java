package org.jonatancarbonellmartinez.presentation.viewmodel;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import org.jonatancarbonellmartinez.data.database.configuration.DatabaseConnection;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@Singleton
public class DatabaseCheckViewModel {
    private final DatabaseConnection databaseConnection;
    private final BooleanProperty connectionSuccess;

    @Inject
    public DatabaseCheckViewModel(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
        this.connectionSuccess = new SimpleBooleanProperty(false);
    }

    public void checkAndInitializeDatabase() {
        if (!databaseConnection.isDatabaseFilePresent()) {
            showFileChooser();
        } else {
            testConnection();
        }
    }

    private void showFileChooser() {
        FileChooser fileChooser = configureFileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            handleFileSelection(selectedFile);
        } else {
            showConfirmationDialog();
        }
    }

    private FileChooser configureFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccione la base de datos SQLite");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos de base de datos (*.db)", "*.db")
        );
        return fileChooser;
    }

    private void handleFileSelection(File selectedFile) {
        try {
            String path = selectedFile.getAbsolutePath();
            databaseConnection.setDatabasePath("jdbc:sqlite:" + path.replace("\\", "/"));
            testConnection();
        } catch (IOException e) {
            showError("Error al guardar la configuración: " + e.getMessage());
            showFileChooser();
        }
    }

    private void testConnection() {
        Connection connection = null;
        try {
            connection = databaseConnection.getConnection();
            connectionSuccess.set(true);
        } catch (SQLException e) {
            showError("Error de conexión: " + e.getMessage());
            showFileChooser();
        } finally {
            databaseConnection.close(connection);
        }
    }

    private void showConfirmationDialog() {
        Alert alert = createAlert(
                Alert.AlertType.CONFIRMATION,
                "Confirmación",
                "No ha seleccionado ninguna base de datos. ¿Desea intentarlo de nuevo?"
        );

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            showFileChooser();
        } else {
            showError("Se requiere una conexión a la base de datos. Cerrando aplicación.");
            Platform.exit();
        }
    }

    private void showError(String message) {
        createAlert(Alert.AlertType.ERROR, "Error", message).showAndWait();
    }

    private Alert createAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert;
    }

    public BooleanProperty connectionSuccessProperty() {
        return connectionSuccess;
    }
}