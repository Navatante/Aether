package org.jonatancarbonellmartinez.model.dao;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.entities.Entity;
import org.jonatancarbonellmartinez.model.entities.Projectile;
import org.jonatancarbonellmartinez.utilities.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class ProjectileDAOSqlite implements GenericDAO<Projectile, Integer> {
    @Override
    public void insert(Projectile entity) throws DatabaseException {
        String sql = "INSERT INTO main.junction_projectile (projectile_flight_fk, projectile_person_fk, projectile_type_fk, projectile_qty) VALUES (?, ?, ?, ?)";

        try(Connection connection = Database.getInstance().getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1,     entity.getFlightFk());
            pstmt.setInt(2,     entity.getPersonFk());
            pstmt.setInt(3,     entity.getProjectileTypeFk());
            pstmt.setDouble(4,  entity.getProjectileQty());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error insertando horas Proyectiles en la base de datos", e);
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
