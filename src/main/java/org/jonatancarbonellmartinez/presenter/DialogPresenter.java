package org.jonatancarbonellmartinez.presenter;


import org.jonatancarbonellmartinez.model.entities.Entity;
import org.jonatancarbonellmartinez.view.DialogView;

import javax.swing.*;

public interface DialogPresenter {

    boolean isFormValid();
    void addEntity();
    void editEntity();
    void getEntity(int entityId);
    void onSaveButtonClicked();
    Entity collectEntityData();
    void notifyObserver();

    /**
     * UTILITY STATIC MEMBERS ON INTERFACES ACT AS UTILITY FIELDS AND METHODS, THERE IS NO NEED FOR A UTILITY CLASS.
     */

    static boolean validateComboBox(JDialog parentView, JComboBox<String> comboBox, String fieldName) {
        if (comboBox.getSelectedIndex() == 0) {
            DialogView.showError(parentView,"Por favor, selecciona un valor para " + fieldName);
            return false;
        }
        return true;
    }

    static boolean validateField(JDialog parentView, JTextField field, String placeHolderName) {
        if (field.getText().isEmpty() || field.getText().equals(placeHolderName)) {
            DialogView.showError(parentView,"Por favor, completa el campo " + placeHolderName);
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

    static boolean containsOnlyLetters(JDialog parentView, JTextField field, String fieldName) {
        if(!field.getText().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*$")) {
            DialogView.showError(parentView,"El campo " + fieldName + " solo acepta letras.");
            return false;
        }
        return  true;
    }

    static boolean containsOnlyNumbers(JDialog parentView, JTextField field, String fieldName) {
        if(!field.getText().matches("^[0-9]*$")) {
            DialogView.showError(parentView, "El campo " + fieldName + " solo acepta números.");
        }
        return true;
    }

    static String calculateDniLetter(JTextField personDniField) {
        // Tabla de letras correspondiente a cada resto
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int dniNumero = Integer.parseInt(personDniField.getText());
        // Obtener el resto de dividir el número del DNI entre 23
        int resto = dniNumero % 23;
        return personDniField.getText()+ letras.charAt(resto);
    }
}
