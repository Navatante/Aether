package org.jonatancarbonellmartinez.presentation.viewmodel;


import org.jonatancarbonellmartinez.data.model.Entity;
import org.jonatancarbonellmartinez.presentation.view.fxml.xDialogView;

import javax.swing.*;

public interface xDialogPresenter {

    boolean isFormValid();
    void insertEntity();
    void editEntity();
    void getEntity(int entityId);
    void onSaveButtonClicked();
    Entity collectEntityData();
    void populateEntityDialog(Entity entity);
    void notifyObserver();

    /**
     * UTILITY STATIC MEMBERS ON INTERFACES ACT AS UTILITY FIELDS AND METHODS, THERE IS NO NEED FOR A UTILITY CLASS.
     */

    static boolean validateSimpleComboBox(JDialog parentView, JComboBox<String> comboBox, String fieldName) {
        if (comboBox.getSelectedIndex() == 0) {
            xDialogView.showError(parentView,"Seleccione un valor para " + fieldName + ".");
            return false;
        }
        return true;
    }

    static boolean validateDynamicComboBox(JDialog parentView, JComboBox<Entity> comboBox, String fieldName) {
        if (comboBox.getSelectedItem() == null) {
            xDialogView.showError(parentView,"Seleccione un valor para " + fieldName + ".");
            return false;
        }
        return true;
    }

    static boolean isFieldCompleted(JDialog parentView, JTextField field, String placeHolderName) {
        if (field.getText().equals(placeHolderName)) {
            xDialogView.showError(parentView,"Complete el campo " + placeHolderName);
            return false;
        }
        return true;
    }

    static String capitalizeWords(JTextField field) {
        String text = field.getText();
        String[] words = text.split("\\s+");
        StringBuilder capitalizedName = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                String firstLetter = word.substring(0, 1).toUpperCase();
                String restOfString = word.substring(1).toLowerCase();
                capitalizedName.append(firstLetter).append(restOfString).append(" ");
            }
        }
        return capitalizedName.toString().trim();
    }

    static String calculateDniLetter(JTextField personDniField) {
        // Tabla de letras correspondiente a cada resto
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int dniNumero = Integer.parseInt(personDniField.getText());
        // Obtener el resto de dividir el número del DNI entre 23
        int resto = dniNumero % 23;
        return personDniField.getText()+ letras.charAt(resto);
    }

    static void handleUnexpectedError(Exception e, JDialog view) {
        e.printStackTrace();
        xDialogView.showError(view,"Error inesperado: ");
    }
}