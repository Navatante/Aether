package org.jonatancarbonellmartinez.presentation.viewmodel;

import org.jonatancarbonellmartinez.xexceptions.DatabaseException;
import org.jonatancarbonellmartinez.xfactory.DAOFactorySQLite;
import org.jonatancarbonellmartinez.data.database.GenericDAO;
import org.jonatancarbonellmartinez.data.model.Entity;
import org.jonatancarbonellmartinez.data.model.PersonEntity;
import org.jonatancarbonellmartinez.xobservers.Observer;
import org.jonatancarbonellmartinez.presentation.view.fxml.xDialogView;
import org.jonatancarbonellmartinez.presentation.view.fxml.xPersonXDialogXView;

public class xPersonXDialogXPresenter implements xPresenter, xDialogPresenter {
    private final GenericDAO<PersonEntity,Integer> personDAO;
    private final xPersonXDialogXView view;
    private final Observer observer;

    public xPersonXDialogXPresenter(xPersonXDialogXView personView, Observer observer) {
        this.view = personView;
        this.personDAO = DAOFactorySQLite.getInstance().createPersonDAO(); // new
        this.observer = observer;
    }

    @Override
    public boolean isFormValid() {
        boolean isValid = xDialogPresenter.validateSimpleComboBox(view, view.getEmpleoBox(), "Empleo") &&
                            xDialogPresenter.validateSimpleComboBox(view, view.getDivisionBox(), "División") &&
                            xDialogPresenter.validateSimpleComboBox(view, view.getRolBox(), "Rol") &&
                            xDialogPresenter.isFieldCompleted(view, view.getPersonNkField(), "Código") &&
                            xDialogPresenter.isFieldCompleted(view, view.getPersonNameField(), "Nombre") &&
                            xDialogPresenter.isFieldCompleted(view, view.getPersonLastName1Field(), "Apellido 1") &&
                            xDialogPresenter.isFieldCompleted(view, view.getPersonLastName2Field(), "Apellido 2") &&
                            xDialogPresenter.isFieldCompleted(view, view.getPersonPhoneField(), "Teléfono") &&
                            xDialogPresenter.isFieldCompleted(view, view.getPersonDniField(), "DNI") &&
                            xDialogPresenter.isFieldCompleted(view, view.getOrderField(), "Orden");

        // Additional validation if in edit mode
        if (view.isEditMode()) {
            isValid = isValid && xDialogPresenter.isFieldCompleted(view, view.getEditPersonIdField(), "ID");
        }

        return isValid;
    }

    @Override
    public void insertEntity() {
        try {
            PersonEntity personEntity = (PersonEntity) collectEntityData();
            personEntity.setPersonCurrentFlag(1);

            personDAO.insert(personEntity);
            xDialogView.showMessage(view,"Persona añadida correctamente.");

            view.clearFields();

        } catch (DatabaseException ex) {
            xDialogView.showError(view,"Error al añadir persona: ");
        } catch (Exception ex) {
            xDialogPresenter.handleUnexpectedError(ex, view);
        }
    }

    @Override
    public void editEntity() {
        try {
            PersonEntity personEntity = (PersonEntity) collectEntityData();

            int personId = Integer.parseInt(view.getEditPersonIdField().getText());
            //personEntity.setPersonCurrentFlag(view.getPersonStateBox().getSelectedItem().toString().equals("Activo") ? 1 : 0);
            //personEntity.setPersonOrder(calculatePersonOrder());

            personDAO.update(personEntity, personId);
            xDialogView.showMessage(view,"Persona editada correctamente.");

            view.clearFields();

        } catch (DatabaseException e) {
            xDialogView.showError(view,"Error al editar persona: ");
        } catch (Exception ex) {
            xDialogPresenter.handleUnexpectedError(ex, view);
        }
    }

    @Override
    public void getEntity(int entityId) {
        try {
            PersonEntity personEntity = (PersonEntity) personDAO.read(entityId);
            if (personEntity != null) {
                populateEntityDialog((Entity) personEntity);
            } else {
                xDialogView.showMessage(view,"No se encontró ninguna persona con ese ID.");
            }
        } catch (DatabaseException e) {
            xDialogView.showError(view,"Error al obtener la persona: ");
        } catch (Exception ex) {
            xDialogPresenter.handleUnexpectedError(ex,view);
        }
    }

    @Override
    public void setActionListeners() {
        view.getSaveButton().addActionListener(e -> onSaveButtonClicked());
        if (view.isEditMode()) view.getEditPersonIdField().addActionListener(e -> view.onEditEntityIdFieldAction());
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
    public Entity collectEntityData() {
        PersonEntity personEntity = new PersonEntity();

        personEntity.setPersonNk(view.getPersonNkField().getText().toUpperCase());
        personEntity.setPersonRank(view.getEmpleoBox().getSelectedItem().toString());
        personEntity.setPersonName(xDialogPresenter.capitalizeWords(view.getPersonNameField()));
        personEntity.setPersonLastName1(xDialogPresenter.capitalizeWords(view.getPersonLastName1Field()));
        personEntity.setPersonLastName2(xDialogPresenter.capitalizeWords(view.getPersonLastName2Field()));
        personEntity.setPersonPhone(view.getPersonPhoneField().getText());
        personEntity.setPersonDni(xDialogPresenter.calculateDniLetter(view.getPersonDniField()));
        personEntity.setPersonDivision(view.getDivisionBox().getSelectedItem().toString());
        personEntity.setPersonRole(view.getRolBox().getSelectedItem().toString());
        personEntity.setPersonOrder(Integer.parseInt(view.getOrderField().getText()));
        return (Entity) personEntity;
    }

    @Override
    public void notifyObserver() {
        observer.update();
    }

    @Override
    public void populateEntityDialog(Entity entity) {
        PersonEntity personEntity = (PersonEntity) entity;
        view.setPersonNk(personEntity.getPersonNk());
        view.setPersonRank(personEntity.getPersonRank());
        view.setPersonName(personEntity.getPersonName());
        view.setPersonLastName1(personEntity.getPersonLastName1());
        view.setPersonLastName2(personEntity.getPersonLastName2());
        view.setPersonPhone(personEntity.getPersonPhone());
        view.setPersonDni(stripDniLetter(personEntity.getPersonDni()));
        view.setPersonDivision(personEntity.getPersonDivision());
        view.setPersonRol(personEntity.getPersonRole());
        view.setPersonOrder(personEntity.getPersonOrder());
        view.setPersonStateBox(personEntity.getPersonCurrentFlag() == 1 ? "Activo" : "Inactivo");
    }

    private int calculatePersonOrder() {
        return view.getPersonStateBox().getSelectedItem().toString().equals("Activo") ? Integer.parseInt(view.getOrderField().getText()) : 99999;
    }

    private String stripDniLetter(String dni) {
        return dni != null && dni.length() > 0 ? dni.substring(0, dni.length() - 1) : "";
    }
}
