package org.jonatancarbonellmartinez.utilities;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.util.regex.Pattern;


// TODO aplicar esta clase a todos los JtextFields posible y evitar los mensajes de "la has cagado" (conisguiendo asi evitar que la cague antes de que la cague)
public class JTextFieldFilterPlusLimit extends PlainDocument {
    private final int limit;
    private final Pattern pattern;

    /**
     * Constructor para establecer un límite y un patrón de validación.
     *
     * @param limit Número máximo de caracteres permitidos.
     * @param regex Expresión regular para validar la entrada.
     * @throws IllegalArgumentException Si el límite es <= 0 o el patrón es nulo/vacío.
     */

    public JTextFieldFilterPlusLimit(int limit, String regex) {
        if (limit <= 0) {
            throw new IllegalArgumentException("El límite debe ser mayor a 0.");
        }
        if (regex == null || regex.isEmpty()) {
            throw new IllegalArgumentException("El patrón no puede ser nulo o vacío.");
        }
        this.limit = limit;
        this.pattern = Pattern.compile(regex);
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str == null) return;

        // Validar que no exceda el límite y cumpla con el patrón
        if ((getLength() + str.length()) <= limit && pattern.matcher(str).matches()) {
            super.insertString(offs, str, a);
        }
    }
}
