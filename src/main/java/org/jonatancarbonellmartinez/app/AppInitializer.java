package org.jonatancarbonellmartinez.app;

import org.jonatancarbonellmartinez.controller.PersonController;
import org.jonatancarbonellmartinez.factory.SQLiteDAOFactory;
import org.jonatancarbonellmartinez.model.Database;
import org.jonatancarbonellmartinez.model.dao.DimPersonDAO;
import org.jonatancarbonellmartinez.view.MainView;
import org.jonatancarbonellmartinez.view.PersonView;

import javax.swing.*;
import java.sql.SQLException;

public class AppInitializer {

    public static void initialize() {
        try {
            // Obtenemos la instancia de la fábrica DAO
            SQLiteDAOFactory daoFactory = SQLiteDAOFactory.getInstance();  // Usamos el patrón Singleton

            // Inicializamos los DAOs necesarios
            DimPersonDAO personDAO = daoFactory.createDimPersonDAO();
            // Puedes seguir creando otros DAOs si los necesitas:
            // DimHeloDAO heloDAO = daoFactory.createDimHeloDAO();

            // Creamos la vista
            PersonView personView = new PersonView();
            personView.setVisible(false); // la oculto para solo mostrarla cuando la necesite. creo que esto no es buena practica, investigar si es mejor que cree las vistas y controladores a demanda.
            /**
             * Híbrido: Una práctica común es combinar ambos enfoques. Puedes inicializar los componentes
             * que son críticos para la interfaz de usuario al inicio y cargar los demás a demanda. Por ejemplo,
             * si tienes una pantalla principal que se usa constantemente, la puedes cargar al inicio y otros paneles menos usados se pueden cargar a demanda.
             *
             * No hay una respuesta definitiva, ya que la mejor práctica puede variar según las necesidades específicas de tu aplicación y su contexto.
             * Es recomendable considerar un enfoque que combine ambos métodos, aprovechando las ventajas de cada uno y minimizando sus desventajas.
             * Además, realizar pruebas de rendimiento y usabilidad puede ayudar a determinar el enfoque más adecuado para tu caso específico.
             */

            // Creamos el controlador y le pasamos el DAO y la vista
            PersonController personController = new PersonController(personDAO, personView);

            // Asignamos el controlador a la vista
            personView.setController(personController);

            // Registro la vista como observadora
            personView.registerAsObserver();

            // Inicializamos la vista principal que integra todas las sub-vistas
            MainView mainView = new MainView(personController); // esto debe ser mainViewController, tengo que hacerlo, ahora lo dejo asi para que no de error.

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
