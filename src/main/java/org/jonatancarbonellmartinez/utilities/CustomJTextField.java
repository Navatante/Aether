package org.jonatancarbonellmartinez.utilities;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

// TODO use me, sigue por aqui
public class CustomJTextField {

    public static JTextField createTextField(String placeholder, int placeholderLimit, int userLimit, String regex) {
        JTextField textField = new JTextField();

        // Placeholder handling
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });

        // Limit and regex enforcement
        PlainDocument doc = new PlainDocument() {
            @Override
            public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
                if (str == null) return;

                String currentText = getText(0, getLength());
                String proposedText = currentText.substring(0, offset) + str + currentText.substring(offset);

                // Check if within user limit and matches regex
                if (proposedText.length() <= userLimit && proposedText.matches(regex)) {
                    super.insertString(offset, str, attr);
                }
            }
        };

        textField.setDocument(doc);

        // Adjust initial placeholder length if it exceeds the placeholder limit
        if (placeholder.length() > placeholderLimit) {
            textField.setText(placeholder.substring(0, placeholderLimit));
        }

        return textField;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Custom JTextField Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new FlowLayout());

            JTextField textField = createTextField("Placeholder", 10, 3, "\\d*");
            frame.add(textField);

            frame.setSize(400, 200);
            frame.setVisible(true);
        });
    }
}

