package org.jonatancarbonellmartinez.model.dao;

import org.jonatancarbonellmartinez.model.Database;

import java.sql.*;
// Puedes utilizar un patrón de fábrica para crear instancias de tus DAO. Esto es útil si decides cambiar la implementación de tus DAOs en el futuro.
public class DAOFactory {

    public DimPersonDAO createDimPersonDAO() throws SQLException {
        Connection connection = Database.getDatabaseInstance().connectToDatabase();
        return new DimPersonDAOImpl(connection);
    }

    // Métodos para otros DAOs
}
