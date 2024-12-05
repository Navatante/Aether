package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.factory.DAOFactorySQLite;
import org.jonatancarbonellmartinez.model.dao.GenericDAO;
import org.jonatancarbonellmartinez.model.entities.Person;
import org.jonatancarbonellmartinez.model.entities.Unit;
import org.jonatancarbonellmartinez.view.PanelView;
import org.jonatancarbonellmartinez.view.UnitPanelView;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UnitPanelPresenter implements Presenter, PanelPresenter {
    private final GenericDAO<Unit, Integer> unitDAO;
    private final UnitPanelView view;

    public UnitPanelPresenter(UnitPanelView unitPanelView) {
        this.view = unitPanelView;
        this.unitDAO = DAOFactorySQLite.getInstance().createUnitDAO();
    }

    @Override
    public void setActionListeners() {
        createSearchFieldListener();

    }

    private void createSearchFieldListener() {
        // Placeholder text
        final String placeholder = "Buscar";

        view.getSearchField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String searchText = view.getSearchField().getText();
                if (!searchText.equals(placeholder)) { // Ignore if it matches the placeholder
                    onSearchTextChanged(searchText); // Forward search text to presenter
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String searchText = view.getSearchField().getText();
                if (!searchText.equals(placeholder)) { // Ignore if it matches the placeholder
                    onSearchTextChanged(searchText); // Forward search text to presenter
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String searchText = view.getSearchField().getText();
                if (!searchText.equals(placeholder)) { // Ignore if it matches the placeholder
                    onSearchTextChanged(searchText); // Forward search text to presenter
                }
            }
        });
    }

    public void loadAllUnits() {
        try {
            List<Unit> units = unitDAO.getAll();
            if (view != null) {
                addUnitsToTableModel(units);  // Populate table model with units
            }
        } catch (DatabaseException e) {
            PanelView.showError(view,"Error loading persons: " + e.getMessage());
        }
    }

    public void addUnitsToTableModel(List<Unit> units) {
        view.getTableModel().setRowCount(0); // Clear existing rows

        for (Unit unit : units) {
            Object[] rowData = {
                    unit.getSk(),
                    unit.getUnitShort(),
                    unit.getUnitName(),
                    unit.getAgencyShort(),
                    unit.getAgencyName(),
                    unit.getAuthority(),
                    unit.getAuthorityShort(),
            };
            view.getTableModel().addRow(rowData);
        }

        // Apply filters after loading data
        applySearchFilter(""); // Clear search filter initially
    }

    public void onSearchTextChanged(String searchText) {
        applySearchFilter(searchText);
    }

    private void applySearchFilter(String searchText) {
        List<RowFilter<TableModel, Object>> filters = new ArrayList<>();

        // Escape special characters in the search text
        String escapedSearchText = escapeSpecialCharacters(searchText);

        // Add the search filter if it's not empty
        if (!escapedSearchText.trim().isEmpty()) {
            filters.add(RowFilter.regexFilter("(?i)" + escapedSearchText)); // Case-insensitive search
        }

        // Combine filters
        RowFilter<TableModel, Object> combinedFilter = null;
        combinedFilter = RowFilter.andFilter(filters);

        // Set the combined filter or reset if both are null
        view.getSorter().setRowFilter(combinedFilter);
    }

    // Method to escape special regex characters
    static String escapeSpecialCharacters(String text) {
        // This will escape the backslash and any other regex special characters
        return Pattern.quote(text);
    }
}
