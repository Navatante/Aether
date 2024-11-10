package org.jonatancarbonellmartinez.model.dao;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.entities.Entity;
import org.jonatancarbonellmartinez.model.entities.Flight;
import org.jonatancarbonellmartinez.utilities.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class FlightDAOSQLite implements GenericDAO<Flight, Integer>{
    @Override
    public void insert(Flight entity) throws DatabaseException {
        String sql = "INSERT INTO fact_flight (flight_datetime, flight_helo_fk, flight_event_fk, flight_person_cta_fk, flight_total_hours) VALUES (?, ?, ?, ?, ?)";

        try(Connection connection = Database.getInstance().getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, entity.getDateTime());
            pstmt.setInt(2,    entity.getHelo());
            pstmt.setInt(3,    entity.getEvent());
            pstmt.setInt(4,    entity.getPersonCta());
            pstmt.setDouble(5, entity.getTotalHours());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error insertando vuelo en la base de datos", e);
        }
    }

    @Override
    public Entity read(Integer entitySk) throws DatabaseException { // TODO
        return null;
    }

    @Override
    public void update(Flight entity, int skToUpdate) throws DatabaseException { // TODO

    }

    @Override
    public void delete(Integer entitySk) throws DatabaseException { // TODO

    }

    @Override
    public List<Flight> getAll() throws DatabaseException { // TODO
        return Collections.emptyList();
    }

    @Override
    public Entity mapResultSetToEntity(ResultSet rs) throws SQLException { // TODO
        return null;
    }
}
