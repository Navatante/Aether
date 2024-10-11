package org.jonatancarbonellmartinez.view;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

public class MainView extends JFrame {

    public MainView() {
        super("FlightHub - Decimocuarta Escuadrilla");
        frameConfiguration();
        initComponents();
    }

    private void frameConfiguration() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1280,720);
        this.setVisible(true);
        this.setResizable(false); // to avoid users destroy the GUI.
        this.setLocationRelativeTo(null);
    }


    private void initComponents() {
        JLabel greeting = new JLabel("Hello world", JLabel.CENTER);
        greeting.setFont(new Font("serif", Font.PLAIN, 32));
        greeting.setForeground(Color.WHITE);

        Dimension textFieldsDimension = new Dimension(125,30); // Dimension to be used in all of my text fields.


        // Create the SpinnerDateModel with the current date
        SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);

        // Create the Spinner with the dateModel
        JSpinner dateTextField = new JSpinner(dateModel);

        // Set the date format for the JSpinner (e.g., "dd/MM/yyyy")
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateTextField, "dd/MM/yyyy");

        // Set the already created dimension
        dateTextField.setPreferredSize(textFieldsDimension);

        // Create the Spinner with the dateModel
        JPanel spinnerPanel = new JPanel(new FlowLayout());
        spinnerPanel.add(dateTextField);


        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(greeting, BorderLayout.NORTH);
        mainPanel.add(spinnerPanel, BorderLayout.CENTER);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));

        // Main JFrame
        this.setContentPane(mainPanel);
    }
}
