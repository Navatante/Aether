package org.jonatancarbonellmartinez.model.dao;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.entities.Entity;
import org.jonatancarbonellmartinez.model.entities.IftHour;
import org.jonatancarbonellmartinez.model.entities.Passenger;
import org.jonatancarbonellmartinez.utilities.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class PassengerDAOSQLite implements GenericDAO<Passenger, Integer>{
    @Override
    public void insert(Passenger entity) throws DatabaseException {

    }

    public void insertBatch(List<Passenger> entities) throws DatabaseException {
        if (entities == null || entities.isEmpty()) {
            return; // No operation needed for empty lists
        }

        String sql = "INSERT INTO main.junction_passenger (passenger_flight_fk, passenger_type_fk, passenger_qty, passenger_route) VALUES (?, ?, ?, ?)";

        try (Connection connection = Database.getInstance().getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                for (Passenger entity : entities) {
                    pstmt.setInt(1,     entity.getFlightFk());
                    pstmt.setInt(2,     entity.getPassengerTypeFk());
                    pstmt.setDouble(3,  entity.getPassengerQty());
                    pstmt.setString(4, entity.getRoute());
                    pstmt.addBatch();
                }

                pstmt.executeBatch();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback(); // Undo all changes in case of an error
                throw new DatabaseException("Error inserting Passenger data in batch.", e);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error with database connection or transaction.", e);
        }
    }

    @Override
    public Entity read(Integer entitySk) throws DatabaseException {
        return null;
    }

    @Override
    public void update(Passenger entity, int skToUpdate) throws DatabaseException {

    }

    @Override
    public void delete(Integer entitySk) throws DatabaseException {

    }

    @Override
    public List<Passenger> getAll() throws DatabaseException {
        return Collections.emptyList();
    }

    @Override
    public Entity mapResultSetToEntity(ResultSet rs) throws SQLException {
        return null;
    }
}
