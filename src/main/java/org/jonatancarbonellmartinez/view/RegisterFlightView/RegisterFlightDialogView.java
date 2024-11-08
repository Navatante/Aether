package org.jonatancarbonellmartinez.view.RegisterFlightView;

import org.jonatancarbonellmartinez.presenter.RegisterFlightPresenter;
import org.jonatancarbonellmartinez.view.DialogView;
import org.jonatancarbonellmartinez.view.MainView;
import org.jonatancarbonellmartinez.view.View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Date;
import java.util.Vector;

public class RegisterFlightDialogView extends JDialog implements View, DialogView {

    private MainView mainView;
    private RegisterFlightPresenter presenter;

    JSpinner dateTimeSpinner;

    JComboBox heloBox, eventBox, personBox;

    JTextField totalHoursField;

    JButton saveButton;

    JPanel flightPanel, crewPanel, savePanel;


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
        flightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
        crewPanel = new JPanel();
        savePanel = new JPanel();
    }

    @Override
    public void createComponents() {
        dateTimeSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.MINUTE));
        heloBox = View.createDynamicComboBox(new Vector<>(presenter.getHeloList()),"Helicóptero");
        eventBox = View.createDynamicComboBox(new Vector<>(presenter.getEventList()),"Evento");
        personBox = View.createDynamicComboBox(new Vector<>(presenter.getOnlyActualPilots()),"Cte. Aeronave");
        totalHoursField = View.createTextField("Horas totales");
        saveButton = new JButton("Guardar");


    }

    @Override
    public void configurePanels() {
        TitledBorder titleBorder = new TitledBorder("Vuelo");
        titleBorder.setTitleFont(new Font("Segoe UI", Font.PLAIN, 15));

        flightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 100, 10, 100),
                titleBorder
        ));



    }

    @Override
    public void configureComponents() {
        dateTimeSpinner.setEditor(new JSpinner.DateEditor(dateTimeSpinner, "dd/MM/yyyy HH:mm"));
        View.setInitialComboBoxLook(heloBox,eventBox, personBox);
        View.setPreferredSizeForComponents(new Dimension(eventBox.getPreferredSize().width+10, 25), eventBox);
        View.setPreferredSizeForComponents(new Dimension(150,24),dateTimeSpinner,heloBox, personBox, totalHoursField);
        totalHoursField.setHorizontalAlignment(JTextField.CENTER);


    }

    @Override
    public void assemblePanels() {
        getContentPane().add(flightPanel, BorderLayout.NORTH);
        getContentPane().add(savePanel,BorderLayout.SOUTH);
    }

    @Override
    public void assembleComponents() {
        View.addComponentsToPanel(flightPanel,dateTimeSpinner, heloBox, eventBox, personBox, totalHoursField);
        View.addComponentsToPanel(savePanel,saveButton);

    }

    @Override
    public void addActionListeners() {

    }
    @Override
    public void createEditModeComponents() {

    }

    @Override
    public void configureEditModeComponents() {

    }

    @Override
    public void clearFields() {

    }

    @Override
    public void onEditEntityIdFieldAction() {

    }

    @Override
    public void setDocumentFilters() {

    }
}
