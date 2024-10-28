package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.exceptions.DatabaseException;
import org.jonatancarbonellmartinez.factory.DAOFactorySQLite;
import org.jonatancarbonellmartinez.model.dao.GenericDAO;
import org.jonatancarbonellmartinez.model.entities.Person;
import org.jonatancarbonellmartinez.observers.Observer;
import org.jonatancarbonellmartinez.view.DialogView;
import org.jonatancarbonellmartinez.view.PersonDialogView;

import javax.swing.*;

public class PersonDialogPresenter implements Presenter, DialogPresenter {
    private final GenericDAO<Person,Integer> personDAO;
    private final PersonDialogView view;
    private final Observer observer;

    public PersonDialogPresenter(PersonDialogView addPersonView, Observer observer) {
        this.view = addPersonView;
        this.personDAO = DAOFactorySQLite.getInstance().createPersonDAOSQLite(); // new
        this.observer = observer;
    }

    @Override
    public boolean isFormValid() {
        return DialogPresenter.validateComboBox(view, view.getEmpleoBox(), "Empleo") &&
                DialogPresenter.validateComboBox(view, view.getDivisionBox(), "División") &&
                DialogPresenter.validateComboBox(view, view.getRolBox(), "Rol") &&
                DialogPresenter.validateField(view, view.getPersonNkField(), "Código") &&
                DialogPresenter.validateField(view, view.getPersonNameField(), "Nombre") &&
                DialogPresenter.validateField(view, view.getPersonLastName1Field(), "Apellido 1") &&
                DialogPresenter.validateField(view, view.getPersonLastName2Field(), "Apellido 2") &&
                DialogPresenter.validateField(view, view.getPersonPhoneField(), "Teléfono") &&
                DialogPresenter.validateField(view, view.getPersonDniField(), "DNI") &&
                DialogPresenter.validateField(view,view.getOrderField(), "Orden") &&
                DialogPresenter.containsOnlyLetters(view, view.getPersonNkField(),"Código") &&
                DialogPresenter.containsOnlyLetters(view, view.getPersonNameField(),"Nombre") &&
                DialogPresenter.containsOnlyLetters(view, view.getPersonLastName1Field(),"Apellido 1") &&
                DialogPresenter.containsOnlyLetters(view, view.getPersonLastName2Field(),"Apellido 2") &&
                DialogPresenter.containsOnlyNumbers(view, view.getPersonPhoneField(),"Teléfono") &&
                DialogPresenter.containsOnlyNumbers(view, view.getPersonDniField(),"DNI") &&
                view.doNotContainZero(view.getOrderField(), "Orden");
    }

    @Override
    public void addEntity() {
        try {
            Person person = collectEntityData();
            person.setPersonCurrentFlag(1);

            personDAO.create(person);
            DialogView.showMessage(view,"Persona añadida correctamente.");

            view.clearFields();

        } catch (DatabaseException ex) {
            DialogView.showError(view,"Error al añadir persona: ");
        } catch (Exception ex) {
            handleUnexpectedError(ex);
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
        } catch (Exception e) {
            handleUnexpectedError(e);
        }
    }

    @Override
    public void getEntity(int entityId) {
        try {
            Person person = (Person) personDAO.read(entityId);
            if (person != null) {
                populatePersonDialog(person);
            } else {
                DialogView.showMessage(view,"No se encontró ninguna persona con ese ID.");
            }
        } catch (DatabaseException e) {
            DialogView.showError(view,"Error al obtener la persona: ");
        } catch (Exception e) {
            handleUnexpectedError(e);
        }
    }

    @Override
    public void setActionListeners() {
        view.getSaveButton().addActionListener(e -> onSaveButtonClicked());
        if (view.isEditMode()) view.getEditPersonIdField().addActionListener(e -> view.onEditPersonIdFieldAction());
    }

    @Override
    public void onSaveButtonClicked() {
        if (isFormValid()) {
            if (view.isEditMode()) {
                editEntity();
            } else {
                addEntity();
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

    private void populatePersonDialog(Person person) {
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

    private void handleUnexpectedError(Exception e) {
        e.printStackTrace();
        DialogView.showError(view,"Error inesperado: ");
    }

}
