package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.factory.DAOFactorySQLite;
import org.jonatancarbonellmartinez.model.dao.EventDAOSQLite;
import org.jonatancarbonellmartinez.model.dao.GenericDAO;
import org.jonatancarbonellmartinez.model.dao.HeloDAOSQLite;
import org.jonatancarbonellmartinez.model.dao.PersonDAOSQLite;
import org.jonatancarbonellmartinez.model.entities.Entity;
import org.jonatancarbonellmartinez.model.entities.Event;
import org.jonatancarbonellmartinez.model.entities.Helo;
import org.jonatancarbonellmartinez.model.entities.Person;
import org.jonatancarbonellmartinez.observers.Observer;
import org.jonatancarbonellmartinez.view.RegisterFlightView.RegisterFlightDialogView;

import java.util.List;

public class RegisterFlightPresenter implements Presenter, DialogPresenter {
    private final HeloDAOSQLite heloDAO;
    private final EventDAOSQLite eventDAO;
    private final PersonDAOSQLite personDAO;
    private final RegisterFlightDialogView view;
    private final Observer observer;

    public RegisterFlightPresenter(RegisterFlightDialogView registerFlightDialogView, Observer observer) {
        this.view = registerFlightDialogView;
        this.heloDAO = DAOFactorySQLite.getInstance().createHeloDAOSQLite();
        this.eventDAO = DAOFactorySQLite.getInstance().createEventDAOSQLite();
        this.personDAO = DAOFactorySQLite.getInstance().createPersonDAOSQLite();
        this.observer = observer;
    }

    @Override
    public boolean isFormValid() {
        return false;
    }

    @Override
    public void addEntity() {

    }

    @Override
    public void editEntity() {

    }

    @Override
    public void getEntity(int entityId) {

    }

    @Override
    public void onSaveButtonClicked() {

    }

    @Override
    public Entity collectEntityData() {
        return null;
    }

    @Override
    public void populateEntityDialog(Entity entity) {

    }

    @Override
    public void notifyObserver() {

    }

    @Override
    public void setActionListeners() {

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
}
