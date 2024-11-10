package org.jonatancarbonellmartinez.presenter.RegisterFlightPresenter;

import org.jonatancarbonellmartinez.factory.DAOFactorySQLite;
import org.jonatancarbonellmartinez.model.dao.PersonDAOSQLite;
import org.jonatancarbonellmartinez.model.entities.Person;
import org.jonatancarbonellmartinez.presenter.Presenter;
import org.jonatancarbonellmartinez.view.RegisterFlightView.PilotCardView;

import java.util.List;

public class PilotCardPresenter implements Presenter {
    private final PersonDAOSQLite personDAO;
    //private final PersonHourDAOSQLite personHourDAO;
    //private final MixHorDAOSQLite mixHourDAO;
    //private final IfrAppDAOSQLite ifrAppDAO;
    //private final LandingDAOSQLite landingDAO;

    private final PilotCardView view;



    public PilotCardPresenter(PilotCardView pilotCardView) { // En principio no tiene Observer, el encargado de notificar al Observer es el RegisterFlightPresenter.
        this.view = pilotCardView;
        this.personDAO = DAOFactorySQLite.getInstance().createPersonDAO();
        // this.personHourDAO..
        //...

    }

    @Override
    public void setActionListeners() {

    }

    public List<Person> getPersons() {
        return  personDAO.getAll();
    }

    public List<Person> getOnlyActualPilots() {
        return  personDAO.getOnlyActualPilots();
    }

    public List<Person> getOnlyActualDvs() {
        return  personDAO.getOnlyActualDVs();
    }
}
