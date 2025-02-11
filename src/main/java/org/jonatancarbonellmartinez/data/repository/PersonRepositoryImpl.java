package org.jonatancarbonellmartinez.data.repository;

import org.jonatancarbonellmartinez.data.database.configuration.DatabaseConnection;
import org.jonatancarbonellmartinez.domain.repository.contract.PersonRepository;
import org.jonatancarbonellmartinez.domain.model.Person;
import org.jonatancarbonellmartinez.data.model.PersonEntity;
import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.data.database.DAO.PersonDAO;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Singleton
public class PersonRepositoryImpl implements PersonRepository {
    private final DatabaseConnection databaseConnection;
    private final PersonDAO personDAO;

    @Inject
    public PersonRepositoryImpl(DatabaseConnection databaseConnection, PersonDAO personDAO) {
        this.databaseConnection = databaseConnection;
        this.personDAO = personDAO;
    }

    @Override
    public CompletableFuture<List<Person>> getAllPersons() {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = databaseConnection.getConnection()) {
                return personDAO.findAll(connection).stream()
                        .map(PersonEntity::toDomainModel)
                        .collect(Collectors.toList());
            } catch (Exception e) {
                throw new DatabaseException("Error accessing persons", e);
            }
        });
    }

    @Override
    public CompletableFuture<List<Person>> getActivePilots() {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = databaseConnection.getConnection()) {
                return personDAO.getOnlyActualPilots(connection).stream()
                        .map(PersonEntity::toDomainModel)
                        .collect(Collectors.toList());
            } catch (Exception e) {
                throw new DatabaseException("Error accessing pilots", e);
            }
        });
    }

    @Override
    public CompletableFuture<List<Person>> getActiveCrew() {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = databaseConnection.getConnection()) {
                return personDAO.getOnlyActualDvs(connection).stream()
                        .map(PersonEntity::toDomainModel)
                        .collect(Collectors.toList());
            } catch (Exception e) {
                throw new DatabaseException("Error accessing crew", e);
            }
        });
    }

    @Override
    public CompletableFuture<Person> getPersonById(int id) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = databaseConnection.getConnection()) {
                PersonEntity entity = personDAO.findById(id, connection);
                return entity != null ? entity.toDomainModel() : null;
            } catch (Exception e) {
                throw new DatabaseException("Error accessing person with id: " + id, e);
            }
        });
    }

    @Override
    public CompletableFuture<Boolean> insertPerson(Person person) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = databaseConnection.getConnection()) {
                databaseConnection.beginTransaction(connection);
                try {
                    PersonEntity entity = PersonEntity.fromDomainModel(person);
                    personDAO.insert(entity, connection);
                    databaseConnection.commitTransaction(connection);
                    return true;
                } catch (Exception e) {
                    databaseConnection.rollbackTransaction(connection);
                    throw new DatabaseException("Error inserting person", e);
                }
            } catch (Exception e) {
                throw new DatabaseException("Error in database connection", e);
            }
        });
    }

    @Override
    public CompletableFuture<Boolean> updatePerson(Person person, int id) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = databaseConnection.getConnection()) {
                databaseConnection.beginTransaction(connection);
                try {
                    PersonEntity entity = PersonEntity.fromDomainModel(person);
                    personDAO.update(entity, connection);
                    databaseConnection.commitTransaction(connection);
                    return true;
                } catch (Exception e) {
                    databaseConnection.rollbackTransaction(connection);
                    throw new DatabaseException("Error updating person", e);
                }
            } catch (Exception e) {
                throw new DatabaseException("Error in database connection", e);
            }
        });
    }

    @Override
    public CompletableFuture<Boolean> updatePersonStatus(int id, boolean isActive) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = databaseConnection.getConnection()) {
                databaseConnection.beginTransaction(connection);
                try {
                    PersonEntity entity = personDAO.findById(id, connection);
                    if (entity != null) {
                        entity.setPersonCurrentFlag(isActive ? 1 : 0);
                        personDAO.update(entity, connection);
                        databaseConnection.commitTransaction(connection);
                        return true;
                    }
                    return false;
                } catch (Exception e) {
                    databaseConnection.rollbackTransaction(connection);
                    throw new DatabaseException("Error updating person status", e);
                }
            } catch (Exception e) {
                throw new DatabaseException("Error in database connection", e);
            }
        });
    }
}