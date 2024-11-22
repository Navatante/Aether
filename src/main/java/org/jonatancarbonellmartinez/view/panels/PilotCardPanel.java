package org.jonatancarbonellmartinez.view.panels;

import org.jonatancarbonellmartinez.presenter.RegisterFlightPresenter;
import org.jonatancarbonellmartinez.view.RegisterFlightDialogView;
import org.jonatancarbonellmartinez.view.View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.Vector;

public class PilotCardPanel extends JPanel implements View, CardPanel {

    private RegisterFlightPresenter presenter;
    RegisterFlightDialogView registerFlightDialogView;

    private JPanel mainPanel, personPanel, pilotPanel;

    private JPanel hoursPanel, hoursTituloPanel ,hoursDetailPanel,
                    vueloPanel, vueloTituloPanel, vueloDataPanel,
                    instrumentosPanel, instrumentosTituloPanel, instrumentosDataPanel,
                    hdmsPanel, hdmsTituloPanel, hdmsDataPanel,
                    instructorPanel, instructorTituloPanel, instructorDataPanel;

    private JPanel appsPanel, appsTituloPanel, appsDetailPanel,
                    precisionPanel, precisionTituloPanel, precisionDataPanel,
                    noPrecisionPanel, noPrecisionTituloPanel, noPrecisionDataPanel;

    private JPanel cuartoEjePanel, cuartoEjeTituloPanel, cuartoEjeDetailPanel,
                    sarnPanel, sarnTituloPanel, sarnDataPanel;

    private JPanel landingsPanel, landingsTituloPanel, landingsDetailPanel,
                    monospotPanel, monospotTituloPanel, monospotDataPanel,
                    multispotPanel, multispotTituloPanel, multispotDataPanel,
                    tierraPanel, tierraTituloPanel, tierraDataPanel;

    private JLabel horasLabel, vueloLabel, instrumentosLabel, hdmsLabel, instructorLabel, aproximacionesLabel, cuartoEjeLabel, precisionLabel, noPrecisionLabel, sarnLabel, tomasLabel, monospotLabel, multispotLabel, tierraLabel;

    private JTextField dayHourField, nightHourField, gvnHourField, iftHourField, hdmsHourField, instructorHourField;

    private JTextField precisionField, noPrecisionField;

    private JTextField sarnField;

    private JTextField monoDayField, monoNightField, monoGvnField, multiDayField, multiNightField, multiGvnField, tierraDayField, tierraNightField, tierraGvnField;

    JComboBox pilotBox;

    public PilotCardPanel(RegisterFlightDialogView registerFlightDialogView, RegisterFlightPresenter registerFlightPresenter) {
        this.presenter = registerFlightPresenter;
        this.registerFlightDialogView = registerFlightDialogView; // this way i have access to data like pilotList or dvList, si mas adelante doy con una mejor solucion pues cambialo.
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
        pilotPanel = new JPanel(new FlowLayout());

        hoursPanel = new JPanel(new BorderLayout());
        hoursTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        hoursDetailPanel= new JPanel(new FlowLayout(FlowLayout.CENTER));

            vueloPanel = new JPanel(new BorderLayout());
            vueloTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            vueloDataPanel = new JPanel(new FlowLayout());

            instrumentosPanel = new JPanel(new BorderLayout());
            instrumentosTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            instrumentosDataPanel = new JPanel(new FlowLayout());

            hdmsPanel = new JPanel(new BorderLayout());
            hdmsTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            hdmsDataPanel = new JPanel(new FlowLayout());

            instructorPanel = new JPanel(new BorderLayout());
            instructorTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            instructorDataPanel = new JPanel(new FlowLayout());

        appsPanel = new JPanel(new BorderLayout());
        appsTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        appsDetailPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            precisionPanel = new JPanel(new BorderLayout());
            precisionTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            precisionDataPanel = new JPanel(new FlowLayout());

            noPrecisionPanel = new JPanel(new BorderLayout());
            noPrecisionTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            noPrecisionDataPanel = new JPanel(new FlowLayout());

        cuartoEjePanel = new JPanel(new BorderLayout());
        cuartoEjeTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cuartoEjeDetailPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            sarnPanel = new JPanel(new BorderLayout());
            sarnTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            sarnDataPanel = new JPanel(new FlowLayout());

        landingsPanel = new JPanel(new BorderLayout());
        landingsTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        landingsDetailPanel = new JPanel(new FlowLayout());

            monospotPanel = new JPanel(new BorderLayout());
            monospotTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            monospotDataPanel = new JPanel(new FlowLayout());

            multispotPanel = new JPanel(new BorderLayout());
            multispotTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            multispotDataPanel = new JPanel(new FlowLayout());

            tierraPanel = new JPanel(new BorderLayout());
            tierraTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            tierraDataPanel = new JPanel(new FlowLayout());
    }

