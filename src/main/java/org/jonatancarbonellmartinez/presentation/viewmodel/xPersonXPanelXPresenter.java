package org.jonatancarbonellmartinez.presentation.viewmodel;

import org.jonatancarbonellmartinez.xexceptions.DatabaseException;
import org.jonatancarbonellmartinez.xfactory.DAOFactorySQLite;
import org.jonatancarbonellmartinez.data.database.GenericDAO;
import org.jonatancarbonellmartinez.data.model.PersonEntity;
import org.jonatancarbonellmartinez.presentation.view.fxml.xPanelView;
import org.jonatancarbonellmartinez.presentation.view.fxml.xPersonXPanelXView;

import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class xPersonXPanelXPresenter implements xPresenter, xPanelPresenter {
    private final GenericDAO<PersonEntity,Integer> personDAO;
    private final xPersonXPanelXView view;
    private boolean isShowingActive = true; // Default to showing "Active" persons

    public xPersonXPanelXPresenter(xPersonXPanelXView personCardView) {
        this.view = personCardView;
        this.personDAO = DAOFactorySQLite.getInstance().createPersonDAO(); // new
    }

    @Override
    public void setActionListeners() {
        createSearchFieldListener();
        view.getTogglePersonState().addActionListener(e -> onPersonStateChanged(view.getTogglePersonState().isSelected()));
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

    public void loadAllPersons() {
        try {
            List<PersonEntity> personEntities = personDAO.getAll();
            if (view != null) {
                addPersonsToTableModel(personEntities);  // Populate table model with persons
            }
        } catch (DatabaseException e) {
            xPanelView.showError(view,"Error loading persons: " + e.getMessage());
        }
    }

    public void addPersonsToTableModel(List<PersonEntity> personEntities) {
        view.getTableModel().setRowCount(0); // Clear existing rows

        for (PersonEntity personEntity : personEntities) {
            Object[] rowData = {
                    personEntity.getPersonSk(),
                    personEntity.getPersonNk(),
                    personEntity.getPersonRank(),
                    personEntity.getPersonName(),
                    personEntity.getPersonLastName1(),
                    personEntity.getPersonLastName2(),
                    personEntity.getPersonPhone(),
                    personEntity.getPersonDni(),
                    personEntity.getPersonDivision(),
                    personEntity.getPersonRol(),
                    personEntity.getPersonCurrentFlag() == 1 ? "Activo" : "Inactivo",
                    personEntity.getPersonOrder()
            };
            view.getTableModel().addRow(rowData);
        }

        // Apply filters after loading data
        applyPersonStateFilter(); // Ensure the correct state filter is applied
        applySearchFilter(""); // Clear search filter initially
    }

    public void onSearchTextChanged(String searchText) {
        applySearchFilter(searchText);
    }

    public void onPersonStateChanged(boolean isActiveSelected) {
        isShowingActive = isActiveSelected;
        applyPersonStateFilter(); // Apply the state filter
    }

    private void applyPersonStateFilter() {
        RowFilter<TableModel, Object> stateFilter = RowFilter.regexFilter(isShowingActive ? "Activo" : "Inactivo", 10);
        view.getSorter().setRowFilter(stateFilter); // Set the "Situación" filter

        // Get the current text from the search field
        String searchText = view.getSearchField().getText();

        // Ignore the placeholder text "Buscar"
        if (searchText != null && !searchText.trim().isEmpty() && !searchText.equals("Buscar")) {
            applySearchFilter(searchText); // Reapply the current search filter if it's not the placeholder
        } else {
            applySearchFilter(""); // Clears the search filter if the placeholder is present
        }
    }

    private void applySearchFilter(String searchText) {
        List<RowFilter<TableModel, Object>> filters = new ArrayList<>();

        // Escape special characters in the search text
        String escapedSearchText = escapeSpecialCharacters(searchText);

        // Add the search filter if it's not empty
        if (!escapedSearchText.trim().isEmpty()) {
            filters.add(RowFilter.regexFilter("(?i)" + escapedSearchText)); // Case-insensitive search
        }

        // Always add the state filter
        filters.add(RowFilter.regexFilter(isShowingActive ? "Activo" : "Inactivo", 10));

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
