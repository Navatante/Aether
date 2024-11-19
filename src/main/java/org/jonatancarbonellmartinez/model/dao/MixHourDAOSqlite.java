package org.jonatancarbonellmartinez.model.dao;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.entities.Entity;
import org.jonatancarbonellmartinez.model.entities.MixHour;
import org.jonatancarbonellmartinez.utilities.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class MixHourDAOSqlite implements GenericDAO<MixHour, Integer>{
    @Override
    public void insert(MixHour entity) throws DatabaseException {
        String sql = "INSERT INTO main.junction_mix_hour (mix_hour_flight_fk, mix_hour_person_fk, mix_hour_ift_qty, mix_hour_instructor_qty, mix_hour_hmds_qty) VALUES (?, ?, ?, ?, ?)";

        try(Connection connection = Database.getInstance().getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1,     entity.getFlightFk());
            pstmt.setInt(2,     entity.getPersonFk());
            pstmt.setDouble(3,  entity.getIftQty());
            pstmt.setDouble(4,  entity.getInstructorQty());
            pstmt.setDouble(5,  entity.getHdmsQty());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error insertando mix de horas en la base de datos", e);
        }

    }

    @Override
    public Entity read(Integer entitySk) throws DatabaseException {
        return null;
    }

    @Override
    public void update(MixHour entity, int skToUpdate) throws DatabaseException {

    }

    @Override
    public void delete(Integer entitySk) throws DatabaseException {

    }

    @Override
    public List<MixHour> getAll() throws DatabaseException {
        return Collections.emptyList();
    }

    @Override
    public Entity mapResultSetToEntity(ResultSet rs) throws SQLException {
        return null;
   }
}
