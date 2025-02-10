package org.jonatancarbonellmartinez.data.database;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import java.sql.Connection;
import java.util.List;

public interface GenericDAO<T, K> {
    void insert(T entity, Connection connection) throws DatabaseException;
    T findById(K id, Connection connection) throws DatabaseException;
    void update(T entity, Connection connection) throws DatabaseException;
    List<T> findAll(Connection connection) throws DatabaseException;
}