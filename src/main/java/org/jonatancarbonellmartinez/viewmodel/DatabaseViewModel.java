package org.jonatancarbonellmartinez.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.jonatancarbonellmartinez.utilities.Database;

public class DatabaseViewModel {
    private final Database database;
    private final BooleanProperty databaseConnected;
    private final StringProperty errorMessage;

    public DatabaseViewModel() {
        this.database = Database.getInstance();
        this.databaseConnected = new SimpleBooleanProperty(false);
        this.errorMessage = new SimpleStringProperty("");
    }

    public void checkDatabaseConnection() {
        try {
            if (database.isDatabaseFilePresent()) {
                database.getConnection();
                databaseConnected.set(true);
            } else {
                errorMessage.set("Database file not found. Please select a database file.");
            }
        } catch (Exception e) {
            errorMessage.set("Error connecting to database: " + e.getMessage());
        }
    }

    public void setDatabaseFile(String filePath) {
        try {
            database.setDatabasePath(filePath);
            database.getConnection();
            databaseConnected.set(true);
        } catch (Exception e) {
            errorMessage.set("Error connecting to database: " + e.getMessage());
        }
    }

    public BooleanProperty getDatabaseConnectedProperty() {
        return databaseConnected;
    }

    public StringProperty getErrorMessageProperty() {
        return errorMessage;
    }
}
