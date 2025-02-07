package org.jonatancarbonellmartinez.presentation.viewmodel;

import org.jonatancarbonellmartinez.xexceptions.DatabaseException;
import org.jonatancarbonellmartinez.xfactory.DAOFactorySQLite;
import org.jonatancarbonellmartinez.data.database.GenericDAO;
import org.jonatancarbonellmartinez.data.model.Event;
import org.jonatancarbonellmartinez.presentation.view.fxml.xEventXPanelXView;
import org.jonatancarbonellmartinez.presentation.view.fxml.xPanelView;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.List;

public class xEventXPanelXPresenter implements xPresenter, xPanelPresenter {
    private final GenericDAO<Event,Integer> eventDAO;
    private final xEventXPanelXView view;

    public xEventXPanelXPresenter(xEventXPanelXView eventPanelView) {
        this.view = eventPanelView;
        this.eventDAO = DAOFactorySQLite.getInstance().createEventDAO();
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
                    xPanelPresenter.applySearchFilter(searchText,view.getSorter());
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String searchText = view.getSearchField().getText();
                if (!searchText.equals(placeholder)) { // Ignore if it matches the placeholder
                    xPanelPresenter.applySearchFilter(searchText,view.getSorter());
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String searchText = view.getSearchField().getText();
                if (!searchText.equals(placeholder)) { // Ignore if it matches the placeholder
                    xPanelPresenter.applySearchFilter(searchText,view.getSorter());
                }
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
            xPanelView.showError(view,"Error loading events: " + e.getMessage());
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
        xPanelPresenter.applySearchFilter("", view.getSorter());
    }
}
