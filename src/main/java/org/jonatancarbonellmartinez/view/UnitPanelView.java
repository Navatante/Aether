package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.presenter.UnitPanelPresenter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;


public class UnitPanelView extends JPanel implements View, PanelView {
    private UnitPanelPresenter presenter;
    private JTable unitTable;

    private DefaultTableModel tableModel;
    private TableRowSorter<TableModel> sorter;

    private JTextField searchField;
    private JLabel unitTitleLabel;
    private JPanel topPanel, insideTopPanelLeft, insideTopPanelRight;
    JScrollPane scrollPane;

    public UnitPanelView() {
        this.presenter = new UnitPanelPresenter(this);
        this.initializeUI();
        updatePanel();
        setVisible(true);
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
    }

    @Override
    public void createComponents() {

        tableModel = new DefaultTableModel(new String[] {"ID", "Nombre abrev.", "Nombre", "Organismo abrev.", "Organismo", "Autoridad", "Autoridad Abrev."}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Making table non-editable
            }
            // Define "ID" and "Orden" as Integer; other columns default to String (in order to sort properly when headers are clicked)
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Integer.class;
                }
                return String.class;
            }
        };

        unitTable = new JTable(tableModel);

        // Left-align the "ID" column
        DefaultTableCellRenderer leftAlignRenderer = new DefaultTableCellRenderer();
        leftAlignRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        unitTable.getColumnModel().getColumn(0).setCellRenderer(leftAlignRenderer);

        scrollPane = new JScrollPane(unitTable);

        searchField = View.createTextField("Buscar");
        sorter = new TableRowSorter<>(tableModel);
        unitTitleLabel = new JLabel("Unidades");

    }

    @Override
    public void configurePanels() {
        topPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
    }

    @Override
    public void configureComponents() {
        unitTitleLabel.setFont(PanelView.ENTITY_TITLE_LABEL_FONT);
        searchField.setPreferredSize(new Dimension(200, 25));
        unitTable.setRowSorter(sorter);
        unitTable.setCellSelectionEnabled(true);
        unitTable.setToolTipText("Ctrl+C para copiar");
    }

    @Override
    public void assemblePanels() {
        this.add(topPanel, BorderLayout.NORTH);
        topPanel.add(insideTopPanelLeft, BorderLayout.WEST);
        topPanel.add(insideTopPanelRight, BorderLayout.EAST);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void assembleComponents() {
        View.addComponentsToPanel(insideTopPanelLeft, unitTitleLabel);
        View.addComponentsToPanel(insideTopPanelRight, searchField);
    }

    @Override
    public void addActionListeners() {
        presenter.setActionListeners();
    }

    @Override
    public void updatePanel() {
        presenter.loadAllUnits();
    }

    // Getters
    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public TableRowSorter<TableModel> getSorter() {
        return sorter;
    }
}
