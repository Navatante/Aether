package org.jonatancarbonellmartinez.data.database;


import org.jonatancarbonellmartinez.xexceptions.DatabaseException;
import org.jonatancarbonellmartinez.data.model.CupoHour;
import org.jonatancarbonellmartinez.data.model.Entity;

import java.sql.*;
import java.util.Collections;
import java.util.List;


public class CupoHourDAOSQLite implements GenericDAO<CupoHour, Integer> {

    @Override
    public void insert(CupoHour entity) throws DatabaseException {

    }

    public void insertBatch(List<CupoHour> entities) throws DatabaseException {
        if (entities == null || entities.isEmpty()) {
            return; // No operation needed for empty lists
        }

        String enableForeignKeys = "PRAGMA foreign_keys = ON;";
        String sql = "INSERT INTO main.junction_cupo_hour (cupo_flight_fk, cupo_authority_fk, cupo_hour_qty) VALUES (?, ?, ?)";

        try (Connection connection = Database.getInstance().getConnection();
             Statement stmt = connection.createStatement()) {

            // Habilitar claves foráneas
            stmt.execute(enableForeignKeys);

            connection.setAutoCommit(false);

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                for (CupoHour entity : entities) {
                    pstmt.setInt(1,     entity.getFlightFk());
                    pstmt.setInt(2,     entity.getUnitFk());
                    pstmt.setDouble(3,  entity.getCupoHourQty());
                    pstmt.addBatch();
                }

                pstmt.executeBatch();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback(); // Undo all changes in case of an error
                throw new DatabaseException("Error inserting Cupo Hour data in batch", e);
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
    public void update(CupoHour entity, int skToUpdate) throws DatabaseException {

    }

    @Override
    public void delete(Integer entitySk) throws DatabaseException {

    }

    @Override
    public List<CupoHour> getAll() throws DatabaseException {
        return Collections.emptyList();
    }

    @Override
    public Entity mapResultSetToEntity(ResultSet rs) throws SQLException {
        return null;
    }
}
