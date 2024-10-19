package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.model.dao.PersonDAO;
import org.jonatancarbonellmartinez.model.entities.Person;
import org.jonatancarbonellmartinez.presenter.AddPersonPresenter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;

public class AddPersonView extends JDialog {
    private MainView mainView;
    private AddPersonPresenter presenter;

    private JTable personTable;
    private DefaultTableModel tableModel;

    public AddPersonView(MainView mainView, PersonDAO personDAO) {
        this.mainView = mainView; // This is mainly used to do things like setLocationRelativeTo(mainView);
        this.presenter = new AddPersonPresenter(this, personDAO);
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setResizable(false);
        setTitle("Añadir personal");
        setLayout(new BorderLayout());
        setSize(914, 360);


        // Set up the table model
        String[] columnNames = {
                "ID", "Código", "Orden", "Empleo",
                "Nombre", "Apellido 1", "Apellido 2",
                "DNI", "Teléfono", "División", "Situación"
        };
        tableModel = new DefaultTableModel(columnNames, 0);
        personTable = new JTable(tableModel);

        // Add the table to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(personTable);
        add(scrollPane, BorderLayout.CENTER);

        setColumnWidths(personTable);

        showView();
    }

    public void showView() {
        setLocationRelativeTo(mainView);
        presenter.loadAllPersons();
        setVisible(true);
    }

    private static void setColumnWidths(JTable table) {
        // Set width for each column individually
        TableColumn column;

        column = table.getColumnModel().getColumn(0); // ID
        column.setPreferredWidth(60);

        column = table.getColumnModel().getColumn(1); // Código
        column.setPreferredWidth(60);

        column = table.getColumnModel().getColumn(2); // Orden
        column.setPreferredWidth(60);

        column = table.getColumnModel().getColumn(3); // Empleo
        column.setPreferredWidth(60);

        column = table.getColumnModel().getColumn(4); // Nombre
        column.setPreferredWidth(100);

        column = table.getColumnModel().getColumn(5); // Apellido 1
        column.setPreferredWidth(100);

        column = table.getColumnModel().getColumn(6); // Apellido 2
        column.setPreferredWidth(100);

        column = table.getColumnModel().getColumn(7); // DNI
        column.setPreferredWidth(80);

        column = table.getColumnModel().getColumn(8); // Teléfono
        column.setPreferredWidth(80);

        column = table.getColumnModel().getColumn(9); // División
        column.setPreferredWidth(120);

        column = table.getColumnModel().getColumn(10); // Situación
        column.setPreferredWidth(80);
    }

    public void displayPersons(List<Person> persons) {
        // Clear existing rows
        tableModel.setRowCount(0);

        // Add persons to the table model
        for (Person person : persons) {
            Object[] rowData = {
                    person.getPersonSk(),
                    person.getPersonNk(),
                    person.getPersonRankNumber(),
                    person.getPersonRank(),
                    person.getPersonName(),
                    person.getPersonLastName1(),
                    person.getPersonLastName2(),
                    person.getPersonDni(),
                    person.getPersonPhone(),
                    person.getPersonDivision(),
                    person.getPersonCurrentFlag()
            };
            tableModel.addRow(rowData);
        }
    }
}
