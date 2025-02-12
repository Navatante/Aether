package org.jonatancarbonellmartinez.domain.repository.contract;

import org.jonatancarbonellmartinez.domain.model.Person;

import java.sql.Connection;
import java.util.List;

/**
 * Define los métodos que se pueden usar en la app, pero no implementa la lógica.
 */

public interface PersonRepository {
    List<Person> getAllPersons(Connection connection);
    List<Person> getActivePilots(Connection connection);
    List<Person> getActiveCrew(Connection connection);
    Person getPersonById(Connection connection, int id);
    Boolean insertPerson(Connection connection, Person person);
    Boolean updatePerson(Connection connection, Person person, int id);
    Boolean updatePersonStatus(Connection connection, int id, boolean isActive);
}
