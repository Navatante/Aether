package org.jonatancarbonellmartinez.data.database;

import org.jonatancarbonellmartinez.xexceptions.DatabaseException;
import org.jonatancarbonellmartinez.data.model.Entity;
import org.jonatancarbonellmartinez.data.model.Projectile;

import java.sql.*;
import java.util.Collections;
import java.util.List;

public class ProjectileDAOSQLite implements GenericDAO<Projectile, Integer> {
    @Override
    public void insert(Projectile entity) throws DatabaseException {
        // Projectiles are inserted in batch
    }

    public void insertBatch(List<Projectile> entities) throws DatabaseException {
        if (entities == null || entities.isEmpty()) {
            return; // No operation needed for empty lists
        }

        String enableForeignKeys = "PRAGMA foreign_keys = ON;";
        String sql = "INSERT INTO main.junction_projectile (projectile_flight_fk, projectile_person_fk, projectile_type_fk, projectile_qty) VALUES (?, ?, ?, ?)";

        try (Connection connection = Database.getInstance().getConnection();
             Statement stmt = connection.createStatement()) {

            // Habilitar claves foráneas
            stmt.execute(enableForeignKeys);

            connection.setAutoCommit(false);

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                for (Projectile entity : entities) {
                    pstmt.setInt(1, entity.getFlightFk());
                    pstmt.setInt(2, entity.getPersonFk());
                    pstmt.setInt(3, entity.getProjectileTypeFk());
                    pstmt.setInt(4, entity.getProjectileQty());
                    pstmt.addBatch();
                }

                pstmt.executeBatch();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback(); // Undo all changes in case of an error
                throw new DatabaseException("Error inserting projectile data in batch", e);
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
    public void update(Projectile entity, int skToUpdate) throws DatabaseException {

    }

    @Override
    public void delete(Integer entitySk) throws DatabaseException {

    }

    @Override
    public List<Projectile> getAll() throws DatabaseException {
        return Collections.emptyList();
    }

    @Override
    public Entity mapResultSetToEntity(ResultSet rs) throws SQLException {
        return null;
    }
}
