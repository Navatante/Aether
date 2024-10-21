package org.jonatancarbonellmartinez.model.dao;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.entities.Person;
import org.jonatancarbonellmartinez.model.utilities.Database;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDAOSQLite implements PersonDAO {

    @Override
    public void create(Person person) throws DatabaseException {
        String sql = "INSERT INTO dim_person (person_nk, person_rank, person_name, person_last_name_1, person_last_name_2, person_phone, person_dni, person_division, person_rol, person_order, person_current_flag)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection connection = Database.getInstance().getConnection();
        PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, person.getPersonNk());
            pstmt.setString(2, person.getPersonRank());
            pstmt.setString(3, person.getPersonName());
            pstmt.setString(4, person.getPersonLastName1());
            pstmt.setString(5, person.getPersonLastName2());
            pstmt.setString(6, person.getPersonPhone());
            pstmt.setString(7,person.getPersonDni());
            pstmt.setString(8, person.getPersonDivision());
            pstmt.setString(9, person.getPersonRol());
            pstmt.setInt(10, person.getPersonOrder());
            pstmt.setInt(11, person.getPersonCurrentFlag());

            pstmt.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Error insertando persona en la base de datos",e);
        }
    }

    @Override
    public Person read(Integer personSk) throws DatabaseException {
        return null;
    }

    @Override
    public void update(Person person) throws DatabaseException {

    }

    @Override
    public void delete(Integer personSk) throws DatabaseException {
        // I will not permit to delete persons.
    }

    @Override
    public List<Person> getCurrents(Integer currentFlag) throws DatabaseException {
        return null;
    }

    @Override
    // Each DAO method should handle its own connection lifecycle, creating a new connection
    public List<Person> getAll() throws DatabaseException {
        String sql = "SELECT * FROM dim_person ORDER BY person_order DESC";
        List<Person> personList = new ArrayList<>();

        // Obtain a new connection each time the method is called
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                personList.add(mapResultSetToPerson(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error al acceder al personal", e);
        }

        return personList;
    }

    // Utility method to map a ResultSet row to a DimPerson object
    private Person mapResultSetToPerson(ResultSet rs) throws SQLException {
        Person person = new Person();
        person.setPersonSk(rs.getInt("person_sk"));
        person.setPersonNk(rs.getString("person_nk"));
        person.setPersonRank(rs.getString("person_rank"));
        person.setPersonName(rs.getString("person_name"));
        person.setPersonLastName1(rs.getString("person_last_name_1"));
        person.setPersonLastName2(rs.getString("person_last_name_2"));
        person.setPersonPhone(rs.getString("person_phone"));
        person.setPersonDivision(rs.getString("person_division"));
        person.setPersonOrder(rs.getInt("person_order"));
        person.setPersonRol(rs.getString("person_rol"));
        person.setPersonCurrentFlag(rs.getInt("person_current_flag"));
        return person;
    }

}
