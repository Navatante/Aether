package org.jonatancarbonellmartinez.app;

import org.jonatancarbonellmartinez.factory.SQLiteDAOFactory;
import org.jonatancarbonellmartinez.model.dao.DimPersonDAO;
import org.jonatancarbonellmartinez.presenter.PersonPresenter;
import org.jonatancarbonellmartinez.view.MainView;
import org.jonatancarbonellmartinez.view.PersonView;

import javax.swing.*;
import java.sql.SQLException;

public class AppInitializer {

    public static void initialize() {
        try {
            // Initialize the file chooser view (optional)
            // DatabaseFileChooserView dbFileChooserView = new DatabaseFileChooserView();

            // Get DAO Factory and create DAOs
            SQLiteDAOFactory daoFactory = SQLiteDAOFactory.getInstance();
            DimPersonDAO personDAO = daoFactory.createDimPersonDAO();

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
