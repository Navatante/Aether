package org.jonatancarbonellmartinez.controller;

import org.jonatancarbonellmartinez.command.*;
import org.jonatancarbonellmartinez.model.entities.DimPerson;
import org.jonatancarbonellmartinez.model.dao.DimPersonDAO;

// Controller to handle interactions between the view and the model
public class PersonController {
    private DimPersonDAO personDAO;

    public PersonController(DimPersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public void createPerson(DimPerson person) {
        CreatePersonCommand command = new CreatePersonCommand(personDAO, person);
        command.execute();
    }

    public void updatePerson(DimPerson person) {
        UpdatePersonCommand command = new UpdatePersonCommand(personDAO, person);
        command.execute();
    }

    public void deletePerson(int personSk) {
        DeletePersonCommand command = new DeletePersonCommand(personDAO, personSk);
        command.execute();
    }

    public DimPerson getPerson(int personSk) {
        return personDAO.read(personSk);
    }
}
