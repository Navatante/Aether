package org.jonatancarbonellmartinez.exceptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomFormatter extends Formatter {
    private static final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public String format(LogRecord record) {
        StringBuilder sb = new StringBuilder();

        sb.append(dateFormatter.format(LocalDateTime.now()))
                .append(" [").append(record.getLevel()).append("] ");

        sb.append(formatMessage(record));

        if (record.getThrown() != null) {
            sb.append("\nExcepción: ")
                    .append(record.getThrown().getClass().getName())
                    .append(": ").append(record.getThrown().getMessage())
                    .append("\nStack trace:\n");

            for (StackTraceElement element : record.getThrown().getStackTrace()) {
                sb.append("\tat ").append(element.toString()).append("\n");
            }
        }

        sb.append("\n");
        return sb.toString();
    }
}