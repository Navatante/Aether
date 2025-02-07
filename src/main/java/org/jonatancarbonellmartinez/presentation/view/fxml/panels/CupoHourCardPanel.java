package org.jonatancarbonellmartinez.presentation.view.fxml.panels;

import org.jonatancarbonellmartinez.presentation.viewmodel.xRegisterFlightXPresenterX;
import org.jonatancarbonellmartinez.xcomponents.JonJTextField;
import org.jonatancarbonellmartinez.presentation.view.fxml.xRegisterFlightXDialogXView;
import org.jonatancarbonellmartinez.presentation.view.fxml.xView;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.Vector;

public class CupoHourCardPanel extends JPanel implements xView {
    private xRegisterFlightXPresenterX presenter;
    private xRegisterFlightXDialogXView registerFlightDialogView;

    private JPanel mainPanel, unitPanel, hourPanel;

    private JComboBox unitBox;
    private JonJTextField hourQtyField;

    public CupoHourCardPanel(xRegisterFlightXDialogXView registerFlightDialogView, xRegisterFlightXPresenterX registerFlightPresenter) {
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
        unitBox = xView.createDynamicComboBox(new Vector<>(presenter.getAllUnitsVector()), "Autoridad");
        hourQtyField = new JonJTextField(xView.INPUT_FONT, xView.PLACEHOLDER_FONT,"Horas", xView.DYNAMIC_HOUR, xView.FINAL_HOUR);
    }

    @Override
    public void configurePanels() {
        this.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
    }

    @Override
    public void configureComponents() {
        xView.setInitialComboBoxLook(unitBox);
        unitBox.setPreferredSize(new Dimension(200,25));
        xView.setPreferredSizeForComponents(CrewCardPanel.PERSON_BOX_DIMENSION, hourQtyField);
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
