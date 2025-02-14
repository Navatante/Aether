package org.jonatancarbonellmartinez.services.util;

import javax.inject.Singleton;
import java.text.Normalizer;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/**
 * Utility class for text formatting and input validation operations.
 * This class provides text formatters for various types of input (numeric, decimal, DNI, phone numbers)
 * with built-in validation and formatting capabilities. Each formatter provides feedback through
 * FormattingResult objects that contain validation status and messages.
 *
 * <p>This class is thread-safe and follows singleton pattern through Dagger.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * TextField phoneField = new TextField();
 * TextFormatter<FormattingResult> formatter = textFormatter.createPhoneFormatter();
 * phoneField.setTextFormatter(formatter);
 *
 * // To display validation messages
 * Label validationLabel = new Label();
 * phoneField.textProperty().addListener((obs, oldVal, newVal) -> {
 *     FormattingResult result = formatter.getValue();
 *     validationLabel.setText(result.getMessage());
 * });
 * </pre>
 */

/**
 * ### EJEMPLO DE USO ####
 *
 * TextField phoneField = new TextField();
 * TextFormatter<FormattingResult> formatter = textFormatter.createPhoneFormatter();
 * phoneField.setTextFormatter(formatter);
 *
 * // Para mostrar los mensajes de validación
 * Label validationLabel = new Label();
 * phoneField.textProperty().addListener((obs, oldVal, newVal) -> {
 *     FormattingResult result = formatter.getValue();
 *     if (result != null) {
 *         validationLabel.setText(result.getMessage());
 *         validationLabel.setStyle(result.isValid() ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
 *     }
 * });
 */
@Singleton
public class TextFormatter implements TextFormatterService {
    // Compile patterns once for better performance
    private static final Pattern DIACRITICS_PATTERN = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");
    private static final Pattern NUMERIC_PATTERN = Pattern.compile("\\d*");
    private static final Pattern DECIMAL_PATTERN = Pattern.compile("\\d*\\.?\\d*");
    private static final Pattern ALPHABETIC_WITHOUT_SPACES_PATTERN = Pattern.compile("[a-zA-ZñÑáéíóúÁÉÍÓÚ]*");
    private static final Pattern ALPHABETIC_WITH_SPACES_PATTERN = Pattern.compile("[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]*");
    private static final Pattern ALPHANUMERIC_WITHOUT_SPACES_PATTERN = Pattern.compile("[a-zA-Z0-9]*");
    private static final Pattern ALPHANUMERIC_WITH_SPACES_PATTERN = Pattern.compile("[a-zA-Z0-9\\s]*");

    /**
     * Represents the result of a text formatting operation.
     */
    public class FormattingResult {
        private final boolean valid;
        private final String message;
        private final String formattedText;

        public FormattingResult(boolean valid, String message, String formattedText) {
            this.valid = valid;
            this.message = message;
            this.formattedText = formattedText;
        }

        public boolean isValid() { return valid; }
        public String getMessage() { return message; }
        public String getFormattedText() { return formattedText; }
    }

    /**
     * Creates a TextFormatter that only allows numeric input.
     *
     * @param maxLength Maximum number of digits allowed
     * @return TextFormatter configured for numeric input with validation
     */
    @Override
    public javafx.scene.control.TextFormatter<String> createNumericFormatter(int maxLength) {
        UnaryOperator<javafx.scene.control.TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            if (newText.isEmpty() || NUMERIC_PATTERN.matcher(newText).matches() && newText.length() <= maxLength) {
                return change;
            }
            return null;
        };

