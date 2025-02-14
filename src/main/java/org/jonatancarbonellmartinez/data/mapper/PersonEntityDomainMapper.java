package org.jonatancarbonellmartinez.data.mapper;

import org.jonatancarbonellmartinez.data.model.PersonEntity;
import org.jonatancarbonellmartinez.domain.model.PersonDomain;
import org.jonatancarbonellmartinez.services.DateService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Los mappers son comúnmente utilizados en varios lugares dentro de la arquitectura de una aplicación, especialmente en:
 *
 * Repositorios/DAOs:
 *
 * javaCopy@Singleton
 * public class PersonRepository {
 *     private final PersonMapper mapper;
 *
 *     public Person findById(Integer id) {
 *         PersonEntity entity = // consulta a base de datos
 *         return mapper.toDomain(entity);  // convierte a modelo de dominio
 *     }
 *
 *     public void save(Person person) {
 *         PersonEntity entity = mapper.toEntity(person);
 *         // guarda en base de datos
 *     }
 * }
 */

// New mapper class to separate mapping logic from entity
@Singleton
public class PersonEntityDomainMapper {
    private final DateService dateService;

    @Inject
    public PersonEntityDomainMapper(DateService dateService) {
        this.dateService = dateService;
    }
    public PersonDomain toDomain(PersonEntity entity) {

        return new PersonDomain.Builder()
                .id(entity.getPersonSk())
                .code(entity.getPersonNk())
                .rank(entity.getPersonRank())
                .cuerpo(entity.getCuerpo())
                .especialidad(entity.getEspecialidad())
                .name(entity.getPersonName())
                .lastName1(entity.getPersonLastName1())
                .lastName2(entity.getPersonLastName2())
                .phone(entity.getPersonPhone())
                .dni(entity.getPersonDni())
                .division(entity.getPersonDivision())
                .role(entity.getPersonRole())
                .antiguedadEmpleo(dateService.convertUnixToUTCDate(entity.getAntiguedadEmpleo()))
                .fechaEmbarque(dateService.convertUnixToUTCDate(entity.getFechaEmbarque()))
                .order(entity.getPersonOrder())
                .isActive(entity.getPersonCurrentFlag() == 1 ? true : false)
                .build();
    }

    public PersonEntity toEntity(PersonDomain domain) {
        PersonEntity entity = new PersonEntity();
        entity.setPersonSk(domain.getId());
        entity.setPersonSk(domain.getId());
        entity.setPersonNk(domain.getCode());
        entity.setPersonRank(domain.getRank());
        entity.setCuerpo(domain.getCuerpo());
        entity.setEspecialidad(domain.getEspecialidad());
        entity.setPersonName(domain.getName());
        entity.setPersonLastName1(domain.getLastName1());
        entity.setPersonLastName2(domain.getLastName2());
        entity.setPersonPhone(domain.getPhone());
        entity.setPersonDni(domain.getDni());
        entity.setPersonDivision(domain.getDivision());
        entity.setPersonRole(domain.getRole());
        entity.setAntiguedadEmpleo(dateService.convertUTCDateToUnix(domain.getAntiguedadEmpleo()));
        entity.setFechaEmbarque(dateService.convertUTCDateToUnix(domain.getFechaEmbarque()));
        entity.setPersonOrder(domain.getOrder());
        entity.setPersonCurrentFlag(domain.isActive() ? 1 : 0);
        return entity;
    }
}
