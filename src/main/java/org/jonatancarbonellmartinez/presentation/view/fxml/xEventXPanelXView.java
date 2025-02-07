package org.jonatancarbonellmartinez.presentation.view.fxml;

import org.jonatancarbonellmartinez.presentation.viewmodel.xEventXPanelXPresenter;
import org.jonatancarbonellmartinez.xcomponents.JonJTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;

public class xEventXPanelXView extends JPanel implements xView, xPanelView {
    private xEventXPanelXPresenter presenter;
    private JTable eventTable;

    private DefaultTableModel tableModel;
    private TableRowSorter<TableModel> sorter;

    JLabel eventTitleLabel;

    private JonJTextField searchField;
    private JPanel topPanel, insideTopPanelLeft, insideTopPanelRight;
    JScrollPane scrollPane;

    public xEventXPanelXView() {
        this.presenter = new xEventXPanelXPresenter(this);
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
    public void configurePanels() {
        topPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
    }

    @Override
    public void assemblePanels() {
        this.add(topPanel, BorderLayout.NORTH);
        topPanel.add(insideTopPanelLeft, BorderLayout.WEST);
        topPanel.add(insideTopPanelRight, BorderLayout.EAST);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void createComponents() {
        tableModel = new DefaultTableModel(new String[] {"ID", "Nombre", "Lugar"}, 0) {
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

        eventTable = new JTable(tableModel);

        // Left-align the "ID" column
        DefaultTableCellRenderer leftAlignRenderer = new DefaultTableCellRenderer();
        leftAlignRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        eventTable.getColumnModel().getColumn(0).setCellRenderer(leftAlignRenderer);

        scrollPane = new JScrollPane(eventTable);

        searchField = new JonJTextField(xView.INPUT_FONT, xView.PLACEHOLDER_FONT,"Buscar", xView.DYNAMIC_FINAL_SEARCH, xView.DYNAMIC_FINAL_SEARCH);
        sorter = new TableRowSorter<>(tableModel);

        eventTitleLabel = new JLabel("Eventos");
    }

    @Override
    public void configureComponents() {

        eventTitleLabel.setFont(xPanelView.ENTITY_TITLE_LABEL_FONT);
        searchField.setPreferredSize(new Dimension(200, 25));
        eventTable.setRowSorter(sorter);
        eventTable.setCellSelectionEnabled(true);
        eventTable.setToolTipText("Ctrl+C para copiar");
    }

    @Override
    public void assembleComponents() {
        xView.addComponentsToPanel(insideTopPanelLeft, eventTitleLabel);
        xView.addComponentsToPanel(insideTopPanelRight, searchField);
    }

    @Override
    public void addActionListeners() {
        presenter.setActionListeners();
    }

    @Override
    public void updatePanel() {
        presenter.loadAllEvents();
    }

    // Getters and Setters
    public JTextField getSearchField() {
        return searchField;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public TableRowSorter<TableModel> getSorter() {
        return sorter;
    }
}
