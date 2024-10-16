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
        addObserver(personView);
        registerEventHandlers(); // Configura los listeners para los botones en la vista
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
        // Manejar la creación de una persona
        personView.createPersonMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí deberías obtener los datos necesarios para crear una nueva persona
                // Supongamos que tienes un metodo en la vista para obtener los datos de la interfaz
                DimPerson newPerson = personView.getNewPersonData();
                createPerson(newPerson);
            }
        });

        // Manejar la actualización de una persona
        personView.updatePersonMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DimPerson selectedPerson = personView.getSelectedPerson();
                if (selectedPerson != null) {
                    // Suponiendo que tienes un metodo en la vista para obtener datos actualizados
                    DimPerson updatedPerson = personView.getUpdatedPersonData(selectedPerson);
                    updatePerson(updatedPerson);
                }
            }
        });

        // Manejar la eliminación de una persona
        personView.deletePersonMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DimPerson selectedPerson = personView.getSelectedPerson();
                if (selectedPerson != null) {
                    deletePerson(selectedPerson.getPersonSk()); // Asegúrate de que este metodo exista
                }
            }
        });

        // Aquí puedes agregar más manejadores para otros eventos si es necesario
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
        notifyObservers(newPerson, "update"); // Notifica a los observadores
        personView.updatePersonList(personDAO.getAll()); // Actualizamos la lista de personas
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


