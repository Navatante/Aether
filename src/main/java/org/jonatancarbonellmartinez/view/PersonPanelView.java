package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.model.dao.PersonDAO;
import org.jonatancarbonellmartinez.presenter.PersonPanelPresenter;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
    private JTextField searchField;
    private JPanel topPanel, insideTopPanel;
    JScrollPane scrollPane;

    public PersonPanelView(PersonDAO personDAO) {
        this.presenter = new PersonPanelPresenter(this, personDAO);
        initializeUI();
    }

    @Override
    public void initializeUI() {
        setupUIProperties();
        initializeComponents();
        createPanels();
        addActionListeners();
        configureComponents();
        assembleUI();
        updatePanel();
        setVisible(true);
    }

    @Override
    public void setupUIProperties() {
        setLayout(new BorderLayout());
    }

    @Override
    public void createPanels() {
        topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(5, 25, 5, 25));
        add(topPanel, BorderLayout.NORTH);

        insideTopPanel = new JPanel();
        topPanel.add(insideTopPanel, BorderLayout.WEST);

        scrollPane = new JScrollPane(personTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void initializeComponents() {

        tableModel = new DefaultTableModel(new String[] {"ID", "Código", "Empleo", "Nombre", "Apellido 1", "Apellido 2", "Teléfono", "DNI", "División", "Rol", "Situación", "Orden"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Making table non-editable
            }
        };

        personTable = new JTable(tableModel);
        searchField = new JTextField();
        sorter = new TableRowSorter<>(tableModel);
        togglePersonState = new JRadioButton("Activos");
    }

    @Override
    public void addActionListeners() {
        presenter.setActionListeners();
    }

    @Override
    public void configureComponents() {
        searchField.setPreferredSize(new Dimension(200, 25));
        personTable.setRowSorter(sorter);
        personTable.setCellSelectionEnabled(true);
        personTable.setToolTipText("Ctrl+C para copiar");
        togglePersonState.setSelected(true); // Initially selected (Active state)

    }

    @Override
    public void assembleUI() {
    View.addComponentsToPanel(insideTopPanel, new JLabel("Buscar"), searchField);
    topPanel.add(togglePersonState, BorderLayout.EAST);
    }

    @Override
    public void updatePanel() {
        presenter.loadAllPersons();
    }

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
