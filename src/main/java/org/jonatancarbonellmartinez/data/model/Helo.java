package org.jonatancarbonellmartinez.data.model;

public class Helo implements Entity {
    private Integer heloSk;
    private String heloPlateNk;
    private String heloName;
    private String heloNumber;


    public Integer getHeloSk() {
        return heloSk;
    }

    public void setHeloSk(Integer heloSk) {
        this.heloSk = heloSk;
    }

    public String getHeloPlateNk() {
        return heloPlateNk;
    }

    public void setHeloPlateNk(String heloPlateNk) {
        this.heloPlateNk = heloPlateNk;
    }

    public String getHeloName() {
        return heloName;
    }

    public void setHeloName(String heloName) {
        this.heloName = heloName;
    }

    public String getHeloNumber() {
        return heloNumber;
    }

    public void setHeloNumber(String heloNumber) {
        this.heloNumber = heloNumber;
    }

    @Override
    public String toString() {
        return heloNumber;
    }

    @Override
    public int getSk() {
        return heloSk;
    }
}
