package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.factory.DAOFactory;
import org.jonatancarbonellmartinez.model.dao.PersonDAO;
import org.jonatancarbonellmartinez.observers.Observer;
import org.jonatancarbonellmartinez.view.*;

import java.awt.*;

public class MainPresenter implements Observer, Presenter {
    private final MainView view;
    private final DAOFactory daoFactory; // This reference will allow me to create DAOs trough methods (like showAddPersonView is doing)

    public MainPresenter(MainView view, DAOFactory daoFactory) {
        this.view = view;
        this.daoFactory = daoFactory; // DAOFactory instance is passed in order to create DAOs from the MainPresenter.
    }

    @Override
    public void setActionListeners() {
        view.getBotonPersonal().addActionListener(e -> showPersonCardView());
        view.getAddPersonalMenuItem().addActionListener(e -> showAddPersonView());
        view.getEditPersonalMenuItem().addActionListener(e -> showEditPersonView());
//        mainView.getPersonMenuItem().addActionListener(e -> showView("person"));
//        mainView.getFlightMenuItem().addActionListener(e -> showView("flight"));
//        mainView.getEventMenuItem().addActionListener(e -> showView("event"));
    }

    // Methods to show New Windows, like JDialogs.
    private void showAddPersonView() {
        PersonDAO personDAOSQLite = daoFactory.createPersonDAOSQLite(); // Here the PersonDAOSQLITE instance is created! (At the moment the button is clicked)
        new PersonDialogView(view, personDAOSQLite, this,false); // ese this lo he metido tras implementar la logica del observer, quiza se quite cuando me aclare con ese patron.
    }

    private void showEditPersonView() {
        PersonDAO personDAOSQLite = daoFactory.createPersonDAOSQLite(); // Here the PersonDAOSQLITE instance is created! (At the moment the button is clicked)
        new PersonDialogView(view, personDAOSQLite, this,true); // ese this lo he metido tras implementar la logica del observer, quiza se quite cuando me aclare con ese patron.
    }

    // Methods to show cards
    public void showPersonCardView() {
        CardLayout cardLayout = (CardLayout) view.getCardPanel().getLayout(); // Get the CardLayout

        // Check if there are any components in the card panel
        int componentCount = view.getCardPanel().getComponentCount();
        boolean isPersonViewVisible = componentCount > 0 && view.getCardPanel().getComponent(0) instanceof PersonPanelView;

        if (!isPersonViewVisible) {
            // If there are no components or the first component isn't PersonCardView, create and add a new one
            PersonDAO personDAOSQLite = daoFactory.createPersonDAOSQLite(); // Create the PersonDAO instance
            PersonPanelView personPanelView = new PersonPanelView(personDAOSQLite);
            view.getCardPanel().add(personPanelView, "Person View"); // Add new card
        }

        // Show the Person View card
        cardLayout.show(view.getCardPanel(), "Person View");

        // Refresh the panel
        view.getCardPanel().revalidate();
        view.getCardPanel().repaint();
    }

    @Override
    public void update() {
        if (view.getCardPanel().getComponentCount() > 0) {
            // Safe to access the first component
            Component currentComponent = view.getCardPanel().getComponent(0);
            if (currentComponent instanceof PersonPanelView) {
                PersonPanelView personPanelView = (PersonPanelView) currentComponent;
                personPanelView.updatePanel();
            } //else if (currentComponent instanceof EventPanelView) {
                //EventPanelView eventPanelView = (EventPanelView) currentComponent;
                //eventPanelView.updatePanel();
            //}
        }
    }
}
