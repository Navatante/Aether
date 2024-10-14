package org.jonatancarbonellmartinez.view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DatabaseFileChooserView {

    public String showFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccione la base de datos SQLite");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        FileNameExtensionFilter filterToOnlyDbFiles = new FileNameExtensionFilter("Archivos de base de datos (*.db)", "db");
        fileChooser.setFileFilter(filterToOnlyDbFiles);

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

    public void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }

    public void showError(String message) {
        showMessage(message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccess(String message) {
        showMessage(message, "Estado", JOptionPane.INFORMATION_MESSAGE);
    }


}
