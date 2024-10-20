package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.dao.PersonDAO;
import org.jonatancarbonellmartinez.model.entities.Person;
import org.jonatancarbonellmartinez.view.PersonCardView;

import javax.swing.*;
import java.util.List;

public class PersonCardPresenter {
    private final PersonDAO personDAO;  // DAO to handle database operations
    private final PersonCardView personCardView;

    // Constructor to pass DAO
    public PersonCardPresenter(PersonCardView personCardView, PersonDAO personDAO) {
        this.personCardView = personCardView;
        this.personDAO = personDAO;
    }

    // Method to load and display all persons in the view
    public void loadAllPersons() {
        try {
            List<Person> persons = personDAO.getAll();
            if (personCardView != null) {
                addPersonsToTableModel(persons);  // Display persons in the table
            }
        } catch (DatabaseException e) {
            // Handle the exception appropriately, perhaps showing a dialog
            JOptionPane.showMessageDialog(personCardView, "Error loading persons: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addPersonsToTableModel(List<Person> persons) {
        // Clear existing rows
        personCardView.getTableModel().setRowCount(0);

        // Add persons to the table model
        for (Person person : persons) {
            Object[] rowData = {
                    person.getPersonSk(),
                    person.getPersonNk(),
                    person.getPersonRank(),
                    person.getPersonName(),
                    person.getPersonLastName1(),
                    person.getPersonLastName2(),
                    person.getPersonPhone(),
                    person.getPersonDivision(),
                    person.getPersonOrder(),
                    person.getPersonRol(),
                    person.getPersonCurrentFlag() == 1 ? "Activo" : "Inactivo"
            };
            personCardView.getTableModel().addRow(rowData);
        }
    }
}
