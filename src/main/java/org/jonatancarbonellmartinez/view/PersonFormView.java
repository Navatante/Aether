package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.model.dao.PersonDAO;
import org.jonatancarbonellmartinez.model.utilities.LimitDocumentFilter;
import org.jonatancarbonellmartinez.observers.PersonObserver;
import org.jonatancarbonellmartinez.presenter.PersonFormPresenter;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class PersonFormView extends JDialog {
    // MVP with Observer
    private MainView mainView;
    private PersonFormPresenter presenter;
    private PersonObserver observer;  // Observer to notify when a person is added PROBABLY DELETE WHEN OBSERVER PATTERN LEARNED
    private boolean isEditMode;
    // Components
    private JTextField personPhoneField;
    private JComboBox<String> empleoBox;
    private JTextField personNkField;
    private JTextField personNameField;
    private JTextField personLastName1Field;
    private JTextField personLastName2Field;
    private JComboBox<String> divisionBox;
    private JTextField orderField;
    private JComboBox<String> rolBox;
    private JTextField personDniField;
    private JButton addButton;
    private JComboBox<String> personStateBox;
    private JTextField editPersonIdField;

    private Dimension fieldSize = new Dimension(180, 25);

    // Panels
    JPanel centerPanel;
    JPanel bottomPanel;
    JPanel topPanel;

    public PersonFormView(MainView mainView, PersonDAO personDAO, PersonObserver observer, boolean isEditMode) {
        super(mainView, isEditMode ? "Editar personal" : "Añadir personal", true);
        this.mainView = mainView; // This is mainly used to do things like setLocationRelativeTo(mainView);
        this.presenter = new PersonFormPresenter(this, personDAO);
        this.observer = observer; // PROBABLY DELETE WHEN OBSERVER PATTERN LEARNED
        this.isEditMode = isEditMode;
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

        if(isEditMode) {
            createIdSearchGui();
        }

        setVisible(true);
    }

    private void setPreferedSizeToComponents() {

        empleoBox.setPreferredSize(fieldSize);
        personNkField.setPreferredSize(fieldSize);
        personNameField.setPreferredSize(fieldSize);
        personLastName1Field.setPreferredSize(fieldSize);
        personLastName2Field.setPreferredSize(fieldSize);
        personPhoneField.setPreferredSize(fieldSize);
        divisionBox.setPreferredSize(fieldSize);
        orderField.setPreferredSize(fieldSize);
        rolBox.setPreferredSize(fieldSize);
        personDniField.setPreferredSize(fieldSize);
    }

    private void setFieldsInputConstraints() {
        ((AbstractDocument) personNameField.getDocument()).setDocumentFilter(new LimitDocumentFilter(30));
        ((AbstractDocument) personLastName1Field.getDocument()).setDocumentFilter(new LimitDocumentFilter(30));
        ((AbstractDocument) personLastName2Field.getDocument()).setDocumentFilter(new LimitDocumentFilter(30));
        ((AbstractDocument) personDniField.getDocument()).setDocumentFilter(new LimitDocumentFilter(8));
        ((AbstractDocument) personPhoneField.getDocument()).setDocumentFilter(new LimitDocumentFilter(9));
        ((AbstractDocument) orderField.getDocument()).setDocumentFilter(new LimitDocumentFilter(5));
    }

    private void initializeUIcomponents(){
        empleoBox = myComboBox(new String[]{
                "CF","TCOL","CC","CTE","TN","CAP","AN","TTE","STTE",
                "BG","SG1","SGTO","CBMY","CB1","CBO","SDO","MRO"},"Empleo");
        personNkField = myTextFieldWithLargerPlaceHolder("Código",3,6);
        personNameField = myTextField("Nombre");
        personLastName1Field = myTextField("Apellido 1");
        personLastName2Field = myTextField("Apellido 2");
        personPhoneField = myTextField("Teléfono");
        personDniField = myTextField("DNI");
        divisionBox = myComboBox(new String[] {"Jefe", "Segundo", "Operaciones","Mantenimiento","Seguridad de vuelo","Estandarización","Inteligencia"},"División");
        rolBox = myComboBox(new String[] {"Piloto", "Dotación"},"Rol");
        orderField = myTextField("Orden");
    }

    private void createPanels() {
        // Create the main panel to hold all form fields.
        centerPanel = new JPanel();
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        // Create a bottom panel for the button
        bottomPanel = new JPanel();
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 35, 10));
    }

    private void addComponentsToCenterPanel() {
        centerPanel.add(empleoBox);
        centerPanel.add(personNkField);
        centerPanel.add(personNameField);
        centerPanel.add(personLastName1Field);
        centerPanel.add(personLastName2Field);
        centerPanel.add(personPhoneField);
        centerPanel.add(personDniField);
        centerPanel.add(divisionBox);
        centerPanel.add(rolBox);
        centerPanel.add(orderField);
        //centerPanel.add(currentFlagBox);
    }

    private void createAddButton() {
        // Add an "Add" button with an action listener
        addButton = new JButton(isEditMode ? "Aplicar cambios" : "Guardar");
        addButton.addActionListener(e -> {
            if(isFormValid()) {
                if(isEditMode) {
                    presenter.editPerson();
                } else {
                    presenter.addPerson();
                }
                // Notify the observer (MainPresenter) that a person was added
                if (observer!=null) {
                    observer.onPersonChanges();
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

    private JTextField myTextFieldWithLargerPlaceHolder(String placeholder,int inputLimit, int placeholderLimit) {
        JTextField textField = new JTextField();
        textField.setText(placeholder);
        if(textField.getText().equals(placeholder)) {
            textField.setForeground(Color.GRAY);
            textField.setFont(new Font("Segoe UI", Font.ITALIC, 15));
        }

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                ((AbstractDocument) personNkField.getDocument()).setDocumentFilter(new LimitDocumentFilter(inputLimit));
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.LIGHT_GRAY);
                    textField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                ((AbstractDocument) personNkField.getDocument()).setDocumentFilter(new LimitDocumentFilter(placeholderLimit));
                if (textField.getText().trim().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                    textField.setFont(new Font("Segoe UI", Font.ITALIC, 15));
                }
            }
        });

        return textField;
    }

    private JTextField myTextField(String placeholder) {
        JTextField textField = new JTextField();
        textField.setText(placeholder);
        if(textField.getText().equals(placeholder)) {
            textField.setForeground(Color.GRAY);
            textField.setFont(new Font("Segoe UI", Font.ITALIC, 15));
        }

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
                if (textField.getText().trim().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                    textField.setFont(new Font("Segoe UI", Font.ITALIC, 15));
                }
            }

        });

        return textField;
    }

    public void clearFields() {
        // Clear text fields and restore placeholder
        resetTextFieldWithPlaceholder(personNkField, "Código");
        resetTextFieldWithPlaceholder(personPhoneField, "Teléfono");
        resetTextFieldWithPlaceholder(personNameField, "Nombre");
        resetTextFieldWithPlaceholder(personLastName1Field, "Apellido 1");
        resetTextFieldWithPlaceholder(personLastName2Field, "Apellido 2");
        resetTextFieldWithPlaceholder(personDniField, "DNI");
        resetTextFieldWithPlaceholder(orderField, "Orden");

        // Clear combo boxes and restore their default (placeholder) state
        empleoBox.setSelectedIndex(0);
        divisionBox.setSelectedIndex(0);
        rolBox.setSelectedIndex(0);
        if(isEditMode) {
            personStateBox.setSelectedIndex(0);
        }
    }

    private void resetTextFieldWithPlaceholder(JTextField textField, String placeholder) {
        textField.setText(placeholder);  // Reset to placeholder text
        textField.setForeground(Color.GRAY);  // Placeholder color
        textField.setFont(new Font("Segoe UI", Font.ITALIC, 15));  // Placeholder font
    }

    public boolean isFormValid() {
        return areFieldsValid() && areComboBoxesValid();
    }

    public boolean areFieldsValid() {
        JTextField[] textFields = {
                personNkField,
                personNameField,
                personLastName1Field,
                personLastName2Field,
                personPhoneField,
                personDniField,
                orderField
        };

        for (JTextField textField : textFields) {
            if (isTextFieldEmpty(textField)) {
                showErrorMessage("Por favor, completa todos los campos");
                return false;
            }
        }

        if (!containsOnlyLetters(getPersonName())) {
            showErrorMessage("El nombre solo debe contener letras");
            return false;
        }

        if(!containsOnlyLetters(getPersonLastName1())) {
            showErrorMessage("El Apellido1 solo debe contener letras");
            return false;
        }

        if(!containsOnlyLetters(getPersonLastName2())) {
            showErrorMessage("El Apellido2 solo debe contener letras");
            return false;
        }

        if(!containsOnlyNumbers(getPersonPhone())) { // hacer esto para el resto, en lugar de crear un metodo especifico
            showErrorMessage("El campo 'Teléfono' debe ser numérico");
            return false;
        }

        if(!containsOnlyNumbers(personDniField.getText())) {
            showErrorMessage("El campo 'DNI' debe ser numérico");
            return false;
        }

        if(!containsOnlyNumbers(String.valueOf(getPersonOrder()))) {
            showErrorMessage("El campo 'Orden' debe ser numérico");
        }

        if (orderField.getText().equals("0")) {
            showErrorMessage("El campo 'Orden' no puede ser 0");
            return false;
        }

        return true;
    }

    public boolean areComboBoxesValid() {
        JComboBox<?>[] comboBoxes = {
                empleoBox,
                divisionBox,
                rolBox
        };

        for (JComboBox<?> comboBox : comboBoxes) {
            if (isComboBoxUnselected(comboBox)) {
                showErrorMessage("Por favor, selecciona todas las opciones");
                return false;
            }
        }

        return true;
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private boolean isTextFieldEmpty(JTextField textField) {
        return textField.getText().trim().isEmpty();
    }

    private boolean isComboBoxUnselected(JComboBox<?> comboBox) {
        return comboBox.getSelectedIndex() == 0;
    }

    private boolean containsOnlyLetters(String input) {
        return input != null && input.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*$");
    }

    public boolean containsOnlyNumbers(String input) {
        return input.matches("^[0-9]*$");
    }

    public String capitalizeWords(JTextField field) {
        String text = field.getText();
        String[] words = text.split("\\s+"); // Split the string by spaces
        StringBuilder capitalizedName = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) { // Check if the word is not empty
                String firstLetter = word.substring(0, 1).toUpperCase();
                String restOfString = word.substring(1).toLowerCase();
                capitalizedName.append(firstLetter).append(restOfString).append(" ");
            }
        }
        return capitalizedName.toString().trim();
    }

    private void createIdSearchGui() {
        setSize(450,370);
        topPanel = new JPanel();
        getContentPane().add(topPanel, BorderLayout.NORTH);
        JLabel insertIdLabel = new JLabel("Introduzca el ID");
        editPersonIdField = myTextField("ID");
        editPersonIdField.setPreferredSize(new Dimension(60, 25));
        editPersonIdField.setToolTipText("Presiona Enter para buscar");
        personStateBox = myComboBox(new String[] {"Activo","Inactivo"}, "Situación");
        personStateBox.setPreferredSize(fieldSize);
        centerPanel.add(personStateBox);
        topPanel.add(insertIdLabel);
        topPanel.add(editPersonIdField);

        // Add an ActionListener to the ID text field
        editPersonIdField.addActionListener(e -> {
            String idText = editPersonIdField.getText();
            if (!idText.trim().isEmpty()) {
                try {

                    JTextField[] fieldArray = {editPersonIdField, personNameField,
                                                personLastName1Field, personLastName2Field,
                                                personNkField, personDniField, orderField, personPhoneField};
                    for(JTextField textField : fieldArray) {
                        textField.setForeground(Color.LIGHT_GRAY);
                        textField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                    }

                    int personId = Integer.parseInt(idText);
                    presenter.getPerson(personId);  // Call the presenter to fetch the person
                } catch (NumberFormatException ex) {
                    showErrorMessage("Por favor, introduce un ID válido");
                }
            }
        });
    }



    // GETTERS AND SETTERS

    public String getPersonNkField() {
        return personNkField.getText().toUpperCase();
    }

    public String getPersonRank() {
        return empleoBox.getSelectedItem().toString(); // maybe is not correct
    }

    public String getPersonName() {
        return capitalizeWords(personNameField);
    }

    public String getPersonLastName1() {
        return capitalizeWords(personLastName1Field);
    }

    public String getPersonLastName2() {
        return capitalizeWords(personLastName2Field);
    }

    public String getPersonPhone() {
        return personPhoneField.getText();
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

    public String getPersonState() {
        return personStateBox.getSelectedItem().toString();
    }

    public String getEditPersonIdField() {
        return editPersonIdField.getText();
    }

    public void setPersonNk(String personNk) {
        personNkField.setText(personNk);
    }

    public void setPersonRank(String personRank) {
        empleoBox.setSelectedItem(personRank);
    }

    public void setPersonName(String personName) {
        personNameField.setText(personName);
    }

    public void setPersonLastName1(String personLastName1) {
        personLastName1Field.setText(personLastName1);
    }

    public void setPersonLastName2(String personLastName2) {
        personLastName2Field.setText(personLastName2);
    }

    public void setPersonPhone(String personPhone) {
        personPhoneField.setText(personPhone);
    }

    public void setPersonDni(String personDni) {
        personDniField.setText(personDni);
    }

    public void setPersonDivision(String personDivision) {
        divisionBox.setSelectedItem(personDivision);
    }

    public void setPersonRol(String personRol) {
        rolBox.setSelectedItem(personRol);
    }

    public void setPersonOrder(int personOrder) {
        orderField.setText(String.valueOf(personOrder));
    }

    public void setPersonStateBox(String personState) {
        personStateBox.setSelectedItem(personState);
    }
}
