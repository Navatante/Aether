package org.jonatancarbonellmartinez.view.RegisterFlightView;

import org.jonatancarbonellmartinez.presenter.RegisterFlightPresenter;
import org.jonatancarbonellmartinez.view.DialogView;
import org.jonatancarbonellmartinez.view.MainView;
import org.jonatancarbonellmartinez.view.View;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class RegisterFlightDialogView extends JDialog implements View, DialogView {

    private MainView mainView;
    private RegisterFlightPresenter presenter;

    JSpinner dateTimeSpinner;

    JComboBox heloBox, eventBox, commanderBox;

    JTextField totalHoursField;

    JPanel flightPanel, crewPanel;


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
        setSize(1000,700);
        setLocationRelativeTo(mainView);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @Override
    public void createPanels() {
        flightPanel = new JPanel();
        crewPanel = new JPanel();
    }

    @Override
    public void createComponents() {
        dateTimeSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.MINUTE));
        heloBox = View.createComboBox()


    }

    @Override
    public void configurePanels() {

    }

    @Override
    public void configureComponents() {
        dateTimeSpinner.setEditor(new JSpinner.DateEditor(dateTimeSpinner, "MM/dd/yyyy HH:mm"));
    }

    @Override
    public void assemblePanels() {

    }

    @Override
    public void assembleComponents() {

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
