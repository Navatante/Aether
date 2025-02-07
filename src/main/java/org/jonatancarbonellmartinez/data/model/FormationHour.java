package org.jonatancarbonellmartinez.data.model;



public class FormationHour implements Entity {
    private int formationHourSk;
    private int flightFk;
    private int personFk;
    private int perdiodFk;
    private double formationHourQty;

    @Override
    public int getSk() {
        return 0;
    }

    @Override
    public String toString() {
        return String.valueOf(formationHourSk);
    }

    // Getters and Setters
    public int getFormationHourSk() {
        return formationHourSk;
    }

    public void setFormationHourSk(int formationHourSk) {
        this.formationHourSk = formationHourSk;
    }

    public int getFlightFk() {
        return flightFk;
    }

    public void setFlightFk(int flightFk) {
        this.flightFk = flightFk;
    }

    public int getPersonFk() {
        return personFk;
    }

    public void setPersonFk(int personFk) {
        this.personFk = personFk;
    }

    public int getPerdiodFk() {
        return perdiodFk;
    }

    public void setPerdiodFk(int perdiodFk) {
        this.perdiodFk = perdiodFk;
    }

    public double getFormationHourQty() {
        return formationHourQty;
    }

    public void setFormationHourQty(double formationHourQty) {
        this.formationHourQty = formationHourQty;
    }
}