package org.jonatancarbonellmartinez.model.dao;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.entities.Entity;
import org.jonatancarbonellmartinez.model.entities.WtHour;
import org.jonatancarbonellmartinez.utilities.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class WtHourDAOSqlite implements GenericDAO<WtHour, Integer> {
    @Override
    public void insert(WtHour entity) throws DatabaseException {
        String sql = "INSERT INTO main.junction_wt_hour (wt_hour_flight_fk, wt_hour_person_fk, wt_hour_qty) VALUES (?, ?, ?)";

        try(Connection connection = Database.getInstance().getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1,     entity.getFlightFk());
            pstmt.setInt(2,     entity.getPersonFk());
            pstmt.setDouble(3,  entity.getWtHourQty());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error insertando horas Winch Trim en la base de datos", e);
        }
    }

    @Override
    public Entity read(Integer entitySk) throws DatabaseException {
        return null;
    }

    @Override
    public void update(WtHour entity, int skToUpdate) throws DatabaseException {

    }

    @Override
    public void delete(Integer entitySk) throws DatabaseException {

    }

    @Override
    public List<WtHour> getAll() throws DatabaseException {
        return Collections.emptyList();
    }

    @Override
    public Entity mapResultSetToEntity(ResultSet rs) throws SQLException {
        return null;
    }
}
