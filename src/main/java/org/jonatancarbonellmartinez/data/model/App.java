package org.jonatancarbonellmartinez.data.model;

public class App implements Entity{
    private int appSk;
    private int flightFk;
    private int personFk;
    private int appTypeFk;
    private int appQty;


    @Override
    public int getSk() {
        return appSk;
    }

    @Override
    public String toString() {
        return String.valueOf(appSk);
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

    public int getAppTypeFk() {
        return appTypeFk;
    }

    public void setAppTypeFk(int appTypeFk) {
        this.appTypeFk = appTypeFk;
    }

    public int getAppQty() {
        return appQty;
    }

    public void setAppQty(int appQty) {
        this.appQty = appQty;
    }
}