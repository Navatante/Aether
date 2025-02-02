package org.jonatancarbonellmartinez.components;

import javax.swing.text.AbstractDocument;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;


public class JonJTextField extends JTextField {
    private String placeholder;
    private String dynamicRegex;
    private String finalRegex; // he quitado el input limit porque ya en el finalRegex puedo poner el limite de caracteres.
    private Font inputFont;
    private Font placeholderFont;

    public JonJTextField(Font inputFont, Font placeholderFont, String placeholder, String dynamicRegex, String finalRegex) {
        this.inputFont = inputFont;
        this.placeholderFont = placeholderFont;
        this.placeholder = placeholder;
        this.dynamicRegex = dynamicRegex;
        this.finalRegex = finalRegex;
        changeTextFontAndColor(placeholder, placeholderFont, Color.GRAY);
        setFocusListener();
        setInputVerifier();
    }

    private void setFocusListener() {
        addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(placeholder)) {
                    changeTextFontAndColor("", inputFont, Color.LIGHT_GRAY);
                    setDocumentFilter(); // Solo pongo el documentfilter cuando gano el focus y se lo quitare al perderlo.
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    ((AbstractDocument) getDocument()).setDocumentFilter(null); // Remove DocumentFilter by resetting the Document
                    changeTextFontAndColor(placeholder, placeholderFont, Color.GRAY); // Restore placeholder appearance
                }
            }
        });
    }

    private void setDocumentFilter() {
        // Configurar el filtro para que solo se puedan escribir valores válidos
        ((AbstractDocument) this.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                StringBuilder currentText = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
                currentText.insert(offset, string);

                if (currentText.toString().matches(dynamicRegex)) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                StringBuilder currentText = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
                currentText.replace(offset, offset + length, text);

                if (currentText.toString().matches(dynamicRegex)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            @Override
            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                super.remove(fb, offset, length); // Siempre permitir eliminar texto
            }
        });
    }

    private void setInputVerifier() {
        // Configurar el InputVerifier para bloquear pérdida de foco si el texto es inválido
        this.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                String text = getText();
                if (text.equals(placeholder) || text.isEmpty()) {
                    return true;
                } else if (!text.matches(finalRegex)) {
                    JOptionPane.showMessageDialog(input, "Cagada.", "Error", JOptionPane.ERROR_MESSAGE);
                    requestFocusInWindow();
                    return false;
                } else {
                    return true;
                }
            }
        });
    }

    private void changeTextFontAndColor(String text, Font font, Color color) {
        setText(text);
        setFont(font);
        setForeground(color);
    }
}