package org.jonatancarbonellmartinez.model.dao;


import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.entities.Person;

import java.util.List;

public interface PersonDAO extends GenericDAO<Person, Integer> {
    // hereda todos los metodos de GenericDAO y ademas puedo implementar los metodos que yo quiera para este DAO en especifico.
    // getActivePerson . getPassivePerson
    List<Person> getCurrents(Integer currentFlag) throws DatabaseException;
}

