package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.presenter.MainPresenter;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class MainView extends JFrame {
    MainPresenter presenter; // The view holds a reference to his presenter
    JMenuItem personalMenuItem;

    private static final Dimension TEXT_FIELD_DIMENSION = new Dimension(125, 30);


    public MainView() throws SQLException {
        initializeUI();  // Initializes the UI and binds event listeners
        createMenuBar();
    }

    // Getter for the "Personal" menu item
    public JMenuItem getPersonalMenuItem() {
        return personalMenuItem;
    }

    // Method to set the presenter
    public void setPresenter(MainPresenter presenter) {
        this.presenter = presenter;
    }

    // Method to show a specific view (not implemented in this example)
    public void showView(Object view) {
        // Logic to display the selected view
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

        mainPanel.add(new JButton("Principal"));
        mainPanel.add(new JButton("Pilotos"));
        mainPanel.add(new JButton("Dotaciones"));
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
