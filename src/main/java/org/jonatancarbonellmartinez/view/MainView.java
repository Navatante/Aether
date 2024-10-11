package org.jonatancarbonellmartinez.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

public class MainView extends JFrame {
    private Dimension textFieldsDimension = new Dimension(125,30); // Dimension to be used in all of my text fields.

    public MainView() {
        super("FlightHub - Decimocuarta Escuadrilla");
        frameConfiguration();
        initComponents();
        createMenuBar();
    }

    private void frameConfiguration() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1280,720);
        this.setVisible(true);
        this.setResizable(false); // to avoid users destroy the GUI.
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
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateTextField, "dd/MM/yyyy");

        // Set the already created dimension
        dateTextField.setPreferredSize(textFieldsDimension);
        dateTextField.setEnabled(false);




        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        mainPanel.add(new JButton ("One"));
        mainPanel.add(new JButton ("Two"));
        mainPanel.add(new JButton ("Three"));
        mainPanel.add(new JButton ("Four"));
        mainPanel.add(new JButton ("Five"));
        mainPanel.add(dateTextField);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));

        // Going to try to place the JPanels around the screen in an ordered way.
        JPanel backgroundPanel = new JPanel(new BorderLayout(8,8));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        backgroundPanel.add(mainPanel, BorderLayout.NORTH);
        backgroundPanel.add(new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10) ),BorderLayout.CENTER);

        // Main JFrame
        this.setContentPane(backgroundPanel);


    } //  este metodo esta fatal, subdividilo en varios 1 METODO SOLO PUEDE HACER 1 COSA!

    private void createMenuBar() {
        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();

        JMenu registrarVueloMenu = new JMenu("Registrar vuelo");

        // Create the "File" menu
        JMenu anadirMenu = new JMenu("Añadir...");


        // Create the "Exit" menu item
        JMenuItem exitItem = new JMenuItem("Salir");

        JMenuItem miembroItem = new JMenuItem("Miembro");

        JMenuItem eventoItem = new JMenuItem("Evento");

        // Add ActionListener to the "Exit" menu item to close the application
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);  // Exit the application when "Exit" is clicked
            }
        });

        // Add the "Miembro" item to the Anadir menu
        anadirMenu.add(miembroItem);

        anadirMenu.add(eventoItem);

        // Add the "Exit" item to the Anadir menu
        anadirMenu.add(exitItem);

        // Add the "File" menu to the menu bar
        menuBar.add(registrarVueloMenu);
        menuBar.add(anadirMenu);

        // Set the menu bar to the JFrame
        this.setJMenuBar(menuBar);
    }
}


