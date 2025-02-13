package org.jonatancarbonellmartinez.presentation.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import org.jonatancarbonellmartinez.presentation.viewmodel.AddPersonViewModel;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class AddPersonViewController {
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML private TextField codigoField;
    @FXML private ComboBox<String> empleoComboBox;
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
    @FXML private VBox addPersonView;

    private final AddPersonViewModel viewModel;

    @Inject
    public AddPersonViewController(AddPersonViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private void initialize() {
        setupBindings();
        setupValidation();
        setupDatePicker();
        populateComboBoxes();
    }

    private void populateComboBoxes() {
        empleoComboBox.getItems().addAll("TCOL", "CF","CTE","CC","CAP","TN","TTE","AN","SBMY","STTE","BG","SG1","SGTO","CBMY","CB1","CBO","SDO","MRO");
    }

    private void setupDatePicker() {
        // Establecer locale español
        Locale.setDefault(new Locale("es", "ES"));
        configureDatePicker(embarqueDatePicker);
        configureDatePicker(antiguedadDatePicker);
    }

    private void setupBindings() {
        // Two-way bindings for form fields
        codigoField.textProperty().bindBidirectional(viewModel.codeProperty());
        //empleoComboBox.buttonCellProperty().bindBidirectional(viewModel.em);
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

    public static void configureDatePicker(DatePicker datePicker) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return formatter.format(date);
                }
                return "";
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, formatter);
                }
                return null;
            }
        });
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

    @FXML
    private void handleDialogPressed(MouseEvent event) {
        // Guardar la posición inicial del cursor relativa a la ventana
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    private void handleDialogDragged(MouseEvent event) {
        Stage stage = (Stage) addPersonView.getScene().getWindow();

        // Mover la ventana utilizando la diferencia entre la posición actual del cursor
        // y la posición inicial guardada
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    private void closeDialog() {
        viewModel.reset();
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}