package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.viewmodel.PersonPanelPresenter;
import org.jonatancarbonellmartinez.utilities.JonJTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class PersonPanelView extends JPanel implements View, PanelView {
    private PersonPanelPresenter presenter;
    private JTable personTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<TableModel> sorter;

    private JRadioButton togglePersonState;
    private JonJTextField searchField;
    private JLabel personTitleLabel;
    private JPanel topPanel, insideTopPanelLeft, insideTopPanelRight;
    JScrollPane scrollPane;

    public PersonPanelView() {
        this.presenter = new PersonPanelPresenter(this);
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

        tableModel = new DefaultTableModel(new String[] {"ID", "Código", "Empleo", "Nombre", "Apellido 1", "Apellido 2", "Teléfono", "DNI", "División", "Rol", "Situación", "Orden"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Making table non-editable
            }
            // Define "ID" and "Orden" as Integer; other columns default to String (in order to sort properly when headers are clicked)
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0 || columnIndex == 11) {
                    return Integer.class;
                }
                return String.class;
            }
        };

        personTable = new JTable(tableModel);

        // Left-align the "ID" column
        DefaultTableCellRenderer leftAlignRenderer = new DefaultTableCellRenderer();
        leftAlignRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        personTable.getColumnModel().getColumn(0).setCellRenderer(leftAlignRenderer);

        scrollPane = new JScrollPane(personTable);

        searchField = new JonJTextField(View.INPUT_FONT, View.PLACEHOLDER_FONT,"Buscar", View.DYNAMIC_FINAL_SEARCH, View.DYNAMIC_FINAL_SEARCH);

        sorter = new TableRowSorter<>(tableModel);
        togglePersonState = new JRadioButton("Activos");
        personTitleLabel = new JLabel("Personal");
    }

    @Override
    public void configurePanels() {
        topPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
    }

    @Override
    public void configureComponents() {
        personTitleLabel.setFont(PanelView.ENTITY_TITLE_LABEL_FONT);
        searchField.setPreferredSize(new Dimension(200, 25));
        personTable.setRowSorter(sorter);
        personTable.setCellSelectionEnabled(true);
        personTable.setToolTipText("Ctrl+C para copiar");
        togglePersonState.setSelected(true); // Initially selected (Active state)
        togglePersonState.setBorder(new EmptyBorder(0, 25, 0, 0));

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
    View.addComponentsToPanel(insideTopPanelLeft, personTitleLabel);
    View.addComponentsToPanel(insideTopPanelRight, searchField, togglePersonState);

    }

    @Override
    public void addActionListeners() {
        presenter.setActionListeners();
    }

    @Override
    public void updatePanel() {
        presenter.loadAllPersons();
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

    public JRadioButton getTogglePersonState() {
        return togglePersonState;
    }
}
