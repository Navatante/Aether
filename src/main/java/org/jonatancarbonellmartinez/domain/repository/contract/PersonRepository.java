package org.jonatancarbonellmartinez.domain.repository.contract;

import org.jonatancarbonellmartinez.domain.model.PersonDomain;

import java.sql.Connection;
import java.util.List;

/**
 * Define los métodos que se pueden usar en la app, pero no implementa la lógica.
 */

/**
 * El CompletableFuture es manejado por el DatabaseConnection.executeOperation, no necesita estar en la interfaz del repositorio.
 */

public interface PersonRepository {
    List<PersonDomain> getAllPersons(Connection connection);  // No CompletableFuture
    List<PersonDomain> getActivePilots(Connection connection);
    List<PersonDomain> getActiveCrew(Connection connection);
    PersonDomain getPersonById(Connection connection, int id);
    Boolean insertPerson(Connection connection, PersonDomain personDomain);
    Boolean updatePerson(Connection connection, PersonDomain personDomain, int id);
    Boolean updatePersonStatus(Connection connection, int id, boolean isActive);
}
