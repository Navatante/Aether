package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.factory.DAOFactorySQLite;
import org.jonatancarbonellmartinez.model.dao.GenericDAO;
import org.jonatancarbonellmartinez.model.entities.Unit;
import org.jonatancarbonellmartinez.view.UnitPanelView;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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

    public void onSearchTextChanged(String searchText) {
        applySearchFilter(searchText);
    }
}
