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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventDAOSQLite implements GenericDAO<Event, Integer> {
    @Override
    public void create(Event entity) throws DatabaseException {
        String sql = "INSERT INTO dim_event (event_name, event_place)" +
                " VALUES (?, ?)";

        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.getEventName());
            pstmt.setString(2, entity.getEventPlace());

            pstmt.executeUpdate();  // Cambiar execute() por executeUpdate() para inserciones
        } catch (SQLException e) {
            throw new DatabaseException("Error insertando persona en la base de datos", e);
        }

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
        String sql = "UPDATE dim_event\n" +
                "SET \n" +
                "    event_name = ?," +
                "    event_place = ?" +
                "WHERE event_sk = ?";

        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.getEventName());
            pstmt.setString(2, entity.getEventPlace());
            pstmt.setInt(3, skToUpdate);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error editando evento en la base de datos", e);
        }
    }

    @Override
    public void delete(Integer entitySk) throws DatabaseException {

    }

    @Override
    public List<Event> getAll() throws DatabaseException {
        String sql = "SELECT * FROM dim_event ORDER BY event_name";
        List<Event> eventList = new ArrayList<>();

        // Obtain a new connection each time the method is called
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                eventList.add( (Event) mapResultSetToEntity(rs) );
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error al acceder al evento", e);
        }

        return eventList;
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
