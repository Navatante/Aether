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
    public IftHourDAOSQLite createIftHourDAO() throws DatabaseException {
        return new IftHourDAOSQLite();
    }

    @Override
    public InstructorHourDAOSQLite createInstructorHourDAO() throws DatabaseException {
        return new InstructorHourDAOSQLite();
    }

    @Override
    public HdmsHourDAOSQLite createHdmsHourDAO() throws DatabaseException {
        return new HdmsHourDAOSQLite();
    }

    @Override
    public AppDAOSQLite createAppDAO() throws DatabaseException {
        return new AppDAOSQLite();
    }

    @Override
    public LandingDAOSQLite createLandingDAO() throws DatabaseException {
        return new LandingDAOSQLite();
    }

    @Override
    public WtHourDAOSQLite createWtHourDAO() throws DatabaseException {
        return new WtHourDAOSQLite();
    }

    @Override
    public ProjectileDAOSQLite createProjectileDAO() throws DatabaseException {
        return new ProjectileDAOSQLite();
    }

    @Override
    public SessionCrewCountDAOSQLite createSessionCrewCountDAO() throws DatabaseException {
        return new SessionCrewCountDAOSQLite();
    }

    @Override
    public SessionDAOSQLite createSessionDAO() throws DatabaseException {
        return new SessionDAOSQLite();
    }

    @Override
    public CupoHourDAOSQLite createCupoHourDAO() throws DatabaseException {
        return new CupoHourDAOSQLite();
    }

    @Override
    public AuthorityDAOSQLite createUnitDAO() throws DatabaseException {
        return new AuthorityDAOSQLite();
    }

    @Override
    public PassengerDAOSQLite createPassengerDAO() throws DatabaseException {
        return new PassengerDAOSQLite();
    }


    // Add methods for creating other DAOs
    // public OtherDAO createOtherDAO() throws SQLException {
    //     ...
    // }
}
