package org.jonatancarbonellmartinez.model.entities;

public class DimPerson {
    private Integer personSk; // Primary Key
    private String personNk; // Unique identifier
    private Integer personRankNumber;
    private String personRank;
    private String personName;
    private String personLastName1;
    private String personLastName2;
    private String personDni;
    private String personPhone;
    private String personDivision;
    private Integer personCurrentFlag;

    // Default constructor
    public DimPerson() {}

    // Constructor with parameters
    public DimPerson(Integer personSk, String personNk, Integer personRankNumber, String personRank,
                     String personName, String personLastName1, String personLastName2,
                     String personDni, String personPhone, String personDivision, Integer personCurrentFlag) {
        this.personSk = personSk;
        this.personNk = personNk;
        this.personRankNumber = personRankNumber;
        this.personRank = personRank;
        this.personName = personName;
        this.personLastName1 = personLastName1;
        this.personLastName2 = personLastName2;
        this.personDni = personDni;
        this.personPhone = personPhone;
        this.personDivision = personDivision;
        this.personCurrentFlag = personCurrentFlag;
    }

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

    public Integer getPersonRankNumber() {
        return personRankNumber;
    }

    public void setPersonRankNumber(Integer personRankNumber) {
        this.personRankNumber = personRankNumber;
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

    public String getPersonDni() {
        return personDni;
    }

    public void setPersonDni(String personDni) {
        this.personDni = personDni;
    }

    public String getPersonPhone() {
        return personPhone;
    }

    public void setPersonPhone(String personPhone) {
        this.personPhone = personPhone;
    }

    public String getPersonDivision() {
        return personDivision;
    }

    public void setPersonDivision(String personDivision) {
        this.personDivision = personDivision;
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
        return "DimPerson{" +
                "personSk=" + personSk +
                ", personNk='" + personNk + '\'' +
                ", personRankNumber=" + personRankNumber +
                ", personRank='" + personRank + '\'' +
                ", personName='" + personName + '\'' +
                ", personLastName1='" + personLastName1 + '\'' +
                ", personLastName2='" + personLastName2 + '\'' +
                ", personDni='" + personDni + '\'' +
                ", personPhone='" + personPhone + '\'' +
                ", personDivision='" + personDivision + '\'' +
                ", personCurrentFlag=" + personCurrentFlag +
                '}';
    }
}
