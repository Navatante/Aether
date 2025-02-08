package org.jonatancarbonellmartinez.data.mapper;

import org.jonatancarbonellmartinez.data.model.PersonEntity;
import org.jonatancarbonellmartinez.domain.model.Person;

import javax.inject.Singleton;

// New mapper class to separate mapping logic from entity
@Singleton
public class PersonMapper {
    public Person toDomain(PersonEntity entity) {
        return new Person.Builder()
                .id(entity.getPersonSk())
                // ... rest of mapping
                .build();
    }

    public PersonEntity toEntity(Person domain) {
        PersonEntity entity = new PersonEntity();
        entity.setPersonSk(domain.getId());
        // ... rest of mapping
        return entity;
    }
}
