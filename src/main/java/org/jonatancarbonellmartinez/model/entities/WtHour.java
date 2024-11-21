package org.jonatancarbonellmartinez.model.entities;

public class WtHour implements Entity {
    private int wtHourSk;
    private int flightFk;
    private int personFk;
    private double wtHourQty;

    @Override
    public int getSk() {
        return wtHourSk;
    }
    @Override
    public String toString() {
        return String.valueOf(wtHourSk);
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

    public double getWtHourQty() {
        return wtHourQty;
    }

    public void setWtHourQty(double wtHourQty) {
        this.wtHourQty = wtHourQty;
    }
}
