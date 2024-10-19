package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.presenter.MainPresenter;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class MainView extends JFrame {
    private JMenuItem personalMenuItem;
    private final Dimension TEXT_FIELD_DIMENSION = new Dimension(125, 30);

    public MainView() {
        initializeUI();
        createMenuBar();
    }

    public JMenuItem getPersonalMenuItem() {
        return personalMenuItem;
    }

    private void initializeUI() {
        setTitle("Haverkat - Decimocuarta Escuadrilla");
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

        // Set tooltip delay
        ToolTipManager.sharedInstance().setInitialDelay(0); // Delay in milliseconds. I think about putting the button in the left and only icons, so i want a tooltip to appear fast. like in intellij idea. (Copy Intellij Idea GUI and you will rock, make logo icons accordingly)
        ToolTipManager.sharedInstance().setDismissDelay(3000); // How long the tooltip stays visible

        // Creating Buttons
        JButton botonPrincipal = new JButton("Principal");
        JButton botonPilotos = new JButton("Pilotos");
        JButton botonDotaciones = new JButton("Dotaciones");

        // Assigning tooltips
        botonPrincipal.setToolTipText("Principal");
        botonPilotos.setToolTipText("Pilotos");
        botonDotaciones.setToolTipText("Dotaciones");

        // Assigning buttons to mainPanel
        mainPanel.add(botonPrincipal);
        mainPanel.add(botonPilotos);
        mainPanel.add(botonDotaciones);

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
        menuBar.add(createEditarMenu());
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
        personalMenuItem = new JMenuItem("Personal");
        anadirMenu.add(personalMenuItem);
        anadirMenu.add(new JMenuItem("Evento"));
        return anadirMenu;
    }

    private JMenu createEditarMenu() {
        JMenu editarMenu = new JMenu("Editar");
        editarMenu.add(new JMenuItem("Personal"));
        editarMenu.add(new JMenuItem("Evento"));

        return editarMenu;
    }

    private JMenu createGenerarMenu() {
        JMenu generarMenu = new JMenu("Generar");
        generarMenu.add(new JMenuItem("Documentación semanal"));
        generarMenu.add(new JMenuItem("Documentación mensual"));
        return generarMenu;
    }
}
