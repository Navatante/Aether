package org.jonatancarbonellmartinez.app;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Establecemos el look and feel (FlatDarkLaf) al iniciar
        try {
            FlatDarkLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Usamos SwingUtilities.invokeLater para asegurar que la GUI se ejecute en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // Inicializamos la aplicación a través del AppInitializer
            AppInitializer appInitializer = new AppInitializer();
            appInitializer.initialize(); // Llamamos al metodo para inicializar la aplicación
        });
    }
}
