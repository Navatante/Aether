package org.jonatancarbonellmartinez.domain.repository.contract;

import org.jonatancarbonellmartinez.domain.model.Person;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PersonRepository {
    CompletableFuture<List<Person>> getAllPersons();
    CompletableFuture<List<Person>> getActivePilots();
    CompletableFuture<List<Person>> getActiveCrew();
    CompletableFuture<Person> getPersonById(int id);
    CompletableFuture<Boolean> insertPerson(Person person);
    CompletableFuture<Boolean> updatePerson(Person person, int id);
    CompletableFuture<Boolean> updatePersonStatus(int id, boolean isActive);
}
