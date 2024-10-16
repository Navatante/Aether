package org.jonatancarbonellmartinez.factory;

import org.jonatancarbonellmartinez.model.Database;
import org.jonatancarbonellmartinez.model.dao.*;

import java.sql.*;

// Puedes utilizar un patrón de fábrica para crear instancias de tus DAO. Esto es útil si decides cambiar la implementación de tus DAOs en el futuro. (usar MySQL)
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

    /**
     * Creates an instance of DimPersonDAO.
     *
     * @return a DimPersonDAO instance
     * @throws SQLException if a database access error occurs
     */
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
