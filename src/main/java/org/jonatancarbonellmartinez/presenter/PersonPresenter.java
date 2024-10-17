package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.model.entities.DimPerson;
import org.jonatancarbonellmartinez.model.dao.DimPersonDAO;
import org.jonatancarbonellmartinez.view.PersonView;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

// Presenter to handle interactions between the view and the model
public class PersonPresenter {
    private DimPersonDAO personDAO;
    private PersonView personView;

    public PersonPresenter(DimPersonDAO personDAO, PersonView personView) {
        this.personDAO = personDAO;
        this.personView = personView;

        // Set the presenter in the view
        this.personView.setPresenter(this);  // Refactored to setPresenter instead of setController

        // Load the initial list of people
        //loadPeople();
        //registerEventHandlers();
    }

    // Load initial data from the model and pass it to the view
    public void loadPeople() {
        List<DimPerson> people = personDAO.getAll();
        personView.updatePersonList(people);  // Passive update call to view
    }

    private void registerEventHandlers() {
        // Handle creating a person
        personView.createPersonMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createPerson();
            }
        });

        // Handle updating a person
        personView.updatePersonMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DimPerson selectedPerson = personView.getSelectedPerson();
                if (selectedPerson != null) {
                    updatePerson(selectedPerson);
                }
            }
        });

        // Handle deleting a person
        personView.deletePersonMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DimPerson selectedPerson = personView.getSelectedPerson();
                if (selectedPerson != null) {
                    deletePerson(selectedPerson);
                }
            }
        });
    }

    public void createPerson() {
        DimPerson newPerson = personView.getPersonFromForm();  // Refactored to get the person from form
        personDAO.create(newPerson);
        loadPeople();  // Reload the list after creating a new person
    }

    public void updatePerson(DimPerson person) {
        personDAO.update(person);
        loadPeople();  // Reload the list after updating a person
    }

    public void deletePerson(DimPerson person) {
        personDAO.delete(person.getPersonSk());
        loadPeople();  // Reload the list after deleting a person
    }
}
