package org.jonatancarbonellmartinez.data.database;

import org.jonatancarbonellmartinez.xexceptions.DatabaseException;
import org.jonatancarbonellmartinez.data.model.Entity;
import org.jonatancarbonellmartinez.data.model.Helo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HeloDAO implements GenericDAO<Helo,Integer> {
    @Override
    public void insert(Helo entity) throws DatabaseException {
        // helo is only readable.
    }

    @Override
    public Helo read(Integer heloSk) throws DatabaseException {
        String sql = "SELECT * FROM dim_helo WHERE helo_sk = ?";
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, heloSk);

            // Execute the query and get the ResultSet
            try (ResultSet rs = pstmt.executeQuery()) {
                // Check if a person with the given ID exists
                if (rs.next()) {
                    return (Helo)mapResultSetToEntity(rs); // Return the populated Person object
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error buscando persona por ID", e);
        }
        return null; // Return null if no person was found with the given ID
    }

    @Override
    public void update(Helo entity, int skToUpdate) throws DatabaseException {
        // helo is only readable.
    }

    @Override
    public void delete(Integer entitySk) throws DatabaseException {
        // helo is only readable.
    }

    @Override
    public List<Helo> getAll() throws DatabaseException {
        String sql = "SELECT * FROM dim_helo ORDER BY helo_number";
        List<Helo> heloList = new ArrayList<>();

        // Obtain a new connection each time the method is called
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                heloList.add( (Helo)mapResultSetToEntity(rs) ); // Anade cada fila como un objeto helo individual.
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error al acceder al los helicóptero", e);
        }

        return heloList; // pasa una lista llena de objetos helos, un objeto por cada fila que haya.
    }

    @Override
    public Entity mapResultSetToEntity(ResultSet rs) throws SQLException {
        Helo helo = new Helo();
        helo.setHeloSk(rs.getInt("helo_sk"));
        helo.setHeloPlateNk(rs.getString("helo_plate_nk"));
        helo.setHeloName(rs.getString("helo_name"));
        helo.setHeloNumber(rs.getString("helo_number"));
        return helo;
    }
}
