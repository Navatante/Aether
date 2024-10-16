package org.jonatancarbonellmartinez.model.entities;

import org.jonatancarbonellmartinez.Observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class DimPerson {
    private Integer personSk;
    private String personNk;
    private int personRankNumber;
    private String personRank;
    private String personName;
    private String personLastName1;
    private String personLastName2;
    private String personDni;
    private String personPhone;
    private String personDivision;
    private int personCurrentFlag;

    // List to hold observers
    private List<Observer> observers = new ArrayList<>();

    // Getters y Setters

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers(String propertyName) {
        for (Observer observer : observers) {
            observer.update(this, propertyName);
        }
    }

    // Method to update a property and notify observers
    public void setPersonName(String personName) {
        this.personName = personName;
        notifyObservers("personName");
    }

    public int getPersonSk() {
        return personSk;
    }

    public void setPersonSk(int personSk) {
        this.personSk = personSk;
    }

    public String getPersonNk() {
        return personNk;
    }

    public void setPersonNk(String personNk) {
        this.personNk = personNk;
    }

    public int getPersonRankNumber() {
        return personRankNumber;
    }

    public void setPersonRankNumber(int personRankNumber) {
        this.personRankNumber = personRankNumber;
    }

    public String getPersonRank() {
        return personRank;
    }

    public void setPersonRank(String personRank) {
        this.personRank = personRank;
    }

    public String getPersonName() {
        return personName;
    }

    public String getPersonLastName1() {
        return personLastName1;
    }

    public void setPersonLastName1(String personLastName1) {
        this.personLastName1 = personLastName1;
    }

    public String getPersonLastName2() {
        return personLastName2;
    }

    public void setPersonLastName2(String personLastName2) {
        this.personLastName2 = personLastName2;
    }

    public String getPersonDni() {
        return personDni;
    }

    public void setPersonDni(String personDni) {
        if (personDni == null || personDni.isEmpty()) {
            throw new IllegalArgumentException("DNI cannot be null or empty");
        }
        this.personDni = personDni;
        notifyObservers("personDni");
    }

    public String getPersonPhone() {
        return personPhone;
    }

    public void setPersonPhone(String personPhone) {
        this.personPhone = personPhone;
    }

    public String getPersonDivision() {
        return personDivision;
    }

    public void setPersonDivision(String personDivision) {
        this.personDivision = personDivision;
    }

    public int getPersonCurrentFlag() {
        return personCurrentFlag;
    }

    public void setPersonCurrentFlag(int personCurrentFlag) {
        this.personCurrentFlag = personCurrentFlag;
    }

    public List<Observer> getObservers() {
        return observers;
    }

    public void setObservers(List<Observer> observers) {
        this.observers = observers;
    }
}
