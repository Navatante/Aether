package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.model.entities.Person;
import org.jonatancarbonellmartinez.model.dao.PersonDAO;
import org.jonatancarbonellmartinez.view.PersonView;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

// Presenter to handle interactions between the view and the model
public class PersonPresenter {
    private PersonDAO personDAO;
    private PersonView personView;

    public PersonPresenter(PersonDAO personDAO, PersonView personView) {
        this.personDAO = personDAO;
        this.personView = personView;

        // Set the presenter in the view
        this.personView.setPresenter(this); // We establish a relationship where the view knows about the presenter. This is essential because the view will need to call presenter methods in response to user actions (like button clicks)

        //initialize();
    }

    private void initialize() { // This allows the presenter to immediately prepare the view with data and set up interactions.
        loadPeople();
        registerEventHandlers();
    }

    // Load initial data from the model and pass it to the view
    public void loadPeople() {
        List<Person> people = personDAO.getAll();
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
                Person selectedPerson = personView.getSelectedPerson();
                if (selectedPerson != null) {
                    updatePerson(selectedPerson);
                }
            }
        });

        // Handle deleting a person
        personView.deletePersonMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Person selectedPerson = personView.getSelectedPerson();
                if (selectedPerson != null) {
                    deletePerson(selectedPerson);
                }
            }
        });
    }

    public void createPerson() {
        Person newPerson = personView.getPersonFromForm();  // Refactored to get the person from form
        personDAO.create(newPerson);
        loadPeople();  // Reload the list after creating a new person
    }

    public void updatePerson(Person person) {
        personDAO.update(person);
        loadPeople();  // Reload the list after updating a person
    }

    public void deletePerson(Person person) {
        personDAO.delete(person.getPersonSk());
        loadPeople();  // Reload the list after deleting a person
    }
}
