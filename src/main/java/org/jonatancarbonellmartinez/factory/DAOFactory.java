package org.jonatancarbonellmartinez.factory;

import org.jonatancarbonellmartinez.model.Database;
import org.jonatancarbonellmartinez.model.dao.DimPersonDAO;
import org.jonatancarbonellmartinez.model.dao.DimPersonDAOImpl;

import java.sql.*;
// Puedes utilizar un patrón de fábrica para crear instancias de tus DAO. Esto es útil si decides cambiar la implementación de tus DAOs en el futuro.
public class DAOFactory {

    public DimPersonDAO createDimPersonDAO() throws SQLException {
        Connection connection = Database.getInstance().getConnection();
        return new DimPersonDAOImpl(connection);
    }



    // Métodos para otros DAOs
}