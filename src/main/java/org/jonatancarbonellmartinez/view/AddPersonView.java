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

    public AddPersonView(MainView mainView, PersonDAO personDAO) {
        super(mainView,"Añadir personal",true);
        this.mainView = mainView; // This is mainly used to do things like setLocationRelativeTo(mainView);
        this.presenter = new AddPersonPresenter(this, personDAO);
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new FlowLayout());
        setResizable(false);
        setSize(914, 360);
        setLocationRelativeTo(mainView);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Combo box test
        add(myComboBox());
        add(myTextField());

        setVisible(true);
    }

    private JComboBox<String> myComboBox() {
        // Create a JComboBox with a list of names
        String[] names = {"Alice", "Bob", "Charlie"};
        JComboBox<String> comboBox = new JComboBox<>(names);

        // Add an empty item to act as a placeholder
        comboBox.insertItemAt("", 0);
        comboBox.setSelectedIndex(0); // Select the empty item initially

        // Set a custom renderer to show the "Name" label when no selection is made
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

        // Add a listener to handle when an item is selected
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (comboBox.getSelectedIndex() == 0) {
                        // If the empty item is selected, do nothing or reset
                        comboBox.setSelectedIndex(0);
                    }
                }
            }
        });
        return comboBox;
    } // It's a test

    private JTextField myTextField() {
        // Create a JTextField for phone number input
        JTextField textField = new JTextField(12); // You can adjust the width as needed

        // Set a placeholder text for the text field
        textField.setText("Número de teléfono");
        textField.setForeground(Color.GRAY); // Set placeholder color
        textField.setFont(new Font("Arial", Font.ITALIC, 12)); // Optional: Italic font for placeholder

        // Add focus listener to handle placeholder behavior
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Clear placeholder text on focus
                if (textField.getText().equals("Número de teléfono")) {
                    textField.setText("");
                    textField.setForeground(Color.LIGHT_GRAY); // Reset to normal color
                    textField.setFont(new Font("Arial",Font.PLAIN,12)); // Enterarme bien cual es la fuente exacta que usa el look and feel para ponerla aqui igual.
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Restore placeholder if the field is empty
                if (textField.getText().isEmpty()) {
                    textField.setText("Número de teléfono");
                    textField.setForeground(Color.GRAY); // Set placeholder color
                    textField.setFont(new Font("Arial", Font.ITALIC, 12));
                }
            }
        });

        return textField;
    } // It's a test.

}
