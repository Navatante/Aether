package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.model.dao.PersonDAO;
import org.jonatancarbonellmartinez.presenter.PersonCardPresenter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PersonCardView extends JPanel {
    private PersonCardPresenter presenter;
    private JTable personTable;
    private DefaultTableModel tableModel;

    public PersonCardView(PersonDAO personDAO) {
        this.presenter = new PersonCardPresenter(this, personDAO);
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 25));
        JLabel label = new JLabel("Buscar");
        JRadioButton radioButton = new JRadioButton("Activos");
        radioButton.setSelected(true);


        JPanel topPanel = new JPanel();
        add(topPanel, BorderLayout.NORTH);
        topPanel.add(label, BorderLayout.WEST);
        topPanel.add(textField, BorderLayout.WEST);
        topPanel.add(radioButton, BorderLayout.EAST);


        // Set up the table model
        String[] columnNames = {
                "ID", "Código", "Empleo",
                "Nombre", "Apellido 1", "Apellido 2",
                "Teléfono", "DNI", "División", "Rol", "Situación", "Orden"
        };
        tableModel = new DefaultTableModel(columnNames, 0) {
            // Override isCellEditable to always return false (non-editable)
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        personTable = new JTable(tableModel);
        personTable.setCellSelectionEnabled(true);

        // Add the table to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(personTable);

        // Add the scrollPane to the centerPanel
        add(scrollPane, BorderLayout.CENTER);

        // Display the UI
        showView();
    }


    public void showView() {
        presenter.loadAllPersons();
        setVisible(true);
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}
