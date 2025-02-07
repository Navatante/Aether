package org.jonatancarbonellmartinez.presentation.viewmodel;

public class FlightViewModel {
    private final FlightRepository repository;
    private final ObjectProperty<ObservableList<Flight>> flights =
            new SimpleObjectProperty<>(FXCollections.observableArrayList());

    @Inject
    public FlightViewModel(FlightRepository repository) {
        this.repository = repository;
    }

    public void loadFlights() {
        repository.getAllFlights()
                .thenAccept(flightList ->
                        Platform.runLater(() ->
                                flights.get().setAll(flightList)));
    }

    // Getters para las propiedades
    public ReadOnlyObjectProperty<ObservableList<Flight>> flightsProperty() {
        return flights;
    }
}
