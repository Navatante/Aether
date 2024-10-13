package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.model.DatabaseLink;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;

public class MainView extends JFrame {
    private Dimension textFieldsDimension = new Dimension(125,30); // Dimension to be used in all of my text fields.

    public MainView() {
        super("Haverkat Flight - Decimocuarta Escuadrilla"); // "FlightHub - Decimocuarta Escuadrilla" (he puesto Haverkat de cachondeo, cambialo)
        frameConfiguration();
        initComponents();
        createMenuBar();
        try {
            if(!DatabaseLink.getDatabaseInstance().propertiesFileCheck()) {
                System.exit(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    private void frameConfiguration() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1280,720);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void initComponents() {
        JLabel greeting = new JLabel("Bienvenido", JLabel.CENTER);
        greeting.setFont(new Font("serif", Font.PLAIN, 20));
        greeting.setForeground(Color.WHITE);

        // Create the SpinnerDateModel with the current date
        SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);

        // Create the Spinner with the dateModel
        JSpinner dateTextField = new JSpinner(dateModel);

        // Set the date format for the JSpinner (e.g., "dd/MM/yyyy")
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateTextField, "dd/MM/yyyy HH:mm");
        dateTextField.setEditor(dateEditor);

        // Set the already created dimension
        dateTextField.setPreferredSize(textFieldsDimension);
        dateTextField.setEnabled(true);


        // Create buttons
        JButton viewDataButton = new JButton("View Data");
        viewDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open DataView when the button is clicked
                new DataViewTest().setVisible(true);
            }
        });


        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        mainPanel.add(viewDataButton); // adding new button to test sql connection
        mainPanel.add(new JButton ("Uno"));
        mainPanel.add(new JButton ("Dos"));
        mainPanel.add(new JButton ("Tres"));
        mainPanel.add(new JButton ("Cuatro"));
        mainPanel.add(new JButton ("Cinco"));
        mainPanel.add(dateTextField);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));

        // Going to try to place the JPanels around the screen in an ordered way.
        JPanel backgroundPanel = new JPanel(new BorderLayout(8,8));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        backgroundPanel.add(mainPanel, BorderLayout.NORTH);
        backgroundPanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10) ),BorderLayout.CENTER);

        // Just testing the separator
        JSeparator verticalSeparator = new JSeparator(SwingConstants.VERTICAL);
        verticalSeparator.setPreferredSize(new Dimension(10, 50));
        backgroundPanel.add(verticalSeparator);


        // Main JFrame
        this.setContentPane(backgroundPanel);


    } //  este metodo esta fatal, subdividilo en varios 1 METODO SOLO PUEDE HACER 1 COSA!

    private void createMenuBar() {
        // Create the JMenu Bar
        JMenuBar menuBar = new JMenuBar();

        // Create the JMenus
        JMenu registrarMenu = new JMenu("Registrar"); // tpodo lo que sea registro, en una ventana nueva. (al final cada Jmenu en una ventana nueva.)
        JMenu anadirMenu = new JMenu("Añadir"); // todo lo que sea anadir, en una ventana nueva. (al final cada Jmenu en una ventana nueva.)
        JMenu modificarMenu = new JMenu("Modificar"); // todo lo que sea modificar, en una ventana nueva. (al final cada Jmenu en una ventana nueva.)
        JMenu eliminarMenu = new JMenu("Eliminar"); // todo lo que sea eliminar, en una ventana nueva. (al final cada Jmenu en una ventana nueva.)
        JMenu verMenu = new JMenu("Ver"); // la vista de Ver se queda en la ventana inicial siempre.
        JMenu generarMenu = new JMenu("Generar"); // todo lo que sea generar, en una ventana nueva. (al final cada Jmenu en una ventana nueva.)


        // Registrar Menu Items
        JMenuItem vueloItemOfRegistrarMenu = new JMenuItem("Vuelo");
        JMenuItem combustibleItemOfRegistrarMenu = new JMenuItem("Combustible");
        JMenuItem calificacionItemOfRegistrarMenu = new JMenuItem("Calificación");


        // Anadir Menu Items
        JMenuItem salirItemOfAnadirMenu = new JMenuItem("Salir"); // este quitarlo
        JMenuItem miembroItem = new JMenuItem("Miembro");
        JMenuItem eventoItem = new JMenuItem("Evento");
        // uno por cada tabla dimension...


        // Ver Menu Items
        JMenuItem vistaPrincipalItem = new JMenuItem("Vista principal");
        JMenuItem cupoAutoridadItem = new JMenuItem("Horas cupo");
        JMenuItem horasEscuadrillaItem = new JMenuItem("Horas escuadrilla");
        JMenuItem horasTripulantesItem = new JMenuItem("Horas tripulantes");
        JMenuItem tomasItem = new JMenuItem("Tomas");
        // app ifr etc...

        // Generar Menu Items
        JMenuItem docSemanalItem = new JMenuItem("Documentación semanal");
        JMenuItem docMensualItem = new JMenuItem("Documentación mensual");




        // Add ActionListener to the "Exit" menu item to close the application
        salirItemOfAnadirMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);  // Exit the application when "Exit" is clicked
            }
        });

        // Add the "File" menu to the menu bar
        menuBar.add(registrarMenu);
        menuBar.add(anadirMenu);
        menuBar.add(modificarMenu);
        menuBar.add(eliminarMenu);
        menuBar.add(verMenu);
        menuBar.add(generarMenu);

        // Add the Items to the Registrar menu
        registrarMenu.add(vueloItemOfRegistrarMenu);
        registrarMenu.add(combustibleItemOfRegistrarMenu);
        registrarMenu.add(calificacionItemOfRegistrarMenu);


        // Add the Items to the Anadir menu
        anadirMenu.add(miembroItem);
        anadirMenu.add(eventoItem);
        anadirMenu.add(salirItemOfAnadirMenu);

        // Add the Items to Ver menu
        verMenu.add(vistaPrincipalItem);
        verMenu.addSeparator();
        verMenu.add(cupoAutoridadItem);
        verMenu.add(horasEscuadrillaItem);
        verMenu.add(horasTripulantesItem);
        verMenu.addSeparator();
        verMenu.add(tomasItem);

        // Add the Items to Generar menu
        generarMenu.add(docSemanalItem);
        generarMenu.add(docMensualItem);

        // Set the menu bar to the JFrame
        this.setJMenuBar(menuBar);
    }
}


