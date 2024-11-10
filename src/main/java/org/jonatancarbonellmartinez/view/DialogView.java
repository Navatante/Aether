package org.jonatancarbonellmartinez.view;

import javax.swing.*;
import java.awt.*;

public interface DialogView { // TODO hacer esto mejor, porque hay algunos que no utilizo en algunas views. quiza crear una interfaz distinta por tipo de Dialog, ejemplo: RegisterDialogView, AddDialogView, EditDialogView.

    void createEditModeComponents();

    void configureEditModeComponents();

    void clearFields();

    void onEditEntityIdFieldAction();

    void setDocumentFilters();

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
