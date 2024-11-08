package org.jonatancarbonellmartinez.model.dao;


import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.entities.Entity;
import org.jonatancarbonellmartinez.model.entities.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T,K> {
    void create(T entity) throws DatabaseException;
    Entity read(K entitySk) throws DatabaseException;
    void update(T entity, int skToUpdate) throws DatabaseException;
    void delete(K entitySk) throws DatabaseException; // I think im not gonna let to delete persons
    List<T> getAll() throws DatabaseException;
    Entity  mapResultSetToEntity(ResultSet rs) throws SQLException;

}
