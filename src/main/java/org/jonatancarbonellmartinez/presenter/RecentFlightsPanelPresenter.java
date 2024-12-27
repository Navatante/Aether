package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.entities.Event;
import org.jonatancarbonellmartinez.utilities.Database;
import org.jonatancarbonellmartinez.view.RecentFlightsPanelView;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecentFlightsPanelPresenter implements Presenter, PanelPresenter {
    //private final GenericDAO<View,Integer> latestFlightDAO; creo que tengo que crear una entidad y su correspondiente DAO de la vista.
    private final RecentFlightsPanelView view;

    public RecentFlightsPanelPresenter(RecentFlightsPanelView view) {
        this.view = view;
    }

    @Override
    public void setActionListeners() {
        createSearchFieldListener();
        // aqui iria el listener de seleccionar el ID para actualizar las tablas de detalles
    }

    public void loadLatest50Flights(DefaultTableModel tableModel) {
        String sql = "SELECT * FROM view_last_flights ORDER BY ID DESC LIMIT 50";

        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // Clear existing rows in the table model
            tableModel.setRowCount(0);

            // Populate the table model with new data
            while (rs.next()) {
                Object[] row = {
                        rs.getInt("ID"),
                        rs.getString("Fecha Hora"),
                        rs.getString("Helicóptero"),
                        rs.getString("Evento"),
                        rs.getString("HAC"),
                        rs.getDouble("Horas")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error al acceder a la vista", e);
        }
    }

    public void loadPilotHoursDetails(DefaultTableModel tableModel) {
        String sql = "SELECT * FROM view_pilot_hours_detail WHERE flight_sk = 91"; // TODO I have to achieve to put ? and do it dinamically

        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // Clear existing rows in the table model
            tableModel.setRowCount(0);

            // Populate the table model with new data
            while (rs.next()) {
                Object[] row = {
                        rs.getInt("flight_sk"),
                        rs.getString("Piloto"),
                        rs.getDouble("Vuelo Dia"),
                        rs.getDouble("Vuelo Noche"),
                        rs.getDouble("Vuelo GVN"),
                        rs.getDouble("Instr."),
                        rs.getDouble("HMDS"),
                        rs.getDouble("IP"),
                        rs.getDouble("Formación Dia"),
                        rs.getDouble("Formación GVN")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error al acceder a la vista", e);
        }
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

}
