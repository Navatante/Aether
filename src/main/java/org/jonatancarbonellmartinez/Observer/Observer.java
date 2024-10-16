package org.jonatancarbonellmartinez.Observer;

import org.jonatancarbonellmartinez.model.entities.DimPerson;

public interface Observer {
    void update(DimPerson person, String propertyName); // un Observer no suele tener mas que el metodo update().
}
