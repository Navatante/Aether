package org.jonatancarbonellmartinez.presentation.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;
import org.jonatancarbonellmartinez.presentation.viewmodel.AddPersonViewModel;

import javax.inject.Inject;

public class AddPersonViewController {
    @FXML private TextField codeField;
    @FXML private TextField rankField;
    @FXML private TextField cuerpoField;
    @FXML private TextField especialidadField;
    @FXML private TextField nameField;
    @FXML private TextField lastName1Field;
    @FXML private TextField lastName2Field;
    @FXML private TextField phoneField;
    @FXML private TextField dniField;
    @FXML private TextField divisionField;
    @FXML private TextField roleField;
    @FXML private DatePicker antiguedadEmpleoField;
    @FXML private DatePicker fechaEmbarqueField;
    @FXML private TextField orderField;
    @FXML private CheckBox activeField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private Label errorLabel;

    private final AddPersonViewModel viewModel;

    @Inject
    public AddPersonViewController(AddPersonViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private void initialize() {
        setupBindings();
        setupValidation();
        setupButtons();
    }

    private void setupBindings() {
        // Two-way bindings for form fields
        codeField.textProperty().bindBidirectional(viewModel.codeProperty());
        rankField.textProperty().bindBidirectional(viewModel.rankProperty());
        cuerpoField.textProperty().bindBidirectional(viewModel.cuerpoProperty());
        especialidadField.textProperty().bindBidirectional(viewModel.especialidadProperty());
        nameField.textProperty().bindBidirectional(viewModel.nameProperty());
        lastName1Field.textProperty().bindBidirectional(viewModel.lastName1Property());
        lastName2Field.textProperty().bindBidirectional(viewModel.lastName2Property());
        phoneField.textProperty().bindBidirectional(viewModel.phoneProperty());
        dniField.textProperty().bindBidirectional(viewModel.dniProperty());
        divisionField.textProperty().bindBidirectional(viewModel.divisionProperty());
        roleField.textProperty().bindBidirectional(viewModel.roleProperty());

        // Use Bindings utility for Integer conversion
        Bindings.bindBidirectional(
                orderField.textProperty(),
                viewModel.orderProperty(),
                new NumberStringConverter()
        );

        activeField.selectedProperty().bindBidirectional(viewModel.activeProperty());

        // Error message binding
        errorLabel.textProperty().bind(viewModel.errorMessageProperty());
        errorLabel.visibleProperty().bind(viewModel.errorMessageProperty().isNotEmpty());
    }

    private void setupValidation() {
        // Required fields styling
        codeField.styleProperty().bind(
                Bindings.when(viewModel.codeProperty().isEmpty())
                        .then("-fx-border-color: red;")
                        .otherwise("")
        );

        nameField.styleProperty().bind(
                Bindings.when(viewModel.nameProperty().isEmpty())
                        .then("-fx-border-color: red;")
                        .otherwise("")
        );

        lastName1Field.styleProperty().bind(
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

    private void setupButtons() {
        saveButton.setOnAction(event -> handleSave());
        cancelButton.setOnAction(event -> handleCancel());
    }

    private void handleSave() {
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

    private void handleCancel() {
        closeDialog();
    }

    private void closeDialog() {
        viewModel.reset();
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}