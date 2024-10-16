package org.jonatancarbonellmartinez.view;



import org.jonatancarbonellmartinez.controller.PersonController;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class MainView extends JFrame {

    private static final Dimension TEXT_FIELD_DIMENSION = new Dimension(125, 30);
    private PersonController personController;

    public MainView(PersonController personController) throws SQLException {
        this.personController = personController;

        initializeUI();  // Initializes the UI and binds event listeners
        createMenuBar();
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setVisible(true);
        setLocationRelativeTo(null);


        JSpinner dateSpinner = createDateSpinner();
        JPanel mainPanel = createMainPanel(dateSpinner);

        JPanel backgroundPanel = new JPanel(new BorderLayout(8, 8));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        backgroundPanel.add(mainPanel, BorderLayout.NORTH);
        backgroundPanel.add(new JPanel(), BorderLayout.CENTER);  // Empty panel for layout

        this.setContentPane(backgroundPanel);
    }

    private JSpinner createDateSpinner() {
        SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        JSpinner dateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy HH:mm");
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setPreferredSize(TEXT_FIELD_DIMENSION);
        return dateSpinner;
    }

    private JPanel createMainPanel(JSpinner dateSpinner) {
        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        mainPanel.add(new JButton("Uno"));
        mainPanel.add(new JButton("Dos"));
        mainPanel.add(new JButton("Tres"));
        mainPanel.add(new JButton("Cuatro"));
        mainPanel.add(new JButton("Cinco"));

        // Add date spinner
        mainPanel.add(dateSpinner);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
        return mainPanel;
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        // Create and add all menu items
        menuBar.add(createRegistrarMenu());
        menuBar.add(createAnadirMenu());
        menuBar.add(createModificarMenu());
        menuBar.add(createEliminarMenu());
        menuBar.add(createVerMenu());
        menuBar.add(createGenerarMenu());
        this.setJMenuBar(menuBar);
    }

    private JMenu createRegistrarMenu() {
        JMenu registrarMenu = new JMenu("Registrar");
        registrarMenu.add(new JMenuItem("Vuelo"));
        registrarMenu.add(new JMenuItem("Combustible"));
        registrarMenu.add(new JMenuItem("Calificación"));
        return registrarMenu;
    }

    private JMenu createAnadirMenu() {
        JMenu anadirMenu = new JMenu("Añadir");
        anadirMenu.add(new JMenuItem("Miembro"));
        anadirMenu.add(new JMenuItem("Evento"));
        JMenuItem salirItem = new JMenuItem("Salir");
        salirItem.addActionListener(e -> System.exit(0));  // Action for "Exit" menu
        anadirMenu.add(salirItem);
        return anadirMenu;
    }

    private JMenu createModificarMenu() {
        return new JMenu("Modificar");  // Menu stub, add items as needed
    }

    private JMenu createEliminarMenu() {
        return new JMenu("Eliminar");  // Menu stub, add items as needed
    }

    private JMenu createVerMenu() {
        JMenu verMenu = new JMenu("Ver");
        verMenu.add(new JMenuItem("Vista principal"));
        verMenu.addSeparator();
        verMenu.add(new JMenuItem("Horas cupo"));
        verMenu.add(new JMenuItem("Horas escuadrilla"));
        verMenu.add(new JMenuItem("Horas tripulantes"));
        verMenu.addSeparator();
        verMenu.add(new JMenuItem("Tomas"));
        return verMenu;
    }

    private JMenu createGenerarMenu() {
        JMenu generarMenu = new JMenu("Generar");
        generarMenu.add(new JMenuItem("Documentación semanal"));
        generarMenu.add(new JMenuItem("Documentación mensual"));
        return generarMenu;
    }
}
