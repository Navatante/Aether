package org.jonatancarbonellmartinez.presentation.viewmodel;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.jonatancarbonellmartinez.domain.model.Person;
import org.jonatancarbonellmartinez.domain.repository.contract.PersonRepository;
import org.jonatancarbonellmartinez.presentation.mapper.PersonUiMapper;
import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Las JavaFX Properties (StringProperty, IntegerProperty, BooleanProperty, etc.)
 * se usan aquí porque permiten la vinculación de datos (data binding) entre la vista (FXML o código JavaFX) y el modelo de la aplicación.
 */

public class PersonViewModel {
    private final PersonRepository repository;
    private final PersonUiMapper uiMapper;
    private final ObservableList<PersonUI> persons = FXCollections.observableArrayList();
    private final FilteredList<PersonUI> filteredPersons = new FilteredList<>(persons);
    private final BooleanProperty showOnlyActive = new SimpleBooleanProperty(true);
    private final StringProperty searchQuery = new SimpleStringProperty("");
    private final BooleanProperty isLoading = new SimpleBooleanProperty(false);
    private final StringProperty errorMessage = new SimpleStringProperty("");
    private final ObjectProperty<PersonUI> selectedPerson = new SimpleObjectProperty<>();

    @Inject
    public PersonViewModel(PersonRepository repository, PersonUiMapper uiMapper) {  // Update constructor
        this.repository = repository;
        this.uiMapper = uiMapper;
        setupFilters();
    }

    private void setupFilters() {
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
        setLoading(true);
        setError("");

        repository.getAllPersons()
                .thenAccept(domainPersons -> {
                    Platform.runLater(() -> {
                        persons.clear();
                        persons.addAll(domainPersons.stream()
                                .map(uiMapper::toUiModel)
                                .collect(Collectors.toList()));
                        setLoading(false);
                    });
                })
                .exceptionally(throwable -> {
                    Platform.runLater(() -> {
                        setError("Error loading persons: " + throwable.getMessage());
                        setLoading(false);
                    });
                    return null;
                });
    }

    public void savePerson(PersonUI person) {
        setLoading(true);
        setError("");

        Person domainPerson = uiMapper.toDomain(person);
        CompletableFuture<Boolean> future = person.getId() == null ?
                repository.insertPerson(domainPerson) :
                repository.updatePerson(domainPerson, person.getId());

        future.thenAccept(success -> {
                    Platform.runLater(() -> {
                        if (success) {
                            loadPersons();
                        } else {
                            setError("Error saving person");
                        }
                        setLoading(false);
                    });
                })
                .exceptionally(throwable -> {
                    Platform.runLater(() -> {
                        setError("Error saving person: " + throwable.getMessage());
                        setLoading(false);
                    });
                    return null;
                });
    }

    private List<PersonUI> mapToUI(List<Person> domainPersons) {
        return domainPersons.stream()
                .map(person -> {
                    PersonUI ui = new PersonUI();
                    ui.setId(person.getId());
                    ui.setCode(person.getCode());
                    ui.setRank(person.getRank());
                    ui.setName(person.getName());
                    ui.setLastName1(person.getLastName1());
                    ui.setLastName2(person.getLastName2());
                    ui.setPhone(person.getPhone());
                    ui.setDni(person.getDni());
                    ui.setDivision(person.getDivision());
                    ui.setRole(person.getRole());
                    ui.setOrder(person.getOrder());
                    ui.setActive(person.isActive());
                    return ui;
                })
                .collect(Collectors.toList());
    }

    private Person mapToDomain(PersonUI ui) {
        return new Person.Builder()
                .id(ui.getId())
                .code(ui.getCode())
                .rank(ui.getRank())
                .name(ui.getName())
                .lastName1(ui.getLastName1())
                .lastName2(ui.getLastName2())
                .phone(ui.getPhone())
                .dni(ui.getDni())
                .division(ui.getDivision())
                .role(ui.getRole())
                .order(ui.getOrder())
                .isActive(ui.isActive())
                .build();
    }

    public void cleanup() {
        persons.clear();
        selectedPerson.set(null);
        errorMessage.set("");
    }

    private void setLoading(boolean loading) {
        Platform.runLater(() -> isLoading.set(loading));
    }

    private void setError(String error) {
        Platform.runLater(() -> errorMessage.set(error));
    }

    public static class PersonUI {
        private Integer id;
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

        public PersonUI() {
            this.id = null;
        }

        public boolean matchesSearch(String query) {
            if (query == null || query.isEmpty()) return true;

            String lowerQuery = query.toLowerCase();
            return name.get().toLowerCase().contains(lowerQuery) ||
                    lastName1.get().toLowerCase().contains(lowerQuery) ||
                    code.get().toLowerCase().contains(lowerQuery);
        }

        // Getters and Setters
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }

        public String getCode() { return code.get(); }
        public void setCode(String value) { code.set(value); }
        public StringProperty codeProperty() { return code; }

        public String getRank() { return rank.get(); }
        public void setRank(String value) { rank.set(value); }
        public StringProperty rankProperty() { return rank; }

        public String getName() { return name.get(); }
        public void setName(String value) { name.set(value); }
        public StringProperty nameProperty() { return name; }

        public String getLastName1() { return lastName1.get(); }
        public void setLastName1(String value) { lastName1.set(value); }
        public StringProperty lastName1Property() { return lastName1; }

        public String getLastName2() { return lastName2.get(); }
        public void setLastName2(String value) { lastName2.set(value); }
        public StringProperty lastName2Property() { return lastName2; }

        public String getPhone() { return phone.get(); }
        public void setPhone(String value) { phone.set(value); }
        public StringProperty phoneProperty() { return phone; }

        public String getDni() { return dni.get(); }
        public void setDni(String value) { dni.set(value); }
        public StringProperty dniProperty() { return dni; }

        public String getDivision() { return division.get(); }
        public void setDivision(String value) { division.set(value); }
        public StringProperty divisionProperty() { return division; }

        public String getRole() { return role.get(); }
        public void setRole(String value) { role.set(value); }
        public StringProperty roleProperty() { return role; }

        public int getOrder() { return order.get(); }
        public void setOrder(int value) { order.set(value); }
        public IntegerProperty orderProperty() { return order; }

        public boolean isActive() { return active.get(); }
        public void setActive(boolean value) { active.set(value); }
        public BooleanProperty activeProperty() { return active; }
    }

    // Public property getters
    public ObservableList<PersonUI> getFilteredPersons() { return filteredPersons; }
    public BooleanProperty showOnlyActiveProperty() { return showOnlyActive; }
    public StringProperty searchQueryProperty() { return searchQuery; }
    public BooleanProperty isLoadingProperty() { return isLoading; }
    public StringProperty errorMessageProperty() { return errorMessage; }
    public ObjectProperty<PersonUI> selectedPersonProperty() { return selectedPerson; }
}