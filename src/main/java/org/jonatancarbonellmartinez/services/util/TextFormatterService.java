package org.jonatancarbonellmartinez.services.util;

import java.util.regex.Pattern;

public interface TextFormatterService {
    javafx.scene.control.TextFormatter<String> createNumericFormatter(int maxLength);
    javafx.scene.control.TextFormatter<String> createAlphabeticWithoutSpacesFormatter(int maxLength);
    javafx.scene.control.TextFormatter<String> createAlphabeticWithSpacesFormatter(int maxLength);
    javafx.scene.control.TextFormatter<TextFormatter.FormattingResult> createDecimalFormatter(int maxLength, int maxDecimals);
    javafx.scene.control.TextFormatter<TextFormatter.FormattingResult> createPhoneFormatter();
    String normalizeText(String text);
    javafx.scene.control.TextFormatter<TextFormatter.FormattingResult> createCustomPatternFormatter(
            Pattern pattern,
            int maxLength,
            String validMessage,
            String invalidMessage);

}
