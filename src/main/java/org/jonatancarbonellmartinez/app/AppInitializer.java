package org.jonatancarbonellmartinez.app;

import org.jonatancarbonellmartinez.factory.*;
import org.jonatancarbonellmartinez.utilities.DatabasePresenter;
import org.jonatancarbonellmartinez.utilities.DbFileChooserView;
import org.jonatancarbonellmartinez.view.*;

public class AppInitializer {

    public void initialize() {

            // Initialize the DatabasePresenter and File Chooser View.
            DatabasePresenter dbPresenter = new DatabasePresenter(new DbFileChooserView());

            // Initialize the DAOFactory
            DAOFactory daoFactory = DAOFactorySQLite.getInstance();

            // Initialize the MainViewt
            MainView mainView = new MainView(daoFactory);

            // Setup main view visibility
            mainView.setVisible(true);
    }
}
