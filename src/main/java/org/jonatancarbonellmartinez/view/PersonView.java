package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.Observer.Observer;
import org.jonatancarbonellmartinez.controller.PersonController;
import org.jonatancarbonellmartinez.model.entities.DimPerson;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PersonView extends JFrame implements Observer<DimPerson> {
    private PersonController controller;
    private JPanel mainPanel;
    public JMenuItem createPersonMenu, updatePersonMenu, deletePersonMenu, searchPersonMenu;
    private JTextField txtPersonNk, txtPersonName, txtRankNumber, txtPersonLastName1, txtPersonLastName2,
            txtPersonDni, txtPersonPhone, txtPersonRank, txtPersonDivision, txtPersonCurrentFlag;

    private JList<DimPerson> personList;

    public PersonView() {
        setTitle("Gestión de Personas - CRUD");
        setSize(600, 400);
        setLocationRelativeTo(null);  // Center the window on the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();  // Initialize the UI components
        setVisible(true);
    }

    // Assign the controller
    public void setController(PersonController controller) {
        this.controller = controller;
    }

    // Register the view as an observer
    public void registerAsObserver() {
        if (controller != null) {
            controller.addObserver(this);  // Register this view as an observer
        } else {
            throw new IllegalStateException("Controller must be set before registering as observer");
        }
    }

    // Observer interface method to handle updates
    @Override
    public void update(DimPerson person, String propertyName) {
        if ("create".equals(propertyName)) {
            JOptionPane.showMessageDialog(this, "Persona creada: " + person.getPersonName());
        } else if ("update".equals(propertyName)) {
            JOptionPane.showMessageDialog(this, "Persona actualizada: " + person.getPersonName());
        } else if ("delete".equals(propertyName)) {
            JOptionPane.showMessageDialog(this, "Persona eliminada: " + person.getPersonName());
        }
    }

    private void initUI() {
        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Operaciones");
        createPersonMenu = new JMenuItem("Crear Persona");
        updatePersonMenu = new JMenuItem("Actualizar Persona");
        deletePersonMenu = new JMenuItem("Eliminar Persona");
        searchPersonMenu = new JMenuItem("Buscar Persona");

        // Add items to the menu
        menu.add(createPersonMenu);
        menu.add(updatePersonMenu);
        menu.add(deletePersonMenu);
        menu.add(searchPersonMenu);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        // Main panel with a grid layout for input fields
        mainPanel = new JPanel(new GridLayout(10, 2));
        add(mainPanel, BorderLayout.CENTER);

        // Initialize text fields for person attributes
        txtPersonNk = new JTextField();
        txtPersonName = new JTextField();
        txtRankNumber = new JTextField();
        txtPersonLastName1 = new JTextField();
        txtPersonLastName2 = new JTextField();
        txtPersonDni = new JTextField();
        txtPersonPhone = new JTextField();
        txtPersonRank = new JTextField();
        txtPersonDivision = new JTextField();
        txtPersonCurrentFlag = new JTextField();

        // Add text fields and labels to the panel
        mainPanel.add(new JLabel("Person NK:"));
        mainPanel.add(txtPersonNk);
        mainPanel.add(new JLabel("Name:"));
        mainPanel.add(txtPersonName);
        mainPanel.add(new JLabel("Rank Number:"));
        mainPanel.add(txtRankNumber);
        mainPanel.add(new JLabel("Last Name 1:"));
        mainPanel.add(txtPersonLastName1);
        mainPanel.add(new JLabel("Last Name 2:"));
        mainPanel.add(txtPersonLastName2);
        mainPanel.add(new JLabel("DNI:"));
        mainPanel.add(txtPersonDni);
        mainPanel.add(new JLabel("Phone:"));
        mainPanel.add(txtPersonPhone);
        mainPanel.add(new JLabel("Rank:"));
        mainPanel.add(txtPersonRank);
        mainPanel.add(new JLabel("Division:"));
        mainPanel.add(txtPersonDivision);
        mainPanel.add(new JLabel("Current Flag:"));
        mainPanel.add(txtPersonCurrentFlag);

        // Initialize the person list
        personList = new JList<>();
        add(new JScrollPane(personList), BorderLayout.EAST); // Display list on the right
    }

    // Method to get input data from fields (without creating the model object)
    public String getPersonNk() { return txtPersonNk.getText(); }
    public String getPersonName() { return txtPersonName.getText(); }
    public int getRankNumber() { return Integer.parseInt(txtRankNumber.getText()); }
    public String getPersonLastName1() { return txtPersonLastName1.getText(); }
    public String getPersonLastName2() { return txtPersonLastName2.getText(); }
    public String getPersonDni() { return txtPersonDni.getText(); }
    public String getPersonPhone() { return txtPersonPhone.getText(); }
    public String getPersonRank() { return txtPersonRank.getText(); }
    public String getPersonDivision() { return txtPersonDivision.getText(); }
    public int getPersonCurrentFlag() { return Integer.parseInt(txtPersonCurrentFlag.getText()); }

    // Method to return the selected person in the list
    public DimPerson getSelectedPerson() {
        return personList.getSelectedValue();
    }

    // Method to update the displayed list of persons
    public void updatePersonList(List<DimPerson> personListData) {
        DefaultListModel<DimPerson> listModel = new DefaultListModel<>();
        for (DimPerson person : personListData) {
            listModel.addElement(person);
        }
        personList.setModel(listModel);
    }
}
