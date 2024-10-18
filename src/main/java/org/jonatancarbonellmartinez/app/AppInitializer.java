package org.jonatancarbonellmartinez.app;

import org.jonatancarbonellmartinez.factory.DAOFactorySQLite;
import org.jonatancarbonellmartinez.presenter.*;
import org.jonatancarbonellmartinez.view.*;

import javax.swing.*;
import java.sql.SQLException;

public class AppInitializer {
    public static final DAOFactorySQLite daoFactory = DAOFactorySQLite.getInstance(); // I will access this daoFactory from Presenter's methods who show views.

    public static void initialize() {
        try {
            // Initialize the file chooser view
            DbFileChooserView dbFileChooserView = new DbFileChooserView();

            // Initialize the DatabasePresenter, which manages the database connection
            DatabasePresenter dbPresenter = new DatabasePresenter(dbFileChooserView);

            // Initialize needed Presenters at startup and pass them their DAO.
            MainPresenter mainPresenter = new MainPresenter();

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
