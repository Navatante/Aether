package org.jonatancarbonellmartinez.model.entities;

public class CupoHour implements Entity {
    private int cupoHourSk;
    private int flightFk;
    private int unitFk;
    private double cupoHourQty;

    @Override
    public int getSk() {
        return cupoHourSk;
    }
    @Override
    public String toString() {
        return String.valueOf(cupoHourSk);
    }

    // Getters and Setters
    public int getFlightFk() {
        return flightFk;
    }

    public void setFlightFk(int flightFk) {
        this.flightFk = flightFk;
    }

    public int getUnitFk() {
        return unitFk;
    }

    public void setUnitFk(int unitFk) {
        this.unitFk = unitFk;
    }

    public double getCupoHourQty() {
        return cupoHourQty;
    }

    public void setCupoHourQty(double cupoHourQty) {
        this.cupoHourQty = cupoHourQty;
    }
}
