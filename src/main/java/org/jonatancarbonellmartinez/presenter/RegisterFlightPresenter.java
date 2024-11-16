package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.factory.DAOFactorySQLite;
import org.jonatancarbonellmartinez.model.dao.EventDAOSQLite;
import org.jonatancarbonellmartinez.model.dao.FlightDAOSQLite;
import org.jonatancarbonellmartinez.model.dao.HeloDAOSQLite;
import org.jonatancarbonellmartinez.model.dao.PersonDAOSQLite;
import org.jonatancarbonellmartinez.model.entities.*;
import org.jonatancarbonellmartinez.observers.Observer;
import org.jonatancarbonellmartinez.view.DialogView;
import org.jonatancarbonellmartinez.view.RegisterFlightDialogView;
import org.jonatancarbonellmartinez.view.panels.PilotCardPanel;

import javax.swing.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RegisterFlightPresenter implements Presenter, DialogPresenter {

    private final HeloDAOSQLite heloDAO;
    private final EventDAOSQLite eventDAO;
    private final PersonDAOSQLite personDAO;
    private final FlightDAOSQLite flightDAO;
    private final RegisterFlightDialogView view;
    private final Observer observer;

    public RegisterFlightPresenter(RegisterFlightDialogView registerFlightDialogView, Observer observer) {
        this.view = registerFlightDialogView;
        this.heloDAO = DAOFactorySQLite.getInstance().createHeloDAO();
        this.eventDAO = DAOFactorySQLite.getInstance().createEventDAO();
        this.personDAO = DAOFactorySQLite.getInstance().createPersonDAO();
        this.flightDAO = DAOFactorySQLite.getInstance().createFlightDAO();
        this.observer = observer;
    }

    @Override
    public boolean isFormValid() {
        boolean isValid = isVueloCardValid() &&
                            arePilotCardsValid();
        return isValid;
    }

    @Override
    public void insertEntity() { // TODO quiza este llame a todos los metodos de insertX
        try {
            Flight flight = collectFlightData();

            flightDAO.insert(flight);
            DialogView.showMessage(view,"Vuelo añadido correctamente.");

            view.clearFields();

        } catch (DatabaseException ex) {
            DialogView.showError(view,"Error al añadir vuelo: ");
        } catch (Exception ex) {
            DialogPresenter.handleUnexpectedError(ex,view);
        }
    }

    public void insertFlight() {}
    public void insertPersonHour() {}
    public void insertMixHour() {}

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

    public void onAddPilotCardViewClicked() {
        view.addExtraPilotCardView();
    }

    public void onDeletePilotCardViewClicked() {
        view.deleteExtraPilotCardView();
    }

    public void onAddDvCardViewClicked() {
        view.addExtraDvCardView();
    }

    public void onDeleteDvCardViewClicked() {
        view.deleteExtraDvCardView();
    }

    @Override
    public Entity collectEntityData() { // TODO this will be the method who will manage all CollectXData method created in the methods below.
        // Maybe simply i just dont need it.
        return null;
    }

    public Flight collectFlightData() {
        Flight flight = new Flight();

        flight.setDateTime(view.getDateTimeSpinner());
        flight.setHelo(getForeignKey(view.getHeloBox().getSelectedItem()));
        flight.setEvent(getForeignKey(view.getEventBox().getSelectedItem()));
        flight.setPersonCta(getForeignKey(view.getPersonBox().getSelectedItem()));
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

//    public PersonHour collectPersonHourData() { // TODO
//
//    }

//    public MixHour collectMixHourData() { // TODO
//
//    }

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
        view.getCreatePilotButton().addActionListener(e -> onAddPilotCardViewClicked());
        view.getDeletePilotButton().addActionListener(e -> onDeletePilotCardViewClicked());
        view.getCreateDvButton().addActionListener(e -> onAddDvCardViewClicked());
        view.getDeleteDvButton().addActionListener(e -> onDeleteDvCardViewClicked());
    }

    public List<Helo> getHeloList() {
            return heloDAO.getAll();
    }

    public List<Event> getEventList() {
        return eventDAO.getAll();
    }

    public List<Person> getOnlyActualPilots() {
        return  personDAO.getOnlyActualPilots();
    }

    public List<Person> getOnlyActualDvs() {
        return  personDAO.getOnlyActualDvs();
    }

    private boolean isVueloCardValid() {
        boolean isValid = DialogPresenter.validateDynamicComboBox(view, view.getHeloBox(),"Helicóptero") &&
                            DialogPresenter.validateDynamicComboBox(view, view.getEventBox(),"Evento") &&
                            DialogPresenter.validateDynamicComboBox(view, view.getPersonBox(), "Cte. Aeronave") &&
                            DialogPresenter.isAValidMandatoryHour(view, view.getTotalHoursField(),"Horas totales");
        return isValid;
    }

    private boolean arePilotCardsValid() { // TODO este manda, reune todos los checks
        boolean isValid = arePilotsSelected() &&
                            selectedPilotsAreNotRepeated() &&
                            isAnyFlightHourInsertedPerCard() &&
                            arePilotCardsHoursValid() &&
                            doesTotalHoursEqualsSumOfPilotHours() &&
                            areDvCardsHoursValid();
        return isValid;
    }

    private boolean arePilotsSelected() {
        // Validate pilot boxes for primary and secondary pilots
        boolean isPilot1Selected = DialogPresenter.validateDynamicComboBox(view, view.getPilotCardPanel1().getPilotBox(), "Primer PIL");
        boolean isPilot2Selected = DialogPresenter.validateDynamicComboBox(view, view.getPilotCardPanel2().getPilotBox(), "Segundo PIL");

        // Validate all extra pilot boxes
        boolean areExtraPilotsSelected = view.getExtraPilotCardPanelDeque()
                .stream()
                .allMatch(panel -> DialogPresenter.validateDynamicComboBox(view, panel.getPilotBox(), "Extra PIL"));

        // Return true only if all validations pass
        return isPilot1Selected && isPilot2Selected && areExtraPilotsSelected;
    }

    private boolean selectedPilotsAreNotRepeated() {
        // Collect all selected pilots from primary, secondary, and extra pilot panels
        Set<String> selectedPilots = new HashSet<>();

        // Check primary and secondary pilot boxes
        String pilot1 = view.getPilotCardPanel1().getPilotBox().getSelectedItem().toString();
        String pilot2 = view.getPilotCardPanel2().getPilotBox().getSelectedItem().toString();

        // Add pilots to the set if they are not null or empty
        if (pilot1 != null && !pilot1.isEmpty()) {
            selectedPilots.add(pilot1);
        }
        if (pilot2 != null && !pilot2.isEmpty()) {
            selectedPilots.add(pilot2);
        }

        // Check extra pilot combo boxes
        view.getExtraPilotCardPanelDeque().forEach(panel -> {
            String extraPilot = panel.getPilotBox().getSelectedItem().toString();
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

    private boolean isAnyFlightHourInsertedPerCard() {

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

    private boolean isAtLeastOneHourFieldInserted(PilotCardPanel panel) {
        return !panel.getDayHourField().getText().equals("D") ||
                !panel.getNightHourField().getText().equals("N") ||
                !panel.getGvnHourField().getText().equals("G");
    }

    private boolean doesTotalHoursEqualsSumOfPilotHours() {
        // Parse total hours from the text field, treating non-numeric placeholders as 0
        double totalHours = parseDoubleOrZero(view.getTotalHoursField().getText());

        // Sum the hours of the first pilot (Aircraft Commander)
        double sumOfAircraftCommanderHours = parseDoubleOrZero(view.getPilotCardPanel1().getDayHourField().getText()) +
                parseDoubleOrZero(view.getPilotCardPanel1().getNightHourField().getText()) +
                parseDoubleOrZero(view.getPilotCardPanel1().getGvnHourField().getText());

        // Sum the hours of the second pilot (Other Pilot)
        double sumOfOtherPilotsHours = parseDoubleOrZero(view.getPilotCardPanel2().getDayHourField().getText()) +
                parseDoubleOrZero(view.getPilotCardPanel2().getNightHourField().getText()) +
                parseDoubleOrZero(view.getPilotCardPanel2().getGvnHourField().getText());

        // Add the hours from extra pilot card panels
        for (PilotCardPanel extraPanel : view.getExtraPilotCardPanelDeque()) {
            sumOfOtherPilotsHours += parseDoubleOrZero(extraPanel.getDayHourField().getText()) +
                    parseDoubleOrZero(extraPanel.getNightHourField().getText()) +
                    parseDoubleOrZero(extraPanel.getGvnHourField().getText());
        }

        // Calculate total sum of hours of both pilots + extra panels
        double sumOfPilotHours = sumOfAircraftCommanderHours + sumOfOtherPilotsHours;

        // Check if total hours match the sum of pilot hours
        if (!(totalHours == sumOfPilotHours)) {
            // Show error message if hours don't match
            JOptionPane.showMessageDialog(view, "Las horas totales del vuelo no coinciden con la suma de las horas de los pilotos.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    // Helper method to safely parse a double, treating non-numeric values as 0
    private double parseDoubleOrZero(String text) {
        try {
            // Attempt to parse the value, if it's valid
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            // If parsing fails (e.g., non-numeric value), return 0
            return 0.0;
        }
    }

    private boolean arePilotCardsHoursValid() {
        boolean isValid =
                // PILOT CARD PANEL 1
                // Check Horas fields
                DialogPresenter.isAValidOptionalHour(view, view.getPilotCardPanel1().getDayHourField(),view.getPilotCardPanel1().getPilotBox().getSelectedItem() + " Horas Vuelo Dia", "D") &&
                DialogPresenter.isAValidOptionalHour(view, view.getPilotCardPanel1().getNightHourField(), view.getPilotCardPanel1().getPilotBox().getSelectedItem() + " Horas Vuelo Noche", "N") &&
                DialogPresenter.isAValidOptionalHour(view, view.getPilotCardPanel1().getGvnHourField(), view.getPilotCardPanel1().getPilotBox().getSelectedItem() + " Horas Vuelo GVN", "G") &&
                DialogPresenter.isAValidOptionalHour(view, view.getPilotCardPanel1().getRealIftHourField(),view.getPilotCardPanel1().getPilotBox().getSelectedItem() + " Horas Instrumentos Real", "R") &&
                DialogPresenter.isAValidOptionalHour(view, view.getPilotCardPanel1().getSimIftHourField(),view.getPilotCardPanel1().getPilotBox().getSelectedItem() + " Horas Instrumentos Simulado", "S") &&
                DialogPresenter.isAValidOptionalHour(view, view.getPilotCardPanel1().getHdmsHourField(),view.getPilotCardPanel1().getPilotBox().getSelectedItem() + " Horas HDMS", "H") &&
                DialogPresenter.isAValidOptionalHour(view, view.getPilotCardPanel1().getInstructorHourField(),view.getPilotCardPanel1().getPilotBox().getSelectedItem() + " Horas Instructor", "I") &&
                // Check Precision fields
                DialogPresenter.isAValidOptionalNumber(view, view.getPilotCardPanel1().getPrecisionField(), view.getPilotCardPanel1().getPilotBox().getSelectedItem() + " Aproximación de Precisión", "P") &&
                DialogPresenter.isAValidOptionalNumber(view, view.getPilotCardPanel1().getNoPrecisionField(), view.getPilotCardPanel1().getPilotBox().getSelectedItem() + " Aproximación de No precisión", "N") &&
                DialogPresenter.isAValidOptionalNumber(view, view.getPilotCardPanel1().getSarnField(), view.getPilotCardPanel1().getPilotBox().getSelectedItem() + " Aproximación SAR-N", "S") &&
                // Check Tomas fields
                DialogPresenter.isAValidOptionalNumber(view, view.getPilotCardPanel1().getMonoDayField(), view.getPilotCardPanel1().getPilotBox().getSelectedItem() + " Toma Monospot Día", "D") &&
                DialogPresenter.isAValidOptionalNumber(view, view.getPilotCardPanel1().getMonoNightField(), view.getPilotCardPanel1().getPilotBox().getSelectedItem() + " Toma Monospot Noche", "N") &&
                DialogPresenter.isAValidOptionalNumber(view, view.getPilotCardPanel1().getMonoGvnField(), view.getPilotCardPanel1().getPilotBox().getSelectedItem() + " Toma Monospot GVN", "G") &&
                // Check Tomas fields
                DialogPresenter.isAValidOptionalNumber(view, view.getPilotCardPanel1().getMultiDayField(), view.getPilotCardPanel1().getPilotBox().getSelectedItem() + " Toma Multispot Día", "D") &&
                DialogPresenter.isAValidOptionalNumber(view, view.getPilotCardPanel1().getMultiNightField(), view.getPilotCardPanel1().getPilotBox().getSelectedItem() + " Toma Multispot Noche", "N") &&
                DialogPresenter.isAValidOptionalNumber(view, view.getPilotCardPanel1().getMultiGvnField(), view.getPilotCardPanel1().getPilotBox().getSelectedItem() + " Toma Multispot GVN", "G") &&
                // Check Tomas fields
                DialogPresenter.isAValidOptionalNumber(view, view.getPilotCardPanel1().getTierraDayField(), view.getPilotCardPanel1().getPilotBox().getSelectedItem() + " Toma Tierra Día", "D") &&
                DialogPresenter.isAValidOptionalNumber(view, view.getPilotCardPanel1().getTierraNightField(), view.getPilotCardPanel1().getPilotBox().getSelectedItem() + " Toma Tierra Noche", "N") &&
                DialogPresenter.isAValidOptionalNumber(view, view.getPilotCardPanel1().getTierraGvnField(), view.getPilotCardPanel1().getPilotBox().getSelectedItem() + " Toma Tierra GVN", "G") &&

                // PILOT CARD PANEL 2
                // Check Horas fields
                DialogPresenter.isAValidOptionalHour(view, view.getPilotCardPanel2().getDayHourField(),view.getPilotCardPanel2().getPilotBox().getSelectedItem() + " Horas Vuelo Dia", "D") &&
                DialogPresenter.isAValidOptionalHour(view, view.getPilotCardPanel2().getNightHourField(), view.getPilotCardPanel2().getPilotBox().getSelectedItem() + " Horas Vuelo Noche", "N") &&
                DialogPresenter.isAValidOptionalHour(view, view.getPilotCardPanel2().getGvnHourField(), view.getPilotCardPanel2().getPilotBox().getSelectedItem() + " Horas Vuelo GVN", "G") &&
                DialogPresenter.isAValidOptionalHour(view, view.getPilotCardPanel2().getRealIftHourField(),view.getPilotCardPanel2().getPilotBox().getSelectedItem() + " Horas Instrumentos Real", "R") &&
                DialogPresenter.isAValidOptionalHour(view, view.getPilotCardPanel2().getSimIftHourField(),view.getPilotCardPanel2().getPilotBox().getSelectedItem() + " Horas Instrumentos Simulado", "S") &&
                DialogPresenter.isAValidOptionalHour(view, view.getPilotCardPanel2().getHdmsHourField(),view.getPilotCardPanel2().getPilotBox().getSelectedItem() + " Horas HDMS", "H") &&
                DialogPresenter.isAValidOptionalHour(view, view.getPilotCardPanel2().getInstructorHourField(),view.getPilotCardPanel2().getPilotBox().getSelectedItem() + " Horas Instructor", "I") &&
                // Check Precision fields
                DialogPresenter.isAValidOptionalNumber(view, view.getPilotCardPanel2().getPrecisionField(), view.getPilotCardPanel2().getPilotBox().getSelectedItem() + " Aproximación de Precisión", "P") &&
                DialogPresenter.isAValidOptionalNumber(view, view.getPilotCardPanel2().getNoPrecisionField(), view.getPilotCardPanel2().getPilotBox().getSelectedItem() + " Aproximación de No precisión", "N") &&
                DialogPresenter.isAValidOptionalNumber(view, view.getPilotCardPanel2().getSarnField(), view.getPilotCardPanel2().getPilotBox().getSelectedItem() + " Aproximación SAR-N", "S") &&
                // Check Tomas fields
                DialogPresenter.isAValidOptionalNumber(view, view.getPilotCardPanel2().getMonoDayField(), view.getPilotCardPanel2().getPilotBox().getSelectedItem() + " Toma Monospot Día", "D") &&
                DialogPresenter.isAValidOptionalNumber(view, view.getPilotCardPanel2().getMonoNightField(), view.getPilotCardPanel2().getPilotBox().getSelectedItem() + " Toma Monospot Noche", "N") &&
                DialogPresenter.isAValidOptionalNumber(view, view.getPilotCardPanel2().getMonoGvnField(), view.getPilotCardPanel2().getPilotBox().getSelectedItem() + " Toma Monospot GVN", "G") &&
                // Check Tomas fields
                DialogPresenter.isAValidOptionalNumber(view, view.getPilotCardPanel2().getMultiDayField(), view.getPilotCardPanel2().getPilotBox().getSelectedItem() + " Toma Multispot Día", "D") &&
                DialogPresenter.isAValidOptionalNumber(view, view.getPilotCardPanel2().getMultiNightField(), view.getPilotCardPanel2().getPilotBox().getSelectedItem() + " Toma Multispot Noche", "N") &&
                DialogPresenter.isAValidOptionalNumber(view, view.getPilotCardPanel2().getMultiGvnField(), view.getPilotCardPanel2().getPilotBox().getSelectedItem() + " Toma Multispot GVN", "G") &&
                // Check Tomas fields
                DialogPresenter.isAValidOptionalNumber(view, view.getPilotCardPanel2().getTierraDayField(), view.getPilotCardPanel2().getPilotBox().getSelectedItem() + " Toma Tierra Día", "D") &&
                DialogPresenter.isAValidOptionalNumber(view, view.getPilotCardPanel2().getTierraNightField(), view.getPilotCardPanel2().getPilotBox().getSelectedItem() + " Toma Tierra Noche", "N") &&
                DialogPresenter.isAValidOptionalNumber(view, view.getPilotCardPanel2().getTierraGvnField(), view.getPilotCardPanel2().getPilotBox().getSelectedItem() + " Toma Tierra GVN", "G");
        return isValid;
    }

    private boolean areDvCardsHoursValid() {
        boolean isValid =
                // DV CARD PANEL 1
                // Check Horas fields
                DialogPresenter.isAValidOptionalHour(view, view.getDvCardPanel1().getDayHourField(),view.getDvCardPanel1().getDvBox().getSelectedItem() + " Horas Vuelo Dia", "D") &&
                DialogPresenter.isAValidOptionalHour(view, view.getDvCardPanel1().getNightHourField(),view.getDvCardPanel1().getDvBox().getSelectedItem() + " Horas Vuelo Noche", "N") &&
                DialogPresenter.isAValidOptionalHour(view, view.getDvCardPanel1().getGvnHourField(),view.getDvCardPanel1().getDvBox().getSelectedItem() + " Horas Vuelo Gvn", "G") &&
                DialogPresenter.isAValidOptionalHour(view, view.getDvCardPanel1().getWinchTrimHourField(),view.getDvCardPanel1().getDvBox().getSelectedItem() + " Horas Winch Trim", "W") &&
                // Check Proyectiles fields
                DialogPresenter.isAValidOptionalNumber(view, view.getDvCardPanel1().getM3mField(), view.getDvCardPanel1().getDvBox().getSelectedItem() + " Proyectil M3M", "P") &&
                DialogPresenter.isAValidOptionalNumber(view, view.getDvCardPanel1().getMagField(), view.getDvCardPanel1().getDvBox().getSelectedItem() + " Proyectil MAG56", "P");

                return isValid;
    }
}
