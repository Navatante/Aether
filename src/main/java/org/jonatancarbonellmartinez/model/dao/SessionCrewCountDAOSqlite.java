package org.jonatancarbonellmartinez.model.dao;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.entities.Entity;
import org.jonatancarbonellmartinez.model.entities.SessionCrewCount;
import org.jonatancarbonellmartinez.utilities.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class SessionCrewCountDAOSqlite implements GenericDAO<SessionCrewCount, Integer> {
    @Override
    public void insert(SessionCrewCount entity) throws DatabaseException {
        String sql = "INSERT INTO main.junction_session_crew_count (session_crew_count_flight_fk, session_crew_count_person_fk, session_crew_count_session_fk) VALUES (?, ?, ?)";

        try(Connection connection = Database.getInstance().getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1,     entity.getFlightFk());
            pstmt.setInt(2,     entity.getPersonFk());
            pstmt.setDouble(3,  entity.getSessionFk());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error insertando sesiones realizadas en la base de datos", e);
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
