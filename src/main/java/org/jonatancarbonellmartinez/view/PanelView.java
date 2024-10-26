package org.jonatancarbonellmartinez.view;

import javax.swing.*;

public interface PanelView {

    void refreshPanel();

    /**
     * UTILITY STATIC MEMBERS ON INTERFACES ACT AS UTILITY FIELDS AND METHODS, THERE IS NO NEED FOR A UTILITY CLASS.
     */

    static void showMessage(JPanel parentView, String message) {
        JOptionPane.showMessageDialog(parentView, message);
    }

    static void showError(JPanel parentView, String message) {
        JOptionPane.showMessageDialog(parentView, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
