package org.jonatancarbonellmartinez.model.entities;

public class SessionCrewCount implements Entity{
    private int sessionSk;
    private int flightFk;
    private int personFk;
    private int sessionFk;

    @Override
    public int getSk() {
        return sessionSk;
    }

    @Override
    public String toString() {
        return String.valueOf(sessionSk);
    }

    // Getters ans Setters
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

    public int getSessionFk() {
        return sessionFk;
    }

    public void setSessionFk(int sessionFk) {
        this.sessionFk = sessionFk;
    }
}



