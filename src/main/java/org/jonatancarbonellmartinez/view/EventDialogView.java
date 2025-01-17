package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.presenter.EventDialogPresenter;
import org.jonatancarbonellmartinez.utilities.JonJTextField;

import javax.swing.*;
import java.awt.*;

public class EventDialogView extends JDialog implements View, DialogView {
    private MainView mainView;
    private EventDialogPresenter presenter;
    private boolean isEditMode;

    private JonJTextField eventPlaceField ,editEventIdField;

    private JComboBox<String> eventNameBox;
    private JLabel insertIdLabel;
    private JButton saveButton;
    private JPanel topPanel, centerPanel, bottomPanel;

    public EventDialogView(MainView mainView, boolean isEditMode) {
        super(mainView, isEditMode ? "Editar evento" : "Añadir Evento", true);
        this.mainView = mainView; // This is mainly used to do things like setLocationRelativeTo(mainView);
        this.presenter = new EventDialogPresenter(this, mainView.getPresenter());
        this.isEditMode = isEditMode;
        this.initializeUI();
        setVisible(true);
    }

    @Override
    public void setupUIProperties() {
        setLayout(new BorderLayout());
        setResizable(false);
        setSize(500, isEditMode ? 200 : 162);
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
    public void configurePanels() {
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 20, 10));
    }

    @Override
    public void assemblePanels() {
        if (isEditMode) getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void createComponents() {
        eventNameBox = View.createFixedComboBox(new String[]{"Adiestramiento", "Colaboración", "Maniobra nacional",
                "Maniobra internacional", "Misión", "Pruebas"}, "Nombre"); // TODO demomento este lo dejo asi porque el metodo toString() de Event devuevle nombre y lugar, y aqui solo necesito nombre.

        eventPlaceField = new JonJTextField(View.INPUT_FONT, View.PLACEHOLDER_FONT,"Lugar", View.DYNAMIC_FINAL_SPANISH_WORDS, View.DYNAMIC_FINAL_SPANISH_WORDS);

        saveButton = new JButton(isEditMode ? "Guardar cambios" : "Guardar");

        if (isEditMode) createEditModeComponents();
    }

    @Override
    public void configureComponents() {
        View.setPreferredSizeForComponents(DialogView.FIELD_SIZE, eventPlaceField);
        View.setPreferredSizeForComponents(new Dimension(200, 25), eventNameBox);
        View.setInitialComboBoxLook(eventNameBox);

        if (isEditMode) configureEditModeComponents();
    }

    @Override
    public void assembleComponents() {
        View.addComponentsToPanel(centerPanel, eventNameBox, eventPlaceField);
        View.addComponentsToPanel(bottomPanel, saveButton);

        if (isEditMode) {
            View.addComponentsToPanel(topPanel, insertIdLabel, editEventIdField);
            View.addComponentsToPanel(centerPanel);
        }
    }

    @Override
    public void addActionListeners() {
        presenter.setActionListeners();
    }

    @Override
    public void createEditModeComponents() {
        editEventIdField = new JonJTextField(View.INPUT_FONT, View.PLACEHOLDER_FONT,"ID",View.DYNAMIC_FINAL_ID,View.DYNAMIC_FINAL_ID);
        insertIdLabel = new JLabel("Introduzca el ID");
    }

    @Override
    public void configureEditModeComponents() {
        editEventIdField.setPreferredSize(new Dimension(60, 25));
        editEventIdField.setToolTipText("Presiona Enter para buscar");
    }

    @Override
    public void clearFields() {
        eventNameBox.setSelectedIndex(0);
    }

    @Override
    public void onEditEntityIdFieldAction() {
        String idText = editEventIdField.getText();
        if (!idText.trim().isEmpty()) {
            try {

                JTextField[] fieldArray = {editEventIdField, eventPlaceField};
                for (JTextField textField : fieldArray) {
                    textField.setForeground(Color.LIGHT_GRAY);
                    textField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                }

                int eventId = Integer.parseInt(idText);
                presenter.getEntity(eventId);
            } catch (NumberFormatException ex) {
                DialogView.showError(this, "Introduzca un ID válido");
            }
        }
    }

    public boolean isEditMode() {
        return isEditMode;
    }


    // GETTERS AND SETTERS
    public JTextField getEventPlaceField() {
        return eventPlaceField;
    }

    public void setEventPlaceField(String eventPlaceField) {
        this.eventPlaceField.setText(eventPlaceField);
    }

    public JTextField getEditEventIdField() {
        return editEventIdField;
    }

    public JComboBox<String> getEventNameBox() {
        return eventNameBox;
    }

    public void setEventNameBox(String eventNameBox) {
        this.eventNameBox.setSelectedItem(eventNameBox);
    }

    public JButton getSaveButton() {
        return saveButton;
    }
}


