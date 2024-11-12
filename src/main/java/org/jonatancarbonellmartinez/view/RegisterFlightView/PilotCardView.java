package org.jonatancarbonellmartinez.view.RegisterFlightView;

import org.jonatancarbonellmartinez.presenter.RegisterFlightPresenter.PilotCardPresenter;
import org.jonatancarbonellmartinez.view.View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.Vector;

public class PilotCardView extends JPanel implements View, CardView {

    private PilotCardPresenter presenter;
    RegisterFlightDialogView registerFlightDialogView;

    private JPanel mainPanel, personPanel, pilotPanel;

    private JPanel hoursPanel, hoursTituloPanel ,hoursDetailPanel,
                    vueloPanel, vueloTituloPanel, vueloDataPanel,
                    instrumentosPanel, instrumentosTituloPanel, instrumentosDataPanel,
                    hdmsPanel, hdmsTituloPanel, hdmsDataPanel,
                    instructorPanel, instructorTituloPanel, instructorDataPanel;

    private JPanel appsPanel, appsTituloPanel, appsDetailPanel,
                    appsDataPanel;

    private JPanel landingsPanel, landingsTituloPanel, landingsDetailPanel,
                    monospotPanel, monospotTituloPanel, monospotDataPanel,
                    multispotPanel, multispotTituloPanel, multispotDataPanel,
                    tierraPanel, tierraTituloPanel, tierraDataPanel;

    private JLabel horasLabel, vueloLabel, instrumentosLabel, hdmsLabel, instructorLabel, aproximacionesLabel, tomasLabel, monospotLabel, multispotLabel, tierraLabel;

    private JTextField dayHourField, nightHourField, gvnHourField, realIftHourField, simIftHourField, hdmsHourField, instructorHourField;

    private JTextField ilsField, parField, sarnField, gcaField, vorField, tacField;

    private JTextField monoDayField, monoNightField, monoGvnField, multiDayField, multiNightField, multiGvnField, tierraDayField, tierraNightField, tierraGvnField;

    JComboBox pilotBox;

    public PilotCardView(RegisterFlightDialogView registerFlightDialogView) { // TODO I have to do another class for DvCardView
        this.presenter = new PilotCardPresenter(this);
        this.registerFlightDialogView = registerFlightDialogView; // this way i have access to data like pilotList or dvList, si mas adelante doy con una mejor solucion pues cambialo.
        this.initializeUI();
        setVisible(true);
    }

    @Override
    public void setupUIProperties() {
        setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
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

            appsDataPanel = new JPanel(new GridLayout(2,3,10,11));

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
        pilotBox = View.createDynamicComboBox(new Vector<>(presenter.getOnlyActualPilots()),"Piloto");

        horasLabel = new JLabel("Horas");
        vueloLabel = new JLabel("Vuelo");
        instrumentosLabel = new JLabel("Instrumentos");
        hdmsLabel = new JLabel("HDMS");
        instructorLabel = new JLabel("Instructor");
        dayHourField = View.createTextField("D");
        nightHourField = View.createTextField("N");
        gvnHourField = View.createTextField("G");
        realIftHourField = View.createTextField("R");
        simIftHourField = View.createTextField("S");
        hdmsHourField = View.createTextField("H");
        instructorHourField = View.createTextField("I");

        aproximacionesLabel = new JLabel("Aproximaciones");
        ilsField = View.createTextField("ILS");
        parField = View.createTextField("PAR");
        sarnField = View.createTextField("SARN");
        sarnField.setToolTipText("Caladas nocturnas bla bla bla"); // TODO colocar esto en si sitio , no aqui
        gcaField = View.createTextField("GCA");
        vorField = View.createTextField("VOR");
        tacField = View.createTextField("TAC");

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
        landingsTituloPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));

        hoursPanel.setBorder(new EmptyBorder(0, 20, 0, 0));
        appsPanel.setBorder(new EmptyBorder(0, 20, 0, 0));
        landingsPanel.setBorder(new EmptyBorder(0, 20, 0, 0));
    }

    @Override
    public void configureComponents() {
        View.setPreferredSizeForComponents(CardView.PERSON_BOX_DIMENSION, pilotBox);
        View.setPreferredSizeForComponents(CardView.HOUR_FIELD_DIMENSION, dayHourField, nightHourField, gvnHourField, realIftHourField, simIftHourField, hdmsHourField, instructorHourField);
        View.setPreferredSizeForComponents(APP_FIELD_DIMENSION, ilsField, parField, sarnField, gcaField, vorField, tacField);
        View.setPreferredSizeForComponents(CardView.HOUR_FIELD_DIMENSION, monoDayField, monoNightField, monoGvnField, multiDayField, multiNightField, multiGvnField, tierraDayField, tierraNightField, tierraGvnField);

        View.setHorizontalAlignmentToFields(dayHourField, nightHourField, gvnHourField, realIftHourField, simIftHourField, hdmsHourField, instructorHourField);
        View.setHorizontalAlignmentToFields(ilsField, parField, sarnField, gcaField, vorField, tacField);
        View.setHorizontalAlignmentToFields(monoDayField, monoNightField, monoGvnField, multiDayField, multiNightField, multiGvnField,  tierraDayField, tierraNightField, tierraGvnField);

        View.setInitialComboBoxLook(pilotBox);
    }

    @Override
    public void assemblePanels() {
        this.add(mainPanel);
        mainPanel.add(personPanel, BorderLayout.WEST);
        mainPanel.add(pilotPanel,BorderLayout.EAST);

        pilotPanel.add(hoursPanel);
        pilotPanel.add(appsPanel);
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
        appsDetailPanel.add(appsDataPanel);

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
        View.addComponentsToPanel(instrumentosDataPanel, realIftHourField, simIftHourField);
        hdmsTituloPanel.add(hdmsLabel);
        hdmsDataPanel.add(hdmsHourField);
        instructorTituloPanel.add(instructorLabel);
        instructorDataPanel.add(instructorHourField);

        appsTituloPanel.add(aproximacionesLabel);
        View.addComponentsToPanel(appsDataPanel, ilsField, parField, sarnField, gcaField, vorField, tacField);

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
    public PilotCardPresenter getPresenter() {
        return presenter;
    }
}
