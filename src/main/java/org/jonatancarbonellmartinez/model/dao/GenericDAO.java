package org.jonatancarbonellmartinez.model.dao;

import org.jonatancarbonellmartinez.model.dao.exceptions.DAOException;
import org.jonatancarbonellmartinez.model.entities.DimPerson;

import java.util.List;

public interface GenericDAO<T,K> {
    void create(T person) throws DAOException;
    DimPerson read(K personSk) throws DAOException;
    void update(T person) throws DAOException;
    void delete(K personSk) throws DAOException; // I think im not gonna let to delete persons
    List<T> getAll() throws DAOException;
}
