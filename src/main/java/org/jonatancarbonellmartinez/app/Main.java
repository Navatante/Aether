package org.jonatancarbonellmartinez.app;

import org.jonatancarbonellmartinez.controller.PersonController;
import org.jonatancarbonellmartinez.factory.DAOFactory;
import org.jonatancarbonellmartinez.view.MainView;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // Establecemos el look and feel (FlatDarkLaf)
        FlatDarkLaf.setup();

        // Usamos SwingUtilities.invokeLater para asegurar que la GUI se ejecute en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Creamos el controlador que se encargará de la lógica de negocio
                DAOFactory daoFactory = new DAOFactory();
                PersonController personController = new PersonController(daoFactory.createDimPersonDAO());

                // Creamos la vista principal (MainView), que integrará las sub-vistas (como PersonView)
                new MainView(personController); // Pasamos el controlador a la vista principal
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
