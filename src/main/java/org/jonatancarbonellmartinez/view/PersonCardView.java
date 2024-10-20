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
                "ID", "Código", "Empleo",
                "Nombre", "Primer apellido", "Segundo apellido",
                "Teléfono", "División","Orden", "Rol", "Situación"
        };
        tableModel = new DefaultTableModel(columnNames, 0);
        personTable = new JTable(tableModel);

        // Add the table to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(personTable);
        add(scrollPane, BorderLayout.CENTER);

        //setColumnWidths(personTable);

        showView();
    }

    public void showView() {
        presenter.loadAllPersons();
        setVisible(true);
    }

//    private static void setColumnWidths(JTable table) {
//        // Set width for each column individually
//        TableColumn column;
//
//        column = table.getColumnModel().getColumn(0); // ID
//        column.setPreferredWidth(10);
//
//        column = table.getColumnModel().getColumn(1); // Codigo
//        column.setPreferredWidth(10);
//
//        column = table.getColumnModel().getColumn(2); // Empleo
//        column.setPreferredWidth(10);
//
//        column = table.getColumnModel().getColumn(3); // Nombre
//        column.setPreferredWidth(60);
//
//        column = table.getColumnModel().getColumn(4); // Apellido 1
//        column.setPreferredWidth(100);
//
//        column = table.getColumnModel().getColumn(5); // Apellido 2
//        column.setPreferredWidth(100);
//
//        column = table.getColumnModel().getColumn(6); // Telefono
//        column.setPreferredWidth(10);
//
//        column = table.getColumnModel().getColumn(7); // Division
//        column.setPreferredWidth(80);
//
//        column = table.getColumnModel().getColumn(8); // Orden
//        column.setPreferredWidth(10);
//
//        column = table.getColumnModel().getColumn(9); // Rol
//        column.setPreferredWidth(10);
//
//        column = table.getColumnModel().getColumn(10); // Current flag;
//        column.setPreferredWidth(10);
//    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}
