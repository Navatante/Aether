package org.jonatancarbonellmartinez.presentation.view.fxml.panels;

import org.jonatancarbonellmartinez.presentation.viewmodel.xRegisterFlightXPresenterX;
import org.jonatancarbonellmartinez.xcomponents.JonJTextField;
import org.jonatancarbonellmartinez.presentation.view.fxml.xRegisterFlightXDialogXView;
import org.jonatancarbonellmartinez.presentation.view.fxml.xView;

import javax.swing.*;
import java.awt.*;

public class PassengerCardPanel extends JPanel implements xView {
    private xRegisterFlightXPresenterX presenter;
    private xRegisterFlightXDialogXView registerFlightDialogView;

    private JPanel mainPanel, typePanel, qtyPanel, routePanel;

    private JComboBox typeBox;
    private JonJTextField qtyField, routeField;

    public PassengerCardPanel(xRegisterFlightXDialogXView registerFlightDialogView, xRegisterFlightXPresenterX registerFlightPresenter) {
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
        typePanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,8));
        qtyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,8));
        routePanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,8));
    }

    @Override
    public void createComponents() {
        typeBox = xView.createFixedComboBox(new String[] {"Civiles", "Militares"},"Tipo");
        qtyField = new JonJTextField(xView.INPUT_FONT, xView.PLACEHOLDER_FONT,"Cantidad", xView.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER, xView.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER);
        routeField = new JonJTextField(xView.INPUT_FONT, xView.PLACEHOLDER_FONT,"Ruta", xView.DYNAMIC_ROUTE, xView.FINAL_ROUTE);
    }

    @Override
    public void configurePanels() {
        xView.setInitialComboBoxLook(typeBox);
        typeBox.setPreferredSize(new Dimension(100,25));
        qtyField.setPreferredSize(new Dimension(80,25));
        routeField.setPreferredSize(new Dimension(110,25));
    }

    @Override
    public void configureComponents() {
        this.add(mainPanel);
        mainPanel.add(typePanel,BorderLayout.WEST);
        mainPanel.add(qtyPanel,BorderLayout.CENTER);
        mainPanel.add(routePanel,BorderLayout.EAST);
        qtyField.setHorizontalAlignment(JTextField.CENTER);
    }

    @Override
    public void assemblePanels() {
        typePanel.add(typeBox);
        qtyPanel.add(qtyField);
        routePanel.add(routeField);
    }

    @Override
    public void assembleComponents() {

    }

    @Override
    public void addActionListeners() {

    }

    // Getters and Setters
    public JComboBox getTypeBox() {
        return typeBox;
    }

    public JTextField getRouteField() {
        return routeField;
    }

    public JTextField getQtyField() {
        return qtyField;
    }
}
