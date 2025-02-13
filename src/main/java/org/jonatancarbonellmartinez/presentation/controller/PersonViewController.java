package org.jonatancarbonellmartinez.presentation.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.jonatancarbonellmartinez.presentation.viewmodel.PersonViewModel;
import org.jonatancarbonellmartinez.presentation.viewmodel.PersonViewModel.PersonUI;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

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

        personTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Manejar evento de teclado para copiar con Ctrl + C
        personTable.setOnKeyPressed(this::handleCopyShortcut);
    }

    @FXML
    public void update() {
        viewModel.loadPersons();
    }

    private void handleCopyShortcut(KeyEvent event) {
        if (event.isControlDown() && event.getCode() == KeyCode.C) {
            copySelectedRowsToClipboard();
        }
    }

    private void copySelectedRowsToClipboard() {
        List<PersonUI> selectedRows = personTable.getSelectionModel().getSelectedItems();

        if (selectedRows.isEmpty()) {
            return; // No hay filas seleccionadas
        }

        // Obtener los valores de todas las celdas de las filas seleccionadas
        String copiedText = selectedRows.stream()
                .map(row -> personTable.getColumns().stream()
                        .map(column -> {
                            Object cellValue = column.getCellData(row);
                            return cellValue != null ? cellValue.toString() : "";
                        })
                        .collect(Collectors.joining("\t"))) // Separar celdas con tabulación
                .collect(Collectors.joining("\n")); // Separar filas con salto de línea

        // Copiar al portapapeles
        ClipboardContent content = new ClipboardContent();
        content.putString(copiedText);
        Clipboard.getSystemClipboard().setContent(content);
    }

    private void setupTableColumns() {
        // Usar ReadOnlyWrapper para mejor rendimiento
        idColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        codeColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getCode()));
        nameColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getName()));
        lastName1Column.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getLastName1()));
        lastName2Column.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getLastName2()));
        rankColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getRank()));
        cuerpoColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getCuerpo()));
        especialidadColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getEspecialidad()));
        phoneColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getPhone()));
        dniColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getDni()));
        divisionColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getDivision()));
        roleColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getRole()));
        antiguedadColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getAntiguedadEmpleo()));
        embarqueColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getFechaEmbarque()));
        activeColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().isActive()));
        orderColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getOrder()));
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