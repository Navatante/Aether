package org.jonatancarbonellmartinez.presentation.viewmodel;

import org.jonatancarbonellmartinez.xexceptions.DatabaseException;
import org.jonatancarbonellmartinez.xfactory.DAOFactorySQLite;
import org.jonatancarbonellmartinez.data.database.GenericDAO;
import org.jonatancarbonellmartinez.data.model.Entity;
import org.jonatancarbonellmartinez.data.model.Person;
import org.jonatancarbonellmartinez.xobservers.Observer;
import org.jonatancarbonellmartinez.presentation.view.fxml.xDialogView;
import org.jonatancarbonellmartinez.presentation.view.fxml.xPersonXDialogXView;

public class xPersonXDialogXPresenter implements xPresenter, xDialogPresenter {
    private final GenericDAO<Person,Integer> personDAO;
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
            Person person = collectEntityData();
            person.setPersonCurrentFlag(1);

            personDAO.insert(person);
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
            Person person = collectEntityData();

            int personId = Integer.parseInt(view.getEditPersonIdField().getText());
            person.setPersonCurrentFlag(view.getPersonStateBox().getSelectedItem().toString().equals("Activo") ? 1 : 0);
            person.setPersonOrder(calculatePersonOrder());

            personDAO.update(person, personId);
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
            Person person = (Person) personDAO.read(entityId);
            if (person != null) {
                populateEntityDialog(person);
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
    public Person collectEntityData() {
        Person person = new Person();

        person.setPersonNk(view.getPersonNkField().getText().toUpperCase());
        person.setPersonRank(view.getEmpleoBox().getSelectedItem().toString());
        person.setPersonName(xDialogPresenter.capitalizeWords(view.getPersonNameField()));
        person.setPersonLastName1(xDialogPresenter.capitalizeWords(view.getPersonLastName1Field()));
        person.setPersonLastName2(xDialogPresenter.capitalizeWords(view.getPersonLastName2Field()));
        person.setPersonPhone(view.getPersonPhoneField().getText());
        person.setPersonDni(xDialogPresenter.calculateDniLetter(view.getPersonDniField()));
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
