package org.jonatancarbonellmartinez.view.RegisterFlightView;

import org.jonatancarbonellmartinez.presenter.RegisterFlightPresenter.RegisterFlightPresenter;
import org.jonatancarbonellmartinez.view.DialogView;
import org.jonatancarbonellmartinez.view.MainView;
import org.jonatancarbonellmartinez.view.View;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Vector;

public class RegisterFlightDialogView extends JDialog implements View, DialogView {

    private MainView mainView;
    private RegisterFlightPresenter presenter;

    private PilotCardView pilotCardView1, pilotCardView2;
    private DvCardView dvCardView1, dvCardView2;

    private ArrayDeque<PilotCardView> extraPilotCardViewDeque;
    private ArrayDeque<DvCardView> extraDvCardViewDeque;

    JSpinner dateTimeSpinner;

    JComboBox heloBox, eventBox, personBox;

    JTextField totalHoursField;

    JLabel pilotoLabel, dotacionLaebl;

    JButton saveButton;
    JButton createPilotButton;

    JButton createDvButton;
    JButton deletePilotButton;
    JButton deleteDvButton;

    JScrollPane tripulantesScrollPane;

    JPanel topPanel, centerPanel, bottomPanel, vueloPanel, tripulantesPanel, createTripulantePanel, crewLabelsPanel, crewButtonsPanel;

    public RegisterFlightDialogView(MainView mainView) {
        super(mainView, "Registrar vuelo");
        this.mainView = mainView;
        this.presenter = new RegisterFlightPresenter(this, mainView.getPresenter());
        this.initializeUI();
        setVisible(true);

        // Request focus on the dateTimeSpinner after the dialog is shown
        SwingUtilities.invokeLater(() -> {
            dateTimeSpinner.requestFocusInWindow();  // Request focus for the JSpinner
        });
    }

