package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.factory.DAOFactory;
import org.jonatancarbonellmartinez.presenter.MainPresenter;
import org.jonatancarbonellmartinez.utilities.NavigationController;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class MainView extends JFrame { // TODO implement View interface and refactor code accordingly.

    private MainPresenter presenter;
    private JPanel cardPanel; // Panel that will hold the different views (cards)

    private CardLayout cardLayout;  // CardLayout to manage the views
    private JButton botonPersonal;
    private JMenuItem addPersonalMenuItem;
    private JMenuItem editPersonalMenuItem;

    public MainView(DAOFactory daoFactory) {
        initializeUI();
        createMenuBar();
        presenter = new MainPresenter(this, daoFactory);
        addActionListeners();
    }

    //@Override
    public void addActionListeners() {
        presenter.setActionListeners();
    }

    public JMenuItem getAddPersonalMenuItem() {
        return addPersonalMenuItem;
    }

    public void initializeUI() {
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

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Set tooltip delay
        ToolTipManager.sharedInstance().setInitialDelay(0); // Tooltip appears instantly
        ToolTipManager.sharedInstance().setDismissDelay(3000); // Stays for 3 seconds

        // CenterPanel
        mainPanel.add(createCardPanel(), BorderLayout.CENTER);

        // TopLeftPanel
        JPanel topLeftPanel = new JPanel();
        topLeftPanel.setLayout(new GridLayout(3,1,0,10));

        // Creating Buttons with rounded corners (no visible borders)
        JButton botonPrincipal = createRoundedButton("G");
        JButton botonPilotos = createRoundedButton("P");
        JButton botonDotaciones = createRoundedButton("D");

        //Add buttons to topLeftPanel
        topLeftPanel.add(botonPrincipal);
        topLeftPanel.add(botonPilotos);
        topLeftPanel.add(botonDotaciones);

        // Assigning tooltips
        botonPrincipal.setToolTipText("General");
        botonPilotos.setToolTipText("Pilotos");
        botonDotaciones.setToolTipText("Dotaciones");


        //BottomLeftPanel
        JPanel bottomLeftPanel = new JPanel();
        bottomLeftPanel.setLayout(new GridLayout(6,1,0,10));

        // Creating Buttons with rounded corners (no visible borders)
        botonPersonal = createRoundedButton("P");
        JButton botonEventos = createRoundedButton("E");
        JButton botonSesiones = createRoundedButton("S");
        JButton botonHelos = createRoundedButton("H");
        JButton botonCapbas = createRoundedButton("C");
        JButton botonGenerator = createRoundedButton("G");

        //Add buttons to topLeftPanel
        bottomLeftPanel.add(botonPersonal);
        bottomLeftPanel.add(botonEventos);
        bottomLeftPanel.add(botonSesiones);
        bottomLeftPanel.add(botonHelos);
        bottomLeftPanel.add(botonCapbas);
        bottomLeftPanel.add(botonGenerator);

        // Assigning tooltips
        botonPersonal.setToolTipText("Personal");
        botonEventos.setToolTipText("Eventos");
        botonSesiones.setToolTipText("Sesiones");
        botonHelos.setToolTipText("Helicópteros");
        botonCapbas.setToolTipText("CAPBAS");
        botonGenerator.setToolTipText("Generadores");

        // LeftPanel
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(60, 0)); // Width slightly larger than button size
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); // Padding around panel
        leftPanel.add(topLeftPanel,BorderLayout.NORTH);
        leftPanel.add(bottomLeftPanel,BorderLayout.SOUTH);

        mainPanel.add(leftPanel, BorderLayout.WEST);

        //leftPanel.setBackground(menusColor);
        mainPanel.setBorder(BorderFactory.createLineBorder(borderColor));
        return mainPanel;
    }

    private JPanel createCardPanel() {
        Color menusColor = new Color(43,45,48);
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        cardPanel.setBackground(menusColor);

        return cardPanel;
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
        button.setContentAreaFilled(false);                         // Prevents default rectangular background
        button.setFocusPainted(false);                              // Remove focus painting
        button.setAlignmentX(Component.CENTER_ALIGNMENT);           // Center alignment

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
        addPersonalMenuItem = new JMenuItem("Personal");
        anadirMenu.add(addPersonalMenuItem);
        anadirMenu.add(new JMenuItem("Evento"));
        return anadirMenu;
    }

    private JMenu createEditarMenu() {
        JMenu editarMenu = new JMenu("Editar");
        editPersonalMenuItem = new JMenuItem("Personal");
        editarMenu.add(editPersonalMenuItem);
        editarMenu.add(new JMenuItem("Evento"));

        return editarMenu;
    }

    private JMenu createGenerarMenu() {
        JMenu generarMenu = new JMenu("Generar");
        generarMenu.add(new JMenuItem("Documentación semanal"));
        generarMenu.add(new JMenuItem("Documentación mensual"));
        return generarMenu;
    }


    // Getters
    public MainPresenter getPresenter() {
        return presenter;
    }

    public JMenuItem getEditPersonalMenuItem() {
        return editPersonalMenuItem;
    }

    public JButton getBotonPersonal() {
        return botonPersonal;
    }

    public JPanel getCardPanel() {
        return cardPanel;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }
}
