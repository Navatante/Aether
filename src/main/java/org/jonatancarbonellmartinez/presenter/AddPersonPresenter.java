package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.model.dao.PersonDAO;
import org.jonatancarbonellmartinez.model.dao.exceptions.DAOException;
import org.jonatancarbonellmartinez.model.entities.Person;
import org.jonatancarbonellmartinez.view.AddPersonView;
import org.jonatancarbonellmartinez.view.MainView;

import javax.swing.*;
import java.util.List;

public class AddPersonPresenter implements Presenter<AddPersonView> {
    private final PersonDAO personDAO;  // DAO to handle database operations
    private AddPersonView personView;   // View associated with this presenter, initially null
    private MainView mainView;          // Reference to MainView

    // Constructor to pass DAO
    public AddPersonPresenter(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    // Set the view and bind actions (like loading persons)
    @Override
    public void setView(AddPersonView view) {
        this.personView = view;
        personView.setPresenter(this);  // Link view to presenter

        // Always pass the mainView reference if it's already available
        if (mainView != null) {
            personView.setMainViewReference(mainView);
        }
    }
    @Override
    public void setMainViewReference(MainView mainView) {
        this.mainView = mainView;  // Store the reference to MainView

        // Always pass the MainView reference to the view if the view is already available
        if (personView != null) {
            personView.setMainViewReference(mainView);
        }
    }

    // Method to load and display all persons in the view
    public void loadAllPersons() {
        try {
            List<Person> persons = personDAO.getAll();
            if (personView != null) {
                personView.displayPersons(persons);  // Display persons in the table
            }
        } catch (DAOException e) {
            // Handle the exception appropriately, perhaps showing a dialog
            JOptionPane.showMessageDialog(personView, "Error loading persons: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
