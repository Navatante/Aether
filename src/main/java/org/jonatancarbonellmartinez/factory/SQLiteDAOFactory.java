package org.jonatancarbonellmartinez.factory;

import org.jonatancarbonellmartinez.model.Database;
import org.jonatancarbonellmartinez.model.dao.*;

import java.sql.*;

// Esta clase la utilizo para crear los distintos DAOs a traves de ella, por cada dao, tendre que crear un metodo 'createDimNameDAO' or createFactNameDAO.
public class SQLiteDAOFactory implements DAOFactory {

    // Singleton instance
    private static SQLiteDAOFactory instance;

    // Private constructor to prevent instantiation
    private SQLiteDAOFactory() {}

    // Public method to return the singleton instance
    public static synchronized SQLiteDAOFactory getInstance() {
        if (instance == null) {
            instance = new SQLiteDAOFactory();
        }
        return instance;
    }

    public DimPersonDAO createDimPersonDAO() throws SQLException {
        Connection connection = null;
        try {
            connection = Database.getInstance().getConnection();
            return new SQLiteDimPersonDAO(connection);
        } catch (SQLException e) {
            // Handle SQLException appropriately
            throw new SQLException("Failed to create DimPersonDAO: " + e.getMessage(), e);
        }
    }

    // Add methods for creating other DAOs
    // public OtherDAO createOtherDAO() throws SQLException {
    //     ...
    // }
}
