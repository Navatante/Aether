package org.jonatancarbonellmartinez.model.entities;

public class Session implements Entity {
    private int sessionSk;
    private String sessionNk;
    private String sessionDv;
    private String sessionName;
    private String sessionType;
    private String sessionSubType;
    private String capba;
    private double crpValue;
    private int expiration;

    @Override
    public int getSk() {
        return sessionSk;
    }

    @Override
    public String toString() {
        return String.valueOf(sessionSk);
    }

    // Getters and Setters
    public String getSessionNk() {
        return sessionNk;
    }

    public void setSessionNk(String sessionNk) {
        this.sessionNk = sessionNk;
    }

    public String getSessionDv() {
        return sessionDv;
    }

    public void setSessionDv(String sessionDv) {
        this.sessionDv = sessionDv;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    public String getSessionSubType() {
        return sessionSubType;
    }

    public void setSessionSubType(String sessionSubType) {
        this.sessionSubType = sessionSubType;
    }

    public String getCapba() {
        return capba;
    }

    public void setCapba(String capba) {
        this.capba = capba;
    }

    public double getCrpValue() {
        return crpValue;
    }

    public void setCrpValue(double crpValue) {
        this.crpValue = crpValue;
    }

    public int getExpiration() {
        return expiration;
    }

    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }
}




