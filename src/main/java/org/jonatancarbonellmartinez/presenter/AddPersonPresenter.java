package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.dao.PersonDAO;
import org.jonatancarbonellmartinez.model.entities.Person;
import org.jonatancarbonellmartinez.view.AddPersonView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPersonPresenter {
    private final PersonDAO personDAO;  // DAO to handle database operations
    private final AddPersonView addPersonView;

    public AddPersonPresenter(AddPersonView addPersonView, PersonDAO personDAO) {
        this.addPersonView = addPersonView;
        this.personDAO = personDAO;
    }

    public void addPerson() {
        try {
            // Collect user input from the view
            String personNk = addPersonView.getPersonNk();
            String personRank = addPersonView.getPersonRank();
            String personName = addPersonView.getPersonName();
            String personLastName1 = addPersonView.getPersonLastName1();
            String personLastName2 = addPersonView.getPersonLastName2();
            String personPhone = addPersonView.getPersonPhone();
            String personDivision = addPersonView.getPersonDivision();
            int personOrder = addPersonView.getPersonOrder();
            String personRol = addPersonView.getPersonRol();
            int personCurrentFlag = addPersonView.getPersonCurrentFlag();

            // Create a new Person object
            Person person = new Person();
            person.setPersonNk(personNk);
            person.setPersonRank(personRank);
            person.setPersonName(personName);
            person.setPersonLastName1(personLastName1);
            person.setPersonLastName2(personLastName2);
            person.setPersonPhone(personPhone);
            person.setPersonDivision(personDivision);
            person.setPersonOrder(personOrder);
            person.setPersonRol(personRol);
            person.setPersonCurrentFlag(personCurrentFlag);

            // Call the create method in the DAO
            personDAO.create(person);

            // Optionally, notify the user of success
            JOptionPane.showMessageDialog(addPersonView, "Person added successfully!");

            // Clear input fields after successful addition
            addPersonView.clearFields();

        } catch (DatabaseException ex) {
            // Handle any database-related exceptions
            JOptionPane.showMessageDialog(addPersonView, "Error adding person: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            // Handle any other exceptions
            JOptionPane.showMessageDialog(addPersonView, "Unexpected error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
