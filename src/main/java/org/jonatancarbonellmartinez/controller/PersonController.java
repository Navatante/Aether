package org.jonatancarbonellmartinez.controller;

import org.jonatancarbonellmartinez.Observer.Observable;
import org.jonatancarbonellmartinez.Observer.Observer;
import org.jonatancarbonellmartinez.model.entities.DimPerson;
import org.jonatancarbonellmartinez.model.dao.DimPersonDAO;
import org.jonatancarbonellmartinez.view.PersonView;

import java.util.ArrayList;
import java.util.List;

// Controller to handle interactions between the view and the model
public class PersonController implements Observable {
    private DimPersonDAO personDAO;
    private PersonView personView;
    private List<Observer> observers;

    public PersonController(DimPersonDAO personDAO, PersonView personView) {
        this.personDAO = personDAO;
        this.personView = personView;
        this.observers = new ArrayList<>();

        // Configura los listeners para los botones en la vista
        registerEventHandlers();
    }

    // Implementación de métodos de la interfaz Observable
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(DimPerson person, String propertyName) {
        for (Observer observer : observers) {
            observer.update(person, propertyName);
        }
    }

    private void registerEventHandlers() {
        personView.createPersonMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createPerson();
            }
        });

        // Agregar listeners para los otros botones (update, delete, etc.)
    }

    public void createPerson(String personNk, int personRankNumber, String personRank, String personName, String personLastName1, String personLastName2, String personDni, String personPhone, String personDivision, int personCurrentFlag) {
        // Aquí creamos la instancia de DimPerson con los datos recibidos
        DimPerson newPerson = new DimPerson();
        newPerson.setPersonNk(personNk);
        newPerson.setPersonRankNumber(personRankNumber);
        newPerson.setPersonRank(personRank);
        newPerson.setPersonName(personName);
        newPerson.setPersonLastName1(personLastName1);
        newPerson.setPersonLastName2(personLastName2);
        newPerson.setPersonDni(personDni);
        newPerson.setPersonPhone(personPhone);
        newPerson.setPersonDivision(personDivision);
        newPerson.setPersonCurrentFlag(personCurrentFlag);

        // Guardamos la nueva persona usando el DAO
        personDAO.create(newPerson);

        // Actualizamos la lista de personas en la vista
        personView.updatePersonList(personDAO.getAll());
    }


    public void updatePerson(DimPerson person) {
        personDAO.update(person);
        personView.updatePersonList(personDAO.getAll()); // actualiza la vista
    }

    public void deletePerson(int personSk) {
        personDAO.delete(personSk);
        personView.updatePersonList(personDAO.getAll()); // actualiza la vista

    }

    public DimPerson getPerson(int personSk) {
        return personDAO.read(personSk);
    }
}


