package org.jonatancarbonellmartinez.data.repository;

import org.jonatancarbonellmartinez.data.model.PersonEntity;
import org.jonatancarbonellmartinez.domain.repository.contract.PersonRepository;
import org.jonatancarbonellmartinez.domain.model.Person;
import org.jonatancarbonellmartinez.data.database.Database;
import org.jonatancarbonellmartinez.xexceptions.DatabaseException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Singleton
public class PersonRepositoryImpl implements PersonRepository {
    private final Database database;
    private final ExecutorService dbExecutor;

    @Inject
    public PersonRepositoryImpl(Database database) {
        this.database = database;
        this.dbExecutor = Executors.newFixedThreadPool(2);
    }

    @Override
    public CompletableFuture<List<Person>> getAllPersons() {
        return CompletableFuture.supplyAsync(() -> {
            List<PersonEntity> entities = new ArrayList<>();
            String sql = "SELECT * FROM dim_person ORDER BY person_order";

            try (Connection connection = database.getConnection();
                 PreparedStatement pstmt = connection.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    entities.add(mapResultSetToEntity(rs));
                }
            } catch (SQLException e) {
                throw new DatabaseException("Error accessing persons", e);
            }

            return entities.stream()
                    .map(PersonEntity::toDomainModel)
                    .collect(Collectors.toList());
        }, dbExecutor);
    }

    @Override
    public CompletableFuture<List<Person>> getActivePilots() {
        return null;
    }

    @Override
    public CompletableFuture<List<Person>> getActiveCrew() {
        return null;
    }

    @Override
    public CompletableFuture<Person> getPersonById(int id) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> insertPerson(Person person) {
        return CompletableFuture.supplyAsync(() -> {
            PersonEntity entity = PersonEntity.fromDomainModel(person);
            String sql = "INSERT INTO dim_person (person_nk, person_rank, person_name, " +
                    "person_last_name_1, person_last_name_2, person_phone, person_dni, " +
                    "person_division, person_rol, person_order, person_current_flag) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection connection = database.getConnection();
                 PreparedStatement pstmt = connection.prepareStatement(sql)) {

                pstmt.setString(1, entity.getPersonNk());
                pstmt.setString(2, entity.getPersonRank());
                pstmt.setString(3, entity.getPersonName());
                pstmt.setString(4, entity.getPersonLastName1());
                pstmt.setString(5, entity.getPersonLastName2());
                pstmt.setString(6, entity.getPersonPhone());
                pstmt.setString(7, entity.getPersonDni());
                pstmt.setString(8, entity.getPersonDivision());
                pstmt.setString(9, entity.getPersonRole());
                pstmt.setInt(10, entity.getPersonOrder());
                pstmt.setInt(11, entity.getPersonCurrentFlag());

                return pstmt.executeUpdate() > 0;
            } catch (SQLException e) {
                throw new DatabaseException("Error inserting person", e);
            }
        }, dbExecutor);
    }

    @Override
    public CompletableFuture<Boolean> updatePerson(Person person, int id) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> updatePersonStatus(int id, boolean isActive) {
        return null;
    }

    private PersonEntity mapResultSetToEntity(ResultSet rs) throws SQLException {
        PersonEntity entity = new PersonEntity();
        entity.setPersonSk(rs.getInt("person_sk"));
        entity.setPersonNk(rs.getString("person_nk"));
        entity.setPersonRank(rs.getString("person_rank"));
        entity.setPersonName(rs.getString("person_name"));
        entity.setPersonLastName1(rs.getString("person_last_name_1"));
        entity.setPersonLastName2(rs.getString("person_last_name_2"));
        entity.setPersonPhone(rs.getString("person_phone"));
        entity.setPersonDni(rs.getString("person_dni"));
        entity.setPersonDivision(rs.getString("person_division"));
        entity.setPersonRole(rs.getString("person_rol"));
        entity.setPersonOrder(rs.getInt("person_order"));
        entity.setPersonCurrentFlag(rs.getInt("person_current_flag"));
        return entity;
    }
}
