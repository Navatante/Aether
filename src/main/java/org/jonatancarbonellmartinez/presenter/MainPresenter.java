package org.jonatancarbonellmartinez.presenter;

import org.jonatancarbonellmartinez.observers.Observer;
import org.jonatancarbonellmartinez.utilities.NavigationController;
import org.jonatancarbonellmartinez.view.*;

import java.awt.*;

public class MainPresenter implements Observer, Presenter {
    private final MainView view;
    private final  NavigationController navigationController;

    public MainPresenter(MainView view) {
        this.view = view;
        navigationController = new NavigationController(view);
    }

    @Override
    public void setActionListeners() {
        view.getBotonPersonal().addActionListener(e -> navigationController.navigateTo("PersonPanelView"));
        view.getBotonEventos().addActionListener(e -> navigationController.navigateTo("EventPanelView"));
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
            if (component instanceof PersonPanelView) {
                ((PersonPanelView) component).updatePanel();
            } else if (component instanceof EventPanelView) {
                ((EventPanelView) component).updatePanel();
            }
            // Add more `else if` blocks for other component types as needed
        }
    }
}
