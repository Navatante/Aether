package org.jonatancarbonellmartinez.data.database;

import org.jonatancarbonellmartinez.xexceptions.DatabaseException;
import org.jonatancarbonellmartinez.data.model.Entity;
import org.jonatancarbonellmartinez.data.model.PersonEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO implements GenericDAO<PersonEntity,Integer> {

    @Override
    public void insert(PersonEntity personEntity) throws DatabaseException {

        // Paso 1: Verificar si ya existe un registro con el mismo número de orden
        if (checkIfOrderExists(personEntity.getPersonOrder())) {
            // Paso 2: Si existe, incrementar el orden de los registros activos con orden >= al nuevo
            incrementOrders(personEntity.getPersonOrder());
        }
        String enableForeignKeys = "PRAGMA foreign_keys = ON;";
        String sql = "INSERT INTO dim_person (person_nk, person_rank, person_name, person_last_name_1, person_last_name_2, person_phone, person_dni, person_division, person_rol, person_order, person_current_flag)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Database.getInstance().getConnection();
             Statement stmt = connection.createStatement();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            // Habilitar claves foráneas
            stmt.execute(enableForeignKeys);

            pstmt.setString(1, personEntity.getPersonNk());
            pstmt.setString(2, personEntity.getPersonRank());
            pstmt.setString(3, personEntity.getPersonName());
            pstmt.setString(4, personEntity.getPersonLastName1());
            pstmt.setString(5, personEntity.getPersonLastName2());
            pstmt.setString(6, personEntity.getPersonPhone());
            pstmt.setString(7, personEntity.getPersonDni());
            pstmt.setString(8, personEntity.getPersonDivision());
            pstmt.setString(9, personEntity.getPersonRole());
            pstmt.setInt(10, personEntity.getPersonOrder());
            pstmt.setInt(11, personEntity.getPersonCurrentFlag());

            pstmt.executeUpdate();  // Cambiar execute() por executeUpdate() para inserciones
        } catch (SQLException e) {
            throw new DatabaseException("Error insertando persona en la base de datos.", e);
        }
    }

    @Override
    public Entity read(Integer entitySk) throws DatabaseException {
        String sql = "SELECT * FROM dim_person WHERE person_sk = ?";
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, entitySk);

            // Execute the query and get the ResultSet
            try (ResultSet rs = pstmt.executeQuery()) {
                // Check if a person with the given ID exists
                if (rs.next()) {
                    return mapResultSetToEntity(rs); // Return the populated Person object
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error buscando persona por ID.", e);
        }
        return null; // Return null if no person was found with the given ID
    }

    @Override
    public void update(PersonEntity entity, int skToUpdate) throws DatabaseException {
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
            pstmt.setString(9, entity.getPersonRole());
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
    public List<PersonEntity> getAll() throws DatabaseException {
        String sql = "SELECT * FROM dim_person ORDER BY person_order";
        List<PersonEntity> personEntityList = new ArrayList<>();

        // Obtain a new connection each time the method is called
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                personEntityList.add( (PersonEntity)mapResultSetToEntity(rs) );
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error al acceder al personal.", e);
        }

        return personEntityList;
    }

    public List<PersonEntity> getOnlyActualPilots() throws DatabaseException {
        String sql = "SELECT * FROM dim_person WHERE person_rol='Piloto' AND Person_current_flag=1 ORDER BY person_order";
        List<PersonEntity> pilotList = new ArrayList<>();

        // Obtain a new connection each time the method is called
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                pilotList.add( (PersonEntity)mapResultSetToEntity(rs) );
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error al acceder a los pilotos.", e);
        }

        return pilotList;
    }

    public List<PersonEntity> getOnlyActualDvs() throws DatabaseException {
        String sql = "SELECT * FROM dim_person WHERE person_rol='Dotación' AND Person_current_flag=1 ORDER BY person_order";
        List<PersonEntity> dvList = new ArrayList<>();

        // Obtain a new connection each time the method is called
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                dvList.add( (PersonEntity)mapResultSetToEntity(rs) );
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error al acceder a los pilotos.", e);
        }

        return dvList;
    }

    // Utility method to map a ResultSet row to a DimPerson object
    @Override
    public Entity mapResultSetToEntity(ResultSet rs) throws SQLException {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setPersonSk(rs.getInt("person_sk"));
        personEntity.setPersonNk(rs.getString("person_nk"));
        personEntity.setPersonRank(rs.getString("person_rank"));
        personEntity.setPersonName(rs.getString("person_name"));
        personEntity.setPersonLastName1(rs.getString("person_last_name_1"));
        personEntity.setPersonLastName2(rs.getString("person_last_name_2"));
        personEntity.setPersonPhone(rs.getString("person_phone"));
        personEntity.setPersonDni(rs.getString("person_dni"));
        personEntity.setPersonDivision(rs.getString("person_division"));
        personEntity.setPersonOrder(rs.getInt("person_order"));
        personEntity.setPersonRole(rs.getString("person_rol"));
        personEntity.setPersonCurrentFlag(rs.getInt("person_current_flag"));
        return (Entity)personEntity;
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


