package org.jonatancarbonellmartinez.model.entities;

public class IftHour implements Entity {
    private int iftHourSk;
    private int flightFk;
    private int personFk;
    private double iftHourQty;

    @Override
    public int getSk() {
        return iftHourSk;
    }

    @Override
    public String toString() {
        return String.valueOf(iftHourSk);
    }

    // Getters and Setters
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

    public double getIftHourQty() {
        return iftHourQty;
    }

    public void setIftHourQty(double iftHourQty) {
        this.iftHourQty = iftHourQty;
    }
}
