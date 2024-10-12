package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.model.DatabaseLink;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class DataViewTest extends JFrame {

    public DataViewTest() {
        super("Data View");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(1920, 1080);
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
            connection = DatabaseLink.getDatabaseInstance().connectToDatabase();
            statement = connection.createStatement();

            // SQL query
            String sql = "SELECT * FROM dim_crew"; // Example query, replace with your own table and columns
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
            DatabaseLink.getDatabaseInstance().disconnectFromDatabase();

        } catch (SQLException e) {
            e.printStackTrace();
            // Show a success dialog
            JOptionPane.showMessageDialog(null, "Compruebe la conexión a la base de datos", "Error de conexión", JOptionPane.INFORMATION_MESSAGE);
            System.exit(1); // aqui en lugar de cerrar tengo que mostrarle al usuario de nuevo el FileChooser para que vuelva a enlazar la base de datos.
        }
    }
}
