package org.jonatancarbonellmartinez.viewmodel;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.model.utilities.Database;
import org.jonatancarbonellmartinez.view.RecentFlightsPanelView;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class RecentFlightsPanelPresenter implements Presenter, PanelPresenter {

    private final RecentFlightsPanelView view;

    private int selectedVueloId;

    public RecentFlightsPanelPresenter(RecentFlightsPanelView view) {
        this.view = view;
    }

    @Override
    public void setActionListeners() {
        createSearchFieldListener();
        idOfLastFlightListener();
        view.getDeleteFlightMenuItem().addActionListener( e -> onDeleteFlightItemClicked(selectedVueloId));
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

    // Session Details TableModel
    public void loadSessionDetails(DefaultTableModel tableModel, int flightId) {
        String sql = "SELECT * FROM view_session_details WHERE Vuelo_ID = ?";

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
                        rs.getString("Papeleta"),
                        rs.getString("Descripción"),
                        rs.getString("Plan"),
                        rs.getString("Bloque")
                };
                tableModel.addRow(row);
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new DatabaseException("Error al acceder a la vista", e);
        }
    }

    // Landing Details TableModel
    public void loadLandingDetails(DefaultTableModel tableModel, int flightId) {
        String sql = "SELECT * FROM view_landings WHERE Vuelo_ID = ?";

        try (Connection connection = Database.getInstance().getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, flightId);
            ResultSet rs = pstmt.executeQuery();

            // Clear existing rows in the table model
            tableModel.setRowCount(0);

            // Populate the table model with new data
            while (rs.next()) {
                Object[] row = {
                        rs.getString("Piloto"),
                        rs.getString("Lugar"),
                        rs.getString("Periodo"),
                        rs.getInt("Cantidad")
                };
                tableModel.addRow(row);
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new DatabaseException("Error al acceder a la vista", e);
        }
    }

    // Instrumental Apps. Details TableModel
    public void loadInstrumentalAppsDetails(DefaultTableModel tableModel, int flightId) {
        String sql = "SELECT * FROM view_instrumental_apps WHERE Vuelo_ID = ?";

        try (Connection connection = Database.getInstance().getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, flightId);
            ResultSet rs = pstmt.executeQuery();

            // Clear existing rows in the table model
            tableModel.setRowCount(0);

            // Populate the table model with new data
            while (rs.next()) {
                Object[] row = {
                        rs.getString("Piloto"),
                        rs.getString("Tipo"),
                        rs.getInt("Cantidad")
                };
                tableModel.addRow(row);
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new DatabaseException("Error al acceder a la vista", e);
        }
    }

    // SAR Apps. Details TableModel
    public void loadSarAppsDetails(DefaultTableModel tableModel, int flightId) {
        String sql = "SELECT * FROM view_sar_apps WHERE Vuelo_ID = ?";

        try (Connection connection = Database.getInstance().getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, flightId);
            ResultSet rs = pstmt.executeQuery();

            // Clear existing rows in the table model
            tableModel.setRowCount(0);

            // Populate the table model with new data
            while (rs.next()) {
                Object[] row = {
                        rs.getString("Piloto"),
                        rs.getString("Tipo"),
                        rs.getInt("Cantidad")
                };
                tableModel.addRow(row);
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new DatabaseException("Error al acceder a la vista", e);
        }
    }

    // Projectiles Details TableModel
    public void loadProjectilesDetails(DefaultTableModel tableModel, int flightId) {
        String sql = "SELECT * FROM view_projectiles WHERE Vuelo_ID = ?";

        try (Connection connection = Database.getInstance().getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, flightId);
            ResultSet rs = pstmt.executeQuery();

            // Clear existing rows in the table model
            tableModel.setRowCount(0);

            // Populate the table model with new data
            while (rs.next()) {
                Object[] row = {
                        rs.getString("Dotación"),
                        rs.getString("Arma"),
                        rs.getInt("Cantidad")
                };
                tableModel.addRow(row);
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new DatabaseException("Error al acceder a la vista", e);
        }
    }

    // Cupo Details TableModel
    public void loadCupoDetails(DefaultTableModel tableModel, int flightId) {
        String sql = "SELECT * FROM view_cupo WHERE Vuelo_ID = ?";

        try (Connection connection = Database.getInstance().getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, flightId);
            ResultSet rs = pstmt.executeQuery();

            // Clear existing rows in the table model
            tableModel.setRowCount(0);

            // Populate the table model with new data
            while (rs.next()) {
                Object[] row = {
                        rs.getString("Autoridad"),
                        rs.getDouble("Horas")
                };
                tableModel.addRow(row);
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new DatabaseException("Error al acceder a la vista", e);
        }
    }

    // Passengers Details TableModel
    public void loadPassengersDetails(DefaultTableModel tableModel, int flightId) {
        String sql = "SELECT * FROM view_passengers WHERE Vuelo_ID = ?";

        try (Connection connection = Database.getInstance().getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, flightId);
            ResultSet rs = pstmt.executeQuery();

            // Clear existing rows in the table model
            tableModel.setRowCount(0);

            // Populate the table model with new data
            while (rs.next()) {
                Object[] row = {
                        rs.getString("Tipo"),
                        rs.getInt("Cantidad"),
                        rs.getString("Ruta")
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
                if (evt.getClickCount() == 1 && evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
                    handleFlightSelection();
                } else if (evt.getClickCount() == 1 && evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
                    // Handle right-click to show popup menu
                    view.getDeleteFlightPopupMenu().show(view.getLastFlightsTable(), evt.getX(), evt.getY());
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
        loadSessionDetails(view.getSessionDetailTableModel(), vueloId);
        loadLandingDetails(view.getLandingTableModel(), vueloId);
        loadInstrumentalAppsDetails(view.getInstrumentalAppsTableModel(), vueloId);
        loadSarAppsDetails(view.getSarAppsTableModel(), vueloId);
        loadProjectilesDetails(view.getProjectilesTableModel(), vueloId);
        loadCupoDetails(view.getCupoTableModel(), vueloId);
        loadPassengersDetails(view.getPassengersTableModel(), vueloId);
    }

    public void onDeleteFlightItemClicked(int flightId) {
        final String confirmationCode = "QUIEROELIMINARELVUELO" + flightId;
        boolean validInput = false;

        while (!validInput) {
            // Create a deletion confirmation panel
            JPanel deletionConfirmationPanel = new JPanel(new BorderLayout(5, 5));

            // Warning label
            JLabel idLabel = new JLabel(String.valueOf(flightId));
            idLabel.setFont(new Font("SEGOE UI", Font.BOLD, 16));
            JLabel warningLabel = new JLabel("Va a borrar el vuelo: ");
            JPanel warningPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            deletionConfirmationPanel.add(warningPanel, BorderLayout.NORTH);
            warningPanel.add(warningLabel);
            warningPanel.add(idLabel);

            // Instruction label
            JLabel instructionLabel = new JLabel("Escriba '" + confirmationCode + "' para confirmar:");
            deletionConfirmationPanel.add(instructionLabel, BorderLayout.CENTER);

            // Text field for user input
            JTextField confirmationTextField = new JTextField(20);
            deletionConfirmationPanel.add(confirmationTextField, BorderLayout.SOUTH);

            // Show the dialog
            int result = JOptionPane.showOptionDialog(
                    null, deletionConfirmationPanel, "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
                    new String[]{"Borrar", "Cancelar"}, "Cancelar"
            );

            if (result == JOptionPane.YES_OPTION) {
                // Validate input
                if (confirmationTextField.getText().equals(confirmationCode)) {
                    try {
                        deleteFlight(flightId);
                        validInput = true;
                        JOptionPane.showMessageDialog(null,
                                "Vuelo eliminado correctamente.",
                                "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } catch (DatabaseException e) {
                        JOptionPane.showMessageDialog(null,
                                "Error al eliminar el vuelo: " + e.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Entrada incorrecta. Inténtalo de nuevo.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // El usuario pulso cancelar
                validInput = true;
            }
        }
    }

    private void deleteFlight(int flightId) {
        String enableForeignKeys = "PRAGMA foreign_keys = ON;"; // TODO lo dejo marcado porque es muy importante.
        String sql = "DELETE FROM fact_flight WHERE flight_sk = ?";

        try (Connection connection = Database.getInstance().getConnection();
             Statement stmt = connection.createStatement();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            // Habilitar claves foráneas
            stmt.execute(enableForeignKeys);

            // Preparar y ejecutar la eliminación
            pstmt.setInt(1, flightId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                // Si se consigue eliminar un registro, actualizamos la tabla y logeamos la eliminación
                view.updatePanel(); // actualizamos el panel
                logFlightDeletion(flightId);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error eliminando el vuelo", e);
        }
    }

    private void logFlightDeletion(int flightId) {
        String sql = "INSERT INTO deleted_flights_log (flight_id, user_name, timestamp) VALUES (?, ?, ?)";

        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, flightId);
            pstmt.setString(2, System.getProperty("user.name")); // Get current user name
            pstmt.setString(3, getCurrentTimestamp()); // Use current timestamp

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error inserting log entry for deleted flight", e);
        }
    }

    private String getCurrentTimestamp() {
        // Define the formatter for SQLite DATETIME
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Convert Instant to LocalDateTime and format it
        return LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).format(formatter);
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
