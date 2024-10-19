package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.presenter.MainPresenter;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
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


        JPanel mainPanel = createMainPanel();
        setContentPane(mainPanel);

    }

    private JPanel createMainPanel() {
        Color borderColor = new Color(29,31,34);
        Color menusColor = new Color(43,45,48);
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Set tooltip delay
        ToolTipManager.sharedInstance().setInitialDelay(0); // Tooltip appears instantly
        ToolTipManager.sharedInstance().setDismissDelay(3000); // Stays for 3 seconds

        // CenterPanel
        JPanel centerPanel = new JPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setBackground(menusColor);

        // LeftPanel
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(60, 0)); // Width slightly larger than button size
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS)); // Arrange buttons vertically
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding around panel

        mainPanel.add(leftPanel, BorderLayout.WEST);

        // Creating Buttons with rounded corners (no visible borders)
        JButton botonPrincipal = createRoundedButton("G");
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(botonPrincipal);
        leftPanel.add(Box.createVerticalStrut(10)); // Space between buttons

        JButton botonPilotos = createRoundedButton("P");
        leftPanel.add(botonPilotos);
        leftPanel.add(Box.createVerticalStrut(10)); // Space between buttons

        JButton botonDotaciones = createRoundedButton("D");
        leftPanel.add(botonDotaciones);

        // Add vertical glue after the buttons to push them to the top
        leftPanel.add(Box.createVerticalGlue());

        // Assigning tooltips
        botonPrincipal.setToolTipText("Principal");
        botonPilotos.setToolTipText("Pilotos");
        botonDotaciones.setToolTipText("Dotaciones");


        //leftPanel.setBackground(menusColor);
        leftPanel.setBorder(BorderFactory.createMatteBorder(0,0,0,1,borderColor));
        mainPanel.setBorder(BorderFactory.createLineBorder(borderColor));
        return mainPanel;
    }

    // Method to create a button with rounded corners (no painted borders)
    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Set the background color
                g2.setColor(getBackground());

                // Create a rounded rectangle for the button background
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // Draw the text on the button
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                // No border painting
            }

            @Override
            public boolean contains(int x, int y) {
                // Check if the point is within the rounded rectangle
                int width = getWidth();
                int height = getHeight();
                int arcWidth = 20;
                int arcHeight = 20;
                return new RoundRectangle2D.Float(0, 0, width, height, arcWidth, arcHeight).contains(x, y);
            }
        };

        button.setPreferredSize(new Dimension(38, 38)); // Set button size
        button.setMaximumSize(new Dimension(38, 38));   // Ensure max size is 38x38
        button.setContentAreaFilled(false);             // Prevents default rectangular background
        button.setFocusPainted(false);                  // Remove focus painting
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment

        return button;
    }


    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Create a panel for the icon and gap
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(55, 0));
        leftPanel.setOpaque(false); // Make the panel transparent

        // Load the Java icon (adjust path to your icon if needed)
        //ImageIcon javaIcon = new ImageIcon(getClass().getResource("/path/to/java_icon.png")); // Use a valid path to your icon

        // Create a label to hold the icon
        //JLabel iconLabel = new JLabel(javaIcon);
        //leftPanel.add(iconLabel); // Add icon to the panel

        // Add the icon panel to the menu bar
        menuBar.add(leftPanel);

        // Create and add all menu items
        menuBar.add(createRegistrarMenu());
        menuBar.add(createAnadirMenu());
        menuBar.add(createEditarMenu());
        menuBar.add(createGenerarMenu());

        // Set the custom menu bar
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
