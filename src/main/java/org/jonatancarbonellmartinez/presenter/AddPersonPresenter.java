package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.dao.PersonDAO;
import org.jonatancarbonellmartinez.model.entities.Person;
import org.jonatancarbonellmartinez.view.AddPersonView;
import javax.swing.*;
import java.util.List;

public class AddPersonPresenter {
    private final PersonDAO personDAO;  // DAO to handle database operations
    private final AddPersonView addPersonView;

    // Constructor to pass DAO
    public AddPersonPresenter(AddPersonView addPersonView,PersonDAO personDAO) {
        this.addPersonView = addPersonView;
        this.personDAO = personDAO;
    }
}
