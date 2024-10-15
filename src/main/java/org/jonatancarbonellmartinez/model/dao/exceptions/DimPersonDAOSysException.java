package org.jonatancarbonellmartinez.model.dao.exceptions;

import java.sql.SQLException;

public class DimPersonDAOSysException extends RuntimeException {
    /**
     * Constructor
     * @param str    a string that explains what the exception condition is
     */
    public DimPersonDAOSysException(String str, SQLException e) {
        super(str);
    }

    /**
     * Default constructor. Takes no arguments
     */
    public DimPersonDAOSysException() {
        super();
    }

}
