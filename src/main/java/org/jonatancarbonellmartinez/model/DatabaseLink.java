package org.jonatancarbonellmartinez.model;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.sql.*;

public class DatabaseLink {
    private static DatabaseLink databaseInstance; // Singleton
    private String pathToDatabase;
    private Connection connection;
    private final PropertiesFile propertiesFile = PropertiesFile.getInstanceOfPropertiesFile();

    private DatabaseLink() {}

    private JFileChooser createFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccione la base de datos SQLite");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Use FileNameExtensionFilter to allow only .db files
        FileNameExtensionFilter dbFilter = new FileNameExtensionFilter("Archivos de base de datos (*.db)", "db");
        fileChooser.setFileFilter(dbFilter);

        return fileChooser;
    }

    private String getPathFromFileChooser(JFileChooser fileChooser) {
        String pathtoSelectedFile = "";
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            // Get the selected file path
            pathtoSelectedFile = fileChooser.getSelectedFile().getAbsolutePath();
        }
        return pathtoSelectedFile;
    }

    public boolean pathToDatabaseCheck() { // pending.
        return true;
    }
    public boolean propertiesFileCheck() { // you have to improve this. this name is already used in Properties file class. te has liado un huevo siguiendo a chatgpt. con tranquilidad dale logica a esto.
        try {
            String path = propertiesFile.read("path");
            if (path == null || path.trim().isEmpty()) {
                String pathUserInput = getPathFromFileChooser(createFileChooser());

                // Check if the path is valid
                if (pathUserInput != null && !pathUserInput.trim().isEmpty()) {
                    // Write the selected file path to properties and replace backslashes with slashes
                    propertiesFile.write("path", "jdbc:sqlite:" + pathUserInput.replace("\\", "/"));
                    setPathToDatabaseFromPropertiesFile();  // It reads from the properties files and assigns the path to the pathToDatabase variable
                    connectToDatabase();
                    disconnectFromDatabase();

                    // Show a success dialog
                    JOptionPane.showMessageDialog(null, "Conexión establecida correctamente", "Estado", JOptionPane.INFORMATION_MESSAGE);
                    return true;
                } else {
                    System.err.println("No se proporcionó la ruta de la base de datos.");
                    return false;
                }
            } else {
                // If path is already present in properties, use it
                setPathToDatabaseFromPropertiesFile();
                connectToDatabase();
                disconnectFromDatabase();
                return true;
            }
        } catch (SQLException e) {
            // Handle SQL exception if necessary
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (Exception e) {
            // Handle any other exception that may occur
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocurrió un error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Public method to provide access to the single databaseInstance
    public static synchronized DatabaseLink getDatabaseInstance() throws SQLException {
        if (databaseInstance == null) {
            databaseInstance = new DatabaseLink();
        }
        return databaseInstance;
    }

    public void setPathToDatabaseFromPropertiesFile() {
        this.pathToDatabase = propertiesFile.read("path");
    }

    public Connection connectToDatabase() throws SQLException {
        if (this.connection == null || this.connection.isClosed()) {
            this.connection = DriverManager.getConnection(this.pathToDatabase);
            System.out.println("Connected to database");
        }
        return this.connection;
    }

    public void disconnectFromDatabase() throws SQLException {
        if (this.connection != null && !this.connection.isClosed()) {
            this.connection.close();
            System.out.println("Disconnected from database");
        }
    }
}
