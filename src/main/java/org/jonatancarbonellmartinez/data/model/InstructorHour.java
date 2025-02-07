package org.jonatancarbonellmartinez.data.model;

public class InstructorHour implements Entity {
    private int instructorHourSk;
    private int flightFk;
    private int personFk;
    private double instructorHourQty;

    @Override
    public int getSk() {
        return 0;
    }

    @Override
    public String toString() {
        return String.valueOf(instructorHourSk);
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

    public double getInstructorHourQty() {
        return instructorHourQty;
    }

    public void setInstructorHourQty(double instructorHourQty) {
        this.instructorHourQty = instructorHourQty;
    }
}
