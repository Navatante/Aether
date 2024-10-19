package org.jonatancarbonellmartinez.factory;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.utilities.Database;
import org.jonatancarbonellmartinez.model.dao.*;

import java.sql.*;

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

    public PersonDAO createPersonDAOSQLite() {
        return new PersonDAOSQLite(); // No need to pass connection
    }

    // Add methods for creating other DAOs
    // public OtherDAO createOtherDAO() throws SQLException {
    //     ...
    // }
}
