package org.jonatancarbonellmartinez.presentation.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import org.jonatancarbonellmartinez.presentation.viewmodel.AddPersonViewModel;

import javax.inject.Inject;

public class AddPersonViewController {
    @FXML private TextField codigoField;
    @FXML private TextField empleoField;
    @FXML private TextField cuerpoField;
    @FXML private TextField especialidadField;
    @FXML private TextField nombreField;
    @FXML private TextField apellido1Field;
    @FXML private TextField apellido2Field;
    @FXML private TextField telefonoField;
    @FXML private TextField dniField;
    @FXML private TextField divisionField;
    @FXML private TextField rolField;
    @FXML private DatePicker antiguedadDatePicker;
    @FXML private DatePicker embarqueDatePicker;
    @FXML private TextField ordenField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private final AddPersonViewModel viewModel;

    @Inject
    public AddPersonViewController(AddPersonViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private void initialize() {
        setupBindings();
        setupValidation();
    }

    private void setupBindings() {
        // Two-way bindings for form fields
        codigoField.textProperty().bindBidirectional(viewModel.codeProperty());
        empleoField.textProperty().bindBidirectional(viewModel.rankProperty());
        cuerpoField.textProperty().bindBidirectional(viewModel.cuerpoProperty());
        especialidadField.textProperty().bindBidirectional(viewModel.especialidadProperty());
        nombreField.textProperty().bindBidirectional(viewModel.nameProperty());
        apellido1Field.textProperty().bindBidirectional(viewModel.lastName1Property());
        apellido2Field.textProperty().bindBidirectional(viewModel.lastName2Property());
        telefonoField.textProperty().bindBidirectional(viewModel.phoneProperty());
        dniField.textProperty().bindBidirectional(viewModel.dniProperty());
        divisionField.textProperty().bindBidirectional(viewModel.divisionProperty());
        rolField.textProperty().bindBidirectional(viewModel.roleProperty());

    }

    private void setupValidation() {
        // Required fields styling
        codigoField.styleProperty().bind(
                Bindings.when(viewModel.codeProperty().isEmpty())
                        .then("-fx-border-color: red;")
                        .otherwise("")
        );

        nombreField.styleProperty().bind(
                Bindings.when(viewModel.nameProperty().isEmpty())
                        .then("-fx-border-color: red;")
                        .otherwise("")
        );

        apellido1Field.styleProperty().bind(
                Bindings.when(viewModel.lastName1Property().isEmpty())
                        .then("-fx-border-color: red;")
                        .otherwise("")
        );

        dniField.styleProperty().bind(
                Bindings.when(viewModel.dniProperty().isEmpty())
                        .then("-fx-border-color: red;")
                        .otherwise("")
        );

        // Save button enabled state
        saveButton.disableProperty().bind(viewModel.formValidProperty().not());
    }

    @FXML
    private void handleSaveButtonClicked() {
        viewModel.savePerson()
                .thenAccept(success -> {
                    if (success) {
                        closeDialog();
                    }
                })
                .exceptionally(throwable -> {
                    viewModel.errorMessageProperty().set(throwable.getMessage());
                    return null;
                });
    }

    @FXML
    private void handleCancelButtonClicked() {
        closeDialog();
    }

    private void closeDialog() {
        viewModel.reset();
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}