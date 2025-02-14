package org.jonatancarbonellmartinez.presentation.viewmodel;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import org.jonatancarbonellmartinez.data.database.configuration.DatabaseConnection;
import org.jonatancarbonellmartinez.data.database.configuration.GlobalLoadingManager;
import org.jonatancarbonellmartinez.domain.model.PersonDomain;
import org.jonatancarbonellmartinez.domain.repository.contract.PersonRepository;
import org.jonatancarbonellmartinez.presentation.mapper.PersonDomainUiMapper;

import javax.inject.Inject;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.concurrent.CompletableFuture;

public class AddPersonViewModel {
    private final PersonRepository repository;
    private final PersonDomainUiMapper uiMapper;
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
    private final ObjectProperty<LocalDate> antiguedadEmpleo = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> fechaEmbarque = new SimpleObjectProperty<>();
    private final ObjectProperty<Integer> order = new SimpleObjectProperty<>(null);
    private final BooleanProperty active = new SimpleBooleanProperty(true);

    // Form state
    private final BooleanProperty formValid = new SimpleBooleanProperty(false);
    private final StringProperty errorMessage = new SimpleStringProperty("");

    @Inject
    public AddPersonViewModel(
            PersonRepository repository,
            PersonDomainUiMapper uiMapper,
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
        // Create individual validation bindings
        BooleanBinding orderValid = order.isNotNull();

        BooleanBinding dniValid = Bindings.createBooleanBinding(
                () -> dni.get() != null && dni.get().length() == 8,
                dni
        );

        BooleanBinding phoneValid = Bindings.createBooleanBinding(
                () -> phone.get() != null && phone.get().length() == 9,
                phone
        );

        BooleanBinding codeValid = Bindings.createBooleanBinding(
                () -> code.get() == null || code.get().isEmpty() || code.get().length() == 3,
                code
        );

        // Basic required field validations
        BooleanBinding requiredFieldsValid = rank.isNotEmpty()
                .and(cuerpo.isNotEmpty())
                .and(especialidad.isNotEmpty())
                .and(name.isNotEmpty())
                .and(lastName1.isNotEmpty())
                .and(lastName2.isNotEmpty())
                .and(division.isNotEmpty())
                .and(role.isNotEmpty())
                .and(fechaEmbarque.isNotNull())
                .and(antiguedadEmpleo.isNotNull());

        // Combine all validations
        formValid.bind(
                orderValid
                        .and(dniValid)
                        .and(phoneValid)
                        .and(codeValid)
                        .and(requiredFieldsValid)
        );
    }

    // TODO save test
    public void saveButtonTest() {

        PersonDomain personDomain = new PersonDomain.Builder()
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

        System.out.println(personDomain);
    }

    public CompletableFuture<Boolean> savePerson() {
        if (!formValid.get()) {
            return CompletableFuture.completedFuture(false);
        }

        PersonDomain personDomain = new PersonDomain.Builder()
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

        return databaseConnection.executeOperation(conn -> repository.insertPerson(conn, personDomain), true);
    }

    public void reset() {
        // Unbind formValid first
        formValid.unbind();

        // Reset all properties
        code.set("");
        rank.unbind();
        rank.set(null);
        cuerpo.unbind();
        cuerpo.set(null);
        especialidad.unbind();
        especialidad.set(null);
        name.set("");
        lastName1.set("");
        lastName2.set("");
        phone.set("");
        dni.set("");
        division.unbind();
        division.set(null);
        role.unbind();
        role.set(null);
        antiguedadEmpleo.set(null);
        fechaEmbarque.set(null);
        order.set(null);
        active.set(true);
        errorMessage.unbind();
        errorMessage.set("");

        // Re-setup validation after reset
        setupValidation();
    }

    // Optional: Add a dispose method to clean up bindings when the view model is no longer needed
    public void dispose() {
        formValid.unbind();
        rank.unbind();
        cuerpo.unbind();
        especialidad.unbind();
        division.unbind();
        role.unbind();
        errorMessage.unbind();
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
    public ObjectProperty<LocalDate> antiguedadEmpleoProperty() { return antiguedadEmpleo; }
    public ObjectProperty<LocalDate> fechaEmbarqueProperty() { return fechaEmbarque; }
    public ObjectProperty<Integer> orderProperty() { return order; }
    public BooleanProperty activeProperty() { return active; }
    public BooleanProperty formValidProperty() { return formValid; }
    public StringProperty errorMessageProperty() { return errorMessage; }
}