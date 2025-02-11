package org.jonatancarbonellmartinez.data.repository;

import org.jonatancarbonellmartinez.data.database.configuration.DatabaseConnection;
import org.jonatancarbonellmartinez.domain.repository.contract.PersonRepository;
import org.jonatancarbonellmartinez.domain.model.Person;
import org.jonatancarbonellmartinez.data.model.PersonEntity;
import org.jonatancarbonellmartinez.exceptions.DatabaseException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Singleton
public class PersonRepositoryImpl implements PersonRepository {
    private final DatabaseConnection databaseConnection;


    @Inject
    public PersonRepositoryImpl(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public CompletableFuture<List<Person>> getAllPersons() {
        return null;
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
        return null;
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