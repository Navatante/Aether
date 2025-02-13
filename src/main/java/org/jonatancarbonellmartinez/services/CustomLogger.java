package org.jonatancarbonellmartinez.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.*;

public class CustomLogger {
    private static final Logger LOGGER = Logger.getLogger(CustomLogger.class.getName());
    private static final String LOG_FOLDER = "logs";
    private static final String LOG_FILE = "app_errors.log";

    static {
        try {
            // Crear directorio de logs si no existe
            Path logDir = Paths.get(LOG_FOLDER);
            if (!Files.exists(logDir)) {
                Files.createDirectories(logDir);
            }

            // Configurar el manejador de archivos
            FileHandler fileHandler = new FileHandler(LOG_FOLDER + "/" + LOG_FILE, true);
            fileHandler.setFormatter(new CustomFormatter());

            // Configurar el logger
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.ALL);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logError(String message, Exception ex) {
        LOGGER.log(Level.SEVERE, message, ex);
    }

    public static void logWarning(String message) {
        LOGGER.log(Level.WARNING, message);
    }

    public static void logInfo(String message) {
        LOGGER.log(Level.INFO, message);
    }
}
