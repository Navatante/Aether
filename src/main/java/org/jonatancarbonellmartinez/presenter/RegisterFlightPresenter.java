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

import javax.swing.*;
import java.util.List;

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

    private boolean arePilotCardsValid() {
        boolean isValid = isAnyFlightHourInserted() &&
                            arePilotCardsHoursValid();
        return isValid;
    }

    private boolean isAnyFlightHourInserted() {
        boolean isDayHourDefault = view.getPilotCardPanel1().getDayHourField().equals("D");
        boolean isNightHourDefault = view.getPilotCardPanel2().getNightHourField().equals("N");
        boolean isGvnHourDefault = view.getPilotCardPanel1().getGvnHourField().equals("G");

        if (isDayHourDefault && isNightHourDefault && isGvnHourDefault) {
            JOptionPane.showMessageDialog(view, "Inserte almenos una hora de vuelo", "Error", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        return false;
    }


    private boolean arePilotCardsHoursValid() {
        boolean isValid = DialogPresenter.validateDynamicComboBox(view, view.getPilotCardPanel1().getPilotBox(),"PIL") &&
                DialogPresenter.isAValidOptionalHour(view, view.getPilotCardPanel1().getDayHourField(),view.getPilotCardPanel1().getPilotBox().getSelectedItem() + " Horas Dia", "D") &&
                DialogPresenter.isAValidOptionalHour(view, view.getPilotCardPanel2().getNightHourField(), view.getPilotCardPanel1().getPilotBox().getSelectedItem() + " Horas Noche", "N") &&
                DialogPresenter.isAValidOptionalHour(view, view.getPilotCardPanel2().getGvnHourField(), view.getPilotCardPanel1().getPilotBox().getSelectedItem() + " Horas GVN", "G");
        return isValid;
    }
}
