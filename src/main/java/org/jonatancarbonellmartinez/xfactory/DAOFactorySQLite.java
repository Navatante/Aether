package org.jonatancarbonellmartinez.xfactory;

import org.jonatancarbonellmartinez.data.database.*;
import org.jonatancarbonellmartinez.xexceptions.DatabaseException;

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
    public PersonDAO createPersonDAO() {
        return new PersonDAO();
    }

    @Override
    public EventDAO createEventDAO() throws DatabaseException {
        return new EventDAO();
    }

    @Override
    public HeloDAO createHeloDAO() throws DatabaseException {
        return new HeloDAO();
    }

    @Override
    public FlightDAO createFlightDAO() throws DatabaseException {
        return new FlightDAO();
    }

    @Override
    public PersonHourDAO createPersonHourDAO() throws DatabaseException {
        return new PersonHourDAO();
    }

    @Override
    public IftHourDAO createIftHourDAO() throws DatabaseException {
        return new IftHourDAO();
    }

    @Override
    public InstructorHourDAO createInstructorHourDAO() throws DatabaseException {
        return new InstructorHourDAO();
    }

    @Override
    public HdmsHourDAO createHdmsHourDAO() throws DatabaseException {
        return new HdmsHourDAO();
    }

    @Override
    public FormationHourDAO createFormationHourDAO() throws DatabaseException {
        return new FormationHourDAO();
    }

    @Override
    public AppDAO createAppDAO() throws DatabaseException {
        return new AppDAO();
    }

    @Override
    public LandingDAO createLandingDAO() throws DatabaseException {
        return new LandingDAO();
    }

    @Override
    public WtHourDAO createWtHourDAO() throws DatabaseException {
        return new WtHourDAO();
    }

    @Override
    public ProjectileDAO createProjectileDAO() throws DatabaseException {
        return new ProjectileDAO();
    }

    @Override
    public SessionCrewCountDAO createSessionCrewCountDAO() throws DatabaseException {
        return new SessionCrewCountDAO();
    }

    @Override
    public SessionDAO createSessionDAO() throws DatabaseException {
        return new SessionDAO();
    }

    @Override
    public CupoHourDAO createCupoHourDAO() throws DatabaseException {
        return new CupoHourDAO();
    }

    @Override
    public AuthorityDAO createUnitDAO() throws DatabaseException {
        return new AuthorityDAO();
    }

    @Override
    public PassengerDAO createPassengerDAO() throws DatabaseException {
        return new PassengerDAO();
    }


    // Add methods for creating other DAOs
    // public OtherDAO createOtherDAO() throws SQLException {
    //     ...
    // }
}
