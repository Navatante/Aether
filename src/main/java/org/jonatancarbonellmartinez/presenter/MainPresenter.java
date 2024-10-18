package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.view.*;

import java.util.*;

public class MainPresenter {
    private MainView mainView;
    private Map<String, Object> presenters;
    private Map<String, Object> views;  // Store views to avoid re-initialization

    public MainPresenter() {
        this.presenters = new HashMap<>();
        this.views = new HashMap<>();
       // bindViewActions(); // Bind actions immediately after initialization
    }

    public void addPresenter(String key, Object presenter) {
        presenters.put(key, presenter);
    }

    public void setView(MainView mainView) {
        this.mainView = mainView;  // Now you have a reference to the view
        //bindViewActions();         // Bind actions after the view is set
    }

//    private void bindViewActions() {
//        mainView.getPersonMenuItem().addActionListener(e -> showView("person"));
//        mainView.getFlightMenuItem().addActionListener(e -> showView("flight"));
//        mainView.getEventMenuItem().addActionListener(e -> showView("event"));
//    }

    private void showView(String key) {
        Object presenter = presenters.get(key);

        if (!views.containsKey(key)) {
            views.put(key, initializeView(key, presenter));  // Lazy init
        }

        mainView.showView(views.get(key));  // Show the view
    }

    private Object initializeView(String key, Object presenter) {
        if (presenter instanceof AddPersonPresenter) {
            AddPersonPresenter personPresenter = (AddPersonPresenter) presenter;
            return personPresenter.getView();  // Lazily initialize PersonView
        } //else if (presenter instanceof FlightPresenter) {
            //FlightPresenter flightPresenter = (FlightPresenter) presenter;
            //return flightPresenter.getView();  // Lazily initialize FlightView
        //}
        // Handle other cases similarly
        return null;
    }
}
