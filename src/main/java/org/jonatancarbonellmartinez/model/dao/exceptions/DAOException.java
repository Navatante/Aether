package org.jonatancarbonellmartinez.model.dao.exceptions;

import java.sql.SQLException;

public class DAOException extends RuntimeException {
    /**
     * Constructor
     * @param str    a string that explains what the exception condition is
     */
    public DAOException(String str, SQLException e) {
        super(str);
    }
    public DAOException(String str) {}

    /**
     * Default constructor. Takes no arguments
     */
    public DAOException() {
        super();
    }

}
