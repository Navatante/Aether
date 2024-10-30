package org.jonatancarbonellmartinez.view;

import javax.swing.*;
import java.awt.*;

public interface PanelView {

    void updatePanel();

    /**
     * UTILITY STATIC MEMBERS ON INTERFACES ACT AS UTILITY FIELDS AND METHODS, THERE IS NO NEED FOR A UTILITY CLASS.
     */

    public static final Font ENTITY_TITLE_LABEL_FONT = new Font("Segoe UI", Font.BOLD, 15);

    static void showMessage(JPanel parentView, String message) {
        JOptionPane.showMessageDialog(parentView, message);
    }

    static void showError(JPanel parentView, String message) {
        JOptionPane.showMessageDialog(parentView, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
