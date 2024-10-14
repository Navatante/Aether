package org.jonatancarbonellmartinez.view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.ArrayList;
import java.util.List;

public class DatabaseFileChooserView {
    private final List<FileSelectionListener> listeners = new ArrayList<>();

    public void addFileSelectionListener(FileSelectionListener listener) {
        listeners.add(listener);
    }

    public void removeFileSelectionListener(FileSelectionListener listener) {
        listeners.remove(listener);
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
            notifyFileSelected(selectedFilePath);
        } else {
            notifyFileSelectionCanceled();
        }
    }

    private void notifyFileSelected(String filePath) {
        for (FileSelectionListener listener : listeners) {
            listener.onFileSelected(filePath);
        }
    }

    private void notifyFileSelectionCanceled() {
        for (FileSelectionListener listener : listeners) {
            listener.onFileSelectionCanceled();
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
