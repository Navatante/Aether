package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.factory.DAOFactory;
import org.jonatancarbonellmartinez.model.dao.PersonDAO;
import org.jonatancarbonellmartinez.observers.PersonObserver;
import org.jonatancarbonellmartinez.view.*;

import java.awt.*;

public class MainPresenter implements PersonObserver {
    private final MainView mainView;
    private final DAOFactory daoFactory; // This reference will allow me to create DAOs trough methods (like showAddPersonView is doing)

    public MainPresenter(MainView mainView, DAOFactory daoFactory) {
        this.mainView = mainView;
        this.daoFactory = daoFactory; // DAOFactory instance is passed in order to create DAOs from the MainPresenter.
        bindViewActions();
    }

    private void bindViewActions() {
        mainView.getBotonPersonal().addActionListener(e -> showPersonCardView());
        mainView.getAddPersonalMenuItem().addActionListener(e -> showAddPersonView());
        mainView.getEditPersonalMenuItem().addActionListener(e -> showEditPersonView());
//        mainView.getPersonMenuItem().addActionListener(e -> showView("person"));
//        mainView.getFlightMenuItem().addActionListener(e -> showView("flight"));
//        mainView.getEventMenuItem().addActionListener(e -> showView("event"));
    }

    // Methods to show New Windows, like JDialogs.
    private void showAddPersonView() {
        PersonDAO personDAOSQLite = daoFactory.createPersonDAOSQLite(); // Here the PersonDAOSQLITE instance is created! (At the moment the button is clicked)
        new PersonDialogView(mainView, personDAOSQLite, this,false); // ese this lo he metido tras implementar la logica del observer, quiza se quite cuando me aclare con ese patron.
    }

    private void showEditPersonView() {
        PersonDAO personDAOSQLite = daoFactory.createPersonDAOSQLite(); // Here the PersonDAOSQLITE instance is created! (At the moment the button is clicked)
        new PersonDialogView(mainView, personDAOSQLite, this,true); // ese this lo he metido tras implementar la logica del observer, quiza se quite cuando me aclare con ese patron.
    }

    // Methods to show cards
    public void showPersonCardView() {
        CardLayout cardLayout = (CardLayout) mainView.getCardPanel().getLayout(); // Get the CardLayout

        // Check if there are any components in the card panel
        int componentCount = mainView.getCardPanel().getComponentCount();
        boolean isPersonViewVisible = componentCount > 0 && mainView.getCardPanel().getComponent(0) instanceof PersonPanelView;

        if (!isPersonViewVisible) {
            // If there are no components or the first component isn't PersonCardView, create and add a new one
            PersonDAO personDAOSQLite = daoFactory.createPersonDAOSQLite(); // Create the PersonDAO instance
            PersonPanelView personCardView = new PersonPanelView(personDAOSQLite);
            mainView.getCardPanel().add(personCardView, "Person View"); // Add new card
        }

        // Show the Person View card
        cardLayout.show(mainView.getCardPanel(), "Person View");

        // Refresh the panel
        mainView.getCardPanel().revalidate();
        mainView.getCardPanel().repaint();
    }

    @Override
    public void onPersonChanges() {
        if (mainView.getCardPanel().getComponentCount() > 0) { // Check if there are any component to avoid nullPointerException
            // Safe to access the first component
            // Get the current component in the card panel and check if it's an instance of PersonCardView
            Component currentComponent = mainView.getCardPanel().getComponent(0);
            if (currentComponent instanceof PersonPanelView) {
                PersonPanelView personCardView = (PersonPanelView) currentComponent;
                personCardView.showPanel(); // Refresh the table by reloading all persons
            }
        }
    }

}
