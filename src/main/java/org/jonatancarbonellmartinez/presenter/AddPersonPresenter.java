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

    // Method to load and display all persons in the view
    public void loadAllPersons() {
        try {
            List<Person> persons = personDAO.getAll();
            if (addPersonView != null) {
                addPersonView.displayPersons(persons);  // Display persons in the table
            }
        } catch (DatabaseException e) {
            // Handle the exception appropriately, perhaps showing a dialog
            JOptionPane.showMessageDialog(addPersonView, "Error loading persons: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
