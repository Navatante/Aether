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
    private JTable lastFlightsTable, crewHoursDetailTable, sessionDetailTable, landingTable, instrumentalAppsTable, sarTable, projectilesTable, cupoTable, passengersTable;

    private DefaultTableModel lastFlightsTableModel;
    private DefaultTableModel crewHoursDetailTableModel;
    private DefaultTableModel sessionDetailTableModel;
    private DefaultTableModel landingTableModel;
    private DefaultTableModel instrumentalAppsTableModel;
    private DefaultTableModel sarAppsTableModel;
    private DefaultTableModel projectilesTableModel;
    private DefaultTableModel cupoTableModel;
    private DefaultTableModel passengersTableModel;

    private TableRowSorter<TableModel> sorter;

    JLabel lastFlightsTitleLabel;
    JLabel crewHoursDetailTitleLabel;
    JLabel sessionDetailTitleLabel;
    JLabel flightDetailsTitleLabel;
    JLabel landingTitleLabel;
    JLabel instrumentalAppsTitleLabel;
    JLabel sarTitleLabel;
    JLabel projectilesTitleLabel;
    JLabel cupoTitleLabel;
    JLabel passengersTitleLabel;

    private JTextField searchField;

    private JPanel topPanel;
    private JPanel centerPanel;
    private JPanel insideTopPanelTop, insideTopPanelTopLeft, insideTopPanelTopRight;
    private JPanel insideTopPanelBottom;
    private JPanel centerPanelTop, centerPanelCenter, bottomPanelCenterTop, bottomPanelCenterBottom;
    private JPanel crewHoursDetailPanel, crewHoursDetailPanelTop, crewHoursDetailPanelBottom;
    private JPanel sessionDetailPanel, sessionDetailPanelTop, sessionDetailPanelBottom;
    private JPanel landingPanel, landingPanelTop, landingPanelBottom;
    private JPanel instrumentalAppsPanel, instrumentalAppsPanelTop, instrumentalAppsPanelBottom;
    private JPanel sarPanel, sarPanelTop, sarPanelBottom;
    private JPanel projectilesPanel, projectilesPanelTop, projectilesPanelBottom;
    private JPanel cupoPanel, cupoPanelTop, cupoPanelBottom;
    private JPanel passengersPanel, passengersPanelTop, passengersPanelBottom;

    private JScrollPane lastFlightsScrollPane, crewHoursDetailScrollPane, sessionDetailScrollPane, landingScrollPane, instrumentalAppsScrollPane, sarScrollPane, projectilesScrollPane, cupoScrollPane, passengersScrollPane;

    private JPopupMenu deleteFlightPopupMenu;

    private JMenuItem deleteFlightMenuItem;

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
        presenter.loadCrewHoursDetails(crewHoursDetailTableModel, presenter.getSelectedVueloId());
        flightDetailsTitleLabel.setText("Detalles del vuelo " + presenter.getSelectedVueloId());
        presenter.loadSessionDetails(sessionDetailTableModel, presenter.getSelectedVueloId());
        presenter.loadLandingDetails(landingTableModel, presenter.getSelectedVueloId());
        presenter.loadInstrumentalAppsDetails(instrumentalAppsTableModel, presenter.getSelectedVueloId());
        presenter.loadSarAppsDetails(sarAppsTableModel, presenter.getSelectedVueloId());
        presenter.loadProjectilesDetails(projectilesTableModel, presenter.getSelectedVueloId());
        presenter.loadCupoDetails(cupoTableModel, presenter.getSelectedVueloId());
        presenter.loadPassengersDetails(passengersTableModel, presenter.getSelectedVueloId());
    }

    @Override
    public void setupUIProperties() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(0, 5, 5, 5));
    }

    @Override
    public void createPanels() {
        topPanel = new JPanel(new BorderLayout());
        insideTopPanelTop = new JPanel(new BorderLayout());
        insideTopPanelTopLeft = new JPanel();
        insideTopPanelTopRight = new JPanel();
        insideTopPanelBottom = new JPanel(new BorderLayout()); // Here is the lastFlightsTable
        centerPanel = new JPanel(new BorderLayout()); // Here are all the details
        centerPanelTop = new JPanel(); // si quieres centrar el texto Detalles del vuelo X. pon: new JPanel(new BorderLayout()).
        centerPanelCenter = new JPanel(new GridLayout(2,1));

        bottomPanelCenterTop = new JPanel(new GridLayout(2,1,10,5)); // Aqui van HORAS y debajo  PAPELETAS
        // Hour panels
        crewHoursDetailPanel = new JPanel(new BorderLayout());
        crewHoursDetailPanelTop = new JPanel();
        crewHoursDetailPanelBottom = new JPanel(new BorderLayout());
        // Session panels
        sessionDetailPanel = new JPanel(new BorderLayout());
        sessionDetailPanelTop = new JPanel();
        sessionDetailPanelBottom = new JPanel(new BorderLayout());

        bottomPanelCenterBottom = new JPanel(new GridLayout(2,3,34,5)); // Aqui van TOMAS APPS INST SAR PROYECTILES CUPO y PASAJEROS
        // Landing panels
        landingPanel = new JPanel(new BorderLayout());
        landingPanelTop = new JPanel();
        landingPanelBottom = new JPanel(new BorderLayout());
        // Instrumental Apps panels
        instrumentalAppsPanel = new JPanel(new BorderLayout());
        instrumentalAppsPanelTop = new JPanel();
        instrumentalAppsPanelBottom = new JPanel(new BorderLayout());
        // SAR panels
        sarPanel = new JPanel(new BorderLayout());
        sarPanelTop = new JPanel();
        sarPanelBottom = new JPanel(new BorderLayout());
        // Projectiles panels
        projectilesPanel = new JPanel(new BorderLayout());
        projectilesPanelTop = new JPanel();
        projectilesPanelBottom = new JPanel(new BorderLayout());
        // Cupo panels
        cupoPanel = new JPanel(new BorderLayout());
        cupoPanelTop = new JPanel();
        cupoPanelBottom = new JPanel(new BorderLayout());
        // Passengers panels
        passengersPanel = new JPanel(new BorderLayout());
        passengersPanelTop = new JPanel();
        passengersPanelBottom = new JPanel(new BorderLayout());

    }

    @Override
    public void createComponents() {
        // Render to hide 0.0 values
        ZeroValueCellRenderer zeroValuesRenderer = new ZeroValueCellRenderer();
        // Render to center-align all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // LAST FLIGHTS TABLE
        lastFlightsTitleLabel = new JLabel("Últimos vuelos");
        lastFlightsTableModel = createLastFlightsTableModel();
        lastFlightsTable = new JTable(lastFlightsTableModel);
        // Apply render to center columns
        for (int i = 0; i < lastFlightsTable.getColumnModel().getColumnCount(); i++) {
            lastFlightsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        lastFlightsScrollPane = new JScrollPane(lastFlightsTable);
        searchField = View.createTextField("Buscar");
        sorter = new TableRowSorter<>(lastFlightsTableModel);


        flightDetailsTitleLabel = new JLabel("Detalles del vuelo " + presenter.getSelectedVueloId());

        // HOURS TABLE
        crewHoursDetailTitleLabel = new JLabel("Horas");
        crewHoursDetailTableModel = createGenericTableModel((new String[] {"Crew", "Rol", "Dia", "Noche", "GVN", "Instrumental", "HMDS", "IP", "Formación Dia", "Formación GVN", "Winch Trim"}));
        crewHoursDetailTable = new JTable(crewHoursDetailTableModel);
        // Apply render to center columns
        for (int i = 0; i < crewHoursDetailTable.getColumnModel().getColumnCount(); i++) {
            crewHoursDetailTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        crewHoursDetailScrollPane = new JScrollPane(crewHoursDetailTable);

        // Apply the custom renderer to hide 0.0 values
        for (int i = 0; i < crewHoursDetailTable.getColumnCount(); i++) {
            crewHoursDetailTable.getColumnModel().getColumn(i).setCellRenderer(zeroValuesRenderer);
        }

        // SESSION TABLE
        sessionDetailTitleLabel = new JLabel("Papeletas");
        sessionDetailTableModel = createGenericTableModel(new String[] {"Crew", "Rol", "Papeleta", "Descripción", "Plan", "Bloque"});
        sessionDetailTable = new JTable(sessionDetailTableModel);
        // Apply render to center columns
        for (int i = 0; i < sessionDetailTable.getColumnModel().getColumnCount(); i++) {
            sessionDetailTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        sessionDetailScrollPane = new JScrollPane(sessionDetailTable);
        // Le poonemos un ancho minimo a la columna de la descripcion
        sessionDetailTable.getColumnModel().getColumn(3).setMinWidth(400);

        // LANDING TABLE
        landingTitleLabel = new JLabel("Tomas");
        landingTableModel = createGenericTableModel(new String[] {"Piloto", "Lugar", "Periodo", "Cantidad"});
        landingTable = new JTable(landingTableModel);
        // Apply render to center columns
        for (int i = 0; i < landingTable.getColumnModel().getColumnCount(); i++) {
            landingTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        landingScrollPane = new JScrollPane(landingTable);

        // INSTRUMENTAL APPS TABLE
        instrumentalAppsTitleLabel = new JLabel("Aproximaciones Instrumentales");
        instrumentalAppsTableModel = createGenericTableModel(new String[] {"Piloto", "Tipo", "Cantidad"});
        instrumentalAppsTable = new JTable(instrumentalAppsTableModel);
        // Apply render to center columns
        for (int i = 0; i < instrumentalAppsTable.getColumnModel().getColumnCount(); i++) {
            instrumentalAppsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        instrumentalAppsScrollPane = new JScrollPane(instrumentalAppsTable);

        // SAR TABLE
        sarTitleLabel = new JLabel("SAR");
        sarAppsTableModel = createGenericTableModel(new String[] {"Piloto", "Tipo", "Cantidad"});
        sarTable = new JTable(sarAppsTableModel);
        // Apply render to center columns
        for (int i = 0; i < sarTable.getColumnModel().getColumnCount(); i++) {
            sarTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        sarScrollPane = new JScrollPane(sarTable);

        // PROJECTILES TABLE
        projectilesTitleLabel = new JLabel("Proyectiles");
        projectilesTableModel = createGenericTableModel(new String[] {"Dotación", "Arma", "Cantidad"});
        projectilesTable = new JTable(projectilesTableModel);
        // Apply render to center columns
        for (int i = 0; i < projectilesTable.getColumnModel().getColumnCount(); i++) {
            projectilesTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        projectilesScrollPane = new JScrollPane(projectilesTable);

        // CUPO TABLE
        cupoTitleLabel = new JLabel("Cupo");
        cupoTableModel = createGenericTableModel(new String[] {"Autoridad", "Horas"});
        cupoTable = new JTable(cupoTableModel);
        // Apply render to center columns
        for (int i = 0; i < cupoTable.getColumnModel().getColumnCount(); i++) {
            cupoTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        cupoScrollPane = new JScrollPane(cupoTable);

        // PASSENGERS TABLE
        passengersTitleLabel = new JLabel("Pasajeros");
        passengersTableModel = createGenericTableModel(new String[] {"Tipo", "Cantidad", "Ruta"});
        passengersTable = new JTable(passengersTableModel);
        // Apply render to center columns
        for (int i = 0; i < passengersTable.getColumnModel().getColumnCount(); i++) {
            passengersTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        passengersScrollPane = new JScrollPane(passengersTable);

        // DELETE FLIGHT POPUP MENU
        deleteFlightPopupMenu = new JPopupMenu();
        deleteFlightMenuItem = new JMenuItem("Eliminar vuelo");
    }

    @Override
    public void configurePanels() {

        insideTopPanelTop.setBorder(new EmptyBorder(5, 10, 5, 10));
        insideTopPanelTop.setPreferredSize(new Dimension(0, 45));

        centerPanelTop.setBorder(new EmptyBorder(5, 10, 0, 10)); // Panel: Detalles del vuelo X
        lastFlightsScrollPane.setPreferredSize(new Dimension(0, 110));

        bottomPanelCenterTop.setBorder(new EmptyBorder(0, 0, 5, 0)); // Panel: Horas y Papeletas

    }

    @Override
    public void configureComponents() {
        View.setFontToLabels(PanelView.ENTITY_TITLE_LABEL_FONT, lastFlightsTitleLabel, flightDetailsTitleLabel);
        View.setFontToLabels(PanelView.ENTITY_SUBTITLE_LABEL_FONT, crewHoursDetailTitleLabel, sessionDetailTitleLabel, landingTitleLabel, instrumentalAppsTitleLabel, sarTitleLabel, projectilesTitleLabel, cupoTitleLabel, passengersTitleLabel);

        searchField.setPreferredSize(new Dimension(200, 25));
        lastFlightsTable.setRowSorter(sorter);

        crewHoursDetailTable.setCellSelectionEnabled(true);
        sessionDetailTable.setCellSelectionEnabled(true);
        landingTable.setCellSelectionEnabled(true);
        instrumentalAppsTable.setCellSelectionEnabled(true);
        sarTable.setCellSelectionEnabled(true);
        projectilesTable.setCellSelectionEnabled(true);
        cupoTable.setCellSelectionEnabled(true);
        passengersTable.setCellSelectionEnabled(true);
    }

    @Override
    public void assemblePanels() {
        // Top Panel
        this.add(topPanel,BorderLayout.NORTH);
        topPanel.add(insideTopPanelTop, BorderLayout.NORTH);
        insideTopPanelTop.add(insideTopPanelTopLeft, BorderLayout.WEST);
        insideTopPanelTop.add(insideTopPanelTopRight, BorderLayout.EAST);
        topPanel.add(insideTopPanelBottom, BorderLayout.SOUTH);
        insideTopPanelBottom.add(lastFlightsScrollPane, BorderLayout.NORTH);

        // Bottom Panel
        this.add(centerPanel, BorderLayout.CENTER);
        centerPanel.add(centerPanelTop, BorderLayout.NORTH); // Este es solo para el titulo
        centerPanel.add(centerPanelCenter, BorderLayout.CENTER); // Aqui van todos los detalles
        centerPanelCenter.add(bottomPanelCenterTop); // Aqui van horas y sesiones
        centerPanelCenter.add(bottomPanelCenterBottom); // Aqui el resto
        View.addComponentsToPanel(bottomPanelCenterTop, crewHoursDetailPanel, sessionDetailPanel);
        View.addComponentsToPanel(bottomPanelCenterBottom, landingPanel, instrumentalAppsPanel, sarPanel, projectilesPanel, cupoPanel, passengersPanel);

        crewHoursDetailPanel.add(crewHoursDetailPanelTop, BorderLayout.NORTH);
        crewHoursDetailPanel.add(crewHoursDetailPanelBottom, BorderLayout.CENTER);

        sessionDetailPanel.add(sessionDetailPanelTop, BorderLayout.NORTH);
        sessionDetailPanel.add(sessionDetailPanelBottom, BorderLayout.CENTER);

        landingPanel.add(landingPanelTop, BorderLayout.NORTH);
        landingPanel.add(landingPanelBottom, BorderLayout.CENTER);

        instrumentalAppsPanel.add(instrumentalAppsPanelTop, BorderLayout.NORTH);
        instrumentalAppsPanel.add(instrumentalAppsPanelBottom, BorderLayout.CENTER);

        sarPanel.add(sarPanelTop, BorderLayout.NORTH);
        sarPanel.add(sarPanelBottom, BorderLayout.CENTER);

        projectilesPanel.add(projectilesPanelTop, BorderLayout.NORTH);
        projectilesPanel.add(projectilesPanelBottom, BorderLayout.CENTER);

        cupoPanel.add(cupoPanelTop, BorderLayout.NORTH);
        cupoPanel.add(cupoPanelBottom, BorderLayout.CENTER);

        passengersPanel.add(passengersPanelTop, BorderLayout.NORTH);
        passengersPanel.add(passengersPanelBottom, BorderLayout.CENTER);

        //PopUpMenu
        deleteFlightPopupMenu.add(deleteFlightMenuItem);
    }

    @Override
    public void assembleComponents() {
        View.addComponentsToPanel(insideTopPanelTopLeft, lastFlightsTitleLabel);
        View.addComponentsToPanel(insideTopPanelTopRight, searchField);
        View.addComponentsToPanel(centerPanelTop, flightDetailsTitleLabel);
        View.addComponentsToPanel(crewHoursDetailPanelTop, crewHoursDetailTitleLabel);
        View.addComponentsToPanel(crewHoursDetailPanelBottom, crewHoursDetailScrollPane);
        View.addComponentsToPanel(sessionDetailPanelTop, sessionDetailTitleLabel);
        View.addComponentsToPanel(sessionDetailPanelBottom, sessionDetailScrollPane);
        View.addComponentsToPanel(landingPanelTop, landingTitleLabel);
        View.addComponentsToPanel(landingPanelBottom, landingScrollPane);
        View.addComponentsToPanel(instrumentalAppsPanelTop, instrumentalAppsTitleLabel);
        View.addComponentsToPanel(instrumentalAppsPanelBottom, instrumentalAppsScrollPane);
        View.addComponentsToPanel(sarPanelTop, sarTitleLabel);
        View.addComponentsToPanel(sarPanelBottom, sarScrollPane);
        View.addComponentsToPanel(projectilesPanelTop, projectilesTitleLabel);
        View.addComponentsToPanel(projectilesPanelBottom, projectilesScrollPane);
        View.addComponentsToPanel(cupoPanelTop, cupoTitleLabel);
        View.addComponentsToPanel(cupoPanelBottom, cupoScrollPane);
        View.addComponentsToPanel(passengersPanelTop, passengersTitleLabel);
        View.addComponentsToPanel(passengersPanelBottom, passengersScrollPane);
    }

    @Override
    public void addActionListeners() {
        presenter.setActionListeners();
    }

    // Table Models
    private DefaultTableModel createLastFlightsTableModel() {
        return new DefaultTableModel(new String[] {"ID", "Fecha", "Helicóptero", "Evento", "HAC", "Horas"}, 0) {
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
    }

    private DefaultTableModel createGenericTableModel(String[] columnNames) {
        return new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Making table non-editable
            }
        };
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

    public DefaultTableModel getCrewHoursDetailTableModel() {
        return crewHoursDetailTableModel;
    }

    public JLabel getFlightDetailsTitleLabel() {
        return flightDetailsTitleLabel;
    }

    public DefaultTableModel getSessionDetailTableModel() {
        return sessionDetailTableModel;
    }

    public DefaultTableModel getLandingTableModel() {
        return landingTableModel;
    }

    public DefaultTableModel getInstrumentalAppsTableModel() {
        return instrumentalAppsTableModel;
    }

    public DefaultTableModel getSarAppsTableModel() {
        return sarAppsTableModel;
    }

    public DefaultTableModel getProjectilesTableModel() {
        return projectilesTableModel;
    }

    public DefaultTableModel getCupoTableModel() {
        return cupoTableModel;
    }

    public DefaultTableModel getPassengersTableModel() {
        return passengersTableModel;
    }

    public JPopupMenu getDeleteFlightPopupMenu() {
        return deleteFlightPopupMenu;
    }

    public JMenuItem getDeleteFlightMenuItem() {
        return deleteFlightMenuItem;
    }
}
