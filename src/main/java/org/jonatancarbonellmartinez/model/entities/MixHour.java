package org.jonatancarbonellmartinez.model.entities;

public class MixHour implements Entity {
    private int mixHourSk;
    private int flightFk;
    private int personFk;
    private double iftQty;
    private double instructorQty;
    private double hdmsQty;

    @Override
    public int getSk() {
        return mixHourSk;
    }

    @Override
    public String toString() {
        return String.valueOf(mixHourSk);
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

    public double getIftQty() {
        return iftQty;
    }

    public void setIftQty(double iftQty) {
        this.iftQty = iftQty;
    }

    public double getInstructorQty() {
        return instructorQty;
    }

    public void setInstructorQty(double instructorQty) {
        this.instructorQty = instructorQty;
    }

    public double getHdmsQty() {
        return hdmsQty;
    }

    public void setHdmsQty(double hdmsQty) {
        this.hdmsQty = hdmsQty;
    }
}
