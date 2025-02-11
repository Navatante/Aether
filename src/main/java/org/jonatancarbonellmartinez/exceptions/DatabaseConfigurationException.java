package org.jonatancarbonellmartinez.exceptions;

public class DatabaseConfigurationException extends RuntimeException {
    public DatabaseConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
