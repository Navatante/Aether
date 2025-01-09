package org.jonatancarbonellmartinez.view.panels;

import org.jonatancarbonellmartinez.presenter.RegisterFlightPresenter;
import org.jonatancarbonellmartinez.view.RegisterFlightDialogView;
import org.jonatancarbonellmartinez.view.View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.Vector;

public class PilotCrewCardPanel extends JPanel implements View, CrewCardPanel {

    private RegisterFlightPresenter presenter;
    RegisterFlightDialogView registerFlightDialogView;

    private JPanel mainPanel, personPanel, pilotPanel, pilotPanelTop, pilotPanelBottom;

    private JPanel hoursPanel, hoursTituloPanel ,hoursDetailPanel,
                    vueloPanel, vueloTituloPanel, vueloDataPanel,
                    instrumentosPanel, instrumentosTituloPanel, instrumentosDataPanel,
                    hdmsPanel, hdmsTituloPanel, hdmsDataPanel,
                    instructorPanel, instructorTituloPanel, instructorDataPanel,
                    formacionesPanel, formacionesTituloPanel, formacionesDataPanel;

    private JPanel appsPanel, appsTituloPanel, appsDetailPanel,
                    precisionPanel, precisionTituloPanel, precisionDataPanel,
                    noPrecisionPanel, noPrecisionTituloPanel, noPrecisionDataPanel;

    private JPanel sarPanel, sarTituloPanel, sarDetailPanel,
                    tdPanel, tdTituloPanel, tdDataPanel,
                    srchPattPanel, srchPattTituloPanel, srchPattDataPanel;

    private JPanel landingsPanel, landingsTituloPanel, landingsDetailPanel,
                    tierraPanel, tierraTituloPanel, tierraDataPanel,
                    monospotPanel, monospotTituloPanel, monospotDataPanel,
                    multispotPanel, multispotTituloPanel, multispotDataPanel,
                    carrierPanel, carrierTituloPanel, carrierDataPanel;


    private JLabel horasLabel, vueloLabel, instrumentosLabel, hdmsLabel, instructorLabel, formacionesLabel, aproximacionesLabel, sarLabel, precisionLabel, noPrecisionLabel, tdLabel,srchPattLabel, tomasLabel, monospotLabel, multispotLabel, tierraLabel, carrierLabel;

    private JTextField dayHourField;
    private JTextField nightHourField;
    private JTextField gvnHourField;
    private JTextField iftHourField;
    private JTextField hdmsHourField;
    private JTextField instructorHourField;
    private JTextField dayFormacionesHourField;
    private JTextField gvnFormacionesHourField;

    private JTextField precisionField, noPrecisionField;

    private JTextField tdField;

    private JTextField srchPattField;

    private JTextField monoDayField;
    private JTextField monoNightField;
    private JTextField monoGvnField;
    private JTextField multiDayField;
    private JTextField multiNightField;
    private JTextField multiGvnField;
    private JTextField tierraDayField;
    private JTextField tierraNightField;
    private JTextField tierraGvnField;
    private JTextField carrierDayField;
    private JTextField carrierNightField;
    private JTextField carrierGvnField;

    JComboBox pilotBox;

    private String pilotPlaceHolder;

