package org.jonatancarbonellmartinez.model.dao;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.entities.Entity;
import org.jonatancarbonellmartinez.model.entities.SessionCrewCount;
import org.jonatancarbonellmartinez.model.utilities.Database;

import java.sql.*;
import java.util.Collections;
import java.util.List;

public class SessionCrewCountDAOSQLite implements GenericDAO<SessionCrewCount, Integer> {
    @Override
    public void insert(SessionCrewCount entity) throws DatabaseException {
        // Sessions are inserted in batch.
    }

    public void insertBatch(List<SessionCrewCount> entities) throws DatabaseException {
        if (entities == null || entities.isEmpty()) {
            return; // No operation needed for empty lists
        }

        String enableForeignKeys = "PRAGMA foreign_keys = ON;";
        String sql = "INSERT INTO main.junction_session_crew_count (session_crew_count_flight_fk, session_crew_count_person_fk, session_crew_count_session_fk) VALUES (?, ?, ?)";

        try (Connection connection = Database.getInstance().getConnection();
             Statement stmt = connection.createStatement()) {

            // Habilitar claves foráneas
            stmt.execute(enableForeignKeys);

            connection.setAutoCommit(false);

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                for (SessionCrewCount entity : entities) {
                    pstmt.setInt(1, entity.getFlightFk());
                    pstmt.setInt(2, entity.getPersonFk());
                    pstmt.setInt(3, entity.getSessionFk());
                    pstmt.addBatch();
                }

                pstmt.executeBatch();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback(); // Undo all changes in case of an error
                throw new DatabaseException("Error inserting session crew count data in batch", e);
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
    public void update(SessionCrewCount entity, int skToUpdate) throws DatabaseException {

    }

    @Override
    public void delete(Integer entitySk) throws DatabaseException {

    }

    @Override
    public List<SessionCrewCount> getAll() throws DatabaseException {
        return Collections.emptyList();
    }

    @Override
    public Entity mapResultSetToEntity(ResultSet rs) throws SQLException {
        return null;
    }
}
