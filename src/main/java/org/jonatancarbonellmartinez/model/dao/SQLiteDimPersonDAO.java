package org.jonatancarbonellmartinez.model.dao;

import org.jonatancarbonellmartinez.model.entities.DimPerson;
import org.jonatancarbonellmartinez.model.dao.exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SQLiteDimPersonDAO implements DimPersonDAO {
    private Connection connection;

    public SQLiteDimPersonDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(DimPerson person) throws DAOException {
        String sql = "INSERT INTO dim_person (person_nk, person_rank_number, person_rank, person_name, person_last_name_1, person_last_name_2, person_dni, person_phone, person_division, person_current_flag) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, person.getPersonNk());
            pstmt.setInt(2, person.getPersonRankNumber());
            pstmt.setString(3, person.getPersonRank());
            pstmt.setString(4, person.getPersonName());
            pstmt.setString(5, person.getPersonLastName1());
            pstmt.setString(6, person.getPersonLastName2());
            pstmt.setString(7, person.getPersonDni());
            pstmt.setString(8, person.getPersonPhone());
            pstmt.setString(9, person.getPersonDivision());
            pstmt.setInt(10, person.getPersonCurrentFlag());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error en SQL", e);
        }
    }

    @Override
    public DimPerson read(Integer personSk) throws DAOException {
        String sql = "SELECT * FROM dim_person WHERE person_sk = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, personSk);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                DimPerson person = new DimPerson();
                person.setPersonSk(rs.getInt("person_sk"));
                person.setPersonNk(rs.getString("person_nk"));
                person.setPersonRankNumber(rs.getInt("person_rank_number"));
                person.setPersonRank(rs.getString("person_rank"));
                person.setPersonName(rs.getString("person_name"));
                person.setPersonLastName1(rs.getString("person_last_name_1"));
                person.setPersonLastName2(rs.getString("person_last_name_2"));
                person.setPersonDni(rs.getString("person_dni"));
                person.setPersonPhone(rs.getString("person_phone"));
                person.setPersonDivision(rs.getString("person_division"));
                person.setPersonCurrentFlag(rs.getInt("person_current_flag"));
                return person;
            }
        } catch (SQLException e) {
            throw new DAOException("Error en SQL", e);
        }
        return null;
    }

    @Override
    public void update(DimPerson person) throws DAOException {
        String sql = "UPDATE dim_person SET person_nk=?, person_rank_number=?, person_rank=?, person_name=?, person_last_name_1=?, person_last_name_2=?, person_dni=?, person_phone=?, person_division=?, person_current_flag=? WHERE person_sk=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, person.getPersonNk());
            pstmt.setInt(2, person.getPersonRankNumber());
            pstmt.setString(3, person.getPersonRank());
            pstmt.setString(4, person.getPersonName());
            pstmt.setString(5, person.getPersonLastName1());
            pstmt.setString(6, person.getPersonLastName2());
            pstmt.setString(7, person.getPersonDni());
            pstmt.setString(8, person.getPersonPhone());
            pstmt.setString(9, person.getPersonDivision());
            pstmt.setInt(10, person.getPersonCurrentFlag());
            pstmt.setInt(11, person.getPersonSk());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error en SQL", e);
        }
    }

    @Override
    public void delete(Integer personSk) throws DAOException {
        String sql = "DELETE FROM dim_person WHERE person_sk=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, personSk);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error en SQL", e);
        }
    }

    @Override
    public List<DimPerson> getAll() throws DAOException {
        String sql = "SELECT * FROM dim_person";
        List<DimPerson> personList = new ArrayList<>(); // Create a list to hold DimPerson objects

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) { // Execute the query and retrieve results

            while (rs.next()) { // Iterate through the result set
                DimPerson person = new DimPerson(); // Create a new DimPerson object
                person.setPersonSk(rs.getInt("person_sk"));
                person.setPersonNk(rs.getString("person_nk"));
                person.setPersonRankNumber(rs.getInt("person_rank_number"));
                person.setPersonRank(rs.getString("person_rank"));
                person.setPersonName(rs.getString("person_name"));
                person.setPersonLastName1(rs.getString("person_last_name_1"));
                person.setPersonLastName2(rs.getString("person_last_name_2"));
                person.setPersonDni(rs.getString("person_dni"));
                person.setPersonPhone(rs.getString("person_phone"));
                person.setPersonDivision(rs.getString("person_division"));
                person.setPersonCurrentFlag(rs.getInt("person_current_flag"));
                personList.add(person); // Add the person to the list
            }
        } catch (SQLException e) {
            throw new DAOException("Error en SQL", e);
        }

        return personList; // Return the list of DimPerson objects
    }
}

