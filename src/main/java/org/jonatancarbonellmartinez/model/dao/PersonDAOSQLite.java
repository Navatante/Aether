package org.jonatancarbonellmartinez.model.dao;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.entities.Person;
import org.jonatancarbonellmartinez.model.utilities.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDAOSQLite implements PersonDAO {

    @Override
    public void create(Person person) throws DatabaseException {

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
        String sql = "SELECT * FROM dim_person ORDER BY person_sk DESC";
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
