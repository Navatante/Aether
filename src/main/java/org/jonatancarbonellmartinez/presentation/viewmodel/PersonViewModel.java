package org.jonatancarbonellmartinez.presentation.viewmodel;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.jonatancarbonellmartinez.domain.model.Person;
import org.jonatancarbonellmartinez.domain.repository.contract.PersonRepository;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

/**
 * Maneja la lógica de la UI y la conexión con los datos.
 * ObservableList y FilteredList permiten filtrar y mostrar personas en la UI.
 * Se inyecta PersonRepository para acceder a los datos.
 */

public class PersonViewModel {
    private final PersonRepository repository;

    // UI State
    private final ObservableList<PersonUI> persons = FXCollections.observableArrayList();
    private final FilteredList<PersonUI> filteredPersons = new FilteredList<>(persons);
    private final BooleanProperty showOnlyActive = new SimpleBooleanProperty(true);
    private final StringProperty searchQuery = new SimpleStringProperty("");
    private final BooleanProperty isLoading = new SimpleBooleanProperty(false);
    private final StringProperty errorMessage = new SimpleStringProperty("");

    // Selection state
    private final ObjectProperty<PersonUI> selectedPerson = new SimpleObjectProperty<>();

    @Inject
    public PersonViewModel(PersonRepository repository) {
        this.repository = repository;
        setupFilters();
    }

    private void setupFilters() {
        // Combine active filter and search filter
        filteredPersons.predicateProperty().bind(
                Bindings.createObjectBinding(() ->
                                person -> {
                                    boolean matchesActive = !showOnlyActive.get() || person.isActive();
                                    boolean matchesSearch = person.matchesSearch(searchQuery.get());
                                    return matchesActive && matchesSearch;
                                },
                        showOnlyActive, searchQuery
                )
        );
    }

    public void loadPersons() {
        isLoading.set(true);
        errorMessage.set("");

        repository.getAllPersons()
                .thenAccept(personList -> {
                    Platform.runLater(() -> {
                        persons.clear();
                        persons.addAll(mapToUI(personList));
                        isLoading.set(false);
                    });
                })
                .exceptionally(throwable -> {
                    Platform.runLater(() -> {
                        errorMessage.set("Error loading persons: " + throwable.getMessage());
                        isLoading.set(false);
                    });
                    return null;
                });
    }

    public void savePerson(PersonUI person) {
        isLoading.set(true);
        errorMessage.set("");

        Person domainPerson = mapToDomain(person);
        CompletableFuture<Boolean> future = person.getId() == null ?
                repository.insertPerson(domainPerson) :
                repository.updatePerson(domainPerson, person.getId());

        future.thenAccept(success -> {
                    Platform.runLater(() -> {
                        if (success) {
                            loadPersons(); // Reload to get updated data
                        } else {
                            errorMessage.set("Error saving person");
                        }
                        isLoading.set(false);
                    });
                })
                .exceptionally(throwable -> {
                    Platform.runLater(() -> {
                        errorMessage.set("Error saving person: " + throwable.getMessage());
                        isLoading.set(false);
                    });
                    return null;
                });
    }

    // UI Model
    public static class PersonUI {
        private final Integer id;
        private final StringProperty code = new SimpleStringProperty();
        private final StringProperty rank = new SimpleStringProperty();
        private final StringProperty name = new SimpleStringProperty();
        private final StringProperty lastName1 = new SimpleStringProperty();
        private final StringProperty lastName2 = new SimpleStringProperty();
        private final StringProperty phone = new SimpleStringProperty();
        private final StringProperty dni = new SimpleStringProperty();
        private final StringProperty division = new SimpleStringProperty();
        private final StringProperty role = new SimpleStringProperty();
        private final IntegerProperty order = new SimpleIntegerProperty();
        private final BooleanProperty active = new SimpleBooleanProperty();

        // TODO Add getters/setters for properties

        public boolean matchesSearch(String query) {
            if (query == null || query.isEmpty()) return true;

            String lowerQuery = query.toLowerCase();
            return name.get().toLowerCase().contains(lowerQuery) ||
                    lastName1.get().toLowerCase().contains(lowerQuery) ||
                    code.get().toLowerCase().contains(lowerQuery);
        }
    }

    // Getters for properties
    public ObservableList<PersonUI> getFilteredPersons() {
        return filteredPersons;
    }

    public BooleanProperty showOnlyActiveProperty() {
        return showOnlyActive;
    }

    public StringProperty searchQueryProperty() {
        return searchQuery;
    }

    public BooleanProperty isLoadingProperty() {
        return isLoading;
    }

    public StringProperty errorMessageProperty() {
        return errorMessage;
    }

    public ObjectProperty<PersonUI> selectedPersonProperty() {
        return selectedPerson;
    }
}
