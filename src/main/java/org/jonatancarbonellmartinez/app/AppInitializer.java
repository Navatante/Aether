package org.jonatancarbonellmartinez.app;

import org.jonatancarbonellmartinez.controller.DatabaseController;
import org.jonatancarbonellmartinez.controller.PersonController;
import org.jonatancarbonellmartinez.factory.SQLiteDAOFactory;
import org.jonatancarbonellmartinez.model.dao.DimPersonDAO;
import org.jonatancarbonellmartinez.view.DatabaseFileChooserView;
import org.jonatancarbonellmartinez.view.MainView;
import org.jonatancarbonellmartinez.view.PersonView;

import javax.swing.*;
import java.sql.SQLException;

public class AppInitializer {

    public static void initialize() {
        try {
            // Initialize the file chooser view and controller
            DatabaseFileChooserView dbFileChooserView = new DatabaseFileChooserView();
            DatabaseController dbController = new DatabaseController(dbFileChooserView);

            // Obtenemos la instancia de la fábrica DAO
            SQLiteDAOFactory daoFactory = SQLiteDAOFactory.getInstance();  // Usamos el patrón Singleton

            // Inicializamos los DAOs necesarios
            DimPersonDAO personDAO = daoFactory.createDimPersonDAO();

            // Creamos la vista
            PersonView personView = new PersonView();

            // Creamos el controlador y le pasamos el DAO y la vista
            PersonController personController = new PersonController(personDAO, personView);

            // Asignamos el controlador a la vista
            personView.setController(personController);

            // Registro la vista como observadora
            personView.registerAsObserver();

            // Inicializamos la vista principal que integra todas las sub-vistas
            MainView mainView = new MainView(personController);

            // Mostramos la vista principal
            mainView.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error al conectarse a la base de datos: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
