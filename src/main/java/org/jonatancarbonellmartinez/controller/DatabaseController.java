package org.jonatancarbonellmartinez.controller;

import org.jonatancarbonellmartinez.model.Database;
import org.jonatancarbonellmartinez.model.Properties;
import org.jonatancarbonellmartinez.view.DatabaseFileChooserView;

import java.sql.SQLException;

public class DatabaseController {
    private final DatabaseFileChooserView view;
    private final Properties propertiesFile;

    public DatabaseController(DatabaseFileChooserView view) {
        this.view = view;
        this.propertiesFile = Properties.getInstanceOfPropertiesFile();
    }

    public void checkDatabasePath() {
        try {
            String path = propertiesFile.readFromPropertiesFile("path");
            if (path == null || path.trim().isEmpty()) {
                promptUserForDatabasePath();  // Prompt user to select the database file
            } else {
                Database.getDatabaseInstance().connectToDatabase();
                Database.getDatabaseInstance().disconnectFromDatabase();
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
        String userPath = view.showFileChooser();

        if (userPath != null && !userPath.trim().isEmpty()) {
            String fullPath = "jdbc:sqlite:" + userPath.replace("\\", "/");
            propertiesFile.writeIntoPropertiesFile("path", fullPath);

            try {
                Database.getDatabaseInstance().connectToDatabase();
                view.showSuccess("Conexión establecida correctamente");
            } catch (SQLException e) {
                view.showError("Error al conectar a la base de datos después de seleccionar un nuevo archivo: " + e.getMessage());
                promptUserForDatabasePath(); // Retry file selection if connection fails again
            }
        } else {
            view.showError("No se proporcionó la ruta de la base de datos.");
            System.exit(1);
        }
    }
}