    public PilotCrewCardPanel(RegisterFlightDialogView registerFlightDialogView, RegisterFlightPresenter registerFlightPresenter, String pilotPlaceHolder) {
        this.presenter = registerFlightPresenter;
        this.registerFlightDialogView = registerFlightDialogView; // this way i have access to data like pilotList or dvList, si mas adelante doy con una mejor solucion pues cambialo.
        this.pilotPlaceHolder = pilotPlaceHolder;
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
        pilotPanel = new JPanel(new BorderLayout());
        pilotPanelTop = new JPanel(new BorderLayout());
        pilotPanelBottom = new JPanel(new BorderLayout());

        hoursPanel = new JPanel(new BorderLayout());
        hoursTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        hoursDetailPanel= new JPanel(new FlowLayout(FlowLayout.CENTER,55,0));

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

            formacionesPanel = new JPanel(new BorderLayout());
            formacionesTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            formacionesDataPanel = new JPanel(new FlowLayout());

        appsPanel = new JPanel(new BorderLayout());
        appsTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        appsDetailPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,33,0));

            precisionPanel = new JPanel(new BorderLayout());
            precisionTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            precisionDataPanel = new JPanel(new FlowLayout());

            noPrecisionPanel = new JPanel(new BorderLayout());
            noPrecisionTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            noPrecisionDataPanel = new JPanel(new FlowLayout());

        sarPanel = new JPanel(new BorderLayout());
        sarTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        sarDetailPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,33,0));

            tdPanel = new JPanel(new BorderLayout());
            tdTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            tdDataPanel = new JPanel(new FlowLayout());

            srchPattPanel = new JPanel(new BorderLayout());
            srchPattTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            srchPattDataPanel = new JPanel(new FlowLayout());

        landingsPanel = new JPanel(new BorderLayout());
        landingsTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        landingsDetailPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,31,0));

            tierraPanel = new JPanel(new BorderLayout());
            tierraTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            tierraDataPanel = new JPanel(new FlowLayout());

            monospotPanel = new JPanel(new BorderLayout());
            monospotTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            monospotDataPanel = new JPanel(new FlowLayout());

            multispotPanel = new JPanel(new BorderLayout());
            multispotTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            multispotDataPanel = new JPanel(new FlowLayout());

            carrierPanel = new JPanel(new BorderLayout());
            carrierTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            carrierDataPanel = new JPanel(new FlowLayout());
    }

    @Override
    public void createComponents() {
        pilotBox = View.createDynamicComboBox(new Vector<>(presenter.getAllPilotsVector()),pilotPlaceHolder);

        horasLabel = new JLabel("Horas");
        vueloLabel = new JLabel("Vuelo");
        instrumentosLabel = new JLabel("Instrumental");
        hdmsLabel = new JLabel("HMDS");
        instructorLabel = new JLabel("IP");
        formacionesLabel = new JLabel("Formaciones");

        dayHourField = View.createTextField("D");
        nightHourField = View.createTextField("N");
        gvnHourField = View.createTextField("G");
        iftHourField = View.createTextField("I");
        hdmsHourField = View.createTextField("H");
        instructorHourField = View.createTextField("I");
        dayFormacionesHourField = View.createTextField("D");
        gvnFormacionesHourField = View.createTextField("G");

        aproximacionesLabel = new JLabel("App. Instrumentales");
        precisionLabel = new JLabel("Precisión");
        noPrecisionLabel = new JLabel("No Precisión ");
        precisionField = View.createTextField("P");
        noPrecisionField = View.createTextField("N");


        sarLabel = new JLabel("SAR");
        tdLabel = new JLabel("T/D");
        tdField = View.createTextField("T");
        srchPattLabel = new JLabel("Search Pattern");
        srchPattField = View.createTextField("S");

        tomasLabel = new JLabel("Tomas");
        tierraLabel = new JLabel("Tierra");
        monospotLabel = new JLabel("Monospot");
        multispotLabel = new JLabel("Multispot");
        carrierLabel = new JLabel("Carrier");
        tierraDayField = View.createTextField("D");
        tierraNightField = View.createTextField("N");
        tierraGvnField = View.createTextField("G");
        monoDayField = View.createTextField("D");
        monoNightField = View.createTextField("N");
        monoGvnField = View.createTextField("G");
        multiDayField = View.createTextField("D");
        multiNightField = View.createTextField("N");
        multiGvnField = View.createTextField("G");
        carrierDayField = View.createTextField("D");
        carrierNightField = View.createTextField("N");
        carrierGvnField = View.createTextField("G");

    }

    @Override
    public void configurePanels() {
        hoursTituloPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        appsTituloPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        sarTituloPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        landingsTituloPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));

        pilotPanelTop.setBorder(new EmptyBorder(0,0,20,0));
        hoursPanel.setBorder(new EmptyBorder(0, 50, 0, 0));
        appsPanel.setBorder(new EmptyBorder(0, 100, 0, 0));
        sarPanel.setBorder(new EmptyBorder(0, 100, 0, 0));
        landingsPanel.setBorder(new EmptyBorder(0, 50, 0, 0));
    }

    @Override
    public void configureComponents() {
        View.setInitialComboBoxLook(pilotBox);
        View.setPreferredSizeForComponents(CrewCardPanel.PERSON_BOX_DIMENSION, pilotBox);
        View.setPreferredSizeForComponents(CrewCardPanel.HOUR_FIELD_DIMENSION, dayHourField, nightHourField, gvnHourField, iftHourField, hdmsHourField, instructorHourField, dayFormacionesHourField, gvnFormacionesHourField,
                                                                            precisionField, noPrecisionField, tdField, srchPattField,
                                                                            monoDayField, monoNightField, monoGvnField, multiDayField, multiNightField, multiGvnField, tierraDayField, tierraNightField, tierraGvnField, carrierDayField, carrierNightField, carrierGvnField);

        View.setHorizontalAlignmentToFields(dayHourField, nightHourField, gvnHourField, iftHourField, hdmsHourField, instructorHourField, dayFormacionesHourField, gvnFormacionesHourField,
                                            precisionField, noPrecisionField, tdField, srchPattField,
                                            monoDayField, monoNightField, monoGvnField, multiDayField, multiNightField, multiGvnField,  tierraDayField, tierraNightField, tierraGvnField, carrierDayField, carrierNightField, carrierGvnField);
        tdField.setToolTipText("Caladas nocturnas");
    }

    @Override
    public void assemblePanels() {
        this.add(mainPanel);
        mainPanel.add(personPanel, BorderLayout.WEST);
        mainPanel.add(pilotPanel,BorderLayout.EAST);

        pilotPanel.add(pilotPanelTop,BorderLayout.NORTH);
        pilotPanel.add(pilotPanelBottom,BorderLayout.SOUTH);

        pilotPanelTop.add(hoursPanel, BorderLayout.WEST);
        pilotPanelTop.add(appsPanel, BorderLayout.EAST);
        pilotPanelBottom.add(landingsPanel, BorderLayout.WEST);
        pilotPanelBottom.add(sarPanel, BorderLayout.EAST);

        hoursPanel.add(hoursTituloPanel,BorderLayout.NORTH);
        hoursPanel.add(hoursDetailPanel,BorderLayout.CENTER);
        View.addComponentsToPanel(hoursDetailPanel, vueloPanel, instrumentosPanel, hdmsPanel, instructorPanel, formacionesPanel);
        vueloPanel.add(vueloTituloPanel, BorderLayout.NORTH);
        vueloPanel.add(vueloDataPanel, BorderLayout.CENTER);
        instrumentosPanel.add(instrumentosTituloPanel, BorderLayout.NORTH);
        instrumentosPanel.add(instrumentosDataPanel, BorderLayout.CENTER);
        hdmsPanel.add(hdmsTituloPanel, BorderLayout.NORTH);
        hdmsPanel.add(hdmsDataPanel, BorderLayout.CENTER);
        instructorPanel.add(instructorTituloPanel, BorderLayout.NORTH);
        instructorPanel.add(instructorDataPanel, BorderLayout.CENTER);
        formacionesPanel.add(formacionesTituloPanel, BorderLayout.NORTH);
        formacionesPanel.add(formacionesDataPanel, BorderLayout.CENTER);

        appsPanel.add(appsTituloPanel, BorderLayout.NORTH);
        appsPanel.add(appsDetailPanel,BorderLayout.CENTER);
        View.addComponentsToPanel(appsDetailPanel, precisionPanel, noPrecisionPanel);
        precisionPanel.add(precisionTituloPanel, BorderLayout.NORTH);
        precisionPanel.add(precisionDataPanel, BorderLayout.CENTER);
        noPrecisionPanel.add(noPrecisionTituloPanel, BorderLayout.NORTH);
        noPrecisionPanel.add(noPrecisionDataPanel, BorderLayout.CENTER);

        sarPanel.add(sarTituloPanel, BorderLayout.NORTH);
        sarPanel.add(sarDetailPanel, BorderLayout.CENTER);
        sarDetailPanel.add(tdPanel);
        sarDetailPanel.add(srchPattPanel);
        tdPanel.add(tdTituloPanel, BorderLayout.NORTH);
        tdPanel.add(tdDataPanel, BorderLayout.CENTER);
        srchPattPanel.add(srchPattTituloPanel, BorderLayout.NORTH);
        srchPattPanel.add(srchPattDataPanel, BorderLayout.CENTER);

        landingsPanel.add(landingsTituloPanel, BorderLayout.NORTH);
        landingsPanel.add(landingsDetailPanel, BorderLayout.CENTER);
        View.addComponentsToPanel(landingsDetailPanel, tierraPanel,monospotPanel, multispotPanel, carrierPanel);
        tierraPanel.add(tierraTituloPanel, BorderLayout.NORTH);
        tierraPanel.add(tierraDataPanel, BorderLayout.CENTER);
        monospotPanel.add(monospotTituloPanel, BorderLayout.NORTH);
        monospotPanel.add(monospotDataPanel, BorderLayout.CENTER);
        multispotPanel.add(multispotTituloPanel, BorderLayout.NORTH);
        multispotPanel.add(multispotDataPanel, BorderLayout.CENTER);
        carrierPanel.add(carrierTituloPanel, BorderLayout.NORTH);
        carrierPanel.add(carrierDataPanel, BorderLayout.CENTER);

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
        formacionesTituloPanel.add(formacionesLabel);
        View.addComponentsToPanel(formacionesDataPanel, dayFormacionesHourField, gvnFormacionesHourField);

        appsTituloPanel.add(aproximacionesLabel);
        precisionTituloPanel.add(precisionLabel);
        precisionDataPanel.add(precisionField);
        noPrecisionTituloPanel.add(noPrecisionLabel);
        noPrecisionDataPanel.add(noPrecisionField);

        sarTituloPanel.add(sarLabel);
        tdTituloPanel.add(tdLabel);
        tdDataPanel.add(tdField);
        srchPattTituloPanel.add(srchPattLabel);
        srchPattDataPanel.add(srchPattField);

        landingsTituloPanel.add(tomasLabel);
        tierraTituloPanel.add(tierraLabel);
        View.addComponentsToPanel(tierraDataPanel, tierraDayField, tierraNightField, tierraGvnField);
        monospotTituloPanel.add(monospotLabel);
        View.addComponentsToPanel(monospotDataPanel, monoDayField, monoNightField, monoGvnField);
        multispotTituloPanel.add(multispotLabel);
        View.addComponentsToPanel(multispotDataPanel, multiDayField, multiNightField, multiGvnField);
        carrierTituloPanel.add(carrierLabel);
        View.addComponentsToPanel(carrierDataPanel, carrierDayField, carrierNightField, carrierGvnField);

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

    public JTextField getTdField() {
        return tdField;
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

    public JTextField getDayFormacionesHourField() {
        return dayFormacionesHourField;
    }

    public JTextField getGvnFormacionesHourField() {
        return gvnFormacionesHourField;
    }

    public JTextField getSrchPattField() {
        return srchPattField;
    }

    public JTextField getCarrierDayField() {
        return carrierDayField;
    }

    public JTextField getCarrierNightField() {
        return carrierNightField;
    }

    public JTextField getCarrierGvnField() {
        return carrierGvnField;
    }

    @Override
    public JComboBox getCrewBox() {
        return pilotBox;
    }
}
