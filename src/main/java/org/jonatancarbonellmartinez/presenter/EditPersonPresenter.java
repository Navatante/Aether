package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.model.dao.PersonDAO;
import org.jonatancarbonellmartinez.view.AddPersonView;

public class EditPersonPresenter {
    private final PersonDAO personDAO;  // DAO to handle database operations
    private AddPersonView personView;       // View associated with this presenter, initially null

    public EditPersonPresenter(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }


}
