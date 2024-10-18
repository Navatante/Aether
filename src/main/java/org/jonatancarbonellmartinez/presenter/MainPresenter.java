package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.app.AppInitializer;
import org.jonatancarbonellmartinez.model.dao.PersonDAOSQLite;
import org.jonatancarbonellmartinez.view.*;

import javax.swing.*;
import java.util.*;
// I think this presenter should have a reference to interact with a bunch of modelDAOs
public class MainPresenter {
    private MainView mainView;

    public MainPresenter() {
       // bindViewActions(); // Bind actions immediately after initialization
    }

    public void setView(MainView mainView) {
        this.mainView = mainView;  // Now you have a reference to the view
        bindViewActions();         // Bind actions after the view is set
    }

    private void bindViewActions() {
        mainView.getPersonalMenuItem().addActionListener(e -> showAddPersonView());
//        mainView.getPersonMenuItem().addActionListener(e -> showView("person"));
//        mainView.getFlightMenuItem().addActionListener(e -> showView("flight"));
//        mainView.getEventMenuItem().addActionListener(e -> showView("event"));
    }

    // Method to handle showing the JDialog AddPerson view, it creates the VIEW, PRESENTER and MODEL with a fresh new instance. Best practice.
    private void showAddPersonView() {
        AddPersonView addPersonView = new AddPersonView(); // Create a VIEW new instance.
        AddPersonPresenter addPersonPresenter = new AddPersonPresenter(AppInitializer.daoFactory.createPersonDAOSQLite()); // Create a new instance of a PRESENTER passing a fresh created MODEL.
        addPersonPresenter.setView(addPersonView); // Set the view to the presenter
        addPersonPresenter.setMainViewReference(mainView);  // Pass the MainView reference to presenter
    }
}
