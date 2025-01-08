package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.factory.DAOFactorySQLite;
import org.jonatancarbonellmartinez.model.dao.*;
import org.jonatancarbonellmartinez.model.entities.*;
import org.jonatancarbonellmartinez.observers.Observer;
import org.jonatancarbonellmartinez.view.DialogView;
import org.jonatancarbonellmartinez.view.RegisterFlightDialogView;
import org.jonatancarbonellmartinez.view.panels.*;

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
    private final IftHourDAOSQLite iftHourDAO;
    private final InstructorHourDAOSQLite instructorHourDAO;
    private final HdmsHourDAOSQLite hdmsHourDAO;
    private final FormationHourDAOSQLite formationHourDAO;
    private final AppDAOSQLite appDAO;
    private final LandingDAOSQLite landingDAO;
    private final WtHourDAOSQLite wtHourDAO;
    private final ProjectileDAOSQLite projectileDAO;
    private final SessionDAOSQLite sessionDAO;
    private final SessionCrewCountDAOSQLite sessionCrewCountDAO;
    private final AuthorityDAOSQLite unitDAO;
    private final CupoHourDAOSQLite cupoHourDAO;
    private final PassengerDAOSQLite passengerDAO;
    private final RegisterFlightDialogView view;
    private final Observer observer;

    private int lastFlightSk;

    private ArrayList<CrewCardPanel> allCrewCrewCardPanels;
    private ArrayList<PilotCrewCardPanel> allPilotCardPanels;
    private ArrayList<DvCrewCardPanel> allDvCardPanels;
    private ArrayList<SessionCardPanel> allSessionCardPanels;
    private ArrayList<CupoHourCardPanel> allCupoHourCardPanels;
    private ArrayList<PassengerCardPanel> allPassengerCardPanels;

    private Vector<Entity> allPilotsVector, allDvsVector, allPersonsVector, allSessionsVector, allUnitsVector;

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
        this.formationHourDAO = DAOFactorySQLite.getInstance().createFormationHourDAO();
        this.appDAO = DAOFactorySQLite.getInstance().createAppDAO();
        this.landingDAO = DAOFactorySQLite.getInstance().createLandingDAO();
        this.wtHourDAO = DAOFactorySQLite.getInstance().createWtHourDAO();
        this.projectileDAO = DAOFactorySQLite.getInstance().createProjectileDAO();
        this.sessionDAO = DAOFactorySQLite.getInstance().createSessionDAO();
        this.sessionCrewCountDAO = DAOFactorySQLite.getInstance().createSessionCrewCountDAO();
        this.unitDAO = DAOFactorySQLite.getInstance().createUnitDAO();
        this.cupoHourDAO = DAOFactorySQLite.getInstance().createCupoHourDAO();
        this.passengerDAO = DAOFactorySQLite.getInstance().createPassengerDAO();
        createVectors();
        this.observer = observer;
    }

    @Override
    public boolean isFormValid() {
        boolean isValid = isVueloCardValid() &&
                            areCrewCardsValid() &&
                            areSessionCardsValid() &&
                            areCupoHourCardsValid() &&
                            arePassengerCardsValid();
        return isValid;
    }

    @Override
    public void insertEntity() { // TODO quiza este llame a todos los metodos de insertX
        try {
            insertFlight();
            insertPersonHour();
            insertIftHour();
            insertHdmsHour();
            insertInstructorHour();
            insertFormationHour();
            insertApp();
            insertLanding();
            insertWtHour();
            insertProjectile();
            insertSessionCrewCount();
            insertCupoHour();
            insertPassenger();
            // add more insert methods.
            DialogView.showMessage(view,"Vuelo añadido correctamente.");

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
        allCrewCrewCardPanels = new ArrayList<>();

        // Add the predefined panels
        allCrewCrewCardPanels.add(view.getPilotCardPanel1());
        allCrewCrewCardPanels.add(view.getPilotCardPanel2());
        allCrewCrewCardPanels.add(view.getDvCardPanel1());

        // Add dynamically added panels from the deque
        allCrewCrewCardPanels.addAll(view.getExtraPilotCardPanelDeque());
        allCrewCrewCardPanels.addAll(view.getExtraDvCardPanelDeque());
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

    private void collectCupoHourCardPanels() {
        allCupoHourCardPanels = new ArrayList<>();

        allCupoHourCardPanels.add(view.getCupoHourCardPanel1());
        allCupoHourCardPanels.add(view.getCupoHourCardPanel2());
        allCupoHourCardPanels.addAll(view.getExtraCupoHourCardPanelDeque());
    }

    private void collectPassengerCardPanels() {
        allPassengerCardPanels = new ArrayList<>();

        allPassengerCardPanels.add(view.getPassengerCardPanel1());
        allPassengerCardPanels.add(view.getPassengerCardPanel2());
        allPassengerCardPanels.addAll(view.getExtraPassengerCardPanelDeque());
    }

    public void insertPersonHour() {
        PersonHour personHour = new PersonHour();
        personHour.setFlightFk(lastFlightSk);
        // Iterate over all the pilot card panels
        for (CrewCardPanel crewCardPanel : allCrewCrewCardPanels) {
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
    private String getPeriodHourFieldText(CrewCardPanel CrewCardPanel, int periodFk) {
        switch (periodFk) {
            case 1: // Day
                return CrewCardPanel.getDayHourField().getText();
            case 2: // Night
                return CrewCardPanel.getNightHourField().getText();
            case 3: // Gvn
                return CrewCardPanel.getGvnHourField().getText();
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
    private String getAppTypeFieldText(PilotCrewCardPanel pilotCardPanel, int appTypeFk) {
        switch (appTypeFk) {
            case 1: // Precision
                return pilotCardPanel.getPrecisionField().getText();
            case 2: // No precision
                return pilotCardPanel.getNoPrecisionField().getText();
            case 3: // T/D
                return pilotCardPanel.getTdField().getText();
            case 4: // Search Pattern
                return pilotCardPanel.getSrchPattField().getText();
            default:
                return "";
        }
    }

    // Helper method of insertApp() to get the default app type value
    private String getDefaultAppTypeValue(int appTypeFk) {
        switch (appTypeFk) {
            case 1: return "P"; // Precision
            case 2: return "N"; // No precision
            case 3: return "T"; // T/D
            case 4: return "S"; // Search Pattern
            default: return "";
        }
    }

    // Helper method of insertLanding() to get the hour field text based on the period
    private String getPeriodMonoLandingFieldText(PilotCrewCardPanel pilotCardPanel, int periodFk) {
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

    // Helper method of insertLanding() to get the hour field text based on the period
    private String getPeriodMultiLandingFieldText(PilotCrewCardPanel pilotCardPanel, int periodFk) {
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

    // Helper method of insertLanding() to get the hour field text based on the period
    private String getPeriodTierraLandingFieldText(PilotCrewCardPanel pilotCardPanel, int periodFk) {
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

    // Helper method of insertLanding() to get the hour field text based on the period
    private String getPeriodCarrierLandingFieldText(PilotCrewCardPanel pilotCardPanel, int periodFk) {
        switch (periodFk) {
            case 1: // Day
                return pilotCardPanel.getCarrierDayField().getText();
            case 2: // Night
                return pilotCardPanel.getCarrierNightField().getText();
            case 3: // Gvn
                return pilotCardPanel.getCarrierGvnField().getText();
            default:
                return "";
        }
    }

    private void insertIftHour() {
        List<IftHour> iftHours = new ArrayList<>();

        for (PilotCrewCardPanel pilotCardPanel : allPilotCardPanels) {
            String iftHourField = pilotCardPanel.getIftHourField().getText();

            // Skip processing if the iftHourField is "I"
            if ("I".equals(iftHourField)) {
                continue;
            }

            // Process the valid IftHourField values
            IftHour iftHour = new IftHour();
            iftHour.setFlightFk(lastFlightSk);
            iftHour.setPersonFk(getForeignKey(pilotCardPanel.getCrewBox().getSelectedItem()));
            iftHour.setIftHourQty(Double.parseDouble(iftHourField));

            iftHours.add(iftHour);
        }
        iftHourDAO.insertBatch(iftHours);
    }

    private void insertHdmsHour() {
        List<HdmsHour> hdmsHours = new ArrayList<>();

        for (PilotCrewCardPanel pilotCardPanel : allPilotCardPanels) {
            String hdmsHourField = pilotCardPanel.getHdmsHourField().getText();

            // Skip processing if the iftHourField is "H"
            if ("H".equals(hdmsHourField)) {
                continue;
            }

            // Process the valid IftHourField values
            HdmsHour hdmsHour = new HdmsHour();
            hdmsHour.setFlightFk(lastFlightSk);
            hdmsHour.setPersonFk(getForeignKey(pilotCardPanel.getCrewBox().getSelectedItem()));
            hdmsHour.setHdmsHourQty(Double.parseDouble(hdmsHourField));

            hdmsHours.add(hdmsHour);
        }
        hdmsHourDAO.insertBatch(hdmsHours);
    }

    private void insertInstructorHour() {
        List<InstructorHour> instructorHours = new ArrayList<>();

        for (PilotCrewCardPanel pilotCardPanel : allPilotCardPanels) {
            String instructorHourField = pilotCardPanel.getInstructorHourField().getText();

            // Skip processing if the iftHourField is "I"
            if ("I".equals(instructorHourField)) {
                continue;
            }

            // Process the valid IftHourField values
            InstructorHour instructorHour = new InstructorHour();
            instructorHour.setFlightFk(lastFlightSk);
            instructorHour.setPersonFk(getForeignKey(pilotCardPanel.getCrewBox().getSelectedItem()));
            instructorHour.setInstructorHourQty(Double.parseDouble(instructorHourField));

            instructorHours.add(instructorHour);
        }
        instructorHourDAO.insertBatch(instructorHours);
    }

    private void insertFormationHour() {
        List<FormationHour> formationHours = new ArrayList<>();

        for (PilotCrewCardPanel pilotCardPanel : allPilotCardPanels) {
            String formationHourDayField = pilotCardPanel.getDayFormacionesHourField().getText();
            String formationHourGvnField = pilotCardPanel.getGvnFormacionesHourField().getText();

            // Skip processing if the both fields are the placeholder
            if ("D".equals(formationHourDayField) && "G".equals(formationHourGvnField)) {
                continue;
            }

            if (!"D".equals(formationHourDayField)) {
                formationHours.add(createFormationHour(1, Double.parseDouble(formationHourDayField), pilotCardPanel.getCrewBox().getSelectedItem()));
            }

            if (!"G".equals(formationHourGvnField)) {
                formationHours.add(createFormationHour(3, Double.parseDouble(formationHourGvnField), pilotCardPanel.getCrewBox().getSelectedItem()));
            }
        }
        formationHourDAO.insertBatch(formationHours);
    }

    // Helper method for insertFormationHour()
    private FormationHour createFormationHour(int periodFk, double hourQty, Object selectedItem) {
        FormationHour formationHour = new FormationHour();
        formationHour.setFlightFk(lastFlightSk);
        formationHour.setPersonFk(getForeignKey(selectedItem));
        formationHour.setPerdiodFk(periodFk);
        formationHour.setFormationHourQty(hourQty);
        return formationHour;
    }


    private void insertApp() {
        List<App> apps = new ArrayList<>();

        // Iterate over all the pilot card panels
        for (PilotCrewCardPanel pilotCardPanel : allPilotCardPanels) {
            // Iterate over all app types (Precision, No precision, T/D and Search Pattern) and corresponding hour fields
            for (int appTypeFk = 1; appTypeFk <= 4; appTypeFk++) {
                String appTypeFieldText = getAppTypeFieldText(pilotCardPanel, appTypeFk);
                String defaultValue = getDefaultAppTypeValue(appTypeFk);

                // Only insert if the field is not equal to the default value
                if (!appTypeFieldText.equals(defaultValue)) {
                    App app = new App();
                    app.setFlightFk(lastFlightSk);
                    app.setPersonFk(getForeignKey(pilotCardPanel.getCrewBox().getSelectedItem()));
                    app.setAppTypeFk(appTypeFk);
                    app.setAppQty(Integer.parseInt(appTypeFieldText));

                    // Insert the person hour into the database
                    apps.add(app);
                }
            }
        }
        appDAO.insertBatch(apps);
    }

    private void insertLanding() {
        List<Landing> landings = new ArrayList<>();

        // Iterate over all the pilot card panels
        for (PilotCrewCardPanel pilotCardPanel : allPilotCardPanels) {
            // Iterate over all periods (Day, Night, Gvn) and corresponding hour fields
            for (int periodFk = 1; periodFk <= 3; periodFk++) {
                String monoLandingFieldText = getPeriodMonoLandingFieldText(pilotCardPanel, periodFk);
                String multiLandingFieldText = getPeriodMultiLandingFieldText(pilotCardPanel, periodFk);
                String tierraLandingFieldText = getPeriodTierraLandingFieldText(pilotCardPanel, periodFk);
                String carrierLandingFieldText = getPeriodCarrierLandingFieldText(pilotCardPanel, periodFk);
                String defaultValue = getDefaultPeriodValue(periodFk);

                // Only insert if the field is not equal to the default value
                if (!monoLandingFieldText.equals(defaultValue)) {
                    Landing landing = new Landing();
                    landing.setFlightFk(lastFlightSk);
                    landing.setPersonFk(getForeignKey(pilotCardPanel.getCrewBox().getSelectedItem()));
                    landing.setPeriodFk(periodFk);
                    landing.setPlaceFk(1); // 1 equals Monospot
                    landing.setLandingQty(Integer.parseInt(monoLandingFieldText));
                    landings.add(landing);
                }
                if (!multiLandingFieldText.equals(defaultValue)) {
                    Landing landing = new Landing();
                    landing.setFlightFk(lastFlightSk);
                    landing.setPersonFk(getForeignKey(pilotCardPanel.getCrewBox().getSelectedItem()));
                    landing.setPeriodFk(periodFk);
                    landing.setPlaceFk(2); // 2 equals Multispot
                    landing.setLandingQty(Integer.parseInt(multiLandingFieldText));
                    landings.add(landing);
                }
                if (!tierraLandingFieldText.equals(defaultValue)) {
                    Landing landing = new Landing();
                    landing.setFlightFk(lastFlightSk);
                    landing.setPersonFk(getForeignKey(pilotCardPanel.getCrewBox().getSelectedItem()));
                    landing.setPeriodFk(periodFk);
                    landing.setPlaceFk(3); // 3 equals Tierra
                    landing.setLandingQty(Integer.parseInt(tierraLandingFieldText));
                    landings.add(landing);
                }
                if (!carrierLandingFieldText.equals(defaultValue)) {
                    Landing landing = new Landing();
                    landing.setFlightFk(lastFlightSk);
                    landing.setPersonFk(getForeignKey(pilotCardPanel.getCrewBox().getSelectedItem()));
                    landing.setPeriodFk(periodFk);
                    landing.setPlaceFk(4); // 4 equals Carrier
                    landing.setLandingQty(Integer.parseInt(carrierLandingFieldText));
                    landings.add(landing);
                }
            }
        }
        landingDAO.insertBatch(landings);
    }

    private void insertWtHour() {
        List<WtHour> wtHours = new ArrayList<>();

        for (DvCrewCardPanel dvCardPanel : allDvCardPanels) {
            String wtHourField = dvCardPanel.getWinchTrimHourField().getText();

            // Skip processing if the iftHourField is "W"
            if ("W".equals(wtHourField)) {
                continue;
            }

            WtHour wtHour = new WtHour();
            wtHour.setFlightFk(lastFlightSk);
            wtHour.setPersonFk(getForeignKey(dvCardPanel.getCrewBox().getSelectedItem()));
            wtHour.setWtHourQty(Double.parseDouble(wtHourField));
            wtHours.add(wtHour);
        }
        wtHourDAO.insertBatch(wtHours);
    }

    private void insertProjectile() {
        List<Projectile> projectiles = new ArrayList<>(); // Collect all projectiles for batch insert

        for (DvCrewCardPanel dvCardPanel : allDvCardPanels) {
            String projectileM3MField = dvCardPanel.getM3mField().getText();
            String projectileMAGField = dvCardPanel.getMagField().getText();

            int personFk = getForeignKey(dvCardPanel.getCrewBox().getSelectedItem());

            // Process M3M field
            if (!"P".equals(projectileM3MField)) {
                Projectile projectile = new Projectile();
                projectile.setFlightFk(lastFlightSk);
                projectile.setPersonFk(personFk);
                projectile.setProjectileTypeFk(1); // 1 points to 7.62 M3M
                projectile.setProjectileQty(Integer.parseInt(projectileM3MField));
                projectiles.add(projectile);
            }

            // Process MAG field
            if (!"P".equals(projectileMAGField)) {
                Projectile projectile = new Projectile();
                projectile.setFlightFk(lastFlightSk);
                projectile.setPersonFk(personFk);
                projectile.setProjectileTypeFk(2); // 2 points to 12.7 MAG58
                projectile.setProjectileQty(Integer.parseInt(projectileMAGField));
                projectiles.add(projectile);
            }
        }

        // Perform batch insert
        projectileDAO.insertBatch(projectiles);
    }

    private void insertSessionCrewCount() { // TODO study this method for batch inserting
        List<SessionCrewCount> sessionCrewCounts = new ArrayList<>();

        for (SessionCardPanel sessionCardPanel : allSessionCardPanels) {
            for(JComboBox personBox : sessionCardPanel.getExtraPersonBoxesDeque()) {
                Integer personFk = getForeignKey(personBox.getSelectedItem());

                if (personFk == null) {
                    continue;
                }

                for(JComboBox sessionBox : sessionCardPanel.getExtraSessionBoxesDeque()) {
                    Integer sessionFk = getForeignKey(sessionBox.getSelectedItem());

                    if (sessionFk == null) {
                        continue;
                    }

                    SessionCrewCount sessionCrewCount = new SessionCrewCount();
                    sessionCrewCount.setFlightFk(lastFlightSk);
                    sessionCrewCount.setPersonFk(personFk);
                    sessionCrewCount.setSessionFk(sessionFk);

                    sessionCrewCounts.add(sessionCrewCount);
                }
            }
        }
        sessionCrewCountDAO.insertBatch(sessionCrewCounts);
    }

    private void insertCupoHour() {
        List<CupoHour> cupoHours = new ArrayList<>();

        for(CupoHourCardPanel cupoHourCardPanel : allCupoHourCardPanels) {
            Integer unitSk = getForeignKey(cupoHourCardPanel.getUnitBox().getSelectedItem());
            String cupoHourQty = cupoHourCardPanel.getHourQtyField().getText();

            if( unitSk == null || cupoHourQty.equals("Horas")) {
                continue;
            }

            CupoHour cupoHour = new CupoHour();
            cupoHour.setFlightFk(lastFlightSk);
            cupoHour.setUnitFk(unitSk);
            cupoHour.setCupoHourQty(Double.parseDouble(cupoHourQty));

            cupoHours.add(cupoHour);
        }

        cupoHourDAO.insertBatch(cupoHours);
    }

    private void insertPassenger() {
        List<Passenger> passengers = new ArrayList<>();

        for(PassengerCardPanel passengerCardPanel : allPassengerCardPanels) {
            String passengerType = passengerCardPanel.getTypeBox().getSelectedItem().toString() == null ? "nulo" : passengerCardPanel.getTypeBox().getSelectedItem().toString();
            String passengerQty = passengerCardPanel.getQtyField().getText();
            String passengerRoute = passengerCardPanel.getRouteField().getText().toUpperCase();

            if( passengerType == null || Objects.equals(passengerQty, "Cantidad") || Objects.equals(passengerRoute, "Ruta")) {
                continue;
            }

            Passenger passenger = new Passenger();
            passenger.setFlightFk(lastFlightSk);
            passenger.setPassengerTypeFk(passengerType.equals("Civiles") ? 1 : 2);
            passenger.setPassengerQty(Integer.parseInt(passengerQty));
            passenger.setRoute(passengerRoute);

            passengers.add(passenger);
        }
        passengerDAO.insertBatch(passengers);
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

        collectCrewCardPanels();
        collectPilotCardPanels();
        collectDvCardPanels();
        collectSessionCardPanels();
        collectCupoHourCardPanels();
        collectPassengerCardPanels();

        if (isFormValid()) {
            insertEntity();
            notifyObserver();

            // Get the current position of the window
            java.awt.Point location = view.getLocation();

            // Dispose of the current window
            view.dispose();

            // Create a new instance of RegisterFlightDialogView at the same position
            RegisterFlightDialogView newView = new RegisterFlightDialogView(view.getMainView());
            newView.setLocation(location);
            newView.setVisible(true); // TODO esto no esta funcionando bien, porque la vista primero aparece donde la mainView y despues en la lastLocation. arreglalo
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

    public void onAddCupoCardItemClicked() {
        view.addExtraCupoHourCardView();
    }

    public void onDeleteCupoCardItemClicked() {
        view.deleteExtraCupoHourCardView();
    }

    public void onAddPassengerCardItemClicked() {
        view.addExtraPassengerCardView();
    }

    public void onDeletePassengerCardItemClicked() {
        view.deleteExtraPassengerCardView();
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
        observer.update();
    }

    @Override
    public void setActionListeners() {
        view.getSaveButton().addActionListener(e -> onSaveButtonClicked());
        view.getAddPilotItem().addActionListener( e -> onAddPilotoItemClicked());
        view.getDeletePilotItem().addActionListener( e -> onDeletePilotoItemClicked());
        view.getAddDvItem().addActionListener( e -> onAddDvItemClicked());
        view.getDeleteDvItem().addActionListener( e -> onDeleteDvItemClicked());
        view.getSessionCardPanel().getAddPersonItem().addActionListener(e -> onAddPersonItemClicked());
        view.getSessionCardPanel().getDeletePersonItem().addActionListener(e -> onDeletePersonItemClicked());
        view.getSessionCardPanel().getAddSessionItem().addActionListener(e -> onAddSessionItemClicked());
        view.getSessionCardPanel().getDeleteSessionItem().addActionListener(e -> onDeleteSessionItemClicked());
        view.getSessionCardPanel().getAddGroupItem().addActionListener(e -> onAddGroupItemClicked());
        view.getSessionCardPanel().getDeleteGroupItem().addActionListener(e -> onDeleteGroupItemClicked());
        view.getAddCupoCardItem().addActionListener( e -> onAddCupoCardItemClicked());
        view.getDeleteCupoCardItem().addActionListener( e -> onDeleteCupoCardItemClicked());
        view.getAddPassengerCardItem().addActionListener( e -> onAddPassengerCardItemClicked());
        view.getDeletePassengerCardItem().addActionListener( e -> onDeletePassengerCardItemClicked());
    }

    public void setCardSessionActionListener(SessionCardPanel sessionCardPanel) {
        sessionCardPanel.getAddPersonItem().addActionListener( e -> sessionCardPanel.addExtraPersonBox());
        sessionCardPanel.getDeletePersonItem().addActionListener(e -> sessionCardPanel.deleteExtraPersonBox());
        sessionCardPanel.getAddSessionItem().addActionListener(e -> sessionCardPanel.addExtraSessionBox());
        sessionCardPanel.getDeleteSessionItem().addActionListener(e -> sessionCardPanel.deleteExtraSessionBox());
        sessionCardPanel.getAddGroupItem().addActionListener(e -> view.addExtraSessionCardView());
        sessionCardPanel.getDeleteGroupItem().addActionListener(e -> view.deleteExtraSessionCardView());
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

    public List<Authority> getAllUnits() {
        return unitDAO.getAll();
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
                            areInstrumentalHoursEqualOrLessThanTotalHours() &&
                            areHmdsHoursEqualOrLessThanTotalHours() &&
                            areIpHoursEqualOrLessThanTotalHours() &&
                            areFormationHoursEqualOrLessThanTotalHours() &&
                            doesTotalHoursEqualsSumOfPilotHours() &&
                            areDvsSelected() &&
                            selectedDvsAreNotRepeated() &&
                            isAnyFlightHourInsertedPerDvCard() &&
                            areDvCardsHoursValid();
                            //doesTotalHoursEqualsSumOfDvHours(), i think i dont need this method, but we will see.
        return isValid;
    }

    private boolean areSessionCardsValid() {
        boolean isValid = hasNoDuplicatePersonsInSessionCardPanel() &&
                          personInSessionCardHasFlight() &&
                          hasNoDuplicateSessionsInSessionCardPanel() &&
                          hasNoDuplicateSessionPanels()  ;

        return isValid;
    }

    private boolean arePassengerCardsValid() {
        boolean isValid = isPassengerCardDataValid() &&
                          hasNoDuplicatePassengerPanels();
        return isValid;
    }

    private boolean isPassengerCardDataValid() {
        for (PassengerCardPanel passengerCardPanel : allPassengerCardPanels) {
            int typeBoxSelection = passengerCardPanel.getTypeBox().getSelectedIndex();
            String qtyFieldValue = passengerCardPanel.getQtyField().getText();
            String routeFieldValue = passengerCardPanel.getRouteField().getText();

            // Si hay intención de rellenar datos pero falta alguno o no es válido
            if (typeBoxSelection != 0 || !qtyFieldValue.equals("Cantidad") || !routeFieldValue.equals("Ruta")) {
                // Validar el campo 'Cantidad'
                if (passengerCardPanel.getQtyField().getText().equals("Cantidad") ||
                        !qtyFieldValue.matches("^[1-9][0-9]*$")) {
                    JOptionPane.showMessageDialog(view,
                            "La cantidad ingresada en el campo 'Cantidad' es inválida o está vacía.\nSolo se permiten números enteros positivos.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                // Validar el campo 'Ruta'
                if (passengerCardPanel.getRouteField().getText().equals("Ruta") ||
                        !routeFieldValue.matches("^[a-zA-Z]{1,20}-[a-zA-Z]{1,20}$")) {
                    JOptionPane.showMessageDialog(view,
                            "La Ruta ingresada no es válida.\nDebe seguir el formato: Abc...-Abc...",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }
        return true;
    }

    private boolean hasNoDuplicatePassengerPanels() {
        // Usamos un conjunto para almacenar las combinaciones únicas de los campos.
        Set<String> uniquePanels = new HashSet<>();

        for (PassengerCardPanel passengerCardPanel : allPassengerCardPanels) {
            int typeBoxSelection = passengerCardPanel.getTypeBox().getSelectedIndex();
            String qtyFieldValue = passengerCardPanel.getQtyField().getText();
            String routeFieldValue = passengerCardPanel.getRouteField().getText();

            if (typeBoxSelection == 0 || qtyFieldValue.equals("Cantidad") || routeFieldValue.equals("Ruta")) {
                continue;
            }

            // Creamos una clave única basada en los valores de los campos.
            String panelKey = typeBoxSelection + "|" + qtyFieldValue + "|" + routeFieldValue;

            // Verificamos si la clave ya existe en el conjunto.
            if (uniquePanels.contains(panelKey)) {
                JOptionPane.showMessageDialog(view,
                        "Existen paneles de pasajeros duplicados.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Si no es un duplicado, lo añadimos al conjunto.
            uniquePanels.add(panelKey);
        }

        return true; // No se encontraron duplicados.
    }


    private boolean hasNoDuplicatePersonsInSessionCardPanel() {

        for (SessionCardPanel sessionCardPanel : allSessionCardPanels) {

            Set<String> persons = new HashSet<>();

            for (JComboBox box : sessionCardPanel.getExtraPersonBoxesDeque()) {
                // Get selected person or skip if null
                Object selectedItem = box.getSelectedItem();
                if (selectedItem == null) {
                    continue; // Skip if no item is selected
                }

                String personName = selectedItem.toString(); // Convert to String

                // Check for duplicates during addition
                if (!persons.add(personName)) {
                    JOptionPane.showMessageDialog(view, "Hay tripulantes repetidos en sesiones.", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }
        return true;
    }

    private boolean hasNoDuplicateSessionsInSessionCardPanel() {


        for (SessionCardPanel sessionCardPanel : allSessionCardPanels) {

            Set<String> sessions = new HashSet<>();

            for (JComboBox box : sessionCardPanel.getExtraSessionBoxesDeque()) {
                // Get selected person or skip if null
                Object selectedItem = box.getSelectedItem();
                if (selectedItem == null) {
                    continue; // Skip if no item is selected
                }

                String sessionName = selectedItem.toString(); // Convert to String

                // Check for duplicates during addition
                if (!sessions.add(sessionName)) {
                    JOptionPane.showMessageDialog(view, "Hay sesiones repetidas.", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }
        return true;

    }

    private boolean personInSessionCardHasFlight() { // This method will check that the person selected appears in any pilot or dv card panel(para no asignar sesiones a alguien que no ha estado en ese vuelo)
        List<String> personsOfFlight = new ArrayList<>();
        List<String> personsOfSession = new ArrayList<>();

        // Populate personsOfFlight
        for (CrewCardPanel crewCardPanel : allCrewCrewCardPanels) {
            personsOfFlight.add(crewCardPanel.getCrewBox().getSelectedItem().toString());
        }

        // Populate personsOfSession
        for (SessionCardPanel sessionCardPanel : allSessionCardPanels) {
            for (JComboBox personBox : sessionCardPanel.getExtraPersonBoxesDeque()) {
                Object selectedItem = personBox.getSelectedItem();
                if (selectedItem == null) {
                    continue; // Skip if no item is selected
                }

                personsOfSession.add(personBox.getSelectedItem().toString());
            }
        }

        // Check for persons in personsOfSession not in personsOfFlight
        for (String person : personsOfSession) {
            if (!personsOfFlight.contains(person)) {
                JOptionPane.showMessageDialog(view, "Hay tripulantes en sesiones que no han volado.", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Found a person in session not in flight
            }
        }
        return true;
    }

    public boolean hasNoDuplicateSessionPanels() {
        // Use a set to store unique combinations of person and session selections
        Set<String> uniqueCombinations = new HashSet<>();

        for (SessionCardPanel sessionCardPanel : allSessionCardPanels) {
            // Collect selected items for persons and sessions
            List<String> persons = new ArrayList<>();
            List<String> sessions = new ArrayList<>();

            for (JComboBox box : sessionCardPanel.getExtraPersonBoxesDeque()) {
                Object selectedItem = box.getSelectedItem();
                if (selectedItem != null) {
                    persons.add(selectedItem.toString());
                }
            }

            for (JComboBox box : sessionCardPanel.getExtraSessionBoxesDeque()) {
                Object selectedItem = box.getSelectedItem();
                if (selectedItem != null) {
                    sessions.add(selectedItem.toString());
                }
            }

            // Create a unique key for the current panel's combination of selections
            String combinationKey = String.join(",", persons) + "|" + String.join(",", sessions);

            // Check if this combination already exists
            if (!uniqueCombinations.add(combinationKey)) {
                // Duplicate found
                JOptionPane.showMessageDialog(view, "Hay grupos de sesiones repetidos.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        // No duplicates detected
        return true;
    }

    private boolean arePilotsSelected() {
        // Validate pilot boxes for primary and secondary pilots
        boolean isPilot1Selected = DialogPresenter.validateDynamicComboBox(view, view.getPilotCardPanel1().getCrewBox(), "HAC");
        boolean isPilot2Selected = DialogPresenter.validateDynamicComboBox(view, view.getPilotCardPanel2().getCrewBox(), "H2P");

        // Validate all extra pilot boxes
        boolean areExtraPilotsSelected = view.getExtraPilotCardPanelDeque()
                .stream()
                .allMatch(panel -> DialogPresenter.validateDynamicComboBox(view, panel.getCrewBox(), "Extra H2P"));

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

    private boolean isAtLeastOneHourFieldInserted(CrewCardPanel panel) {
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

        for (PilotCrewCardPanel extraPanel : view.getExtraPilotCardPanelDeque()) {
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

    private boolean areInstrumentalHoursEqualOrLessThanTotalHours() {
        BigDecimal totalHours = parseBigDecimalOrZero(view.getTotalHoursField().getText());

        for (PilotCrewCardPanel pilotCrewCardPanel : allPilotCardPanels) {
            BigDecimal instrumentalHours = parseBigDecimalOrZero(pilotCrewCardPanel.getIftHourField().getText());
            if (instrumentalHours.compareTo(totalHours) > 0) {
                JOptionPane.showMessageDialog(view, "Las Horas Instr. no pueden ser mayores que las Horas Totales del vuelo.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        return true;
    }

    private boolean areHmdsHoursEqualOrLessThanTotalHours() {
        BigDecimal totalHours = parseBigDecimalOrZero(view.getTotalHoursField().getText());

        for (PilotCrewCardPanel pilotCrewCardPanel : allPilotCardPanels) {
            BigDecimal hmdsHours = parseBigDecimalOrZero(pilotCrewCardPanel.getHdmsHourField().getText());
            if (hmdsHours.compareTo(totalHours) > 0) {
                JOptionPane.showMessageDialog(view, "Las Horas HMDS no pueden ser mayores que las Horas Totales del vuelo.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        return true;
    }

    private boolean areIpHoursEqualOrLessThanTotalHours() {
        BigDecimal totalHours = parseBigDecimalOrZero(view.getTotalHoursField().getText());

        for (PilotCrewCardPanel pilotCrewCardPanel : allPilotCardPanels) {
            BigDecimal ipHours = parseBigDecimalOrZero(pilotCrewCardPanel.getInstructorHourField().getText());
            if (ipHours.compareTo(totalHours) > 0) {
                JOptionPane.showMessageDialog(view, "Las Horas IP no pueden ser mayores que las Horas Totales del vuelo.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        return true;
    }

    private boolean areFormationHoursEqualOrLessThanTotalHours() {
        BigDecimal totalHours = parseBigDecimalOrZero(view.getTotalHoursField().getText());

        for (PilotCrewCardPanel pilotCrewCardPanel : allPilotCardPanels) {
            BigDecimal formationHours = parseBigDecimalOrZero(pilotCrewCardPanel.getDayFormacionesHourField().getText()).add(parseBigDecimalOrZero(pilotCrewCardPanel.getGvnFormacionesHourField().getText()));
            if (formationHours.compareTo(totalHours) > 0) {
                JOptionPane.showMessageDialog(view, "Las Horas Formaciones no pueden ser mayores que las Horas Totales del vuelo.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        return true;
    }


    // Method to check if total hours equals the sum of cupo hours
    private boolean doesTotalHoursEqualsSumOfCupoHours() {
        // Parse the total hours from the view
        BigDecimal totalHours = parseBigDecimalOrZero(view.getTotalHoursField().getText());

        // Calculate the sum of cupo hours
        BigDecimal sumOfCupoHours = BigDecimal.ZERO.setScale(1, RoundingMode.HALF_UP);
        for (CupoHourCardPanel cupoHourCardPanel : allCupoHourCardPanels) {
            sumOfCupoHours = sumOfCupoHours.add(parseBigDecimalOrZero(cupoHourCardPanel.getHourQtyField().getText()));
        }

        // Compare total hours with the sum of cupo hours
        if (!totalHours.equals(sumOfCupoHours)) {
            JOptionPane.showMessageDialog(view,
                    "Las Horas Totales del vuelo no coinciden con las Horas por Cupo.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private boolean isCupoHourCardValid() {
        for (CupoHourCardPanel cupoHourCardPanel : allCupoHourCardPanels) {
            String hourQtyText = cupoHourCardPanel.getHourQtyField().getText().trim(); // Trim for safety
            int selectedIndex = cupoHourCardPanel.getUnitBox().getSelectedIndex();

            if (!hourQtyText.equals("Horas") && selectedIndex == -1) {
                JOptionPane.showMessageDialog(view,
                        "Hay Horas por Cupo a las que no se le ha asignado una Unidad.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (hourQtyText.equals("Horas") && selectedIndex != -1) {
                JOptionPane.showMessageDialog(view,
                        "Hay una Unidad a la que no se le ha asignado un Cupo.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    private boolean hasNoDuplicateCupoPanels() {
        // Usamos un conjunto para almacenar las combinaciones únicas de los campos.
        Set<String> uniquePanels = new HashSet<>();

        for (CupoHourCardPanel cupoHourCardPanel : allCupoHourCardPanels) {
            int unitBoxSelection = cupoHourCardPanel.getUnitBox().getSelectedIndex();
            String hourFieldValue = cupoHourCardPanel.getHourQtyField().getText();

            if (unitBoxSelection == -1 || hourFieldValue.equals("Horas")) {
                continue;
            }

            // Creamos una clave única basada en los valores de los campos.
            String panelKey = unitBoxSelection + "|" + hourFieldValue;

            // Verificamos si la clave ya existe en el conjunto.
            if (uniquePanels.contains(panelKey)) {
                JOptionPane.showMessageDialog(view,
                        "Existen paneles de Cupo duplicados.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Si no es un duplicado, lo añadimos al conjunto.
            uniquePanels.add(panelKey);
        }

        return true; // No se encontraron duplicados.
    }


    private boolean arePilotCardsHoursValid() {
        // Validate each PilotCardPanel
        for (PilotCrewCardPanel panel : allPilotCardPanels) {
            if (!validatePilotCardPanel(panel)) {
                return false; // Return false immediately if any panel is invalid
            }
        }
        return true;
    }

    // Validates a single PilotCardPanel
    private boolean validatePilotCardPanel(PilotCrewCardPanel panel) {
        String crewName = panel.getCrewBox().getSelectedItem().toString();

        // Validate "Horas" fields
        if (!DialogPresenter.isAValidOptionalHour(view, panel.getDayHourField(), crewName + " Horas Vuelo Dia", "D") ||
                !DialogPresenter.isAValidOptionalHour(view, panel.getNightHourField(), crewName + " Horas Vuelo Noche", "N") ||
                !DialogPresenter.isAValidOptionalHour(view, panel.getGvnHourField(), crewName + " Horas Vuelo GVN", "G") ||
                !DialogPresenter.isAValidOptionalHour(view, panel.getIftHourField(), crewName + " Horas Instrumentos", "I") ||
                !DialogPresenter.isAValidOptionalHour(view, panel.getHdmsHourField(), crewName + " Horas HDMS", "H") ||
                !DialogPresenter.isAValidOptionalHour(view, panel.getInstructorHourField(), crewName + " Horas Instructor", "I") ||
                !DialogPresenter.isAValidOptionalHour(view, panel.getDayFormacionesHourField(), crewName + " Horas Formacion Dia", "D") ||
                !DialogPresenter.isAValidOptionalHour(view, panel.getGvnFormacionesHourField(), crewName + " Horas Formacion Gvn", "G")) {
            return false;
        }

        // Validate "Precision" fields
        if (!DialogPresenter.isAValidOptionalNumber(view, panel.getPrecisionField(), crewName + " Aproximación de Precisión", "P") ||
                !DialogPresenter.isAValidOptionalNumber(view, panel.getNoPrecisionField(), crewName + " Aproximación de No precisión", "N") ||
                !DialogPresenter.isAValidOptionalNumber(view, panel.getTdField(), crewName + " Aproximación T/D", "T")) {
            return false;
        }

        // Validate "Tomas" fields
        if (!validateTomasFields(panel, crewName)) {
            return false;
        }

        return true;
    }

    // Helper method for validating "Tomas" fields
    private boolean validateTomasFields(PilotCrewCardPanel panel, String crewName) {
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
        // Validate each DvCardPanel
        for (DvCrewCardPanel panel : allDvCardPanels) {
            if (!validateDvCardPanel(panel)) {
                return false; // Return false immediately if any panel is invalid
            }
        }
        return true;
    }

    private boolean areCupoHourCardsValid() {
        return isCupoHourCardValid() &&
                doesTotalHoursEqualsSumOfCupoHours() &&
                hasNoDuplicateCupoPanels();
    }

    // Validates a single DvCardPanel
    private boolean validateDvCardPanel(DvCrewCardPanel panel) {
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
        allUnitsVector = new Vector<>(getAllUnits());
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

    public Vector<Entity> getAllUnitsVector() {
        return allUnitsVector;
    }
}
