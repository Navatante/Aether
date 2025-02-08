package org.jonatancarbonellmartinez.presentation.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.jonatancarbonellmartinez.presentation.viewmodel.PersonViewModel;

import javax.inject.Inject;

public class PersonViewController {
    @FXML private TableView<PersonViewModel.PersonUI> personTable;
    @FXML private TextField searchField;
    @FXML private Button addButton;
    @FXML private Button editButton;

    private final PersonViewModel viewModel;

    @Inject
    public PersonViewController(PersonViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    public void initialize() {
        // Conectar la tabla con los datos del ViewModel
        personTable.setItems(viewModel.getFilteredPersons());

        // Vincular el campo de búsqueda con el ViewModel
        searchField.textProperty().bindBidirectional(viewModel.searchQueryProperty());

        // Cargar personas al iniciar la vista
        viewModel.loadPersons();

        // Set up button handlers programmatically
        addButton.setOnAction(event -> onAddPersonClicked());
        editButton.setOnAction(event -> onEditPersonClicked());
    }

    private void onAddPersonClicked() {
        viewModel.selectedPersonProperty().set(new PersonViewModel.PersonUI());
    }

    private void onEditPersonClicked() {
        PersonViewModel.PersonUI selected = personTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            viewModel.selectedPersonProperty().set(selected);
        }
    }
}
