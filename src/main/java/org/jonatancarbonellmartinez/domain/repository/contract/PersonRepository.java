package org.jonatancarbonellmartinez.domain.repository.contract;

import org.jonatancarbonellmartinez.domain.model.Person;

import java.sql.Connection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Define los métodos que se pueden usar en la app, pero no implementa la lógica.
 */

/**
 * El CompletableFuture es manejado por el DatabaseConnection.executeOperation, no necesita estar en la interfaz del repositorio.
 */

public interface PersonRepository {
    List<Person> getAllPersons(Connection connection);  // No CompletableFuture
    List<Person> getActivePilots(Connection connection);
    List<Person> getActiveCrew(Connection connection);
    Person getPersonById(Connection connection, int id);
    Boolean insertPerson(Connection connection, Person person);
    Boolean updatePerson(Connection connection, Person person, int id);
    Boolean updatePersonStatus(Connection connection, int id, boolean isActive);
}
