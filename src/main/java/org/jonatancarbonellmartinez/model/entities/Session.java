package org.jonatancarbonellmartinez.model.entities;

public class Session implements Entity {
    private int sessionSk;
    private String sessionName;
    private String sessionDescription;
    private String sessionBlock;
    private String sessionPlan;
    private String sessionTv;
    private double crpValue;
    private int expiration;

    @Override
    public int getSk() {
        return sessionSk;
    }

    @Override
    public String toString() {
        return sessionName;
    }

    // Getters and Setters
    public void setSessionSk(int sessionSk) {
        this.sessionSk = sessionSk;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getSessionDescription() {
        return sessionDescription;
    }

    public void setSessionDescription(String sessionDescription) {
        this.sessionDescription = sessionDescription;
    }

    public String getSessionBlock() {
        return sessionBlock;
    }

    public void setSessionBlock(String sessionBlock) {
        this.sessionBlock = sessionBlock;
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

    public String getSessionTv() {
        return sessionTv;
    }

    public void setSessionTv(String sessionTv) {
        this.sessionTv = sessionTv;
    }

    public String getSessionPlan() {
        return sessionPlan;
    }

    public void setSessionPlan(String sessionPlan) {
        this.sessionPlan = sessionPlan;
    }
}




