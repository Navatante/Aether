package org.jonatancarbonellmartinez.model.dao;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.entities.Entity;
import org.jonatancarbonellmartinez.model.entities.Event;
import org.jonatancarbonellmartinez.model.entities.Person;
import org.jonatancarbonellmartinez.utilities.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class EventDAOSQLite implements GenericDAO<Event, Integer> { // TODO number 1 to do
    @Override
    public void create(Event entity) throws DatabaseException {

    }

    @Override
    public Entity read(Integer entitySk) throws DatabaseException {
        String sql = "SELECT * FROM dim_event WHERE event_sk = ?";
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, entitySk);

            // Execute the query and get the ResultSet
            try (ResultSet rs = pstmt.executeQuery()) {
                // Check if a person with the given ID exists
                if (rs.next()) {
                    return (Event)mapResultSetToEntity(rs); // Return the populated Person object
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error buscando persona por ID", e);
        }
        return null; // Return null if no person was found with the given ID
    }

    @Override
    public void update(Event entity, int skToUpdate) throws DatabaseException {

    }

    @Override
    public void delete(Integer entitySk) throws DatabaseException {

    }

    @Override
    public List<Event> getAll() throws DatabaseException {
        return Collections.emptyList();
    }

    @Override
    public Entity mapResultSetToEntity(ResultSet rs) throws SQLException {
        Event event = new Event();
        event.setEventSk(rs.getInt("event_sk"));
        event.setEventName(rs.getString("event_name"));
        event.setEventPlace(rs.getString("event_place"));
        return event;
    }
}
