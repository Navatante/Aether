package org.jonatancarbonellmartinez.utilities;

import javax.swing.text.*;
import java.util.regex.Pattern;

public class JonPlainDocument extends PlainDocument {
    private int limit;
    private String regex;
    private Pattern pattern;

    public JonPlainDocument() {
        this.limit = 100; // pongo un limite muy grande para que entre cualquier palabra al inicio (ya que estara el placeholder).
        this.regex = null; // lo pongo null para que no haya ningun restriccion de filtro.
        this.pattern = (regex == null || regex.isEmpty()) ? null : Pattern.compile(regex);
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str == null) return;

        // Validar que no exceda el límite y cumpla con el patrón si existe
        if ((getLength() + str.length()) <= limit && (pattern == null || pattern.matcher(str).matches())) {
            super.insertString(offs, str, a);
        }
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setRegex(String regex) {
        this.regex = regex;
        updatePattern(); // cada vez que se cambie el regex, actualizamos el pattern
    }

    private void updatePattern() {
        this.pattern = (regex == null || regex.isEmpty()) ? null : Pattern.compile(regex);
    }

}
