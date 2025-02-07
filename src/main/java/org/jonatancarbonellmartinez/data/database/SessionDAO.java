package org.jonatancarbonellmartinez.data.database;

import org.jonatancarbonellmartinez.xexceptions.DatabaseException;
import org.jonatancarbonellmartinez.data.model.Entity;
import org.jonatancarbonellmartinez.data.model.Session;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SessionDAO implements GenericDAO<Session, Integer> { // TODO Falta crear la opcion de anadir y modificar papeletas en la barra superior del menu. igual que eventos y personas.
    @Override
    public void insert(Session entity) throws DatabaseException {
        String enableForeignKeys = "PRAGMA foreign_keys = ON;";
        String sql = "INSERT INTO dim_session (session_name, session_description, session_block, session_plan, session_tv, session_crp_value, session_expiration)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Database.getInstance().getConnection();
             Statement stmt = connection.createStatement();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            // Habilitar claves foráneas
            stmt.execute(enableForeignKeys);

            pstmt.setString(1, entity.getSessionName());
            pstmt.setString(2, entity.getSessionDescription());
            pstmt.setString(3, entity.getSessionBlock());
            pstmt.setString(4, entity.getSessionPlan());
            pstmt.setString(5, entity.getSessionTv());
            pstmt.setDouble(6, entity.getCrpValue());
            pstmt.setInt(7, entity.getExpiration());

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
        String sql = "SELECT * FROM dim_session ORDER BY session_name";
        List<Session> sessionList = new ArrayList<>();

        // Obtain a new connection each time the method is called
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                sessionList.add( (Session)mapResultSetToEntity(rs) );
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error al acceder a las papeletas", e);
        }

        return sessionList;
    }

    @Override
    public Entity mapResultSetToEntity(ResultSet rs) throws SQLException {
        Session session = new Session();
        session.setSessionSk(rs.getInt("session_sk"));
        session.setSessionName(rs.getString("session_name"));
        session.setSessionDescription(rs.getString("session_description"));
        session.setSessionBlock(rs.getString("session_block"));
        session.setSessionPlan(rs.getString("session_plan"));
        session.setSessionTv(rs.getString("session_tv"));
        session.setCrpValue(rs.getDouble("session_crp_value"));
        session.setExpiration(rs.getInt("session_expiration"));
        return session;
    }
}