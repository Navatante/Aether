package org.jonatancarbonellmartinez.domain.repository.contract;

import org.jonatancarbonellmartinez.domain.model.Person;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Define los métodos que se pueden usar en la app, pero no implementa la lógica.
 */

public interface PersonRepository {
    CompletableFuture<List<Person>> getAllPersons();
    CompletableFuture<List<Person>> getActivePilots();
    CompletableFuture<List<Person>> getActiveCrew();
    CompletableFuture<Person> getPersonById(int id);
    CompletableFuture<Boolean> insertPerson(Person person);
    CompletableFuture<Boolean> updatePerson(Person person, int id);
    CompletableFuture<Boolean> updatePersonStatus(int id, boolean isActive);
}
