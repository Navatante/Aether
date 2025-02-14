package org.jonatancarbonellmartinez.presentation.viewmodel;

import javafx.beans.property.*;
import org.jonatancarbonellmartinez.data.database.configuration.DatabaseConnection;
import org.jonatancarbonellmartinez.data.database.configuration.GlobalLoadingManager;
import org.jonatancarbonellmartinez.domain.model.Person;
import org.jonatancarbonellmartinez.domain.repository.contract.PersonRepository;
import org.jonatancarbonellmartinez.presentation.mapper.PersonUiMapper;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

public class AddPersonViewModel {
    private final PersonRepository repository;
    private final PersonUiMapper uiMapper;
    private final DatabaseConnection databaseConnection;
    private final GlobalLoadingManager loadingManager;

    // Form fields
    private final StringProperty code = new SimpleStringProperty("");
    private final StringProperty rank = new SimpleStringProperty("");
    private final StringProperty cuerpo = new SimpleStringProperty("");
    private final StringProperty especialidad = new SimpleStringProperty("");
    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty lastName1 = new SimpleStringProperty("");
    private final StringProperty lastName2 = new SimpleStringProperty("");
    private final StringProperty phone = new SimpleStringProperty("");
    private final StringProperty dni = new SimpleStringProperty("");
    private final StringProperty division = new SimpleStringProperty("");
    private final StringProperty role = new SimpleStringProperty("");
    private final StringProperty antiguedadEmpleo = new SimpleStringProperty("");
    private final StringProperty fechaEmbarque = new SimpleStringProperty("");
    private final ObjectProperty<Integer> order = new SimpleObjectProperty<>(null);
    private final BooleanProperty active = new SimpleBooleanProperty(true);

    // Form state
    private final BooleanProperty formValid = new SimpleBooleanProperty(false);
    private final StringProperty errorMessage = new SimpleStringProperty("");

    @Inject
    public AddPersonViewModel(
            PersonRepository repository,
            PersonUiMapper uiMapper,
            DatabaseConnection databaseConnection,
            GlobalLoadingManager loadingManager
    ) {
        this.repository = repository;
        this.uiMapper = uiMapper;
        this.databaseConnection = databaseConnection;
        this.loadingManager = loadingManager;
        setupValidation();
    }



    private void setupValidation() {
        // Bind formValid to required fields
        formValid.bind(
                code.isNotEmpty()
                        .and(name.isNotEmpty())
                        .and(lastName1.isNotEmpty())
                        .and(dni.isNotEmpty())
        );
    }

    public CompletableFuture<Boolean> savePerson() {
        if (!formValid.get()) {
            return CompletableFuture.completedFuture(false);
        }

        Person person = new Person.Builder()
                .code(code.get())
                .rank(rank.get())
                .cuerpo(cuerpo.get())
                .especialidad(especialidad.get())
                .name(name.get())
                .lastName1(lastName1.get())
                .lastName2(lastName2.get())
                .phone(phone.get())
                .dni(dni.get())
                .division(division.get())
                .role(role.get())
                .antiguedadEmpleo(antiguedadEmpleo.get())
                .fechaEmbarque(fechaEmbarque.get())
                .order(order.get())
                .isActive(active.get())
                .build();

        return databaseConnection.executeOperation(conn -> repository.insertPerson(conn, person), true);
    }

    public void reset() {
        code.set("");
        rank.set("");
        cuerpo.set("");
        especialidad.set("");
        name.set("");
        lastName1.set("");
        lastName2.set("");
        phone.set("");
        dni.set("");
        division.set("");
        role.set("");
        antiguedadEmpleo.set("");
        fechaEmbarque.set("");
        order.set(null);
        active.set(true);
        errorMessage.set("");
    }

    // Getters for properties
    public StringProperty codeProperty() { return code; }
    public StringProperty rankProperty() { return rank; }
    public StringProperty cuerpoProperty() { return cuerpo; }
    public StringProperty especialidadProperty() { return especialidad; }
    public StringProperty nameProperty() { return name; }
    public StringProperty lastName1Property() { return lastName1; }
    public StringProperty lastName2Property() { return lastName2; }
    public StringProperty phoneProperty() { return phone; }
    public StringProperty dniProperty() { return dni; }
    public StringProperty divisionProperty() { return division; }
    public StringProperty roleProperty() { return role; }
    public StringProperty antiguedadEmpleoProperty() { return antiguedadEmpleo; }
    public StringProperty fechaEmbarqueProperty() { return fechaEmbarque; }
    public ObjectProperty<Integer> orderProperty() { return order; }
    public BooleanProperty activeProperty() { return active; }
    public BooleanProperty formValidProperty() { return formValid; }
    public StringProperty errorMessageProperty() { return errorMessage; }
}