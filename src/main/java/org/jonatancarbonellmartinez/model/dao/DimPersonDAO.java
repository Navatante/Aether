package org.jonatancarbonellmartinez.model.dao;

import org.jonatancarbonellmartinez.model.dao.exceptions.DimPersonDAOSysException;
import org.jonatancarbonellmartinez.model.entities.DimPerson;

public interface DimPersonDAO {
    void create(DimPerson person) throws DimPersonDAOSysException;
    void update(DimPerson person) throws DimPersonDAOSysException;
    void delete(int personSk) throws DimPersonDAOSysException;
    DimPerson findById(int personSk) throws DimPersonDAOSysException;
}

