package org.jonatancarbonellmartinez.controller;

import org.jonatancarbonellmartinez.model.Database;
import org.jonatancarbonellmartinez.model.Properties;
import org.jonatancarbonellmartinez.view.DatabaseFileChooserView;
import org.jonatancarbonellmartinez.view.FileSelectionListener;

import javax.swing.*;
import java.sql.SQLException;

public class DatabaseController implements FileSelectionListener {
    private final DatabaseFileChooserView view;
    private final Properties propertiesFile;

    public DatabaseController(DatabaseFileChooserView view) {
        this.view = view;
        this.propertiesFile = Properties.getInstanceOfPropertiesFile();
        this.view.addFileSelectionListener(this); // Register as an observer
        checkDatabasePath();  // Automatically check the database path on startup
    }

    public void checkDatabasePath() {
        try {
            String path = propertiesFile.readFromPropertiesFile("path");

            // Log and check if path is valid
            System.out.println("Database path from properties file: " + path);

            if (path == null || path.trim().isEmpty()) {
                // If path is missing or empty, prompt user for it
                view.showError("La ruta de la base de datos no está configurada. Seleccione una base de datos.");
                promptUserForDatabasePath(); // This will open the FileChooser dialog
            } else {
                connectToDatabase(path);
            }
        } catch (SQLException e) {
            handleDatabaseConnectionError(e);
        } catch (Exception e) {
            view.showError("Unexpected error: " + e.getMessage());
        }
    }



    private void connectToDatabase(String path) throws SQLException {
        Database db = Database.getInstance();
        db.getConnection();
        view.showSuccess("Successfully connected to the database.");
    }

    private void handleDatabaseConnectionError(SQLException e) {
        if (e.getMessage().contains("El archivo de base de datos no existe")) {
            // Handle case when the database file doesn't exist
            view.showError("The database file does not exist. Please select a valid database file.");
            promptUserForDatabasePath();
        } else {
            // Handle other SQL errors
            view.showError("Error connecting to the database: " + e.getMessage());
            promptUserForDatabasePath();
        }
    }

    private void promptUserForDatabasePath() {
        view.showFileChooser();  // Show the file chooser dialog to the user
    }

    @Override
    public void onFileSelected(String filePath) {
        String fullPath = "jdbc:sqlite:" + filePath.replace("\\", "/");
        propertiesFile.writeIntoPropertiesFile("path", fullPath);

        try {
            connectToDatabase(fullPath);
        } catch (SQLException e) {
            view.showError("Error connecting to the database after selecting a new file: " + e.getMessage());
            promptUserForDatabasePath();
        }
    }

    @Override
    public void onFileSelectionCanceled() {
        int choice = JOptionPane.showConfirmDialog(null,
                "No database file was selected. Do you want to try again?",
                "File Selection Canceled",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            promptUserForDatabasePath();
        } else {
            view.showError("Database connection is required. Exiting the application.");
            System.exit(1);  // Exit if the user declines
        }
    }
}
