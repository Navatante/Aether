package org.jonatancarbonellmartinez.factory;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.dao.GenericDAO;
import org.jonatancarbonellmartinez.model.entities.*;


public interface DAOFactory {
    GenericDAO<Person,Integer> createPersonDAO() throws DatabaseException;
    GenericDAO<Event,Integer> createEventDAO() throws DatabaseException;
    GenericDAO<Helo,Integer> createHeloDAO() throws DatabaseException;
    GenericDAO<Flight,Integer> createFlightDAO() throws DatabaseException;
    GenericDAO<PersonHour, Integer> createPersonHourDAO() throws DatabaseException;

    // You can add methods to create other DAOs for different entities
    // For example:
    // OtherDAO createEventDAOSQLite() throws DatabaseException;

}
