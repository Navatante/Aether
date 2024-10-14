package org.jonatancarbonellmartinez.command;

import org.jonatancarbonellmartinez.model.DimPerson;
import org.jonatancarbonellmartinez.model.dao.DimPersonDAO;

public class CreatePersonCommand implements Command{
    private DimPersonDAO personDAO;
    private DimPerson person;

    public CreatePersonCommand(DimPersonDAO personDAO, DimPerson person) {
        this.personDAO = personDAO;
        this.person = person;
    }

    @Override
    public void execute() {
        personDAO.create(person);
    }
}
