package org.jonatancarbonellmartinez.data.model;

public class Passenger implements Entity{
    private int passengerSk;
    private int flightFk;
    private int passengerTypeFk;
    private int passengerQty;
    private String route;

    @Override
    public int getSk() {
        return passengerSk;
    }

    @Override
    public String toString() {
        return String.valueOf(passengerSk);
    }

    // Getters and Setters
    public int getFlightFk() {
        return flightFk;
    }

    public void setFlightFk(int flightFk) {
        this.flightFk = flightFk;
    }

    public int getPassengerTypeFk() {
        return passengerTypeFk;
    }

    public void setPassengerTypeFk(int passengerTypeFk) {
        this.passengerTypeFk = passengerTypeFk;
    }

    public int getPassengerQty() {
        return passengerQty;
    }

    public void setPassengerQty(int passengerQty) {
        this.passengerQty = passengerQty;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
}