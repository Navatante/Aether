package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.utilities.Database;
import org.jonatancarbonellmartinez.view.RecentFlightsPanelView;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecentFlightsPanelPresenter implements Presenter, PanelPresenter {
    //private final GenericDAO<View,Integer> latestFlightDAO; creo que tengo que crear una entidad y su correspondiente DAO de la vista.
    private final RecentFlightsPanelView view;

    private int selectedVueloId;

    public RecentFlightsPanelPresenter(RecentFlightsPanelView view) {
        this.view = view;
    }

    @Override
    public void setActionListeners() {
        createSearchFieldListener();
        idOfLastFlightListener();
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

        // After loading the table, select first row:
        view.getLastFlightsTable().setRowSelectionInterval(0, 0);
    }

    // Pilot Hours Details TableModel
    public void loadCrewHoursDetails(DefaultTableModel tableModel, int flightId) {
        String sql = "SELECT * FROM view_crew_hours_detail WHERE flight_sk = ?";

        try (Connection connection = Database.getInstance().getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, flightId);
            ResultSet rs = pstmt.executeQuery();

            // Clear existing rows in the table model
            tableModel.setRowCount(0);

            // Populate the table model with new data
            while (rs.next()) {
                Object[] row = {
                        rs.getString("Crew"),
                        rs.getString("Rol"),
                        rs.getDouble("Vuelo_Dia"),
                        rs.getDouble("Vuelo_Noche"),
                        rs.getDouble("Vuelo_GVN"),
                        rs.getDouble("Instr"),
                        rs.getDouble("HMDS"),
                        rs.getDouble("IP"),
                        rs.getDouble("Formacion_Dia"),
                        rs.getDouble("Formacion_GVN"),
                        rs.getDouble("Winch_Trim")
                };
                tableModel.addRow(row);
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new DatabaseException("Error al acceder a la vista", e);
        }
    }


    // Listeners
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

    // Listener para manejar el dobleclick en la tabla de vuelos
    private void idOfLastFlightListener() {
        view.getLastFlightsTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 1) {
                    handleFlightSelection();
                }
            }
        });
    }

    private void handleFlightSelection() {
        int selectedRow = view.getLastFlightsTable().getSelectedRow();
        if (selectedRow != -1) {
            int modelRow = view.getLastFlightsTable().convertRowIndexToModel(selectedRow);
            selectedVueloId = (int) view.getLastFlightsTableModel().getValueAt(modelRow, 0);
            updateFlightDetails(selectedVueloId);
        }
    }

    private void updateFlightDetails(int vueloId) {
        view.getFlightDetailsTitleLabel().setText("Detalles del vuelo " + vueloId);
        loadCrewHoursDetails(view.getCrewHoursDetailTableModel(), vueloId);
    }

    // Getters and setters
    public int getSelectedVueloId() {
        return selectedVueloId;
    }

    public void updateSelectedVueloId() {
        if (view.getLastFlightsTableModel().getRowCount() > 0) {
            selectedVueloId = (int) view.getLastFlightsTableModel().getValueAt(0, 0);
        }
    }

}
