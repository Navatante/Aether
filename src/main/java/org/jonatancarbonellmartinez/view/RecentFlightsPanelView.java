package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.presenter.RecentFlightsPanelPresenter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class RecentFlightsPanelView extends JPanel implements View, PanelView {
    private RecentFlightsPanelPresenter presenter;
    private JTable lastFlightsTable, pilotHoursDetailTable;

    private DefaultTableModel lastFlightsTableModel, pilotHoursDetailTableModel;
    private TableRowSorter<TableModel> sorter;

    JLabel lastFlightsTitleLabel, pilotHoursDetailTitleLabel;

    private JTextField searchField;
    private JPanel topPanel, insideTopPanelLeft, insideTopPanelRight;
    private JPanel middlePanel;
    private JPanel bottomPanel;
    JScrollPane lastFlightsScrollPane, pilotHoursDetailScrollPane;

    public RecentFlightsPanelView() {
        this.presenter = new RecentFlightsPanelPresenter(this);
        this.initializeUI();
        updatePanel();
        setVisible(true);
    }

    @Override
    public void updatePanel() {
        presenter.loadLatest50Flights(lastFlightsTableModel);
        presenter.loadPilotHoursDetails(pilotHoursDetailTableModel);
        // TODO maybe load other detail queries.
    }

    @Override
    public void setupUIProperties() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(0, 5, 5, 5));
    }

    @Override
    public void createPanels() {
        topPanel = new JPanel(new BorderLayout());
        insideTopPanelLeft = new JPanel();
        insideTopPanelRight = new JPanel();
        middlePanel = new JPanel(new BorderLayout());
        bottomPanel = new JPanel(new BorderLayout());
    }

    @Override
    public void createComponents() {
        lastFlightsTitleLabel = new JLabel("Últimos vuelos");
        // Last Flights Table // TODO si se pincha en la cabecera Horas, salta un error interno porque intento ordenarlos pero los valores son String
        lastFlightsTableModel = new DefaultTableModel(new String[] {"ID", "Fecha", "Helicóptero", "Evento", "HAC", "Horas"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Making table non-editable
            }
            // Define "ID" as Integer; other columns default to String (in order to sort properly when headers are clicked)
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Integer.class;
                }
                return String.class;
            }
        };

        lastFlightsTable = new JTable(lastFlightsTableModel);

        // Center-align all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < lastFlightsTable.getColumnModel().getColumnCount(); i++) {
            lastFlightsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        lastFlightsScrollPane = new JScrollPane(lastFlightsTable);

        searchField = View.createTextField("Buscar");
        sorter = new TableRowSorter<>(lastFlightsTableModel);

        // Pilot Hours Details Table
        pilotHoursDetailTitleLabel = new JLabel("Horas Pilotos");
        pilotHoursDetailTableModel = new DefaultTableModel(new String[] {"Vuelo", "Piloto", "Vuelo Dia", "Vuelo Noche", "Vuelo GVN", "Instr.", "HMDS", "IP", "Formación Dia", "Formación GVN"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Making table non-editable
            }
            // Define "ID" as Integer; other columns default to String (in order to sort properly when headers are clicked)
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Integer.class;
                }
                return String.class;
            }
        };

        pilotHoursDetailTable = new JTable(pilotHoursDetailTableModel);

        // Center-align all columns
        for (int i = 0; i < pilotHoursDetailTable.getColumnModel().getColumnCount(); i++) {
            pilotHoursDetailTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        pilotHoursDetailScrollPane = new JScrollPane(pilotHoursDetailTable);
        // TODO this is the render to gray the 0.0 numbers
        // Apply custom renderer to numeric columns
        PilotHoursCellRenderer cellRenderer = new PilotHoursCellRenderer();
        for (int i = 2; i < pilotHoursDetailTable.getColumnCount(); i++) {
            pilotHoursDetailTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
    }

    // Render to gray 0.0 values
    public class PilotHoursCellRenderer extends DefaultTableCellRenderer { // TODO is not working properly
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // If the cell is selected, keep the default color for selected cells
            if (isSelected) {
                cell.setForeground(Color.WHITE); // Text color for selected cells (typically white)
            } else {
                // If the value is 0.0, set the text color to gray, otherwise set it to black
                if (value instanceof Double && ((Double) value) == 0.0) {
                    cell.setForeground(Color.GRAY); // Gray color for 0.0 values
                }
            }

            return cell;
        }
    }

    @Override
    public void configurePanels() {
        topPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        lastFlightsScrollPane.setPreferredSize(new Dimension(0, 150));
        pilotHoursDetailScrollPane.setPreferredSize(new Dimension(0, 100));
    }

    @Override
    public void configureComponents() {
        lastFlightsTitleLabel.setFont(PanelView.ENTITY_TITLE_LABEL_FONT);
        pilotHoursDetailTitleLabel.setFont(PanelView.ENTITY_TITLE_LABEL_FONT);

        searchField.setPreferredSize(new Dimension(200, 25));
        lastFlightsTable.setRowSorter(sorter);

        lastFlightsTable.setCellSelectionEnabled(true);
        pilotHoursDetailTable.setCellSelectionEnabled(true);

        lastFlightsTable.setToolTipText("Doble click para detalles");

    }

    @Override
    public void assemblePanels() {
        this.add(topPanel, BorderLayout.NORTH);
        topPanel.add(insideTopPanelLeft, BorderLayout.WEST);
        topPanel.add(insideTopPanelRight, BorderLayout.EAST);
        this.add(middlePanel, BorderLayout.CENTER);
        middlePanel.add(lastFlightsScrollPane, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(pilotHoursDetailScrollPane, BorderLayout.NORTH);
    }

    @Override
    public void assembleComponents() {
        View.addComponentsToPanel(insideTopPanelLeft, lastFlightsTitleLabel);
        View.addComponentsToPanel(insideTopPanelRight, searchField);
    }

    @Override
    public void addActionListeners() {
        presenter.setActionListeners();
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public TableRowSorter<TableModel> getSorter() {
        return sorter;
    }
}
