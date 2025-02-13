package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.factory.DAOFactorySQLite;
import org.jonatancarbonellmartinez.model.dao.GenericDAO;
import org.jonatancarbonellmartinez.model.entities.Entity;
import org.jonatancarbonellmartinez.model.entities.Person;
import org.jonatancarbonellmartinez.observers.Observer;
import org.jonatancarbonellmartinez.view.DialogView;
import org.jonatancarbonellmartinez.view.PersonDialogView;

public class PersonDialogPresenter implements Presenter, DialogPresenter {
    private final GenericDAO<Person,Integer> personDAO;
    private final PersonDialogView view;
    private final Observer observer;

    public PersonDialogPresenter(PersonDialogView personView, Observer observer) {
        this.view = personView;
        this.personDAO = DAOFactorySQLite.getInstance().createPersonDAO(); // new
        this.observer = observer;
    }

    @Override
    public boolean isFormValid() {
        boolean isValid = DialogPresenter.validateSimpleComboBox(view, view.getEmpleoBox(), "Empleo") &&
                            DialogPresenter.validateSimpleComboBox(view, view.getDivisionBox(), "División") &&
                            DialogPresenter.validateSimpleComboBox(view, view.getRolBox(), "Rol") &&
                            DialogPresenter.isFieldCompleted(view, view.getPersonNkField(), "Código") &&
                            DialogPresenter.isFieldCompleted(view, view.getPersonNameField(), "Nombre") &&
                            DialogPresenter.isFieldCompleted(view, view.getPersonLastName1Field(), "Apellido 1") &&
                            DialogPresenter.isFieldCompleted(view, view.getPersonLastName2Field(), "Apellido 2") &&
                            DialogPresenter.isFieldCompleted(view, view.getPersonPhoneField(), "Teléfono") &&
                            DialogPresenter.isFieldCompleted(view, view.getPersonDniField(), "DNI") &&
                            DialogPresenter.isFieldCompleted(view, view.getOrderField(), "Orden");

        // Additional validation if in edit mode
        if (view.isEditMode()) {
            isValid = isValid && DialogPresenter.isFieldCompleted(view, view.getEditPersonIdField(), "ID");
        }

        return isValid;
    }

    @Override
    public void insertEntity() {
        try {
            Person person = collectEntityData();
            person.setPersonCurrentFlag(1);

            personDAO.insert(person);
            DialogView.showMessage(view,"Persona añadida correctamente.");

            view.clearFields();

        } catch (DatabaseException ex) {
            DialogView.showError(view,"Error al añadir persona: ");
        } catch (Exception ex) {
            DialogPresenter.handleUnexpectedError(ex, view);
        }
    }

    @Override
    public void editEntity() {
        try {
            Person person = collectEntityData();

            int personId = Integer.parseInt(view.getEditPersonIdField().getText());
            person.setPersonCurrentFlag(view.getPersonStateBox().getSelectedItem().toString().equals("Activo") ? 1 : 0);
            person.setPersonOrder(calculatePersonOrder());

            personDAO.update(person, personId);
            DialogView.showMessage(view,"Persona editada correctamente.");

            view.clearFields();

        } catch (DatabaseException e) {
            DialogView.showError(view,"Error al editar persona: ");
        } catch (Exception ex) {
            DialogPresenter.handleUnexpectedError(ex, view);
        }
    }

    @Override
    public void getEntity(int entityId) {
        try {
            Person person = (Person) personDAO.read(entityId);
            if (person != null) {
                populateEntityDialog(person);
            } else {
                DialogView.showMessage(view,"No se encontró ninguna persona con ese ID.");
            }
        } catch (DatabaseException e) {
            DialogView.showError(view,"Error al obtener la persona: ");
        } catch (Exception ex) {
            DialogPresenter.handleUnexpectedError(ex,view);
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
    public Person collectEntityData() {
        Person person = new Person();

        person.setPersonNk(view.getPersonNkField().getText().toUpperCase());
        person.setPersonRank(view.getEmpleoBox().getSelectedItem().toString());
        person.setPersonName(DialogPresenter.capitalizeWords(view.getPersonNameField()));
        person.setPersonLastName1(DialogPresenter.capitalizeWords(view.getPersonLastName1Field()));
        person.setPersonLastName2(DialogPresenter.capitalizeWords(view.getPersonLastName2Field()));
        person.setPersonPhone(view.getPersonPhoneField().getText());
        person.setPersonDni(DialogPresenter.calculateDniLetter(view.getPersonDniField()));
        person.setPersonDivision(view.getDivisionBox().getSelectedItem().toString());
        person.setPersonRol(view.getRolBox().getSelectedItem().toString());
        person.setPersonOrder(Integer.parseInt(view.getOrderField().getText()));
        return person;
    }

    @Override
    public void notifyObserver() {
        observer.update();
    }

    @Override
    public void populateEntityDialog(Entity entity) {
        Person person = (Person) entity;
        view.setPersonNk(person.getPersonNk());
        view.setPersonRank(person.getPersonRank());
        view.setPersonName(person.getPersonName());
        view.setPersonLastName1(person.getPersonLastName1());
        view.setPersonLastName2(person.getPersonLastName2());
        view.setPersonPhone(person.getPersonPhone());
        view.setPersonDni(stripDniLetter(person.getPersonDni()));
        view.setPersonDivision(person.getPersonDivision());
        view.setPersonRol(person.getPersonRol());
        view.setPersonOrder(person.getPersonOrder());
        view.setPersonStateBox(person.getPersonCurrentFlag() == 1 ? "Activo" : "Inactivo");
    }

    private int calculatePersonOrder() {
        return view.getPersonStateBox().getSelectedItem().toString().equals("Activo") ? Integer.parseInt(view.getOrderField().getText()) : 99999;
    }

    private String stripDniLetter(String dni) {
        return dni != null && dni.length() > 0 ? dni.substring(0, dni.length() - 1) : "";
    }
}
