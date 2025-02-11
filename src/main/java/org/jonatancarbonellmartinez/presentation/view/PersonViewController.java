package org.jonatancarbonellmartinez.presentation.view;

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
    @FXML private TableColumn<PersonUI, String> nameColumn;
    @FXML private TableColumn<PersonUI, String> lastName1Column;
    @FXML private TableColumn<PersonUI, String> lastName2Column;
    @FXML private TableColumn<PersonUI, String> phoneColumn;
    @FXML private TableColumn<PersonUI, String> dniColumn;
    @FXML private TableColumn<PersonUI, String> divisionColumn;
    @FXML private TableColumn<PersonUI, String> roleColumn;
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

    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastName1Column.setCellValueFactory(new PropertyValueFactory<>("lastName1"));
        lastName2Column.setCellValueFactory(new PropertyValueFactory<>("lastName2"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        dniColumn.setCellValueFactory(new PropertyValueFactory<>("dni"));
        divisionColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        activeColumn.setCellValueFactory(new PropertyValueFactory<>("active"));
        orderColumn.setCellValueFactory(new PropertyValueFactory<>("order"));

        // Personalizar la columna de activo para mostrar "Activo"/"Inactivo"
        activeColumn.setCellFactory(column -> new TableCell<PersonUI, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    // Assuming the string values are "true"/"false" or "1"/"0"
                    boolean isActive = "true".equalsIgnoreCase(item) || "1".equals(item);
                    setText(isActive ? "Activo" : "Inactivo");
                }
            }
        });
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