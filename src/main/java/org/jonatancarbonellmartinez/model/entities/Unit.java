package org.jonatancarbonellmartinez.model.entities;

public class Unit implements Entity {
    private int unitSk;
    private String unitShort;
    private String unitName;
    private String agencyShort;
    private String agencyName;
    private String authority;
    private String authorityShort;

    @Override
    public int getSk() {
        return unitSk;
    }

    @Override
    public String toString() {
        return String.valueOf(unitShort);
    }

    // Getters and Setters
    public void setUnitSk(int unitSk) {
        this.unitSk = unitSk;
    }

    public String getUnitShort() {
        return unitShort;
    }

    public void setUnitShort(String unitShort) {
        this.unitShort = unitShort;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getAgencyShort() {
        return agencyShort;
    }

    public void setAgencyShort(String agencyShort) {
        this.agencyShort = agencyShort;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getAuthorityShort() {
        return authorityShort;
    }

    public void setAuthorityShort(String authorityShort) {
        this.authorityShort = authorityShort;
    }
}


