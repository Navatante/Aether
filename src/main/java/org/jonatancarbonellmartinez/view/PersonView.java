package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.controller.PersonController;
import org.jonatancarbonellmartinez.model.entities.DimPerson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonView extends JFrame {
    private PersonController controller;
    private JPanel mainPanel;
    private JTextField txtPersonSk, txtPersonNk, txtPersonName, txtPersonLastName1, txtPersonDni, txtPersonPhone;

    public PersonView(PersonController controller) {
        this.controller = controller;
        setTitle("Gestión de Personas - CRUD");
        setSize(600, 400);
        setLocationRelativeTo(null);  // Centra la ventana en la pantalla
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();  // Inicializa los componentes de la interfaz
        setVisible(true);
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

    // Método para mostrar el formulario de creación de una persona
    private void showCreatePersonForm() {
        JPanel createPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        createPanel.setBorder(BorderFactory.createTitledBorder("Crear Persona"));

        txtPersonNk = new JTextField();
        txtPersonName = new JTextField();
        txtPersonLastName1 = new JTextField();
        txtPersonDni = new JTextField();
        txtPersonPhone = new JTextField();

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

    // Método para crear una nueva persona
    private void createPerson() {
        DimPerson newPerson = new DimPerson();
        newPerson.setPersonNk(txtPersonNk.getText());
        newPerson.setPersonName(txtPersonName.getText());
        newPerson.setPersonLastName1(txtPersonLastName1.getText());
        newPerson.setPersonDni(txtPersonDni.getText());
        newPerson.setPersonPhone(txtPersonPhone.getText());
        // Puedes agregar más campos aquí

        controller.createPerson(newPerson);
        JOptionPane.showMessageDialog(this, "Persona creada con éxito");
    }

    // Método para mostrar el formulario de actualización
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

    // Método para actualizar una persona
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

    // Método para mostrar el formulario de eliminación
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

    // Método para eliminar una persona
    private void deletePerson() {
        int personSk = Integer.parseInt(txtPersonSk.getText());
        controller.deletePerson(personSk);
        JOptionPane.showMessageDialog(this, "Persona eliminada con éxito");
    }

    // Método para mostrar el formulario de búsqueda
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

    // Método para buscar una persona por su ID
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

