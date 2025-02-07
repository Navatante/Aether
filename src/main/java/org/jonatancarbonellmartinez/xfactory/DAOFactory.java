package org.jonatancarbonellmartinez.xfactory;

import org.jonatancarbonellmartinez.data.model.*;
import org.jonatancarbonellmartinez.xexceptions.DatabaseException;
import org.jonatancarbonellmartinez.data.database.GenericDAO;


public interface DAOFactory {
    GenericDAO<PersonEntity,Integer> createPersonDAO() throws DatabaseException;
    GenericDAO<Event,Integer> createEventDAO() throws DatabaseException;
    GenericDAO<Helo,Integer> createHeloDAO() throws DatabaseException;
    GenericDAO<Flight,Integer> createFlightDAO() throws DatabaseException;
    GenericDAO<PersonHour, Integer> createPersonHourDAO() throws DatabaseException;
    GenericDAO<IftHour, Integer> createIftHourDAO() throws DatabaseException;
    GenericDAO<InstructorHour, Integer> createInstructorHourDAO() throws DatabaseException;
    GenericDAO<HdmsHour, Integer> createHdmsHourDAO() throws DatabaseException;
    GenericDAO<FormationHour, Integer> createFormationHourDAO() throws DatabaseException;
    GenericDAO<App, Integer> createAppDAO() throws DatabaseException;
    GenericDAO<Landing, Integer> createLandingDAO() throws DatabaseException;
    GenericDAO<WtHour, Integer> createWtHourDAO() throws DatabaseException;
    GenericDAO<Projectile, Integer> createProjectileDAO() throws DatabaseException;
    GenericDAO<SessionCrewCount, Integer> createSessionCrewCountDAO() throws DatabaseException;
    GenericDAO<Session, Integer> createSessionDAO() throws DatabaseException;
    GenericDAO<CupoHour, Integer> createCupoHourDAO() throws DatabaseException;
    GenericDAO<Authority, Integer> createUnitDAO() throws DatabaseException;
    GenericDAO<Passenger, Integer> createPassengerDAO() throws DatabaseException;

    // You can add methods to create other DAOs for different entities
    // For example:
    // OtherDAO createEventDAOSQLite() throws DatabaseException;

}
