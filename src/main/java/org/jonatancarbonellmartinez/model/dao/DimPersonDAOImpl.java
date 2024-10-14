package org.jonatancarbonellmartinez.model.dao;

import org.jonatancarbonellmartinez.model.DimPerson;

import java.sql.Connection;

public class DimPersonDAOImpl implements DimPersonDAO {
    private Connection connection;

    public DimPersonDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(DimPerson person) {
        String sql = "INSERT INTO dim_person (person_nk, person_rank_number, ...) VALUES (?, ?, ...)";
        // Implementación de la lógica de inserción
    }

    @Override
    public DimPerson read(int personSk) {
        String sql = "SELECT * FROM dim_person WHERE person_sk = ?";
        // Implementación de la lógica de lectura
        return new DimPerson(); // you must return a person, i just created the new DimPerson(); to avoid errors
    }

    @Override
    public void update(DimPerson person) {

    }

    @Override
    public void delete(int personSk) {

    }
}




