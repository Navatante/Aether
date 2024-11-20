package org.jonatancarbonellmartinez.model.entities;

public class Landing implements Entity{
    private int landingSk;
    private int flightFk;
    private int personFk;
    private int placeFk;
    private int periodFk;
    private int landingQty;

    @Override
    public int getSk() {
        return 0;
    }

    @Override
    public String toString() {
        return String.valueOf(landingSk);
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

    public int getPlaceFk() {
        return placeFk;
    }

    public void setPlaceFk(int placeFk) {
        this.placeFk = placeFk;
    }

    public int getPeriodFk() {
        return periodFk;
    }

    public void setPeriodFk(int periodFk) {
        this.periodFk = periodFk;
    }

    public int getLandingQty() {
        return landingQty;
    }

    public void setLandingQty(int landingQty) {
        this.landingQty = landingQty;
    }
}