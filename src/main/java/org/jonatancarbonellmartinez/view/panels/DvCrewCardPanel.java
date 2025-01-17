package org.jonatancarbonellmartinez.view.panels;

import org.jonatancarbonellmartinez.presenter.RegisterFlightPresenter;
import org.jonatancarbonellmartinez.utilities.JonValidateAndLimitJTextField;
import org.jonatancarbonellmartinez.view.RegisterFlightDialogView;
import org.jonatancarbonellmartinez.view.View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.Vector;

public class DvCrewCardPanel extends JPanel implements View, CrewCardPanel {
    private RegisterFlightPresenter presenter;
    private RegisterFlightDialogView registerFlightDialogView;

    private JPanel mainPanel, personPanel, dvPanel;

    private JPanel hoursPanel, hoursTituloPanel ,hoursDetailPanel,
            vueloPanel, vueloTituloPanel, vueloDataPanel,
            winchTrimPanel, winchTrimTituloPanel, winchTrimDataPanel;

    private JPanel projectilesPanel, projectilesTituloPanel, projectilesDetailPanel,
            m3mPanel, m3mTituloPanel, m3mDataPanel,
            magPanel, magTituloPanel, magDataPanel;

    private JLabel horasLabel, vueloLabel, winchTrimLabel, projectilesLabel, m3mLabel, magLabel;

    private JonValidateAndLimitJTextField dayHourField, nightHourField, gvnHourField, winchTrimHourField, m3mField, magField;

    private JComboBox dvBox;

    public DvCrewCardPanel(RegisterFlightDialogView registerFlightDialogView, RegisterFlightPresenter registerFlightPresenter) {
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
        dvBox = View.createDynamicComboBox(new Vector<>(presenter.getAllDvsVector()), "DV");

        horasLabel = new JLabel("Horas");
        vueloLabel = new JLabel("Vuelo");
        winchTrimLabel = new JLabel("Winch Trim");
        projectilesLabel = new JLabel("Proyectiles");
        m3mLabel = new JLabel("M3M");
        magLabel = new JLabel("MAG58");

        dayHourField = new JonValidateAndLimitJTextField("D",4, View.HOUR);
        nightHourField = new JonValidateAndLimitJTextField("N",4, View.HOUR);
        gvnHourField = new JonValidateAndLimitJTextField("G",4, View.HOUR);
        winchTrimHourField = new JonValidateAndLimitJTextField("W",4, View.HOUR);
        m3mField = new JonValidateAndLimitJTextField("P",4, View.HOUR);
        magField = new JonValidateAndLimitJTextField("P",4, View.HOUR);
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
        View.setInitialComboBoxLook(dvBox);
        View.setPreferredSizeForComponents(CrewCardPanel.PERSON_BOX_DIMENSION,dvBox);
        View.setPreferredSizeForComponents(CrewCardPanel.HOUR_FIELD_DIMENSION, dayHourField, nightHourField, gvnHourField, winchTrimHourField);
        View.setPreferredSizeForComponents(CrewCardPanel.PROJECTILE_FIELD_DIMENSION, m3mField, magField);
        View.setHorizontalAlignmentToFields(dayHourField, nightHourField, gvnHourField, winchTrimHourField, m3mField, magField);
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
        View.addComponentsToPanel(hoursDetailPanel, vueloPanel, winchTrimPanel);
        vueloPanel.add(vueloTituloPanel, BorderLayout.NORTH);
        vueloPanel.add(vueloDataPanel, BorderLayout.CENTER);
        winchTrimPanel.add(winchTrimTituloPanel, BorderLayout.NORTH);
        winchTrimPanel.add(winchTrimDataPanel, BorderLayout.CENTER);

        projectilesPanel.add(projectilesTituloPanel,BorderLayout.NORTH);
        projectilesPanel.add(projectilesDetailPanel,BorderLayout.CENTER);
        View.addComponentsToPanel(projectilesDetailPanel, m3mPanel, magPanel);
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
        View.addComponentsToPanel(vueloDataPanel, dayHourField, nightHourField, gvnHourField);
        winchTrimTituloPanel.add(winchTrimLabel);
        View.addComponentsToPanel(winchTrimDataPanel, winchTrimHourField);

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
