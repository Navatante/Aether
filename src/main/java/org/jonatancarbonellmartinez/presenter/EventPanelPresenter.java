package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.factory.DAOFactorySQLite;
import org.jonatancarbonellmartinez.model.dao.GenericDAO;
import org.jonatancarbonellmartinez.model.entities.Event;
import org.jonatancarbonellmartinez.model.entities.Person;
import org.jonatancarbonellmartinez.view.EventPanelView;
import org.jonatancarbonellmartinez.view.PanelView;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

public class EventPanelPresenter implements Presenter, PanelPresenter {
    private final GenericDAO<Event,Integer> eventDAO;
    private final EventPanelView view;

    public EventPanelPresenter(EventPanelView eventPanelView) {
        this.view = eventPanelView;
        this.eventDAO = DAOFactorySQLite.getInstance().createEventDAOSQLite();
    }
    @Override
    public void setActionListeners() {
        createSearchFieldListener();
    }

    private void createSearchFieldListener() {
        view.getSearchField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onSearchTextChanged(view.getSearchField().getText()); // Forward search text to presenter
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onSearchTextChanged(view.getSearchField().getText()); // Forward search text to presenter
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                onSearchTextChanged(view.getSearchField().getText()); // Forward search text to presenter
            }
        });
    }

    public void loadAllEvents() {
        try {
            List<Event> events = eventDAO.getAll();
            if (view != null) {
                addEventsToTableModel(events);  // Populate table model with persons
            }
        } catch (DatabaseException e) {
            PanelView.showError(view,"Error loading events: " + e.getMessage());
        }
    }

    public void addEventsToTableModel(List<Event> events) {
        view.getTableModel().setRowCount(0); // Clear existing rows

        for (Event event : events) {
            Object[] rowData = {
                    event.getEventSk(),
                    event.getEventName(),
                    event.getEventPlace(),
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

        // Add the search filter if it's not empty
        if (searchText.trim().length() > 0) {
            filters.add(RowFilter.regexFilter("(?i)" + searchText)); // Case-insensitive search
        }

        // Combine filters
        RowFilter<TableModel, Object> combinedFilter = null;
        if (!filters.isEmpty()) {
            combinedFilter = RowFilter.andFilter(filters);
        }

        // Set the combined filter or reset if both are null
        view.getSorter().setRowFilter(combinedFilter);
    }
}
