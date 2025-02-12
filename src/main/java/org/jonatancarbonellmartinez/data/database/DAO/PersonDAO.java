package org.jonatancarbonellmartinez.data.database.DAO;

import org.jonatancarbonellmartinez.exceptions.CustomLogger;
import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.data.model.PersonEntity;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class PersonDAO {

    private static final String INSERT_PERSON =
            "INSERT INTO dim_person (person_nk, person_rank, person_name, person_last_name_1, " +
                    "person_last_name_2, person_phone, person_dni, person_division, person_rol, " +
                    "person_order, person_current_flag) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_PERSON =
            "UPDATE dim_person SET person_nk = ?, person_rank = ?, person_name = ?, " +
                    "person_last_name_1 = ?, person_last_name_2 = ?, person_phone = ?, " +
                    "person_dni = ?, person_division = ?, person_rol = ?, person_order = ?, " +
                    "person_current_flag = ? WHERE person_sk = ?";

    private static final String FIND_BY_ID =
            "SELECT * FROM dim_person WHERE person_sk = ?";

    private static final String FIND_ALL =
            "SELECT * FROM dim_person ORDER BY person_order";

    private static final String FIND_ACTIVE_PILOTS =
            "SELECT * FROM dim_person WHERE person_rol='Piloto' AND person_current_flag=1 ORDER BY person_order";

    private static final String FIND_ACTIVE_CREW =
            "SELECT * FROM dim_person WHERE person_rol='Dotación' AND person_current_flag=1 ORDER BY person_order";

    private static final String CHECK_ORDER_EXISTS =
            "SELECT COUNT(*) FROM dim_person WHERE person_order = ? AND person_current_flag = 1";

    private static final String INCREMENT_ORDERS =
            "UPDATE dim_person SET person_order = person_order + 1 WHERE person_order >= ? AND person_current_flag = 1";

    @Inject
    public PersonDAO() {

    }

    public void insert(PersonEntity entity, Connection connection) throws DatabaseException {
        try {
            // Check and handle order conflicts
            if (checkIfOrderExists(entity.getPersonOrder(), connection)) {
                incrementOrders(entity.getPersonOrder(), connection);
            }

            // Enable foreign keys and insert
            try (PreparedStatement pstmt = connection.prepareStatement(INSERT_PERSON)) {
                setPersonParameters(pstmt, entity);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            CustomLogger.logError("Error inserting person", e);
            throw new DatabaseException("Error inserting person", e);
        }
    }

    public PersonEntity findById(Integer id, Connection connection) throws DatabaseException {
        try (PreparedStatement pstmt = connection.prepareStatement(FIND_BY_ID)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() ? mapResultSetToEntity(rs) : null;
            }
        } catch (SQLException e) {
            CustomLogger.logError("Error finding person with ID: " + id, e);
            throw new DatabaseException("Error finding person", e);
        }
    }

    public void update(PersonEntity entity, Connection connection) throws DatabaseException {
        try {
            try (PreparedStatement pstmt = connection.prepareStatement(UPDATE_PERSON)) {
                setPersonParameters(pstmt, entity);
                pstmt.setInt(12, entity.getPersonSk());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            CustomLogger.logError("Error updating person with ID: " + entity.getPersonSk(), e);
            throw new DatabaseException("Error updating person", e);
        }
    }

    public List<PersonEntity> findAll(Connection connection) throws DatabaseException {
        try (PreparedStatement pstmt = connection.prepareStatement(FIND_ALL);
             ResultSet rs = pstmt.executeQuery()) {
            List<PersonEntity> persons = new ArrayList<>();
            while (rs.next()) {
                persons.add(mapResultSetToEntity(rs));
            }
            return persons;
        } catch (SQLException e) {
            CustomLogger.logError("Error fetching all persons", e);
            throw new DatabaseException("Error fetching all persons", e);
        }
    }

    public List<PersonEntity> getOnlyActualPilots(Connection connection) throws DatabaseException {
        try (PreparedStatement pstmt = connection.prepareStatement(FIND_ACTIVE_PILOTS);
             ResultSet rs = pstmt.executeQuery()) {
            List<PersonEntity> pilots = new ArrayList<>();
            while (rs.next()) {
                pilots.add(mapResultSetToEntity(rs));
            }
            return pilots;
        } catch (SQLException e) {
            CustomLogger.logError("Error fetching active pilots", e);
            throw new DatabaseException("Error fetching active pilots", e);
        }
    }

    public List<PersonEntity> getOnlyActualDvs(Connection connection) throws DatabaseException {
        try (PreparedStatement pstmt = connection.prepareStatement(FIND_ACTIVE_CREW);
             ResultSet rs = pstmt.executeQuery()) {
            List<PersonEntity> crew = new ArrayList<>();
            while (rs.next()) {
                crew.add(mapResultSetToEntity(rs));
            }
            return crew;
        } catch (SQLException e) {
            CustomLogger.logError("Error fetching active crew", e);
            throw new DatabaseException("Error fetching active crew", e);
        }
    }

    private PersonEntity mapResultSetToEntity(ResultSet rs) throws SQLException {
        PersonEntity entity = new PersonEntity();
        entity.setPersonSk(rs.getInt("person_sk"));
        entity.setPersonNk(rs.getString("person_nk"));
        entity.setCuerpo(rs.getString("person_cuerpo"));
        entity.setEspecialidad(rs.getString("person_especialidad"));
        entity.setPersonRank(rs.getString("person_rank"));
        entity.setPersonName(rs.getString("person_name"));
        entity.setPersonLastName1(rs.getString("person_last_name_1"));
        entity.setPersonLastName2(rs.getString("person_last_name_2"));
        entity.setPersonPhone(rs.getString("person_phone"));
        entity.setPersonDni(rs.getString("person_dni"));
        entity.setPersonDivision(rs.getString("person_division"));
        entity.setPersonOrder(rs.getInt("person_order"));
        entity.setAntiguedadEmpleo(rs.getLong("person_a_emp"));
        entity.setFechaEmbarque(rs.getLong("person_f_emb"));
        entity.setPersonRole(rs.getString("person_rol"));
        entity.setPersonCurrentFlag(rs.getInt("person_current_flag"));
        return entity;
    }

    private boolean checkIfOrderExists(int order, Connection connection) throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement(CHECK_ORDER_EXISTS)) {
            pstmt.setInt(1, order);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    private void incrementOrders(int order, Connection connection) throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement(INCREMENT_ORDERS)) {
            pstmt.setInt(1, order);
            pstmt.executeUpdate();
        }
    }


    private void setPersonParameters(PreparedStatement pstmt, PersonEntity entity) throws SQLException {
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
    }
}
