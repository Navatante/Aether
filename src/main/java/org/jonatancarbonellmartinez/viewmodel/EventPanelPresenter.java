package org.jonatancarbonellmartinez.viewmodel;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.factory.DAOFactorySQLite;
import org.jonatancarbonellmartinez.model.dao.GenericDAO;
import org.jonatancarbonellmartinez.model.entities.Event;
import org.jonatancarbonellmartinez.view.EventPanelView;
import org.jonatancarbonellmartinez.view.PanelView;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.List;

public class EventPanelPresenter implements Presenter, PanelPresenter {
    private final GenericDAO<Event,Integer> eventDAO;
    private final EventPanelView view;

    public EventPanelPresenter(EventPanelView eventPanelView) {
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
                    PanelPresenter.applySearchFilter(searchText,view.getSorter());
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String searchText = view.getSearchField().getText();
                if (!searchText.equals(placeholder)) { // Ignore if it matches the placeholder
                    PanelPresenter.applySearchFilter(searchText,view.getSorter());
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String searchText = view.getSearchField().getText();
                if (!searchText.equals(placeholder)) { // Ignore if it matches the placeholder
                    PanelPresenter.applySearchFilter(searchText,view.getSorter());
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
        PanelPresenter.applySearchFilter("", view.getSorter());
    }
}
