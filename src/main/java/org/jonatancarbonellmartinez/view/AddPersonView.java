package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.model.dao.PersonDAO;
import org.jonatancarbonellmartinez.presenter.AddPersonPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class AddPersonView extends JDialog {
    private MainView mainView;
    private AddPersonPresenter presenter;

    // Components
    private JTextField phoneField;
    private JComboBox<String> empleoBox;
    private JTextField personNameField;
    private JTextField personLastName1Field;
    private JTextField personLastName2Field;
    private JComboBox<String> divisionBox;
    private JTextField orderField;
    private JComboBox<String> rolBox;
    private JTextField dniField;
    private JComboBox<String> currentFlagBox;

    public AddPersonView(MainView mainView, PersonDAO personDAO) {
        super(mainView, "Añadir personal", true);
        this.mainView = mainView; // This is mainly used to do things like setLocationRelativeTo(mainView);
        this.presenter = new AddPersonPresenter(this, personDAO);
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setResizable(false);
        //setSize(540, 270);
        setSize(450,300);
        setLocationRelativeTo(mainView);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Create the main panel to hold all form fields.
        JPanel centerPanel = new JPanel();
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        // Create a bottom panel for the button
        JPanel bottomPanel = new JPanel();
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 20, 10));

        // Initialize UI components
        Dimension fieldSize = new Dimension(180, 25); // Define a common dimension
        empleoBox = myComboBox(new String[]{
                "CF","TCOL","CC","CTE","TN","CAP","AN","TTE","STTE",
                "BG","SG1","SGTO","CBMY","CB1","CBO","SDO","MRO"},"Empleo");
        personNameField = myTextField("Nombre");
        personLastName1Field = myTextField("Apellido 1");
        personLastName2Field = myTextField("Apellido 2");
        phoneField = myTextField("Teléfono");
        dniField = myTextField("DNI");
        divisionBox = myComboBox(new String[] {"Operaciones","Mantenimiento","Seguridad de vuelo","Estandarización","Inteligencia"},"División");
        rolBox = myComboBox(new String[] {"Piloto", "Dotación"},"Rol");
        orderField = myTextField("Orden");
        currentFlagBox = myComboBox(new String[]{"Activo", "Inactivo"},"Situación");


        // Set prefered size
        empleoBox.setPreferredSize(fieldSize);
        personNameField.setPreferredSize(fieldSize);
        personLastName1Field.setPreferredSize(fieldSize);
        personLastName2Field.setPreferredSize(fieldSize);
        phoneField.setPreferredSize(fieldSize);
        divisionBox.setPreferredSize(fieldSize);
        orderField.setPreferredSize(fieldSize);
        rolBox.setPreferredSize(fieldSize);
        dniField.setPreferredSize(fieldSize);
        currentFlagBox.setPreferredSize(fieldSize);


        // Add components to the dialog
        centerPanel.add(empleoBox);
        centerPanel.add(personNameField);
        centerPanel.add(personLastName1Field);
        centerPanel.add(personLastName2Field);
        centerPanel.add(phoneField);
        centerPanel.add(dniField);
        centerPanel.add(divisionBox);
        centerPanel.add(rolBox);
        centerPanel.add(orderField);
        centerPanel.add(currentFlagBox);

        // Add an "Add" button with an action listener
        JButton addButton = new JButton("Añadir");
        addButton.addActionListener(e -> presenter.addPerson());
        bottomPanel.add(addButton);

        setVisible(true);
    }

    private JComboBox<String> myComboBox(String[] listValues, String placeHolder) {
        JComboBox<String> comboBox = new JComboBox<>(listValues);
        comboBox.insertItemAt("", 0); // Add empty item at index 0
        comboBox.setSelectedIndex(0); // Set it as the selected item initially
        comboBox.setForeground(Color.GRAY); // Set default color for placeholder
        comboBox.setFont(new Font("Segoe UI", Font.ITALIC, 15)); // Initial font for placeholder

        // Custom renderer to display the placeholder
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                // If the value is empty or null, use the placeholder
                if (value == null || value.equals("")) {
                    value = placeHolder; // Use placeholder text
                }
                // Call super to get the default component
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                // Set color and font based on whether the placeholder is shown
                if (value.equals(placeHolder)) {
                    c.setForeground(Color.GRAY); // Placeholder color
                    c.setFont(new Font("Segoe UI", Font.ITALIC, 15)); // Italic font for placeholder
                } else {
                    c.setForeground(Color.LIGHT_GRAY); // Normal color for selected items
                    c.setFont(new Font("Segoe UI", Font.PLAIN, 15)); // Plain font for other selections
                }
                return c;
            }
        });

        // Add an ActionListener to handle selection changes
        comboBox.addActionListener(e -> {
            // Check the selected item and set the foreground color and font accordingly
            if (!comboBox.getSelectedItem().equals("") && !comboBox.getSelectedItem().equals(placeHolder)) {
                comboBox.setForeground(Color.LIGHT_GRAY); // Change to white if selected item is not the placeholder
                comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 15)); // Set font to plain
            } else {
                comboBox.setForeground(Color.GRAY); // Set back to gray if it is the placeholder
                comboBox.setFont(new Font("Segoe UI", Font.ITALIC, 15)); // Set back to italic
            }
        });

        return comboBox;
    }




    private JTextField myTextField(String placeholder) {
        JTextField textField = new JTextField(); // 12 columns means the text field is wide enough to show about 12 characters without scrolling.
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);
        textField.setFont(new Font("Segoe UI", Font.ITALIC, 15));

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.LIGHT_GRAY);
                    textField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                    textField.setFont(new Font("Segoe UI", Font.ITALIC, 15));
                }
            }
        });

        return textField;
    }

    // Getter methods for user input
    public String getPersonNk() {
        return (String) empleoBox.getSelectedItem();
    }

    public String getPersonRank() {
        return empleoBox.getSelectedItem().toString(); // maybe is not correct
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
        return divisionBox.getSelectedItem().toString();
    }

    public int getPersonOrder() {
        return Integer.parseInt(orderField.getText());
    }

    public String getPersonRol() {
        return rolBox.getSelectedItem().toString();
    }

    public int getPersonCurrentFlag() {
        int result = 1;
        if(currentFlagBox.getSelectedItem().equals("Inactivo")) {
            result = 0;
        }
        return result;
    }

    public void clearFields() {
        empleoBox.setSelectedIndex(0);
        phoneField.setText("Número de teléfono");
        personNameField.setText("");
        personLastName1Field.setText("");
        personLastName2Field.setText("");
        divisionBox.setSelectedIndex(0);
        orderField.setText("");
        rolBox.setSelectedIndex(0);
        currentFlagBox.setSelectedIndex(0);
    }
}
