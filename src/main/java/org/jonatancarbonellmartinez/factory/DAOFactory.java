package org.jonatancarbonellmartinez.factory;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.dao.PersonDAO;

import java.sql.SQLException;

public interface DAOFactory {

    // Method to create DimPersonDAO
    PersonDAO createPersonDAOSQLite() throws DatabaseException;

    // You can add methods to create other DAOs for different entities
    // For example:

    // OtherDAO createOtherDAO() throws DatabaseException;

}
