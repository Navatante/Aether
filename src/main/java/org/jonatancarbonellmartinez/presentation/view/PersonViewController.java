package org.jonatancarbonellmartinez.presentation.view;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jonatancarbonellmartinez.presentation.viewmodel.PersonViewModel;
import org.jonatancarbonellmartinez.presentation.viewmodel.PersonViewModel.PersonUI;

import javax.inject.Inject;

public class PersonViewController {
    @FXML private TableView<PersonUI> personTable;
    @FXML private TextField searchField;
    @FXML private RadioButton activeInactiveSwitch;

    // Column declarations
    @FXML private TableColumn<PersonUI, Integer> idColumn;
    @FXML private TableColumn<PersonUI, String> codeColumn;
    @FXML private TableColumn<PersonUI, String> rankColumn;
    @FXML private TableColumn<PersonUI, String> cuerpoColumn;
    @FXML private TableColumn<PersonUI, String> especialidadColumn;
    @FXML private TableColumn<PersonUI, String> nameColumn;
    @FXML private TableColumn<PersonUI, String> lastName1Column;
    @FXML private TableColumn<PersonUI, String> lastName2Column;
    @FXML private TableColumn<PersonUI, String> phoneColumn;
    @FXML private TableColumn<PersonUI, String> dniColumn;
    @FXML private TableColumn<PersonUI, String> divisionColumn;
    @FXML private TableColumn<PersonUI, String> roleColumn;
    @FXML private TableColumn<PersonUI, String> antiguedadColumn;
    @FXML private TableColumn<PersonUI, String> embarqueColumn;
    @FXML private TableColumn<PersonUI, String> activeColumn;
    @FXML private TableColumn<PersonUI, Integer> orderColumn;

    private final PersonViewModel viewModel;

    @Inject
    public PersonViewController(PersonViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    public void initialize() {
        setupTableColumns();
        setupBindings();
        viewModel.loadPersons();
    }

    @FXML
    public void update() {
        viewModel.loadPersons();
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        codeColumn.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
        rankColumn.setCellValueFactory(cellData -> cellData.getValue().rankProperty());
        cuerpoColumn.setCellValueFactory(cellData -> cellData.getValue().cuerpoProperty());
        especialidadColumn.setCellValueFactory(cellData -> cellData.getValue().especialidadProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        lastName1Column.setCellValueFactory(cellData -> cellData.getValue().lastName1Property());
        lastName2Column.setCellValueFactory(cellData -> cellData.getValue().lastName2Property());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        dniColumn.setCellValueFactory(cellData -> cellData.getValue().dniProperty());
        divisionColumn.setCellValueFactory(cellData -> cellData.getValue().divisionProperty());
        roleColumn.setCellValueFactory(cellData -> cellData.getValue().roleProperty());
        embarqueColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFechaEmbarque()));
        antiguedadColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAntiguedadEmpleo()));
        orderColumn.setCellValueFactory(cellData -> cellData.getValue().orderProperty().asObject());
        activeColumn.setCellValueFactory(cellData -> cellData.getValue().activeProperty());
    }

    private void setupBindings() {
        // Vincular la tabla con los datos filtrados
        personTable.setItems(viewModel.getFilteredPersons());

        // Vincular el campo de búsqueda
        searchField.textProperty().bindBidirectional(viewModel.searchQueryProperty());

        // Vincular el switch de activos/inactivos
        activeInactiveSwitch.selectedProperty().bindBidirectional(viewModel.showOnlyActiveProperty());

    }
}