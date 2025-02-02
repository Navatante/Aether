package org.jonatancarbonellmartinez.view.panels;

import org.jonatancarbonellmartinez.viewmodel.RegisterFlightPresenter;
import org.jonatancarbonellmartinez.components.JonJTextField;
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

    private JonJTextField dayHourField;
    private JonJTextField nightHourField;
    private JonJTextField gvnHourField;
    private JonJTextField iftHourField;
    private JonJTextField hdmsHourField;
    private JonJTextField instructorHourField;
    private JonJTextField dayFormacionesHourField;
    private JonJTextField gvnFormacionesHourField;

    private JonJTextField precisionField, noPrecisionField;

    private JonJTextField tdField;

    private JonJTextField srchPattField;

    private JonJTextField monoDayField;
    private JonJTextField monoNightField;
    private JonJTextField monoGvnField;
    private JonJTextField multiDayField;
    private JonJTextField multiNightField;
    private JonJTextField multiGvnField;
    private JonJTextField tierraDayField;
    private JonJTextField tierraNightField;
    private JonJTextField tierraGvnField;
    private JonJTextField carrierDayField;
    private JonJTextField carrierNightField;
    private JonJTextField carrierGvnField;

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


        dayHourField = new JonJTextField(View.INPUT_FONT, View.PLACEHOLDER_FONT,"D", View.DYNAMIC_HOUR, View.FINAL_HOUR);
        nightHourField = new JonJTextField(View.INPUT_FONT, View.PLACEHOLDER_FONT,"N", View.DYNAMIC_HOUR, View.FINAL_HOUR);
        gvnHourField = new JonJTextField(View.INPUT_FONT, View.PLACEHOLDER_FONT,"G", View.DYNAMIC_HOUR, View.FINAL_HOUR);
        iftHourField = new JonJTextField(View.INPUT_FONT, View.PLACEHOLDER_FONT,"I", View.DYNAMIC_HOUR, View.FINAL_HOUR);
        hdmsHourField = new JonJTextField(View.INPUT_FONT, View.PLACEHOLDER_FONT,"H", View.DYNAMIC_HOUR, View.FINAL_HOUR);
        instructorHourField = new JonJTextField(View.INPUT_FONT, View.PLACEHOLDER_FONT,"I", View.DYNAMIC_HOUR, View.FINAL_HOUR);
        dayFormacionesHourField = new JonJTextField(View.INPUT_FONT, View.PLACEHOLDER_FONT,"D", View.DYNAMIC_HOUR, View.FINAL_HOUR);
        gvnFormacionesHourField = new JonJTextField(View.INPUT_FONT, View.PLACEHOLDER_FONT,"G", View.DYNAMIC_HOUR, View.FINAL_HOUR);
        aproximacionesLabel = new JLabel("App. Instrumentales");
        precisionLabel = new JLabel("Precisión");
        noPrecisionLabel = new JLabel("No Precisión ");
        precisionField = new JonJTextField(View.INPUT_FONT, View.PLACEHOLDER_FONT,"P", View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER, View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER);
        noPrecisionField = new JonJTextField(View.INPUT_FONT, View.PLACEHOLDER_FONT,"N", View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER, View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER);

        sarLabel = new JLabel("SAR");
        tdLabel = new JLabel("T/D");
        tdField = new JonJTextField(View.INPUT_FONT, View.PLACEHOLDER_FONT,"T", View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER, View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER);
        srchPattLabel = new JLabel("Search Pattern");
        srchPattField = new JonJTextField(View.INPUT_FONT, View.PLACEHOLDER_FONT,"S", View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER, View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER);

        tomasLabel = new JLabel("Tomas");
        tierraLabel = new JLabel("Tierra");
        monospotLabel = new JLabel("Monospot");
        multispotLabel = new JLabel("Multispot");
        carrierLabel = new JLabel("Carrier");

        tierraDayField = new JonJTextField(View.INPUT_FONT, View.PLACEHOLDER_FONT,"D", View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER, View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER);
        tierraNightField = new JonJTextField(View.INPUT_FONT, View.PLACEHOLDER_FONT,"N", View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER, View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER);
        tierraGvnField = new JonJTextField(View.INPUT_FONT, View.PLACEHOLDER_FONT,"G", View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER, View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER);
        monoDayField = new JonJTextField(View.INPUT_FONT, View.PLACEHOLDER_FONT,"D", View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER, View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER);
        monoNightField = new JonJTextField(View.INPUT_FONT, View.PLACEHOLDER_FONT,"N", View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER, View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER);
        monoGvnField = new JonJTextField(View.INPUT_FONT, View.PLACEHOLDER_FONT,"G", View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER, View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER);
        multiDayField = new JonJTextField(View.INPUT_FONT, View.PLACEHOLDER_FONT,"D", View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER, View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER);
        multiNightField = new JonJTextField(View.INPUT_FONT, View.PLACEHOLDER_FONT,"N", View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER, View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER);
        multiGvnField = new JonJTextField(View.INPUT_FONT, View.PLACEHOLDER_FONT,"G", View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER, View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER);
        carrierDayField = new JonJTextField(View.INPUT_FONT, View.PLACEHOLDER_FONT,"D", View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER, View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER);
        carrierNightField = new JonJTextField(View.INPUT_FONT, View.PLACEHOLDER_FONT,"N", View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER, View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER);
        carrierGvnField = new JonJTextField(View.INPUT_FONT, View.PLACEHOLDER_FONT,"G", View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER, View.DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER);
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
        landingsPanel.setBorder(new EmptyBorder(0, 62, 0, 0));
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
