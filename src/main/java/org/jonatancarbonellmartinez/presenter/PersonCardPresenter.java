package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.dao.PersonDAO;
import org.jonatancarbonellmartinez.model.entities.Person;
import org.jonatancarbonellmartinez.view.PersonCardView;

import javax.swing.*;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

public class PersonCardPresenter {
    private final PersonDAO personDAO;
    private final PersonCardView personCardView;
    private boolean isShowingActive = true; // Default to showing "Active" persons

    public PersonCardPresenter(PersonCardView personCardView, PersonDAO personDAO) {
        this.personCardView = personCardView;
        this.personDAO = personDAO;
    }

    public void loadAllPersons() {
        try {
            List<Person> persons = personDAO.getAll();
            if (personCardView != null) {
                addPersonsToTableModel(persons);  // Populate table model with persons
            }
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(personCardView, "Error loading persons: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addPersonsToTableModel(List<Person> persons) {
        personCardView.getTableModel().setRowCount(0); // Clear existing rows

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
            personCardView.getTableModel().addRow(rowData);
        }

        // Apply filters after loading data
        applyPersonStateFilter(); // Ensure the correct state filter is applied
        applySearchFilter(""); // Clear search filter initially
    }

    public void onSearchTextChanged(String searchText) {
        applySearchFilter(searchText); // Apply the search filter
    }

    public void onPersonStateChanged(boolean isActiveSelected) {
        isShowingActive = isActiveSelected;
        applyPersonStateFilter(); // Apply the state filter
    }

    private void applyPersonStateFilter() {
        RowFilter<TableModel, Object> stateFilter = RowFilter.regexFilter(isShowingActive ? "Activo" : "Inactivo", 10);
        personCardView.getSorter().setRowFilter(stateFilter); // Set the "Situación" filter
        applySearchFilter(personCardView.getSearchField().getText()); // Reapply the current search filter
    }

    private void applySearchFilter(String searchText) {
        List<RowFilter<TableModel, Object>> filters = new ArrayList<>();

        // Add the search filter if it's not empty
        if (searchText.trim().length() > 0) {
            filters.add(RowFilter.regexFilter("(?i)" + searchText)); // Case-insensitive search
        }

        // Always add the state filter
        filters.add(RowFilter.regexFilter(isShowingActive ? "Activo" : "Inactivo", 10));

        // Combine filters
        RowFilter<TableModel, Object> combinedFilter = null;
        if (!filters.isEmpty()) {
            combinedFilter = RowFilter.andFilter(filters);
        }

        // Set the combined filter or reset if both are null
        personCardView.getSorter().setRowFilter(combinedFilter);
    }

}
