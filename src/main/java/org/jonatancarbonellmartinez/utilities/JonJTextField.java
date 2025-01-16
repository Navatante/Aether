package org.jonatancarbonellmartinez.utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

// TODO use me, sigue por aqui
public class JonJTextField extends JTextField {
    private String placeholder;
    private int userLimit;
    private String regex;
    JonPlainDocument jonPlainDocument;
    private Font userInputFont;
    private Font placeholderFont;

    public JonJTextField(String placeholder, int userLimit, String regex) {
        super();
        this.placeholder = placeholder;
        this.userLimit = userLimit;
        this.regex = regex;
        this.jonPlainDocument = new JonPlainDocument(); // de primeras no le pongo ningun limite ni filtro, hasta que no gane focus.
        this.userInputFont = new Font("Segoe UI", Font.PLAIN, 15);
        this.placeholderFont = new Font("Segoe UI", Font.ITALIC, 15);
        changeTextFontAndColor(placeholder, placeholderFont, Color.GRAY);
        setDocument(this.jonPlainDocument);
        addFocusListener();
    }


    private void addFocusListener() {
        addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(placeholder)) {
                    changeTextFontAndColor("", userInputFont, Color.LIGHT_GRAY);
                    jonPlainDocument.setLimit(userLimit);
                    jonPlainDocument.setRegex(regex);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    jonPlainDocument.setLimit(100);
                    jonPlainDocument.setRegex(null);
                    changeTextFontAndColor(placeholder, placeholderFont, Color.GRAY);
                }
            }
        });
    }

    private void changeTextFontAndColor(String text, Font font, Color color) {
        setText(text);
        setFont(font);
        setForeground(color);
    }

    // Getters y Setters
    public Font getUserInputFont() {
        return userInputFont;
    }

    public void setUserInputFont(Font userInputFont) {
        this.userInputFont = userInputFont;
    }

    public Font getPlaceholderFont() {
        return placeholderFont;
    }

    public void setPlaceholderFont(Font placeholderFont) {
        this.placeholderFont = placeholderFont;
    }
}

