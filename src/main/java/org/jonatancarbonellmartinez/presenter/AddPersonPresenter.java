package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.model.dao.PersonDAO;
import org.jonatancarbonellmartinez.view.AddPersonView;

public class AddPersonPresenter {
    private final PersonDAO personDAO;  // DAO to handle database operations
    private AddPersonView personView;       // View associated with this presenter, initially null

    public AddPersonPresenter(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    // Method to get or initialize the PersonView
    public AddPersonView getView() {
        if (personView == null) { // Lazy initialization
            personView = new AddPersonView();
            personView.setPresenter(this); // Establish relationship
            //bindViewActions(); // Bind actions after initializing the view
        }
        return personView; // Return the initialized view
    }

//    private void bindViewActions() {
//        // Example: Bind action to a button in the view
//        personView.getSaveButton().addActionListener(e -> savePerson());
//        personView.getLoadButton().addActionListener(e -> loadPerson());
//        // Add other action bindings as needed
//    }

//    public void savePerson() {
//        // Retrieve data from the view and save it via DAO
//        try {
//            Person person = new Person();
//            person.setPersonName(personView.getNameField().getText());
//            // Set other properties from the view fields...
//
//            personDAO.savePerson(person);
//            personView.showSuccessMessage("Person saved successfully.");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            personView.showErrorMessage("Error saving person: " + e.getMessage());
//        }
//    }

//    public void loadPerson() {
//        // Load a person from the database and display in the view
//        try {
//            int personId = Integer.parseInt(personView.getIdField().getText());
//            Person person = personDAO.getPersonById(personId);
//            personView.displayPerson(person);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            personView.showErrorMessage("Error loading person: " + e.getMessage());
//        }
//    }
}
