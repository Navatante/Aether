package org.jonatancarbonellmartinez.presentation.view.fxml.panels;

import org.jonatancarbonellmartinez.presentation.viewmodel.xRegisterFlightXPresenterX;
import org.jonatancarbonellmartinez.xcomponents.JonJTextField;
import org.jonatancarbonellmartinez.presentation.view.fxml.xRegisterFlightXDialogXView;
import org.jonatancarbonellmartinez.presentation.view.fxml.xView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.Vector;

public class DvCrewCardPanel extends JPanel implements xView, CrewCardPanel {
    private xRegisterFlightXPresenterX presenter;
    private xRegisterFlightXDialogXView registerFlightDialogView;

    private JPanel mainPanel, personPanel, dvPanel;

    private JPanel hoursPanel, hoursTituloPanel ,hoursDetailPanel,
            vueloPanel, vueloTituloPanel, vueloDataPanel,
            winchTrimPanel, winchTrimTituloPanel, winchTrimDataPanel;

    private JPanel projectilesPanel, projectilesTituloPanel, projectilesDetailPanel,
            m3mPanel, m3mTituloPanel, m3mDataPanel,
            magPanel, magTituloPanel, magDataPanel;

    private JLabel horasLabel, vueloLabel, winchTrimLabel, projectilesLabel, m3mLabel, magLabel;

    private JonJTextField dayHourField, nightHourField, gvnHourField, winchTrimHourField, m3mField, magField;

    private JComboBox dvBox;

    public DvCrewCardPanel(xRegisterFlightXDialogXView registerFlightDialogView, xRegisterFlightXPresenterX registerFlightPresenter) {
        this.presenter = registerFlightPresenter;
        this.registerFlightDialogView = registerFlightDialogView;
        this.initializeUI();
        setVisible(true);
    }

    @Override
    public void setupUIProperties() {
        setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
        setSize(400,400);
    }

    @Override
    public void createPanels() {
        mainPanel = new JPanel(new BorderLayout());

        personPanel = new JPanel(new GridBagLayout()); // to center personBox
        dvPanel = new JPanel(new FlowLayout());

        hoursPanel = new JPanel(new BorderLayout());
        hoursTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        hoursDetailPanel= new JPanel(new FlowLayout(FlowLayout.CENTER));

            vueloPanel = new JPanel(new BorderLayout());
            vueloTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            vueloDataPanel = new JPanel(new FlowLayout());

            winchTrimPanel = new JPanel(new BorderLayout());
            winchTrimTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            winchTrimDataPanel = new JPanel(new FlowLayout());

        projectilesPanel = new JPanel(new BorderLayout());
        projectilesTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        projectilesDetailPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            m3mPanel = new JPanel(new BorderLayout());
            m3mTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            m3mDataPanel = new JPanel(new FlowLayout());

            magPanel = new JPanel(new BorderLayout());
            magTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            magDataPanel = new JPanel(new FlowLayout());
    }

    @Override
    public void createComponents() {
        dvBox = xView.createDynamicComboBox(new Vector<>(presenter.getAllDvsVector()), "DV");

        horasLabel = new JLabel("Horas");
        vueloLabel = new JLabel("Vuelo");
        winchTrimLabel = new JLabel("Winch Trim");
        projectilesLabel = new JLabel("Proyectiles");
        m3mLabel = new JLabel("M3M");
        magLabel = new JLabel("MAG58");

        dayHourField = new JonJTextField(xView.INPUT_FONT, xView.PLACEHOLDER_FONT,"D", xView.DYNAMIC_HOUR, xView.FINAL_HOUR);
        nightHourField = new JonJTextField(xView.INPUT_FONT, xView.PLACEHOLDER_FONT,"N", xView.DYNAMIC_HOUR, xView.FINAL_HOUR);
        gvnHourField = new JonJTextField(xView.INPUT_FONT, xView.PLACEHOLDER_FONT,"G", xView.DYNAMIC_HOUR, xView.FINAL_HOUR);
        winchTrimHourField = new JonJTextField(xView.INPUT_FONT, xView.PLACEHOLDER_FONT,"W", xView.DYNAMIC_HOUR, xView.FINAL_HOUR);
        m3mField = new JonJTextField(xView.INPUT_FONT, xView.PLACEHOLDER_FONT,"P", xView.DYNAMIC_HOUR, xView.FINAL_HOUR);
        magField = new JonJTextField(xView.INPUT_FONT, xView.PLACEHOLDER_FONT,"P", xView.DYNAMIC_HOUR, xView.FINAL_HOUR);
    }

