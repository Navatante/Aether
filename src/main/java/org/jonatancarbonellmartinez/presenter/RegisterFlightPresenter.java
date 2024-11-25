package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.factory.DAOFactorySQLite;
import org.jonatancarbonellmartinez.model.dao.*;
import org.jonatancarbonellmartinez.model.entities.*;
import org.jonatancarbonellmartinez.observers.Observer;
import org.jonatancarbonellmartinez.view.DialogView;
import org.jonatancarbonellmartinez.view.RegisterFlightDialogView;
import org.jonatancarbonellmartinez.view.panels.CardPanel;
import org.jonatancarbonellmartinez.view.panels.DvCardPanel;
import org.jonatancarbonellmartinez.view.panels.PilotCardPanel;
import org.jonatancarbonellmartinez.view.panels.SessionCardPanel;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class RegisterFlightPresenter implements Presenter, DialogPresenter {

    private final HeloDAOSQLite heloDAO;
    private final EventDAOSQLite eventDAO;
    private final PersonDAOSQLite personDAO;
    private final FlightDAOSQLite flightDAO;
    private final PersonHourDAOSQLite personHourDAO;
    private final IftHourDAOSqlite iftHourDAO;
    private final InstructorHourDAOSqlite instructorHourDAO;
    private final HdmsHourDAOSqlite hdmsHourDAO;
    private final AppDAOSqlite appDAO;
    private final LandingDAOSqlite landingDAO;
    private final WtHourDAOSqlite wtHourDAO;
    private final ProjectileDAOSqlite projectileDAO;
    private final SessionDAOSqlite sessionDAO;
    private final RegisterFlightDialogView view;
    private final Observer observer;

    private int lastFlightSk;

    private ArrayList<CardPanel> allCrewCardPanels;
    private ArrayList<PilotCardPanel> allPilotCardPanels;
    private ArrayList<DvCardPanel> allDvCardPanels;
    private ArrayList<SessionCardPanel> allSessionCardPanels;

    private Vector<Entity> allPilotsVector, allDvsVector, allPersonsVector, allSessionsVector;

    public RegisterFlightPresenter(RegisterFlightDialogView registerFlightDialogView, Observer observer) {
        this.view = registerFlightDialogView;
        this.heloDAO = DAOFactorySQLite.getInstance().createHeloDAO();
        this.eventDAO = DAOFactorySQLite.getInstance().createEventDAO();
        this.personDAO = DAOFactorySQLite.getInstance().createPersonDAO();
        this.flightDAO = DAOFactorySQLite.getInstance().createFlightDAO();
        this.personHourDAO = DAOFactorySQLite.getInstance().createPersonHourDAO();
        this.iftHourDAO = DAOFactorySQLite.getInstance().createIftHourDAO();
        this.instructorHourDAO = DAOFactorySQLite.getInstance().createInstructorHourDAO();
        this.hdmsHourDAO = DAOFactorySQLite.getInstance().createHdmsHourDAO();
        this.appDAO = DAOFactorySQLite.getInstance().createAppDAO();
        this.landingDAO = DAOFactorySQLite.getInstance().createLandingDAO();
        this.wtHourDAO = DAOFactorySQLite.getInstance().createWtHourDAO();
        this.projectileDAO = DAOFactorySQLite.getInstance().createProjectileDAO();
        this.sessionDAO = DAOFactorySQLite.getInstance().createSessionDAO();
        createVectors();
        this.observer = observer;
    }

    @Override
    public boolean isFormValid() {
        boolean isValid = isVueloCardValid() &&
                            areCrewCardsValid();
        return isValid;
    }

    @Override
    public void insertEntity() { // TODO quiza este llame a todos los metodos de insertX
        try {
            insertFlight();
            collectCrewCardPanels();
            collectPilotCardPanels();
            collectDvCardPanels();
            collectSessionCardPanels();
            insertPersonHour();
            insertIftHour();
            insertHdmsHour();
            insertInstructorHour();
            insertApp();
            insertLanding();
            insertWtHour();
            insertProjectile();
            // add more insert methods.
            DialogView.showMessage(view,"Vuelo añadido correctamente.");

            view.clearFields();

        } catch (DatabaseException ex) {
            DialogView.showError(view,"Error al añadir vuelo: ");
        } catch (Exception ex) {
            DialogPresenter.handleUnexpectedError(ex,view);
        }
    }

    public void insertFlight() {
        flightDAO.insert(collectFlightData());
        lastFlightSk = flightDAO.getLastFlightSk(); // Al meter el vuelo en la base de datos, del tiron lo asigno a la variable para utilizarla en otros metodos.
    }

    private void collectCrewCardPanels() {
        // Create a list of all pilot card panels to iterate through, including extra dynamically added ones
        allCrewCardPanels = new ArrayList<>();

        // Add the predefined panels
        allCrewCardPanels.add(view.getPilotCardPanel1());
        allCrewCardPanels.add(view.getPilotCardPanel2());
        allCrewCardPanels.add(view.getDvCardPanel1());

        // Add dynamically added panels from the deque
        allCrewCardPanels.addAll(view.getExtraPilotCardPanelDeque());
        allCrewCardPanels.addAll(view.getExtraDvCardPanelDeque());
    }

    private void collectPilotCardPanels() {
        allPilotCardPanels = new ArrayList<>();

        allPilotCardPanels.add(view.getPilotCardPanel1());
        allPilotCardPanels.add(view.getPilotCardPanel2());
        allPilotCardPanels.addAll(view.getExtraPilotCardPanelDeque());
    }

    private void collectDvCardPanels() {
        allDvCardPanels = new ArrayList<>();

        allDvCardPanels.add(view.getDvCardPanel1());
        allDvCardPanels.addAll(view.getExtraDvCardPanelDeque());
    }

    private void collectSessionCardPanels() {
        allSessionCardPanels = new ArrayList<>();

        allSessionCardPanels.add(view.getSessionCardPanel());
        allSessionCardPanels.addAll(view.getExtraSessionCardPanelDeque());
    }

    public void insertPersonHour() {
        PersonHour personHour = new PersonHour();
        personHour.setFlightFk(lastFlightSk);
        // Iterate over all the pilot card panels
        for (CardPanel crewCardPanel : allCrewCardPanels) {
            // Iterate over all periods (Day, Night, Gvn) and corresponding hour fields
            for (int periodFk = 1; periodFk <= 3; periodFk++) {
                String hourFieldText = getPeriodHourFieldText(crewCardPanel, periodFk);
                String defaultValue = getDefaultPeriodValue(periodFk);

                // Only insert if the field is not equal to the default value
                if (!hourFieldText.equals(defaultValue)) {
                    personHour.setPersonFk(getForeignKey(crewCardPanel.getCrewBox().getSelectedItem()));
                    personHour.setPeriodFk(periodFk);
                    personHour.setHourQty(Double.parseDouble(hourFieldText));

                    // Insert the person hour into the database
                    personHourDAO.insert(personHour);
                }
            }
        }
    } // TODO Study this method

    // Helper method of insertPersonHour() to get the hour field text based on the period
    private String getPeriodHourFieldText(CardPanel CardPanel, int periodFk) {
        switch (periodFk) {
            case 1: // Day
                return CardPanel.getDayHourField().getText();
            case 2: // Night
                return CardPanel.getNightHourField().getText();
            case 3: // Gvn
                return CardPanel.getGvnHourField().getText();
            default:
                return "";
        }
    }

    // Helper method of insertPersonHour() to get the default period value
    private String getDefaultPeriodValue(int periodFk) {
        switch (periodFk) {
            case 1: return "D"; // Day
            case 2: return "N"; // Night
            case 3: return "G"; // Gvn
            default: return "";
        }
    }

    // Helper method of insertApp() to get the app field text based on the type
    private String getAppTypeFieldText(PilotCardPanel pilotCardPanel, int appTypeFk) {
        switch (appTypeFk) {
            case 1: // Precision
                return pilotCardPanel.getPrecisionField().getText();
            case 2: // No precision
                return pilotCardPanel.getNoPrecisionField().getText();
            case 3: // SAR-N
                return pilotCardPanel.getSarnField().getText();
            default:
                return "";
        }
    }

    // Helper method of insertApp() to get the default app type value
    private String getDefaultAppTypeValue(int appTypeFk) {
        switch (appTypeFk) {
            case 1: return "P"; // Precision
            case 2: return "N"; // No precision
            case 3: return "S"; // SAR-N
            default: return "";
        }
    }

    // Helper method of insertPersonHour() to get the hour field text based on the period
    private String getPeriodMonoLandingFieldText(PilotCardPanel pilotCardPanel, int periodFk) {
        switch (periodFk) {
            case 1: // Day
                return pilotCardPanel.getMonoDayField().getText();
            case 2: // Night
                return pilotCardPanel.getMonoNightField().getText();
            case 3: // Gvn
                return pilotCardPanel.getMonoGvnField().getText();
            default:
                return "";
        }
    }

    // Helper method of insertPersonHour() to get the hour field text based on the period
    private String getPeriodMultiLandingFieldText(PilotCardPanel pilotCardPanel, int periodFk) {
        switch (periodFk) {
            case 1: // Day
                return pilotCardPanel.getMultiDayField().getText();
            case 2: // Night
                return pilotCardPanel.getMultiNightField().getText();
            case 3: // Gvn
                return pilotCardPanel.getMultiGvnField().getText();
            default:
                return "";
        }
    }

    // Helper method of insertPersonHour() to get the hour field text based on the period
    private String getPeriodTierraLandingFieldText(PilotCardPanel pilotCardPanel, int periodFk) {
        switch (periodFk) {
            case 1: // Day
                return pilotCardPanel.getTierraDayField().getText();
            case 2: // Night
                return pilotCardPanel.getTierraNightField().getText();
            case 3: // Gvn
                return pilotCardPanel.getTierraGvnField().getText();
            default:
                return "";
        }
    }

    private void insertIftHour() {
        IftHour iftHour = new IftHour();
        iftHour.setFlightFk(lastFlightSk);
        for (PilotCardPanel pilotCardPanel : allPilotCardPanels) {
            String iftHourField = pilotCardPanel.getIftHourField().getText();

            // Skip processing if the iftHourField is "I"
            if ("I".equals(iftHourField)) {
                continue;
            }

            // Process the valid IftHourField values
            iftHour.setPersonFk(getForeignKey(pilotCardPanel.getCrewBox().getSelectedItem()));
            iftHour.setIftHourQty(Double.parseDouble(iftHourField));

            iftHourDAO.insert(iftHour);
        }
    }

    private void insertHdmsHour() {
        HdmsHour hdmsHour = new HdmsHour();
        hdmsHour.setFlightFk(lastFlightSk);
        for (PilotCardPanel pilotCardPanel : allPilotCardPanels) {
            String hdmsHourField = pilotCardPanel.getHdmsHourField().getText();

            // Skip processing if the iftHourField is "I"
            if ("H".equals(hdmsHourField)) {
                continue;
            }

            // Process the valid IftHourField values
            hdmsHour.setPersonFk(getForeignKey(pilotCardPanel.getCrewBox().getSelectedItem()));
            hdmsHour.setHdmsHourQty(Double.parseDouble(hdmsHourField));

            hdmsHourDAO.insert(hdmsHour);
        }
    }

    private void insertInstructorHour() {
        InstructorHour instructorHour = new InstructorHour();
        instructorHour.setFlightFk(lastFlightSk);
        for (PilotCardPanel pilotCardPanel : allPilotCardPanels) {
            String instructorHourField = pilotCardPanel.getInstructorHourField().getText();

            // Skip processing if the iftHourField is "I"
            if ("I".equals(instructorHourField)) {
                continue;
            }

            // Process the valid IftHourField values
            instructorHour.setPersonFk(getForeignKey(pilotCardPanel.getCrewBox().getSelectedItem()));
            instructorHour.setInstructorHourQty(Double.parseDouble(instructorHourField));

            instructorHourDAO.insert(instructorHour);
        }
    }

    private void insertApp() {
        App app = new App();
        app.setFlightFk(lastFlightSk);
        // Iterate over all the pilot card panels
        for (PilotCardPanel pilotCardPanel : allPilotCardPanels) {
            // Iterate over all app types (Precision, No precision, SAR-N) and corresponding hour fields
            for (int appTypeFk = 1; appTypeFk <= 3; appTypeFk++) {
                String appTypeFieldText = getAppTypeFieldText(pilotCardPanel, appTypeFk);
                String defaultValue = getDefaultAppTypeValue(appTypeFk);

                // Only insert if the field is not equal to the default value
                if (!appTypeFieldText.equals(defaultValue)) {
                    app.setPersonFk(getForeignKey(pilotCardPanel.getCrewBox().getSelectedItem()));
                    app.setAppTypeFk(appTypeFk);
                    app.setAppQty(Integer.parseInt(appTypeFieldText));

                    // Insert the person hour into the database
                    appDAO.insert(app);
                }
            }
        }
    }

    private void insertLanding() {
        Landing landing = new Landing();
        landing.setFlightFk(lastFlightSk);

        // Iterate over all the pilot card panels
        for (PilotCardPanel pilotCardPanel : allPilotCardPanels) {
            // Iterate over all periods (Day, Night, Gvn) and corresponding hour fields
            for (int periodFk = 1; periodFk <= 3; periodFk++) {
                String monoLandingFieldText = getPeriodMonoLandingFieldText(pilotCardPanel, periodFk);
                String multiLandingFieldText = getPeriodMultiLandingFieldText(pilotCardPanel, periodFk);
                String tierraLandingFieldText = getPeriodTierraLandingFieldText(pilotCardPanel, periodFk);
                String defaultValue = getDefaultPeriodValue(periodFk);

                landing.setPersonFk(getForeignKey(pilotCardPanel.getCrewBox().getSelectedItem()));
                landing.setPeriodFk(periodFk);

                // Only insert if the field is not equal to the default value
                if (!monoLandingFieldText.equals(defaultValue)) {
                    landing.setPlaceFk(1);
                    landing.setLandingQty(Integer.parseInt(monoLandingFieldText));
                    landingDAO.insert(landing);
                }
                if (!multiLandingFieldText.equals(defaultValue)) {
                    landing.setPlaceFk(2);
                    landing.setLandingQty(Integer.parseInt(multiLandingFieldText));
                    landingDAO.insert(landing);
                }
                if (!tierraLandingFieldText.equals(defaultValue)) {
                    landing.setPlaceFk(3);
                    landing.setLandingQty(Integer.parseInt(tierraLandingFieldText));
                    landingDAO.insert(landing);
                }
            }
        }
    }

    private void insertWtHour() {
        WtHour wtHour = new WtHour();
        wtHour.setFlightFk(lastFlightSk);

        for (DvCardPanel dvCardPanel : allDvCardPanels) {
            String wtHourField = dvCardPanel.getWinchTrimHourField().getText();

            // Skip processing if the iftHourField is "I"
            if ("W".equals(wtHourField)) {
                continue;
            }
            wtHour.setPersonFk(getForeignKey(dvCardPanel.getCrewBox().getSelectedItem()));
            wtHour.setWtHourQty(Double.parseDouble(wtHourField));
            wtHourDAO.insert(wtHour);
        }
    }

    private void insertProjectile() {
        Projectile projectile = new Projectile();
        projectile.setFlightFk(lastFlightSk);

        for (DvCardPanel dvCardPanel : allDvCardPanels) {
            String projectileM3MField = dvCardPanel.getM3mField().getText();
            String projectileMAGField = dvCardPanel.getMagField().getText();

            projectile.setPersonFk(getForeignKey(dvCardPanel.getCrewBox().getSelectedItem()));

            // Process M3M field
            if (!"P".equals(projectileM3MField)) {
                projectile.setProjectileTypeFk(1); // 1 points to 7.62 M3M
                projectile.setProjectileQty(Integer.parseInt(projectileM3MField));
                projectileDAO.insert(projectile);
            }

            // Process MAG field
            if (!"P".equals(projectileMAGField)) {
                projectile.setProjectileTypeFk(2); // 2 points to 12.7 MAG58
                projectile.setProjectileQty(Integer.parseInt(projectileMAGField));
                projectileDAO.insert(projectile);
            }
        }
    }


    @Override
    public void editEntity() {
        // de momento nada aqui
    }

    @Override
    public void getEntity(int entityId) {
        // de momento nada aqui
    }

    @Override
    public void onSaveButtonClicked() {
        if (isFormValid()) {
            insertEntity();
        }
    }

    public void onAddPilotoItemClicked() {
        view.addExtraPilotCardView();
    }

    public void onDeletePilotoItemClicked() {
        view.deleteExtraPilotCardView();
    }

    public void onAddDvItemClicked() {
       view.addExtraDvCardView();
    }

    public void onDeleteDvItemClicked() {
        view.deleteExtraDvCardView();
    }

    public void onAddPersonItemClicked() {
        view.getSessionCardPanel().addExtraPersonBox();
    }

    public void onDeletePersonItemClicked() {
        view.getSessionCardPanel().deleteExtraPersonBox();
    }

    public void onAddSessionItemClicked() {
        view.getSessionCardPanel().addExtraSessionBox();
    }

    public void onDeleteSessionItemClicked() {
        view.getSessionCardPanel().deleteExtraSessionBox();
    }

    public void onAddGroupItemClicked() {
        view.addExtraSessionCardView();
    }

    public void onDeleteGroupItemClicked() {
        view.deleteExtraSessionCardView();
    }

    @Override
    public Entity collectEntityData() { // TODO this will be the method who will manage all CollectXData method created in the methods below.
        // Maybe simply i just dont need it.
        return null;
    }

    private Flight collectFlightData() {
        Flight flight = new Flight();

        flight.setDateTime(view.getDateTimeSpinner());
        flight.setHelo(getForeignKey(view.getHeloBox().getSelectedItem()));
        flight.setEvent(getForeignKey(view.getEventBox().getSelectedItem()));
        flight.setPersonCta(getForeignKey(view.getPilotCardPanel1().getCrewBox().getSelectedItem()));
        flight.setTotalHours(Double.parseDouble(view.getTotalHoursField().getText()));

        return flight;
    }

    public Integer getForeignKey(Object selectedItem) {

        if (selectedItem instanceof Entity) {
            Entity selectedEntity = (Entity) selectedItem;
            return selectedEntity.getSk();  // Ensure this matches the actual method name in Entity
        }
        return null;  // Return null if no valid entity is selected
    }

    @Override
    public void populateEntityDialog(Entity entity) {
        // NOT USE HERE, THIS IS FOR EDIT MODE
    }

    @Override
    public void notifyObserver() {

    }

    @Override
    public void setActionListeners() {
        view.getSaveButton().addActionListener(e -> onSaveButtonClicked());
        view.getAddPilotItem().addActionListener( e -> onAddPilotoItemClicked());
        view.getDeletePilotItem().addActionListener( e -> onDeletePilotoItemClicked());
        view.getAddDvItem().addActionListener( e -> onAddDvItemClicked());
        view.getDeleteDvItem().addActionListener( e -> onDeleteDvItemClicked());
        view.getAddPersonItem().addActionListener(e -> onAddPersonItemClicked());
        view.getDeletePersonItem().addActionListener(e -> onDeletePersonItemClicked());
        view.getAddSessionItem().addActionListener(e -> onAddSessionItemClicked());
        view.getDeleteSessionItem().addActionListener(e -> onDeleteSessionItemClicked());
        view.getAddGroupItem().addActionListener(e -> onAddGroupItemClicked());
        view.getDeleteGroupItem().addActionListener(e -> onDeleteGroupItemClicked());
    }

    public List<Helo> getHeloList() {
        return heloDAO.getAll();
    }

    public List<Event> getEventList() {
        return eventDAO.getAll();
    }

    public List<Person> getAllPersons() {
        return personDAO.getAll();
    }

    public List<Person> getOnlyActualPilots() {
        return  personDAO.getOnlyActualPilots();
    }

    public List<Person> getOnlyActualDvs() {
        return  personDAO.getOnlyActualDvs();
    }

    public List<Session> getAllSessions() {
        return sessionDAO.getAll();
    }

    private boolean isVueloCardValid() {
        boolean isValid = DialogPresenter.validateDynamicComboBox(view, view.getHeloBox(),"Helicóptero") &&
                            DialogPresenter.validateDynamicComboBox(view, view.getEventBox(),"Evento") &&
                            DialogPresenter.isAValidMandatoryHour(view, view.getTotalHoursField(),"Horas totales");
        return isValid;
    }

    private boolean areCrewCardsValid() {
        boolean isValid = arePilotsSelected() &&
                            selectedPilotsAreNotRepeated() &&
                            isAnyFlightHourInsertedPerPilotCard() &&
                            arePilotCardsHoursValid() &&
                            doesTotalHoursEqualsSumOfPilotHours() &&
                            areDvsSelected() &&
                            selectedDvsAreNotRepeated() &&
                            isAnyFlightHourInsertedPerDvCard() &&
                            areDvCardsHoursValid();
                            //doesTotalHoursEqualsSumOfDvHours(), i think i dont need this method, but we will see.
        return isValid;
    }

    private boolean arePilotsSelected() {
        // Validate pilot boxes for primary and secondary pilots
        boolean isPilot1Selected = DialogPresenter.validateDynamicComboBox(view, view.getPilotCardPanel1().getCrewBox(), "Primer PIL");
        boolean isPilot2Selected = DialogPresenter.validateDynamicComboBox(view, view.getPilotCardPanel2().getCrewBox(), "Segundo PIL");

        // Validate all extra pilot boxes
        boolean areExtraPilotsSelected = view.getExtraPilotCardPanelDeque()
                .stream()
                .allMatch(panel -> DialogPresenter.validateDynamicComboBox(view, panel.getCrewBox(), "Extra PIL"));

        // Return true only if all validations pass
        return isPilot1Selected && isPilot2Selected && areExtraPilotsSelected;
    }

    private boolean selectedPilotsAreNotRepeated() {
        // Collect all selected pilots from primary, secondary, and extra pilot panels
        Set<String> selectedPilots = new HashSet<>();

        // Check primary and secondary pilot boxes
        String pilot1 = view.getPilotCardPanel1().getCrewBox().getSelectedItem().toString();
        String pilot2 = view.getPilotCardPanel2().getCrewBox().getSelectedItem().toString();

        // Add pilots to the set if they are not null or empty
        if (pilot1 != null && !pilot1.isEmpty()) {
            selectedPilots.add(pilot1);
        }
        if (pilot2 != null && !pilot2.isEmpty()) {
            selectedPilots.add(pilot2);
        }

        // Check extra pilot combo boxes
        view.getExtraPilotCardPanelDeque().forEach(panel -> {
            String extraPilot = panel.getCrewBox().getSelectedItem().toString();
            if (extraPilot != null && !extraPilot.isEmpty()) {
                selectedPilots.add(extraPilot);
            }
        });

        // If the number of selected pilots is equal to the number of entries in the set,
        // it means no duplicates were found
        if (selectedPilots.size() != (view.getExtraPilotCardPanelDeque().size() + 2)) {
            // Show error message if duplicate pilots are found
            JOptionPane.showMessageDialog(view,
                    "Hay pilotos repetidos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
        return true;
    }

    private boolean isAnyFlightHourInsertedPerPilotCard() {

        boolean isPilotCardPanel1Default = isAtLeastOneHourFieldInserted(view.getPilotCardPanel1());
        boolean isPilotCardPanel2Default = isAtLeastOneHourFieldInserted(view.getPilotCardPanel2());
        boolean allDynamicDefault = view.getExtraPilotCardPanelDeque()
                .stream()
                .allMatch(this::isAtLeastOneHourFieldInserted);

        if (!isPilotCardPanel1Default || !isPilotCardPanel2Default || !allDynamicDefault) {
            JOptionPane.showMessageDialog(view, "Inserte al menos una hora de vuelo", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean isAtLeastOneHourFieldInserted(CardPanel panel) {
        return !panel.getDayHourField().getText().equals("D") ||
                !panel.getNightHourField().getText().equals("N") ||
                !panel.getGvnHourField().getText().equals("G");
    }

    private boolean doesTotalHoursEqualsSumOfPilotHours() {
        BigDecimal totalHours = parseBigDecimalOrZero(view.getTotalHoursField().getText());

        // Sum hours for the aircraft commander
        BigDecimal sumOfAircraftCommanderHours = parseBigDecimalOrZero(view.getPilotCardPanel1().getDayHourField().getText())
                .add(parseBigDecimalOrZero(view.getPilotCardPanel1().getNightHourField().getText()))
                .add(parseBigDecimalOrZero(view.getPilotCardPanel1().getGvnHourField().getText()));

        // Sum hours for the other pilots
        BigDecimal sumOfOtherPilotsHours = parseBigDecimalOrZero(view.getPilotCardPanel2().getDayHourField().getText())
                .add(parseBigDecimalOrZero(view.getPilotCardPanel2().getNightHourField().getText()))
                .add(parseBigDecimalOrZero(view.getPilotCardPanel2().getGvnHourField().getText()));

        for (PilotCardPanel extraPanel : view.getExtraPilotCardPanelDeque()) {
            sumOfOtherPilotsHours = sumOfOtherPilotsHours.add(parseBigDecimalOrZero(extraPanel.getDayHourField().getText()))
                    .add(parseBigDecimalOrZero(extraPanel.getNightHourField().getText()))
                    .add(parseBigDecimalOrZero(extraPanel.getGvnHourField().getText()));
        }

        // Validate `totalHours` matches each sum separately
        boolean matchesAircraftCommander = totalHours.equals(sumOfAircraftCommanderHours);
        boolean matchesOtherPilots = totalHours.equals(sumOfOtherPilotsHours);

        if (!matchesAircraftCommander || !matchesOtherPilots) {
            JOptionPane.showMessageDialog(view,
                    "Las horas totales del vuelo no coinciden con las horas de los pilotos:\n"
                            + (matchesAircraftCommander ? "" : " - Horas del Comandante de Aeronave no coinciden.\n")
                            + (matchesOtherPilots ? "" : " - Horas de los copilotos no coinciden.\n"),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    // Helper method to safely parse a double, treating non-numeric values as 0
    private BigDecimal parseBigDecimalOrZero(String text) {
        try {
            return new BigDecimal(text).setScale(1, RoundingMode.HALF_UP);
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO.setScale(1, RoundingMode.HALF_UP);
        }
    }

    private boolean arePilotCardsHoursValid() {
        // Gather all PilotCardPanels
        ArrayList<PilotCardPanel> pilotCardPanelList = new ArrayList<>();
        pilotCardPanelList.add(view.getPilotCardPanel1());
        pilotCardPanelList.add(view.getPilotCardPanel2());
        pilotCardPanelList.addAll(view.getExtraPilotCardPanelDeque());

        // Validate each PilotCardPanel
        for (PilotCardPanel panel : pilotCardPanelList) {
            if (!validatePilotCardPanel(panel)) {
                return false; // Return false immediately if any panel is invalid
            }
        }
        return true;
    }

    // Validates a single PilotCardPanel
    private boolean validatePilotCardPanel(PilotCardPanel panel) {
        String crewName = panel.getCrewBox().getSelectedItem().toString();

        // Validate "Horas" fields
        if (!DialogPresenter.isAValidOptionalHour(view, panel.getDayHourField(), crewName + " Horas Vuelo Dia", "D") ||
                !DialogPresenter.isAValidOptionalHour(view, panel.getNightHourField(), crewName + " Horas Vuelo Noche", "N") ||
                !DialogPresenter.isAValidOptionalHour(view, panel.getGvnHourField(), crewName + " Horas Vuelo GVN", "G") ||
                !DialogPresenter.isAValidOptionalHour(view, panel.getIftHourField(), crewName + " Horas Instrumentos", "I") ||
                !DialogPresenter.isAValidOptionalHour(view, panel.getHdmsHourField(), crewName + " Horas HDMS", "H") ||
                !DialogPresenter.isAValidOptionalHour(view, panel.getInstructorHourField(), crewName + " Horas Instructor", "I")) {
            return false;
        }

        // Validate "Precision" fields
        if (!DialogPresenter.isAValidOptionalNumber(view, panel.getPrecisionField(), crewName + " Aproximación de Precisión", "P") ||
                !DialogPresenter.isAValidOptionalNumber(view, panel.getNoPrecisionField(), crewName + " Aproximación de No precisión", "N") ||
                !DialogPresenter.isAValidOptionalNumber(view, panel.getSarnField(), crewName + " Aproximación SAR-N", "S")) {
            return false;
        }

        // Validate "Tomas" fields
        if (!validateTomasFields(panel, crewName)) {
            return false;
        }

        return true;
    }

    // Helper method for validating "Tomas" fields
    private boolean validateTomasFields(PilotCardPanel panel, String crewName) {
        return
                DialogPresenter.isAValidOptionalNumber(view, panel.getMonoDayField(), crewName + " Toma Monospot Día", "D") &&
                        DialogPresenter.isAValidOptionalNumber(view, panel.getMonoNightField(), crewName + " Toma Monospot Noche", "N") &&
                        DialogPresenter.isAValidOptionalNumber(view, panel.getMonoGvnField(), crewName + " Toma Monospot GVN", "G") &&
                        DialogPresenter.isAValidOptionalNumber(view, panel.getMultiDayField(), crewName + " Toma Multispot Día", "D") &&
                        DialogPresenter.isAValidOptionalNumber(view, panel.getMultiNightField(), crewName + " Toma Multispot Noche", "N") &&
                        DialogPresenter.isAValidOptionalNumber(view, panel.getMultiGvnField(), crewName + " Toma Multispot GVN", "G") &&
                        DialogPresenter.isAValidOptionalNumber(view, panel.getTierraDayField(), crewName + " Toma Tierra Día", "D") &&
                        DialogPresenter.isAValidOptionalNumber(view, panel.getTierraNightField(), crewName + " Toma Tierra Noche", "N") &&
                        DialogPresenter.isAValidOptionalNumber(view, panel.getTierraGvnField(), crewName + " Toma Tierra GVN", "G");
    }

    private boolean areDvsSelected() {
        // Validate pilot boxes for primary and secondary pilots
        boolean isDv1Selected = DialogPresenter.validateDynamicComboBox(view, view.getDvCardPanel1().getCrewBox(), "Primer DV");

        // Validate all extra pilot boxes
        boolean areExtraDvsSelected = view.getExtraDvCardPanelDeque()
                .stream()
                .allMatch(panel -> DialogPresenter.validateDynamicComboBox(view, panel.getCrewBox(), "Extra DV"));

        // Return true only if all validations pass
        return isDv1Selected && areExtraDvsSelected;
    }

    private boolean selectedDvsAreNotRepeated() {
        // Collect all selected dvs from primary, secondary, and extra dv panels
        Set<String> selectedDvs = new HashSet<>();

        // Check primary and secondary dv boxes
        String dv1 = view.getDvCardPanel1().getCrewBox().getSelectedItem().toString();

        // Add dvs to the set if they are not null or empty
        if (dv1 != null && !dv1.isEmpty()) {
            selectedDvs.add(dv1);
        }

        // Check extra dv combo boxes
        view.getExtraDvCardPanelDeque().forEach(panel -> {
            String extraDv = panel.getCrewBox().getSelectedItem().toString();
            if (extraDv != null && !extraDv.isEmpty()) {
                selectedDvs.add(extraDv);
            }
        });

        // If the number of selected dvs is equal to the number of entries in the set,
        // it means no duplicates were found
        if (selectedDvs.size() != (view.getExtraDvCardPanelDeque().size() + 1)) {
            // Show error message if duplicate pilots are found
            JOptionPane.showMessageDialog(view,
                    "Hay dotaciones repetidos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
        return true;
    }

    private boolean isAnyFlightHourInsertedPerDvCard() {

        boolean isDvCardPanel1Default = isAtLeastOneHourFieldInserted(view.getDvCardPanel1());
        boolean allDynamicDefault = view.getExtraDvCardPanelDeque()
                .stream()
                .allMatch(this::isAtLeastOneHourFieldInserted);

        if (!isDvCardPanel1Default || !allDynamicDefault) {
            JOptionPane.showMessageDialog(view, "Inserte al menos una hora de vuelo", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean areDvCardsHoursValid() {
        // Gather all DvCardPanels
        ArrayList<DvCardPanel> dvCardPanelList = new ArrayList<>();
        dvCardPanelList.add(view.getDvCardPanel1());
        dvCardPanelList.addAll(view.getExtraDvCardPanelDeque());

        // Validate each DvCardPanel
        for (DvCardPanel panel : dvCardPanelList) {
            if (!validateDvCardPanel(panel)) {
                return false; // Return false immediately if any panel is invalid
            }
        }
        return true;
    }

    // Validates a single DvCardPanel
    private boolean validateDvCardPanel(DvCardPanel panel) {
        String crewName = panel.getCrewBox().getSelectedItem().toString();

        // Validate "Horas" fields
        if (!DialogPresenter.isAValidOptionalHour(view, panel.getDayHourField(), crewName + " Horas Vuelo Dia", "D") ||
                !DialogPresenter.isAValidOptionalHour(view, panel.getNightHourField(), crewName + " Horas Vuelo Noche", "N") ||
                !DialogPresenter.isAValidOptionalHour(view, panel.getGvnHourField(), crewName + " Horas Vuelo GVN", "G") ||
                !DialogPresenter.isAValidOptionalHour(view, panel.getWinchTrimHourField(), crewName + " Horas Winch Trim", "W")) {
            return false;
        }

        // Validate "Proyectiles" fields
        if (!DialogPresenter.isAValidOptionalNumber(view, panel.getM3mField(), crewName + " Proyectil M3M", "P") ||
                !DialogPresenter.isAValidOptionalNumber(view, panel.getMagField(), crewName + " Proyectil MAG56", "P")) {
            return false;
        }

        return true;
    }

    public void createVectors() {
        allPersonsVector = new Vector<>(getAllPersons());
        allPilotsVector = new Vector<>(getOnlyActualPilots());
        allDvsVector = new Vector<>(getOnlyActualDvs());
        allSessionsVector =  new Vector<>(getAllSessions());

    }

    public Vector<Entity> getAllPilotsVector() {
        return allPilotsVector;
    }

    public Vector<Entity> getAllDvsVector() {
        return allDvsVector;
    }

    public Vector<Entity> getAllPersonsVector() {
        return allPersonsVector;
    }

    public Vector<Entity> getAllSessionsVector() {
        return allSessionsVector;
    }
}
