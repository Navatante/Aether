package org.jonatancarbonellmartinez.model.dao;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.entities.Entity;
import org.jonatancarbonellmartinez.model.entities.InstructorHour;
import org.jonatancarbonellmartinez.model.entities.WtHour;
import org.jonatancarbonellmartinez.utilities.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class InstructorHourDAOSqlite implements GenericDAO<InstructorHour, Integer>{
    @Override
    public void insert(InstructorHour entity) throws DatabaseException {
        // Instructor hours are inserted in batch.
    }

    public void insertBatch(List<InstructorHour> entities) throws DatabaseException {
        if (entities == null || entities.isEmpty()) {
            return; // No operation needed for empty lists
        }

        String sql = "INSERT INTO main.junction_instructor_hour (instructor_hour_flight_fk, instructor_hour_person_fk, instructor_hour_qty) VALUES (?, ?, ?)";

        try (Connection connection = Database.getInstance().getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                for (InstructorHour entity : entities) {
                    pstmt.setInt(1,     entity.getFlightFk());
                    pstmt.setInt(2,     entity.getPersonFk());
                    pstmt.setDouble(3,  entity.getInstructorHourQty());
                    pstmt.addBatch();
                }

                pstmt.executeBatch();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback(); // Undo all changes in case of an error
                throw new DatabaseException("Error inserting Instructor hours data in batch", e);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error with database connection or transaction", e);
        }
    }

    @Override
    public Entity read(Integer entitySk) throws DatabaseException {
        return null;
    }

    @Override
    public void update(InstructorHour entity, int skToUpdate) throws DatabaseException {

    }

    @Override
    public void delete(Integer entitySk) throws DatabaseException {

    }

    @Override
    public List<InstructorHour> getAll() throws DatabaseException {
        return Collections.emptyList();
    }

    @Override
    public Entity mapResultSetToEntity(ResultSet rs) throws SQLException {
        return null;
    }
}
