package org.jonatancarbonellmartinez.factory;

import org.jonatancarbonellmartinez.model.dao.PersonDAO;

import java.sql.SQLException;

public interface DAOFactory {

    // Method to create DimPersonDAO
    PersonDAO createPersonDAOSQLite() throws SQLException;

    // You can add methods to create other DAOs for different entities
    // For example:

    // OtherDAO createOtherDAO() throws SQLException;

    // This method could be used to close connections or clean up resources if needed
    // void closeConnection() throws SQLException;
}
