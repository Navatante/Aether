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
import org.jonatancarbonellmartinez.services.CustomLogger;
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

    // MANTENER estos como Properties porque se usan para bindings en los filtros
    private final BooleanProperty showOnlyActive = new SimpleBooleanProperty(true);
    private final StringProperty searchQuery = new SimpleStringProperty("");
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
                    CustomLogger.logError("Error loading persons", (Exception) throwable.getCause());
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
                    CustomLogger.logError("Error loading active pilots", (Exception) throwable.getCause());
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
                    CustomLogger.logError("Error loading active crew", (Exception) throwable.getCause());
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
    }


    // Getters for properties
    public ObservableList<PersonUI> getFilteredPersons() { return filteredPersons; }
    public BooleanProperty showOnlyActiveProperty() { return showOnlyActive; }
    public StringProperty searchQueryProperty() { return searchQuery; }
    public ObjectProperty<PersonUI> selectedPersonProperty() { return selectedPerson; }

    @FunctionalInterface
    private interface DatabaseOperation {
        void execute() throws Exception;
    }

    // INNER STATIC CLASS
    public static class PersonUI {
        // Estos campos solo son de lectura y no participan en bindings
        private Integer id;
        private String code;
        private String rank;
        private String cuerpo;
        private String especialidad;
        private String phone;
        private String dni;
        private String division;
        private String role;
        private String antiguedadEmpleo;
        private String fechaEmbarque;
        private Integer order;
        private String active;

        // ESTOS también pueden ser String simples porque:
        // 1. Solo se usan en matchesSearch()
        // 2. No necesitan actualización en tiempo real en la UI
        private String name;        // En lugar de StringProperty
        private String lastName1;   // En lugar de StringProperty
        private String lastName2;   // En lugar de StringProperty

        public PersonUI() {
            this.id = null;
        }

        public boolean matchesSearch(String query) {
            if (query == null || query.isEmpty()) return true;

            String normalizedQuery = normalizeString(query);
            return normalizeString(name).contains(normalizedQuery) ||
                    normalizeString(lastName1).contains(normalizedQuery) ||
                    normalizeString(lastName2).contains(normalizedQuery);
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

        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }

        public String getRank() { return rank; }
        public void setRank(String rank) { this.rank = rank; }

        public String getCuerpo() { return cuerpo; }
        public void setCuerpo(String cuerpo) { this.cuerpo = cuerpo; }


        public String getEspecialidad() { return especialidad; }
        public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }


        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getLastName1() { return lastName1; }
        public void setLastName1(String lastName1) { this.lastName1 = lastName1; }


        public String getLastName2() { return lastName2; }
        public void setLastName2(String lastName2) { this.lastName2 = lastName2; }


        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }

        public String getDni() { return dni; }
        public void setDni(String dni) { this.dni = dni; }

        public String getDivision() { return division; }
        public void setDivision(String division) { this.division = division; }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }

        public String getAntiguedadEmpleo() { return antiguedadEmpleo; }
        public void setAntiguedadEmpleo(String antiguedadEmpleo) { this.antiguedadEmpleo = antiguedadEmpleo; }

        public String getFechaEmbarque() { return fechaEmbarque; }
        public void setFechaEmbarque(String fechaEmbarque) { this.fechaEmbarque = fechaEmbarque; }

        public int getOrder() { return order; }
        public void setOrder(int order) { this.order = order; }

        public String isActive() { return active; }
        public void setActive(String active) { this.active = active; }
    }
}