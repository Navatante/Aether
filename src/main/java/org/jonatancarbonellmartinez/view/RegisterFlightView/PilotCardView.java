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

    private JPanel hoursPanel, hoursTituloPanel ,hoursDetailPanel;

    private JPanel vueloPanel, vueloTituloPanel, vueloDataPanel;

    private JPanel instrumentosPanel, instrumentosTituloPanel, instrumentosDataPanel;

    private JPanel hdmsPanel, hdmsTituloPanel, hdmsDataPanel;

    private JPanel instructorPanel, instructorTituloPanel, instructorDataPanel;

    private JPanel appsPanel, appsTituloPanel, appsDetailPanel;

    private JPanel appsDataPanel;

    private JPanel landingsPanel, ladingsTituloPanel, ladingsDetailPanel;

    private JLabel horasLabel, vueloLabel, instrumentosLabel, hdmsLabel, instructorLabel, aproximacionesLabel, tomasLabel;

    private JTextField dayHourField, nightHourField, gvnHourField, realIftHourField, simIftHourField, hdmsHourField, instructorHourField;

    private JTextField ilsField, parField, rnavField, gcaField, vorField, tacField;



    JComboBox personBox;

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

        hoursTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        hoursPanel = new JPanel(new BorderLayout());
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

        appsTituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        appsPanel = new JPanel(new BorderLayout());
            appsDetailPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                appsDataPanel = new JPanel(new GridLayout(2,3,10,11));

        // TODO me queda tomas
    }

    @Override
    public void createComponents() {
        personBox = View.createDynamicComboBox(new Vector<>(presenter.getOnlyActualPilots()),"Piloto");

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
        rnavField = View.createTextField("RNAV");
        gcaField = View.createTextField("GCA");
        vorField = View.createTextField("VOR");
        tacField = View.createTextField("TAC");
    }

    @Override
    public void configurePanels() {
//        personPanel.setBackground(View.borderColor);
//
//        pilotPanel.setBackground(View.borderColor);
//
//        hoursTituloPanel.setBackground(View.borderColor);
//
//        appsTituloPanel.setBackground(View.borderColor);

        hoursTituloPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        appsTituloPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));

        hoursPanel.setBorder(new EmptyBorder(0, 20, 0, 0));
        appsPanel.setBorder(new EmptyBorder(0, 20, 0, 0));

    }

    @Override
    public void configureComponents() {
        View.setPreferredSizeForComponents(CardView.HOUR_FIELD_DIMENSION, dayHourField, nightHourField, gvnHourField, realIftHourField, simIftHourField, hdmsHourField, instructorHourField);
        View.setPreferredSizeForComponents(CardView.PERSON_BOX_DIMENSION, personBox);

        View.setPreferredSizeForComponents(APP_FIELD_DIMENSION, ilsField, parField, rnavField, gcaField, vorField, tacField);

        View.setHorizontalAlignmentToFields(dayHourField, nightHourField, gvnHourField, realIftHourField, simIftHourField, hdmsHourField, instructorHourField);
        View.setHorizontalAlignmentToFields(ilsField, parField, rnavField, gcaField, vorField, tacField);
    }

    @Override
    public void assemblePanels() {
        this.add(mainPanel);
        mainPanel.add(personPanel, BorderLayout.WEST);
        mainPanel.add(pilotPanel,BorderLayout.EAST);

        pilotPanel.add(hoursPanel);
        pilotPanel.add(appsPanel);
        //pilotPanel.add(landingsPanel);

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

    }

    @Override
    public void assembleComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(37, 5, 10, 5);
        personPanel.add(personBox,gbc);


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
        View.addComponentsToPanel(appsDataPanel, ilsField, parField, rnavField, gcaField, vorField, tacField);
    }

    @Override
    public void addActionListeners() {

    }

    //Getters
    public PilotCardPresenter getPresenter() {
        return presenter;
    }
}
