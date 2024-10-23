package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.dao.PersonDAO;
import org.jonatancarbonellmartinez.model.entities.Person;
import org.jonatancarbonellmartinez.view.PersonFormView;

import javax.swing.*;

public class PersonFormPresenter {
    private final PersonDAO personDAO;  // DAO to handle database operations
    private final PersonFormView addPersonView;

    public PersonFormPresenter(PersonFormView addPersonView, PersonDAO personDAO) {
        this.addPersonView = addPersonView;
        this.personDAO = personDAO;
    }

    public void addPerson() {
        try {
            // Collect user input from the view
            String personNk = addPersonView.getPersonNkField();
            String personRank = addPersonView.getPersonRank();
            String personName = addPersonView.getPersonName();
            String personLastName1 = addPersonView.getPersonLastName1();
            String personLastName2 = addPersonView.getPersonLastName2();
            String personPhone = addPersonView.getPersonPhone();
            String personDni = addPersonView.getPersonDni();
            String personDivision = addPersonView.getPersonDivision();
            String personRol = addPersonView.getPersonRol();
            int personOrder = addPersonView.getPersonOrder();
            int personCurrentFlag = 1;

            // Create a new Person object
            Person person = new Person();
            person.setPersonNk(personNk);
            person.setPersonRank(personRank);
            person.setPersonName(personName);
            person.setPersonLastName1(personLastName1);
            person.setPersonLastName2(personLastName2);
            person.setPersonPhone(personPhone);
            person.setPersonDni(personDni);
            person.setPersonDivision(personDivision);
            person.setPersonOrder(personOrder);
            person.setPersonRol(personRol);
            person.setPersonCurrentFlag(personCurrentFlag);

            // Call the create method in the DAO
            personDAO.create(person);

            // Optionally, notify the user of success
            JOptionPane.showMessageDialog(addPersonView, "Persona añadida correctamente.");

            // Clear input fields after successful addition
            addPersonView.clearFields();

        } catch (DatabaseException ex) {
            // Handle any database-related exceptions
            JOptionPane.showMessageDialog(addPersonView, "Error al añadir persona: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            // Handle any other exceptions
            JOptionPane.showMessageDialog(addPersonView, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
