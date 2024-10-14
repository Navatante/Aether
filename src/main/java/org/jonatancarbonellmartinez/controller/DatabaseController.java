package org.jonatancarbonellmartinez.controller;

import org.jonatancarbonellmartinez.model.Database;
import org.jonatancarbonellmartinez.model.Properties;
import org.jonatancarbonellmartinez.view.DatabaseFileChooserView;
import org.jonatancarbonellmartinez.view.FileSelectionListener;

import java.sql.SQLException;

public class DatabaseController implements FileSelectionListener {
    private final DatabaseFileChooserView view;
    private final Properties propertiesFile;

    public DatabaseController(DatabaseFileChooserView view) {
        this.view = view;
        this.propertiesFile = Properties.getInstanceOfPropertiesFile();
        this.view.addFileSelectionListener(this); // Register as an observer
    }

    public void checkDatabasePath() {
        try {
            String path = propertiesFile.readFromPropertiesFile("path");
            if (path == null || path.trim().isEmpty()) {
                promptUserForDatabasePath();  // Prompt user to select the database file
            } else {
                Database.getInstance().getConnection();
                Database.getInstance().disconnect();
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("El archivo de base de datos no existe")) {
                // Handle the case when the database file doesn't exist
                view.showError("El archivo de base de datos no existe. Seleccione un archivo de base de datos válido.");
                promptUserForDatabasePath(); // Show FileChooser dialog again if the file doesn't exist
            } else {
                // Handle other SQL errors
                view.showError("Error al conectar a la base de datos: " + e.getMessage());
                promptUserForDatabasePath(); // Retry with a new file selection
            }
        } catch (Exception e) {
            view.showError("Ocurrió un error inesperado: " + e.getMessage());
        }
    }

    private void promptUserForDatabasePath() {
        view.showFileChooser(); // Show the file chooser dialog
    }

    @Override
    public void onFileSelected(String filePath) {
        String fullPath = "jdbc:sqlite:" + filePath.replace("\\", "/");
        propertiesFile.writeIntoPropertiesFile("path", fullPath);

        try {
            Database.getInstance().getConnection();
            view.showSuccess("Conexión establecida correctamente");
        } catch (SQLException e) {
            view.showError("Error al conectar a la base de datos después de seleccionar un nuevo archivo: " + e.getMessage());
            promptUserForDatabasePath(); // Retry file selection if connection fails again
        }
    }

    @Override
    public void onFileSelectionCanceled() {
        view.showError("No se proporcionó la ruta de la base de datos.");
        System.exit(1);
    }
}
