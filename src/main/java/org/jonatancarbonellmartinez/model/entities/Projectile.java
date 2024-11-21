package org.jonatancarbonellmartinez.model.entities;

public class Projectile implements Entity{
    private int projectileSk;
    private int flightFk;
    private int personFk;
    private int projectileTypeFk;
    private int projectileQty;

    @Override
    public int getSk() {
        return projectileSk;
    }

    @Override
    public String toString() {
        return String.valueOf(projectileSk);
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

    public int getProjectileTypeFk() {
        return projectileTypeFk;
    }

    public void setProjectileTypeFk(int projectileTypeFk) {
        this.projectileTypeFk = projectileTypeFk;
    }

    public int getProjectileQty() {
        return projectileQty;
    }

    public void setProjectileQty(int projectileQty) {
        this.projectileQty = projectileQty;
    }
}
