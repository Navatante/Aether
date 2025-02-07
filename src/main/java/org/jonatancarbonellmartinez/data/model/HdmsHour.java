package org.jonatancarbonellmartinez.data.model;

public class HdmsHour implements Entity {
    private int hdmsHourSk;
    private int flightFk;
    private int personFk;
    private double hdmsHourQty;

    @Override
    public int getSk() {
        return hdmsHourSk;
    }

    @Override
    public String toString() {
        return String.valueOf(hdmsHourSk);
    }

    //Getters and Setters
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

    public double getHdmsHourQty() {
        return hdmsHourQty;
    }

    public void setHdmsHourQty(double hdmsHourQty) {
        this.hdmsHourQty = hdmsHourQty;
    }
}
