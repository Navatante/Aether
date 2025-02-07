package org.jonatancarbonellmartinez.presentation.view.fxml;

import org.jonatancarbonellmartinez.presentation.viewmodel.xPersonXDialogXPresenter;
import org.jonatancarbonellmartinez.xcomponents.JonJTextField;

import javax.swing.*;
import java.awt.*;

public class xPersonXDialogXView extends JDialog implements xView, xDialogView {

    private xMainXView mainView;
    private xPersonXDialogXPresenter presenter;
    private boolean isEditMode;

    private JonJTextField personPhoneField, personNkField, personNameField, personLastName1Field,
                       personLastName2Field, orderField, personDniField, editPersonIdField;

    private JComboBox<String> empleoBox, divisionBox, rolBox, personStateBox;
    private JLabel insertIdLabel;
    private JButton saveButton;
    private JPanel topPanel, centerPanel, bottomPanel;

    public xPersonXDialogXView(xMainXView mainView, boolean isEditMode) {
        super(mainView, isEditMode ? "Editar personal" : "Añadir personal", true);
        this.mainView = mainView; // This is mainly used to do things like setLocationRelativeTo(mainView);
        this.presenter = new xPersonXDialogXPresenter(this, mainView.getPresenter());
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
        empleoBox = xView.createFixedComboBox(new String[]{"CF","TCOL","CC","CTE","TN","CAP","AN","TTE","STTE",
                "BG","SG1","SGTO","CBMY","CB1","CBO","SDO","MRO"},"Empleo");
        divisionBox = xView.createFixedComboBox(new String[] {"Jefe", "Segundo", "Operaciones","Mantenimiento",
                "Seguridad de vuelo","Estandarización","Inteligencia"},"División");
        rolBox = xView.createFixedComboBox(new String[] {"Piloto", "Dotación"},"Rol");

        personNkField = new JonJTextField(xView.INPUT_FONT, xView.PLACEHOLDER_FONT,"Código", xView.DYNAMIC_CREW_NK, xView.FINAL_CREW_NK);
        personNameField = new JonJTextField(xView.INPUT_FONT, xView.PLACEHOLDER_FONT,"Nombre", xView.DYNAMIC_FINAL_SPANISH_WORDS, xView.DYNAMIC_FINAL_SPANISH_WORDS);
        personLastName1Field = new JonJTextField(xView.INPUT_FONT, xView.PLACEHOLDER_FONT,"Apellido 1", xView.DYNAMIC_FINAL_SPANISH_WORDS, xView.DYNAMIC_FINAL_SPANISH_WORDS);
        personLastName2Field = new JonJTextField(xView.INPUT_FONT, xView.PLACEHOLDER_FONT,"Apellido 2", xView.DYNAMIC_FINAL_SPANISH_WORDS, xView.DYNAMIC_FINAL_SPANISH_WORDS);
        personPhoneField = new JonJTextField(xView.INPUT_FONT, xView.PLACEHOLDER_FONT,"Teléfono", xView.DYNAMIC_PHONE, xView.FINAL_PHONE);
        personDniField = new JonJTextField(xView.INPUT_FONT, xView.PLACEHOLDER_FONT,"DNI", xView.DYNAMIC_DNI, xView.FINAL_DNI);
        orderField = new JonJTextField(xView.INPUT_FONT, xView.PLACEHOLDER_FONT,"Orden", xView.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER, xView.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER);
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
        xView.setPreferredSizeForComponents(xDialogView.FIELD_SIZE, empleoBox, divisionBox, rolBox, personNkField, personNameField, personLastName1Field,
                personLastName2Field, personPhoneField, personDniField, orderField);
        xView.setInitialComboBoxLook(empleoBox,divisionBox,rolBox);

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
        xView.addComponentsToPanel(centerPanel, empleoBox, personNkField, personNameField, personLastName1Field, personLastName2Field,
                                    personPhoneField, personDniField, divisionBox, rolBox, orderField);
        xView.addComponentsToPanel(bottomPanel, saveButton);

        if (isEditMode) {
            xView.addComponentsToPanel(topPanel,insertIdLabel,editPersonIdField);
            xView.addComponentsToPanel(centerPanel, personStateBox);
        }
    }

    @Override
    public void addActionListeners() {
        presenter.setActionListeners();
    }

    @Override
    public void createEditModeComponents() {
        editPersonIdField = new JonJTextField(xView.INPUT_FONT, xView.PLACEHOLDER_FONT,"ID", xView.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER, xView.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER);
        personStateBox = xView.createFixedComboBox(new String[]{"Activo", "Inactivo"}, "Situación");
        insertIdLabel = new JLabel("Introduzca el ID");
    }

    @Override
    public void configureEditModeComponents() {
        editPersonIdField.setPreferredSize(new Dimension(60, 25));
        editPersonIdField.setToolTipText("Presiona Enter para buscar");
        personStateBox.setPreferredSize(xDialogView.FIELD_SIZE);
        personStateBox.setForeground(Color.GRAY);
        personStateBox.setFont(new Font("Segoe UI", Font.ITALIC, 15));
    }

    @Override
    public void clearFields() {
        empleoBox.setSelectedIndex(0);
        divisionBox.setSelectedIndex(0);
        rolBox.setSelectedIndex(0);
        if(isEditMode) {
            personStateBox.setSelectedIndex(0);
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
                presenter.getEntity(personId);
            } catch (NumberFormatException ex) {
                xDialogView.showError(this,"Por favor, introduce un ID válido");
            }
        }
    }


    // GETTERS AND SETTERS

    public JComboBox<String> getEmpleoBox() {
        return empleoBox;
    }

    public xPersonXDialogXPresenter getPresenter() {
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
