package org.jonatancarbonellmartinez.command;

import org.jonatancarbonellmartinez.model.dao.DimPersonDAO;

public class DeletePersonCommand implements Command {
    private DimPersonDAO personDAO;
    private int personSk;

    public DeletePersonCommand(DimPersonDAO personDAO, int personSk) {
        this.personDAO = personDAO;
        this.personSk = personSk;
    }

    @Override
    public void execute() {
        personDAO.delete(personSk);
    }
}
