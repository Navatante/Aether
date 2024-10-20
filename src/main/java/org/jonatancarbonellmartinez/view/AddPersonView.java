package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.model.dao.PersonDAO;
import org.jonatancarbonellmartinez.presenter.AddPersonPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class AddPersonView extends JDialog {
    private MainView mainView;
    private AddPersonPresenter presenter;

    // Components
    private JTextField phoneField;
    private JComboBox<String> nameComboBox;
    private JTextField rankField;
    private JTextField personNameField;
    private JTextField personLastName1Field;
    private JTextField personLastName2Field;
    private JTextField divisionField;
    private JTextField orderField;
    private JTextField roleField;
    private JTextField currentFlagField;

    public AddPersonView(MainView mainView, PersonDAO personDAO) {
        super(mainView, "Añadir personal", true);
        this.mainView = mainView; // This is mainly used to do things like setLocationRelativeTo(mainView);
        this.presenter = new AddPersonPresenter(this, personDAO);
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new FlowLayout(FlowLayout.CENTER)); // Set BorderLayout() instead of FlowLayout() and place the  elements in top, center and the button in the top.
        setResizable(false);
        setSize(914, 360);
        setLocationRelativeTo(mainView);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Initialize UI components
        nameComboBox = myComboBox();
        phoneField = myTextField("Número de teléfono");
        rankField = myTextField("Rango");
        personNameField = myTextField("Nombre");
        personLastName1Field = myTextField("Apellido 1");
        personLastName2Field = myTextField("Apellido 2");
        divisionField = myTextField("División");
        orderField = myTextField("Orden");
        roleField = myTextField("Rol");
        currentFlagField = myTextField("Flag Actual");

        // Add components to the dialog
        add(nameComboBox);
        add(phoneField);
        add(rankField);
        add(personNameField);
        add(personLastName1Field);
        add(personLastName2Field);
        add(divisionField);
        add(orderField);
        add(roleField);
        add(currentFlagField);

        // Add an "Add" button with an action listener
        JButton addButton = new JButton("Añadir");
        addButton.addActionListener(e -> presenter.addPerson());
        add(addButton);

        setVisible(true);
    }

    private JComboBox<String> myComboBox() {
        String[] names = {"Alice", "Bob", "Charlie"};
        JComboBox<String> comboBox = new JComboBox<>(names);
        comboBox.insertItemAt("", 0);
        comboBox.setSelectedIndex(0);
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                if (value == null || value.equals("")) {
                    value = "Nombre"; // Placeholder text
                }
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value.equals("Nombre")) {
                    c.setForeground(Color.GRAY); // Change color to indicate it's a placeholder
                } else {
                    c.setForeground(Color.LIGHT_GRAY);
                }
                return c;
            }
        });

        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (comboBox.getSelectedIndex() == 0) {
                        comboBox.setSelectedIndex(0);
                    }
                }
            }
        });
        return comboBox;
    }

    private JTextField myTextField(String placeholder) {
        JTextField textField = new JTextField(12);
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);
        textField.setFont(new Font("Arial", Font.ITALIC, 12));

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.LIGHT_GRAY);
                    textField.setFont(new Font("Arial", Font.PLAIN, 12));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                    textField.setFont(new Font("Arial", Font.ITALIC, 12));
                }
            }
        });

        return textField;
    }

    // Getter methods for user input
    public String getPersonNk() {
        return (String) nameComboBox.getSelectedItem();
    }

    public String getPersonRank() {
        return rankField.getText();
    }

    public String getPersonName() {
        return personNameField.getText();
    }

    public String getPersonLastName1() {
        return personLastName1Field.getText();
    }

    public String getPersonLastName2() {
        return personLastName2Field.getText();
    }

    public String getPersonPhone() {
        return phoneField.getText();
    }

    public String getPersonDivision() {
        return divisionField.getText();
    }

    public int getPersonOrder() {
        return Integer.parseInt(orderField.getText());
    }

    public String getPersonRol() {
        return roleField.getText();
    }

    public int getPersonCurrentFlag() {
        return Integer.parseInt(currentFlagField.getText());
    }

    public void clearFields() {
        nameComboBox.setSelectedIndex(0);
        phoneField.setText("Número de teléfono");
        rankField.setText("");
        personNameField.setText("");
        personLastName1Field.setText("");
        personLastName2Field.setText("");
        divisionField.setText("");
        orderField.setText("");
        roleField.setText("");
        currentFlagField.setText("");
    }
}
