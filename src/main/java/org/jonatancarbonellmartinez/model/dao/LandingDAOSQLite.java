package org.jonatancarbonellmartinez.model.dao;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.entities.Entity;
import org.jonatancarbonellmartinez.model.entities.Landing;
import org.jonatancarbonellmartinez.utilities.Database;

import java.sql.*;
import java.util.Collections;
import java.util.List;

public class LandingDAOSQLite implements GenericDAO<Landing, Integer>{
    @Override
    public void insert(Landing entity) throws DatabaseException {
        // Landings are inserted in batch.
    }

    public void insertBatch(List<Landing> entities) throws DatabaseException {
        if (entities == null || entities.isEmpty()) {
            return; // No operation needed for empty lists
        }

        String enableForeignKeys = "PRAGMA foreign_keys = ON;";
        String sql = "INSERT INTO main.junction_landing (landing_flight_fk, landing_person_fk, landing_place_fk, landing_period_fk, landing_qty) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = Database.getInstance().getConnection();
             Statement stmt = connection.createStatement()) {

            // Habilitar claves foráneas
            stmt.execute(enableForeignKeys);

            connection.setAutoCommit(false);

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                for (Landing entity : entities) {
                    pstmt.setInt(1,     entity.getFlightFk());
                    pstmt.setInt(2,     entity.getPersonFk());
                    pstmt.setDouble(3,  entity.getPlaceFk());
                    pstmt.setDouble(4,  entity.getPeriodFk());
                    pstmt.setDouble(5,  entity.getLandingQty());
                    pstmt.addBatch();
                }

                pstmt.executeBatch();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback(); // Undo all changes in case of an error
                throw new DatabaseException("Error inserting Landing data in batch", e);
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
    public void update(Landing entity, int skToUpdate) throws DatabaseException {

    }

    @Override
    public void delete(Integer entitySk) throws DatabaseException {

    }

    @Override
    public List<Landing> getAll() throws DatabaseException {
        return Collections.emptyList();
    }

    @Override
    public Entity mapResultSetToEntity(ResultSet rs) throws SQLException {
        return null;
    }
}
