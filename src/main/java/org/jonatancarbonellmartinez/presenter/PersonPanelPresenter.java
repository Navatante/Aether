package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.factory.DAOFactorySQLite;
import org.jonatancarbonellmartinez.model.dao.GenericDAO;
import org.jonatancarbonellmartinez.model.entities.Person;
import org.jonatancarbonellmartinez.view.PanelView;
import org.jonatancarbonellmartinez.view.PersonPanelView;

import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.List;

public class PersonPanelPresenter implements Presenter, PanelPresenter {
    private final GenericDAO<Person,Integer> personDAO;
    private final PersonPanelView view;
    private boolean isShowingActive = true; // Default to showing "Active" persons

    public PersonPanelPresenter(PersonPanelView personCardView) {
        this.view = personCardView;
        this.personDAO = DAOFactorySQLite.getInstance().createPersonDAOSQLite(); // new
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
            List<Person> persons = personDAO.getAll();
            if (view != null) {
                addPersonsToTableModel(persons);  // Populate table model with persons
            }
        } catch (DatabaseException e) {
            PanelView.showError(view,"Error loading persons: " + e.getMessage());
        }
    }

    public void addPersonsToTableModel(List<Person> persons) {
        view.getTableModel().setRowCount(0); // Clear existing rows

        for (Person person : persons) {
            Object[] rowData = {
                    person.getPersonSk(),
                    person.getPersonNk(),
                    person.getPersonRank(),
                    person.getPersonName(),
                    person.getPersonLastName1(),
                    person.getPersonLastName2(),
                    person.getPersonPhone(),
                    person.getPersonDni(),
                    person.getPersonDivision(),
                    person.getPersonRol(),
                    person.getPersonCurrentFlag() == 1 ? "Activo" : "Inactivo",
                    person.getPersonOrder()
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
        return text.replaceAll("([\\\\\\^\\.\\$\\|\\?\\*\\+\\(\\)\\[\\]\\{\\}])", "\\\\$1");
    }


}
