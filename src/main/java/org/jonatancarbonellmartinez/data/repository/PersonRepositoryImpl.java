package org.jonatancarbonellmartinez.data.repository;

import org.jonatancarbonellmartinez.domain.repository.contract.PersonRepository;
import org.jonatancarbonellmartinez.domain.model.Person;
import org.jonatancarbonellmartinez.data.model.PersonEntity;
import org.jonatancarbonellmartinez.data.database.ConnectionManager;
import org.jonatancarbonellmartinez.xexceptions.DatabaseException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Singleton
public class PersonRepositoryImpl implements PersonRepository {
    private final ConnectionManager connectionManager;

    @Inject
    public PersonRepositoryImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public CompletableFuture<List<Person>> getAllPersons() {
        return CompletableFuture.supplyAsync(() -> {
            List<PersonEntity> entities = new ArrayList<>();
            String sql = "SELECT * FROM dim_person ORDER BY person_order";

            try (PreparedStatement pstmt = connectionManager.getConnection().prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    //entities.add(mapResultSetToEntity(rs));
                }

                return entities.stream()
                        .map(PersonEntity::toDomainModel)
                        .collect(Collectors.toList());

            } catch (SQLException e) {
                throw new DatabaseException("Error accessing persons", e);
            }
        });
    }

    // TODO Implementar otros métodos...
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
            String sql = "INSERT INTO dim_person (...) VALUES (...)";

            try (PreparedStatement pstmt = connectionManager.getConnection().prepareStatement(sql)) {
                // Set parameters...
                return pstmt.executeUpdate() > 0;
            } catch (SQLException e) {
                throw new DatabaseException("Error inserting person", e);
            }
        });
    }

    @Override
    public CompletableFuture<Boolean> updatePerson(Person person, int id) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> updatePersonStatus(int id, boolean isActive) {
        return null;
    }


}
