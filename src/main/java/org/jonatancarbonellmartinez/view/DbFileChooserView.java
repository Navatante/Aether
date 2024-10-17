package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.presenter.DatabasePresenter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DbFileChooserView {

    private DatabasePresenter presenter; // Reference to the presenter

    public void setPresenter(DatabasePresenter presenter) {
        this.presenter = presenter; // Allow the presenter to be set
    }

    public void showFileChooser() {
        JFileChooser fileChooser = createFileChooser();
        int result = fileChooser.showOpenDialog(null);
        handleFileChooserResult(result, fileChooser);
    }

    private JFileChooser createFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccione la base de datos SQLite");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        FileNameExtensionFilter filterToOnlyDbFiles = new FileNameExtensionFilter("Archivos de base de datos (*.db)", "db");
        fileChooser.setFileFilter(filterToOnlyDbFiles);

        return fileChooser;
    }

    private void handleFileChooserResult(int result, JFileChooser fileChooser) {
        if (result == JFileChooser.APPROVE_OPTION) {
            String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
            // Notify the presenter directly
            if (presenter != null) {
                presenter.onFileSelected(selectedFilePath);
            }
        } else {
            // Notify the presenter about cancellation
            if (presenter != null) {
                presenter.onFileSelectionCanceled();
            }
        }
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
