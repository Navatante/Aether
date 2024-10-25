package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.dao.PersonDAO;
import org.jonatancarbonellmartinez.model.entities.Person;
import org.jonatancarbonellmartinez.view.PersonFormView;

import javax.swing.*;

public class PersonFormPresenter {
    private final PersonDAO personDAO;
    private final PersonFormView personFormView;

    public PersonFormPresenter(PersonFormView addPersonView, PersonDAO personDAO) {
        this.personFormView = addPersonView;
        this.personDAO = personDAO;
    }

    public void addPerson() {
        try {
            Person person = collectPersonData();
            person.setPersonCurrentFlag(1);
            personDAO.create(person);
            showMessage("Persona añadida correctamente.");
            personFormView.clearFields();

        } catch (DatabaseException ex) {
            showError("Error al añadir persona: ", ex);
        } catch (Exception ex) {
            handleUnexpectedError(ex);
        }
    }

    public void editPerson() {
        try {
            Person person = collectPersonData();
            int personId = Integer.parseInt(personFormView.getEditPersonIdField());
            int currentFlag = personFormView.getPersonState().equals("Activo") ? 1 : 0;
            person.setPersonCurrentFlag(currentFlag);
            person.setPersonOrder(calculatePersonOrder());
            personDAO.update(person, personId);
            showMessage("Persona editada correctamente.");
            personFormView.clearFields();

        } catch (DatabaseException e) {
            showError("Error al editar persona: ", e);
        } catch (Exception e) {
            handleUnexpectedError(e);
        }
    }

    public void getPerson(int personId) {
        try {
            Person person = personDAO.read(personId);
            if (person != null) {
                populatePersonForm(person);
            } else {
                showMessage("No se encontró ninguna persona con ese ID.");
            }
        } catch (DatabaseException e) {
            showError("Error al obtener la persona: ", e);
        } catch (Exception e) {
            handleUnexpectedError(e);
        }
    }

    private Person collectPersonData() {
        Person person = new Person();
        person.setPersonNk(personFormView.getPersonNkField());
        person.setPersonRank(personFormView.getPersonRank());
        person.setPersonName(personFormView.getPersonName());
        person.setPersonLastName1(personFormView.getPersonLastName1());
        person.setPersonLastName2(personFormView.getPersonLastName2());
        person.setPersonPhone(personFormView.getPersonPhone());
        person.setPersonDni(personFormView.getPersonDni());
        person.setPersonDivision(personFormView.getPersonDivision());
        person.setPersonRol(personFormView.getPersonRol());
        person.setPersonOrder(personFormView.getPersonOrder());
        return person;
    }

    private void populatePersonForm(Person person) {
        personFormView.setPersonNk(person.getPersonNk());
        personFormView.setPersonRank(person.getPersonRank());
        personFormView.setPersonName(person.getPersonName());
        personFormView.setPersonLastName1(person.getPersonLastName1());
        personFormView.setPersonLastName2(person.getPersonLastName2());
        personFormView.setPersonPhone(person.getPersonPhone());
        personFormView.setPersonDni(stripDniLetter(person.getPersonDni()));
        personFormView.setPersonDivision(person.getPersonDivision());
        personFormView.setPersonRol(person.getPersonRol());
        personFormView.setPersonOrder(person.getPersonOrder());
        personFormView.setPersonStateBox(person.getPersonCurrentFlag() == 1 ? "Activo" : "Inactivo");
    }

    private int calculatePersonOrder() {
        return personFormView.getPersonState().equals("Activo") ? personFormView.getPersonOrder() : 99999;
    }

    private String stripDniLetter(String dni) {
        return dni != null && dni.length() > 0 ? dni.substring(0, dni.length() - 1) : "";
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(personFormView, message);
    }

    private void showError(String prefix, Exception e) {
        JOptionPane.showMessageDialog(personFormView, prefix + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void handleUnexpectedError(Exception e) {
        e.printStackTrace();
        showError("Error inesperado: ", e);
    }

}
