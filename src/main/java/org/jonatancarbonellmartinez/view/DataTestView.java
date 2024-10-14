package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.model.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class DataTestView extends JFrame {

    public DataTestView() {
        super("Listado de pilotos");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(1024, 576);
        this.setLocationRelativeTo(null);

        // Create a JTable
        JTable table = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel();
        table.setModel(tableModel);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setDefaultEditor(Object.class, null); // It is awesome, works so great, will use it a lot!

        // Populate the JTable with data from the SQLite database
        populateTableWithData(tableModel);

        // Add the JTable to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);
    }


    // Method to populate JTable with data from SQLite database
    private void populateTableWithData(DefaultTableModel tableModel)  { // ESTE METODO LO TENGO QUE SACAR DE AQUI Y METERLO EN model en una clase nueva
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the SQLite connection
            connection = Database.getDatabaseInstance().connectToDatabase();
            statement = connection.createStatement();

            // SQL query
            String sql = "SELECT crew_nk AS 'Codigo', crew_rank_number AS 'Num. Escalafon', crew_rank AS 'Empleo', crew_name AS 'Nombre', crew_last_name_1 AS 'Apellido1', crew_last_name_2 AS 'Apellido2', crew_dni AS 'DNI', crew_phone AS Telefono, crew_division AS 'Division'\n" +
                    "FROM dim_crew\n" +
                    "ORDER BY crew_sk DESC;";
            resultSet = statement.executeQuery(sql);

            // Get metadata to dynamically get the column count and names
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Clear any existing columns and rows
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);

            // Add column names dynamically
            for (int column = 1; column <= columnCount; column++) {
                tableModel.addColumn(metaData.getColumnName(column));
            }

            // Add rows dynamically
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int column = 1; column <= columnCount; column++) {
                    row[column - 1] = resultSet.getObject(column);
                }
                tableModel.addRow(row);
            }

            // Disconnect from database
            Database.getDatabaseInstance().disconnectFromDatabase();

        } catch (SQLException e) {
            e.printStackTrace();
            // Show a success dialog
            JOptionPane.showMessageDialog(null, "Compruebe la conexión a la base de datos", "Error de conexión", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null,e.getMessage());
            //System.exit(1); // aqui en lugar de cerrar tengo que mostrarle al usuario de nuevo el FileChooser para que vuelva a enlazar la base de datos.
        }
    }
}
