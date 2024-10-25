package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.model.dao.PersonDAO;
import org.jonatancarbonellmartinez.utilities.LimitDocumentFilter;
import org.jonatancarbonellmartinez.observers.PersonObserver;
import org.jonatancarbonellmartinez.presenter.PersonFormPresenter;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class PersonFormView extends JDialog {

    private final Dimension FIELD_SIZE = new Dimension(180, 25);

    private MainView mainView;
    private PersonFormPresenter presenter;
    private PersonObserver observer;  // Observer to notify when a person is added PROBABLY DELETE WHEN OBSERVER PATTERN LEARNED
    private boolean isEditMode;

    private JTextField personPhoneField, personNkField, personNameField, personLastName1Field,
                       personLastName2Field, orderField, personDniField, editPersonIdField;
    private JComboBox<String> empleoBox, divisionBox, rolBox, personStateBox;
    private JLabel insertIdLabel;
    private JButton saveButton;
    private JPanel topPanel, centerPanel, bottomPanel;

    public PersonFormView(MainView mainView, PersonDAO personDAO, PersonObserver observer, boolean isEditMode) {
        super(mainView, isEditMode ? "Editar personal" : "Añadir personal", true);
        this.mainView = mainView; // This is mainly used to do things like setLocationRelativeTo(mainView);
        this.presenter = new PersonFormPresenter(this, personDAO);
        this.observer = observer; // PROBABLY DELETE WHEN OBSERVER PATTERN LEARNED
        this.isEditMode = isEditMode;
        initializeUI();
    }

    private void initializeUI() {
        setupDialogProperties();
        createPanels();
        initializeComponents();
        configureComponents();
        assembleUI();
        setVisible(true);
    }

    private void setupDialogProperties() {
        setLayout(new BorderLayout());
        setResizable(false);
        setSize(450, isEditMode ? 340 : 280);
        setLocationRelativeTo(mainView);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void createPanels() {
        if (isEditMode) {
            topPanel = new JPanel();
            add(topPanel, BorderLayout.NORTH);
        }

        centerPanel = new JPanel();
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        getContentPane().add(centerPanel, BorderLayout.CENTER);

        bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 20, 10));
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
    }

    private void initializeComponents(){
        empleoBox = createComboBox(new String[]{"CF","TCOL","CC","CTE","TN","CAP","AN","TTE","STTE",
                                                "BG","SG1","SGTO","CBMY","CB1","CBO","SDO","MRO"},"Empleo");
        divisionBox = createComboBox(new String[] {"Jefe", "Segundo", "Operaciones","Mantenimiento",
                                                   "Seguridad de vuelo","Estandarización","Inteligencia"},"División");
        rolBox = createComboBox(new String[] {"Piloto", "Dotación"},"Rol");

        personNkField = createTextField("Código",3,6);
        personNameField = createTextField("Nombre");
        personLastName1Field = createTextField("Apellido 1");
        personLastName2Field = createTextField("Apellido 2");
        personPhoneField = createTextField("Teléfono");
        personDniField = createTextField("DNI");
        orderField = createTextField("Orden");

        saveButton = new JButton(isEditMode ? "Guardar cambios" : "Guardar");
        saveButton.addActionListener(e -> onSaveButtonClicked());

        if (isEditMode) {
            createEditModeComponents();
        }
    }

    private void configureComponents() {
        setFieldSizeAndConstraints();
        if (isEditMode) configureEditModeComponents();
    }

    private void assembleUI() {
        addComponentsToPanel(centerPanel, empleoBox, personNkField, personNameField, personLastName1Field,
                personLastName2Field, personPhoneField, personDniField, divisionBox, rolBox, orderField);
        bottomPanel.add(saveButton);
        if (isEditMode) {
            topPanel.add(insertIdLabel);
            topPanel.add(editPersonIdField);
            centerPanel.add(personStateBox);
        }
    }

    private void createEditModeComponents() {
        editPersonIdField = createTextField("ID", 0, 60);
        personStateBox = createComboBox(new String[]{"Activo", "Inactivo"}, "Situación");
        insertIdLabel = new JLabel("Introduzca el ID");
        editPersonIdField.addActionListener(e -> onEditPersonIdFieldAction());
    }

    private void setFieldSizeAndConstraints() {
        setPreferredSizeForComponents(FIELD_SIZE, empleoBox, divisionBox, rolBox, personNkField, personNameField,
                personLastName1Field, personLastName2Field, personPhoneField, personDniField, orderField);
        setDocumentFilters();
        setInitialComboBoxLook(empleoBox,divisionBox,rolBox);
    }

    private void setInitialComboBoxLook(JComponent... comboBox) {
        for (JComponent box : comboBox) {
            box.setForeground(Color.GRAY);
            box.setFont(new Font("Segoe UI", Font.ITALIC, 15));
        }
    }

    private void configureEditModeComponents() {
        editPersonIdField.setPreferredSize(new Dimension(60, 25));
        editPersonIdField.setToolTipText("Presiona Enter para buscar");
        personStateBox.setPreferredSize(FIELD_SIZE);
        personStateBox.setForeground(Color.GRAY);
        personStateBox.setFont(new Font("Segoe UI", Font.ITALIC, 15));
    }

    private void onSaveButtonClicked() {
        if (isFormValid()) {
            if (isEditMode) {
                presenter.editPerson();
            } else {
                presenter.addPerson();
            }
            notifyObserver();
        }
    }

    private void notifyObserver() {
        if (observer != null) {
            observer.onPersonChanges();
        }
    }

    private void onEditPersonIdFieldAction() {
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
                ((AbstractDocument) personNkField.getDocument()).setDocumentFilter(new LimitDocumentFilter(3));
                presenter.getPerson(personId);  // Call the presenter to fetch the person
            } catch (NumberFormatException ex) {
                showErrorMessage("Por favor, introduce un ID válido");
            }
        }
    }

    private JComboBox<String> createComboBox(String[] values, String placeholder) {
        JComboBox<String> comboBox = new JComboBox<>(values);
        comboBox.insertItemAt("", 0);
        comboBox.setSelectedIndex(0);
        comboBox.setRenderer(createComboBoxRenderer(placeholder));
        comboBox.addActionListener(e -> updateComboBoxAppearance(comboBox, placeholder));
        return comboBox;
    }

    private JTextField createTextField(String placeholder) {
        JTextField textField = new JTextField(placeholder);
        setPlaceholder(textField, placeholder);
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                onTextFieldFocusGained(textField, placeholder);
            }

            @Override
            public void focusLost(FocusEvent e) {
                onTextFieldFocusLost(textField, placeholder);
            }
        });
        return textField;
    }

    private JTextField createTextField(String placeholder, int inputLimit, int placeHolderLimit) {
        JTextField textField = new JTextField(placeholder);
        setPlaceholder(textField, placeholder);
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                onTextFieldFocusGained(textField, placeholder, inputLimit);
            }

            @Override
            public void focusLost(FocusEvent e) {
                onTextFieldFocusLost(textField, placeholder, placeHolderLimit);
            }
        });
        return textField;
    }

    private ListCellRenderer<Object> createComboBoxRenderer(String placeholder) {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                if (value == null || value.equals("")) value = placeholder;
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                c.setFont(new Font("Segoe UI", value.equals(placeholder) ? Font.ITALIC : Font.PLAIN, 15));
                c.setForeground(value.equals(placeholder) ? Color.GRAY : Color.LIGHT_GRAY);
                return c;
            }
        };
    }

    private void setPreferredSizeForComponents(Dimension size, JComponent... components) {
        for (JComponent component : components) {
            component.setPreferredSize(size);
        }
    }

    private void setDocumentFilters() {
        ((AbstractDocument) personNameField.getDocument()).setDocumentFilter(new LimitDocumentFilter(30));
        ((AbstractDocument) personLastName1Field.getDocument()).setDocumentFilter(new LimitDocumentFilter(30));
        ((AbstractDocument) personLastName2Field.getDocument()).setDocumentFilter(new LimitDocumentFilter(30));
        ((AbstractDocument) personDniField.getDocument()).setDocumentFilter(new LimitDocumentFilter(8));
        ((AbstractDocument) personPhoneField.getDocument()).setDocumentFilter(new LimitDocumentFilter(9));
        ((AbstractDocument) orderField.getDocument()).setDocumentFilter(new LimitDocumentFilter(5));
    }

    private void setPlaceholder(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);
        textField.setFont(new Font("Segoe UI", Font.ITALIC, 15));
    }

    private void updateComboBoxAppearance(JComboBox<String> comboBox, String placeholder) {
        if (!comboBox.getSelectedItem().equals("") && !comboBox.getSelectedItem().equals(placeholder)) {
            comboBox.setForeground(Color.LIGHT_GRAY);
            comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        } else {
            comboBox.setForeground(Color.GRAY);
            comboBox.setFont(new Font("Segoe UI", Font.ITALIC, 15));
        }
    }

    private void onTextFieldFocusGained(JTextField textField, String placeholder) {
        if (textField.getText().equals(placeholder)) {
            textField.setText("");
            textField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            textField.setForeground(Color.LIGHT_GRAY);
        }
    }

    private void onTextFieldFocusGained(JTextField textField, String placeholder, int limit) {
        if (textField.getText().equals(placeholder)) {
            ((AbstractDocument) personNkField.getDocument()).setDocumentFilter(new LimitDocumentFilter(limit));
            textField.setText("");
            textField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            textField.setForeground(Color.LIGHT_GRAY);
        }
    }

    private void onTextFieldFocusLost(JTextField textField, String placeholder) {
        if (textField.getText().isEmpty()) {
            setPlaceholder(textField, placeholder);
        }
    }

    private void onTextFieldFocusLost(JTextField textField, String placeholder, int limit) {
        if (textField.getText().isEmpty()) {
            ((AbstractDocument) personNkField.getDocument()).setDocumentFilter(new LimitDocumentFilter(limit));
            setPlaceholder(textField, placeholder);
        }
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
            resetTextFieldWithPlaceholder(editPersonIdField,"ID");
        }
    }

    private void resetTextFieldWithPlaceholder(JTextField textField, String placeholder) {
        ((AbstractDocument) personNkField.getDocument()).setDocumentFilter(new LimitDocumentFilter(6));
        textField.setText(placeholder);  // Reset to placeholder text
        textField.setForeground(Color.GRAY);  // Placeholder color
        textField.setFont(new Font("Segoe UI", Font.ITALIC, 15));  // Placeholder font
    }

    private boolean isFormValid() {
        return validateComboBox(empleoBox, "Empleo") &&
                validateComboBox(divisionBox, "División") &&
                validateComboBox(rolBox, "Rol") &&
                validateField(personNkField, "Código") &&
                validateField(personNameField, "Nombre") &&
                validateField(personLastName1Field, "Apellido 1") &&
                validateField(personLastName2Field, "Apellido 2") &&
                validateField(personPhoneField, "Teléfono") &&
                validateField(personDniField, "DNI") &&
                validateField(orderField, "Orden") &&
                fieldContainsOnlyLetters(personNkField,"Código") &&
                fieldContainsOnlyLetters(personNameField,"Nombre") &&
                fieldContainsOnlyLetters(personLastName1Field,"Apellido 1") &&
                fieldContainsOnlyLetters(personLastName2Field,"Apellido 2") &&
                containsOnlyNumbers(personPhoneField,"Teléfono") &&
                containsOnlyNumbers(personDniField,"DNI") &&
                fieldDoesntContainZero(orderField, "Orden");
    }

    private boolean validateComboBox(JComboBox<String> comboBox, String fieldName) {
        if (comboBox.getSelectedIndex() == 0) {
            showErrorMessage("Por favor, selecciona un valor para " + fieldName);
            return false;
        }
        return true;
    }

    private boolean validateField(JTextField field, String fieldName) {
        if (field.getText().isEmpty() || field.getText().equals(fieldName)) {
            showErrorMessage("Por favor, completa el campo " + fieldName);
            return false;
        }
        return true;
    }

    private boolean fieldDoesntContainZero(JTextField field, String fieldName) {
        if(field.getText().equals("0")) {
            showErrorMessage("El campo " + fieldName + " no puede ser 0.");
            return false;
        }
        return true;
    }

    private void addComponentsToPanel(JPanel panel, JComponent... components) {
        for (JComponent component : components) {
            panel.add(component);
        }
    }

    private boolean fieldContainsOnlyLetters(JTextField field, String fieldName) {
        if(!field.getText().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*$")) {
            showErrorMessage("El campo " + fieldName + " solo acepta letras.");
            return false;
        }
        return  true;
    }

    public boolean containsOnlyNumbers(JTextField field, String fieldName) {
        if(!field.getText().matches("^[0-9]*$")) {
            showErrorMessage("El campo " + fieldName + " solo acepta números.");
        }
        return true;
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

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
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
