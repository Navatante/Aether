package org.jonatancarbonellmartinez.model.dao;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.entities.Entity;
import org.jonatancarbonellmartinez.model.entities.Person;
import org.jonatancarbonellmartinez.utilities.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAOSQLite implements GenericDAO<Person,Integer> {

    @Override
    public void insert(Person person) throws DatabaseException {

        // Paso 1: Verificar si ya existe un registro con el mismo número de orden
        if (checkIfOrderExists(person.getPersonOrder())) {
            // Paso 2: Si existe, incrementar el orden de los registros activos con orden >= al nuevo
            incrementOrders(person.getPersonOrder());
        }
        String enableForeignKeys = "PRAGMA foreign_keys = ON;";
        String sql = "INSERT INTO dim_person (person_nk, person_rank, person_name, person_last_name_1, person_last_name_2, person_phone, person_dni, person_division, person_rol, person_order, person_current_flag)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Database.getInstance().getConnection();
             Statement stmt = connection.createStatement();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            // Habilitar claves foráneas
            stmt.execute(enableForeignKeys);

            pstmt.setString(1, person.getPersonNk());
            pstmt.setString(2, person.getPersonRank());
            pstmt.setString(3, person.getPersonName());
            pstmt.setString(4, person.getPersonLastName1());
            pstmt.setString(5, person.getPersonLastName2());
            pstmt.setString(6, person.getPersonPhone());
            pstmt.setString(7, person.getPersonDni());
            pstmt.setString(8, person.getPersonDivision());
            pstmt.setString(9, person.getPersonRol());
            pstmt.setInt(10, person.getPersonOrder());
            pstmt.setInt(11, person.getPersonCurrentFlag());

            pstmt.executeUpdate();  // Cambiar execute() por executeUpdate() para inserciones
        } catch (SQLException e) {
            throw new DatabaseException("Error insertando persona en la base de datos.", e);
        }
    }

    @Override
    public Person read(Integer entitySk) throws DatabaseException {
        String sql = "SELECT * FROM dim_person WHERE person_sk = ?";
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, entitySk);

            // Execute the query and get the ResultSet
            try (ResultSet rs = pstmt.executeQuery()) {
                // Check if a person with the given ID exists
                if (rs.next()) {
                    return (Person)mapResultSetToEntity(rs); // Return the populated Person object
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error buscando persona por ID.", e);
        }
        return null; // Return null if no person was found with the given ID
    }

    @Override
    public void update(Person entity, int skToUpdate) throws DatabaseException {
        String enableForeignKeys = "PRAGMA foreign_keys = ON;";
        String sql = "UPDATE dim_person\n" +
                    "SET \n" +
                    "    person_nk = ?," +
                    "    person_rank = ?," +
                    "    person_name = ?," +
                    "    person_last_name_1 = ?," +
                    "    person_last_name_2 = ?," +
                    "    person_phone = ?," +
                    "    person_dni = ?," +
                    "    person_division = ?," +
                    "    person_rol = ?," +
                    "    person_order = ?," +
                    "    person_current_flag = ?" +
                    "WHERE person_sk = ?";

        try (Connection connection = Database.getInstance().getConnection();
             Statement stmt = connection.createStatement();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            // Habilitar claves foráneas
            stmt.execute(enableForeignKeys);

            pstmt.setString(1, entity.getPersonNk());
            pstmt.setString(2, entity.getPersonRank());
            pstmt.setString(3, entity.getPersonName());
            pstmt.setString(4, entity.getPersonLastName1());
            pstmt.setString(5, entity.getPersonLastName2());
            pstmt.setString(6, entity.getPersonPhone());
            pstmt.setString(7, entity.getPersonDni());
            pstmt.setString(8, entity.getPersonDivision());
            pstmt.setString(9, entity.getPersonRol());
            pstmt.setInt(10, entity.getPersonOrder());
            pstmt.setInt(11, entity.getPersonCurrentFlag());
            pstmt.setInt(12, skToUpdate);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error editando persona en la base de datos", e);
        }
    }

    @Override
    public void delete(Integer personSk) throws DatabaseException {
        // I will not permit to delete persons.
    }

    @Override
    // Each DAO method should handle its own connection lifecycle, creating a new connection
    public List<Person> getAll() throws DatabaseException {
        String sql = "SELECT * FROM dim_person ORDER BY person_order";
        List<Person> personList = new ArrayList<>();

        // Obtain a new connection each time the method is called
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                personList.add( (Person)mapResultSetToEntity(rs) );
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error al acceder al personal.", e);
        }

        return personList;
    }

    public List<Person> getOnlyActualPilots() throws DatabaseException {
        String sql = "SELECT * FROM dim_person WHERE person_rol='Piloto' AND Person_current_flag=1 ORDER BY person_order";
        List<Person> pilotList = new ArrayList<>();

        // Obtain a new connection each time the method is called
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                pilotList.add( (Person)mapResultSetToEntity(rs) );
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error al acceder a los pilotos.", e);
        }

        return pilotList;
    }

    public List<Person> getOnlyActualDvs() throws DatabaseException {
        String sql = "SELECT * FROM dim_person WHERE person_rol='Dotación' AND Person_current_flag=1 ORDER BY person_order";
        List<Person> dvList = new ArrayList<>();

        // Obtain a new connection each time the method is called
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                dvList.add( (Person)mapResultSetToEntity(rs) );
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error al acceder a los pilotos.", e);
        }

        return dvList;
    }

    // Utility method to map a ResultSet row to a DimPerson object
    @Override
    public Entity mapResultSetToEntity(ResultSet rs) throws SQLException {
        Person person = new Person();
        person.setPersonSk(rs.getInt("person_sk"));
        person.setPersonNk(rs.getString("person_nk"));
        person.setPersonRank(rs.getString("person_rank"));
        person.setPersonName(rs.getString("person_name"));
        person.setPersonLastName1(rs.getString("person_last_name_1"));
        person.setPersonLastName2(rs.getString("person_last_name_2"));
        person.setPersonPhone(rs.getString("person_phone"));
        person.setPersonDni(rs.getString("person_dni"));
        person.setPersonDivision(rs.getString("person_division"));
        person.setPersonOrder(rs.getInt("person_order"));
        person.setPersonRol(rs.getString("person_rol"));
        person.setPersonCurrentFlag(rs.getInt("person_current_flag"));
        return person;
    }

    // Metodo para comprobar si ya existe un registro con el mismo orden
    private boolean checkIfOrderExists(int orden) throws DatabaseException {
        String sql = "SELECT COUNT(*) FROM dim_person WHERE person_order = ? AND person_current_flag = 1";
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, orden);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {  // Mover el cursor al primer registro
                    return rs.getInt(1) > 0;  // Obtener el primer valor (COUNT)
                }
                return false;  // Si no hay resultados
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error al verificar el orden en la base de datos", e);
        }
    }

    // Metodo para incrementar el orden de los registros >= al nuevo
    private void incrementOrders(int orden) throws DatabaseException {
        String enableForeignKeys = "PRAGMA foreign_keys = ON;";
        String sql = "UPDATE dim_person SET person_order = person_order + 1 WHERE person_order >= ? AND person_current_flag = 1";

        try (Connection connection = Database.getInstance().getConnection();
             Statement stmt = connection.createStatement();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            // Habilitar claves foráneas
            stmt.execute(enableForeignKeys);

            pstmt.setInt(1, orden);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error al incrementar el orden en la base de datos", e);
        }
    }
}


