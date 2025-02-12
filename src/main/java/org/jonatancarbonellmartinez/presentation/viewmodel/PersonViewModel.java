package org.jonatancarbonellmartinez.presentation.viewmodel;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.jonatancarbonellmartinez.data.database.configuration.GlobalLoadingManager;
import org.jonatancarbonellmartinez.data.database.configuration.DatabaseConnection;
import org.jonatancarbonellmartinez.domain.model.Person;
import org.jonatancarbonellmartinez.domain.repository.contract.PersonRepository;
import org.jonatancarbonellmartinez.presentation.mapper.PersonUiMapper;

import javax.inject.Inject;
import java.text.Normalizer;
import java.util.List;
import java.util.ArrayList;

public class PersonViewModel {
    private final PersonRepository repository;
    private final PersonUiMapper uiMapper;
    private final DatabaseConnection databaseConnection;
    private final GlobalLoadingManager loadingManager;
    private final ObservableList<PersonUI> persons = FXCollections.observableArrayList();
    private final FilteredList<PersonUI> filteredPersons = new FilteredList<>(persons);
    private final BooleanProperty showOnlyActive = new SimpleBooleanProperty(true);
    private final StringProperty searchQuery = new SimpleStringProperty("");
    private final StringProperty errorMessage = new SimpleStringProperty("");
    private final ObjectProperty<PersonUI> selectedPerson = new SimpleObjectProperty<>();

    @Inject
    public PersonViewModel(PersonRepository repository,
                           PersonUiMapper uiMapper,
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
                                    boolean matchesSearch = person.matchesSearch(searchQuery.get());
                                    return matchesActive && matchesSearch;
                                },
                        showOnlyActive, searchQuery
                )
        );
    }

    public void loadPersons() {
        databaseConnection
                .executeOperation(connection -> repository.getAllPersons(connection))
                .thenAccept(domainPersons -> {
                    List<PersonUI> uiPersons = new ArrayList<>();
                    for (Person person : domainPersons) {
                        uiPersons.add(uiMapper.toUiModel(person));
                    }
                    updatePersonsList(uiPersons);
                })
                .exceptionally(throwable -> {
                    Platform.runLater(() -> setError("Error: " + throwable.getMessage()));
                    return null;
                });
    }

    public void loadActivePilots() {
        databaseConnection
                .executeOperation(connection -> repository.getActivePilots(connection))
                .thenAccept(pilots -> {
                    List<PersonUI> uiPilots = new ArrayList<>();
                    for (Person pilot : pilots) {
                        uiPilots.add(uiMapper.toUiModel(pilot));
                    }
                    updatePersonsList(uiPilots);
                })
                .exceptionally(throwable -> {
                    Platform.runLater(() -> setError("Error: " + throwable.getMessage()));
                    return null;
                });
    }

    public void loadActiveCrew() {
        databaseConnection
                .executeOperation(connection -> repository.getActiveCrew(connection))
                .thenAccept(crew -> {
                    List<PersonUI> uiCrew = new ArrayList<>();
                    for (Person member : crew) {
                        uiCrew.add(uiMapper.toUiModel(member));
                    }
                    updatePersonsList(uiCrew);
                })
                .exceptionally(throwable -> {
                    Platform.runLater(() -> setError("Error: " + throwable.getMessage()));
                    return null;
                });
    }

//    public void savePerson(PersonUI person) {
//        databaseConnection
//                .executeOperation(connection -> {
//                    databaseConnection.beginTransaction(connection);
//                    try {
//                        Person domainPerson = uiMapper.toDomain(person);
//                        boolean success;
//
//                        if (person.getId() == null) {
//                            success = repository.insertPerson(connection, domainPerson);
//                        } else {
//                            success = repository.updatePerson(connection, domainPerson, person.getId());
//                        }
//
//                        if (success) {
//                            databaseConnection.commitTransaction(connection);
//                            return true;
//                        } else {
//                            throw new Exception("Failed to save person");
//                        }
//                    } catch (Exception e) {
//                        databaseConnection.rollbackTransaction(connection);
//                        throw e;
//                    }
//                })
//                .thenAccept(success -> Platform.runLater(this::loadPersons))
//                .exceptionally(throwable -> {
//                    Platform.runLater(() -> setError("Error: " + throwable.getMessage()));
//                    return null;
//                });
//    }

