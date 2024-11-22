package org.jonatancarbonellmartinez.factory;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.dao.*;

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

    @Override
    public PersonDAOSQLite createPersonDAO() {
        return new PersonDAOSQLite();
    }

    @Override
    public EventDAOSQLite createEventDAO() throws DatabaseException {
        return new EventDAOSQLite();
    }

    @Override
    public HeloDAOSQLite createHeloDAO() throws DatabaseException {
        return new HeloDAOSQLite();
    }

    @Override
    public FlightDAOSQLite createFlightDAO() throws DatabaseException {
        return new FlightDAOSQLite();
    }

    @Override
    public PersonHourDAOSQLite createPersonHourDAO() throws DatabaseException {
        return new PersonHourDAOSQLite();
    }

    @Override
    public IftHourDAOSqlite createIftHourDAO() throws DatabaseException {
        return new IftHourDAOSqlite();
    }

    @Override
    public InstructorHourDAOSqlite createInstructorHourDAO() throws DatabaseException {
        return new InstructorHourDAOSqlite();
    }

    @Override
    public HdmsHourDAOSqlite createHdmsHourDAO() throws DatabaseException {
        return new HdmsHourDAOSqlite();
    }

    @Override
    public AppDAOSqlite createAppDAO() throws DatabaseException {
        return new AppDAOSqlite();
    }

    @Override
    public LandingDAOSqlite createLandingDAO() throws DatabaseException {
        return new LandingDAOSqlite();
    }

    @Override
    public WtHourDAOSqlite createWtHourDAO() throws DatabaseException {
        return new WtHourDAOSqlite();
    }

    @Override
    public ProjectileDAOSqlite createProjectileDAO() throws DatabaseException {
        return new ProjectileDAOSqlite();
    }

    @Override
    public SessionCrewCountDAOSqlite createSessionCrewCountDAO() throws DatabaseException {
        return new SessionCrewCountDAOSqlite();
    }

    @Override
    public SessionDAOSqlite createSessionDAO() throws DatabaseException {
        return new SessionDAOSqlite();
    }


    // Add methods for creating other DAOs
    // public OtherDAO createOtherDAO() throws SQLException {
    //     ...
    // }
}
