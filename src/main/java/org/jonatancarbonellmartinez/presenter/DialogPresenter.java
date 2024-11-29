package org.jonatancarbonellmartinez.presenter;


import org.jonatancarbonellmartinez.model.entities.Entity;
import org.jonatancarbonellmartinez.view.DialogView;

import javax.swing.*;

public interface DialogPresenter {

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
            DialogView.showError(parentView,"Seleccione un valor para " + fieldName + ".");
            return false;
        }
        return true;
    }

    static boolean validateDynamicComboBox(JDialog parentView, JComboBox<Entity> comboBox, String fieldName) {
        if (comboBox.getSelectedItem() == null) {
            DialogView.showError(parentView,"Seleccione un valor para " + fieldName + ".");
            return false;
        }
        return true;
    }

    static boolean validateField(JDialog parentView, JTextField field, String placeHolderName) {
        if (field.getText().isEmpty() || field.getText().equals(placeHolderName)) {
            DialogView.showError(parentView,"Complete el campo " + placeHolderName);
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

    static boolean isAValidMandatoryHour(JDialog parentView, JTextField field, String fieldName) {
        if (field.getText().matches("\\d{1,2}\\.\\d{1,1}")) {
            return true;
        } else {
            DialogView.showError(parentView,"El formato de " + fieldName+ " no es correcto.");
            return false;
        }
    }

    static boolean isAValidOptionalHour(JDialog parentView, JTextField field, String fieldName, String placeHolder) {
        String text = field.getText();
        if (text.isEmpty() || text.equals(placeHolder)) {
            return true; // Field is empty or contains the placeholder, considered valid
        } else if (text.matches("\\d{1,2}\\.\\d{1}")) {
            return true; // Matches valid hour format
        } else {
            DialogView.showError(parentView, "El formato de " + fieldName+ " no es correcto.");
            return false;
        }
    }

    static boolean isAValidOptionalNumber(JDialog parentView, JTextField field, String fieldName, String placeHolder) {
        String text = field.getText();
        if (text.isEmpty() || text.equals(placeHolder)) {
            return true; // Field is empty or contains the placeholder, considered valid
        } else if (text.matches("[1-9][0-9]*")) {
            return true; // Matches valid hour format
        } else {
            DialogView.showError(parentView, "El formato de " + fieldName+ " no es correcto.");
            return false;
        }
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

    static void handleUnexpectedError(Exception e, JDialog view) {
        e.printStackTrace();
        DialogView.showError(view,"Error inesperado: ");
    }
}