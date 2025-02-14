package org.jonatancarbonellmartinez.presentation.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.jonatancarbonellmartinez.presentation.viewmodel.AddPersonViewModel;
import org.jonatancarbonellmartinez.services.util.TextFormatterService;

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
    @FXML private Label errorLabel;
    private final StringProperty errorMessage = new SimpleStringProperty("");

    private final AddPersonViewModel viewModel;
    private final TextFormatterService textFormatter;

    @Inject
    public AddPersonViewController(AddPersonViewModel viewModel, TextFormatterService textFormatter) {
        this.viewModel = viewModel;
        this.textFormatter = textFormatter;
    }

    @FXML
    private void initialize() {
        setupBindings();
        setupValidation();
        setupDatePicker();
        populateComboBoxes();
        setupTextFormatters();
        setDniFieldFormatter();
    }

    private void setupTextFormatters() {
        setOrderFieldFormatter();
        setCodigoFieldFormatter();
        setNameFieldFormatter();
        setLastName1FieldFormatter();
        setLastName2FieldFormatter();
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
        ordenField.textProperty().bindBidirectional(viewModel.orderProperty(), converter);
        empleoComboBox.valueProperty().bindBidirectional(viewModel.rankProperty());
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

    // Crear un StringConverter para la conversión entre String e Integer
    StringConverter<Integer> converter = new StringConverter<Integer>() {
        @Override
        public String toString(Integer number) {
            if (number == null) {
                return "";
            }
            return number.toString();
        }

        @Override
        public Integer fromString(String string) {
            if (string == null || string.isEmpty()) {
                return null;
            }
            try {
                return Integer.parseInt(string);
            } catch (NumberFormatException e) {
                return null;
            }
        }
    };

    private void setupValidation() {
        // Required fields styling
        ordenField.styleProperty().bind(
                Bindings.when(viewModel.orderProperty().isNull())
                        .then("-fx-border-color: #B85C4B;")
                        .otherwise("")
        );

        nombreField.styleProperty().bind(
                Bindings.when(viewModel.nameProperty().isEmpty())
                        .then("-fx-border-color: #B85C4B;")
                        .otherwise("")
        );

        empleoComboBox.styleProperty().bind(
                Bindings.when(empleoComboBox.valueProperty().isNull())
                        .then("-fx-border-color: #B85C4B;")
                        .otherwise("")
        );

        apellido1Field.styleProperty().bind(
                Bindings.when(viewModel.lastName1Property().isEmpty())
                        .then("-fx-border-color: #B85C4B;")
                        .otherwise("")
        );

        apellido2Field.styleProperty().bind(
                Bindings.when(viewModel.lastName2Property().isEmpty())
                        .then("-fx-border-color: #B85C4B;")
                        .otherwise("")
        );

        telefonoField.styleProperty().bind(
                Bindings.when(viewModel.phoneProperty().isEmpty())
                        .then("-fx-border-color: #B85C4B;")
                        .otherwise("")
        );

        dniField.styleProperty().bind(
                Bindings.when(viewModel.dniProperty().isEmpty())
                        .then("-fx-border-color: #B85C4B;")
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

    private void setOrderFieldFormatter() {
        TextFormatter<String> orderFormatter = textFormatter.createNumericFormatter(5);
        ordenField.setTextFormatter(orderFormatter);

//        ordenField.textProperty().addListener((obs, oldVal, newVal) -> {
//            if (!newVal.isEmpty()) {
//                try {
//                    int value = Integer.parseInt(newVal);
//                    viewModel.orderProperty().set(value);
//                    errorLabel.setText(""); // Limpiar mensaje de error si es válido
//                } catch (NumberFormatException e) {
//                    errorLabel.setText("Solo se permiten números");
//                    errorLabel.setStyle("-fx-text-fill: red;");
//                }
//            } else {
//                viewModel.orderProperty().set(0);
//                errorLabel.setText(""); // Limpiar mensaje de error si está vacío
//            }
//        });
    }

    private void setCodigoFieldFormatter() {
        TextFormatter<String> codigoFormatter = textFormatter.createAlphabeticWithoutSpacesFormatter(3);
        codigoField.setTextFormatter(codigoFormatter);

        // Add focus listener to validate when field loses focus
        codigoField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // When focus is lost
                String text = codigoField.getText();
                if (text.length() != 3) {
                    if (!text.trim().isEmpty()) {
                        errorLabel.setText("El Código debe contener 3 letras");
                        errorLabel.setStyle("-fx-text-fill: #B85C4B;");
                        Platform.runLater(() -> codigoField.requestFocus());

                        // Crear un Timeline para ocultar el mensaje después de 3 segundos
                        Timeline timeline = new Timeline(new KeyFrame(
                                Duration.seconds(3),
                                evt -> errorLabel.setText("")
                        ));
                        timeline.play();
                    }
                }
            }
        });
    }

    private void setNameFieldFormatter() {
        TextFormatter<String> nameFormatter = textFormatter.createAlphabeticWithSpacesFormatter(50);
        nombreField.setTextFormatter(nameFormatter);
    }

    private void setLastName1FieldFormatter() {
        TextFormatter<String> lastName1Formatter = textFormatter.createAlphabeticWithSpacesFormatter(50);
        apellido1Field.setTextFormatter(lastName1Formatter);
    }

    private void setLastName2FieldFormatter() {
        TextFormatter<String> lastName2Formatter = textFormatter.createAlphabeticWithSpacesFormatter(50);
        apellido2Field.setTextFormatter(lastName2Formatter);
    }

    private void setDniFieldFormatter() {
        TextFormatter<String> codigoFormatter = textFormatter.createNumericFormatter(8);
        dniField.setTextFormatter(codigoFormatter);

        // Add focus listener to validate when field loses focus
        dniField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // When focus is lost
                String text = dniField.getText();
                if (text.length() != 8) {
                    if (!text.trim().isEmpty()) {
                        errorLabel.setText("El DNI debe contener 8 números");
                        errorLabel.setStyle("-fx-text-fill: #B85C4B;");
                        Platform.runLater(() -> dniField.requestFocus());

                        // Crear un Timeline para ocultar el mensaje después de 3 segundos
                        Timeline timeline = new Timeline(new KeyFrame(
                                Duration.seconds(3),
                                evt -> errorLabel.setText("")
                        ));
                        timeline.play();
                    }
                }
            }
        });
    }
}