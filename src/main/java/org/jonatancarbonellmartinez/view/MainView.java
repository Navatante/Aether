package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.presenter.MainPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class MainView extends JFrame implements View {

    private MainPresenter presenter;

    private JPanel mainPanel;
    private JPanel topLeftPanel;
    private JPanel leftPanel;
    private JPanel bottomLeftPanel;
    private JPanel cardPanel; // Panel that will hold the different views (cards)
    private JPanel leftGapPanel;

    private CardLayout cardLayout;  // CardLayout to manage the views

    private JMenuBar menuBar;

    private JMenu registrarMenu;
    private JMenu anadirMenu;
    private JMenu editarMenu;
    private JMenu generarMenu;

    private JMenuItem registrarVueloMenuItem;
    private JMenuItem registrarCombustibleMenuItem;
    private JMenuItem registrarCalificacionMenuItem;

    private JMenuItem anadirPersonalMenuItem;
    private JMenuItem anadirEventoMenuItem;

    public JMenuItem getAnadirEventoMenuItem() {
        return anadirEventoMenuItem;
    }

    public JMenuItem getEditarEventoMenuItem() {
        return editarEventoMenuItem;
    }

    private JMenuItem editarPersonalMenuItem;
    private JMenuItem editarEventoMenuItem;

    private JMenuItem docSemanalMenuItem;
    private JMenuItem docMensualMenuItem;

    private JButton botonPersonal;
    private JButton botonPrincipal;
    private JButton botonPilotos;
    private JButton botonDotaciones;
    private JButton botonEventos;
    private JButton botonSesiones;
    private JButton botonHelos;
    private JButton botonCapbas;
    private JButton botonGenerator;

    private Color menusColor;
    private Color borderColor;

    public MainView() {
        presenter = new MainPresenter(this);
        this.initializeUI();
        setVisible(true);
    }

    @Override
    public void setupUIProperties() {
        setTitle("Haverkat - Decimocuarta Escuadrilla");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);
    }

    @Override
    public void createPanels() {
        mainPanel = new JPanel(new BorderLayout());
        topLeftPanel = new JPanel();
        bottomLeftPanel = new JPanel();
        leftPanel = new JPanel(new BorderLayout());

        cardPanel = new JPanel();
        cardLayout = new CardLayout();

        leftGapPanel = new JPanel();
    }

    @Override
    public void createComponents() {
        menusColor = new Color(43,45,48);
        borderColor = new Color(29,31,34);

        menuBar = new JMenuBar();

        registrarMenu = new JMenu("Registrar");
        anadirMenu = new JMenu("Añadir");
        editarMenu = new JMenu("Editar");
        generarMenu = new JMenu("Generar");

        registrarVueloMenuItem = (new JMenuItem("Vuelo"));
        registrarCombustibleMenuItem = (new JMenuItem("Combustible"));
        registrarCalificacionMenuItem = (new JMenuItem("Calificación"));

        anadirPersonalMenuItem = new JMenuItem("Personal");
        anadirEventoMenuItem = new JMenuItem("Evento");

        editarPersonalMenuItem = new JMenuItem("Personal");
        editarEventoMenuItem = new JMenuItem("Evento");

        docSemanalMenuItem = new JMenuItem("Documentación semanal");
        docMensualMenuItem = new JMenuItem("Documentación mensual");


        botonPrincipal = createRoundedButton("G");
        botonPilotos = createRoundedButton("P");
        botonDotaciones = createRoundedButton("D");
        botonPersonal = createRoundedButton("P");
        botonEventos = createRoundedButton("E");
        botonSesiones = createRoundedButton("S");
        botonHelos = createRoundedButton("H");
        botonCapbas = createRoundedButton("C");
        botonGenerator = createRoundedButton("G");
    }

    @Override
    public void configurePanels() {
        setContentPane(mainPanel);
        mainPanel.setBorder(BorderFactory.createLineBorder(borderColor));
        topLeftPanel.setLayout(new GridLayout(3,1,0,10));
        topLeftPanel.setLayout(new GridLayout(3,1,0,10));
        bottomLeftPanel.setLayout(new GridLayout(6,1,0,10));
        leftPanel.setPreferredSize(new Dimension(60, 0)); // Width slightly larger than button size
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); // Padding around panel

        cardPanel.setLayout(cardLayout);
        cardPanel.setBackground(menusColor);

        leftGapPanel.setPreferredSize(new Dimension(55, 0));
        leftGapPanel.setOpaque(false); // Make the panel transparent


    }

    @Override
    public void configureComponents() {
        ToolTipManager.sharedInstance().setInitialDelay(0); // Tooltip appears instantly
        ToolTipManager.sharedInstance().setDismissDelay(3000); // Stays for 3 seconds
        botonPrincipal.setToolTipText("General");
        botonPilotos.setToolTipText("Pilotos");
        botonDotaciones.setToolTipText("Dotaciones");
        botonPersonal.setToolTipText("Personal");
        botonEventos.setToolTipText("Eventos");
        botonSesiones.setToolTipText("Sesiones");
        botonHelos.setToolTipText("Helicópteros");
        botonCapbas.setToolTipText("CAPBAS");
        botonGenerator.setToolTipText("Generadores");

        this.setJMenuBar(menuBar);
    }

    @Override
    public void assemblePanels() {
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(cardPanel, BorderLayout.CENTER);
        leftPanel.add(topLeftPanel,BorderLayout.NORTH);
        leftPanel.add(bottomLeftPanel,BorderLayout.SOUTH);

    }

    @Override
    public void assembleComponents() {
        menuBar.add(leftGapPanel);
        View.addMenusToMenu(menuBar, registrarMenu, anadirMenu, anadirMenu, editarMenu, generarMenu);
        View.addMenusToMenu(registrarMenu,registrarVueloMenuItem, registrarCombustibleMenuItem, registrarCalificacionMenuItem);
        View.addMenusToMenu(anadirMenu,anadirPersonalMenuItem,anadirEventoMenuItem);
        View.addMenusToMenu(editarMenu, editarPersonalMenuItem, editarEventoMenuItem);
        View.addMenusToMenu(generarMenu, docSemanalMenuItem, docMensualMenuItem);

        View.addComponentsToPanel(bottomLeftPanel, botonPersonal, botonEventos, botonSesiones, botonHelos, botonCapbas, botonGenerator);
        View.addComponentsToPanel(topLeftPanel, botonPrincipal, botonPilotos, botonDotaciones);
    }

    @Override
    public void addActionListeners() {
        presenter.setActionListeners();
    }

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

    // Getters
    public JMenuItem getAnadirPersonalMenuItem() {
        return anadirPersonalMenuItem;
    }

    public MainPresenter getPresenter() {
        return presenter;
    }

    public JMenuItem getEditarPersonalMenuItem() {
        return editarPersonalMenuItem;
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
