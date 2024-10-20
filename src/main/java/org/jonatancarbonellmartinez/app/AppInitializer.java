package org.jonatancarbonellmartinez.app;

import org.jonatancarbonellmartinez.factory.*;
import org.jonatancarbonellmartinez.presenter.*;
import org.jonatancarbonellmartinez.view.*;

public class AppInitializer {

    public void initialize() {

            // Initialize the DatabasePresenter and File Chooser View.
            DatabasePresenter dbPresenter = new DatabasePresenter(new DbFileChooserView());

            // Initialize the DAOFactory
            DAOFactory daoFactory = DAOFactorySQLite.getInstance();

            // Initialize the MainView
            MainView mainView = new MainView(daoFactory);

            // Setup main view visibility
            mainView.setVisible(true);
    }
}
