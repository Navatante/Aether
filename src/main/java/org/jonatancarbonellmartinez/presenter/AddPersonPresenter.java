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
                addPersonsToTableModel(persons);  // Display persons in the table
            }
        } catch (DatabaseException e) {
            // Handle the exception appropriately, perhaps showing a dialog
            JOptionPane.showMessageDialog(addPersonView, "Error loading persons: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addPersonsToTableModel(List<Person> persons) {
        // Clear existing rows
        addPersonView.getTableModel().setRowCount(0);

        // Add persons to the table model
        for (Person person : persons) {
            Object[] rowData = {
                    person.getPersonSk(),
                    person.getPersonNk(),
                    person.getPersonRankNumber(),
                    person.getPersonRank(),
                    person.getPersonName(),
                    person.getPersonLastName1(),
                    person.getPersonLastName2(),
                    person.getPersonDni(),
                    person.getPersonPhone(),
                    person.getPersonDivision(),
                    person.getPersonCurrentFlag()
            };
            addPersonView.getTableModel().addRow(rowData);
        }
    }
}
