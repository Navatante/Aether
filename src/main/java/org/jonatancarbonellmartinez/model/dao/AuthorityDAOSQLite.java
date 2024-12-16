package org.jonatancarbonellmartinez.model.dao;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.entities.Entity;
import org.jonatancarbonellmartinez.model.entities.Authority;
import org.jonatancarbonellmartinez.utilities.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorityDAOSQLite implements GenericDAO<Authority, Integer>{
    @Override
    public void insert(Authority entity) throws DatabaseException {
    }

    @Override
    public Entity read(Integer entitySk) throws DatabaseException {
        String sql = "SELECT * FROM dim_authority WHERE authority_sk = ?";
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, entitySk);

            // Execute the query and get the ResultSet
            try (ResultSet rs = pstmt.executeQuery()) {
                // Check if a Unit with the given ID exists
                if (rs.next()) {
                    return (Authority)mapResultSetToEntity(rs); // Return the populated Unit object
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error buscando unidad por ID.", e);
        }
        return null; // Return null if no unit was found with the given ID
    }

    @Override
    public void update(Authority entity, int skToUpdate) throws DatabaseException {

    }

    @Override
    public void delete(Integer entitySk) throws DatabaseException {

    }

    @Override
    public List<Authority> getAll() throws DatabaseException {
        String sql = "SELECT * FROM dim_authority ORDER BY dim_authority.authority_name";
        List<Authority> authorityList = new ArrayList<>();

        // Obtain a new connection each time the method is called
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                authorityList.add( (Authority)mapResultSetToEntity(rs) );
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error al acceder a las unidades.", e);
        }

        return authorityList;
    }

    @Override
    public Entity mapResultSetToEntity(ResultSet rs) throws SQLException {
        Authority authority = new Authority();
        authority.setAuthoritySk(rs.getInt("authority_sk"));
        authority.setAuthorityName(rs.getString("authority_name"));
        authority.setAuthorityShort(rs.getString("authority_abrv"));
        return authority;
    }
}
