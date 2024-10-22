package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.model.dao.PersonDAO;
import org.jonatancarbonellmartinez.presenter.PersonCardPresenter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
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

        // Set up the table model
        String[] columnNames = {
                "Orden", "Código", "Empleo",
                "Nombre", "Apellido 1", "Apellido 2",
                "Teléfono","DNI", "División", "Rol", "Situación"
        };
        tableModel = new DefaultTableModel(columnNames, 0);
        personTable = new JTable(tableModel);

        // Add the table to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(personTable);
        add(scrollPane, BorderLayout.CENTER);

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
