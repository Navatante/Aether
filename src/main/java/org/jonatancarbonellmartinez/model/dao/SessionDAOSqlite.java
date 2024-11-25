package org.jonatancarbonellmartinez.model.dao;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.entities.Entity;
import org.jonatancarbonellmartinez.model.entities.Session;
import org.jonatancarbonellmartinez.utilities.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SessionDAOSqlite implements GenericDAO<Session, Integer> { // TODO cuando los oficiales se aclaren y sepan como seran las Sesiones, entonces creare la opcion de anadir y modificar secciones en la barra superior del menu. igual que eventos y personas.
    @Override
    public void insert(Session entity) throws DatabaseException {
        String sql = "INSERT INTO dim_session (session_nk, session_dv, session_name, session_type, session_subtype, session_CAPBA, session_crp_value, session_expiration)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.getSessionNk());
            pstmt.setString(2, entity.getSessionDv());
            pstmt.setString(3, entity.getSessionName());
            pstmt.setString(4, entity.getSessionType());
            pstmt.setString(5, entity.getSessionSubType());
            pstmt.setString(6, entity.getCapba());
            pstmt.setDouble(7, entity.getCrpValue());
            pstmt.setInt(8, entity.getExpiration());

            pstmt.executeUpdate();  // Cambiar execute() por executeUpdate() para inserciones
        } catch (SQLException e) {
            throw new DatabaseException("Error insertando sesión en la base de datos", e);
        }
    }

    @Override
    public Entity read(Integer entitySk) throws DatabaseException {
        return null;
    }

    @Override
    public void update(Session entity, int skToUpdate) throws DatabaseException {

    }

    @Override
    public void delete(Integer entitySk) throws DatabaseException {

    }

    @Override
    public List<Session> getAll() throws DatabaseException {
        String sql = "SELECT * FROM dim_session ORDER BY session_nk";
        List<Session> sessionList = new ArrayList<>();

        // Obtain a new connection each time the method is called
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                sessionList.add( (Session)mapResultSetToEntity(rs) );
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error al acceder a las sesiones", e);
        }

        return sessionList;
    }

    @Override
    public Entity mapResultSetToEntity(ResultSet rs) throws SQLException {
        Session session = new Session();
        session.setSessionSk(rs.getInt("session_sk"));
        session.setSessionNk(rs.getString("session_nk"));
        session.setSessionDv(rs.getString("session_dv"));
        session.setSessionName(rs.getString("session_name"));
        session.setSessionType(rs.getString("session_type"));
        session.setSessionSubType(rs.getString("session_subtype"));
        session.setCapba(rs.getString("session_CAPBA"));
        session.setCrpValue(rs.getDouble("session_crp_value"));
        session.setExpiration(rs.getInt("session_expiration"));
        return session;
    }
}