package org.jonatancarbonellmartinez.presentation.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.jonatancarbonellmartinez.presentation.viewmodel.AddPersonViewModel;

import javax.inject.Inject;

public class AddPersonViewController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;


    private AddPersonViewModel viewModel;

    @Inject
    public AddPersonViewController(AddPersonViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private void initialize() {
       setupBindings();
    }

    public void setupBindings() {
        // Configurar bindings
        nameField.textProperty().bindBidirectional(viewModel.personProperty().get().nameProperty());
        emailField.textProperty().bindBidirectional(viewModel.personProperty().get().emailProperty());
    }
}
