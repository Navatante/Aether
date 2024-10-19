package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.factory.DAOFactory;
import org.jonatancarbonellmartinez.model.dao.PersonDAO;
import org.jonatancarbonellmartinez.view.*;

public class MainPresenter {
    private final MainView mainView;
    private final DAOFactory daoFactory; // This reference will allow me to create DAOs trough methods (like showAddPersonView is doing)

    public MainPresenter(MainView mainView, DAOFactory daoFactory) {
        this.mainView = mainView;
        this.daoFactory = daoFactory; // DAOFactory instance is passed in order to create DAOs from the MainPresenter.
        bindViewActions();
    }

    private void bindViewActions() {
        mainView.getPersonalMenuItem().addActionListener(e -> showAddPersonView());
//        mainView.getPersonMenuItem().addActionListener(e -> showView("person"));
//        mainView.getFlightMenuItem().addActionListener(e -> showView("flight"));
//        mainView.getEventMenuItem().addActionListener(e -> showView("event"));
    }

    private void showAddPersonView() {
        PersonDAO personDAOSQLite = daoFactory.createPersonDAOSQLite(); // Here the PersonDAOSQLITE instance is created! (At the moment the button is clicked)
        new AddPersonView(mainView, personDAOSQLite);
    }
}
