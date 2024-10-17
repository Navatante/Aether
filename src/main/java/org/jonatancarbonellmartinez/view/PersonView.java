package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.model.entities.Person;
import org.jonatancarbonellmartinez.presenter.PersonPresenter;

import javax.swing.*;
import java.util.List;

public class PersonView extends JFrame {

    // UI components
    public JMenu createPersonMenu;
    public JMenu updatePersonMenu;
    public JMenu deletePersonMenu;

    private PersonPresenter presenter;  // Now we use a presenter instead of a controller

    // Set the presenter
    public void setPresenter(PersonPresenter presenter) {
        this.presenter = presenter;
    }

    // Method to update the list of people in the view
    public void updatePersonList(List<Person> people) {
        // Update the UI with the new list of people
    }

    // Method to get the selected person from the UI
    public Person getSelectedPerson() {
        // Return the currently selected person in the UI
        return null;
    }

    // Method to get a new person from form input
    public Person getPersonFromForm() {
        Person newPerson = new Person();
        // Populate newPerson with form data
        // (e.g., retrieve text field values and populate the person object)
        return newPerson;
    }
}
