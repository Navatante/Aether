package org.jonatancarbonellmartinez.model.entities;

public class PersonHour implements Entity{
    private int personHourSk;
    private int flightFk;
    private int personFk;
    private int periodFk;
    private double hourQty;

    // Getters and setters
    @Override
    public int getSk() {
        return personHourSk;
    }

    @Override
    public String toString() {
        return String.valueOf(personHourSk);
    }

    public int getPersonHourSk() {
        return personHourSk;
    }

    public void setPersonHourSk(int personHourSk) {
        this.personHourSk = personHourSk;
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

    public int getPeriodFk() {
        return periodFk;
    }

    public void setPeriodFk(int periodFk) {
        this.periodFk = periodFk;
    }

    public double getHourQty() {
        return hourQty;
    }

    public void setHourQty(double hourQty) {
        this.hourQty = hourQty;
    }
}
