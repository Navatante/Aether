package org.jonatancarbonellmartinez.presenter.RegisterFlightPresenter;

import org.jonatancarbonellmartinez.factory.DAOFactorySQLite;
import org.jonatancarbonellmartinez.model.dao.PersonDAOSQLite;
import org.jonatancarbonellmartinez.model.entities.Person;
import org.jonatancarbonellmartinez.presenter.Presenter;
import org.jonatancarbonellmartinez.view.RegisterFlightView.DvCardView;

import java.util.List;

public class DvCardPresenter implements Presenter {
    private final PersonDAOSQLite personDAO;
    private final DvCardView view;

    public DvCardPresenter(DvCardView dvCardView) {
        this.view = dvCardView;
        this.personDAO = DAOFactorySQLite.getInstance().createPersonDAO();
    }
    @Override
    public void setActionListeners() {

    }

    public List<Person> getOnlyActualDvs() {
        return  personDAO.getOnlyActualDVs();
    }
}
