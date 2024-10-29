package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.utilities.LimitDocumentFilter;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public interface View {

    default void initializeUI() {
        setupUIProperties();
        createPanels();
        createComponents();
        configurePanels();
        configureComponents();
        assemblePanels();
        assembleComponents();
        addActionListeners();
    };

    /**
     * createPanels();
     * createComponents();
     * configurePanels();
     * configureComponents();
     * assemblePanels();
     * assembleComponents();
     * setupUIProperties();
     * addActionListeners();
     */

    void setupUIProperties();
    void createPanels();
    void createComponents();
    void configurePanels();
    void configureComponents();
    void assemblePanels();
    void assembleComponents();
    void addActionListeners();

    /**
     * UTILITY STATIC MEMBERS ON INTERFACES ACT AS UTILITY FIELDS AND METHODS, THERE IS NO NEED FOR A UTILITY CLASS.
     */

    // To be used when Placeholder is larger than input.
    static JTextField createTextField(String placeholder) {
        JTextField textField = new JTextField(placeholder);
        View.setPlaceholder(textField, placeholder);
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                View.onTextFieldFocusGained(textField, placeholder);
            }

            @Override
            public void focusLost(FocusEvent e) {
                View.onTextFieldFocusLost(textField, placeholder);
            }
        });
        return textField;
    }

    static JTextField createTextField(String placeholder, int inputLimit, int placeHolderLimit) {
        JTextField textField = new JTextField(placeholder);
        View.setPlaceholder(textField, placeholder);
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                View.onTextFieldFocusGained(textField, placeholder, inputLimit);
            }

            @Override
            public void focusLost(FocusEvent e) {
                View.onTextFieldFocusLost(textField, placeholder, placeHolderLimit);
            }
        });
        return textField;
    }

    static JComboBox<String> createComboBox(String[] values, String placeholder) {
        JComboBox<String> comboBox = new JComboBox<>(values);
        comboBox.insertItemAt("", 0);
        comboBox.setSelectedIndex(0);
        comboBox.setRenderer(View.createComboBoxRenderer(placeholder));
        comboBox.addActionListener(e -> View.updateComboBoxAppearance(comboBox, placeholder));
        return comboBox;
    }

    static void addComponentsToPanel(JPanel panel, JComponent... components) {
        for (JComponent component : components) {
            panel.add(component);
        }
    }

    static void addMenusToMenu(JComponent component, JMenuItem... menus) {
        for (JMenuItem menu : menus) {
            component.add(menu);
        }
    }

    static void setPreferredSizeForComponents(Dimension size, JComponent... components) {
        for (JComponent component : components) {
            component.setPreferredSize(size);
        }
    }

    static void setDocumentFilter(JTextField field, int limit) {
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new LimitDocumentFilter(limit));
    }

    static void setPlaceholder(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);
        textField.setFont(new Font("Segoe UI", Font.ITALIC, 15));
    }

    static void onTextFieldFocusGained(JTextField textField, String placeholder) {
        if (textField.getText().equals(placeholder)) {
            textField.setText("");
            textField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            textField.setForeground(Color.LIGHT_GRAY);
        }
    }

    static void onTextFieldFocusGained(JTextField textField, String placeholder, int limit) {
        if (textField.getText().equals(placeholder)) {
            View.setDocumentFilter(textField,limit);
            textField.setText("");
            textField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            textField.setForeground(Color.LIGHT_GRAY);
        }
    }

    static void onTextFieldFocusLost(JTextField textField, String placeholder) {
        if (textField.getText().isEmpty()) {
            View.setPlaceholder(textField, placeholder);
        }
    }

    static void onTextFieldFocusLost(JTextField textField, String placeholder, int limit) {
        if (textField.getText().isEmpty()) {
            View.setDocumentFilter(textField,limit);
            View.setPlaceholder(textField, placeholder);
        }
    }

    static void setInitialComboBoxLook(JComponent... comboBox) {
        for (JComponent box : comboBox) {
            box.setForeground(Color.GRAY);
            box.setFont(new Font("Segoe UI", Font.ITALIC, 15));
        }
    }

    static void updateComboBoxAppearance(JComboBox<String> comboBox, String placeholder) {
        if (!comboBox.getSelectedItem().equals("") && !comboBox.getSelectedItem().equals(placeholder)) {
            comboBox.setForeground(Color.LIGHT_GRAY);
            comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        } else {
            comboBox.setForeground(Color.GRAY);
            comboBox.setFont(new Font("Segoe UI", Font.ITALIC, 15));
        }
    }

    static ListCellRenderer<Object> createComboBoxRenderer(String placeholder) {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                if (value == null || value.equals("")) value = placeholder;
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                c.setFont(new Font("Segoe UI", value.equals(placeholder) ? Font.ITALIC : Font.PLAIN, 15));
                c.setForeground(value.equals(placeholder) ? Color.GRAY : Color.LIGHT_GRAY);
                return c;
            }
        };
    }
}
