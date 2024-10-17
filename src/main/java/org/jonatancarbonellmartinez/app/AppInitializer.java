package org.jonatancarbonellmartinez.app;

import org.jonatancarbonellmartinez.factory.DAOFactorySQLite;
import org.jonatancarbonellmartinez.model.dao.PersonDAO;
import org.jonatancarbonellmartinez.presenter.DatabasePresenter;
import org.jonatancarbonellmartinez.presenter.PersonPresenter;
import org.jonatancarbonellmartinez.view.DbFileChooserView;
import org.jonatancarbonellmartinez.view.MainView;
import org.jonatancarbonellmartinez.view.PersonView;

import javax.swing.*;
import java.sql.SQLException;

public class AppInitializer {

    public static void initialize() {
        try {
            // Initialize the file chooser view
            DbFileChooserView dbFileChooserView = new DbFileChooserView();

            // Initialize the DatabasePresenter, which manages the database connection
            DatabasePresenter dbPresenter = new DatabasePresenter(dbFileChooserView);

            // Get DAO Factory and create DAOs regardless of connection state
            DAOFactorySQLite daoFactory = DAOFactorySQLite.getInstance();
            PersonDAO personDAO = daoFactory.createDimPersonDAO();

            // Initialize the views
            PersonView personView = new PersonView();

            // Initialize the presenter with DAO and view
            PersonPresenter personPresenter = new PersonPresenter(personDAO, personView);

            // Initialize the main view (optional)
            MainView mainView = new MainView(personPresenter);
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