//    public void updatePersonStatus(PersonUI person, boolean isActive) {
//        if (person.getId() == null) return;
//
//        databaseConnection
//                .executeOperation(connection -> {
//                    databaseConnection.beginTransaction(connection);
//                    try {
//                        boolean success = repository.updatePersonStatus(connection, person.getId(), isActive);
//                        if (success) {
//                            databaseConnection.commitTransaction(connection);
//                            return true;
//                        } else {
//                            throw new Exception("Failed to update person status");
//                        }
//                    } catch (Exception e) {
//                        databaseConnection.rollbackTransaction(connection);
//                        throw e;
//                    }
//                })
//                .thenAccept(success -> Platform.runLater(this::loadPersons))
//                .exceptionally(throwable -> {
//                    Platform.runLater(() -> setError("Error: " + throwable.getMessage()));
//                    return null;
//                });
//    }

    private void updatePersonsList(List<PersonUI> newPersons) {
        Platform.runLater(() -> {
            persons.clear();
            persons.addAll(newPersons);
        });
    }

    public void cleanup() {
        persons.clear();
        selectedPerson.set(null);
        errorMessage.set("");
    }

    private void setError(String error) {
        Platform.runLater(() -> errorMessage.set(error));
    }

    // Getters for properties
    public ObservableList<PersonUI> getFilteredPersons() { return filteredPersons; }
    public BooleanProperty showOnlyActiveProperty() { return showOnlyActive; }
    public StringProperty searchQueryProperty() { return searchQuery; }
    public BooleanProperty isLoadingProperty() { return loadingManager.globalLoadingProperty(); }
    public StringProperty errorMessageProperty() { return errorMessage; }
    public ObjectProperty<PersonUI> selectedPersonProperty() { return selectedPerson; }

    @FunctionalInterface
    private interface DatabaseOperation {
        void execute() throws Exception;
    }

    // INNER STATIC CLASS
    public static class PersonUI {
        private Integer id;
        private final StringProperty code = new SimpleStringProperty();
        private final StringProperty rank = new SimpleStringProperty();
        private final StringProperty cuerpo = new SimpleStringProperty();
        private final StringProperty especialidad = new SimpleStringProperty();
        private final StringProperty name = new SimpleStringProperty();
        private final StringProperty lastName1 = new SimpleStringProperty();
        private final StringProperty lastName2 = new SimpleStringProperty();
        private final StringProperty phone = new SimpleStringProperty();
        private final StringProperty dni = new SimpleStringProperty();
        private final StringProperty division = new SimpleStringProperty();
        private final StringProperty role = new SimpleStringProperty();
        private final StringProperty antiguedadEmpleo = new SimpleStringProperty();
        private final StringProperty fechaEmbarque = new SimpleStringProperty();
        private final IntegerProperty order = new SimpleIntegerProperty();
        private final StringProperty active = new SimpleStringProperty();

        public PersonUI() {
            this.id = null;
        }

        public boolean matchesSearch(String query) {
            if (query == null || query.isEmpty()) return true;

            // Normaliza el texto de búsqueda (quita acentos y convierte a minúsculas)
            String normalizedQuery = normalizeString(query);

            // Normaliza y compara cada campo
            return  normalizeString(code.get()).contains(normalizedQuery) ||
                    normalizeString(rank.get()).contains(normalizedQuery) ||
                    normalizeString(name.get()).contains(normalizedQuery) ||
                    normalizeString(lastName1.get()).contains(normalizedQuery) ||
                    normalizeString(lastName2.get()).contains(normalizedQuery) ||
                    normalizeString(division.get()).contains(normalizedQuery) ||
                    normalizeString(role.get()).contains(normalizedQuery);
        }

        // Metodo auxiliar para normalizar strings
        private String normalizeString(String text) {
            if (text == null || text.isEmpty()) return "";

            // Normaliza el texto quitando diacríticos y convirtiendo a minúsculas
            return Normalizer.normalize(text, Normalizer.Form.NFD)
                    .replaceAll("\\p{M}", "") // Elimina marcas diacríticas
                    .toLowerCase();
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

        public String getCuerpo() { return cuerpo.get(); }
        public void setCuerpo(String value) { cuerpo.set(value); }
        public StringProperty cuerpoProperty() { return cuerpo; }

        public String getEspecialidad() { return especialidad.get(); }
        public void setEspecialidad(String value) { especialidad.set(value); }
        public StringProperty especialidadProperty() { return especialidad; }

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

        public String getAntiguedadEmpleo() { return antiguedadEmpleo.get(); }
        public void setAntiguedadEmpleo(String value) { antiguedadEmpleo.set(value); }
        public StringProperty antiguedadEmpleoProperty() { return antiguedadEmpleo; }

        public String getFechaEmbarque() { return fechaEmbarque.get(); }
        public void setFechaEmbarque(String value) { fechaEmbarque.set(value); }
        public StringProperty fechaEmbarqueProperty() { return fechaEmbarque; }

        public int getOrder() { return order.get(); }
        public void setOrder(int value) { order.set(value); }
        public IntegerProperty orderProperty() { return order; }

        public String isActive() { return active.get(); }
        public void setActive(String value) { active.set(value); }
        public StringProperty activeProperty() { return active; }
    }
}