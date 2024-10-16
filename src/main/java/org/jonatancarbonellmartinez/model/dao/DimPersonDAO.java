package org.jonatancarbonellmartinez.model.dao;

import org.jonatancarbonellmartinez.model.dao.exceptions.DAOException;
import org.jonatancarbonellmartinez.model.entities.DimPerson;

import java.util.List;

public interface DimPersonDAO extends GenericDAO<DimPerson, Integer> {
    // hereda todos los metodos de GenericDAO y ademas puedo implementar los metodos que yo quiera para este DAO en especifico.
    // getActivePerson . getPassivePerson
    List<DimPerson> getCurrents(Integer currentFlag) throws DAOException;
}

