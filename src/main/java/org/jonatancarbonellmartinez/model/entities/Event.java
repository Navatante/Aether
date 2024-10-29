package org.jonatancarbonellmartinez.model.entities;

public class Event implements Entity {
    private Integer eventSk;
    private String eventName;
    private String eventPlace;

    public Integer getEventSk() {
        return eventSk;
    }

    public void setEventSk(Integer eventSk) {
        this.eventSk = eventSk;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventPlace() {
        return eventPlace;
    }

    public void setEventPlace(String eventPlace) {
        this.eventPlace = eventPlace;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventSk=" + eventSk +
                ", eventName='" + eventName + '\'' +
                ", eventPlace='" + eventPlace + '\'' +
                '}';
    }
}
