package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.factory.DAOFactorySQLite;
import org.jonatancarbonellmartinez.model.dao.GenericDAO;
import org.jonatancarbonellmartinez.model.entities.Entity;
import org.jonatancarbonellmartinez.model.entities.Event;
import org.jonatancarbonellmartinez.observers.Observer;
import org.jonatancarbonellmartinez.view.DialogView;
import org.jonatancarbonellmartinez.view.EventDialogView;

public class EventDialogPresenter implements Presenter, DialogPresenter {
    private final GenericDAO<Event,Integer> eventDAO;
    private final EventDialogView view;
    private final Observer observer;

    public EventDialogPresenter(EventDialogView eventView, Observer observer) {
        this.view = eventView;
        this.eventDAO = DAOFactorySQLite.getInstance().createEventDAO(); // new
        this.observer = observer;
    }

    @Override
    public boolean isFormValid() {
        boolean isValid = DialogPresenter.validateSimpleComboBox(view, view.getEventNameBox(), "Nombre") &&
                DialogPresenter.isFieldCompleted(view, view.getEventPlaceField(), "Lugar");

        // If edit mode is active, add the extra validation
        if (view.isEditMode()) {
            isValid = isValid && DialogPresenter.isFieldCompleted(view, view.getEditEventIdField(), "ID");
        }

        return isValid;
    }

    @Override
    public void insertEntity() {
        try {
            Event event = collectEntityData();

            eventDAO.insert(event);
            DialogView.showMessage(view,"Evento añadido correctamente.");

            view.clearFields();

        } catch (DatabaseException ex) {
            DialogView.showError(view,"Error al añadir evento: ");
        } catch (Exception ex) {
            DialogPresenter.handleUnexpectedError(ex,view);
        }
    }

    @Override
    public void editEntity() {
        try {
            Event event = collectEntityData();

            int eventId = Integer.parseInt(view.getEditEventIdField().getText());

            eventDAO.update(event, eventId);
            DialogView.showMessage(view,"Evento editado correctamente.");

            view.clearFields();

        } catch (DatabaseException e) {
            DialogView.showError(view,"Error al editar evento: ");
        } catch (Exception ex) {
            DialogPresenter.handleUnexpectedError(ex, view);
        }
    }

    @Override
    public void getEntity(int entityId) {
        try {
            Event event = (Event) eventDAO.read(entityId);
            if (event != null) {
                populateEntityDialog(event);
            } else {
                DialogView.showMessage(view,"No se encontró ningun evento con ese ID.");
            }
        } catch (DatabaseException e) {
            DialogView.showError(view,"Error al obtener el evento: ");
        } catch (Exception ex) {
            DialogPresenter.handleUnexpectedError(ex,view);
        }
    }

    @Override
    public void onSaveButtonClicked() {
        if (isFormValid()) {
            if (view.isEditMode()) {
                editEntity();
            } else {
                insertEntity();
            }
            notifyObserver();
        }
    }

    @Override
    public Event collectEntityData() {
        Event event = new Event();

        event.setEventName(view.getEventNameBox().getSelectedItem().toString());
        event.setEventPlace(view.getEventPlaceField().getText());

        return event;
    }

    @Override
    public void populateEntityDialog(Entity entity) {
        Event event = (Event) entity;
        view.setEventNameBox(event.getEventName());
        view.setEventPlaceField(event.getEventPlace());
    }

    @Override
    public void notifyObserver() {
        observer.update();
    }

    @Override
    public void setActionListeners() {
        view.getSaveButton().addActionListener(e -> onSaveButtonClicked());
        if (view.isEditMode()) view.getEditEventIdField().addActionListener(e -> view.onEditEntityIdFieldAction());
    }
}
