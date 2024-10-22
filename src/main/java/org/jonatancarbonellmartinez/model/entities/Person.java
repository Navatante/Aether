package org.jonatancarbonellmartinez.model.entities;

import java.util.HashSet;
import java.util.Set;

public class Person {
    private static final Set<String> VALID_RANKS = new HashSet<>();
    static {
        // Initialize the set of valid ranks
        VALID_RANKS.add("CF");
        VALID_RANKS.add("TCOL");
        VALID_RANKS.add("CC");
        VALID_RANKS.add("CTE");
        VALID_RANKS.add("TN");
        VALID_RANKS.add("CAP");
        VALID_RANKS.add("AN");
        VALID_RANKS.add("TTE");
        VALID_RANKS.add("STTE");
        VALID_RANKS.add("BG");
        VALID_RANKS.add("SG1");
        VALID_RANKS.add("SGTO");
        VALID_RANKS.add("CBMY");
        VALID_RANKS.add("CB1");
        VALID_RANKS.add("CBO");
        VALID_RANKS.add("MRO");
        VALID_RANKS.add("SDO");
    }
    private Integer personSk; // Primary Key
    private String personNk; // Unique identifier
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

    // Default constructor
    public Person() {}

    // Constructor with parameters
    public Person(Integer personSk, String personNk, String personRank,
                  String personName, String personLastName1, String personLastName2,
                  String personPhone, String dni, String personDivision, String personRol, Integer personOrder,
                  Integer personCurrentFlag) {
        this.personSk = personSk;
        this.personNk = personNk;
        this.personRank = personRank;
        this.personName = personName;
        this.personLastName1 = personLastName1;
        this.personLastName2 = personLastName2;
        this.personPhone = personPhone;
        this.personDni = dni;
        this.personDivision = personDivision;
        this.personRol = personRol;
        this.personOrder = personOrder;
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

        this.personNk = personNk.toUpperCase();
    }


    public String getPersonRank() {
        return personRank;
    }

    public void setPersonRank(String personRank) {
        if (!VALID_RANKS.contains(personRank)) {
            throw new IllegalArgumentException("Empleo no válido");
        }
        this.personRank = personRank;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        // Convert first character to uppercase and the rest to lowercase
        String firstLetter = personName.substring(0, 1).toUpperCase();
        String restOfString = personName.substring(1).toLowerCase();
        this.personName = firstLetter + restOfString;
    }

    public String getPersonLastName1() {
        return personLastName1;
    }

    public void setPersonLastName1(String personLastName1) {
        String firstLetter = personLastName1.substring(0, 1).toUpperCase();
        String restOfString = personLastName1.substring(1).toLowerCase();
        this.personLastName1 = firstLetter + restOfString;
    }

    public String getPersonLastName2() {
        return personLastName2;
    }

    public void setPersonLastName2(String personLastName2) {
        String firstLetter = personLastName2.substring(0, 1).toUpperCase();
        String restOfString = personLastName2.substring(1).toLowerCase();
        this.personLastName2 = firstLetter + restOfString;
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
        if (!"Piloto".equals(personRol) && !"Dotación".equals(personRol)) {
            throw new IllegalArgumentException("Rol inválido: debe ser 'Piloto' o 'Dotación'");
        }
        this.personRol = personRol;
    }

    public Integer getPersonCurrentFlag() {
        return personCurrentFlag;
    }

    public void setPersonCurrentFlag(Integer personCurrentFlag) {
        if(personCurrentFlag!=0 && personCurrentFlag!=1) {
            throw new IllegalArgumentException("Debe ser 0 o 1");
        }
        this.personCurrentFlag = personCurrentFlag;
    }

    // Override toString() for easy debugging
    @Override
    public String toString() {
        return "DimPerson{" +
                "personSk=" + personSk +
                ", personNk='" + personNk + '\'' +
                ", personRank='" + personRank + '\'' +
                ", personName='" + personName + '\'' +
                ", personLastName1='" + personLastName1 + '\'' +
                ", personLastName2='" + personLastName2 + '\'' +
                ", personPhone='" + personPhone + '\'' +
                ", personDni='" + personDni + '\'' +
                ", personDivision='" + personDivision + '\'' +
                ", personOrder=" + personOrder +
                ", personRol='" + personRol + '\'' +
                ", personCurrentFlag=" + personCurrentFlag +
                '}';
    }
}
