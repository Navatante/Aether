package org.jonatancarbonellmartinez.data.database;

import org.jonatancarbonellmartinez.xexceptions.DatabaseException;
import org.jonatancarbonellmartinez.data.model.Entity;
import org.jonatancarbonellmartinez.data.model.IftHour;

import java.sql.*;
import java.util.Collections;
import java.util.List;

public class IftHourDAOSQLite implements GenericDAO<IftHour, Integer>{
    @Override
    public void insert(IftHour entity) throws DatabaseException {
        // Ift hours are inserted in batch.
    }

    public void insertBatch(List<IftHour> entities) throws DatabaseException {
        if (entities == null || entities.isEmpty()) {
            return; // No operation needed for empty lists
        }

        String enableForeignKeys = "PRAGMA foreign_keys = ON;";
        String sql = "INSERT INTO main.junction_ift_hour (ift_hour_flight_fk, ift_hour_person_fk, ift_hour_qty) VALUES (?, ?, ?)";

        try (Connection connection = Database.getInstance().getConnection();
             Statement stmt = connection.createStatement()) {

            // Habilitar claves foráneas
            stmt.execute(enableForeignKeys);

            connection.setAutoCommit(false);

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                for (IftHour entity : entities) {
                    pstmt.setInt(1,     entity.getFlightFk());
                    pstmt.setInt(2,     entity.getPersonFk());
                    pstmt.setDouble(3,  entity.getIftHourQty());
                    pstmt.addBatch();
                }

                pstmt.executeBatch();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback(); // Undo all changes in case of an error
                throw new DatabaseException("Error inserting Ift Hour data in batch", e);
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
    public void update(IftHour entity, int skToUpdate) throws DatabaseException {

    }

    @Override
    public void delete(Integer entitySk) throws DatabaseException {

    }

    @Override
    public List<IftHour> getAll() throws DatabaseException {
        return Collections.emptyList();
    }

    @Override
    public Entity mapResultSetToEntity(ResultSet rs) throws SQLException {
        return null;
   }
}
