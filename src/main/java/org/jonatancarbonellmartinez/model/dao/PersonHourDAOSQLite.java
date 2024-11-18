package org.jonatancarbonellmartinez.model.dao;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.entities.Entity;
import org.jonatancarbonellmartinez.model.entities.PersonHour;
import org.jonatancarbonellmartinez.utilities.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class PersonHourDAOSQLite implements GenericDAO<PersonHour, Integer> {
    @Override
    public void insert(PersonHour entity) throws DatabaseException {
        String sql = "INSERT INTO junction_person_hour (person_hour_flight_fk, person_hour_person_fk, person_hour_period_fk, person_hour_hour_qty) VALUES (?, ?, ?, ?)";

        try(Connection connection = Database.getInstance().getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, entity.getFlightFk());
            pstmt.setInt(2,    entity.getPersonFk());
            pstmt.setInt(3,    entity.getPeriodFk());
            pstmt.setDouble(4,    entity.getHourQty());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error insertando horas persona en la base de datos", e);
        }
    }

    @Override
    public Entity read(Integer entitySk) throws DatabaseException { // TODO
        return null;
    }

    @Override
    public void update(PersonHour entity, int skToUpdate) throws DatabaseException { // TODO

    }

    @Override
    public void delete(Integer entitySk) throws DatabaseException { // TODO

    }

    @Override
    public List<PersonHour> getAll() throws DatabaseException { // TODO
        return Collections.emptyList();
    }

    @Override
    public Entity mapResultSetToEntity(ResultSet rs) throws SQLException { // TODO
        return null;
    }
}
