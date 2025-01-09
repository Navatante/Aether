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
    private JTable lastFlightsTable, pilotHoursDetailTable, dvHoursDetailTable;

    private DefaultTableModel lastFlightsTableModel;

    private DefaultTableModel pilotHoursDetailTableModel, dvHoursDetailTableModel;
    private TableRowSorter<TableModel> sorter;

    JLabel lastFlightsTitleLabel;
    JLabel pilotHoursDetailTitleLabel;
    JLabel flightDetailsTitleLabel;
    JLabel dvHoursDetailTitleLabel;

    private JTextField searchField;
    private JPanel topPanel, insideTopPanelLeft, insideTopPanelRight;
    private JPanel middlePanel;
    private JPanel bottomPanel, bottomPanelTop, bottomPanelCenter, bottomPanelCenterTop, bottomPanelCenterBottom;
    private JPanel pilotHoursDetailPanel, pilotHoursDetailPanelTop, pilotHoursDetailPanelCenter;
    private JPanel dvHoursDetailPanel, dvHoursDetailPanelTop, dvHoursDetailPanelCenter;
    private JScrollPane lastFlightsScrollPane, pilotHoursDetailScrollPane, dvHoursDetailScrollPane;

    public RecentFlightsPanelView() {
        this.presenter = new RecentFlightsPanelPresenter(this);
        this.initializeUI();
        updatePanel();
        setVisible(true);
    }

    @Override
    public void updatePanel() {
        presenter.loadLatest50Flights(lastFlightsTableModel);
        presenter.updateSelectedVueloId();
        presenter.loadPilotHoursDetails(pilotHoursDetailTableModel, presenter.getSelectedVueloId());
        flightDetailsTitleLabel.setText("Detalles del vuelo " + presenter.getSelectedVueloId());
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
        middlePanel = new JPanel(new BorderLayout()); // Here is the lastFlightsTable
        bottomPanel = new JPanel(new BorderLayout()); // Here are all the details
        bottomPanelTop = new JPanel(new BorderLayout());
        bottomPanelCenter = new JPanel(new BorderLayout());
        bottomPanelCenterTop = new JPanel(new GridLayout(1,2,10,5)); // TODO AQUI VAN HORAS VUELO PITLOS Y HORAS VUELO DVs
        bottomPanelCenterBottom = new JPanel(new GridLayout()); // TODO AQUI VAN TOMAS APPS INST SAR PROYECTILES CUPO Y PASAJEROS
        pilotHoursDetailPanel = new JPanel(new BorderLayout());
        pilotHoursDetailPanelTop = new JPanel();
        pilotHoursDetailPanelCenter = new JPanel(new BorderLayout());
        dvHoursDetailPanel = new JPanel(new BorderLayout());
        dvHoursDetailPanelTop = new JPanel();
        dvHoursDetailPanelCenter = new JPanel(new BorderLayout());
    }

    @Override
    public void createComponents() {
        lastFlightsTitleLabel = new JLabel("Últimos vuelos");
        flightDetailsTitleLabel = new JLabel("Detalles del vuelo " + presenter.getSelectedVueloId());

        // Last Flights Table
        lastFlightsTableModel = new DefaultTableModel(new String[] {"Vuelo ID", "Fecha", "Helicóptero", "Evento", "HAC", "Horas"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Making table non-editable
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Integer.class; // Vuelo ID as Integer
                } else if (columnIndex == 5) {
                    return Double.class; // Horas as Double
                }
                return String.class; // Other columns as String
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
        pilotHoursDetailTableModel = new DefaultTableModel(new String[] {"Piloto", "Dia", "Noche", "GVN", "Instrumental", "HMDS", "IP", "Form. Dia", "Form. GVN"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Making table non-editable
            }
        };

        pilotHoursDetailTable = new JTable(pilotHoursDetailTableModel);

        // Center-align all columns
        for (int i = 0; i < pilotHoursDetailTable.getColumnModel().getColumnCount(); i++) {
            pilotHoursDetailTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        pilotHoursDetailScrollPane = new JScrollPane(pilotHoursDetailTable);

        // Apply the custom renderer to the lastFlightsTable
        for (int i = 0; i < pilotHoursDetailTable.getColumnCount(); i++) {
            pilotHoursDetailTable.getColumnModel().getColumn(i).setCellRenderer(new ZeroValueCellRenderer());
        }

        // DV Hours Details Table
        dvHoursDetailTitleLabel = new JLabel("Horas Dotaciones");
        dvHoursDetailTableModel = new DefaultTableModel(new String[] {"Dotación", "Dia", "Noche", "GVN", "WT"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Making table non-editable
            }
        };

        dvHoursDetailTable = new JTable(dvHoursDetailTableModel);

        // Center-align all columns
        for (int i = 0; i < dvHoursDetailTable.getColumnModel().getColumnCount(); i++) {
            dvHoursDetailTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        dvHoursDetailScrollPane = new JScrollPane(dvHoursDetailTable);
    }

    // INNER CLASS Custom cell renderer to gray out 0.0 values
    public class ZeroValueCellRenderer extends DefaultTableCellRenderer {
        public ZeroValueCellRenderer() {
            setHorizontalAlignment(SwingConstants.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // If the cell is selected, keep the default color for selected cells
            if (isSelected) {
                cell.setForeground(Color.LIGHT_GRAY); // Text color for selected cells (typically white)
            } else {
                // If the value is 0.0, set the text color to gray, otherwise set it to black
                if (value instanceof Double && ((Double) value) == 0.0) {
                    cell.setForeground(View.tableBackgroundColor); // Gray color for 0.0 values
                } else {
                    cell.setForeground(Color.LIGHT_GRAY); // Default color for other values
                }
            }

            return cell;
        }
    }

    @Override
    public void configurePanels() {
        topPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        bottomPanelTop.setBorder(new EmptyBorder(5, 10, 5, 10));
        lastFlightsScrollPane.setPreferredSize(new Dimension(0, 150));
        //pilotHoursDetailScrollPane.setPreferredSize(new Dimension(900, 100));
    }

    @Override
    public void configureComponents() {
        lastFlightsTitleLabel.setFont(PanelView.ENTITY_TITLE_LABEL_FONT);
        flightDetailsTitleLabel.setFont(PanelView.ENTITY_TITLE_LABEL_FONT);
        pilotHoursDetailTitleLabel.setFont(PanelView.ENTITY_SUBTITLE_LABEL_FONT);
        dvHoursDetailTitleLabel.setFont(PanelView.ENTITY_SUBTITLE_LABEL_FONT);

        searchField.setPreferredSize(new Dimension(200, 25));
        lastFlightsTable.setRowSorter(sorter);

        pilotHoursDetailTable.setCellSelectionEnabled(true);
    }

    @Override
    public void assemblePanels() {
        // Top Panel
        this.add(topPanel, BorderLayout.NORTH);
        topPanel.add(insideTopPanelLeft, BorderLayout.WEST);
        topPanel.add(insideTopPanelRight, BorderLayout.EAST);

        // Middle Panel
        this.add(middlePanel, BorderLayout.CENTER);
        middlePanel.add(lastFlightsScrollPane, BorderLayout.NORTH);

        // Bottom Panel
        this.add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(bottomPanelTop, BorderLayout.NORTH);
        bottomPanel.add(bottomPanelCenter, BorderLayout.CENTER);
        bottomPanelCenter.add(bottomPanelCenterTop, BorderLayout.NORTH);
        bottomPanelCenter.add(bottomPanelCenterBottom, BorderLayout.SOUTH);
        bottomPanelCenterTop.add(pilotHoursDetailPanel);
        bottomPanelCenterTop.add(dvHoursDetailPanel);

        pilotHoursDetailPanel.add(pilotHoursDetailPanelTop, BorderLayout.NORTH);
        pilotHoursDetailPanel.add(pilotHoursDetailPanelCenter, BorderLayout.CENTER);
        dvHoursDetailPanel.add(dvHoursDetailPanelTop, BorderLayout.NORTH);
        dvHoursDetailPanel.add(dvHoursDetailPanelCenter, BorderLayout.CENTER);

    }

    @Override
    public void assembleComponents() {
        View.addComponentsToPanel(insideTopPanelLeft, lastFlightsTitleLabel);
        View.addComponentsToPanel(insideTopPanelRight, searchField);
        View.addComponentsToPanel(bottomPanelTop, flightDetailsTitleLabel);
        View.addComponentsToPanel(pilotHoursDetailPanelTop, pilotHoursDetailTitleLabel);
        View.addComponentsToPanel(pilotHoursDetailPanelCenter, pilotHoursDetailScrollPane);
        View.addComponentsToPanel(dvHoursDetailPanelTop, dvHoursDetailTitleLabel);
        View.addComponentsToPanel(dvHoursDetailPanelCenter, dvHoursDetailScrollPane);
    }

    @Override
    public void addActionListeners() {
        presenter.setActionListeners();
    }

    // Getters and Setters
    public JTextField getSearchField() {
        return searchField;
    }

    public TableRowSorter<TableModel> getSorter() {
        return sorter;
    }

    public JTable getLastFlightsTable() {
        return lastFlightsTable;
    }

    public DefaultTableModel getLastFlightsTableModel() {
        return lastFlightsTableModel;
    }

    public DefaultTableModel getPilotHoursDetailTableModel() {
        return pilotHoursDetailTableModel;
    }

    public JLabel getFlightDetailsTitleLabel() {
        return flightDetailsTitleLabel;
    }
}
