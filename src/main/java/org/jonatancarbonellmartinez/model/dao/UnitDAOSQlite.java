package org.jonatancarbonellmartinez.model.dao;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.entities.Entity;
import org.jonatancarbonellmartinez.model.entities.Person;
import org.jonatancarbonellmartinez.model.entities.Unit;
import org.jonatancarbonellmartinez.utilities.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UnitDAOSQlite implements GenericDAO<Unit, Integer>{
    @Override
    public void insert(Unit entity) throws DatabaseException {
        String sql = "INSERT INTO dim_unit (unit_short, unit_name, unit_agency_short, unit_agency_name, unit_authority, unit_authority_abrv)" +
                " VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.getUnitShort());
            pstmt.setString(2, entity.getUnitName());
            pstmt.setString(3, entity.getAgencyShort());
            pstmt.setString(4, entity.getAgencyName());
            pstmt.setString(5, entity.getAuthority());
            pstmt.setString(6, entity.getAuthorityShort());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error insertando unidad en la base de datos.", e);
        }
    }

    @Override
    public Entity read(Integer entitySk) throws DatabaseException {
        String sql = "SELECT * FROM dim_unit WHERE unit_sk = ?";
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, entitySk);

            // Execute the query and get the ResultSet
            try (ResultSet rs = pstmt.executeQuery()) {
                // Check if a Unit with the given ID exists
                if (rs.next()) {
                    return (Unit)mapResultSetToEntity(rs); // Return the populated Unit object
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error buscando unidad por ID.", e);
        }
        return null; // Return null if no unit was found with the given ID
    }

    @Override
    public void update(Unit entity, int skToUpdate) throws DatabaseException {

    }

    @Override
    public void delete(Integer entitySk) throws DatabaseException {

    }

    @Override
    public List<Unit> getAll() throws DatabaseException {
        String sql = "SELECT * FROM dim_unit ORDER BY unit_name";
        List<Unit> unitList = new ArrayList<>();

        // Obtain a new connection each time the method is called
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                unitList.add( (Unit)mapResultSetToEntity(rs) );
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error al acceder a las unidades.", e);
        }

        return unitList;
    }

    @Override
    public Entity mapResultSetToEntity(ResultSet rs) throws SQLException {
        Unit unit = new Unit();
        unit.setUnitSk(rs.getInt("unit_sk"));
        unit.setUnitShort(rs.getString("unit_short"));
        unit.setUnitName(rs.getString("unit_name"));
        unit.setAgencyShort(rs.getString("unit_agency_short"));
        unit.setAgencyName(rs.getString("unit_agency_name"));
        unit.setAuthority(rs.getString("unit_authority"));
        unit.setAuthorityShort(rs.getString("unit_authority_abrv"));
        return unit;
    }
}
