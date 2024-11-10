package org.jonatancarbonellmartinez.factory;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.dao.*;
import org.jonatancarbonellmartinez.model.entities.Event;
import org.jonatancarbonellmartinez.model.entities.Flight;
import org.jonatancarbonellmartinez.model.entities.Helo;
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

    @Override
    public PersonDAOSQLite createPersonDAO() {
        return new PersonDAOSQLite();
    }

    @Override
    public EventDAOSQLite createEventDAO() throws DatabaseException {
        return new EventDAOSQLite();
    }

    @Override
    public HeloDAOSQLite createHeloDAO() throws DatabaseException {
        return new HeloDAOSQLite();
    }

    @Override
    public FlightDAOSQLite createFlightDAO() throws DatabaseException {
        return new FlightDAOSQLite();
    }


    // Add methods for creating other DAOs
    // public OtherDAO createOtherDAO() throws SQLException {
    //     ...
    // }
}
