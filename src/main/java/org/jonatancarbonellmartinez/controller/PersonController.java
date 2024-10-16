package org.jonatancarbonellmartinez.controller;

import org.jonatancarbonellmartinez.Observer.Observable;
import org.jonatancarbonellmartinez.Observer.Observer;
import org.jonatancarbonellmartinez.model.entities.DimPerson;
import org.jonatancarbonellmartinez.model.dao.DimPersonDAO;
import org.jonatancarbonellmartinez.view.PersonView;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
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
        addObserver(personView);  // Register the view as an observer
        registerEventHandlers();  // Configure event listeners for buttons in the view
    }

    // Implement Observable methods
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
        // Handle creating a person
        personView.createPersonMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get input data from the view
                String personNk = personView.getPersonNk();
                String personName = personView.getPersonName();
                int rankNumber = personView.getRankNumber();
                String lastName1 = personView.getPersonLastName1();
                String lastName2 = personView.getPersonLastName2();
                String dni = personView.getPersonDni();
                String phone = personView.getPersonPhone();
                String rank = personView.getPersonRank();
                String division = personView.getPersonDivision();
                int currentFlag = personView.getPersonCurrentFlag();

                // Create a new person in the controller
                createPerson(personNk, rankNumber, personName, lastName1, lastName2, dni, phone, rank, division, currentFlag);
            }
        });

        // Handle updating a person
        personView.updatePersonMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DimPerson selectedPerson = personView.getSelectedPerson();
                if (selectedPerson != null) {
                    // Update the selected person with new data from the view
                    selectedPerson.setPersonNk(personView.getPersonNk());
                    selectedPerson.setPersonRankNumber(personView.getRankNumber());
                    selectedPerson.setPersonName(personView.getPersonName());
                    selectedPerson.setPersonLastName1(personView.getPersonLastName1());
                    selectedPerson.setPersonLastName2(personView.getPersonLastName2());
                    selectedPerson.setPersonDni(personView.getPersonDni());
                    selectedPerson.setPersonPhone(personView.getPersonPhone());
                    selectedPerson.setPersonRank(personView.getPersonRank());
                    selectedPerson.setPersonDivision(personView.getPersonDivision());
                    selectedPerson.setPersonCurrentFlag(personView.getPersonCurrentFlag());
                    updatePerson(selectedPerson);
                }
            }
        });

        // Handle deleting a person
        personView.deletePersonMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DimPerson selectedPerson = personView.getSelectedPerson();
                if (selectedPerson != null) {
                    deletePerson(selectedPerson.getPersonSk());
                }
            }
        });
    }

    public void createPerson(String personNk, int rankNumber, String personName, String lastName1, String lastName2, String dni, String phone, String rank, String division, int currentFlag) {
        DimPerson newPerson = new DimPerson();
        newPerson.setPersonNk(personNk);
        newPerson.setPersonRankNumber(rankNumber);
        newPerson.setPersonName(personName);
        newPerson.setPersonLastName1(lastName1);
        newPerson.setPersonLastName2(lastName2);
        newPerson.setPersonDni(dni);
        newPerson.setPersonPhone(phone);
        newPerson.setPersonRank(rank);
        newPerson.setPersonDivision(division);
        newPerson.setPersonCurrentFlag(currentFlag);

        // Save the new person using the DAO
        personDAO.create(newPerson);
        notifyObservers(newPerson, "create");
        personView.updatePersonList(personDAO.getAll());
    }

    public void updatePerson(DimPerson person) {
        personDAO.update(person);
        notifyObservers(person, "update");
        personView.updatePersonList(personDAO.getAll());
    }

    public void deletePerson(int personSk) {
        personDAO.delete(personSk);
        notifyObservers(personDAO.read(personSk), "delete");
        personView.updatePersonList(personDAO.getAll());
    }

    public DimPerson getPerson(int personSk) {
        return personDAO.read(personSk);
    }
}


