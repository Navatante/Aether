package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.model.dao.PersonDAO;
import org.jonatancarbonellmartinez.presenter.PersonCardPresenter;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class PersonCardView extends JPanel {
    private PersonCardPresenter presenter;
    private JTable personTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<TableModel> sorter;
    private JRadioButton togglePersonState;
    private JTextField searchField;

    public PersonCardView(PersonDAO personDAO) {
        this.presenter = new PersonCardPresenter(this, personDAO);
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 25));
        addSearchFieldListener(); // Externalize the listener setup

        String[] columnNames = {
                "ID", "Código", "Empleo",
                "Nombre", "Apellido 1", "Apellido 2",
                "Teléfono", "DNI", "División", "Rol", "Situación", "Orden"
        };

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Making table non-editable
            }
        };

        personTable = new JTable(tableModel);
        sorter = new TableRowSorter<>(tableModel);
        personTable.setRowSorter(sorter);
        personTable.setCellSelectionEnabled(true);
        personTable.setToolTipText("Ctrl+C para copiar");

        togglePersonState = new JRadioButton("Activos");
        togglePersonState.setSelected(true); // Initially selected (Active state)
        togglePersonState.addActionListener(e -> presenter.onPersonStateChanged(togglePersonState.isSelected()));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(5, 25, 5, 25));
        add(topPanel, BorderLayout.NORTH);

        JPanel insideTopPanel = new JPanel();
        topPanel.add(insideTopPanel, BorderLayout.WEST);
        insideTopPanel.add(new JLabel("Buscar"));
        insideTopPanel.add(searchField);

        topPanel.add(togglePersonState, BorderLayout.EAST);

        JScrollPane scrollPane = new JScrollPane(personTable);
        add(scrollPane, BorderLayout.CENTER);

        showView(); // Load data on view initialization
    }

    private void addSearchFieldListener() {
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                presenter.onSearchTextChanged(searchField.getText()); // Forward search text to presenter
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                presenter.onSearchTextChanged(searchField.getText()); // Forward search text to presenter
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                presenter.onSearchTextChanged(searchField.getText()); // Forward search text to presenter
            }
        });
    }

    public void showView() {
        presenter.loadAllPersons();  // Load data through presenter
        setVisible(true);
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

}
