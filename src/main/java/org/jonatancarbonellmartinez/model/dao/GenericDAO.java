package org.jonatancarbonellmartinez.model.dao;


import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.entities.Person;

import java.util.List;

public interface GenericDAO<T,K> {
    void create(T person) throws DatabaseException;
    Person read(K personSk) throws DatabaseException;
    void update(T person) throws DatabaseException;
    void delete(K personSk) throws DatabaseException; // I think im not gonna let to delete persons
    List<T> getAll() throws DatabaseException;
}
