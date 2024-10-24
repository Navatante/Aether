package org.jonatancarbonellmartinez.utilities;

import javax.swing.*;
import java.sql.SQLException;

public class DatabasePresenter {
    private final DbFileChooserView view;
    private final Properties propertiesFile;
    private final Database databaseModel;

    public DatabasePresenter(DbFileChooserView view) {
        this.view = view;
        this.propertiesFile = Properties.getInstanceOfPropertiesFile();
        this.databaseModel = Database.getInstance();
        this.view.setPresenter(this); // Set the presenter in the view

        // Check the database path on startup
        checkDatabasePath();
    }

    /**
     * Checks if the database path is available and attempts to connect to the database.
     */
    public void checkDatabasePath() {
        String path = propertiesFile.readFromPropertiesFile("path");
        System.out.println("Database path from properties file: " + path);

        if (path == null || path.trim().isEmpty()) {
            view.showError("La ruta de la base de datos no está configurada. Seleccione una base de datos.");
            promptUserForDatabasePath(); // Open the file chooser dialog
        } else {
            attemptDatabaseConnection(path); // Connect with the stored path
        }
    }

    /**
     * Attempts to connect to the database and updates the view accordingly.
     */
    private void attemptDatabaseConnection(String path) {
        try {
            databaseModel.getConnection(); // The model handles the connection logic
            databaseModel.closeConnection(); // Close connection after testing.
        } catch (SQLException e) {
            handleDatabaseConnectionError(e);
        }
    }

    /**
     * Handles any database connection errors.
     */
    private void handleDatabaseConnectionError(SQLException e) {
        view.showError("Error connecting to the database: " + e.getMessage());
        promptUserForDatabasePath(); // Prompt for a new file if the connection fails
    }

    /**
     * Prompts the user to select a new database file.
     */
    private void promptUserForDatabasePath() {
        view.showFileChooser();  // Show the file chooser dialog to the user
    }

    public void onFileSelected(String filePath) {
        String fullPath = "jdbc:sqlite:" + filePath.replace("\\", "/");
        propertiesFile.writeIntoPropertiesFile("path", fullPath);  // Save the selected path

        attemptDatabaseConnection(fullPath); // Attempt to connect after selecting the file
    }

    public void onFileSelectionCanceled() {
        int choice = JOptionPane.showConfirmDialog(null,
                "No ha seleccionado ninguna base de datos. Desea intentarlo de nuevo?",
                "Selección de base de datos cancelada",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            promptUserForDatabasePath();
        } else {
            view.showError("Se requiere una conexión a la base de datos. Cerrando aplicación.");
            System.exit(1);  // Exit if the user declines
        }
    }
}