        return new javafx.scene.control.TextFormatter<>(filter);
    }

    /**
     * Creates a TextFormatter that only allows aplhanumeric without spaces input.
     *
     * @param maxLength Maximum number of digits allowed
     * @return TextFormatter configured for numeric input with validation
     */
    @Override
    public javafx.scene.control.TextFormatter<String> createAlphabeticWithoutSpacesFormatter(int maxLength) {
        UnaryOperator<javafx.scene.control.TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            if (newText.isEmpty() || ALPHABETIC_WITHOUT_SPACES_PATTERN.matcher(newText).matches() && newText.length() <= maxLength) {
                return change;
            }
            return null;
        };

        return new javafx.scene.control.TextFormatter<>(filter);
    }

    /**
     * Creates a TextFormatter that only allows aplhanumeric without spaces input.
     *
     * @param maxLength Maximum number of digits allowed
     * @return TextFormatter configured for numeric input with validation
     */
    @Override
    public javafx.scene.control.TextFormatter<String> createAlphabeticWithSpacesFormatter(int maxLength) {
        UnaryOperator<javafx.scene.control.TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            if (newText.isEmpty() || ALPHABETIC_WITH_SPACES_PATTERN.matcher(newText).matches() && newText.length() <= maxLength) {
                return change;
            }
            return null;
        };

        return new javafx.scene.control.TextFormatter<>(filter);
    }

    /**
     * Creates a TextFormatter that allows decimal numbers with validation feedback.
     *
     * @param maxLength Maximum total length including decimal point
     * @param maxDecimals Maximum number of decimal places allowed
     * @return TextFormatter configured for decimal input with validation
     */
    @Override
    public javafx.scene.control.TextFormatter<FormattingResult> createDecimalFormatter(int maxLength, int maxDecimals) {
        UnaryOperator<javafx.scene.control.TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            FormattingResult result;

            if (newText.isEmpty()) {
                result = new FormattingResult(true, "", "");
                return change;
            }

            if (!DECIMAL_PATTERN.matcher(newText).matches()) {
                result = new FormattingResult(false, "Formato decimal inválido", change.getControlText());
                return null;
            }

            if (newText.length() > maxLength) {
                result = new FormattingResult(false,
                        String.format("El número no puede tener más de %d caracteres", maxLength),
                        change.getControlText());
                return null;
            }

            int dotIndex = newText.indexOf('.');
            if (dotIndex != -1 && newText.length() - dotIndex - 1 > maxDecimals) {
                result = new FormattingResult(false,
                        String.format("Máximo %d decimales permitidos", maxDecimals),
                        change.getControlText());
                return null;
            }

            result = new FormattingResult(true, "Formato válido", newText);
            return change;
        };
        return new javafx.scene.control.TextFormatter<>(filter);
    }

    /**
     * Creates a TextFormatter for phone numbers with automatic formatting and validation.
     *
     * @return TextFormatter configured for phone number input with validation
     */
    @Override
    public javafx.scene.control.TextFormatter<FormattingResult> createPhoneFormatter() {
        UnaryOperator<javafx.scene.control.TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            FormattingResult result;

            if (newText.isEmpty()) {
                result = new FormattingResult(true, "", "");
                return change;
            }

            String cleaned = newText.replaceAll("\\D", "");

            if (cleaned.length() > 9) {
                result = new FormattingResult(false,
                        "El número de teléfono no puede tener más de 9 dígitos",
                        change.getControlText());
                return null;
            }

            if (cleaned.length() >= 3) {
                StringBuilder formatted = new StringBuilder(cleaned.substring(0, 3));
                if (cleaned.length() > 3) {
                    formatted.append("-").append(cleaned.substring(3, Math.min(6, cleaned.length())));
                    if (cleaned.length() > 6) {
                        formatted.append("-").append(cleaned.substring(6));
                    }
                }
                change.setText(formatted.toString());
                change.setRange(0, change.getControlText().length());
            } else {
                change.setText(cleaned);
                change.setRange(0, change.getControlText().length());
            }

            result = new FormattingResult(
                    cleaned.length() == 9,
                    cleaned.length() == 9 ? "Número de teléfono válido" :
                            String.format("Faltan %d dígitos", 9 - cleaned.length()),
                    change.getControlNewText()
            );

            return change;
        };
        return new javafx.scene.control.TextFormatter<>(filter);
    }

    /**
     * Normalizes text by removing diacritics, extra spaces, and converting to lowercase.
     *
     * @param text Text to normalize
     * @return Normalized text or empty string if input is null
     */
    @Override
    public String normalizeText(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        normalized = DIACRITICS_PATTERN.matcher(normalized).replaceAll("");
        normalized = normalized.toLowerCase().trim();
        return WHITESPACE_PATTERN.matcher(normalized).replaceAll(" ");
    }

    /**
     * Creates a TextFormatter with a custom pattern and validation.
     *
     * @param pattern Regular expression pattern to match
     * @param maxLength Maximum length of input
     * @param validMessage Message to show when input is valid
     * @param invalidMessage Message to show when input is invalid
     * @return TextFormatter with custom pattern and validation
     */
    @Override
    public javafx.scene.control.TextFormatter<FormattingResult> createCustomPatternFormatter(
            Pattern pattern,
            int maxLength,
            String validMessage,
            String invalidMessage) {
        UnaryOperator<javafx.scene.control.TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            FormattingResult result;

            if (newText.isEmpty()) {
                result = new FormattingResult(true, "", "");
                return change;
            }

            if (!pattern.matcher(newText).matches() || newText.length() > maxLength) {
                result = new FormattingResult(false, invalidMessage, change.getControlText());
                return null;
            }

            result = new FormattingResult(true, validMessage, newText);
            return change;
        };
        return new javafx.scene.control.TextFormatter<>(filter);
    }
}