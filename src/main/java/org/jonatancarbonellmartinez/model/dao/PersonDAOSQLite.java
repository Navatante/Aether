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
        String sql = "SELECT * FROM dim_crew ORDER BY crew_SK DESC";
        List<Person> personList = new ArrayList<>();

        // Obtain a new connection each time the method is called
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                personList.add(mapResultSetToPerson(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving all persons", e);
        }

        return personList;
    }

    // Utility method to map a ResultSet row to a DimPerson object
    private Person mapResultSetToPerson(ResultSet rs) throws SQLException {
        Person person = new Person();
        person.setPersonSk(rs.getInt("crew_sk"));
        person.setPersonNk(rs.getString("crew_nk"));
        person.setPersonRankNumber(rs.getInt("crew_rank_number"));
        person.setPersonRank(rs.getString("crew_rank"));
        person.setPersonName(rs.getString("crew_name"));
        person.setPersonLastName1(rs.getString("crew_last_name_1"));
        person.setPersonLastName2(rs.getString("crew_last_name_2"));
        person.setPersonDni(rs.getString("crew_dni"));
        person.setPersonPhone(rs.getString("crew_phone"));
        person.setPersonDivision(rs.getString("crew_division"));
        person.setPersonCurrentFlag(rs.getInt("crew_current_flag"));
        return person;
    }

}
