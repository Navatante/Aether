package org.jonatancarbonellmartinez.view.panels;

import org.jonatancarbonellmartinez.presenter.RegisterFlightPresenter;
import org.jonatancarbonellmartinez.utilities.JonJTextField;
import org.jonatancarbonellmartinez.view.RegisterFlightDialogView;
import org.jonatancarbonellmartinez.view.View;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.Vector;

public class CupoHourCardPanel extends JPanel implements View {
    private RegisterFlightPresenter presenter;
    private RegisterFlightDialogView registerFlightDialogView;

    private JPanel mainPanel, unitPanel, hourPanel;

    private JComboBox unitBox;
    private JonJTextField hourQtyField;

    public CupoHourCardPanel(RegisterFlightDialogView registerFlightDialogView, RegisterFlightPresenter registerFlightPresenter) {
        this.presenter = registerFlightPresenter;
        this.registerFlightDialogView = registerFlightDialogView;
        this.initializeUI();
        setVisible(true);
    }



    @Override
    public void setupUIProperties() {
        setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
        setSize(200,100);
    }

    @Override
    public void createPanels() {
        mainPanel = new JPanel(new BorderLayout());
        unitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,8));
        hourPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,8));
    }

    @Override
    public void createComponents() {
        unitBox = View.createDynamicComboBox(new Vector<>(presenter.getAllUnitsVector()), "Autoridad");
        hourQtyField = new JonJTextField("Horas", View.DYNAMIC_HOUR, View.FINAL_HOUR);
    }

    @Override
    public void configurePanels() {
        this.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
    }

    @Override
    public void configureComponents() {
        View.setInitialComboBoxLook(unitBox);
        unitBox.setPreferredSize(new Dimension(200,25));
        View.setPreferredSizeForComponents(CrewCardPanel.PERSON_BOX_DIMENSION, hourQtyField);
        hourQtyField.setHorizontalAlignment(JTextField.CENTER);
    }

    @Override
    public void assemblePanels() {
        this.add(mainPanel);
        mainPanel.add(unitPanel, BorderLayout.WEST);
        mainPanel.add(hourPanel, BorderLayout.EAST);
    }

    @Override
    public void assembleComponents() {
        unitPanel.add(unitBox);
        hourPanel.add(hourQtyField);
    }

    @Override
    public void addActionListeners() {

    }

    public JComboBox getUnitBox() {
        return unitBox;
    }

    public JTextField getHourQtyField() {
        return hourQtyField;
    }
}
