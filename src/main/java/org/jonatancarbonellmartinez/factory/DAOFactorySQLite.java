package org.jonatancarbonellmartinez.factory;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.dao.*;
import org.jonatancarbonellmartinez.model.entities.Event;
import org.jonatancarbonellmartinez.model.entities.Person;

// Esta clase la utilizo para crear los distintos DAOs a traves de ella, por cada dao, tendre que crear un metodo 'createDimNameDAO' or createFactNameDAO.
public class DAOFactorySQLite implements DAOFactory {

    // Singleton instance
    private static DAOFactorySQLite instance;

    // Private constructor to prevent instantiation
    private DAOFactorySQLite() {}

    // Public method to return the singleton instance
    public static synchronized DAOFactorySQLite getInstance() {
        if (instance == null) {
            instance = new DAOFactorySQLite();
        }
        return instance;
    }

    public GenericDAO<Person,Integer> createPersonDAOSQLite() {
        return new PersonDAOSQLite(); // No need to pass connection
    }

    @Override
    public GenericDAO<Event, Integer> createEventDAOSQLite() throws DatabaseException {
        return null;
    }

    // Add methods for creating other DAOs
    // public OtherDAO createOtherDAO() throws SQLException {
    //     ...
    // }
}
