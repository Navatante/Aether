package org.jonatancarbonellmartinez.presentation.viewmodel;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.jonatancarbonellmartinez.data.database.configuration.GlobalLoadingManager;
import org.jonatancarbonellmartinez.data.database.configuration.DatabaseConnection;
import org.jonatancarbonellmartinez.domain.model.PersonDomain;
import org.jonatancarbonellmartinez.domain.repository.contract.PersonRepository;
import org.jonatancarbonellmartinez.presentation.mapper.PersonDomainUiMapper;
import org.jonatancarbonellmartinez.presentation.model.PersonUI;
import org.jonatancarbonellmartinez.services.CustomLogger;

import javax.inject.Inject;
import java.text.Normalizer;
import java.util.List;
import java.util.ArrayList;

public class PersonViewModel {
    private final PersonRepository repository;
    private final PersonDomainUiMapper uiMapper;
    private final DatabaseConnection databaseConnection;
    private final GlobalLoadingManager loadingManager;
    private final ObservableList<PersonUI> persons = FXCollections.observableArrayList();
    private final FilteredList<PersonUI> filteredPersons = new FilteredList<>(persons);

    // MANTENER estos como Properties porque se usan para bindings en los filtros
    private final BooleanProperty showOnlyActive = new SimpleBooleanProperty(true);
    private final StringProperty searchQuery = new SimpleStringProperty("");
    private final ObjectProperty<PersonUI> selectedPerson = new SimpleObjectProperty<>();

    @Inject
    public PersonViewModel(PersonRepository repository,
                           PersonDomainUiMapper uiMapper,
                           DatabaseConnection databaseConnection,
                           GlobalLoadingManager loadingManager) {
        this.repository = repository;
        this.uiMapper = uiMapper;
        this.databaseConnection = databaseConnection;
        this.loadingManager = loadingManager;
        setupFilters();
    }

    private void setupFilters() {
        filteredPersons.predicateProperty().bind(
                Bindings.createObjectBinding(() ->
                                person -> {
                                    boolean matchesActive = showOnlyActive.get() ?
                                            person.isActive().equals("Activo") :
                                            person.isActive().equals("Inactivo");
                                    boolean matchesSearch = matchesSearch(person, searchQuery.get());
                                    return matchesActive && matchesSearch;
                                },
                        showOnlyActive, searchQuery
                )
        );
    }

    public boolean matchesSearch(PersonUI person, String query) {
        if (query == null || query.isEmpty()) return true;

        String normalizedQuery = normalizeString(query);
        return normalizeString(person.getName()).contains(normalizedQuery) ||
                normalizeString(person.getLastName1()).contains(normalizedQuery) ||
                normalizeString(person.getLastName2()).contains(normalizedQuery);
    }

    // Metodo auxiliar para normalizar strings
    private String normalizeString(String text) {
        if (text == null || text.isEmpty()) return "";

        // Normaliza el texto quitando diacríticos y convirtiendo a minúsculas
        return Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "") // Elimina marcas diacríticas
                .toLowerCase();
    }

    public void loadPersons() {
        databaseConnection
                .executeOperation(repository::getAllPersons, false)
                .thenAccept(domainPersons -> {
                    List<PersonUI> uiPersons = new ArrayList<>();
                    for (PersonDomain personDomain : domainPersons) {
                        uiPersons.add(uiMapper.toUiModel(personDomain));
                    }
                    updatePersonsList(uiPersons);
                })
                .exceptionally(throwable -> {
                    CustomLogger.logError("Error loading persons", (Exception) throwable.getCause());
                    return null;
                });
    }

    private void updatePersonsList(List<PersonUI> newPersons) {
        Platform.runLater(() -> {
            persons.clear();
            persons.addAll(newPersons);
        });
    }

    public void cleanup() {
        persons.clear();
        selectedPerson.set(null);
    }


    // Getters for properties
    public ObservableList<PersonUI> getFilteredPersons() { return filteredPersons; }
    public BooleanProperty showOnlyActiveProperty() { return showOnlyActive; }
    public StringProperty searchQueryProperty() { return searchQuery; }
    public ObjectProperty<PersonUI> selectedPersonProperty() { return selectedPerson; }
}