package org.jonatancarbonellmartinez.model.dao;

import org.jonatancarbonellmartinez.model.DimPerson;

public interface DimPersonDAO {
    void create(DimPerson person);
    DimPerson read(int personSk);
    void update(DimPerson person);
    void delete(int personSk);
}

