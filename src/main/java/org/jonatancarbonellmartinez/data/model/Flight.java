package org.jonatancarbonellmartinez.data.model;

public class Flight implements Entity {
    private Integer flightSk;
    private String dateTime;
    private Integer helo;
    private Integer event;
    private Integer personCta;
    private Double totalHours;

    @Override
    public int getSk() {
        return 0;
    }

    @Override
    public String toString() {
        return String.valueOf(flightSk);
    }

    // Getters and Setters
    public Integer getFlightSk() {
        return flightSk;
    }

    public void setFlightSk(Integer flightSk) {
        this.flightSk = flightSk;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getHelo() {
        return helo;
    }

    public void setHelo(Integer helo) {
        this.helo = helo;
    }

    public Integer getEvent() {
        return event;
    }

    public void setEvent(Integer event) {
        this.event = event;
    }

    public Integer getPersonCta() {
        return personCta;
    }

    public void setPersonCta(Integer personCta) {
        this.personCta = personCta;
    }

    public Double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Double totalHours) {
        this.totalHours = totalHours;
    }
}
