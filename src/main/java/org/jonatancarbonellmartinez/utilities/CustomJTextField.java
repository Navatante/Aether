package org.jonatancarbonellmartinez.utilities;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

// TODO use me, sigue por aqui
public class CustomJTextField extends JTextField {

    private String placeholder;
    private int userLimit;
    private String regex;

    public CustomJTextField(String placeholder, int placeholderLimit, int userLimit, String regex) {
        super();
        this.placeholder = placeholder;
        this.userLimit = userLimit;
        this.regex = regex;

        // Placeholder handling
        setText(placeholder);
        setForeground(Color.GRAY);

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(placeholder)) {
                    setText("");
                    setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(placeholder);
                    setForeground(Color.GRAY);
                }
            }
        });

        // Limit and regex enforcement
        PlainDocument doc = new PlainDocument() {
            @Override
            public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
                if (str == null) return;

                String currentText = getText(0, getLength()); // esto creo que va fuera
                String proposedText = currentText.substring(0, offset) + str + currentText.substring(offset); // esto creo que va fuera

                // Check if within user limit and matches regex if provided
                if (proposedText.length() <= userLimit && (regex == null || regex.isEmpty() || proposedText.matches(regex))) {
                    super.insertString(offset, str, attr);
                }
            }
        };

        setDocument(doc);
        // TODO bingo jugar con este metodo.
        doc.insertString(0, placeholder, null);
    }
}

