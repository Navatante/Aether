package org.jonatancarbonellmartinez.data.model;

/**
 * JavaFX Properties NO deben usarse en esta capa.
 */

// Data Layer (how it's stored in database)
public class PersonEntity {
    private Integer personSk;
    private String personNk;
    private String personRank;
    private String cuerpo;
    private String especialidad;
    private String personName;
    private String personLastName1;
    private String personLastName2;
    private String personPhone;
    private String personDni;
    private String personDivision;
    private String personRole;
    private Long antiguedadEmpleo;
    private Long fechaEmbarque;
    private Integer personOrder;
    private Integer personCurrentFlag;

    // Constructor por defecto necesario para JDBC
    public PersonEntity() {}

    // Getters y Setters - objeto mutable para la capa de datos porque necesita ser modificable para JDBC
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

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Long getAntiguedadEmpleo() {
        return antiguedadEmpleo;
    }

    public void setAntiguedadEmpleo(Long antiguedadEmpleo) {
        this.antiguedadEmpleo = antiguedadEmpleo;
    }

    public Long getFechaEmbarque() {
        return fechaEmbarque;
    }

    public void setFechaEmbarque(Long fechaEmbarque) {
        this.fechaEmbarque = fechaEmbarque;
    }

    @Override
    public String toString() {
        return "PersonEntity{" +
                "personSk=" + personSk +
                ", personNk='" + personNk + '\'' +
                ", personRank='" + personRank + '\'' +
                ", cuerpo='" + cuerpo + '\'' +
                ", especialidad='" + especialidad + '\'' +
                ", personName='" + personName + '\'' +
                ", personLastName1='" + personLastName1 + '\'' +
                ", personLastName2='" + personLastName2 + '\'' +
                ", personPhone='" + personPhone + '\'' +
                ", personDni='" + personDni + '\'' +
                ", personDivision='" + personDivision + '\'' +
                ", personRole='" + personRole + '\'' +
                ", antiguedadEmpleo=" + antiguedadEmpleo +
                ", fechaEmbarque=" + fechaEmbarque +
                ", personOrder=" + personOrder +
                ", personCurrentFlag=" + personCurrentFlag +
                '}';
    }
}
