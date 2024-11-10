package org.jonatancarbonellmartinez.view.RegisterFlightView;

import org.jonatancarbonellmartinez.presenter.RegisterFlightPresenter.RegisterFlightPresenter;
import org.jonatancarbonellmartinez.view.DialogView;
import org.jonatancarbonellmartinez.view.MainView;
import org.jonatancarbonellmartinez.view.View;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class RegisterFlightDialogView extends JDialog implements View, DialogView {

    private MainView mainView;
    private RegisterFlightPresenter presenter;

    private PilotCardView pilotCardView;


    JSpinner dateTimeSpinner;

    JComboBox heloBox, eventBox, personBox;

    JTextField totalHoursField;

    JButton saveButton;

    JPanel topPanel, centerPanel, bottomPanel, vueloPanel, tripulantesPanel;


    public RegisterFlightDialogView(MainView mainView) {
        super(mainView, "Registrar vuelo");
        this.mainView = mainView;
        this.presenter = new RegisterFlightPresenter(this, mainView.getPresenter());
        this.initializeUI();
        setVisible(true);
    }

    @Override
    public void setupUIProperties() {
        setLayout(new BorderLayout());
        setResizable(false);
        setSize(1200,700);
        setLocationRelativeTo(mainView);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @Override
    public void createPanels() {
        topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        vueloPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tripulantesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel = new JPanel();
        pilotCardView = new PilotCardView(this);
    }

    @Override
    public void createComponents() {

        dateTimeSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.MINUTE));
        heloBox = View.createDynamicComboBox(new Vector<>(presenter.getHeloList()),"Helicóptero");
        eventBox = View.createDynamicComboBox(new Vector<>(presenter.getEventList()),"Evento");
        personBox = View.createDynamicComboBox(new Vector<>(presenter.getOnlyActualPilots()),"Cte. Aeronave");
        totalHoursField = View.createTextField("Horas totales",4,13);
        saveButton = new JButton("Guardar");


    }

    @Override
    public void configurePanels() {
        TitledBorder titleBorder = new TitledBorder("Vuelo");
        titleBorder.setTitleFont(new Font("Segoe UI", Font.PLAIN, 15));

        vueloPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 100, 10, 100),
                titleBorder
        ));

        TitledBorder titleBorder2 = new TitledBorder("Tripulantes");
        titleBorder2.setTitleFont(new Font("Segoe UI", Font.PLAIN, 15));

        tripulantesPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 100, 10, 100),
                titleBorder2
        ));

        pilotCardView.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));


//        topPanel.setBackground(Color.DARK_GRAY);
//        bottomPanel.setBackground(Color.DARK_GRAY);
//        pilotCardView.setBackground(Color.DARK_GRAY);
    }

    @Override
    public void configureComponents() {
        dateTimeSpinner.setEditor(new JSpinner.DateEditor(dateTimeSpinner, "dd/MM/yyyy HH:mm"));
        View.setInitialComboBoxLook(heloBox,eventBox, personBox);
        View.setPreferredSizeForComponents(new Dimension(eventBox.getPreferredSize().width+10, 25), eventBox);
        View.setPreferredSizeForComponents(new Dimension(150,24),dateTimeSpinner,heloBox, personBox, totalHoursField);
        totalHoursField.setHorizontalAlignment(JTextField.CENTER);
        setDocumentFilters();

        // Set a bigger font for the editor
        JSpinner.DateEditor editor = (JSpinner.DateEditor) dateTimeSpinner.getEditor();
        JFormattedTextField textField = editor.getTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 15));

    }

    @Override
    public void assemblePanels() {
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(centerPanel,BorderLayout.CENTER);
        getContentPane().add(bottomPanel,BorderLayout.SOUTH);

        topPanel.add(vueloPanel);

        centerPanel.add(tripulantesPanel);
        tripulantesPanel.add(pilotCardView);
    }

    @Override
    public void assembleComponents() {
        View.addComponentsToPanel(vueloPanel,dateTimeSpinner, heloBox, eventBox, personBox, totalHoursField);
        View.addComponentsToPanel(bottomPanel,saveButton);
    }

    @Override
    public void addActionListeners() {
        presenter.setActionListeners();
    }
    @Override
    public void createEditModeComponents() {

    }

    @Override
    public void configureEditModeComponents() {

    }

    @Override
    public void clearFields() {
        View.setDocumentFilter(totalHoursField,13); // This is set because placeholder is larger than value permitted limit.
        heloBox.setSelectedIndex(0);
        eventBox.setSelectedIndex(0);
        personBox.setSelectedIndex(0);
        View.setPlaceholder(totalHoursField,"Horas totales");
    }

    @Override
    public void onEditEntityIdFieldAction() {
        // nothing to do here
    }

    @Override
    public void setDocumentFilters() {
        View.setDocumentFilter(totalHoursField,4);
    }

    // GETTERS
    public JComboBox getHeloBox() {
        return heloBox;
    }

    public JComboBox getPersonBox() {
        return personBox;
    }

    public JComboBox getEventBox() {
        return eventBox;
    }

    public JTextField getTotalHoursField() {
        return totalHoursField;
    }

    public String getDateTimeSpinner() { // Logic implemented to return a string with the date and time
        // Retrieve the Date from the JSpinner
        Date date = (Date) dateTimeSpinner.getValue();

        // Format Date to ISO 8601 string format
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = isoFormat.format(date);

        return dateString;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public PilotCardView getPilotCardView() {
        return pilotCardView;
    }

}
