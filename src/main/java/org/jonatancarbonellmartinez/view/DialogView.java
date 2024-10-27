package org.jonatancarbonellmartinez.view;

import javax.swing.*;
import java.awt.*;

public interface DialogView {

    void createEditModeComponents();

    void configureEditModeComponents();

    void clearFields();

    /**
     * UTILITY STATIC MEMBERS ON INTERFACES ACT AS UTILITY FIELDS AND METHODS, THERE IS NO NEED FOR A UTILITY CLASS.
     */

    Dimension FIELD_SIZE = new Dimension(180, 25);

    static void showMessage(JDialog parentView, String message) {
        JOptionPane.showMessageDialog(parentView, message);
    }

    static void showError(JDialog parentView, String message) {
        JOptionPane.showMessageDialog(parentView, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
