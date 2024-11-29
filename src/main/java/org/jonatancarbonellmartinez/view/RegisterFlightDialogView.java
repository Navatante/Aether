package org.jonatancarbonellmartinez.view;


import org.jonatancarbonellmartinez.presenter.RegisterFlightPresenter;
import org.jonatancarbonellmartinez.view.panels.CupoHourCardPanel;
import org.jonatancarbonellmartinez.view.panels.DvCrewCardPanel;
import org.jonatancarbonellmartinez.view.panels.PilotCrewCardPanel;
import org.jonatancarbonellmartinez.view.panels.SessionCardPanel;

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

    private PilotCrewCardPanel pilotCardPanel1, pilotCardPanel2;
    private DvCrewCardPanel dvCardPanel1;
    private SessionCardPanel sessionCardPanel;
    private CupoHourCardPanel cupoHourCardPanel1, cupoHourCardPanel2;

    private ArrayDeque<PilotCrewCardPanel> extraPilotCardPanelDeque;
    private ArrayDeque<DvCrewCardPanel> extraDvCardPanelDeque;
    private ArrayDeque<SessionCardPanel> extraSessionCardPanelDeque;
    private ArrayDeque<CupoHourCardPanel> extraCupoHourCardPanelDeque;

    private JSpinner dateTimeSpinner;

    private JComboBox heloBox, eventBox;

    private JTextField totalHoursField;

    private JButton saveButton;

    private JScrollPane hoursAppsLandingsProjectilesScrollPanel, sessionScrollPanel, cupoHourScrollPanel;

    private JPanel topPanel, centerPanel, bottomPanel, vueloPanel, tripulantesPanel;
    private JPanel sessionPanel;
    private JPanel cupoHourPanel;

    JPopupMenu horasAppsTomasProjectilesPopupMenu;
    JMenuItem addPilotItem, deletePilotItem, addDvItem, deleteDvItem;

    JPopupMenu horasCupoPopupMenu;
    JMenuItem addCupoCardItem, deleteCupoCardItem;

    public RegisterFlightDialogView(MainView mainView) {
        super(mainView, "Registrar vuelo",true);
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
        setSize(1280,720);
        setLocationRelativeTo(mainView);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @Override
    public void createPanels() {
        topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        vueloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,100,20));

        centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,60)); // TODO this vgap is the key
        tripulantesPanel = new JPanel();
        tripulantesPanel.setLayout(new BoxLayout(tripulantesPanel, BoxLayout.Y_AXIS));
        hoursAppsLandingsProjectilesScrollPanel = new JScrollPane(tripulantesPanel);
        sessionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sessionPanel.setLayout(new BoxLayout(sessionPanel, BoxLayout.Y_AXIS));
        sessionScrollPanel = new JScrollPane(sessionPanel);

        cupoHourPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cupoHourPanel.setLayout(new BoxLayout(cupoHourPanel, BoxLayout.Y_AXIS));
        cupoHourScrollPanel = new JScrollPane(cupoHourPanel);

        bottomPanel = new JPanel();
        pilotCardPanel1 = new PilotCrewCardPanel(this, presenter);
        pilotCardPanel2 = new PilotCrewCardPanel(this, presenter);
        dvCardPanel1 = new DvCrewCardPanel(this, presenter);
        sessionCardPanel = new SessionCardPanel(this, presenter);
        cupoHourCardPanel1 = new CupoHourCardPanel(this, presenter);
        cupoHourCardPanel2 = new CupoHourCardPanel(this, presenter);
    }

    @Override
    public void createComponents() {
        dateTimeSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.MINUTE));

        heloBox = View.createDynamicComboBox(new Vector<>(presenter.getHeloList()),"Helicóptero");
        eventBox = View.createDynamicComboBox(new Vector<>(presenter.getEventList()),"Evento");
        totalHoursField = View.createTextField("Horas totales",4,13);
        saveButton = new JButton("Guardar");

        extraPilotCardPanelDeque = new ArrayDeque<>();
        extraDvCardPanelDeque = new ArrayDeque<>();
        extraSessionCardPanelDeque = new ArrayDeque<>();
        extraCupoHourCardPanelDeque = new ArrayDeque<>();

        horasAppsTomasProjectilesPopupMenu = new JPopupMenu();
        horasCupoPopupMenu = new JPopupMenu();

        addPilotItem = new JMenuItem("Añadir piloto");
        deletePilotItem = new JMenuItem("Eliminar piloto");
        addDvItem = new JMenuItem("Añadir dotación");
        deleteDvItem = new JMenuItem("Eliminar dotación");

        addCupoCardItem = new JMenuItem("Añadir cupo");
        deleteCupoCardItem = new JMenuItem("Eliminar cupo");
    }

    @Override
    public void configurePanels() {
        TitledBorder titleBorder = new TitledBorder("Vuelo");
        titleBorder.setTitleFont(new Font("Segoe UI", Font.PLAIN, 15));
        //titleBorder.setTitleJustification(TitledBorder.CENTER); // In case i want to center the Vuelo title.

        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        vueloPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 5, 0, 100),
                titleBorder
        ));

        // Horas, tomas, aproximacions y proyectiles
        TitledBorder titleBorder2 = new TitledBorder("Horas, Aproximaciones, Tomas y Proyectiles");
        titleBorder2.setTitleFont(new Font("Segoe UI", Font.PLAIN, 15));

        hoursAppsLandingsProjectilesScrollPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 10, 0, 45),
                titleBorder2
        ));

        // Sesion
        TitledBorder titleBorder3 = new TitledBorder("Sesiones");
        titleBorder3.setTitleFont(new Font("Segoe UI", Font.PLAIN, 15));

        sessionScrollPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 10, 0, 45),
                titleBorder3
        ));

        // Cupo Hour
        TitledBorder titleBorder4 = new TitledBorder("Cupo");
        titleBorder4.setTitleFont(new Font("Segoe UI", Font.PLAIN, 15));

        cupoHourScrollPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 5, 0, 45),
                titleBorder4
        ));

        pilotCardPanel1.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        pilotCardPanel2.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        dvCardPanel1.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        sessionCardPanel.setBorder(new MatteBorder(0,0,1,0,Color.GRAY));
        cupoHourCardPanel1.setBorder(new MatteBorder(0,0,1,0,Color.GRAY));
        cupoHourCardPanel2.setBorder(new MatteBorder(0,0,1,0,Color.GRAY));

        hoursAppsLandingsProjectilesScrollPanel.setPreferredSize(new Dimension(1310, 150));
        hoursAppsLandingsProjectilesScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        hoursAppsLandingsProjectilesScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //JScrollBar verticalScrollBar = tripulantesScrollPane.getVerticalScrollBar();

        sessionScrollPanel.setPreferredSize(new Dimension(395, 150));
        sessionScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sessionScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        cupoHourScrollPanel.setPreferredSize(new Dimension(395, 150));
        cupoHourScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        cupoHourScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);



        pilotCardPanel1.getCrewBox().setToolTipText("Comandante de Aeronave");

        vueloPanel.setPreferredSize(new Dimension(1360,100));

        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0,10,25,10));

        // Añadir un MouseListener al panel para detectar clic derecho
        View.setMouseListenerToPanel(hoursAppsLandingsProjectilesScrollPanel, horasAppsTomasProjectilesPopupMenu);
        View.setMouseListenerToPanel(cupoHourScrollPanel, horasCupoPopupMenu);

    }

    @Override
    public void configureComponents() {
        dateTimeSpinner.setEditor(new JSpinner.DateEditor(dateTimeSpinner, "dd/MM/yyyy HH:mm"));
        View.setInitialComboBoxLook(heloBox,eventBox);
        View.setPreferredSizeForComponents(new Dimension(eventBox.getPreferredSize().width+100, 25), eventBox);
        View.setPreferredSizeForComponents(new Dimension(155,24),dateTimeSpinner,heloBox, totalHoursField);
        totalHoursField.setHorizontalAlignment(JTextField.CENTER);
        setDocumentFilters();

        // Set a bigger font for the editor
        JSpinner.DateEditor editor = (JSpinner.DateEditor) dateTimeSpinner.getEditor();
        JFormattedTextField textField = editor.getTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        saveButton.setPreferredSize(new Dimension(100,25));
    }

    @Override
    public void assemblePanels() {
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(centerPanel,BorderLayout.CENTER);
        getContentPane().add(bottomPanel,BorderLayout.SOUTH);

        topPanel.add(vueloPanel);

        centerPanel.add(hoursAppsLandingsProjectilesScrollPanel);
        centerPanel.add(sessionScrollPanel);
        centerPanel.add(cupoHourScrollPanel);

        tripulantesPanel.add(pilotCardPanel1);
        tripulantesPanel.add(pilotCardPanel2);
        tripulantesPanel.add(dvCardPanel1);

        sessionPanel.add(sessionCardPanel);

        cupoHourPanel.add(cupoHourCardPanel1);
        cupoHourPanel.add(cupoHourCardPanel2);

        horasAppsTomasProjectilesPopupMenu.add(addPilotItem);
        horasAppsTomasProjectilesPopupMenu.add(deletePilotItem);
        horasAppsTomasProjectilesPopupMenu.addSeparator();
        horasAppsTomasProjectilesPopupMenu.add(addDvItem);
        horasAppsTomasProjectilesPopupMenu.add(deleteDvItem);

        horasCupoPopupMenu.add(addCupoCardItem);
        horasCupoPopupMenu.add(deleteCupoCardItem);
    }

    @Override
    public void assembleComponents() {
        View.addComponentsToPanel(vueloPanel,dateTimeSpinner, heloBox, eventBox, totalHoursField);
        View.addComponentsToPanel(bottomPanel,saveButton);
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
        PilotCrewCardPanel pilotCardPanel = new PilotCrewCardPanel(this, presenter);
        pilotCardPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        tripulantesPanel.add(pilotCardPanel);
        extraPilotCardPanelDeque.add(pilotCardPanel);
        // Ensure the UI updates to reflect the added component
        tripulantesPanel.revalidate();
        tripulantesPanel.repaint();
    }

    public void addExtraDvCardView() {
        DvCrewCardPanel dvCardPanel = new DvCrewCardPanel(this, presenter);
        dvCardPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        tripulantesPanel.add(dvCardPanel);
        extraDvCardPanelDeque.add(dvCardPanel);
        // Ensure the UI updates to reflect the added component
        tripulantesPanel.revalidate();
        tripulantesPanel.repaint();
    }

    public void addExtraSessionCardView() {
        SessionCardPanel sessionCardPanel = new SessionCardPanel(this, presenter);
        sessionCardPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        sessionPanel.add(sessionCardPanel);
        extraSessionCardPanelDeque.add(sessionCardPanel);
        presenter.setCardSessionActionListener(sessionCardPanel); // TODO
        // Ensure the UI updates to reflect the added component
        sessionPanel.revalidate();
        sessionPanel.repaint();
    }

    public void addExtraCupoHourCardView() {
        CupoHourCardPanel cupoHourCardPanel = new CupoHourCardPanel(this, presenter);
        cupoHourCardPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        cupoHourPanel.add(cupoHourCardPanel);
        extraCupoHourCardPanelDeque.add(cupoHourCardPanel);
        // Ensure the UI updates to reflect the added component
        cupoHourPanel.revalidate();
        cupoHourPanel.repaint();
    }

    public void deleteExtraPilotCardView() {
        if (!extraPilotCardPanelDeque.isEmpty()) {
            // Get and remove the last added PilotCardView from the Deque
            PilotCrewCardPanel lastPilotCardPanel = extraPilotCardPanelDeque.removeLast();

            // Remove it from the panel
            tripulantesPanel.remove(lastPilotCardPanel);

            // Revalidate and repaint the panel to reflect the changes
            tripulantesPanel.revalidate();
            tripulantesPanel.repaint();
        }
    }

    public void deleteExtraDvCardView() {
            if (!extraDvCardPanelDeque.isEmpty()) {
                // Get and remove the last added PilotCardView from the Deque
                DvCrewCardPanel lastDvCardPanel = extraDvCardPanelDeque.removeLast();

                // Remove it from the panel
                tripulantesPanel.remove(lastDvCardPanel);

                // Revalidate and repaint the panel to reflect the changes
                tripulantesPanel.revalidate();
                tripulantesPanel.repaint();
            }
    }

    public void deleteExtraSessionCardView() {
        if (!extraSessionCardPanelDeque.isEmpty()) {
            // Get and remove the last added PilotCardView from the Deque
            SessionCardPanel lastSessionCardPanel = extraSessionCardPanelDeque.removeLast();

            // Remove it from the panel
            sessionPanel.remove(lastSessionCardPanel);

            // Revalidate and repaint the panel to reflect the changes
            sessionPanel.revalidate();
            sessionPanel.repaint();
        }
    }

    public void deleteExtraCupoHourCardView() {
        if (!extraCupoHourCardPanelDeque.isEmpty()) {
            // Get and remove the last added PilotCardView from the Deque
            CupoHourCardPanel lastCupoHourCardPanel = extraCupoHourCardPanelDeque.removeLast();

            // Remove it from the panel
            cupoHourPanel.remove(lastCupoHourCardPanel);

            // Revalidate and repaint the panel to reflect the changes
            cupoHourPanel.revalidate();
            cupoHourPanel.repaint();
        }
    }

    // GETTERS
    public JComboBox getHeloBox() {
        return heloBox;
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

    public PilotCrewCardPanel getPilotCardPanel1() {
        return pilotCardPanel1;
    }

    public PilotCrewCardPanel getPilotCardPanel2() {
        return pilotCardPanel2;
    }

    public DvCrewCardPanel getDvCardPanel1() {
        return dvCardPanel1;
    }

    public ArrayDeque<PilotCrewCardPanel> getExtraPilotCardPanelDeque() {
        return extraPilotCardPanelDeque;
    }

    public ArrayDeque<DvCrewCardPanel> getExtraDvCardPanelDeque() {
        return extraDvCardPanelDeque;
    }

    public SessionCardPanel getSessionCardPanel() {
        return sessionCardPanel;
    }

    public JMenuItem getAddPilotItem() {
        return addPilotItem;
    }

    public JMenuItem getDeletePilotItem() {
        return deletePilotItem;
    }

    public JMenuItem getAddDvItem() {
        return addDvItem;
    }

    public JMenuItem getDeleteDvItem() {
        return deleteDvItem;
    }

    public JScrollPane getHoursAppsLandingsProjectilesScrollPanel() {
        return hoursAppsLandingsProjectilesScrollPanel;
    }

    public JPopupMenu getHorasAppsTomasProjectilesPopupMenu() {
        return horasAppsTomasProjectilesPopupMenu;
    }

    public ArrayDeque<SessionCardPanel> getExtraSessionCardPanelDeque() {
        return extraSessionCardPanelDeque;
    }

    public ArrayDeque<CupoHourCardPanel> getExtraCupoHourCardPanelDeque() {
        return extraCupoHourCardPanelDeque;
    }

    public MainView getMainView() {
        return mainView;
    }

    public CupoHourCardPanel getCupoHourCardPanel1() {
        return cupoHourCardPanel1;
    }

    public CupoHourCardPanel getCupoHourCardPanel2() {
        return cupoHourCardPanel2;
    }

    public JMenuItem getAddCupoCardItem() {
        return addCupoCardItem;
    }

    public JMenuItem getDeleteCupoCardItem() {
        return deleteCupoCardItem;
    }
}
