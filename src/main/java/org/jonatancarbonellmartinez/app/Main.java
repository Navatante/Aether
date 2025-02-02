package org.jonatancarbonellmartinez.app;

import javafx.application.Application;
import javafx.stage.Stage;
import org.jonatancarbonellmartinez.view.DatabaseView;
import org.jonatancarbonellmartinez.viewmodel.DatabaseViewModel;

import java.sql.Connection;

// TODO estoy en la rama de JavaFX
public class Main extends Application {
    private static final String DEFAULT_DB_PATH = "path/to/database.db";
    private Connection connection;

    @Override
    public void start(Stage primaryStage) {
        if (checkDatabaseConnection()) {
            launchMainApplication(primaryStage);
        } else {
            showDatabaseSelectionDialog(primaryStage);
        }
    }

    private boolean checkDatabaseConnection() {
        try {
            // Primero verificamos si el archivo existe
            File dbFile = new File(DEFAULT_DB_PATH);
            if (!dbFile.exists()) {
                return false;
            }

            // Intentamos establecer la conexión
            connection = DriverManager.getConnection("jdbc:sqlite:" + DEFAULT_DB_PATH);

            // Verificación adicional: intentamos hacer una consulta simple
            try (Statement stmt = connection.createStatement()) {
                stmt.executeQuery("SELECT 1");
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    private void showDatabaseSelectionDialog(Stage primaryStage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Conexión");
        alert.setHeaderText("No se pudo conectar a la base de datos");
        alert.setContentText("¿Desea seleccionar manualmente el archivo de la base de datos?");

        ButtonType selectButton = new ButtonType("Seleccionar archivo");
        ButtonType exitButton = new ButtonType("Salir", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(selectButton, exitButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == selectButton) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("SQLite Database", "*.db")
            );

            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                try {
                    connection = DriverManager.getConnection("jdbc:sqlite:" + selectedFile.getAbsolutePath());
                    // Guardamos la nueva ruta para futuros usos
                    saveNewDatabasePath(selectedFile.getAbsolutePath());
                    launchMainApplication(primaryStage);
                } catch (SQLException e) {
                    showFatalError("No se pudo conectar a la base de datos seleccionada.");
                    Platform.exit();
                }
            } else {
                Platform.exit();
            }
        } else {
            Platform.exit();
        }
    }

    private void saveNewDatabasePath(String newPath) {
        // Aquí implementarías la lógica para guardar la nueva ruta
        // Por ejemplo, en un archivo de configuración
        Properties props = new Properties();
        props.setProperty("database.path", newPath);
        try (FileOutputStream out = new FileOutputStream("config.properties")) {
            props.store(out, "Database Configuration");
        } catch (IOException e) {
            // Manejar el error de guardado de configuración
        }
    }

    private void launchMainApplication(Stage primaryStage) {
        try {
            // Aquí cargas tu ventana principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
            Parent root = loader.load();

            // Pasamos la conexión al controlador
            MainController controller = loader.getController();
            controller.setConnection(connection);

            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            showFatalError("Error al cargar la aplicación principal.");
            Platform.exit();
        }
    }

    private void showFatalError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Fatal");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void stop() {
        // Cerramos la conexión al cerrar la aplicación
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // Manejar el error de cierre de conexión
            }
        }
    }
}