    @Override
    public void setupUIProperties() {
        setLayout(new BorderLayout());
        setResizable(false);
        setSize(1400,1000);
        setLocationRelativeTo(mainView);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @Override
    public void createPanels() {
        topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        vueloPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,30,10));
        centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tripulantesPanel = new JPanel();
        tripulantesPanel.setLayout(new BoxLayout(tripulantesPanel, BoxLayout.Y_AXIS));
        tripulantesScrollPane = new JScrollPane(tripulantesPanel);
        bottomPanel = new JPanel();
        createTripulantePanel = new JPanel(new FlowLayout(FlowLayout.LEFT,20,20));
        crewLabelsPanel = new JPanel();
        crewLabelsPanel.setLayout(new BoxLayout(crewLabelsPanel, BoxLayout.Y_AXIS));
        crewButtonsPanel = new JPanel(new GridLayout(2, 3,15,15));
        pilotCardView1 = new PilotCardView(this);
        pilotCardView2 = new PilotCardView(this);
        dvCardView1 = new DvCardView(this);
        dvCardView2 = new DvCardView(this);
    }

    @Override
    public void createComponents() {
        dateTimeSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.MINUTE));
        heloBox = View.createDynamicComboBox(new Vector<>(presenter.getHeloList()),"Helicóptero");
        eventBox = View.createDynamicComboBox(new Vector<>(presenter.getEventList()),"Evento");
        personBox = View.createDynamicComboBox(new Vector<>(presenter.getOnlyActualPilots()),"Cte. Aeronave");
        totalHoursField = View.createTextField("Horas totales",4,13);
        saveButton = new JButton("Guardar");
        pilotoLabel = new JLabel("Piloto");
        createPilotButton = new JButton("+");
        deletePilotButton = new JButton("-");
        dotacionLaebl = new JLabel("Dotación");
        createDvButton = new JButton("+");
        deleteDvButton = new JButton("-");
        extraPilotCardViewDeque = new ArrayDeque<>();
        extraDvCardViewDeque  = new ArrayDeque<>();
    }

    @Override
    public void configurePanels() {
        TitledBorder titleBorder = new TitledBorder("Vuelo");
        titleBorder.setTitleFont(new Font("Segoe UI", Font.PLAIN, 15));
        //titleBorder.setTitleJustification(TitledBorder.CENTER); // In case i want to center the Vuelo title.

        vueloPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 55, 10, 25),
                titleBorder
        ));

        TitledBorder titleBorder2 = new TitledBorder("Tripulantes");
        titleBorder2.setTitleFont(new Font("Segoe UI", Font.PLAIN, 15));

        tripulantesScrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 55, 10, 45),
                titleBorder2
        ));

        pilotCardView1.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        pilotCardView2.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        dvCardView1.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        dvCardView2.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));

        tripulantesScrollPane.setPreferredSize(new Dimension(1375, 185)); // Width and max height
        tripulantesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tripulantesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        createTripulantePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        crewButtonsPanel.setPreferredSize(new Dimension(55, 55));

    }

    @Override
    public void configureComponents() {
        dateTimeSpinner.setEditor(new JSpinner.DateEditor(dateTimeSpinner, "dd/MM/yyyy HH:mm"));
        View.setInitialComboBoxLook(heloBox,eventBox, personBox);
        View.setPreferredSizeForComponents(new Dimension(eventBox.getPreferredSize().width+10, 25), eventBox);
        View.setPreferredSizeForComponents(new Dimension(155,24),dateTimeSpinner,heloBox, personBox, totalHoursField);
        totalHoursField.setHorizontalAlignment(JTextField.CENTER);
        setDocumentFilters();

        // Set a bigger font for the editor
        JSpinner.DateEditor editor = (JSpinner.DateEditor) dateTimeSpinner.getEditor();
        JFormattedTextField textField = editor.getTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
    }

    @Override
    public void assemblePanels() {
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(centerPanel,BorderLayout.CENTER);
        getContentPane().add(bottomPanel,BorderLayout.SOUTH);

        topPanel.add(vueloPanel);
        topPanel.add(createTripulantePanel);
        createTripulantePanel.add(crewLabelsPanel);
        createTripulantePanel.add(crewButtonsPanel);

        centerPanel.add(tripulantesScrollPane);

        tripulantesPanel.add(pilotCardView1);
        tripulantesPanel.add(pilotCardView2);
        tripulantesPanel.add(dvCardView1);
        tripulantesPanel.add(dvCardView2);
    }

    @Override
    public void assembleComponents() {
        View.addComponentsToPanel(vueloPanel,dateTimeSpinner, heloBox, eventBox, personBox, totalHoursField);
        View.addComponentsToPanel(crewButtonsPanel,createPilotButton,deletePilotButton, createDvButton, deleteDvButton);
        View.addComponentsToPanel(bottomPanel,saveButton);
        crewLabelsPanel.add(pilotoLabel);
        crewLabelsPanel.add(Box.createVerticalStrut(10));
        crewLabelsPanel.add(dotacionLaebl);
    }

    @Override
    public void addActionListeners() {
        presenter.setActionListeners();
    }
    @Override
    public void createEditModeComponents() {

    }

    @Override
    public void configureEditModeComponents() {

    }

    @Override
    public void clearFields() {
        View.setDocumentFilter(totalHoursField,13); // This is set because placeholder is larger than value permitted limit.
        heloBox.setSelectedIndex(0);
        eventBox.setSelectedIndex(0);
        personBox.setSelectedIndex(0);
        View.setPlaceholder(totalHoursField,"Horas totales");
    }

    @Override
    public void onEditEntityIdFieldAction() {
        // nothing to do here
    }

    @Override
    public void setDocumentFilters() {
        View.setDocumentFilter(totalHoursField,4);
    }

    public void addExtraPilotCardView() {
        PilotCardView pilotCardView = new PilotCardView(this);
        pilotCardView.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        tripulantesPanel.add(pilotCardView);
        extraPilotCardViewDeque.add(pilotCardView);
        // Ensure the UI updates to reflect the added component
        tripulantesPanel.revalidate();
        tripulantesPanel.repaint();
    }

    public void addExtraDvCardView() {
        DvCardView dvCardView = new DvCardView(this);
        dvCardView.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        tripulantesPanel.add(dvCardView);
        extraDvCardViewDeque.add(dvCardView);
        // Ensure the UI updates to reflect the added component
        tripulantesPanel.revalidate();
        tripulantesPanel.repaint();
    }

    public void deleteExtraPilotCardView() {
        if (!extraPilotCardViewDeque.isEmpty()) {
            // Get and remove the last added PilotCardView from the Deque
            PilotCardView lastPilotCardView = extraPilotCardViewDeque.removeLast();

            // Remove it from the panel
            tripulantesPanel.remove(lastPilotCardView);

            // Revalidate and repaint the panel to reflect the changes
            tripulantesPanel.revalidate();
            tripulantesPanel.repaint();
        }
    }
        public void deleteExtraDvCardView() {
            if (!extraDvCardViewDeque.isEmpty()) {
                // Get and remove the last added PilotCardView from the Deque
                DvCardView lastDvCardView = extraDvCardViewDeque.removeLast();

                // Remove it from the panel
                tripulantesPanel.remove(lastDvCardView);

                // Revalidate and repaint the panel to reflect the changes
                tripulantesPanel.revalidate();
                tripulantesPanel.repaint();
            }
    }

    // GETTERS
    public JComboBox getHeloBox() {
        return heloBox;
    }

    public JComboBox getPersonBox() {
        return personBox;
    }

    public JComboBox getEventBox() {
        return eventBox;
    }

    public JTextField getTotalHoursField() {
        return totalHoursField;
    }

    public String getDateTimeSpinner() { // Logic implemented to return a string with the date and time
        // Retrieve the Date from the JSpinner
        Date date = (Date) dateTimeSpinner.getValue();

        // Format Date to ISO 8601 string format
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = isoFormat.format(date);

        return dateString;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getCreatePilotButton() {
        return createPilotButton;
    }

    public JButton getDeletePilotButton() {
        return deletePilotButton;
    }

    public JButton getCreateDvButton() {
        return createDvButton;
    }

    public JButton getDeleteDvButton() {
        return deleteDvButton;
    }
}
