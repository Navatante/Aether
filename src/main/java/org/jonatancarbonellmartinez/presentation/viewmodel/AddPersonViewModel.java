package org.jonatancarbonellmartinez.presentation.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.jonatancarbonellmartinez.data.database.configuration.DatabaseConnection;
import org.jonatancarbonellmartinez.data.database.configuration.GlobalLoadingManager;
import org.jonatancarbonellmartinez.domain.repository.contract.PersonRepository;
import org.jonatancarbonellmartinez.presentation.mapper.PersonUiMapper;

import javax.inject.Inject;

public class AddPersonViewModel {
    private final PersonRepository repository;
    private final PersonUiMapper uiMapper;
    private final DatabaseConnection databaseConnection;
    private final GlobalLoadingManager loadingManager;

    @Inject
    public AddPersonViewModel(PersonRepository repository,
                           PersonUiMapper uiMapper,
                           DatabaseConnection databaseConnection,
                           GlobalLoadingManager loadingManager) {
        this.repository = repository;
        this.uiMapper = uiMapper;
        this.databaseConnection = databaseConnection;
        this.loadingManager = loadingManager;
    }

}
