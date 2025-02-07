package org.jonatancarbonellmartinez.presentation.viewmodel;

import org.jonatancarbonellmartinez.xexceptions.DatabaseException;
import org.jonatancarbonellmartinez.xfactory.DAOFactorySQLite;
import org.jonatancarbonellmartinez.data.database.GenericDAO;
import org.jonatancarbonellmartinez.data.model.Entity;
import org.jonatancarbonellmartinez.data.model.Event;
import org.jonatancarbonellmartinez.xobservers.Observer;
import org.jonatancarbonellmartinez.presentation.view.fxml.xDialogView;
import org.jonatancarbonellmartinez.presentation.view.fxml.xEventXDialogXView;

public class xEventXDialogXPresenter implements xPresenter, xDialogPresenter {
    private final GenericDAO<Event,Integer> eventDAO;
    private final xEventXDialogXView view;
    private final Observer observer;

    public xEventXDialogXPresenter(xEventXDialogXView eventView, Observer observer) {
        this.view = eventView;
        this.eventDAO = DAOFactorySQLite.getInstance().createEventDAO(); // new
        this.observer = observer;
    }

    @Override
    public boolean isFormValid() {
        boolean isValid = xDialogPresenter.validateSimpleComboBox(view, view.getEventNameBox(), "Nombre") &&
                xDialogPresenter.isFieldCompleted(view, view.getEventPlaceField(), "Lugar");

        // If edit mode is active, add the extra validation
        if (view.isEditMode()) {
            isValid = isValid && xDialogPresenter.isFieldCompleted(view, view.getEditEventIdField(), "ID");
        }

        return isValid;
    }

    @Override
    public void insertEntity() {
        try {
            Event event = collectEntityData();

            eventDAO.insert(event);
            xDialogView.showMessage(view,"Evento añadido correctamente.");

            view.clearFields();

        } catch (DatabaseException ex) {
            xDialogView.showError(view,"Error al añadir evento: ");
        } catch (Exception ex) {
            xDialogPresenter.handleUnexpectedError(ex,view);
        }
    }

    @Override
    public void editEntity() {
        try {
            Event event = collectEntityData();

            int eventId = Integer.parseInt(view.getEditEventIdField().getText());

            eventDAO.update(event, eventId);
            xDialogView.showMessage(view,"Evento editado correctamente.");

            view.clearFields();

        } catch (DatabaseException e) {
            xDialogView.showError(view,"Error al editar evento: ");
        } catch (Exception ex) {
            xDialogPresenter.handleUnexpectedError(ex, view);
        }
    }

    @Override
    public void getEntity(int entityId) {
        try {
            Event event = (Event) eventDAO.read(entityId);
            if (event != null) {
                populateEntityDialog(event);
            } else {
                xDialogView.showMessage(view,"No se encontró ningun evento con ese ID.");
            }
        } catch (DatabaseException e) {
            xDialogView.showError(view,"Error al obtener el evento: ");
        } catch (Exception ex) {
            xDialogPresenter.handleUnexpectedError(ex,view);
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