    @Override
    public void createComponents() {
        pilotBox = View.createDynamicComboBox(new Vector<>(presenter.getAllPilotsVector()),"PIL");

        horasLabel = new JLabel("Horas");
        vueloLabel = new JLabel("Vuelo");
        instrumentosLabel = new JLabel("Instrumentos");
        hdmsLabel = new JLabel("HDMS");
        instructorLabel = new JLabel("Instructor");

        dayHourField = View.createTextField("D");
        nightHourField = View.createTextField("N");
        gvnHourField = View.createTextField("G");
        iftHourField = View.createTextField("I");
        hdmsHourField = View.createTextField("H");
        instructorHourField = View.createTextField("I");

        aproximacionesLabel = new JLabel("Aproximaciones");
        precisionLabel = new JLabel("Precisión");
        noPrecisionLabel = new JLabel("No Precisión");
        precisionField = View.createTextField("P");
        noPrecisionField = View.createTextField("N");
        sarnField = View.createTextField("S");

        cuartoEjeLabel = new JLabel("Aprox. auto");
        sarnLabel = new JLabel("SAR-N");

        tomasLabel = new JLabel("Tomas");
        monospotLabel = new JLabel("Monospot");
        multispotLabel = new JLabel("Multispot");
        tierraLabel = new JLabel("Tierra");
        monoDayField = View.createTextField("D");
        monoNightField = View.createTextField("N");
        monoGvnField = View.createTextField("G");
        multiDayField = View.createTextField("D");
        multiNightField = View.createTextField("N");
        multiGvnField = View.createTextField("G");
        tierraDayField = View.createTextField("D");
        tierraNightField = View.createTextField("N");
        tierraGvnField = View.createTextField("G");
    }

