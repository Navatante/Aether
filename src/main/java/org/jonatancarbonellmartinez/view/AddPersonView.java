package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.model.dao.PersonDAO;
import org.jonatancarbonellmartinez.model.utilities.LimitDocumentFilter;
import org.jonatancarbonellmartinez.observers.AddPersonObserver;
import org.jonatancarbonellmartinez.presenter.AddPersonPresenter;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class AddPersonView extends JDialog {
    private MainView mainView;
    private AddPersonPresenter presenter;
    private AddPersonObserver observer;  // Observer to notify when a person is added PROBABLY DELETE WHEN OBSERVER PATTERN LEARNED
    // Components
    private JTextField phoneField;
    private JComboBox<String> empleoBox;
    private JTextField personNkField;
    private JTextField personNameField;
    private JTextField personLastName1Field;
    private JTextField personLastName2Field;
    private JComboBox<String> divisionBox;
    private JTextField orderField;
    private JComboBox<String> rolBox;
    private JTextField personDniField;
    private JComboBox<String> currentFlagBox;
    private JButton addButton;
    // Panels
    JPanel centerPanel;
    JPanel bottomPanel;

    public AddPersonView(MainView mainView, PersonDAO personDAO, AddPersonObserver observer) {
        super(mainView, "Añadir personal", true);
        this.mainView = mainView; // This is mainly used to do things like setLocationRelativeTo(mainView);
        this.presenter = new AddPersonPresenter(this, personDAO);
        this.observer = observer; // PROBABLY DELETE WHEN OBSERVER PATTERN LEARNED
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setResizable(false);
        setSize(450,300);
        setLocationRelativeTo(mainView);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        createPanels();
        initializeUIcomponents();
        setPreferedSizeToComponents();
        setFieldsInputConstraints();
        addComponentsToCenterPanel();
        createAddButton();
        addComponentsToBottomPanel();

        setVisible(true);
    }

    private void setPreferedSizeToComponents() {
        Dimension fieldSize = new Dimension(180, 25); // Define a common dimension

        empleoBox.setPreferredSize(fieldSize);
        personNkField.setPreferredSize(fieldSize);
        personNameField.setPreferredSize(fieldSize);
        personLastName1Field.setPreferredSize(fieldSize);
        personLastName2Field.setPreferredSize(fieldSize);
        phoneField.setPreferredSize(fieldSize);
        divisionBox.setPreferredSize(fieldSize);
        orderField.setPreferredSize(fieldSize);
        rolBox.setPreferredSize(fieldSize);
        personDniField.setPreferredSize(fieldSize);
        currentFlagBox.setPreferredSize(fieldSize);
    }

    private void setFieldsInputConstraints() {
        ((AbstractDocument) personNkField.getDocument()).setDocumentFilter(new LimitDocumentFilter(3));
        ((AbstractDocument) personNameField.getDocument()).setDocumentFilter(new LimitDocumentFilter(30));
        ((AbstractDocument) personLastName1Field.getDocument()).setDocumentFilter(new LimitDocumentFilter(30));
        ((AbstractDocument) personLastName2Field.getDocument()).setDocumentFilter(new LimitDocumentFilter(30));
        ((AbstractDocument) personDniField.getDocument()).setDocumentFilter(new LimitDocumentFilter(8));
        ((AbstractDocument) phoneField.getDocument()).setDocumentFilter(new LimitDocumentFilter(9));
        ((AbstractDocument) orderField.getDocument()).setDocumentFilter(new LimitDocumentFilter(5));
    }

    private void initializeUIcomponents(){
        empleoBox = myComboBox(new String[]{
                "CF","TCOL","CC","CTE","TN","CAP","AN","TTE","STTE",
                "BG","SG1","SGTO","CBMY","CB1","CBO","SDO","MRO"},"Empleo");
        personNkField = myTextField("Código");
        personNameField = myTextField("Nombre");
        personLastName1Field = myTextField("Apellido 1");
        personLastName2Field = myTextField("Apellido 2");
        phoneField = myTextField("Teléfono");
        personDniField = myTextField("DNI");
        divisionBox = myComboBox(new String[] {"Operaciones","Mantenimiento","Seguridad de vuelo","Estandarización","Inteligencia"},"División");
        rolBox = myComboBox(new String[] {"Piloto", "Dotación"},"Rol");
        orderField = myTextField("Orden");
        currentFlagBox = myComboBox(new String[]{"Activo", "Inactivo"},"Situación");
    }

    private void createPanels() {
        // Create the main panel to hold all form fields.
        centerPanel = new JPanel();
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        // Create a bottom panel for the button
        bottomPanel = new JPanel();
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 20, 10));
    }

    private void addComponentsToCenterPanel() {
        centerPanel.add(empleoBox);
        centerPanel.add(personNkField);
        centerPanel.add(personNameField);
        centerPanel.add(personLastName1Field);
        centerPanel.add(personLastName2Field);
        centerPanel.add(phoneField);
        centerPanel.add(personDniField);
        centerPanel.add(divisionBox);
        centerPanel.add(rolBox);
        centerPanel.add(orderField);
        centerPanel.add(currentFlagBox);
    }

    private void createAddButton() {
        // Add an "Add" button with an action listener
        addButton = new JButton("Guardar");
        addButton.addActionListener(e -> {
            if(isFormValid()) {
                presenter.addPerson();
                // Notify the observer (MainPresenter) that a person was added
                if (observer!=null) {
                    observer.onPersonAdded();
                }

            }
        });
    }

    private void addComponentsToBottomPanel() {
        bottomPanel.add(addButton);
    }


    private JComboBox<String> myComboBox(String[] listValues, String placeHolder) {
        JComboBox<String> comboBox = new JComboBox<>(listValues);
        comboBox.insertItemAt("", 0); // Add empty item at index 0
        comboBox.setSelectedIndex(0); // Set it as the selected item initially
        comboBox.setForeground(Color.GRAY); // Set default color for placeholder
        comboBox.setFont(new Font("Segoe UI", Font.ITALIC, 15)); // Initial font for placeholder

        // Custom renderer to display the placeholder
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                // If the value is empty or null, use the placeholder
                if (value == null || value.equals("")) {
                    value = placeHolder; // Use placeholder text
                }
                // Call super to get the default component
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                // Set color and font based on whether the placeholder is shown
                if (value.equals(placeHolder)) {
                    c.setForeground(Color.GRAY); // Placeholder color
                    c.setFont(new Font("Segoe UI", Font.ITALIC, 15)); // Italic font for placeholder
                } else {
                    c.setForeground(Color.LIGHT_GRAY); // Normal color for selected items
                    c.setFont(new Font("Segoe UI", Font.PLAIN, 15)); // Plain font for other selections
                }
                return c;
            }
        });

        // Add an ActionListener to handle selection changes
        comboBox.addActionListener(e -> {
            // Check the selected item and set the foreground color and font accordingly
            if (!comboBox.getSelectedItem().equals("") && !comboBox.getSelectedItem().equals(placeHolder)) {
                comboBox.setForeground(Color.LIGHT_GRAY); // Change to white if selected item is not the placeholder
                comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 15)); // Set font to plain
            } else {
                comboBox.setForeground(Color.GRAY); // Set back to gray if it is the placeholder
                comboBox.setFont(new Font("Segoe UI", Font.ITALIC, 15)); // Set back to italic
            }
        });

        return comboBox;
    }

    private JTextField myTextField(String placeholder) {
        JTextField textField = new JTextField();
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);
        textField.setFont(new Font("Segoe UI", Font.ITALIC, 15));

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.LIGHT_GRAY);
                    textField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                    textField.setFont(new Font("Segoe UI", Font.ITALIC, 15));
                }
            }
        });

        return textField;
    }

    // Getter methods for user input
    public String getPersonNkField() {
        return personNkField.getText().toUpperCase();
    }

    public String getPersonRank() {
        return empleoBox.getSelectedItem().toString(); // maybe is not correct
    }

    public String getPersonName() {
        String firstLetter = personNameField.getText().substring(0, 1).toUpperCase();
        String restOfString = personNameField.getText().substring(1).toLowerCase();
        return firstLetter + restOfString;
    }

    public String getPersonLastName1() {
        String firstLetter = personLastName1Field.getText().substring(0, 1).toUpperCase();
        String restOfString = personLastName1Field.getText().substring(1).toLowerCase();
        return firstLetter + restOfString;
    }

    public String getPersonLastName2() {
        String firstLetter = personLastName2Field.getText().substring(0, 1).toUpperCase();
        String restOfString = personLastName2Field.getText().substring(1).toLowerCase();
        return firstLetter + restOfString;
    }

    public String getPersonPhone() {
        return phoneField.getText();
    }

    public String getPersonDni() {
        // Tabla de letras correspondiente a cada resto
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int dniNumero = Integer.parseInt(personDniField.getText());
        // Obtener el resto de dividir el número del DNI entre 23
        int resto = dniNumero % 23;
        return personDniField.getText()+ letras.charAt(resto);
    }

    public String getPersonDivision() {
        return divisionBox.getSelectedItem().toString();
    }

    public int getPersonOrder() {
        return Integer.parseInt(orderField.getText());
    }

    public String getPersonRol() {
        return rolBox.getSelectedItem().toString();
    }

    public int getPersonCurrentFlag() {
        int result = 1;
        if(currentFlagBox.getSelectedItem().equals("Inactivo")) {
            result = 0;
        }
        return result;
    }

    public void clearFields() {
        // Clear text fields and restore placeholder
        resetTextFieldWithPlaceholder(personNkField, "Código");
        resetTextFieldWithPlaceholder(phoneField, "Teléfono");
        resetTextFieldWithPlaceholder(personNameField, "Nombre");
        resetTextFieldWithPlaceholder(personLastName1Field, "Apellido 1");
        resetTextFieldWithPlaceholder(personLastName2Field, "Apellido 2");
        resetTextFieldWithPlaceholder(personDniField, "DNI");
        resetTextFieldWithPlaceholder(orderField, "Orden");

        // Clear combo boxes and restore their default (placeholder) state
        empleoBox.setSelectedIndex(0);
        divisionBox.setSelectedIndex(0);
        rolBox.setSelectedIndex(0);
        currentFlagBox.setSelectedIndex(0);
    }

    // Helper method to reset text fields with placeholder logic
    private void resetTextFieldWithPlaceholder(JTextField textField, String placeholder) {
        textField.setText(placeholder);  // Reset to placeholder text
        textField.setForeground(Color.GRAY);  // Placeholder color
        textField.setFont(new Font("Segoe UI", Font.ITALIC, 15));  // Placeholder font
    }

    public boolean isFormValid() {

        // List of all JTextField components to validate
        JTextField[] textFields = {
                personNkField,
                personNameField,
                personLastName1Field,
                personLastName2Field,
                phoneField,
                personDniField,
                orderField
        };

        // Iterate through each text field and check if it's empty
        for (JTextField textField : textFields) {
            if (textField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

//        // List of all JComboBox components to validate
//        JComboBox<?>[] comboBoxes = {
//                empleoBox,
//                divisionBox,
//                rolBox,
//                currentFlagBox
//        };
//
//        // Iterate through each combo box and check if the selected item is empty or the placeholder
//        for (JComboBox<?> comboBox : comboBoxes) {
//            if (comboBox.getSelectedIndex() == 0) { // Assuming the placeholder is at index 0
//                JOptionPane.showMessageDialog(this, "Por favor, selecciona todas las opciones", "Error", JOptionPane.ERROR_MESSAGE);
//                return false;
//            }
//        }

//        // Ensure the 'order' field is numeric
//        try {
//            Integer.parseInt(orderField.getText());
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(this, "El campo 'Orden' debe ser numérico", "Error", JOptionPane.ERROR_MESSAGE);
//            return false;
//        }

        // If all validations pass
        return true;
    }

    public boolean areComboBoxesValid() {
        // List of all JComboBox components to validate
        JComboBox<?>[] comboBoxes = {
                empleoBox,
                divisionBox,
                rolBox,
                currentFlagBox
        };

        // Iterate through each combo box and check if the selected item is empty or the placeholder
        for (JComboBox<?> comboBox : comboBoxes) {
            if (comboBox.getSelectedIndex() == 0) { // Assuming the placeholder is at index 0
                JOptionPane.showMessageDialog(this, "Por favor, selecciona todas las opciones", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Return false immediately if any combo box is invalid
            }
        }

        // If all combo boxes are valid, return true
        return true;
    }


    public boolean isOrderFieldNumeric() {
        try {
            // Try to parse the integer from the text field
            Integer.parseInt(orderField.getText());
            return true; // Return true if parsing is successful
        } catch (NumberFormatException e) {
            // Show an error dialog if parsing fails
            JOptionPane.showMessageDialog(this, "El campo 'Orden' debe ser numérico", "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Return false if parsing fails
        }
    }

    public boolean noContainsSpecialCharacters(String input) { // to be used on DNI, that is the only one that have numbers and letters
        return input.matches("^[a-zA-Z0-9 ]*$");
    }

    public boolean containsOnlyNumbers(String input) {
        return input.matches("^[0-9]*$");
    }

    public boolean containsOnlyLetters(String input) {
        return input.matches("^[a-zA-Z ]*$");
    }
}
