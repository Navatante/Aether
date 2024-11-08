package org.jonatancarbonellmartinez.model.entities;

public class Person implements Entity {
    private Integer personSk;
    private String personNk;
    private String personRank;
    private String personName;
    private String personLastName1;
    private String personLastName2;
    private String personPhone;
    private String personDni;
    private String personDivision;
    private String personRol;
    private Integer personOrder;
    private Integer personCurrentFlag;

    // Getters and Setters
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

    public Integer getPersonOrder() {
        return personOrder;
    }

    public void setPersonOrder(Integer personOrder) {
        this.personOrder = personOrder;
    }

    public String getPersonRol() {
        return personRol;
    }

    public void setPersonRol(String personRol) {
        this.personRol = personRol;
    }

    public Integer getPersonCurrentFlag() {
        return personCurrentFlag;
    }

    public void setPersonCurrentFlag(Integer personCurrentFlag) {
        this.personCurrentFlag = personCurrentFlag;
    }

    // Override toString() for easy debugging
    @Override
    public String toString() {
        return personNk;
    }
}
