package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.presenter.PersonDialogPresenter;
import org.jonatancarbonellmartinez.utilities.JonJTextField;

import javax.swing.*;
import java.awt.*;

public class PersonDialogView extends JDialog implements View, DialogView {

    private MainView mainView;
    private PersonDialogPresenter presenter;
    private boolean isEditMode;

    private JonJTextField personPhoneField, personNkField, personNameField, personLastName1Field,
                       personLastName2Field, orderField, personDniField, editPersonIdField;

    private JComboBox<String> empleoBox, divisionBox, rolBox, personStateBox;
    private JLabel insertIdLabel;
    private JButton saveButton;
    private JPanel topPanel, centerPanel, bottomPanel;

    public PersonDialogView(MainView mainView, boolean isEditMode) {
        super(mainView, isEditMode ? "Editar personal" : "Añadir personal", true);
        this.mainView = mainView; // This is mainly used to do things like setLocationRelativeTo(mainView);
        this.presenter = new PersonDialogPresenter(this, mainView.getPresenter());
        this.isEditMode = isEditMode;
        this.initializeUI();
        setVisible(true);
    }

    @Override
    public void setupUIProperties() {
        setLayout(new BorderLayout());
        setResizable(false);
        setSize(450, isEditMode ? 340 : 280);
        setLocationRelativeTo(mainView);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @Override
    public void createPanels() {
        if (isEditMode) topPanel = new JPanel();
        centerPanel = new JPanel();
        bottomPanel = new JPanel();
    }

    @Override
    public void createComponents(){
        empleoBox = View.createFixedComboBox(new String[]{"CF","TCOL","CC","CTE","TN","CAP","AN","TTE","STTE",
                "BG","SG1","SGTO","CBMY","CB1","CBO","SDO","MRO"},"Empleo");
        divisionBox = View.createFixedComboBox(new String[] {"Jefe", "Segundo", "Operaciones","Mantenimiento",
                "Seguridad de vuelo","Estandarización","Inteligencia"},"División");
        rolBox = View.createFixedComboBox(new String[] {"Piloto", "Dotación"},"Rol");

        personNkField = View.createTextField("Código",3,6);
        personNameField = View.createTextField("Nombre");
        personLastName1Field = View.createTextField("Apellido 1");
        personLastName2Field = View.createTextField("Apellido 2");
        personPhoneField = View.createTextField("Teléfono");
        personDniField = View.createTextField("DNI");
        orderField = View.createTextField("Orden");

        saveButton = new JButton(isEditMode ? "Guardar cambios" : "Guardar");

        if (isEditMode) createEditModeComponents();
    }

    @Override
    public void configurePanels() {
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 20, 10));
    }

    @Override
    public void configureComponents() {
        View.setPreferredSizeForComponents(DialogView.FIELD_SIZE, empleoBox, divisionBox, rolBox, personNkField, personNameField, personLastName1Field,
                personLastName2Field, personPhoneField, personDniField, orderField);
        setDocumentFilters();
        View.setInitialComboBoxLook(empleoBox,divisionBox,rolBox);

        if (isEditMode) configureEditModeComponents();
    }

    @Override
    public void assemblePanels() {
        if (isEditMode) getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void assembleComponents() {
        View.addComponentsToPanel(centerPanel, empleoBox, personNkField, personNameField, personLastName1Field, personLastName2Field,
                                    personPhoneField, personDniField, divisionBox, rolBox, orderField);
        View.addComponentsToPanel(bottomPanel, saveButton);

        if (isEditMode) {
            View.addComponentsToPanel(topPanel,insertIdLabel,editPersonIdField);
            View.addComponentsToPanel(centerPanel, personStateBox);
        }
    }

    @Override
    public void addActionListeners() {
        presenter.setActionListeners();
    }

    @Override
    public void createEditModeComponents() {
        editPersonIdField = View.createTextField("ID");
        personStateBox = View.createFixedComboBox(new String[]{"Activo", "Inactivo"}, "Situación");
        insertIdLabel = new JLabel("Introduzca el ID");
    }

    @Override
    public void configureEditModeComponents() {
        editPersonIdField.setPreferredSize(new Dimension(60, 25));
        editPersonIdField.setToolTipText("Presiona Enter para buscar");
        personStateBox.setPreferredSize(DialogView.FIELD_SIZE);
        personStateBox.setForeground(Color.GRAY);
        personStateBox.setFont(new Font("Segoe UI", Font.ITALIC, 15));
    }

    @Override
    public void clearFields() {
        View.setDocumentFilter(personNkField,6); // Because input was shorter than placeholder.
        View.setPlaceholder(personNkField, "Código");
        View.setPlaceholder(personPhoneField, "Teléfono");
        View.setPlaceholder(personNameField, "Nombre");
        View.setPlaceholder(personLastName1Field, "Apellido 1");
        View.setPlaceholder(personLastName2Field, "Apellido 2");
        View.setPlaceholder(personDniField, "DNI");
        View.setPlaceholder(orderField, "Orden");

        empleoBox.setSelectedIndex(0);
        divisionBox.setSelectedIndex(0);
        rolBox.setSelectedIndex(0);
        if(isEditMode) {
            personStateBox.setSelectedIndex(0);
            View.setPlaceholder(editPersonIdField,"ID");
        }
    }

    @Override
    public void onEditEntityIdFieldAction() {
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
                View.setDocumentFilter(personNkField,3);
                presenter.getEntity(personId);
            } catch (NumberFormatException ex) {
                DialogView.showError(this,"Por favor, introduce un ID válido");
            }
        }
    }

    @Override
    public void setDocumentFilters() {
        View.setDocumentFilter(personNkField,30);
        View.setDocumentFilter(personLastName1Field,30);
        View.setDocumentFilter(personLastName2Field,30);
        View.setDocumentFilter(personDniField,8);
        View.setDocumentFilter(personPhoneField,9);
        View.setDocumentFilter(orderField,5);
    }

    public boolean doNotContainZero(JTextField field, String fieldName) { // Este metodo de momento lo dejo aqui porque no creo que otra clase lo necesite.
        if(field.getText().equals("0")) {
            DialogView.showError(this,"El campo " + fieldName + " no puede ser 0.");
            return false;
        }
        return true;
    }


    // GETTERS AND SETTERS

    public JComboBox<String> getEmpleoBox() {
        return empleoBox;
    }

    public PersonDialogPresenter getPresenter() {
        return presenter;
    }

    public boolean isEditMode() {
        return isEditMode;
    }

    public JTextField getPersonPhoneField() {
        return personPhoneField;
    }

    public JTextField getPersonNameField() {
        return personNameField;
    }

    public JTextField getPersonLastName1Field() {
        return personLastName1Field;
    }

    public JTextField getPersonLastName2Field() {
        return personLastName2Field;
    }

    public JTextField getOrderField() {
        return orderField;
    }

    public JTextField getPersonDniField() {
        return personDniField;
    }

    public JComboBox<String> getDivisionBox() {
        return divisionBox;
    }

    public JComboBox<String> getRolBox() {
        return rolBox;
    }

    public JComboBox<String> getPersonStateBox() {
        return personStateBox;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JTextField getPersonNkField() {
        return personNkField;
    }

    public JTextField getEditPersonIdField() {
        return editPersonIdField;
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