    @Override
    public void configurePanels() {
        hoursTituloPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        appsTituloPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        cuartoEjeTituloPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        landingsTituloPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));

        hoursPanel.setBorder(new EmptyBorder(0, 20, 0, 0));
        appsPanel.setBorder(new EmptyBorder(0, 20, 0, 0));
        cuartoEjePanel.setBorder(new EmptyBorder(0, 20, 0, 0));
        landingsPanel.setBorder(new EmptyBorder(0, 20, 0, 0));
    }

    @Override
    public void configureComponents() {
        View.setInitialComboBoxLook(pilotBox);
        View.setPreferredSizeForComponents(CardPanel.PERSON_BOX_DIMENSION, pilotBox);
        View.setPreferredSizeForComponents(CardPanel.HOUR_FIELD_DIMENSION, dayHourField, nightHourField, gvnHourField, iftHourField, hdmsHourField, instructorHourField,
                                                                            precisionField, noPrecisionField, sarnField,
                                                                            monoDayField, monoNightField, monoGvnField, multiDayField, multiNightField, multiGvnField, tierraDayField, tierraNightField, tierraGvnField);

        View.setHorizontalAlignmentToFields(dayHourField, nightHourField, gvnHourField, iftHourField, hdmsHourField, instructorHourField,
                                            precisionField, noPrecisionField, sarnField,
                                            monoDayField, monoNightField, monoGvnField, multiDayField, multiNightField, multiGvnField,  tierraDayField, tierraNightField, tierraGvnField);
        sarnField.setToolTipText("Caladas nocturnas");
    }

    @Override
    public void assemblePanels() {
        this.add(mainPanel);
        mainPanel.add(personPanel, BorderLayout.WEST);
        mainPanel.add(pilotPanel,BorderLayout.EAST);

        pilotPanel.add(hoursPanel);
        pilotPanel.add(appsPanel);
        pilotPanel.add(cuartoEjePanel);
        pilotPanel.add(landingsPanel);

        hoursPanel.add(hoursTituloPanel,BorderLayout.NORTH);
        hoursPanel.add(hoursDetailPanel,BorderLayout.CENTER);
        View.addComponentsToPanel(hoursDetailPanel, vueloPanel, instrumentosPanel, hdmsPanel, instructorPanel);
        vueloPanel.add(vueloTituloPanel, BorderLayout.NORTH);
        vueloPanel.add(vueloDataPanel, BorderLayout.CENTER);
        instrumentosPanel.add(instrumentosTituloPanel, BorderLayout.NORTH);
        instrumentosPanel.add(instrumentosDataPanel, BorderLayout.CENTER);
        hdmsPanel.add(hdmsTituloPanel, BorderLayout.NORTH);
        hdmsPanel.add(hdmsDataPanel, BorderLayout.CENTER);
        instructorPanel.add(instructorTituloPanel, BorderLayout.NORTH);
        instructorPanel.add(instructorDataPanel, BorderLayout.CENTER);

        appsPanel.add(appsTituloPanel, BorderLayout.NORTH);
        appsPanel.add(appsDetailPanel,BorderLayout.CENTER);
        View.addComponentsToPanel(appsDetailPanel, precisionPanel, noPrecisionPanel);
        precisionPanel.add(precisionTituloPanel, BorderLayout.NORTH);
        precisionPanel.add(precisionDataPanel, BorderLayout.CENTER);
        noPrecisionPanel.add(noPrecisionTituloPanel, BorderLayout.NORTH);
        noPrecisionPanel.add(noPrecisionDataPanel, BorderLayout.CENTER);

        cuartoEjePanel.add(cuartoEjeTituloPanel, BorderLayout.NORTH);
        cuartoEjePanel.add(cuartoEjeDetailPanel, BorderLayout.CENTER);
        cuartoEjeDetailPanel.add(sarnPanel);
        sarnPanel.add(sarnTituloPanel, BorderLayout.NORTH);
        sarnPanel.add(sarnDataPanel, BorderLayout.CENTER);

        landingsPanel.add(landingsTituloPanel, BorderLayout.NORTH);
        landingsPanel.add(landingsDetailPanel, BorderLayout.CENTER);
        View.addComponentsToPanel(landingsDetailPanel, monospotPanel, multispotPanel, tierraPanel);
        monospotPanel.add(monospotTituloPanel, BorderLayout.NORTH);
        monospotPanel.add(monospotDataPanel, BorderLayout.CENTER);
        multispotPanel.add(multispotTituloPanel, BorderLayout.NORTH);
        multispotPanel.add(multispotDataPanel, BorderLayout.CENTER);
        tierraPanel.add(tierraTituloPanel, BorderLayout.NORTH);
        tierraPanel.add(tierraDataPanel, BorderLayout.CENTER);
    }

    @Override
    public void assembleComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(37, 5, 10, 5);
        personPanel.add(pilotBox,gbc);

        hoursTituloPanel.add(horasLabel);
        vueloTituloPanel.add(vueloLabel);
        View.addComponentsToPanel(vueloDataPanel, dayHourField, nightHourField, gvnHourField);
        instrumentosTituloPanel.add(instrumentosLabel);
        View.addComponentsToPanel(instrumentosDataPanel, iftHourField);
        hdmsTituloPanel.add(hdmsLabel);
        hdmsDataPanel.add(hdmsHourField);
        instructorTituloPanel.add(instructorLabel);
        instructorDataPanel.add(instructorHourField);

        appsTituloPanel.add(aproximacionesLabel);
        precisionTituloPanel.add(precisionLabel);
        precisionDataPanel.add(precisionField);
        noPrecisionTituloPanel.add(noPrecisionLabel);
        noPrecisionDataPanel.add(noPrecisionField);

        cuartoEjeTituloPanel.add(cuartoEjeLabel);
        sarnTituloPanel.add(sarnLabel);
        sarnDataPanel.add(sarnField);

        landingsTituloPanel.add(tomasLabel);
        monospotTituloPanel.add(monospotLabel);
        View.addComponentsToPanel(monospotDataPanel, monoDayField, monoNightField, monoGvnField);
        multispotTituloPanel.add(multispotLabel);
        View.addComponentsToPanel(multispotDataPanel, multiDayField, multiNightField, multiGvnField);
        tierraTituloPanel.add(tierraLabel);
        View.addComponentsToPanel(tierraDataPanel, tierraDayField, tierraNightField, tierraGvnField);
    }

    @Override
    public void addActionListeners() {

    }

    //Getters
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

    public JTextField getIftHourField() {
        return iftHourField;
    }

    public JTextField getHdmsHourField() {
        return hdmsHourField;
    }

    public JTextField getInstructorHourField() {
        return instructorHourField;
    }

    public JTextField getPrecisionField() {
        return precisionField;
    }

    public JTextField getNoPrecisionField() {
        return noPrecisionField;
    }

    public JTextField getSarnField() {
        return sarnField;
    }

    public JTextField getMonoDayField() {
        return monoDayField;
    }

    public JTextField getMonoNightField() {
        return monoNightField;
    }

    public JTextField getMonoGvnField() {
        return monoGvnField;
    }

    public JTextField getMultiDayField() {
        return multiDayField;
    }

    public JTextField getMultiNightField() {
        return multiNightField;
    }

    public JTextField getMultiGvnField() {
        return multiGvnField;
    }

    public JTextField getTierraDayField() {
        return tierraDayField;
    }

    public JTextField getTierraNightField() {
        return tierraNightField;
    }

    public JTextField getTierraGvnField() {
        return tierraGvnField;
    }

    @Override
    public JComboBox getCrewBox() {
        return pilotBox;
    }
}
