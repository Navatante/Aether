package org.jonatancarbonellmartinez.data.repository;

import org.jonatancarbonellmartinez.domain.model.PersonDomain;
import org.jonatancarbonellmartinez.data.model.PersonEntity;
import org.jonatancarbonellmartinez.domain.repository.contract.PersonRepository;
import org.jonatancarbonellmartinez.services.CustomLogger;
import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.data.database.DAO.PersonDAO;
import org.jonatancarbonellmartinez.data.mapper.PersonEntityDomainMapper;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Eliminé la dependencia de DatabaseConnection ya que ahora la conexión se maneja externamente.
 * Removí CompletableFuture ya que la gestión de la asincronía se puede manejar en una capa superior.
 * Ahora todos los métodos reciben la conexión como parámetro.
 * Eliminé el manejo de transacciones (begin/commit/rollback) ya que esto también se debería manejar en la capa superior que provee la conexión.
 * Simplifiqué el manejo de excepciones ya que no necesitamos manejar excepciones relacionadas con la conexión.
 *
 * Esta refactorización permite:
 *
 * Mejor control de las transacciones desde la capa superior
 * Reutilización de la misma conexión para múltiples operaciones
 * Código más limpio y con una única responsabilidad
 * Mejor testabilidad al poder inyectar la conexión
 */

@Singleton
public class PersonRepositoryImpl implements PersonRepository {
    private final PersonDAO personDAO;
    private final PersonEntityDomainMapper personEntityDomainMapper;

    @Inject
    public PersonRepositoryImpl(PersonDAO personDAO, PersonEntityDomainMapper personEntityDomainMapper) {
        this.personDAO = personDAO;
        this.personEntityDomainMapper = personEntityDomainMapper;
    }

    @Override
    public List<PersonDomain> getAllPersons(Connection connection) {
        try {
            return personDAO.findAll(connection).stream()
                    .map(personEntityDomainMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            CustomLogger.logError("Error accessing persons", e);
            throw new DatabaseException("Error accessing persons", e);
        }
    }

    public List<PersonDomain> getActivePilots(Connection connection) {
        try {
            return personDAO.getOnlyActualPilots(connection).stream()
                    .map(personEntityDomainMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            CustomLogger.logError("Error accessing pilots", e);
            throw new DatabaseException("Error accessing pilots", e);
        }
    }

    public List<PersonDomain> getActiveCrew(Connection connection) {
        try {
            return personDAO.getOnlyActualDvs(connection).stream()
                    .map(personEntityDomainMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            CustomLogger.logError("Error accessing crew", e);
            throw new DatabaseException("Error accessing crew", e);
        }
    }

    public PersonDomain getPersonById(Connection connection, int id) {
        try {
            PersonEntity entity = personDAO.findById(id, connection);
            return entity != null ? personEntityDomainMapper.toDomain(entity) : null;
        } catch (Exception e) {
            CustomLogger.logError("Error accessing person with id: " + id, e);
            throw new DatabaseException("Error accessing person with id: " + id, e);
        }
    }

    public Boolean insertPerson(Connection connection, PersonDomain personDomain) {
        try {
            PersonEntity entity = personEntityDomainMapper.toEntity(personDomain);
            System.out.println(entity);
            personDAO.insert(entity, connection);
            return true;
        } catch (Exception e) {
            CustomLogger.logError("Error inserting person", e);
            throw new DatabaseException("Error inserting person", e);
        }
    }

    public Boolean updatePerson(Connection connection, PersonDomain personDomain, int id) {
        try {
            PersonEntity entity = personEntityDomainMapper.toEntity(personDomain);
            personDAO.update(entity, connection);
            return true;
        } catch (Exception e) {
            CustomLogger.logError("Error updating person", e);
            throw new DatabaseException("Error updating person", e);
        }
    }

    public Boolean updatePersonStatus(Connection connection, int id, boolean isActive) {
        try {
            PersonEntity entity = personDAO.findById(id, connection);
            if (entity != null) {
                entity.setPersonCurrentFlag(isActive ? 1 : 0);
                personDAO.update(entity, connection);
                return true;
            }
            return false;
        } catch (Exception e) {
            CustomLogger.logError("Error updating person status", e);
            throw new DatabaseException("Error updating person status", e);
        }
    }
}