package org.jonatancarbonellmartinez.presentation.viewmodel;

import org.jonatancarbonellmartinez.presentation.view.fxml.xEventXPanelXView;
import org.jonatancarbonellmartinez.presentation.view.fxml.xMainXView;
import org.jonatancarbonellmartinez.presentation.view.fxml.xPersonXPanelXView;
import org.jonatancarbonellmartinez.presentation.view.fxml.xRecentFlightsXPanelXView;
import org.jonatancarbonellmartinez.xobservers.Observer;
import org.jonatancarbonellmartinez.xcoordinator.NavigationController;

import java.awt.*;

public class xMainXPresenter implements Observer, xPresenter {
    private final xMainXView view;
    private final  NavigationController navigationController;

    public xMainXPresenter(xMainXView view) {
        this.view = view;
        navigationController = new NavigationController(view);
    }

    @Override
    public void setActionListeners() {
        view.getBotonPersonal().addActionListener(e -> navigationController.navigateTo("PersonPanelView"));
        view.getBotonEventos().addActionListener(e -> navigationController.navigateTo("EventPanelView"));
        view.getBotonRecentFlights().addActionListener(e -> navigationController.navigateTo("RecentFlightsPanelView"));
        view.getRegistrarVueloMenuItem().addActionListener(e -> navigationController.openDialog("RegistrarVuelo"));
        view.getAnadirPersonalMenuItem().addActionListener(e -> navigationController.openDialog("AddPerson"));
        view.getEditarPersonalMenuItem().addActionListener(e -> navigationController.openDialog("EditPerson"));
        view.getAnadirEventoMenuItem().addActionListener(e -> navigationController.openDialog("AddEvent"));
        view.getEditarEventoMenuItem().addActionListener(e -> navigationController.openDialog("EditEvent"));
        // Add more
    }

    @Override
    public void update() {
        // Iterate through all components in the CardPanel
        for (Component component : view.getCardPanel().getComponents()) {
            if (component instanceof xPersonXPanelXView) {
                ((xPersonXPanelXView) component).updatePanel();
            } else if (component instanceof xEventXPanelXView) {
                ((xEventXPanelXView) component).updatePanel();
            } else if (component instanceof xRecentFlightsXPanelXView) {
                ((xRecentFlightsXPanelXView) component).updatePanel();
            }
            // Add more `else if` blocks for other component types as needed
        }
    }
}