    @Override
    public void configurePanels() {
        hoursTituloPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        projectilesTituloPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));

        hoursPanel.setBorder(new EmptyBorder(0, 20, 0, 0));
        projectilesPanel.setBorder(new EmptyBorder(0, 20, 0, 0));
    }

    @Override
    public void configureComponents() {
        xView.setInitialComboBoxLook(dvBox);
        xView.setPreferredSizeForComponents(CrewCardPanel.PERSON_BOX_DIMENSION,dvBox);
        xView.setPreferredSizeForComponents(CrewCardPanel.HOUR_FIELD_DIMENSION, dayHourField, nightHourField, gvnHourField, winchTrimHourField);
        xView.setPreferredSizeForComponents(CrewCardPanel.PROJECTILE_FIELD_DIMENSION, m3mField, magField);
        xView.setHorizontalAlignmentToFields(dayHourField, nightHourField, gvnHourField, winchTrimHourField, m3mField, magField);
    }

    @Override
    public void assemblePanels() {
        this.add(mainPanel);
        mainPanel.add(personPanel, BorderLayout.WEST);
        mainPanel.add(dvPanel, BorderLayout.EAST);

        dvPanel.add(hoursPanel);
        dvPanel.add(projectilesPanel);

        hoursPanel.add(hoursTituloPanel,BorderLayout.NORTH);
        hoursPanel.add(hoursDetailPanel,BorderLayout.CENTER);
        xView.addComponentsToPanel(hoursDetailPanel, vueloPanel, winchTrimPanel);
        vueloPanel.add(vueloTituloPanel, BorderLayout.NORTH);
        vueloPanel.add(vueloDataPanel, BorderLayout.CENTER);
        winchTrimPanel.add(winchTrimTituloPanel, BorderLayout.NORTH);
        winchTrimPanel.add(winchTrimDataPanel, BorderLayout.CENTER);

        projectilesPanel.add(projectilesTituloPanel,BorderLayout.NORTH);
        projectilesPanel.add(projectilesDetailPanel,BorderLayout.CENTER);
        xView.addComponentsToPanel(projectilesDetailPanel, m3mPanel, magPanel);
        m3mPanel.add(m3mTituloPanel,BorderLayout.NORTH);
        m3mPanel.add(m3mDataPanel,BorderLayout.CENTER);
        magPanel.add(magTituloPanel,BorderLayout.NORTH);
        magPanel.add(magDataPanel,BorderLayout.CENTER);
    }

    @Override
    public void assembleComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(37, 5, 10, 5);
        personPanel.add(dvBox,gbc);

        hoursTituloPanel.add(horasLabel);
        vueloTituloPanel.add(vueloLabel);
        xView.addComponentsToPanel(vueloDataPanel, dayHourField, nightHourField, gvnHourField);
        winchTrimTituloPanel.add(winchTrimLabel);
        xView.addComponentsToPanel(winchTrimDataPanel, winchTrimHourField);

        projectilesTituloPanel.add(projectilesLabel);
        m3mTituloPanel.add(m3mLabel);
        m3mDataPanel.add(m3mField);
        magTituloPanel.add(magLabel);
        magDataPanel.add(magField);
    }

    @Override
    public void addActionListeners() {

    }

    // Getters
    @Override
    public JTextField getDayHourField() {
        return dayHourField;
    }
    @Override
    public JTextField getNightHourField() {
        return nightHourField;
    }
    @Override
    public JTextField getGvnHourField() {
        return gvnHourField;
    }

    public JTextField getWinchTrimHourField() {
        return winchTrimHourField;
    }

    public JTextField getM3mField() {
        return m3mField;
    }

    public JTextField getMagField() {
        return magField;
    }

    @Override
    public JComboBox getCrewBox() {
        return dvBox;
    }
}
