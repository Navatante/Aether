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
    GenericDAO<IftHour, Integer> createIftHourDAO() throws DatabaseException;
    GenericDAO<InstructorHour, Integer> createInstructorHourDAO() throws DatabaseException;
    GenericDAO<HdmsHour, Integer> createHdmsHourDAO() throws DatabaseException;
    GenericDAO<App, Integer> createAppDAO() throws DatabaseException;
    GenericDAO<Landing, Integer> createLandingDAO() throws DatabaseException;
    GenericDAO<WtHour, Integer> createWtHourDAO() throws DatabaseException;
    GenericDAO<Projectile, Integer> createProjectileDAO() throws DatabaseException;
    GenericDAO<SessionCrewCount, Integer> createSessionCrewCountDAO() throws DatabaseException;
    GenericDAO<Session, Integer> createSessionDAO() throws DatabaseException;
    GenericDAO<CupoHour, Integer> createCupoHourDAO() throws DatabaseException;
    GenericDAO<Unit, Integer> createUnitDAO() throws DatabaseException;

    // You can add methods to create other DAOs for different entities
    // For example:
    // OtherDAO createEventDAOSQLite() throws DatabaseException;

}
