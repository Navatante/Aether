package org.jonatancarbonellmartinez.app;

import org.jonatancarbonellmartinez.factory.DAOFactorySQLite;
import org.jonatancarbonellmartinez.model.dao.PersonDAO;
import org.jonatancarbonellmartinez.presenter.*;
import org.jonatancarbonellmartinez.view.*;

import javax.swing.*;
import java.sql.SQLException;

public class AppInitializer {

    public static void initialize() {
        try {
            // Initialize the file chooser view
            DbFileChooserView dbFileChooserView = new DbFileChooserView();
            // Initialize the DatabasePresenter, which manages the database connection
            DatabasePresenter dbPresenter = new DatabasePresenter(dbFileChooserView);

            // Initialize DAOs at startup
            DAOFactorySQLite daoFactory = DAOFactorySQLite.getInstance(); // First create the Factory os DAOs!
            PersonDAO personDAO = daoFactory.createDimPersonDAO();
            // FlightDAO flightDAO = daoFactory.createFlightDAO();
            // EventDAO eventDAO = daoFactory.createEventDAO();


            // Initialize Presenters at startup
            MainPresenter mainPresenter = new MainPresenter();
            AddPersonPresenter addPersonPresenter = new AddPersonPresenter(personDAO);
            EditPersonPresenter editPersonPresenter = new EditPersonPresenter(personDAO);
            // FlightPresenter flightPresenter = new FlightPresenter(flightDAO);
            // EventPresenter eventPresenter = new EventPresenter(eventDAO);


            // Add Presenters to the Map list of presenters located on MainPresenter
            mainPresenter.addPresenter("person", addPersonPresenter);


            // Initialize the main view
            MainView mainView = new MainView();

            // Set the presenter to the view and view to the presenter
            mainView.setPresenter(mainPresenter); // Set the presenter
            mainPresenter.setView(mainView); // Set the view

            // Setup main view visibility
            mainView.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error connecting to the database: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
