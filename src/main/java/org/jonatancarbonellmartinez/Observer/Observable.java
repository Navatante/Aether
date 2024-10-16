package org.jonatancarbonellmartinez.Observer;

import org.jonatancarbonellmartinez.model.entities.DimPerson;

public interface Observable {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(DimPerson person, String propertyName);
}