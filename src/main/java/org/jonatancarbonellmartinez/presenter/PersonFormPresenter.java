package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.dao.PersonDAO;
import org.jonatancarbonellmartinez.model.entities.Person;
import org.jonatancarbonellmartinez.view.PersonFormView;

import javax.swing.*;

public class PersonFormPresenter {
    private final PersonDAO personDAO;  // DAO to handle database operations
    private final PersonFormView PersonFormView;

    public PersonFormPresenter(PersonFormView addPersonView, PersonDAO personDAO) {
        this.PersonFormView = addPersonView;
        this.personDAO = personDAO;
    }

    public void addPerson() {
        try {
            // Collect user input from the view
            String personNk = PersonFormView.getPersonNkField();
            String personRank = PersonFormView.getPersonRank();
            String personName = PersonFormView.getPersonName();
            String personLastName1 = PersonFormView.getPersonLastName1();
            String personLastName2 = PersonFormView.getPersonLastName2();
            String personPhone = PersonFormView.getPersonPhone();
            String personDni = PersonFormView.getPersonDni();
            String personDivision = PersonFormView.getPersonDivision();
            String personRol = PersonFormView.getPersonRol();
            int personOrder = PersonFormView.getPersonOrder();
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
            JOptionPane.showMessageDialog(PersonFormView, "Persona añadida correctamente.");

            // Clear input fields after successful addition
            PersonFormView.clearFields();

        } catch (DatabaseException ex) {
            // Handle any database-related exceptions
            JOptionPane.showMessageDialog(PersonFormView, "Error al añadir persona: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            // Handle any other exceptions
            JOptionPane.showMessageDialog(PersonFormView, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void editPerson() {
        try {
            // Collect user input from the view
            String personNk = PersonFormView.getPersonNkField();
            String personRank = PersonFormView.getPersonRank();
            String personName = PersonFormView.getPersonName();
            String personLastName1 = PersonFormView.getPersonLastName1();
            String personLastName2 = PersonFormView.getPersonLastName2();
            String personPhone = PersonFormView.getPersonPhone();
            String personDni = PersonFormView.getPersonDni();
            String personDivision = PersonFormView.getPersonDivision();
            String personRol = PersonFormView.getPersonRol();
            int personOrder = PersonFormView.getPersonState().equals("Activo") ? PersonFormView.getPersonOrder() : 99999;
            int personCurrentFlag = PersonFormView.getPersonState().equals("Activo") ? 1 : 0;

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

            // Call the Update method in the dao
            personDAO.update(person,Integer.parseInt(PersonFormView.getEditPersonIdField()));

            // Optionally, notify the user of success
            JOptionPane.showMessageDialog(PersonFormView, "Persona editada correctamente.");

            // Clear input fields after successful addition
            PersonFormView.clearFields();

        } catch (DatabaseException ex) {
            // Handle any database-related exceptions
            JOptionPane.showMessageDialog(PersonFormView, "Error al editar persona: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            // Handle any other exceptions
            ex.printStackTrace();
            JOptionPane.showMessageDialog(PersonFormView, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void getPerson(int personId) {
        try {
            Person person = personDAO.read(personId);
            if (person != null) {
                // Update the view with person data
                updateEditPersonFormView(person);
            } else {
                JOptionPane.showMessageDialog(PersonFormView, "No se encontró ninguna persona con ese ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (DatabaseException ex) {
            JOptionPane.showMessageDialog(PersonFormView, "Error al obtener la persona: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(PersonFormView, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateEditPersonFormView(Person person) {
        // Fill the view components with person data
        PersonFormView.setPersonNk(person.getPersonNk());
        PersonFormView.setPersonRank(person.getPersonRank());
        PersonFormView.setPersonName(person.getPersonName());
        PersonFormView.setPersonLastName1(person.getPersonLastName1());
        PersonFormView.setPersonLastName2(person.getPersonLastName2());
        PersonFormView.setPersonPhone(person.getPersonPhone());
        PersonFormView.setPersonDni(person.getPersonDni().substring(0, person.getPersonDni().length() - 1)); // as the field only permits numbers, i substract the letter
        PersonFormView.setPersonDivision(person.getPersonDivision());
        PersonFormView.setPersonRol(person.getPersonRol());
        PersonFormView.setPersonOrder(person.getPersonOrder());
        PersonFormView.setPersonStateBox(person.getPersonCurrentFlag().equals(1) ? "Activo": "Inactivo");
    }
}
