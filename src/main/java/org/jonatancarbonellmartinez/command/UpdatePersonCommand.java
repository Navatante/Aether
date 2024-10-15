package org.jonatancarbonellmartinez.command;

import org.jonatancarbonellmartinez.model.entities.DimPerson;
import org.jonatancarbonellmartinez.model.dao.DimPersonDAO;

public class UpdatePersonCommand implements Command {
    private DimPersonDAO personDAO;
    private DimPerson person;

    public UpdatePersonCommand(DimPersonDAO personDAO, DimPerson person) {
        this.personDAO = personDAO;
        this.person = person;
    }

    @Override
    public void execute() {
        personDAO.update(person);
    }
}
