package org.jonatancarbonellmartinez.Observer;

import org.jonatancarbonellmartinez.model.entities.DimPerson;

public interface Observer<T> {
    void update(T view, String propertyName); // un Observer no suele tener mas que el metodo update().
}
