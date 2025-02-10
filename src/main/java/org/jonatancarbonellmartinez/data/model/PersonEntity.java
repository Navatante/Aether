package org.jonatancarbonellmartinez.data.model;

import org.jonatancarbonellmartinez.domain.model.Person;

/**
 * JavaFX Properties NO deben usarse en esta capa.
 */

// Data Layer (how it's stored in database)
public class PersonEntity {
    private Integer personSk;
    private String personNk;
    private String personRank;
    private String personName;
    private String personLastName1;
    private String personLastName2;
    private String personPhone;
    private String personDni;
    private String personDivision;
    private String personRole;
    private Integer personOrder;
    private Integer personCurrentFlag;

    // Constructor por defecto necesario para JDBC
    public PersonEntity() {}

    // Metodo para mapear a domain model
    public Person toDomainModel() {
        return new Person.Builder()
                .id(personSk)
                .code(personNk)
                .rank(personRank)
                .name(personName)
                .lastName1(personLastName1)
                .lastName2(personLastName2)
                .phone(personPhone)
                .dni(personDni)
                .division(personDivision)
                .role(personRole)
                .order(personOrder)
                .isActive(personCurrentFlag == 1)
                .build();
    }

    // Metodo para crear desde domain model
    public static PersonEntity fromDomainModel(Person person) {
        PersonEntity entity = new PersonEntity();
        entity.setPersonSk(person.getId());
        entity.setPersonNk(person.getCode());
        entity.setPersonRank(person.getRank());
        entity.setPersonName(person.getName());
        entity.setPersonLastName1(person.getLastName1());
        entity.setPersonLastName2(person.getLastName2());
        entity.setPersonPhone(person.getPhone());
        entity.setPersonDni(person.getDni());
        entity.setPersonDivision(person.getDivision());
        entity.setPersonRole(person.getRole());
        entity.setPersonOrder(person.getOrder());
        entity.setPersonCurrentFlag(person.isActive() ? 1 : 0);
        return entity;
    }

    // Getters y Setters - objeto mutable para la capa de datos
    public Integer getPersonSk() {
        return personSk;
    }

    public void setPersonSk(Integer personSk) {
        this.personSk = personSk;
    }

    public String getPersonNk() {
        return personNk;
    }

    public void setPersonNk(String personNk) {
        this.personNk = personNk;
    }

    public String getPersonRank() {
        return personRank;
    }

    public void setPersonRank(String personRank) {
        this.personRank = personRank;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonLastName1() {
        return personLastName1;
    }

    public void setPersonLastName1(String personLastName1) {
        this.personLastName1 = personLastName1;
    }

    public String getPersonLastName2() {
        return personLastName2;
    }

    public void setPersonLastName2(String personLastName2) {
        this.personLastName2 = personLastName2;
    }

    public String getPersonPhone() {
        return personPhone;
    }

    public void setPersonPhone(String personPhone) {
        this.personPhone = personPhone;
    }

    public String getPersonDni() {
        return personDni;
    }

    public void setPersonDni(String personDni) {
        this.personDni = personDni;
    }

    public String getPersonDivision() {
        return personDivision;
    }

    public void setPersonDivision(String personDivision) {
        this.personDivision = personDivision;
    }

    public String getPersonRole() {
        return personRole;
    }

    public void setPersonRole(String personRole) {
        this.personRole = personRole;
    }

    public Integer getPersonOrder() {
        return personOrder;
    }

    public void setPersonOrder(Integer personOrder) {
        this.personOrder = personOrder;
    }

    public Integer getPersonCurrentFlag() {
        return personCurrentFlag;
    }

    public void setPersonCurrentFlag(Integer personCurrentFlag) {
        this.personCurrentFlag = personCurrentFlag;
    }
}
