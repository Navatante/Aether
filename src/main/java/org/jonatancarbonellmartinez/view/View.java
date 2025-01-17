package org.jonatancarbonellmartinez.view;

import org.jonatancarbonellmartinez.model.entities.Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

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

    // COLORS
    public static final Color mouseEnteredColor = new Color(78,80,82);
    Color transparentColor = new Color(0, 0, 0, 0);
    Color mousePressedColor = new Color(93,95,98);
    Color borderColor = new Color(48,50,51);
    Color tableBackgroundColor = new Color(70,73,75);

    // FONTS
    Font INPUT_FONT = new Font("Segoe UI", Font.PLAIN, 15);
    Font PLACEHOLDER_FONT = new Font("Segoe UI", Font.ITALIC, 15);

    // REGEX PATTERNS
    String DYNAMIC_HOUR = "^(?=.{1,4}$)[1-9]\\d*\\.?\\d*$";
    String FINAL_HOUR = "^[1-9]\\d?\\.(\\d{1})$";
    String DYNAMIC_FINAL_ID = "\\d{1,17}$"; // requiere al menos un digito y hasta 17 digitos
    String DYNAMIC_CREW_NK = "^[A-Z]{1,3}$";
    String FINAL_CREW_NK = "^[A-Z]{3}$";
    String DYNAMIC_FINAL_SPANISH_WORDS = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s-]{1,30}$";
    String DYNAMIC_PHONE = "^\\d{1,9}$";
    String FINAL_PHONE = "^\\d{9}$";
    String DYNAMIC_DNI = "^\\d{1,8}$";
    String FINAL_DNI = "^\\d{8}$";
    String DYNAMIC_FINAL_NON_NEGATIVE_OR_ZERO_INTEGER = "^[1-9]\\d{0,6}$";
    String DYNAMIC_ROUTE = "^[A-ZÁÉÍÓÚÑ\\s-]{1,40}$";
    String FINAL_ROUTE = "^[A-ZÁÉÍÓÚÑ\\s-]{4,20}-[A-ZÁÉÍÓÚÑ\\s-]{4,20}$";
    String DYNAMIC_FINAL_SEARCH = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ_.-]{1,100}$";


    static JComboBox<String> createFixedComboBox(String[] values, String placeholder) {
        JComboBox<String> comboBox = new JComboBox<>(values);
        comboBox.insertItemAt("", 0);
        comboBox.setSelectedIndex(0);
        comboBox.setRenderer(View.createComboBoxRenderer(placeholder));
        comboBox.addActionListener(e -> View.updateFixedComboBoxAppearance(comboBox, placeholder));
        return comboBox;
    }

    static JComboBox<Entity> createDynamicComboBox(Vector<Entity> entityList, String placeholder) {
        JComboBox<Entity> comboBox = new JComboBox<>(entityList);

        comboBox.insertItemAt(null, 0); // Inserta un elemento vacío al inicio
        comboBox.setSelectedIndex(0); // Selecciona el elemento vacío por defecto
        comboBox.setRenderer(View.createComboBoxRenderer(placeholder)); // Configura el renderer para el placeholder
        comboBox.addActionListener(e -> View.updateDynamicComboBoxAppearance(comboBox, placeholder)); // Actualiza la apariencia
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

    static void setHorizontalAlignmentToFields(JTextField... fields) {
        for (JTextField component : fields) {
            component.setHorizontalAlignment(JTextField.CENTER);
        }
    }


    static void setInitialComboBoxLook(JComponent... comboBox) {
        for (JComponent box : comboBox) {
            box.setForeground(Color.GRAY);
            box.setFont(new Font("Segoe UI", Font.ITALIC, 15));
        }
    }

    static void updateFixedComboBoxAppearance(JComboBox<String> comboBox, String placeholder) {
        if (!comboBox.getSelectedItem().equals("") && !comboBox.getSelectedItem().equals(placeholder)) {
            comboBox.setForeground(Color.LIGHT_GRAY);
            comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        } else {
            comboBox.setForeground(Color.GRAY);
            comboBox.setFont(new Font("Segoe UI", Font.ITALIC, 15));
        }
    }

    static void updateDynamicComboBoxAppearance(JComboBox<Entity> comboBox, String placeholder) {
        Object selectedItem = comboBox.getSelectedItem();

        // Check if selectedItem is null before proceeding
        if (selectedItem != null && !selectedItem.equals("") && !selectedItem.equals(placeholder)) {
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

    static void setMouseListenerToPanel(JPanel panel, JPopupMenu popupMenu) {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) { // Verificar si es un clic derecho
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) { // Verificar si es un clic derecho
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    static void setMouseListenerToPanel(JScrollPane jScrollPane, JPopupMenu popupMenu) {
        jScrollPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) { // Verificar si es un clic derecho
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) { // Verificar si es un clic derecho
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    static void setFontToLabels(Font font, JLabel... labels) {
        for (JLabel label : labels) {
            label.setFont(font);
        }
    }
}
