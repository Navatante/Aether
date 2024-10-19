package org.jonatancarbonellmartinez.app;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        try {
            FlatDarkLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Use SwingUtilities.invokeLater to make sure GUI runs inside Event Dispatch Thread.
        SwingUtilities.invokeLater(() -> {
            AppInitializer appInitializer = new AppInitializer();
            appInitializer.initialize();
        });
    }
}
