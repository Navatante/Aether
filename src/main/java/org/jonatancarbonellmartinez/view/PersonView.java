package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.Observer.Observer;
import org.jonatancarbonellmartinez.controller.PersonController;
import org.jonatancarbonellmartinez.model.entities.DimPerson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonView extends JFrame implements Observer {
    private PersonController controller;
    private JPanel mainPanel;
    private JTextField txtPersonSk,txtPersonNk, txtPersonName,
                        txtRankNumber, txtPersonLastName1, txtPersonLastName2,
                        txtPersonDni, txtPersonPhone, txtPersonRank,
                        txtPersonDivision, txtPersonCurrentFlag;

    public PersonView() {
        setTitle("Gestión de Personas - CRUD");
        setSize(600, 400);
        setLocationRelativeTo(null);  // Centra la ventana en la pantalla
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();  // Inicializa los componentes de la interfaz
        setVisible(true);
    }

    // Metodo para asignar el controlador a la vista
    public void setController(PersonController controller) {
        this.controller = controller;
    }

    // Implementación del metodo de la interfaz Observer
    @Override
    public void update(DimPerson person, String propertyName) {
        // Actualizar la vista con la información de la nueva persona
        if ("create".equals(propertyName)) {
            JOptionPane.showMessageDialog(this, "Persona creada: " + person.getPersonName());
        }
        // Si quieres actualizar una lista de personas, puedes hacerlo aquí.
        // Por ejemplo, puedes actualizar una tabla o lista en la vista
    }

    private void initUI() {
        // Crear barra de menú
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Operaciones");
        JMenuItem createPersonMenu = new JMenuItem("Crear Persona");
        JMenuItem updatePersonMenu = new JMenuItem("Actualizar Persona");
        JMenuItem deletePersonMenu = new JMenuItem("Eliminar Persona");
        JMenuItem searchPersonMenu = new JMenuItem("Buscar Persona");

        // Agregar items al menú
        menu.add(createPersonMenu);
        menu.add(updatePersonMenu);
        menu.add(deletePersonMenu);
        menu.add(searchPersonMenu);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        // Panel principal que cambiará dependiendo de la acción
        mainPanel = new JPanel(new CardLayout());
        add(mainPanel, BorderLayout.CENTER);

        // ActionListeners para los menús
        createPersonMenu.addActionListener(e -> showCreatePersonForm());
        updatePersonMenu.addActionListener(e -> showUpdatePersonForm());
        deletePersonMenu.addActionListener(e -> showDeletePersonForm());
        searchPersonMenu.addActionListener(e -> showSearchPersonForm());

        // Mostrar el formulario de búsqueda por defecto
        showSearchPersonForm();
    }

    // Metodo para mostrar el formulario de creación de una persona
    private void showCreatePersonForm() {
        JPanel createPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        createPanel.setBorder(BorderFactory.createTitledBorder("Crear Persona"));

        txtPersonNk = new JTextField();
        txtPersonRank = new JTextField();
        txtRankNumber = new JTextField();
        txtPersonName = new JTextField();
        txtPersonLastName1 = new JTextField();
        txtPersonLastName2 = new JTextField();
        txtPersonDni = new JTextField();
        txtPersonPhone = new JTextField();
        txtPersonDivision = new JTextField();
        txtPersonCurrentFlag = new JTextField();

        createPanel.add(new JLabel("ID Natural (NK):"));
        createPanel.add(txtPersonNk);
        createPanel.add(new JLabel("Nombre:"));
        createPanel.add(txtPersonName);
        createPanel.add(new JLabel("Apellido:"));
        createPanel.add(txtPersonLastName1);
        createPanel.add(new JLabel("DNI:"));
        createPanel.add(txtPersonDni);
        createPanel.add(new JLabel("Teléfono:"));
        createPanel.add(txtPersonPhone);

        JButton createButton = new JButton("Crear Persona");
        createButton.addActionListener(e -> createPerson());

        createPanel.add(createButton);
        mainPanel.removeAll();
        mainPanel.add(createPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // En la vista, en lugar de crear el objeto DimPerson, solo debes recoger los valores del formulario y pasarlos al controlador:
    private void createPerson() {
        String personNk = txtPersonNk.getText();
        int personRankNumber = Integer.parseInt(txtRankNumber.getText());  // Asegúrate de validar antes de convertir
        String personRank = txtPersonRank.getText();
        String personName = txtPersonName.getText();
        String personLastName1 = txtPersonLastName1.getText();
        String personLastName2 = txtPersonLastName2.getText();
        String personDni = txtPersonDni.getText();
        String personPhone = txtPersonPhone.getText();
        String personDivision = txtPersonDivision.getText();
        int personCurrentFlag = Integer.parseInt(txtPersonCurrentFlag.getText());

        // Pasamos los datos al controlador
        controller.createPerson(personNk, personRankNumber, personRank, personName, personLastName1, personLastName2, personDni, personPhone, personDivision, personCurrentFlag);

        JOptionPane.showMessageDialog(this, "Persona creada con éxito");
    }

    // Metodo para mostrar el formulario de actualización
    private void showUpdatePersonForm() {
        JPanel updatePanel = new JPanel(new GridLayout(6, 2, 5, 5));
        updatePanel.setBorder(BorderFactory.createTitledBorder("Actualizar Persona"));

        txtPersonSk = new JTextField();
        txtPersonNk = new JTextField();
        txtPersonName = new JTextField();
        txtPersonLastName1 = new JTextField();
        txtPersonDni = new JTextField();
        txtPersonPhone = new JTextField();

        updatePanel.add(new JLabel("ID Persona (SK):"));
        updatePanel.add(txtPersonSk);
        updatePanel.add(new JLabel("ID Natural (NK):"));
        updatePanel.add(txtPersonNk);
        updatePanel.add(new JLabel("Nombre:"));
        updatePanel.add(txtPersonName);
        updatePanel.add(new JLabel("Apellido:"));
        updatePanel.add(txtPersonLastName1);
        updatePanel.add(new JLabel("DNI:"));
        updatePanel.add(txtPersonDni);
        updatePanel.add(new JLabel("Teléfono:"));
        updatePanel.add(txtPersonPhone);

        JButton updateButton = new JButton("Actualizar Persona");
        updateButton.addActionListener(e -> updatePerson());

        updatePanel.add(updateButton);
        mainPanel.removeAll();
        mainPanel.add(updatePanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // Metodo para actualizar una persona
    private void updatePerson() {
        int personSk = Integer.parseInt(txtPersonSk.getText());
        DimPerson person = controller.getPerson(personSk);

        if (person != null) {
            person.setPersonNk(txtPersonNk.getText());
            person.setPersonName(txtPersonName.getText());
            person.setPersonLastName1(txtPersonLastName1.getText());
            person.setPersonDni(txtPersonDni.getText());
            person.setPersonPhone(txtPersonPhone.getText());

            controller.updatePerson(person);
            JOptionPane.showMessageDialog(this, "Persona actualizada con éxito");
        } else {
            JOptionPane.showMessageDialog(this, "Persona no encontrada");
        }
    }

    // Metodo para mostrar el formulario de eliminación
    private void showDeletePersonForm() {
        JPanel deletePanel = new JPanel(new GridLayout(2, 2, 5, 5));
        deletePanel.setBorder(BorderFactory.createTitledBorder("Eliminar Persona"));

        txtPersonSk = new JTextField();
        deletePanel.add(new JLabel("ID Persona (SK):"));
        deletePanel.add(txtPersonSk);

        JButton deleteButton = new JButton("Eliminar Persona");
        deleteButton.addActionListener(e -> deletePerson());

        deletePanel.add(deleteButton);
        mainPanel.removeAll();
        mainPanel.add(deletePanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // Metodo para eliminar una persona
    private void deletePerson() {
        int personSk = Integer.parseInt(txtPersonSk.getText());
        controller.deletePerson(personSk);
        JOptionPane.showMessageDialog(this, "Persona eliminada con éxito");
    }

    // Metodo para mostrar el formulario de búsqueda
    private void showSearchPersonForm() {
        JPanel searchPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Buscar Persona"));

        txtPersonSk = new JTextField();
        searchPanel.add(new JLabel("ID Persona (SK):"));
        searchPanel.add(txtPersonSk);

        JButton searchButton = new JButton("Buscar Persona");
        searchButton.addActionListener(e -> searchPerson());

        searchPanel.add(searchButton);
        mainPanel.removeAll();
        mainPanel.add(searchPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // Metodo para buscar una persona por su ID
    private void searchPerson() {
        int personSk = Integer.parseInt(txtPersonSk.getText());
        DimPerson person = controller.getPerson(personSk);

        if (person != null) {
            JOptionPane.showMessageDialog(this, "Persona encontrada: " + person.getPersonName());
        } else {
            JOptionPane.showMessageDialog(this, "Persona no encontrada");
        }
    }
}

